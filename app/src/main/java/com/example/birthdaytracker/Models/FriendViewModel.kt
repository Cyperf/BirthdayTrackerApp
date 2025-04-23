package com.example.birthdaytracker.Models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.birthdaytracker.Repository.FriendsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

public class FriendViewModel : ViewModel() {
    private val repository = FriendsRepository()
    val friends: State<List<Friend>> = repository.friends
    private val auth = FirebaseAuth.getInstance()
    var user: FirebaseUser? by mutableStateOf(auth.currentUser)
    var message by mutableStateOf("")

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun getAllFriends() {
        repository.getAllFriends()
    }

    fun getMyFriends(userId: String) {
        repository.getMyFriends(userId)
    }

    fun createFriend(friend: Friend) {
        repository.createFriend(friend)
    }

    fun deleteFriend(id: Int) {
        repository.deleteFriend(id)
    }

    fun sortMyFriend(userId: String, sortBy: String) {
        repository.sortMyFriend(userId, sortBy)
    }

    fun filterMyFriend(userId: String, nameContains: String, ageBelow: Int?, ageAbove: Int?) {
        repository.filterMyFriend(userId, nameContains,ageBelow,ageAbove)
    }

    fun signIn(email: String, password: String, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ajaja","success")
                    user = auth.currentUser
                    Log.d("ajaja",user.toString())
                } else {
                    message = task.exception?.message ?: "Unknown error"
                    Log.d("ajaja",message)
                    user = null
                    onError(message)
                }
                onError(message)
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ajaja","successful register")

                    user = auth.currentUser
                } else {
                    message = task.exception?.message ?: "Unknown error"
                    user = null
                    Log.d("ajaja",message)
                }
            }
    }
    fun signOut() {
        Log.d("sign out",user.toString()+" signed out?")
        user = null
        auth.signOut()
    }
}


