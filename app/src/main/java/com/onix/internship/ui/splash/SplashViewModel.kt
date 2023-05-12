package com.onix.internship.ui.splash

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.onix.internship.arch.controller.BaseViewModel

class SplashViewModel : BaseViewModel(), ScrollPresenter {

    private val _images = MutableLiveData<List<String>>()
    val images: LiveData<List<String>> = _images

    val selectedItem: ObservableField<String> = ObservableField("")

    private var list: List<String> = emptyList()

    init {
        list = listOf(
            "https://fastly.picsum.photos/id/866/180/140.jpg?hmac=tGz11gF07f2AAS-7MdRMtsPYl-hXHCyPN-xMtjpKt9Q",
            "https://fastly.picsum.photos/id/59/200/300.jpg?grayscale&hmac=-jYkM8w4f-kdpisRxY0TaJx2UX1wgS6mEIIlDkUwxdo",
            "https://fastly.picsum.photos/id/102/200/300.jpg?blur=5&hmac=68_i1IgxVNxxE10O3vfx9cVnrorS0z6L-8J32fUjyzk",
            "https://fastly.picsum.photos/id/454/200/300.jpg?blur=2&hmac=Q5nWnZiQWfNO0otlKUaK5TKMb-hnBTYtnO1UH-b6dVs",
            "https://fastly.picsum.photos/id/997/200/300.jpg?hmac=NeXq5MvhpKvGEq_X3jULp2C3Lg-8IQK8bdtnyJeXDIQ"
        )
        selectedItem.set(list[0])
        _images.value = list
    }

    override fun onItemSelected(position: Int) {
        selectedItem.set(list[position])
    }

}