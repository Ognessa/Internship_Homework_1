package com.onix.internship.ui.advancedSearch

import androidx.databinding.ObservableField

class AdvancedSearchModel {
    val gen = ObservableField("")
    val ssp = ObservableField("")
    val also = ObservableField("")
    val type = ObservableField("")
    val loc = ObservableField("")
    val cnt = ObservableField("")
    val rmk = ObservableField("")
    val rec = ObservableField("")

    fun createQuery() : String{
        var query = ""

        gen.get()?.let { if(it.isNotEmpty()) query += "gen:\"$it\"" }
        ssp.get()?.let { if(it.isNotEmpty()) query += "ssp:\"$it\"" }
        also.get()?.let { if(it.isNotEmpty()) query += "also:\"$it\"" }
        type.get()?.let { if(it.isNotEmpty()) query += "type:\"$it\"" }
        loc.get()?.let { if(it.isNotEmpty()) query += "loc:\"$it\"" }
        cnt.get()?.let{ if(it != "CHOOSE") query += "cnt:\"${cnt.get()}\"" }
        rmk.get()?.let { if(it.isNotEmpty()) query += "rmk:\"$it\"" }
        rec.get()?.let { if(it.isNotEmpty()) query += "rec:\"$it\"" }

        return query
    }
}