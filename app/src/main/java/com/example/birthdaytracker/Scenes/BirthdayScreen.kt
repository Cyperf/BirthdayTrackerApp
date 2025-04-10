package com.example.birthdaytracker.Scenes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.birthdaytracker.Models.Friend
import com.example.birthdaytracker.Models.FriendViewModel
import com.example.birthdaytracker.ui.theme.BirthdayTrackerTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp

private val viewModel  = FriendViewModel()
@Composable
fun Tracker(modifier: Modifier = Modifier, friends: List<Friend>) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {

            FriendList(friends)
                    Text("I have " + friends.count() + " friends")
                }
            }
        }

        @Composable
        fun FriendList(friends: List<Friend>) {
            LazyColumn {
                items(friends) {friend ->
                    FriendItem(friend)
                }
            }
        }

        @Composable
        fun FriendItem(friend: Friend) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            )
            {
                Row() {
                Text(text = "Name: ${friend.name} ")
                Text(text = "Day: ${friend.birtDayOfTheMonth} ")
                Text(text = "Month: ${friend.birthMonth} ")
                Text(text = "Year: ${friend.birthYear}")
                }
            }
        }



//@Preview(showBackground = true)
//@Composable
//fun BDayScreenPreview() {
//    BirthdayTrackerTheme() {
//        Tracker()
//    }
//
//}



