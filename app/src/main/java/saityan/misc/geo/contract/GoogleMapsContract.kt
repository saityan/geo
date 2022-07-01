package saityan.misc.geo.contract

import android.app.Activity
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap

interface GoogleMapsContract {
    fun checkPermissions(context: Context) : Boolean

    fun requestPermissions(activity: Activity)

    fun findLastLocation(map: GoogleMap, locationClient: FusedLocationProviderClient) : Boolean
}
