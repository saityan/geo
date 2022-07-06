package saityan.misc.geo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import saityan.misc.geo.contract.GoogleMapsContractImplementation
import saityan.misc.geo.databinding.ActivityMapsBinding
import saityan.misc.geo.model.CustomMarker
import saityan.misc.geo.view.MarkersFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val mapsImplementation = GoogleMapsContractImplementation()
    private lateinit var mMap: GoogleMap
    private val markers: ArrayList<CustomMarker> = arrayListOf()
    private lateinit var mLocationClient : FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.markers_button -> supportFragmentManager
                .beginTransaction()
                .replace(binding.map.id, MarkersFragment.newInstance(markers))
                .addToBackStack("")
                .commit()
            R.id.remove_markers_button -> {
                for(i in (markers.size - 1) downTo 0) {
                    markers[i].marker.remove()
                    markers.removeAt(i)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (mapsImplementation.checkPermissions(this))
            mMap.isMyLocationEnabled = true
        else mapsImplementation.requestPermissions(this)

        mLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapsImplementation.findLastLocation(mMap, mLocationClient)

        with(this@MapsActivity.mMap) {
            mMap.setOnMapLongClickListener {
                addMapMarker(it)
            }
        }
   }

    override fun onBackPressed() {
        super.onBackPressed()
        if (markers.size > 0) {
            markers[markers.size - 1].marker.hideInfoWindow()
            markers[markers.size - 1].marker.showInfoWindow()
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
}
