package saityan.misc.geo.view

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import saityan.misc.geo.R
import saityan.misc.geo.databinding.ItemMarkerBinding
import saityan.misc.geo.model.CustomMarker

class MarkersAdapter(
    private val menuInflater: MenuInflater,
) : ListAdapter<CustomMarker, MarkersAdapter.MarkersViewHolder>(MarkersCallback) {

    var itemPosition = -1

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
        with(currentListMutable) {
            this[itemPosition].marker.remove()
            this.removeAt(itemPosition)
            submitList(this)
        }
    }

    inner class MarkersViewHolder(private val binding: ItemMarkerBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(position: Int) = with(binding) {
            markerName.text = currentList[position].marker.title
            markerDescription.text = currentList[position].description
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

    companion object MarkersCallback : DiffUtil.ItemCallback<CustomMarker>() {
        override fun areItemsTheSame(oldItem: CustomMarker, newItem: CustomMarker) = oldItem == newItem
        override fun areContentsTheSame(oldItem: CustomMarker, newItem: CustomMarker) = oldItem == newItem
    }
}
