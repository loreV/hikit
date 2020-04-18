package org.hikit.web

import mil.nga.sf.geojson.FeatureConverter
import mil.nga.sf.geojson.GeoJsonObject
import org.hikit.common.data.Trail

class GeoTrailCache {
    private val trailMap: HashMap<Pair<String, String>, GeoJsonObject> = HashMap()

    fun addElementUnlessExists(postCode: String, trailCode: String, trail: Trail) {
        val pair = Pair(postCode, trailCode)
        if (trailMap.containsKey(pair)) {
            trailMap[pair] = FeatureConverter.toGeoJsonObject(trail.geo)
        }
    }

    fun getElement(postCode: String, trailCode: String): GeoJsonObject = trailMap[Pair(postCode, trailCode)]!!

}