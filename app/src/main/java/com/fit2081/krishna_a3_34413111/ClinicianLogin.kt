package com.fit2081.krishna_a3_34413111

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.fit2081.krishna_a3_34413111.HomePage
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme

class ClinicianLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClinicianLoginBar(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/*
* Top bar to go back to homepage if we have entered the clinician login
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianLoginBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "For Clinicians Only", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, HomePage::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF311465))
            )
        }
    ) { paddingValues ->
        // Pass paddingValues to Login to handle scaffold content padding correctly
        ClinicianLoginScreen(modifier = Modifier.padding(paddingValues))
    }
}

/*
* Main function that has all the UI for clinician login:
* The auth key to get in is 'letmein123'
* */
@Composable
fun ClinicianLoginScreen(modifier: Modifier) {
    val authKey = "letmein123"  //  fixed clinician key
    var clinicianKey by remember { mutableStateOf("") }
    val context = LocalContext.current
    val showError = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Clinician Login",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Enter clinician key
            OutlinedTextField(
                value = clinicianKey,
                onValueChange = { clinicianKey = it; showError.value = false },  // reset error on input change },
                label = { Text("Enter your clinician key") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "For the sake of this assignment, I hardcoded the clinician key in the code " +
                        "(I didn't know what else to do) :P"
            )

            Button(
                onClick = {
                    if (clinicianKey == authKey) {
                        context.startActivity(Intent(context, ClinicianDashboard::class.java))
                    } else {
                        showError.value = true
                    }
                }
                ,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text(
                    "Log into Clinician Portal",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            if (showError.value) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Incorrect clinician key. Please try again.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

