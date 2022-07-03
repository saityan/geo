package saityan.misc.geo.view

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.Marker
import saityan.misc.geo.R
import saityan.misc.geo.databinding.ItemMarkerBinding

class MarkersAdapter(
    private val menuInflater: MenuInflater
) : ListAdapter<Marker, MarkersAdapter.MarkersViewHolder>(MarkersCallback) {

    private var itemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkersViewHolder =
        MarkersViewHolder(
            ItemMarkerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MarkersViewHolder, position: Int) {
        holder.bind(position)
    }

    fun removeItem() {
        val currentListMutable = currentList.toMutableList()
        currentListMutable.removeAt(itemPosition)
        submitList(currentListMutable)
    }

    inner class MarkersViewHolder(private val binding: ItemMarkerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            markerName.text = currentList[position].title
            itemView.setOnLongClickListener {
                itemPosition = adapterPosition
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menuInflater.inflate(R.menu.fragment_markers, menu)
        }
    }

    companion object MarkersCallback : DiffUtil.ItemCallback<Marker>() {
        override fun areItemsTheSame(oldItem: Marker, newItem: Marker) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Marker, newItem: Marker) = oldItem == newItem
    }
}
