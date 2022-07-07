package saityan.misc.geo.model

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import saityan.misc.geo.R
import saityan.misc.geo.contract.GoogleMapsContractImplementation

class CustomMap (
    private val activity: AppCompatActivity
) : OnMapReadyCallback {
    private val mapsImplementation = GoogleMapsContractImplementation()
    private lateinit var mMap: GoogleMap
    private lateinit var mLocationClient: FusedLocationProviderClient
    val markers: ArrayList<CustomMarker> = arrayListOf()

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (mapsImplementation.checkPermissions(activity))
            mMap.isMyLocationEnabled = true
        else mapsImplementation.requestPermissions(activity)

        mLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        mapsImplementation.findLastLocation(mMap, mLocationClient)

        with(mMap) {
            mMap.setOnMapLongClickListener {
                addMapMarker(it)
            }
        }
    }

    private fun GoogleMap.addMapMarker(it: LatLng) {
        this.addMarker(
            MarkerOptions()
                .icon(
                    BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_map_pin)
                )
                .position(it)
                .title("Marker" + (markers.size + 1).toString())
        )?.let { marker ->
            markers.add(CustomMarker(marker))
            marker.showInfoWindow()
        }
    }

    fun clearMarkers() {
        if (markers.size > 0) {
            for (i in (markers.size - 1) downTo 0) {
                markers[i].marker.remove()
                markers.removeAt(i)
            }
        }
    }

    fun blinkLastMarker() {
        with (markers) {
            if (this.size > 0) {
                this[this.size - 1].marker.hideInfoWindow()
                this[this.size - 1].marker.showInfoWindow()
            }
        }
    }

    companion object {
        fun newInstance (activity: AppCompatActivity): CustomMap = CustomMap(activity)
    }
}
