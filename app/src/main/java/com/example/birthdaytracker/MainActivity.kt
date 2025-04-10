package com.example.birthdaytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.birthdaytracker.Models.FriendViewModel
import com.example.birthdaytracker.Scenes.LogIn
import com.example.birthdaytracker.Scenes.Tracker
import com.example.birthdaytracker.ui.theme.BirthdayTrackerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayTrackerTheme {
                    Greeting()
                }
            }
        }
    }



@Composable
fun Greeting() {
    val navController = rememberNavController()
    val viewModel: FriendViewModel = viewModel()
    val friends = viewModel.friends.value

    NavHost(navController = navController, startDestination = NavRoutes.LogIn.route)
    {
        composable(NavRoutes.LogIn.route)
        {
            LogIn(true, navController)
        }
        composable(NavRoutes.Tracker.route)
        {
            Tracker(
                friends = friends,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun     GreetingPreview() {
    BirthdayTrackerTheme {
        Greeting()
    }
}