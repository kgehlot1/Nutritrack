package com.fit2081.krishna_a3_34413111

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.krishna_a3_34413111.ai.GenAIViewModel
import com.fit2081.krishna_a3_34413111.ai.UiState
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.HomePage
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme

class ClinicianDashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    val patientViewModel: PatientViewModel = viewModel(
                        factory = PatientViewModel.PatientViewModelFactory(context))
                    ClinicianDashboard(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = patientViewModel
                    )
                }
            }
        }
    }
}

/*
* Reads the values from CSV to send to the AI to generate insights
* */
fun readCsvFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

/*
* Main function with all the UI for clinician dashboard that shows:
* 1. Average HEIFA scores for males and females
* 2. generated insights from AI using the CSV file
* */
@Composable
fun ClinicianDashboard(modifier: Modifier = Modifier, viewModel: PatientViewModel) {

    LaunchedEffect(Unit) {
        viewModel.observeAverageHeifaScores()
    }

    val maleHeifaAvg = viewModel.averageMaleScore.toString()
    val femaleHeifaAvg = viewModel.averageFemaleScore.toString()
    val context = LocalContext.current
    val aiViewModel: GenAIViewModel = viewModel()
    val uiState by aiViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Clinician Dashboard",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Average male HEIFA scores: $maleHeifaAvg",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Average female HEIFA scores: $femaleHeifaAvg",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    val csvText = readCsvFromAssets(context, "AppUserData.csv")
                    aiViewModel.sendCsvForAnalysis(csvText)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text("Find data patterns!")
            }

            when (uiState) {
                is UiState.Initial -> Text("Submit CSV to get insights")
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Error -> Text("Error: ${(uiState as UiState.Error).errorMessage}")
                is UiState.Success -> {
                    val output = (uiState as UiState.Success).outputText

                    // Use the ViewModel's parser function
                    val insights = aiViewModel.parseInsights(output)

                    InsightCards(insights = insights)
                }
            }

            Button(
                onClick = {context.startActivity(Intent(context, HomePage::class.java))},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text("Back to Patient Homepage")
            }
        }
    }
}

@Composable
fun InsightCards(insights: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(insights) { insight ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = insight,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

