package com.onix.internship.entity

import androidx.annotation.StringRes
import com.onix.internship.R

enum class SensorSubType(@StringRes val res : Int) {
    SWITCH(R.string.switch_subtype), ONETIME(R.string.onetime_subtype),
    LEVEL(R.string.level_subtype);

    fun getValue() : Int{
        return res
    }
}