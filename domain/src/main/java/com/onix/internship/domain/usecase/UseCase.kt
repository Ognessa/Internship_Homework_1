package com.onix.internship.domain.usecase

interface UseCase<out Result> where Result : Any {
    open class Params
}