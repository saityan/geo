package saityan.misc.geo.contract

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class GoogleMapsContractImplementation : GoogleMapsContract {
    override fun checkPermissions(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
            &&
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)
    }

    override fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 1001
        )
    }

    @SuppressLint("MissingPermission")
    override fun findLastLocation(
        map: GoogleMap,
        locationClient: FusedLocationProviderClient
    ): Boolean {
        var isSuccessful = false
        locationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let {
                    val coordinates = LatLng(it.latitude, it.longitude)
                    val cameraUpdate: CameraUpdate =
                        CameraUpdateFactory.newLatLngZoom(coordinates, 18f)
                    map.moveCamera(cameraUpdate)
                    map.mapType = GoogleMap.MAP_TYPE_NORMAL
                    isSuccessful = true
                }
            }
        }
        return isSuccessful
    }
}
