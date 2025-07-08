package com.example.my_app_android.example_room_mvvm.data.repository

import com.example.my_app_android.example_room_mvvm.data.local.RoomMVVMNoteDao
import com.example.my_app_android.example_room_mvvm.data.local.RoomMVVMNoteModel
import jakarta.inject.Inject


class RoomMVVMNoteRepository @Inject constructor(private val dao: RoomMVVMNoteDao) {
    val notes = dao.getAllNotes()

    suspend fun insert(note: RoomMVVMNoteModel) = dao.insertNote(note)
    suspend fun delete(note: RoomMVVMNoteModel) = dao.deleteNote(note)
}