package com.example.maptutorial

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import com.example.maptutorial.misc.CameraAndViewport
import com.example.maptutorial.misc.TypeAndStyle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewport by lazy {CameraAndViewport()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        typeAndStyle.setMapType(item,mMap)

        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)

        val marker =  mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")
                .draggable(true)
                .position(sydney)
                .icon(fromVectorToMap(R.drawable.ic_launcher_background, Color.parseColor("#00d176")))
        )
        marker.tag="Restaurant"
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewport.losAngeles))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isMapToolbarEnabled = true
            isMyLocationButtonEnabled = true
        }
        typeAndStyle.setMapStyle(mMap,context = this)
        lifecycleScope.launch{
            delay(2000L)
            mMap.moveCamera(CameraUpdateFactory.zoomBy(3f))

        }
    }

    private fun fromVectorToMap(id : Int , color: Int) : BitmapDescriptor{
        val vectorDrawable : Drawable? = ResourcesCompat.getDrawable(resources,id,null)
        if(vectorDrawable == null){
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
        DrawableCompat.setTint(vectorDrawable,color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerDragStart(p0: Marker?) {
       Log.d("Drag","start")
    }

    override fun onMarkerDrag(p0: Marker?) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        TODO("Not yet implemented")
    }

}