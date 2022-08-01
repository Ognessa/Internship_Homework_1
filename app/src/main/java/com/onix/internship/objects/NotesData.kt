package com.onix.internship.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesData (
    @PrimaryKey (autoGenerate = true) val id : Int,
    @ColumnInfo (name = "title") var title : String,
    @ColumnInfo (name = "description") var description: String,
    @ColumnInfo (name = "color") var color : NotesColors,
    @ColumnInfo (name = "is_editable") var isEditable : Boolean = true,
    @ColumnInfo (name = "note_state") var notesState: Boolean = false
)