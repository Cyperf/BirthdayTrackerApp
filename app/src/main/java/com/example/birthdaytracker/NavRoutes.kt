package com.example.birthdaytracker

sealed class NavRoutes(val route: String) {
    data object LogIn : NavRoutes("login")
    data object Tracker : NavRoutes("tracker")
}