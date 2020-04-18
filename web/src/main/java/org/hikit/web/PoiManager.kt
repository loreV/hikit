package org.hikit.web

import com.google.inject.Inject
import org.bson.Document
import org.hikit.common.converter.MetricConverter
import org.hikit.common.data.*
import org.hikit.common.data.helper.PoiDAOHelper

class PoiManager @Inject constructor(private val poiDao: PoiDAO,
                                     private val metricConverter: MetricConverter,
                                     private val poiDaoHelper: PoiDAOHelper) {

    fun getByTrailPostCodeCountryType(trailCodes: String,
                                      postCode: List<String>,
                                      country: String,
                                      types: List<String>): List<Poi> {
        var doc = Document()
        if (country.isNotBlank()) {
            doc = poiDaoHelper.appendEqualFilter(doc, Trail.COUNTRY, country)
        }
        if (trailCodes.isNotEmpty()) {
            val resolvedTrailRefField = Poi.TRAIL_REF + "." + TrailReference.TRAIL_CODE
            doc = poiDaoHelper.appendEqualFilter(doc, resolvedTrailRefField, trailCodes)
        }
        if (postCode.isNotEmpty()) {
            doc = poiDaoHelper.appendIn(doc, Poi.POST_CODE, postCode)
        }
        if (types.isNotEmpty()) {
            doc = poiDaoHelper.appendIn(doc, Poi.TYPES, types)
        }
        return poiDao.executeQueryAndGetResult(doc)
    }

    fun getByGeo(coordinates: Coordinates, distance: Int, unitOfMeasurement: UnitOfMeasurement, types: List<String>): List<Poi> {
        val meters = if (unitOfMeasurement == UnitOfMeasurement.km) metricConverter.getMetersFromKm(distance) else distance
        return poiDao.getPosByPositionAndTypes(
                coordinates.longitude,
                coordinates.latitude,
                meters, types)
    }
}