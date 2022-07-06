package saityan.misc.geo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import saityan.misc.geo.databinding.FragmentMarkerBinding
import saityan.misc.geo.model.CustomMarker

class MarkerFragment(
    private val markers: ArrayList<CustomMarker>,
    private val position: Int
) : Fragment() {

    private var _binding: FragmentMarkerBinding? = null
    private val binding: FragmentMarkerBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarkerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.markerNameEdit.setText(markers[position].marker.title)
        binding.markerDescriptionEdit.setText(markers[position].description)
    }

    override fun onDestroy() {
        val name = binding.markerNameEdit.text.toString()
        if (name != "")
            markers[position].marker.title = name
        markers[position].description = binding.markerDescriptionEdit.text.toString()
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(markers: ArrayList<CustomMarker>, position: Int): MarkerFragment {
            return MarkerFragment(markers, position)
        }
    }
}
