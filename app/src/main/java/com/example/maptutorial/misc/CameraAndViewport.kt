package com.example.maptutorial.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class CameraAndViewport {

    val losAngeles : CameraPosition =  CameraPosition.Builder()
            .target(LatLng(-34.0, 151.0))
            .zoom(17f)
            .bearing(0f)
            .tilt(45f)
            .build()
}