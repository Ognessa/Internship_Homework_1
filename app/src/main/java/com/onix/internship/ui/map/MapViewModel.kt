package com.onix.internship.ui.map

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.onix.internship.arch.BaseViewModel
import com.onix.internship.data.keys.Keys
import com.onix.internship.data.repository.PreferenceStorage
import com.onix.internship.objects.Point
import com.onix.internship.objects.PointsStore
import java.text.SimpleDateFormat
import java.util.*

class MapViewModel(store : PointsStore, private val sharedPref : PreferenceStorage) : BaseViewModel(){
    val pointList = store.pointsList

    fun createUserPoint(location: Location) : Point {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val timeFormat = SimpleDateFormat("hh:mm")
        val calendar = Calendar.getInstance()

        val date = dateFormat.format(calendar.time)
        val time = timeFormat.format(calendar.time)

        return Point(LatLng(location.latitude, location.longitude), date, time,
            sharedPref.getString(Keys().classKey).toString().toInt(), sharedPref.getString(Keys().levelKey).toString().toInt())
    }
}