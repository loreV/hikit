package org.hikit.importer.service

import org.hikit.common.TrailManager
import org.hikit.common.data.CoordinatesWithAltitude
import org.hikit.common.data.TrailDistance
import org.hikit.common.data.UnitOfMeasurement
import javax.inject.Inject

class TrailImporterManager @Inject constructor(private val trailsManager : TrailManager){

    fun findPossibleConnectingWayPoints(coordinatesWithAltitudes: List<CoordinatesWithAltitude>): List<TrailDistance> {
        val trailDistancesWithinRangeAtPoint = arrayListOf<TrailDistance>()
        coordinatesWithAltitudes.forEach {
            trailDistancesWithinRangeAtPoint.addAll(trailsManager.getTrailDistancesWithinRangeAtPoint(it, 200, UnitOfMeasurement.m, 10))
        }
        return trailDistancesWithinRangeAtPoint
    }


}