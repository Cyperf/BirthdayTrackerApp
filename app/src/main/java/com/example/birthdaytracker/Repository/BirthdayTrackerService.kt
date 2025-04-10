package com.example.birthdaytracker.Repository

import com.example.birthdaytracker.Models.Friend


import retrofit2.Call

import retrofit2.http.*

interface BirthdayTrackerService {

    @GET("persons")
    fun getAllFriends(): Call<List<Friend>>

    @GET("persons?user_id={userId}")
    fun getMyFriends(): Call<List<Friend>>
}