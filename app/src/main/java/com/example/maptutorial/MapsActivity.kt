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
import com.example.maptutorial.misc.Shapes
import com.example.maptutorial.misc.TypeAndStyle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val typeAndStyle by lazy { TypeAndStyle() }
    private val cameraAndViewport by lazy {CameraAndViewport()}
    private var start = LatLng(39.92227736063888, 32.84759116750344)
    private var target = LatLng(37.90388307574215, 32.51564981785679)

    private val shapes by lazy { Shapes() }

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

    override  fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        val marker =  mMap.addMarker(MarkerOptions().position(start).title("Marker in Sydney")
                .draggable(true)
                .position(start))


        val marker2 = mMap.addMarker(MarkerOptions().position(target).title("Target")
                .position(target))

        marker.tag="Restaurant"

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,10f))
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isMapToolbarEnabled = true
            isMyLocationButtonEnabled = true
        }
        typeAndStyle.setMapStyle(mMap,context = this)
        lifecycleScope.launch{
            shapes.addPolyline(mMap)
        }

        //shapes.addCircle(mMap)
        //shapes.addPolygon(mMap)

    }




    private fun fromVectorToMap(id : Int , color: Int) : BitmapDescriptor{
        val vectorDrawable : Drawable = ResourcesCompat.getDrawable(resources,id,null)
                ?: return BitmapDescriptorFactory.defaultMarker()
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



}