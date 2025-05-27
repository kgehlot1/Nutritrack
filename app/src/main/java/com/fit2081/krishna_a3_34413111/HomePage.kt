package com.fit2081.krishna_a3_34413111

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fit2081.krishna_a3_34413111.ai.GenAIViewModel
import com.fit2081.krishna_a3_34413111.data.FoodIntakeViewModel
import com.fit2081.krishna_a3_34413111.data.PatientViewModel
import com.fit2081.krishna_a3_34413111.fruitData.FruitViewModel
import com.fit2081.krishna_a3_34413111.homepages.*
import com.fit2081.krishna_a3_34413111.ui.theme.Krishna_A3_34413111Theme

/*
* This page is made to allow a bottom navigation bar and for the implementation of navcontroller/navhost
* to move from one page to another (i.e. from Home to Insights and back)
* */
class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Krishna_A3_34413111Theme {
                val navController = rememberNavController()  // Create navcontroller

                // List made of the icons/pages the bottom navigation bar has (made using a data class)
                val navItemList = listOf(
                    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
                    NavItem("Insights", Icons.Filled.Info, Icons.Outlined.Info),
                    NavItem("NutriCoach", Icons.Filled.Face, Icons.Outlined.Face),
                    NavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
                )

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = navItemList, // List of pages/icons
                            navController = navController // Pass navcontroller
                        )
                    },
                    content = { innerPadding ->
                        // NavHost will handle the navigation between different screens
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                val context = LocalContext.current
                                val patientViewModel: PatientViewModel = viewModel(
                                    factory = PatientViewModel.PatientViewModelFactory(context))
                                Home(
                                    modifier = Modifier.padding(innerPadding),
                                    navController = navController,
                                    viewModel = patientViewModel
                                )
                            }
                            composable("insights") {
                                val context = LocalContext.current
                                val patientViewModel: PatientViewModel = viewModel(
                                    factory = PatientViewModel.PatientViewModelFactory(context))
                                Insights(
                                    modifier = Modifier.padding(innerPadding),
                                    navController = navController,
                                    viewModel = patientViewModel
                                )
                            }
                            composable("nutricoach") {
                                val fruitViewModel: FruitViewModel = viewModel()
                                val aiViewModel: GenAIViewModel = viewModel()
                                NutriCoach(
                                    modifier = Modifier.padding(innerPadding),
                                    navController = navController,
                                    fruitViewModel = fruitViewModel,
                                    aiViewModel = aiViewModel
                                )
                            }
                            composable("settings") {
                                val context = LocalContext.current
                                val patientViewModel: PatientViewModel = viewModel(
                                    factory = PatientViewModel.PatientViewModelFactory(context))
                                Settings(
                                modifier = Modifier.padding(innerPadding),
                                    navController = navController,
                                    viewModel = patientViewModel
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

/*
* Function that handles moving from one screen to another using the navigation bar
* */
@Composable
fun BottomNavigationBar(
    items: List<NavItem>,
    navController: NavController
) {
    NavigationBar(
        modifier = Modifier.height(80.dp)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = navController.currentDestination?.route == item.title.toLowerCase(),
                onClick = {
                    navController.navigate(item.title.toLowerCase()) // Navigate using the title
                },
                icon = {
                    Icon(
                        imageVector = if (navController.currentDestination?.route == item.title.toLowerCase()) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                alwaysShowLabel = true
            )
        }
    }
}

/*
* Data class for each page
* */
data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
