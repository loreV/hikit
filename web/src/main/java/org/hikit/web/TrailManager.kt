package org.hikit.web

import com.google.inject.Inject
import org.bson.Document
import org.hikit.common.converter.MetricConverter
import org.hikit.common.data.Coordinates
import org.hikit.common.data.Trail
import org.hikit.common.data.TrailDAO
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.TrailDAOHelper

class TrailManager @Inject constructor(private val trailDAO: TrailDAO,
                                       private val metricConverter: MetricConverter,
                                       private val trailDAOHelper: TrailDAOHelper) {

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

    fun getByGeo(coordinates: Coordinates, distance: Int, unitOfMeasurement: UnitOfMeasurement): List<Trail> {
        val meters = if (unitOfMeasurement == UnitOfMeasurement.km) metricConverter.getMetersFromKm(distance) else distance
        return trailDAO.getTrailsByStartPosMetricDistance(
                coordinates.longitude,
                coordinates.latitude,
                meters)
    }
}