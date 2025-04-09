package com.example.birthdaytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdaytracker.Models.FriendViewModel
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
fun Greeting(viewModel: FriendViewModel = viewModel(), modifier: Modifier) {
    val navController = rememberNavController()

    NavHost()
}

@Preview(showBackground = true)
@Composable
fun     GreetingPreview() {
    BirthdayTrackerTheme {
        Greeting()
    }
}