package com.example.maptutorial.misc

import android.graphics.Color
import com.example.maptutorial.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.delay

class Shapes {

    private var start = LatLng(39.92227736063888, 32.84759116750344)
    private var target = LatLng(27.01786801988754, 30.218936838453118)
    private var target2 = LatLng(28.91680817614032, 77.02035108291693)
    private val p0 = LatLng(39.76819893589757, 30.534528506113656)
    private val p1 = LatLng(39.76045584555918, 30.520013407703452)
    private val p2 = LatLng(39.78559911086176, 30.495010770089664)
    private val p3 = LatLng(39.79946563146704, 30.519967487379688)



     suspend fun addPolyline(mMap : GoogleMap){
        val pattern = listOf(Dot(),Gap(30f))
        val polyline = mMap.addPolyline(
                PolylineOptions().apply {
                    add(start,target)
                            .width(40f)
                            .color(Color.BLUE)
                            .geodesic(true)
                            .pattern(pattern)
                }

        )
        delay(5000L)
        val newList = listOf(start,target,target2)
        polyline.points = newList
    }


     fun addPolygon(map : GoogleMap){
        val polygon = map.addPolygon(
                PolygonOptions().apply {
                    add(p0,p1,p2,p3)
                }
        )
    }

    fun addCircle(map : GoogleMap){
        val circle = map.addCircle(
                CircleOptions().apply {
                    center(start)
                    radius(5000.0)
                    fillColor(R.color.purple_200)
                }
        )
    }
}