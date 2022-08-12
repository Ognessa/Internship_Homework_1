package com.onix.internship.ui.crop

import androidx.databinding.ObservableField
import com.onix.internship.arch.lifecycle.SingleLiveEvent

class CropModel {
    val ratioEvent = ObservableField(Pair(1, 1))

    val cropEvent = SingleLiveEvent<Unit>()
    val saveAndNavigateEvent = SingleLiveEvent<Unit>()
    val restoreEvent = SingleLiveEvent<Unit>()
}