package com.fit2081.krishna_a3_34413111

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/*
This page is made for the alert dialogs of each persona as as helping functions for Part 2 of the questionnaire
*/

@Composable
fun HealthDevoteePersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.health_devotee),
                contentDescription = "Health Devotee",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Health Devotee")
        },
        text = {
            Text(text = "I’m passionate about healthy eating & health plays a big part in my life. " +
                    "I use social media to follow active lifestyle personalities or get new " +
                    "recipes/exercise ideas. I may even buy superfoods or follow a particular " +
                    "type of diet. I like to think I am super healthy.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun MindfulEaterPersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.mindful_eater),
                contentDescription = "Mindful Eater",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Mindful Eater")
        },
        text = {
            Text(text = "I’m health-conscious and being healthy and eating healthy is " +
                    "important to me. Although health means different things to different " +
                    "people, I make conscious lifestyle decisions about eating based on what " +
                    "I believe healthy means. I look for new recipes and healthy eating information " +
                    "on social media."
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun WellnessStriverPersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.wellness_striver),
                contentDescription = "Wellness Striver",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Wellness Striver")
        },
        text = {
            Text(text = "I aspire to be healthy (but struggle sometimes). Healthy eating is " +
                    "hard work! I’ve tried to improve my diet, but always find things that make " +
                    "it difficult to stick with the changes. Sometimes I notice recipe ideas or " +
                    "healthy eating hacks, and if it seems easy enough, I’ll give it a go.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun BalanceSeekerPersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.balance_seeker),
                contentDescription = "Balance Seeker",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Balance Seeker")
        },
        text = {
            Text(text = "I try and live a balanced lifestyle, and I think that all foods are " +
                    "okay in moderation. I shouldn’t have to feel guilty about eating a piece " +
                    "of cake now and again. I get all sorts of inspiration from social media " +
                    "like finding out about new restaurants, fun recipes and sometimes healthy " +
                    "eating tips.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun HealthProcrastinatorPersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.health_procrastinator),
                contentDescription = "Health Procrastinator",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Health Procrastinator")
        },
        text = {
            Text(text = "I’m contemplating healthy eating but it’s not a priority for me right now. " +
                    "I know the basics about what it means to be healthy, but it doesn’t seem " +
                    "relevant to me right now. I have taken a few steps to be healthier but " +
                    "I am not motivated to make it a high priority because I have too many " +
                    "other things going on in my life.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}

@Composable
fun FoodCarefreePersona(onDismissRequest: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.food_carefree),
                contentDescription = "Food Carefree",
                modifier = Modifier.size(150.dp)
            )
        },
        title = {
            Text(text = "Food Carefree")
        },
        text = {
            Text(text = "\tI’m not bothered about healthy eating. I don’t really see the point " +
                    "and I don’t think about it. I don’t really notice healthy eating tips or " +
                    "recipes and I don’t care what I eat.")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF311465))
            ) {
                Text(
                    "Close",
                    color = Color.White
                )
            }
        }
    )
}
