package com.onix.internship.ui.askClass

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage

class AskClassViewModel(private val pref : PreferenceStorage) : BaseViewModel(){
    fun saveClassToPref(classId : Int){
        pref.save(Keys().classKey, classId.toString())
    }
}