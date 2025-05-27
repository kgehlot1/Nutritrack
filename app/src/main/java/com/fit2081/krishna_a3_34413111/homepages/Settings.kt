package com.fit2081.krishna_a3_34413111.homepages

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fit2081.krishna_a3_34413111.LoginScreen
import com.fit2081.krishna_a3_34413111.ClinicianLogin
import com.fit2081.krishna_a3_34413111.UserManager
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.readPatientsFromCSV

/*
* Settings screen that displays the user information and allows a clinician to sign in if needed.
* User can also logout here.
* */
@Composable
fun Settings(modifier: Modifier = Modifier, navController: NavController, viewModel: PatientViewModel) {
    val selectedUserId = UserManager.userId

    LaunchedEffect(selectedUserId) {
        viewModel.fetchPatientName(selectedUserId)
        viewModel.fetchPatientPhoneNo(selectedUserId)
    }

    val patientName by viewModel.patientName  // get patient name from database
    val patientPhoneNo by viewModel.patientPhoneNo
    val context = LocalContext.current

    // ui here
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Account",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(15.dp))

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
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Patient's name",
                        tint = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = patientName.toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Patient's phone number",
                        tint = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = patientPhoneNo.toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Patient's ID",
                        tint = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = selectedUserId
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Other Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Gray
            )

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
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Logout")
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    IconButton(
                        onClick = {
                            UserManager.clearUser()
                            context.startActivity(Intent(context, LoginScreen::class.java)) },
                        modifier = Modifier.padding(horizontal = 15.dp)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Go back to login screen",
                            tint = Color.DarkGray
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
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Clinician login",
                        tint = Color.Black
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Clinician Login")
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    IconButton(
                        onClick = { context.startActivity(Intent(context, ClinicianLogin::class.java)) },
                        modifier = Modifier.padding(horizontal = 15.dp)) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Go to clinician screen",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}