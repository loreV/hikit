package org.hikit.web

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
                                       val altitudeService: AltitudeServiceHelper) {

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
        val coordinatesWithAltitude = CoordinatesWithAltitude(coordinates.longitude,
                coordinates.latitude, altitudeService.getAltitudeByLongLat(coordinates.latitude, coordinates.longitude))
        val meters = if (unitOfMeasurement == UnitOfMeasurement.km) metricConverter.getMetersFromKm(distance) else distance
        if (!isAnyPoint) {
            val trailsByStartPosMetricDistance = trailDAO.getTrailsByStartPosMetricDistance(
                    coordinates.longitude,
                    coordinates.latitude,
                    meters, limit)
            return trailsByStartPosMetricDistance.map {
                TrailDistance(PositionProcessor.distanceBetweenPoints(coordinatesWithAltitude, it.startPos.coords).roundToInt(),
                        it.startPos.coords, it)
            }
        } else {
            val trailsByPointDistance = trailDAO.trailsByPointDistance(
                    coordinates.longitude,
                    coordinates.latitude,
                    meters, limit)

            // for each trail, calculate the distance
            return trailsByPointDistance.map {
                val closestCoordinate = getClosestCoordinate(coordinatesWithAltitude, it)
                TrailDistance(
                        PositionProcessor.distanceBetweenPoints(coordinatesWithAltitude, closestCoordinate).toInt(),
                        closestCoordinate, it)
            }
        }
    }

    /**
     * TODO: calculating the distance for many points could be very expensive. Filter out some points
     */
    fun getClosestCoordinate(givenCoordinatesWAltitude: CoordinatesWithAltitude, trail: Trail): CoordinatesWithAltitude {
        return trail.coordinates
                .minBy { PositionProcessor.distanceBetweenPoints(it, givenCoordinatesWAltitude) }!!
    }
}