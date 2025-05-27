package com.fit2081.krishna_a3_34413111

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.data.Patient
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                val viewModel: PatientViewModel = ViewModelProvider(
                        this, PatientViewModel.PatientViewModelFactory(this@LoginScreen)
                )[PatientViewModel::class.java]

                LoginScreenContent(viewModel = viewModel)
            }
        }
    }
}

/*
* Top bar that allows users to go back to welcome screen
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    viewModel: PatientViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome back buddy!", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
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
        Login(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
    }
}


/*
* Function to create the dropdown text field for the userID
* Gets the userId values from a function that creates a list of userId values (UserIdValues)
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdDropdownField(
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


private fun String.toDoubleOrNullSafe(): Double? {
    return this.trim().ifEmpty { null }?.toDoubleOrNull()
}

/*
* Reads the user data from csv on the first run
* */
fun readPatientsFromCSV(context: Context): List<Patient> {

    val patients = mutableListOf<Patient>()
    val inputStream = context.assets.open("AppUserData.csv")
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.readLine() // skip header
    var line: String?

    while (reader.readLine().also { line = it } != null) {
        val tokens = line!!.split(",")
        if (tokens.size >= 63) {
            val patient = Patient(
                userId = tokens[1].trim(),
                phoneNumber = tokens[0].trim(),
                name = "",
                password = "",
                sex = tokens[2].trim(),
                HEIFAtotalscoreMale = tokens[3].toDoubleOrNullSafe(),
                HEIFAtotalscoreFemale = tokens[4].toDoubleOrNullSafe(),
                DiscretionaryHEIFAscoreMale = tokens[5].toDoubleOrNullSafe(),
                DiscretionaryHEIFAscoreFemale = tokens[6].toDoubleOrNullSafe(),
                Discretionaryservesize = tokens[7].toDoubleOrNullSafe(),
                VegetablesHEIFAscoreMale = tokens[8].toDoubleOrNullSafe(),
                VegetablesHEIFAscoreFemale = tokens[9].toDoubleOrNullSafe(),
                Vegetableswithlegumesallocatedservesize = tokens[10].toDoubleOrNullSafe(),
                LegumesallocatedVegetables = tokens[11].toDoubleOrNullSafe(),
                Vegetablesvariationsscore = tokens[12].toDoubleOrNullSafe(),
                VegetablesCruciferous = tokens[13].toDoubleOrNullSafe(),
                VegetablesTuberandbulb = tokens[14].toDoubleOrNullSafe(),
                VegetablesOther = tokens[15].toDoubleOrNullSafe(),
                Legumes = tokens[16].toDoubleOrNullSafe(),
                VegetablesGreen = tokens[17].toDoubleOrNullSafe(),
                VegetablesRedandorange = tokens[18].toDoubleOrNullSafe(),
                FruitHEIFAscoreMale = tokens[19].toDoubleOrNullSafe(),
                FruitHEIFAscoreFemale = tokens[20].toDoubleOrNullSafe(),
                Fruitservesize = tokens[21].toDoubleOrNullSafe(),
                Fruitvariationsscore = tokens[22].toDoubleOrNullSafe(),
                FruitPome = tokens[23].toDoubleOrNullSafe(),
                FruitTropicalandsubtropical = tokens[24].toDoubleOrNullSafe(),
                FruitBerry = tokens[25].toDoubleOrNullSafe(),
                FruitStone = tokens[26].toDoubleOrNullSafe(),
                FruitCitrus = tokens[27].toDoubleOrNullSafe(),
                FruitOther = tokens[28].toDoubleOrNullSafe(),
                GrainsandcerealsHEIFAscoreMale = tokens[29].toDoubleOrNullSafe(),
                GrainsandcerealsHEIFAscoreFemale = tokens[30].toDoubleOrNullSafe(),
                Grainsandcerealsservesize = tokens[31].toDoubleOrNullSafe(),
                GrainsandcerealsNonwholegrains = tokens[32].toDoubleOrNullSafe(),
                WholegrainsHEIFAscoreMale = tokens[33].toDoubleOrNullSafe(),
                WholegrainsHEIFAscoreFemale = tokens[34].toDoubleOrNullSafe(),
                Wholegrainsservesize = tokens[35].toDoubleOrNullSafe(),
                MeatandalternativesHEIFAscoreMale = tokens[36].toDoubleOrNullSafe(),
                MeatandalternativesHEIFAscoreFemale = tokens[37].toDoubleOrNullSafe(),
                Meatandalternativeswithlegumesallocatedservesize = tokens[38].toDoubleOrNullSafe(),
                LegumesallocatedMeatandalternatives = tokens[39].toDoubleOrNullSafe(),
                DairyandalternativesHEIFAscoreMale = tokens[40].toDoubleOrNullSafe(),
                DairyandalternativesHEIFAscoreFemale = tokens[41].toDoubleOrNullSafe(),
                Dairyandalternativesservesize = tokens[42].toDoubleOrNullSafe(),
                SodiumHEIFAscoreMale = tokens[43].toDoubleOrNullSafe(),
                SodiumHEIFAscoreFemale = tokens[44].toDoubleOrNullSafe(),
                Sodiummgmilligrams = tokens[45].toDoubleOrNullSafe(),
                AlcoholHEIFAscoreMale = tokens[46].toDoubleOrNullSafe(),
                AlcoholHEIFAscoreFemale = tokens[47].toDoubleOrNullSafe(),
                Alcoholstandarddrinks = tokens[48].toDoubleOrNullSafe(),
                WaterHEIFAscoreMale = tokens[49].toDoubleOrNullSafe(),
                WaterHEIFAscoreFemale = tokens[50].toDoubleOrNullSafe(),
                Water = tokens[51].toDoubleOrNullSafe(),
                WaterTotalmL = tokens[52].toDoubleOrNullSafe(),
                BeverageTotalmL = tokens[53].toDoubleOrNullSafe(),
                SugarHEIFAscoreMale = tokens[54].toDoubleOrNullSafe(),
                SugarHEIFAscoreFemale = tokens[55].toDoubleOrNullSafe(),
                Sugar = tokens[56].toDoubleOrNullSafe(),
                SaturatedFatHEIFAscoreMale = tokens[57].toDoubleOrNullSafe(),
                SaturatedFatHEIFAscoreFemale = tokens[58].toDoubleOrNullSafe(),
                SaturatedFat = tokens[59].toDoubleOrNullSafe(),
                UnsaturatedFatHEIFAscoreMale = tokens[60].toDoubleOrNullSafe(),
                UnsaturatedFatHEIFAscoreFemale = tokens[61].toDoubleOrNullSafe(),
                UnsaturatedFatservesize = tokens[62].toDoubleOrNullSafe()
            )
            patients.add(patient)
        }
    }

    return patients
}

/*
* Main function with all the UI features
* */
@Composable
fun Login(modifier: Modifier, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val patientViewModel: PatientViewModel =
        viewModel(factory = PatientViewModel.PatientViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()

    var selectedUserId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                text = "Log in",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calls dropdown function for selecting user ID
            IdDropdownField(patientViewModel) { selectedUserId = it }

            Spacer(modifier = Modifier.height(8.dp))

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
                        patientViewModel.validatePatient(selectedUserId, password) { isValid ->
                            if (isValid) {
                                UserManager.setUserId(selectedUserId)
                                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT)
                                    .show()
                                context.startActivity(
                                    Intent(
                                        context,
                                        IntakeQuestionnaire::class.java
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Invalid User ID or Password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text(
                    "Continue",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Register button
            Button(
                onClick = {
                    context.startActivity(Intent(context, RegisterScreen::class.java))
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465)),
                modifier = Modifier.height(50.dp).width(300.dp)
            ) {
                Text(
                    "Register",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}
