package com.example.birthdaytracker.Models

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.example.birthdaytracker.Repository.FriendsRepository

public class FriendViewModel: ViewModel() {
    private val repository = FriendsRepository()
    val friends: State<List<Friend>> = repository.friends


   fun getAllFriends(){
        repository.getAllFriends()
    }

    fun getMyFriends(userId: String){
        repository.getMyFriends(userId)
    }
}