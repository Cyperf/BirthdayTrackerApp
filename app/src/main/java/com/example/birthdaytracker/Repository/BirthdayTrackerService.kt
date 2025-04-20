package com.example.birthdaytracker.Repository

import com.example.birthdaytracker.Models.Friend
import retrofit2.Call

import retrofit2.http.*

interface BirthdayTrackerService {

    @GET("persons")
    fun getAllFriends(): Call<List<Friend>>

    @GET("persons")
    fun getMyFriends(@Query("user_id") userId: String): Call<List<Friend>>?

    @POST("persons")
    fun createFriend(@Body friend: Friend): Call<Friend>

    @DELETE("persons/{id}")
    fun deleteFriend(@Path("id") id: Int): Call<Friend>

}