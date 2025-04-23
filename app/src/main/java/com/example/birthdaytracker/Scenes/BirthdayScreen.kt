package com.example.birthdaytracker.Scenes

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.birthdaytracker.Models.Friend
import com.example.birthdaytracker.Models.FriendViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import java.util.Calendar

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
    var filterDialogue by remember { mutableStateOf(false) }



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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

            ) {
                Text("Sorting Method:")
                SortingMenu(viewModel, modifier.weight(1f))

                Button(
                    onClick = { filterDialogue = true }, modifier = Modifier
                        .wrapContentWidth()
                        .weight(1f)

                ) { Text("Filter Friends") }
            }
            LazyColumn {
                items(friends) { friend ->
                    FriendItem(
                        friend,
                        onDelete = onDelete
                    )
                }
            }
        }
        Text("I have " + friends.count() + " friends")
        if (filterDialogue) {
            FilterMenu(
                viewModel,
                onDismiss = { filterDialogue = false },
            )
        }
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
                            month = calendar.get(Calendar.MONTH) + 1
                            year = calendar.get(Calendar.YEAR)
                        }
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
                    viewModel.createFriend(
                        Friend(
                            id = 0,
                            name = name,
                            userId = user?.email.toString(),
                            birthYear = year,
                            birthMonth = month,
                            birthDayOfMonth = day,
                            pictureUrl = "",
                            remarks = remarks
                        )
                    )
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
                    if (day != 0) {
                        Text("Birthday is: " + day.toString() + "/" + month.toString() + "/" + year.toString())
                    } else {
                        Text("Pick birthday")
                    }
                }
            }
        },
    )
}

@Composable
fun SortingMenu(
    viewModel: FriendViewModel,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Unsorted", "Age", "Name", "Next birthday")
    var selectedText by remember { mutableStateOf(items[0]) }

    Box(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { expanded = true }) {
            Text(selectedText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        selectedText = label
                        expanded = false
                        viewModel.sortMyFriend(viewModel.user?.email.toString(), label)
                    })
            }
        }
    }
}

@Composable
fun FilterMenu(
    viewModel: FriendViewModel,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var ageBelow by remember { mutableStateOf("") }
    var ageAbove by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                viewModel.filterMyFriend(viewModel.user?.email.toString(),name,ageBelow.toIntOrNull(),ageAbove.toIntOrNull())
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Enter Details") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = ageBelow,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            ageBelow = input
                        }
                    },
                    label = { Text("Below Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = ageAbove,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            ageAbove = input
                        }
                    },
                    label = { Text("Above Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            }

        })
}
//@Preview(showBackground = true)
//@Composable
//fun BDayScreenPreview() {
//    BirthdayTrackerTheme() {
//        Tracker()
//    }
//
//}



