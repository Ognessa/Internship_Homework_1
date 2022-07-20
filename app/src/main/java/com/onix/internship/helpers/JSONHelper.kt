package com.onix.internship.helpers

import com.google.android.gms.maps.model.LatLng
import com.onix.internship.objects.Point
import org.json.JSONObject
import org.json.JSONTokener

class JSONHelper {

    fun parseJSONToPoint(json: String): Point {
        val jsonObject = JSONTokener(json).nextValue() as JSONObject

        val latLng = LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"))

        return Point(
            latLng, jsonObject.getString("date"), jsonObject.getString("time"),
            jsonObject.getInt("pointClass"), jsonObject.getInt("level")
        )
    }

    fun parsePointToJSON(point: Point): String {
        val jsonObject = JSONObject()

        jsonObject.put("lat", point.location.latitude)
        jsonObject.put("lon", point.location.longitude)
        jsonObject.put("date", point.date)
        jsonObject.put("time", point.time)
        jsonObject.put("pointClass", point.pointClass)
        jsonObject.put("level", point.level)

        return jsonObject.toString()
    }

}