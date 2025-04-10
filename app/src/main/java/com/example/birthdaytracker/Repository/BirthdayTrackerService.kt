package com.example.birthdaytracker.Repository

import com.example.birthdaytracker.Models.Friend


import retrofit2.Call

import retrofit2.http.*

interface BirthdayTrackerService {

    @GET("persons")
    fun getMyFriends(): Call<List<Friend>>


}