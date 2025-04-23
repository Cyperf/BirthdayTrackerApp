package com.example.birthdaytracker.Repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.birthdaytracker.Models.Friend
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

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
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            //.addConverterFactory(KotlinJsonAdapterFactory)
            //.addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi, added to Gradle dependencies
            .client(client)
            .build()
        birthDayTrackerService = build.create(BirthdayTrackerService::class.java)
        //getMyFriends("anbo@zealand.dk")
    }

    fun getAllFriends(){
        Log.d("Kagemand","GetAllFriends")
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

    fun getMyFriends(userId: String){
        Log.d("Slikkemand","GetMyFriends "+userId)
        birthDayTrackerService.getMyFriends(userId)?.enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    Log.d("Kagemanden", response.body().toString())
                    val friendsList: List<Friend>? = response.body()
                    Log.d("Kagemand",friendsList.toString())
                    friends.value = friendsList ?: emptyList()
                    errorMessage.value = ""

                } else {
                    Log.d("Kagemand","no success")
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("Kagemand", message)
                }
            }

            override fun onFailure(
                call: Call<List<Friend>?>,
                t: Throwable
            ) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun sortMyFriend(userId: String, sortBy: String){
        birthDayTrackerService.sortMyFriends(userId, sortBy)?.enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendsList: List<Friend>? = response.body()
                    friends.value = friendsList ?: emptyList()
                    errorMessage.value = ""

                } else {
                    Log.d("Kagemand","no success")
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("Kagemand", message)
                }
            }

            override fun onFailure(
                call: Call<List<Friend>?>,
                t: Throwable
            ) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun filterMyFriend(userId: String, nameContains: String, ageBelow: Int?, ageAbove: Int?){
        birthDayTrackerService.filterMyFriends(userId, nameContains,ageBelow,ageAbove)?.enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendsList: List<Friend>? = response.body()
                    friends.value = friendsList ?: emptyList()
                    errorMessage.value = ""

                } else {
                    Log.d("Kagemand","no success")
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("Kagemand", message)
                }
            }

            override fun onFailure(
                call: Call<List<Friend>?>,
                t: Throwable
            ) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.d("APPLE", message)
            }
        })
    }

    fun createFriend(friend: Friend){
        Log.d("Kagemand","Creating Friend: "+friend)
        birthDayTrackerService.createFriend(friend).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    Log.d("Kagemand", "Created: "+response.body().toString())
                    getMyFriends(friend.userId)
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message() + " " + response.body() + " " + friend
                    errorMessage.value = message
                    Log.d("Kagemanddød", message)
                }
            }

            override fun onFailure(
                call: Call<Friend?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteFriend(id: Int){
        Log.d("Kagemand","Deleting Friend: ")
        birthDayTrackerService.deleteFriend(id).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    Log.d("Kagemand", "Created: "+response.body().toString())
                    getMyFriends(friends.value.firstOrNull()?.userId ?: "")
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message() + " " + response.body() + " "
                    errorMessage.value = message
                    Log.d("Kagemanddød", message)
                }
            }

            override fun onFailure(
                call: Call<Friend?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}

