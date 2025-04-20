package com.example.birthdaytracker.Scenes

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.birthdaytracker.Models.Friend
import com.example.birthdaytracker.Models.FriendViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseUser
import java.text.DateFormat
import java.util.Calendar
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tracker(
    modifier: Modifier = Modifier,
    friends: List<Friend>,
    viewModel: FriendViewModel,
    onDelete: (Int) -> Unit,
    goBack: () -> Unit,
    user: FirebaseUser?
) {

    var showDialogue by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("Hello User: " + viewModel.user?.email) },
                actions = {
                    Button(
                        onClick = {
                            viewModel.signOut()
                            goBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    )
                    {
                        Text("Sign Out")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Text("Add new friend:")
                    IconButton(onClick = {
                        showDialogue = true
                        Log.d("Dialogue", showDialogue.toString())
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add a new friend"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            Log.d("Kagemand", friends.toString())
            LazyColumn {
                items(friends) { friend ->
                    FriendItem(
                        friend,
                        onDelete = onDelete
                    )
                }
            }
            Text("I have " + friends.count() + " friends")

            Button(onClick = {
                viewModel.createFriend(
                    Friend(
                        id = 0,
                        userId = "jaco",
                        name = "string",
                        birthYear = 1,
                        birthMonth = 1,
                        birthDayOfMonth = 1,
                        remarks = "string",
                        pictureUrl = "string"
                    )
                )
            }) { Text("Create new Friend to track") }
            if (showDialogue == true)
                AddFriendDialog(
                    onDismiss = { showDialogue = false },
                    onConfirm = { name ->
                        showDialogue = false
                    },
                    user = user,
                    viewModel = viewModel
                )
        }
    }
}


@Composable
fun FriendItem(friend: Friend, onDelete: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    )
    {
        Row() {
            Text(text = "Name: ${friend.name} ")
            Text(text = "Day: ${friend.birthDayOfMonth} ")
            Text(text = "Month: ${friend.birthMonth} ")
            Text(text = "Year: ${friend.birthYear}")
            IconButton(
                onClick = { onDelete(friend.id) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Red,
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete a friend"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    user: FirebaseUser?,
    viewModel: FriendViewModel
) {
    var name by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    var showDateDialogue by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    var day by remember { mutableStateOf(0) }
    var month by remember { mutableStateOf(0) }
    var year by remember { mutableStateOf(0) }

    if (showDateDialogue) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { /* no reaction */ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDateDialogue = false
                        selectedDate = datePickerState.selectedDateMillis

                        selectedDate?.let {
                            val calendar = Calendar.getInstance().apply {
                                timeInMillis = it
                            }
                            day = calendar.get(Calendar.DAY_OF_MONTH)
                            month = calendar.get(Calendar.MONTH)+1
                            year = calendar.get(Calendar.YEAR)}
                    },
                )
                { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDateDialogue = false })
                { Text("Cancel") }
            },
        ) {
            DatePicker(state = datePickerState)
        }

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(name)
                    viewModel.createFriend(Friend(
                        id = 0,
                        name = name,
                        userId = user?.email.toString(),
                        birthYear = year,
                        birthMonth = month,
                        birthDayOfMonth = day,
                        pictureUrl = "",
                        remarks = remarks
                    ))
                    onDismiss()
                },
                enabled = name.isNotBlank() && day != 0,
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text("Add New Friend")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Remarks") },
                    singleLine = true,
                )
                Button(onClick = {
                    showDateDialogue = true
                }) {
                    if(day!=0){
                        Text("Birthday is: "+day.toString()+"/"+month.toString()+"/"+year.toString())
                    } else {
                    Text("Pick birthday")}
                }
            }
        },
    )
}
//@Preview(showBackground = true)
//@Composable
//fun BDayScreenPreview() {
//    BirthdayTrackerTheme() {
//        Tracker()
//    }
//
//}



