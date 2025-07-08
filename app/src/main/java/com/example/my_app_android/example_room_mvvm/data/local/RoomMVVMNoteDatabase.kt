package com.example.my_app_android.example_room_mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomMVVMNoteModel::class],
    version = 1)
abstract class RoomMVVMNoteDatabase : RoomDatabase() {
    abstract fun noteDao(): RoomMVVMNoteDao
}