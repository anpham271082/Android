package com.example.my_app_android.example_room_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_android.example_room_mvvm.data.local.RoomMVVMNoteModel
import com.example.my_app_android.example_room_mvvm.data.repository.RoomMVVMNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RoomMVVMNoteViewModel @Inject constructor(
    private val repository: RoomMVVMNoteRepository
) : ViewModel() {

    val notes = repository.notes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(RoomMVVMNoteModel(title = title, content = content))
        }
    }

    fun removeNote(note: RoomMVVMNoteModel) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}