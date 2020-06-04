package org.hikit.common

import com.google.inject.Inject
import org.bson.Document
import org.hikit.common.converter.MetricConverter
import org.hikit.common.data.*
import org.hikit.common.data.helper.TrailDAOHelper
import org.hikit.common.service.AltitudeServiceHelper
import kotlin.math.roundToInt

class TrailManager @Inject constructor(private val trailDAO: TrailDAO,
                                       private val metricConverter: MetricConverter,
                                       private val trailDAOHelper: TrailDAOHelper,
                                       private val altitudeService: AltitudeServiceHelper) {

    fun getByTrailPostCodeCountry(trailCode: String, postCodes: List<String>, country: String): List<Trail> {
        var doc = Document()
        if (country.isNotBlank()) {
            doc = trailDAOHelper.appendEqualFilter(doc, Trail.COUNTRY, country)
        }
        if (trailCode.isNotBlank()) {
            doc = trailDAOHelper.appendEqualFilter(doc, Trail.CODE, trailCode)
        }
        if (postCodes.isNotEmpty()) {
            doc = trailDAOHelper.appendOrFilterOnStartAndFinalPost(doc, postCodes)
        }
        return trailDAO.executeQueryAndGetResult(doc)
    }

    fun getByGeo(coordinates: Coordinates, distance: Int, unitOfMeasurement: UnitOfMeasurement, isAnyPoint: Boolean, limit: Int): List<TrailDistance> {
        val coords = CoordinatesWithAltitude(coordinates.longitude,
                coordinates.latitude, altitudeService.getAltitudeByLongLat(coordinates.latitude, coordinates.longitude))
        val meters = getMeters(unitOfMeasurement, distance)
        if (!isAnyPoint) {
            val trailsByStartPosMetricDistance = trailDAO.getTrailsByStartPosMetricDistance(
                    coords.longitude,
                    coords.latitude,
                    meters, limit)
            return trailsByStartPosMetricDistance.map {
                TrailDistance(PositionProcessor.distanceBetweenPoints(coords, it.startPos.coords).roundToInt(),
                        it.startPos.coords, it)
            }
        } else {
            return getTrailDistancesWithinRangeAtPoint(coords, distance, unitOfMeasurement, limit)
        }
    }

    /**
     * Get a list of trail distances from a given point.
     *
     * @param coordinates the given coordinate
     * @param distance the distance value
     * @param unitOfMeasurement a specific unit of measurement to range within
     * @param limit maximum number of trails to be found near the given coordinate
     */
    fun getTrailDistancesWithinRangeAtPoint(coordinates: CoordinatesWithAltitude, distance: Int, unitOfMeasurement: UnitOfMeasurement, limit: Int) : List<TrailDistance> {
        val meters = getMeters(unitOfMeasurement, distance)
        val trailsByPointDistance = trailDAO.trailsByPointDistance(
                coordinates.longitude,
                coordinates.latitude,
                meters, limit)

        // for each trail, calculate the distance
        return trailsByPointDistance.map {
            val closestCoordinate = getClosestCoordinate(coordinates, it)
            TrailDistance(
                    PositionProcessor.distanceBetweenPoints(coordinates, closestCoordinate).toInt(),
                    closestCoordinate, it)
        }
    }

    /**
     * Get the trail closest point to a given coordinate
     *
     * @param givenCoordinatesWAltitude the given coordinate
     * @param trail to refer to
     */
    fun getClosestCoordinate(givenCoordinatesWAltitude: CoordinatesWithAltitude, trail: Trail): CoordinatesWithAltitude {
        return trail.coordinates
                .minBy { PositionProcessor.distanceBetweenPoints(it, givenCoordinatesWAltitude) }!!
    }

    private fun getMeters(unitOfMeasurement: UnitOfMeasurement, distance: Int) =
            if (unitOfMeasurement == UnitOfMeasurement.km) metricConverter.getMetersFromKm(distance) else distance
}