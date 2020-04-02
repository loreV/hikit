package org.ltrails.web

import com.google.inject.Inject
import org.bson.Document
import org.ltrails.common.converter.MetricConverter
import org.ltrails.common.data.*

class TrailManager @Inject constructor(val trailDAO: TrailDAO, val metricConverter: MetricConverter) {

    fun getByTrailPostCodeCountry(trailCode: String, postCodes: List<String>, country: String): List<Trail> {
        val doc = Document()
        if (country.isNotBlank()) {
            doc.append(Trail.COUNTRY, country)
        }
        if (trailCode.isNotBlank()) {
            doc.append(Trail.CODE, trailCode)
        }
        if (postCodes.isNotEmpty()) {
            doc.append("\$or", listOf(
                    Document(Trail.START_POS + "." + Position.POSTCODE, Document("\$in", postCodes)),
                    Document(Trail.FINAL_POS + "." + Position.POSTCODE, Document("\$in", postCodes))
            ))
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