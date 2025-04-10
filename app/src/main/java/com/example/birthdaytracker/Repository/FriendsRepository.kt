package com.example.birthdaytracker.Repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.birthdaytracker.Models.Friend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FriendsRepository {
    private val baseUrl = "https://birthdaysrest.azurewebsites.net/api/"

    private val birthDayTrackerService: BirthdayTrackerService
    val friends: MutableState<List<Friend>> = mutableStateOf(listOf())

    val errorMessage = mutableStateOf("")


    init {
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            //.addConverterFactory(KotlinJsonAdapterFactory)
            //.addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi, added to Gradle dependencies
            .build()
        birthDayTrackerService = build.create(BirthdayTrackerService::class.java)
        getMyFriends()
    }

    fun getMyFriends(){
        Log.d("Kagemand","GetMyFriends")
        birthDayTrackerService.getAllFriends().enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    //Log.d("APPLE", response.body().toString())
                    val friendsList: List<Friend>? = response.body()
                    Log.d("Kagemand",friendsList.toString())
                    friends.value = friendsList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("Kagemand", message)
                }
            }

            override fun onFailure(
                call: Call<List<Friend>?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}

