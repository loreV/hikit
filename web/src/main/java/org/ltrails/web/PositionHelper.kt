package org.ltrails.web

import mil.nga.sf.LineString
import mil.nga.sf.geojson.FeatureConverter
import mil.nga.sf.geojson.GeoJsonObject
import org.ltrails.common.data.Position

class PositionHelper {

    private val halfKmInKm = 500

    fun isDestinationWithinHalfKm(position: Position, position2: Position) =
            (PositionProcessor.getRadialDistance(position.coords.latitude,
                    position.coords.longitude,
                    position2.coords.latitude,
                    position.coords.longitude) < halfKmInKm)

    fun getDistanceBetweenPointsOnTrailInM(trail: GeoJsonObject, startingPosition: Position, connectingPosition: Position): Double {
        val featureCollection = FeatureConverter.toFeatureCollection(trail)
        val feature = featureCollection.features.stream().findFirst().get()
        val line: LineString = (feature.geometry.geometry as LineString)
        val points = line.points.map { it -> Position(it.z, it.y, it.x) }

        val indexStartingPos = getPositionIndex(points, startingPosition)
        val indexFinalPos = getPositionIndex(points, connectingPosition)

        if (indexStartingPos == indexFinalPos) return 0.0

        var distance = 0.0
        for (i in indexStartingPos..indexFinalPos) {
            if (i == indexStartingPos) continue
            distance += PositionProcessor.distanceBetweenPoint(points[i - 1], points[i])
        }
        return distance
    }

    private fun getPositionIndex(positions: List<Position>, position: Position): Int {
        // Could start looping with offset > than starting pos?
        for (t in positions.indices) {
            if (positions[t].coords.latitude.equals(position.coords.latitude) &&
                    positions[t].coords.longitude.equals(position.coords.longitude))
                return t
        }
        throw IllegalStateException("Cannot find given position index")
    }

}
