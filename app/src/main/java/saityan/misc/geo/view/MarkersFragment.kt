package saityan.misc.geo.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import saityan.misc.geo.R
import saityan.misc.geo.databinding.FragmentMarkersBinding
import saityan.misc.geo.model.CustomMarker

class MarkersFragment (
    private val markers: ArrayList<CustomMarker>
) : Fragment() {

    private val adapter: MarkersAdapter by lazy {
        MarkersAdapter(requireActivity().menuInflater)
    }
    private var _binding: FragmentMarkersBinding? = null
    private val binding: FragmentMarkersBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMarkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.markersList.adapter = adapter
        adapter.submitList(markers)
        registerForContextMenu(binding.markersList)
        toggleZeroMarkersText()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_marker_from_context -> adapter.removeItem()
            R.id.action_edit_marker_from_context -> {
                requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.map, MarkerFragment.newInstance(markers, adapter.itemPosition))
                .addToBackStack("")
                .commit()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun toggleZeroMarkersText() {
        if (markers.size == 0)
            binding.noMarkers.visibility = View.VISIBLE
        else
            binding.noMarkers.visibility = View.GONE
    }

    companion object {
        fun newInstance(markers: ArrayList<CustomMarker>): MarkersFragment {
            return MarkersFragment(markers)
        }
    }
}
