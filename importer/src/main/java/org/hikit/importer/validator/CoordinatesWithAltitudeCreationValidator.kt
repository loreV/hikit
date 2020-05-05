package org.hikit.importer.validator

import org.hikit.common.data.CoordinatesWithAltitude
import org.hikit.common.data.validator.Validator

class CoordinatesWithAltitudeCreationValidator : Validator<CoordinatesWithAltitude> {
    companion object {
        const val coordinatesError = "A coordinate set not valid"
    }

    override fun validate(request: CoordinatesWithAltitude): Set<String> {
        return emptySet()
    }

}