package com.onix.internship.data.database

import androidx.room.*
import com.onix.internship.objects.NotesData

@Dao
interface NotesDao {
    @Query("SELECT * FROM notesData")
    fun getAll(): List<NotesData>

    @Query("SELECT * FROM notesData WHERE id = :id")
    fun getById(id : Int) : NotesData

    @Insert
    fun insert(notes: NotesData)

    @Update
    fun update(notes : NotesData)

    @Query("DELETE FROM notesData")
    fun deleteAll()

    @Query("DELETE FROM sqlite_sequence")
    fun deleteKey()

    @Delete
    fun delete(notesData: NotesData)
}