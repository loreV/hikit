package org.hikit.importer.validator

import org.hikit.common.data.CoordinatesWithAltitude
import org.hikit.common.data.validator.CoordinatesValidator
import org.hikit.common.data.validator.Validator

class CoordinatesWithAltitudeCreationValidator : Validator<CoordinatesWithAltitude>, CoordinatesValidator {

    override fun validate(request: CoordinatesWithAltitude): Set<String> {
        val listOfErrorMessages = mutableSetOf<String>()

        val validateLongitude = validateCoordinates(request.longitude, CoordinatesValidator.Companion.CoordDimension.LONGITUDE)
        if (validateLongitude.isNotEmpty()) listOfErrorMessages.add(validateLongitude)
        val validateLatitude = validateCoordinates(request.latitude, CoordinatesValidator.Companion.CoordDimension.LATITUDE)
        if (validateLatitude.isNotEmpty()) listOfErrorMessages.add(validateLatitude)
        return listOfErrorMessages
    }

}