package com.example.birthdaytracker.Models

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.birthdaytracker.Repository.FriendsRepository

class FriendViewModel: ViewModel() {
    //private val repository = FriendsRepository()

   // fun getMyFriends(){
        //repository.getMyFriends()
    //}

    private val _currentScreen = mutableStateOf("LogInRegisterScreen")
    val currentScreen: State<String> get() = _currentScreen

    fun navigateTo(screen: String){
        _currentScreen.value = screen
    }
}