package com.onix.internship.domain.entities.failure

sealed class ServerError : Failure.FeatureFailure() {
    object ServerCommon : ServerError()
}