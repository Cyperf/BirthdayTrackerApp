package com.example.birthdaytracker.Models

import android.icu.text.DateFormat
import java.util.Date

data class Friend(val id: Int, val name: String, val userId: String, val birthYear: Int, val birthMonth: Int, val birthDayOfMonth: Int, val pictureUrl: String, val remarks: String){

}