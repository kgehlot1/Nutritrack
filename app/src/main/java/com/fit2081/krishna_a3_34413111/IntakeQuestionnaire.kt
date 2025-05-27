package com.fit2081.krishna_a3_34413111

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.krishna_a3_34413111.HomePage
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme
import com.fit2081.krishna_a3_34413111.*
import com.fit2081.krishna_a3_34413111.data.FoodIntake
import com.fit2081.krishna_a3_34413111.data.FoodIntakeRepository
import com.fit2081.krishna_a3_34413111.data.FoodIntakeViewModel
import java.time.LocalTime
import java.util.Calendar

class IntakeQuestionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionnaireContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/*
* Function to implement a Top App Bar (to go back to Login Screen)
* Calls the main questionnaire function later to display it as content
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(90.dp),
                title = {
                    Text(
                        text = "Food Intake Questionnaire",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        UserManager.clearUser()
                        context.startActivity(Intent(context, LoginScreen::class.java)) }) {
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
    ) { paddingValues -> // Explicitly specify content padding and calls main function Questionnaire
        val foodIntakeViewModel: FoodIntakeViewModel =
            viewModel(factory = FoodIntakeViewModel.Factory(context))
        IntakeQuestionnaire(modifier = Modifier.padding(paddingValues), viewModel = foodIntakeViewModel)
    }
}

/*
* Helping function that display the dialog of the persona (i.e. persona information) when clicked.
* Uses information in PersonaDialogs.kt for the persona's details
* */
@Composable
fun PersonaInfoButtons() {
    // Storing the persona's ui and info
    var showHealthDevoteeDialog by remember { mutableStateOf(false) }
    var showMindfulEaterDialog by remember { mutableStateOf(false) }
    var showWellnessStriverDialog by remember { mutableStateOf(false) }
    var showBalanceSeekerDialog by remember { mutableStateOf(false) }
    var showHealthProcrastinatorDialog by remember { mutableStateOf(false) }
    var showFoodCarefreeDialog by remember { mutableStateOf(false) }

    // Row 1: first three personas
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { showHealthDevoteeDialog = true },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Health Devotee", textAlign = TextAlign.Center)
        }

        Button(
            onClick = { showMindfulEaterDialog = true },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Mindful Eater", textAlign = TextAlign.Center)
        }

        Button(
            onClick = { showWellnessStriverDialog = true },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Wellness Striver", textAlign = TextAlign.Center)
        }
    }

    // Row 2: final three personas
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { showBalanceSeekerDialog = true },
            modifier = Modifier
                .weight(1.15f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Balance Seeker", textAlign = TextAlign.Center)
        }
        Button(
            onClick = { showHealthProcrastinatorDialog = true },
            modifier = Modifier
                .weight(1.6f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Health Procrastinator", textAlign = TextAlign.Center)
        }
        Button(
            onClick = { showFoodCarefreeDialog = true },
            modifier = Modifier
                .weight(1.25f)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF311465),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Food Carefree", textAlign = TextAlign.Center)
        }
    }

    // Show dialogs from PersonaDialogs.kt
    if (showHealthDevoteeDialog) {
        HealthDevoteePersona(onDismissRequest = { showHealthDevoteeDialog = false })
    }
    if (showMindfulEaterDialog) {
        MindfulEaterPersona(onDismissRequest = { showMindfulEaterDialog = false })
    }
    if (showWellnessStriverDialog) {
        WellnessStriverPersona(onDismissRequest = { showWellnessStriverDialog = false })
    }
    if (showBalanceSeekerDialog) {
        BalanceSeekerPersona(onDismissRequest = { showBalanceSeekerDialog = false })
    }
    if (showHealthProcrastinatorDialog) {
        HealthProcrastinatorPersona(onDismissRequest = { showHealthProcrastinatorDialog = false })
    }
    if (showFoodCarefreeDialog) {
        FoodCarefreePersona(onDismissRequest = { showFoodCarefreeDialog = false })
    }
}

