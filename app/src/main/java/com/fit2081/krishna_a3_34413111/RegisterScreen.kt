package com.fit2081.krishna_a3_34413111

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Base64
import androidx.compose.material.icons.filled.KeyboardArrowDown
import com.fit2081.krishna_a3_34413111.LoginScreen
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.data.Patient
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme
import kotlinx.coroutines.launch

class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                val viewModel: PatientViewModel = ViewModelProvider(
                    this, PatientViewModel.PatientViewModelFactory(this@RegisterScreen)
                )[PatientViewModel::class.java]

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Register(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
                }
            }
        }
    }
}

/*
* Function to create the dropdown text field for the userID
* Gets the userId values from a function that creates a list of userId values (UserIdValues)
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdSelectField(
    patientViewModel: PatientViewModel,
    onSelectedChange: (String) -> Unit
) {
    val allPatients by patientViewModel.allPatients.collectAsState(initial = emptyList())
    val userIds = allPatients.map { it.userId }

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(userIds.firstOrNull() ?: "Select ID") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = "My ID (Provided by Clinician)",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.Black
            ),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .menuAnchor(),
            shape = RoundedCornerShape(15.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            userIds.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        selectedOption = option
                        onSelectedChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/*
* Function that creates a text field for user to input their phone number
* */
@Composable
fun PhoneNoField(onPhoneNoChange: (String) -> Unit) {
    var phoneNo by remember { mutableStateOf("") }

    OutlinedTextField(
        value = phoneNo,
        onValueChange = {
            val filtered = it.filter { char -> char.isDigit() }
            phoneNo = filtered
            onPhoneNoChange(filtered) // Call the callback with the updated value
        },
        label = {
            Text(
                text = "Phone Number",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        },
        textStyle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone // Ensures numeric keypad
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Phone Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(15.dp),
        singleLine = true
    )
}

/*
* Main Register function with UI that gets all user details, fills up the database,
* verifies using database if the user details are correct, then proceeds to login screen so user can login.
* */
@Composable
fun Register(modifier: Modifier, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val patientViewModel: PatientViewModel =
        viewModel(factory = PatientViewModel.PatientViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()

    var selectedUserId by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cnfmPassword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (viewModel.isFirstLaunch(context)) {
            val patients = readPatientsFromCSV(context)
            viewModel.insertAll(patients)
            viewModel.markFirstLaunchDone(context)
        }
    }

    // ui here
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
                text = "Register",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calls dropdown function for selecting user ID
            IdSelectField(patientViewModel) { selectedUserId = it }

            Spacer(modifier = Modifier.height(8.dp))

            // Phone no input
            PhoneNoField() { phoneNo = it }

            // Name field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    name = filtered
                },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(15.dp)
            )

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(15.dp)
            )

            // Re-enter password to confirm
            OutlinedTextField(
                value = cnfmPassword,
                onValueChange = { cnfmPassword = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(15.dp)
            )

            Text(
                text = "This app is only for pre-registered users. " +
                        "Please have your ID and phone number handy before continuing.",
                color = Color.DarkGray,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Button to confirm and verify login (calls ValidateUser function later)
            // Login button
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (password != cnfmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        val patient = patientViewModel.getPatientByUserId(selectedUserId)

                        if (patient == null || patient.phoneNumber != phoneNo) {
                            Toast.makeText(context, "Invalid ID or phone number", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        if (!patient.name.isNullOrEmpty() || !patient.password.isNullOrEmpty()) {
                            Toast.makeText(context, "This account has already been registered", Toast.LENGTH_SHORT).show()
                            return@launch
                        }

                        // Save name and password
                        patient.name = name
                        patient.password = password
                        patientViewModel.update(patient)

                        // Navigate to next screen
                        context.startActivity(Intent(context, LoginScreen::class.java))
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text(
                    "Confirm Registration",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Go back to login screen button
            Button(
                onClick = {
                    context.startActivity(Intent(context, LoginScreen::class.java))
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text(
                    "Back to Login",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}
