package com.example.my_app_android.example_room_mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.my_app_android.example_room_mvvm.data.local.RoomMVVMNoteDao
import com.example.my_app_android.example_room_mvvm.data.local.RoomMVVMNoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomMVVMAppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomMVVMNoteDatabase {
        return Room.databaseBuilder(context, RoomMVVMNoteDatabase::class.java, "notes_db").build()
    }

    @Provides
    @Singleton
    fun provideDao(db: RoomMVVMNoteDatabase): RoomMVVMNoteDao = db.noteDao()
}