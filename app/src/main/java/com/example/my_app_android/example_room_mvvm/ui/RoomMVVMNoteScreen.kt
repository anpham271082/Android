package com.example.my_app_android.example_room_mvvm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.my_app_android.example_room_mvvm.viewmodel.RoomMVVMNoteViewModel

@Composable
fun RoomMVVMNoteScreen(viewModel: RoomMVVMNoteViewModel = hiltViewModel()) {
    val notes = viewModel.notes.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") })
        Button(onClick = {
            viewModel.addNote(title, content)
            title = ""
            content = ""
        }) {
            Text("Add Note")
        }

        LazyColumn {
            items(notes.value) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { viewModel.removeNote(note) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(note.title, fontWeight = FontWeight.Bold)
                        Text(note.content)
                    }
                }
            }
        }
    }
}