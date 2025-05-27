package com.fit2081.krishna_a3_34413111.homepages

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.krishna_a3_34413111.IntakeQuestionnaire
import com.fit2081.krishna_a3_34413111.R
import com.fit2081.krishna_a3_34413111.UserManager
import com.fit2081.krishna_a3_34413111.data.PatientViewModel

/*
* Main function for Home Screen that has all the UI
* */
@Composable
fun Home(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PatientViewModel = viewModel()
) {
    val selectedUserId = UserManager.userId

    LaunchedEffect(selectedUserId) {
        viewModel.fetchPatientName(selectedUserId)
        viewModel.fetchTotalHeifaScore(selectedUserId)
    }

    val patientName by viewModel.patientName
    val totalHeifaScore by viewModel.totalHeifaScore
    val context = LocalContext.current

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
            // Greeting to user
            Text(
                text = "Hey, ${patientName ?: "User"}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            // Section that allows user to go back and edit their questionnaire choices
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "You've already filled out your food intake questionnaire, " +
                                "but you can change your details here: ",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    context,
                                    IntakeQuestionnaire::class.java
                                )
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(
                                0xFF311465
                            )
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }
            }

            // Plate image
            Image(
                painter = painterResource(id = R.drawable.plate_image),
                contentDescription = "Plate image",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp)
            )

            Spacer(Modifier.height(8.dp))

            // Section that shows user's score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Text(
                        "My Score",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        onClick = { navController.navigate("insights") }
                    ) {
                        Text(
                            "See all scores >", // Allows user to go to screen 6 by clicking this
                            color = Color.Gray,
                            fontSize = 16.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Text("Your food quality score:  ")
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Text(
                        "$totalHeifaScore/100",
                        color = Color(0xFF16621B),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            // Information about the food quality score from expected output
            Text(
                "What is the Food Quality Score?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Text(
                "Your Good Quality Score provides a snapshot of how well your eating " +
                        "patterns align with established food guidelines, helping you identify " +
                        "both strengths and opportunities for improvement in your diet.",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Text(
                "This personalised measurement considers various food groups including " +
                        "vegetables, fruits, whole grains, and protein to give you practical " +
                        "insights for making healthier food choices.",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}