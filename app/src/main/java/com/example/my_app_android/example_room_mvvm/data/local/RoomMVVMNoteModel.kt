package com.example.my_app_android.example_room_mvvm.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class RoomMVVMNoteModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)