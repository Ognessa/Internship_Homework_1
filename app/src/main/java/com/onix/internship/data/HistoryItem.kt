package com.onix.internship.data

class HistoryItem(
    val dictTitle : String,
    val key : String,
    val value : String){

    fun isSameHistoryItems(item : HistoryItem) : Boolean{
        return (item.key == key && item.value == value) || (item.key == value && item.value == key)
    }
}