package saityan.misc.geo.model

import com.google.android.gms.maps.model.Marker

class CustomMarker (
    val marker: Marker,
) {
    var description: String = ""

    override fun hashCode(): Int {
        var result = marker.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomMarker

        if (marker != other.marker) return false
        if (description != other.description) return false

        return true
    }
}
