package com.onix.internship.ui.popup

import android.widget.PopupWindow

interface PopupClickListener {
    fun onItemClickListener(itemId: Int, popup: PopupWindow)
}