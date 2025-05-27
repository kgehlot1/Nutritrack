package com.fit2081.krishna_a3_34413111.homepages

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.krishna_a3_34413111.UserManager
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import java.io.BufferedReader
import java.io.InputStreamReader

/*
* Data class to store the food type's information from csv and expected output
* */
data class FoodScore(
    val name: String,
    val score: Float,
    val max: Float
)

/*
* Function to get all the scores from the database and store them in variables to make a list of these variables
* Then creates a row of the name, LinearProgressIndicator, score out of max for UI later
* */
@Composable
fun ScoreSection(context: Context = LocalContext.current,
    viewModel: PatientViewModel = viewModel()
) {
    val selectedUserId = UserManager.userId

    // Load patient on first composition
    LaunchedEffect(selectedUserId) {
        selectedUserId?.let { viewModel.loadPatient(it) }
    }

    val patient by viewModel.selectedPatient

    patient?.let { data ->
        val sex = data.sex

        val scores = listOf(
            FoodScore("Vegetable", if (sex == "Male") data.VegetablesHEIFAscoreMale?.toFloat() ?: 0f else data.VegetablesHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Fruit", if (sex == "Male") data.FruitHEIFAscoreMale?.toFloat() ?: 0f else data.FruitHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Grains and Cereals", if (sex == "Male") data.GrainsandcerealsHEIFAscoreMale?.toFloat() ?: 0f else data.GrainsandcerealsHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Whole Grains", if (sex == "Male") data.WholegrainsHEIFAscoreMale?.toFloat() ?: 0f else data.WholegrainsHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Meat and Alternatives", if (sex == "Male") data.MeatandalternativesHEIFAscoreMale?.toFloat() ?: 0f else data.MeatandalternativesHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Dairy", if (sex == "Male") data.DairyandalternativesHEIFAscoreMale?.toFloat() ?: 0f else data.DairyandalternativesHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Water", if (sex == "Male") data.WaterHEIFAscoreMale?.toFloat() ?: 0f else data.WaterHEIFAscoreFemale?.toFloat() ?: 0f, 5f),
            FoodScore("Unsaturated Fats", if (sex == "Male") data.UnsaturatedFatHEIFAscoreMale?.toFloat() ?: 0f else data.UnsaturatedFatHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Saturated Fats", if (sex == "Male") data.SaturatedFatHEIFAscoreMale?.toFloat() ?: 0f else data.SaturatedFatHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Sodium", if (sex == "Male") data.SodiumHEIFAscoreMale?.toFloat() ?: 0f else data.SodiumHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Sugar", if (sex == "Male") data.SugarHEIFAscoreMale?.toFloat() ?: 0f else data.SugarHEIFAscoreFemale?.toFloat() ?: 0f, 10f),
            FoodScore("Alcohol", if (sex == "Male") data.AlcoholHEIFAscoreMale?.toFloat() ?: 0f else data.AlcoholHEIFAscoreFemale?.toFloat() ?: 0f, 5f),
            FoodScore("Discretionary Foods", if (sex == "Male") data.DiscretionaryHEIFAscoreMale?.toFloat() ?: 0f else data.DiscretionaryHEIFAscoreFemale?.toFloat() ?: 0f, 10f)
        )


        scores.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    modifier = Modifier.weight(1.5f),
                    fontSize = 14.sp
                )

                LinearProgressIndicator(
                    progress = category.score / category.max,
                    modifier = Modifier
                        .weight(3f)
                        .height(6.dp),
                    color = Color(0xFF311465),
                    trackColor = Color.Gray
                )

                Text(
                    text = String.format("%.2f/%.1f", category.score, category.max),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

/*
* Main function for Insights that has all the UI
* */
@Composable
fun Insights(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PatientViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedUserId = UserManager.userId

    LaunchedEffect(selectedUserId) {
        viewModel.fetchTotalHeifaScore(selectedUserId)
    }

    val totalHeifaScore by viewModel.totalHeifaScore

    // Handle the null case separately
    if (selectedUserId == null) {
        Text("No user selected")
        return
    }

    var sharedText by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "Insights: Food Score",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )

            // Calls the function to display all the food types and their scores
            ScoreSection(viewModel = viewModel)

            Spacer(Modifier.padding(vertical = 6.dp))

            Text(
                text = "Total Food Quality Score",
                modifier = Modifier.padding(vertical = 1.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                LinearProgressIndicator(
                    progress = (totalHeifaScore?.toFloat() ?: 0f) / 100f,
                    modifier = Modifier.height(9.dp),
                    color = Color(0xFF311465),
                    trackColor = Color.Gray
                )

                Text(
                    text = String.format(" %.1f / %d", totalHeifaScore, 100),
                    modifier = Modifier.padding(vertical = 10.dp),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // "Share" button that calls ShareButton function so the user can share their score
                ShareButton(sharedText)

                // "Improve my diet!" button that leads to NutriCoach screen that has not been implemented yet
                Button(
                    onClick = {
                        navController.navigate("nutricoach")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text("Improve my diet!")
                }
            }
        }
    }
}

/*
* Function that pops up when the user wants to share their food quality score.
* */
@Composable
fun ShareButton(sharedText: String)
{
    val context = LocalContext.current

    Spacer(modifier = Modifier.padding(16.dp))

    Button(
        onClick = {
            val shareIntent = Intent(ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText)
            context.startActivity(Intent.createChooser(shareIntent, "Download Nutritrack! Share score via:"))
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text("Share with someone!")
    }
}
