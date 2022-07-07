package saityan.misc.geo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.SupportMapFragment
import saityan.misc.geo.databinding.ActivityMapsBinding
import saityan.misc.geo.model.CustomMap
import saityan.misc.geo.view.MarkersFragment

class MapsActivity : AppCompatActivity() {
    private val map = CustomMap.newInstance(this)
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(map)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.markers_button -> supportFragmentManager
                .beginTransaction()
                .replace(binding.map.id, MarkersFragment.newInstance(map.markers))
                .addToBackStack("")
                .commit()
            R.id.remove_markers_button -> map.clearMarkers()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        map.blinkLastMarker()
    }
}
