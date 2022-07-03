package saityan.misc.geo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.Marker
import saityan.misc.geo.R
import saityan.misc.geo.databinding.ActivityMapsBinding
import saityan.misc.geo.databinding.FragmentMarkersBinding

class MarkersFragment (
    private val markers: ArrayList<Marker>
) : Fragment() {
    private val adapter: MarkersAdapter by lazy { MarkersAdapter(requireActivity().menuInflater) }
    private var _binding: FragmentMarkersBinding? = null
    private val binding: FragmentMarkersBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.markersList.adapter = adapter
        adapter.submitList(markers)
        registerForContextMenu(binding.markersList)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_marker_from_context -> {
                adapter.removeItem()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(markers: ArrayList<Marker>): MarkersFragment {
            return MarkersFragment(markers)
        }
    }
}
