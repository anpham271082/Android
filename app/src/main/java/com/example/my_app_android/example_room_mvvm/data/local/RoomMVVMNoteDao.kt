package com.example.my_app_android.example_room_mvvm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomMVVMNoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<RoomMVVMNoteModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: RoomMVVMNoteModel)

    @Delete
    suspend fun deleteNote(note: RoomMVVMNoteModel)
}