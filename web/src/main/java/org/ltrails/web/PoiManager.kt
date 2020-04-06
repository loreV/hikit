package org.ltrails.web

import com.google.inject.Inject
import org.bson.Document
import org.ltrails.common.converter.MetricConverter
import org.ltrails.common.data.*
import org.ltrails.web.data.helper.PoiDAOHelper

class PoiManager @Inject constructor(private val poiDao: PoiDAO,
                                     private val metricConverter: MetricConverter,
                                     private val poiDaoHelper: PoiDAOHelper) {

    fun getByTrailPostCodeCountry(trailCodes: List<String>,
                                  postCode: String,
                                  country: String,
                                  types: List<String>): MutableList<Trail> {
        var doc = Document()
        if (country.isNotBlank()) {
            doc = poiDaoHelper.appendEqualFilter(doc, Trail.COUNTRY, country)
        }
        if (trailCodes.isNotEmpty()) {
            doc = poiDaoHelper.appendIn(doc, Poi.TRAIL_CODES, trailCodes)
        }
        if (postCode.isNotEmpty()) {
            doc = poiDaoHelper.appendEqualFilter(doc, Poi.POST_CODE, postCode)
        }
        if (types.isNotEmpty()) {
            doc = poiDaoHelper.appendIn(doc, Poi.TYPES, types)
        }
        return poiDao.executeQueryAndGetResult(doc)
    }

    fun getByGeo(coordinates: Coordinates, distance: Int, unitOfMeasurement: UnitOfMeasurement, types: List<String>): List<Trail> {
        val meters = if (unitOfMeasurement == UnitOfMeasurement.km) metricConverter.getMetersFromKm(distance) else distance
        return poiDao.getPosByPositionAndTypes(
                coordinates.longitude,
                coordinates.latitude,
                meters, types)
    }
}