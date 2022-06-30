package com.onix.internship.ui.translate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.parser.DictionaryXmlParser

class TranslateViewModel(val dictionaryXmlParser: DictionaryXmlParser) : BaseViewModel() {

    private val _history = MutableLiveData<ArrayList<String>>(arrayListOf())
    val history : LiveData<ArrayList<String>> get() = _history
    val HISTORY_SIZE = 16

    fun updateHistory(key: String, value : ArrayList<String>){
        val string = "$key - ${value[0]}"
        val array = _history.value
        if(array != null){
            if(array.contains(string)){
                array.remove(string)
                array.add(string)
            }
            else if(array.size == HISTORY_SIZE){
                array.removeFirst()
                array.add(string)
            }
            else array.add(string)
        }
        else _history.postValue(arrayListOf(string))
    }

    fun findWord(dictionary : String, key : String) : ArrayList<String>?{
        return dictionaryXmlParser.getValue(dictionary, key)
    }

    fun arrayListToString(array : ArrayList<String>) : String{
        var string = ""
        array.forEach {
            string += "$it\n"
        }
        return string
    }
}