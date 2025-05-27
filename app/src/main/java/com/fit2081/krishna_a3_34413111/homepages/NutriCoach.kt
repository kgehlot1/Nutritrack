package com.fit2081.krishna_a3_34413111.homepages

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.krishna_a3_34413111.LoginScreen
import com.fit2081.krishna_a3_34413111.ai.GenAIViewModel
import com.fit2081.krishna_a3_34413111.ai.UiState
import com.fit2081.krishna_a3_34413111.fruitData.FruitViewModel
import kotlinx.coroutines.flow.forEach

/*
* Nutricoach screen that uses Fruity Vice API and Gemini API to:
* 1. Get information about different fruits
* 2. Generate a motivational tip for the patient
* */
@Composable
fun NutriCoach(
    modifier: Modifier = Modifier,
    navController: NavController,
    fruitViewModel: FruitViewModel = viewModel(),
    aiViewModel: GenAIViewModel = viewModel()
) {
    val fruit by fruitViewModel.fruit.collectAsState()
    val error by fruitViewModel.error.collectAsState()
    val uiState by aiViewModel.uiState.collectAsState()
    val tipHistory by aiViewModel.tipHistory.collectAsState()
    val scrollState = rememberScrollState()

    var fruitName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "NutriCoach",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.Black
            )

            Text("Fruit Nutrition Finder", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = fruitName,
                        onValueChange = { fruitName = it },
                        label = { Text("e.g. banana") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { if (fruitName.isNotBlank()) {
                            fruitViewModel.fetchFruit(fruitName)
                        } },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C4AB6)),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Search", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            error?.let {
                Text(it, color = Color.Red)
            }

            fruit?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        FruitRow("family", it.family)
                        FruitRow("calories", it.nutritions.calories.toString())
                        FruitRow("fat", it.nutritions.fat.toString())
                        FruitRow("sugar", it.nutritions.sugar.toString())
                        FruitRow("carbohydrates", it.nutritions.carbohydrates.toString())
                        FruitRow("protein", it.nutritions.protein.toString())
                    }
                }
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Button(onClick = { aiViewModel.sendPrompt("Give me a short motivational tip") }) {
                Text("Get Motivational Tip")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (uiState) {
                is UiState.Initial -> Text("Press the button to get inspired!")
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Success -> Text(text = (uiState as UiState.Success).outputText)
                is UiState.Error -> Text("Error: ${(uiState as UiState.Error).errorMessage}")
            }

            Spacer(modifier = Modifier.height(12.dp))

            (uiState as? UiState.Success)?.let { successState ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEAFF)),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = successState.outputText,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF311465)
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
                ) {
                    Text(
                        text = "Show All Tips",
                        color = Color.White
                    )
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        confirmButton = {
                            Button(onClick = { showDialog = false }) {
                                Text("Close")
                            }
                        },
                        title = { Text("Motivational AI Tips") },
                        text = {
                            if (tipHistory.isEmpty()) {
                                Text("No tips available.")
                            } else {
                                Column(modifier = Modifier.verticalScroll(scrollState)) {
                                    tipHistory.forEach { tip ->
                                        Text(
                                            "• $tip",
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FruitRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$label:", fontWeight = FontWeight.Bold)
        Text(value)
    }
}

