package org.hikit.importer.validator

import com.google.inject.Inject
import org.hikit.common.data.Trail
import org.hikit.common.data.validator.CountryValidator
import org.hikit.common.data.validator.Validator

class TrailCreationValidator @Inject constructor(private val countryValidator: CountryValidator,
                                                 private val connectPointsValidator: ConnectingWayPointsCreationValidator,
                                                 private val coordsValidator: CoordinatesWithAltitudeCreationValidator,
                                                 private val poiValidator: PoiCreationValidator,
                                                 private val positionValidator: PositionCreationValidator) : Validator<Trail> {
    companion object {
        const val minGeoPoints = 3

        const val emptyListPointError = "No geo points specified"
        const val tooFewPointsError = "At least $minGeoPoints geoPoints should be specified"
        const val noParamSpecified = "Empty field '%s'"

    }

    override fun validate(request: Trail): Set<String> {
        val errors = mutableSetOf<String>()
        if (request.name.isEmpty()) {
            errors.add(String.format(noParamSpecified, "Name"))
        }
        if (request.code.isEmpty()) {
            errors.add(String.format(noParamSpecified, "Code"))
        }
        if (request.coordinates.isEmpty()) {
            errors.add(emptyListPointError)
        }
        if (request.coordinates.size < minGeoPoints) {
            errors.add(tooFewPointsError)
        }

        val countryErrors = countryValidator.validate(request.country)
        errors.addAll(countryErrors)

        val errorsStartPos = positionValidator.validate(request.startPos)
        errors.addAll(errorsStartPos)

        val errorsFinalPos = positionValidator.validate(request.finalPos)
        errors.addAll(errorsFinalPos)

        val coordinatesSetErrors = request.coordinates.map { coordsValidator.validate(it) }
        coordinatesSetErrors.forEach { errors.addAll(it) }

        val poiSetErrors = request.pois.map { poiValidator.validate(it) }
        poiSetErrors.forEach { errors.addAll(it) }

        val connectingPointSetErrors = request.connectingWayPoints.map { connectPointsValidator.validate(it) }
        connectingPointSetErrors.forEach { errors.addAll(it) }

        return errors
    }
}