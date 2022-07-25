package com.onix.internship.ui.locationPermission

import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage

class LocationPermissionViewModel(private val pref : PreferenceStorage) : BaseViewModel(){

    fun checkPreferencesKeys() : Boolean{
        return pref.check(Keys().levelKey) && pref.check(Keys().classKey)
    }

}
