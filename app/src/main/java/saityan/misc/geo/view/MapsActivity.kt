package saityan.misc.geo.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import saityan.misc.geo.R
import saityan.misc.geo.contract.GoogleMapsContractImplementation
import saityan.misc.geo.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val mapsImplementation = GoogleMapsContractImplementation()
    private lateinit var mMap: GoogleMap
    private lateinit var mLocationClient : FusedLocationProviderClient
    private var _binding: ActivityMapsBinding? = null
    private val binding: ActivityMapsBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (mapsImplementation.checkPermissions(this))
            mMap.isMyLocationEnabled = true
        else mapsImplementation.requestPermissions(this)

        mLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapsImplementation.findLastLocation(mMap, mLocationClient)
   }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
