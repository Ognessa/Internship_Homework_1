package com.onix.internship.objects

import android.content.Context
import androidx.room.Room
import com.onix.internship.data.database.AppDatabase

class DataStore(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "notes-database"
    ).build()

    fun gelAllData(): List<NotesData> {
        return db.notesDao().getAll()
    }

    fun getFilteredData(data : DataFilter, type : TypeFilter): List<NotesData> {
        return db.notesDao().getFilteredData(data, type)
    }

    fun getById(id : Int): NotesData {
        return db.notesDao().getById(id)
    }

    fun addNewNote(note : NotesData){
        db.notesDao().insert(note)
    }

    fun editNote(note : NotesData){
        db.notesDao().update(note)
    }

    fun clearDB(){
        db.notesDao().deleteAll()
    }

    fun delete(note : NotesData){
        db.notesDao().delete(note)
    }
}