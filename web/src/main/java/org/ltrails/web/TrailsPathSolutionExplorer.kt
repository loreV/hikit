package org.ltrails.web

import com.google.inject.Inject
import mil.nga.sf.geojson.GeoJsonObject
import org.ltrails.common.data.ConnectingWayPoint
import org.ltrails.common.data.Position
import org.ltrails.common.data.Trail
import org.ltrails.common.data.TrailDAO
import org.ltrails.web.data.OrderedSolution
import org.ltrails.web.data.SuccessorPosition
import java.util.*

class TrailsPathSolutionExplorer @Inject constructor(private val trailDao: TrailDAO,
                                                     private val positionHelper: PositionHelper,
                                                     private val geoTrailCache: GeoTrailCache) {


    fun explorePathsSolutions(startingTrail: Trail,
                              destination: Position): Array<OrderedSolution> {

        val openList = TreeSet<OrderedSolution>()
        val closedList = TreeSet<OrderedSolution>()

        // Add first successor 'S'
        val startingSuccessor = SuccessorPosition(0.0,
                getManhattanDistanceFromTrailsPoints(startingTrail.startPos, destination),
                startingTrail.startPos,
                startingTrail,
                null) // TODO LV 3-8-2020 implement null safety

        openList.add(OrderedSolution(startingSuccessor))

        // TODO: avoid loops!
        val visited = mutableSetOf<Trail>()

        while (openList.isNotEmpty()) {
            // Q cheapest option at current time
            val processed: OrderedSolution = openList.pollFirst()!!

            // Generate the successors
            val connectingSuccessors = processed.trailPositionPath.trail.connectingWayPoints

            // Calculate each distance
            // Currently we only consider endingPositions
            connectingSuccessors.forEach {
                val costToNextPoint = calculateDistanceBetweenParentAndSuccessor(processed.getParentNode(), it)
                val heuristicValue = getManhattanDistanceFromTrailsPoints(it.position, destination)
                val successor = SuccessorPosition(
                        costToNextPoint,
                        heuristicValue,
                        it.position,
                        getSuccessorTrailFromMemory(it),
                        processed.getParentNode())

                val newSolution = OrderedSolution(successor)
                // Check if goal -> final Pos
                if (isGoal(getSuccessorTrailFromMemory(it).finalPos, destination))
                    closedList.add(newSolution) else openList.add(newSolution)
            }
        }
        return closedList.toTypedArray()
    }

    private fun calculateDistanceBetweenParentAndSuccessor(previousPosition: SuccessorPosition, it: ConnectingWayPoint): Double {
        val trailPostcode = previousPosition.trail.postCode
        val trailCode = previousPosition.trail.code
        geoTrailCache.addElementUnlessExists(trailPostcode, trailCode, previousPosition.trail)
        val trailGeoObject: GeoJsonObject = geoTrailCache.getElement(trailPostcode, trailCode)
        return previousPosition.costSoFar + positionHelper.getDistanceBetweenPointsOnTrailInM(trailGeoObject, previousPosition.position, it.position)
    }

    fun getDirectTrails(startingTrailsWithinRange: List<Trail>, trailsWithRequestedDestination: Set<Trail>) =
            startingTrailsWithinRange.intersect(trailsWithRequestedDestination).toList()

    private fun isGoal(it: Position, destination: Position) = positionHelper.isDestinationWithinHalfKm(it, destination)

    private fun getSuccessorTrailFromMemory(connectingWayPoint: ConnectingWayPoint) =
            trailDao.getTrailsByCodeAndPostcode(connectingWayPoint.connectingTo.code, connectingWayPoint.connectingTo.postcode)


    private fun getManhattanDistanceFromTrailsPoints(position: Position, positionTo: Position) = getManhattanDistanceFrom(position.coords.latitude,
            position.coords.longitude, positionTo.coords.latitude, positionTo.coords.longitude)

    private fun getManhattanDistanceFrom(latY: Double, longX: Double, destLatY: Double, destLongX: Double) =
            kotlin.math.abs(destLongX - longX) + kotlin.math.abs(destLatY - latY)
}