/*
* MAIN function of that displays all UI of Questionnaire screen
* */
@Composable
fun IntakeQuestionnaire(
    modifier: Modifier = Modifier,
    viewModel: FoodIntakeViewModel = viewModel()
) {
    var context = LocalContext.current

    // List of food options from Screen 3
    val foodOptions = listOf(
        "Fruits", "Vegetables", "Grains",
        "Red Meat", "Seafood", "Poultry",
        "Fish", "Eggs", "Nuts/Seeds"
    )

    val selectedUserId = UserManager.userId

    val selectedFoodMap = remember { mutableStateMapOf<String, Boolean>() }

    // Track which foods are selected using a map
    val foodSelections = remember {
        mutableStateMapOf<String, Boolean>().apply {
            foodOptions.forEach { this[it] = false }
        }
    }

    // Mutable state variables to hold the values of user selected items
    var selectedPersona by remember { mutableStateOf("") }
    var biggestFoodIntake by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }

    // Observe latest saved intake
    val latestFoodIntake by viewModel.latestFoodIntake.collectAsState()

    // Load once when screen launches
    LaunchedEffect(Unit) {
        viewModel.loadSavedFoodIntake(selectedUserId)
    }

    // Populate UI when latestFoodIntake is loaded
    LaunchedEffect(latestFoodIntake) {
        latestFoodIntake?.let { intake ->
            foodSelections["Fruits"] = intake.fruits
            foodSelections["Vegetables"] = intake.vegetables
            foodSelections["Grains"] = intake.grains
            foodSelections["Red Meat"] = intake.redMeat
            foodSelections["Seafood"] = intake.seafood
            foodSelections["Poultry"] = intake.poultry
            foodSelections["Fish"] = intake.fish
            foodSelections["Eggs"] = intake.eggs
            foodSelections["Nuts/Seeds"] = intake.nutsSeeds
            selectedPersona = intake.persona
            biggestFoodIntake = intake.bigMealTime
            sleepTime = intake.sleepTime
            wakeTime = intake.wakeTime
        }
    }

    // List of personas from Screen 3
    val personas = listOf(
        "Health Devotee", "Mindful Eater", "Wellness Striver",
        "Balance Seeker", "Health Procrastinator", "Food Carefree"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((80.dp)))

            // Part 1: all foods the user can eat
            Text(
                text = "Tick all the food categories you can eat:",
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height((5.dp)))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                items(foodOptions) { food ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val isChecked = selectedFoodMap[food] ?: false
                        Checkbox(
                            checked = foodSelections[food] == true,
                            onCheckedChange = { isChecked ->
                                foodSelections[food] = isChecked
                            }
                        )
                        Text(
                            text = food,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height((8.dp)))

            // Portion 2: learn more about personas and user selects persona
            Text(
                text = "Your persona",
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "People can broadly be classified into 6 different types based " +
                        "on their eating preferences. Click on each button below to find out the " +
                        "different types, and select the type that best fits you!",
                textAlign = TextAlign.Left,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic
            )

            PersonaInfoButtons() // Calling function for "learn more" buttons

            Text(
                text = "Which persona best fits you?",
                fontWeight = FontWeight.Bold
            )

            // User selects persona by calling a function that has a dropdown selecting text field
            PersonaDropdownField(personas, selectedPersona) { newPersona ->
                selectedPersona = newPersona
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Part 3: user body clock timings (calls the TimeGrid function)
            TimeGrid(
                savedBigMealTime = biggestFoodIntake,
                savedSleepTime = sleepTime,
                savedWakeTime = wakeTime,
                onBigMealTimeChange = { biggestFoodIntake = it },
                onSleepTimeChange = { sleepTime = it },
                onWakeTimeChange = { wakeTime = it }
            )


            // Save all user preferences and goes to Screen 5
            Button(
                onClick = {
                    viewModel.saveFoodIntake(
                        patientId = selectedUserId ?: "",
                        selectedFoods = foodSelections,
                        persona = selectedPersona,
                        bigMealTime = biggestFoodIntake,
                        sleepTime = sleepTime,
                        wakeTime = wakeTime
                    )
                    context.startActivity(Intent(context, HomePage::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF311465),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SAVE")
            }
        }
    }
}

/*
* Function that creates a dropdown text field for the user to select their persona.
* Similar to the UserID implementation in the Login Screen.
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaDropdownField(personas: List<String>, selectedPersona: String, onSelectedChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selectedPersona.ifEmpty { "Select Persona" }) }

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
                    text = "Select Your Persona",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Black
            ),
            trailingIcon = {
                Icon(
                    imageVector =
                        if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Dropdown"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .menuAnchor(),
            shape = RoundedCornerShape(20.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            personas.forEach { persona ->
                DropdownMenuItem(
                    text = { Text(text = persona) },
                    onClick = {
                        selectedOption = persona
                        onSelectedChange(persona)  // Callback with the selected persona
                        expanded = false
                    }
                )
            }
        }
    }
}


/*
* Function that creates a 3x2 "grid" that displays the time question on the left, and time button on right.
* Calls TimeRow function to create each row that consists of the following mentioned above.
* Calls ShowTimePicker function for the button functionality so the user gets a time picker dialog.
* */
@Composable
fun TimeGrid(
    savedBigMealTime: String,
    savedSleepTime: String,
    savedWakeTime: String,
    onBigMealTimeChange: (String) -> Unit,
    onSleepTimeChange: (String) -> Unit,
    onWakeTimeChange: (String) -> Unit,
) {
    val context = LocalContext.current

    // Mutable states for selected times
    var bigMealTime by remember { mutableStateOf(savedBigMealTime) }
    var sleepTime by remember { mutableStateOf(savedSleepTime) }
    var wakeTime by remember { mutableStateOf(savedWakeTime) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimeRow("What time do you normally eat your biggest meal?", bigMealTime) {
            ShowTimePicker(context) {
                if (validateTimes(it, bigMealTime, sleepTime, context)) {
                    bigMealTime = it
                    onBigMealTimeChange(it)
                }
            }
        }
        TimeRow("What time do you usually sleep?", sleepTime) {
            ShowTimePicker(context) {
                if (validateTimes(it, bigMealTime, sleepTime, context)) {
                    sleepTime = it
                    onSleepTimeChange(it)
                }
            }
        }
        TimeRow("What time do you wake up?", wakeTime) {
            ShowTimePicker(context) {
                if (validateTimes(it, bigMealTime, sleepTime, context)) {
                    wakeTime = it
                    onWakeTimeChange(it)
                }
            }
        }
    }
}

/*
* Helping function that creates a row with the question, then button (for time picking)
* */
@Composable
fun TimeRow(question: String, time: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = question,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(2f)
        )
        Button(
            onClick = onClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = if (time.isEmpty()) "Select Time" else time,
                textAlign = TextAlign.Center)
        }
    }
}

/*
* Helping function that shows the time picker dialog for the TimeGrid
* */
fun ShowTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(selectedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}

/*
* Validates that wakeTime < bigMealTime < sleepTime
* */
fun validateTimes(wake: String, bigMeal: String, sleep: String, context: Context): Boolean {
    try {
        val wakeTime = LocalTime.parse(wake)
        val bigMealTime = LocalTime.parse(bigMeal)
        val sleepTime = LocalTime.parse(sleep)

        return if (wakeTime < bigMealTime && bigMealTime < sleepTime) {
            true
        } else {
            Toast.makeText(
                context,
                "Times must follow: Wake Time < Biggest Meal Time < Sleep Time",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    } catch (e: Exception) {
        // If any time is blank or invalid, we assume it's valid temporarily
        return true
    }
}
