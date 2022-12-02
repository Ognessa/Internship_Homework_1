package com.onix.internship.ui.chooseCountry

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

data class ChooseCountryModel(
    val taxResidenceCountry: ObservableField<String> = ObservableField(""),
    val citizenshipCountry: ObservableField<String> = ObservableField(""),
    val moveNext: ObservableBoolean = ObservableBoolean(false)
)