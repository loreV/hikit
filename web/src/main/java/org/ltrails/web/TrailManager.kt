package org.ltrails.web

import com.google.common.collect.ImmutableList
import com.google.inject.Inject
import org.ltrails.common.data.Coordinates
import org.ltrails.common.data.Trail
import org.ltrails.common.data.TrailDAO
import org.ltrails.common.data.UnitOfMeasurement

class TrailManager @Inject constructor(val trailDAO: TrailDAO) {

    fun getByTrailPostCodeCountry(listOfParamsPresent: MutableList<String>,
                                  stringMap: MutableMap<String, Array<String>>): ImmutableList<Trail> {
        throw NotImplementedError();
    }

    fun getByGeo(coordinates: Coordinates, distance: Double, unitOfMeasurement: UnitOfMeasurement): List<Trail> {
        return emptyList()
    }
}