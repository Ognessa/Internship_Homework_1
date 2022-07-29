package com.onix.internship.data.database

import androidx.room.*
import com.onix.internship.objects.DataFilter
import com.onix.internship.objects.NotesData
import com.onix.internship.objects.TypeFilter

@Dao
interface NotesDao {
    @Query("SELECT * FROM notesData")
    fun getAll() : List<NotesData>

    @Query("SELECT * FROM notesData ORDER BY " +
            "CASE WHEN :type = 'ASC' THEN :data END ASC, " +
            "CASE WHEN :type = 'DESC' THEN :data END DESC")
    fun getFilteredData(data : DataFilter, type : TypeFilter) : List<NotesData>

    @Query("SELECT * FROM notesData WHERE id = :id")
    fun getById(id : Int) : NotesData

    @Insert
    fun insert(notes : NotesData)

    @Update
    fun update(notes : NotesData)

    @Query("DELETE FROM notesData")
    fun deleteAll()

    @Delete
    fun delete(notesData : NotesData)
}