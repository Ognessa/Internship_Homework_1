package com.onix.internship.ui.popup

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.PopupWindow
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.children
import com.onix.internship.databinding.PopupWindowBinding

class PopupWindowCreator {

    fun showPopup(
        context: Context,
        view: View,
        parent: View,
        @MenuRes menuRes: Int,
        clickListener: PopupClickListener
    ) {
        val inflater = LayoutInflater.from(context)
        val binding = PopupWindowBinding.inflate(inflater, null, false)
        val popupView: View = binding.root

        val mMenu = MenuBuilder(context)
        MenuInflater(context).inflate(menuRes, mMenu)
        val list: List<MenuItem> = mMenu.children.toList()

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        val adapter = PopupAdapter(
            context = context,
            lastItem = list.last(),
            clickListener = clickListener,
            popupWindow = popupWindow
        )

        with(binding.optionsList) {
            this.adapter = adapter
            adapter.submitList(list)
        }

        popupWindow.animationStyle = ListPopupWindow(context).animationStyle

        val popupX = calculatePopupX(view, parent, popupView.width)
        val popupY = calculatePopupY(view, parent, popupView.height)

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, popupX, popupY)
    }

    private fun locateView(v: View): Rect {
        val loc_int = IntArray(2)

        v.getLocationOnScreen(loc_int)

        val location = Rect()
        location.left = loc_int[0]
        location.top = loc_int[1]
        location.right = loc_int[0] + v.width
        location.bottom = loc_int[1] + v.height
        Log.d(
            "DEBUG", "(${location.left}; ${location.top})\n" +
                    "(${location.right}; ${location.bottom})"
        )
        return location
    }

    private fun calculatePopupX(view: View, parent: View, popupWidth: Int): Int {
        val location = locateView(view)
        val windowParameters = locateView(parent)

        return if (location.left > windowParameters.left) location.left
        else windowParameters.left
    }

    private fun calculatePopupY(view: View, parent: View, popupHeight: Int): Int {
        val location = locateView(view)
        val windowParameters = locateView(parent)

        return if (location.top > windowParameters.top) location.top
        else windowParameters.top
    }

}