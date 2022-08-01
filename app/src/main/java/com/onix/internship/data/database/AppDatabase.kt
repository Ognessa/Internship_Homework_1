package com.onix.internship.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onix.internship.objects.NotesData

@Database(entities = [NotesData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao() : NotesDao
}