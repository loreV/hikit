package org.ltrails.web

import com.google.inject.Inject
import org.bson.Document
import org.ltrails.common.converter.MetricConverter
import org.ltrails.common.data.Coordinates
import org.ltrails.common.data.Trail
import org.ltrails.common.data.TrailDAO
import org.ltrails.common.data.UnitOfMeasurement
import org.ltrails.web.data.helper.TrailDAOHelper

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