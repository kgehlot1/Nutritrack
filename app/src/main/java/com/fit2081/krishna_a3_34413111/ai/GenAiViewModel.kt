package com.fit2081.krishna_a3_34413111.ai

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.krishna_a3_34413111.BuildConfig
import com.fit2081.krishna_a3_34413111.ai.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenAIViewModel : ViewModel() {

    /**
     * Mutable state flow to hold the current UI state.
     * Initially set to `UiState.Initial`.
     */
    @SuppressLint("RestrictedApi")
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)

    /**
     * Publicly exposed immutable state flow for observing the UI state.
     */
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Holds the last 5 tips
    private val _tipHistory = MutableStateFlow<List<String>>(emptyList())
    val tipHistory: StateFlow<List<String>> = _tipHistory.asStateFlow()

    /**
     * Instance of the GenerativeModel used to generate content.
     * The model is initialized with a specific model name and API key.
     */
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKeySafe
    )

    fun readCsvFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun parseInsights(text: String): List<String> {
        return text.lines()
            .map { it.trimStart('•', '-', ' ', '\t') }
            .filter { it.isNotBlank() }
    }


    /**
     * Sends a prompt to the generative AI model and updates the UI state
     * based on the response.
     *
     * @param prompt The input text prompt to be sent to the generative model.
     */
    fun sendPrompt(prompt: String) {
        // Set the UI state to Loading before making the API call.
        _uiState.value = UiState.Loading

        // Launch a coroutine in the IO dispatcher to perform the API call.
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Generate content using the generative model.
                val response = generativeModel.generateContent (
                    content {
                        text(prompt) // Set the input text for the model.
                    })

                // Update the UI state with the generated content if successful.
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)

                    // Update tip history (only keep latest 5)
                    _tipHistory.update { current ->
                        (listOf(outputContent) + current).take(5)
                    }
                }
            } catch (e: Exception) {
                // Update the UI state with an error message if an exception occurs.
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    fun sendCsvForAnalysis(csvText: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(
                            """
                        Analyze the dataset below and extract 3 key patterns or insights.
                        Be concise and write them as bullet points.
                        
                        Dataset:
                        $csvText
                        """.trimIndent()
                        )
                    }
                )

                response.text?.let { output ->
                    _uiState.value = UiState.Success(output)

                    // Save to history (last 5 pattern sets)
                    _tipHistory.update { current ->
                        (listOf(output) + current).take(5)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
