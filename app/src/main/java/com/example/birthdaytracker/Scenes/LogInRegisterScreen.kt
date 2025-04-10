package com.example.birthdaytracker.Scenes


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController


@Composable
fun LogIn(initialLogInState: Boolean, navController: NavHostController) {
    var logIn by remember { mutableStateOf(initialLogInState) }
    var email by remember { mutableStateOf(" ") }
    var password by remember { mutableStateOf(" ") }
    var confirmPassword by remember { mutableStateOf(" ") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if (logIn) {
                Text(text = "Log In", style = MaterialTheme.typography.headlineLarge)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter E-mail") }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter Password") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        navController.navigate("tracker")
                    }) { Text("Log In ") }
                    Spacer(modifier = Modifier.padding(28.dp))

                    Button(onClick = {
                        logIn = false
                    }) { Text("Register") }
                }
            } else {
                Text(text = "Register", style = MaterialTheme.typography.headlineLarge)
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter E-mail") }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter Password") }
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        //TODO
                        logIn = false
                    }) { Text("Register") }
                    Spacer(modifier = Modifier.padding(28.dp))

                    Button(onClick = {
                        logIn = true
                    }) { Text("Log In") }
                }
            }


        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//    BirthdayTrackerTheme {
//        LogIn(true, navController)
//    }
//}
//@Preview(showBackground = true, showSystemUi = true,
//    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
//@Composable
//fun WelcomeScreenPreviewLandscape() {
//    BirthdayTrackerTheme {
//        LogIn(true)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun WelcomeScreenPreview2() {
//    BirthdayTrackerTheme {
//        LogIn(false)
//
//    }
//}