package com.onix.internship.ui.home

import androidx.databinding.ObservableField
import com.onix.internship.entity.local.PersonDataEntity

data class HomeModel(
    val name: ObservableField<String> = ObservableField(),
    val personDataEntity: ObservableField<PersonDataEntity> = ObservableField(PersonDataEntity())
)