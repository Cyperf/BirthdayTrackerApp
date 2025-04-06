package com.example.birthdaytracker.Models

import androidx.lifecycle.ViewModel
import com.example.birthdaytracker.Repository.FriendsRepository

class FriendViewModel: ViewModel() {
    private val repository = FriendsRepository()

    fun getMyFriends(){
        repository.getMyFriends()
    }

}