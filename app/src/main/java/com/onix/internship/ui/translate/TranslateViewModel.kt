package com.onix.internship.ui.translate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.HistoryItem
import com.onix.internship.parser.DictionaryXmlParser

class TranslateViewModel(dictionaryXmlParser: DictionaryXmlParser) : BaseViewModel() {

    private val HISTORY_SIZE = 16
    private val dictionaries = dictionaryXmlParser.dictionary

    private val _history = MutableLiveData<ArrayList<HistoryItem>>(arrayListOf())
    val history : LiveData<ArrayList<HistoryItem>> get() = _history

    private val _currentDict = MutableLiveData(dictionaries.first().title)
    val currentDict : LiveData<String> get() = _currentDict

    fun updateHistory(item : HistoryItem){
        val array = _history.value
        if(array != null){
            var hasSameItem = false
            var index = 0
            array.forEach {
                if(it.isSameHistoryItems(item)){
                    hasSameItem = true
                    index = array.indexOf(it)
                }
            }
            if(hasSameItem){
                array.removeAt(index)
                array.add(item)
            }
            else{
                if(array.size == HISTORY_SIZE){
                    array.removeFirst()
                    array.add(item)
                }
                else array.add(item)
            }
        }
        else _history.postValue(arrayListOf(item))
    }

    fun changeDict(title: String){
        _currentDict.postValue(title)
    }

    fun getDictsTitles(): ArrayList<String> {
        val list = arrayListOf<String>()
        dictionaries.forEach { list.add(it.title) }
        return list
    }

    fun findWord(title : String, key : String) : Array<String> {
        var resultList : Array<String> = arrayOf()

        dictionaries.forEach {
            if (it.title == title)
                resultList = it.findWord(key).toTypedArray()
        }

        return resultList
    }
}