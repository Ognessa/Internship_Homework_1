package com.onix.internship.ui.home

import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.entity.local.PersonDataEntity
import com.onix.internship.network.useCase.GetPersonDataUseCase

class HomeViewModel(
    private val getPersonDataUseCase: GetPersonDataUseCase
) : BaseViewModel() {

    val model = HomeModel()

    fun searchPersonData() {
        launch {
            val result = getPersonDataUseCase.execute(model.name.get() ?: "")
            result.fold(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(entity: PersonDataEntity) {
        model.personDataEntity.set(entity)
    }

}