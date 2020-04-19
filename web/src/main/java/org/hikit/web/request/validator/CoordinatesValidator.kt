package org.hikit.web.request.validator

interface CoordinatesValidator {
    companion object {
        enum class CoordDimension {
            LATITUDE, LONGITUDE
        }

        const val limitLongLat = 90
        const val longitudeValueOutOfBoundErrorMessage = "Longitude value out of bound"
        const val latitudeValueOutOfBoundErrorMessage = "Latitude value out of bound"
    }

    fun validateCoordinates(value: Double, dim: CoordDimension): String {
        if (value > limitLongLat || value < -limitLongLat) {
            return if (dim == CoordDimension.LATITUDE) latitudeValueOutOfBoundErrorMessage else longitudeValueOutOfBoundErrorMessage
        }
        return ""
    }
}