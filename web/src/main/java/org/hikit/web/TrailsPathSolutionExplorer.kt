package org.hikit.web

import com.google.inject.Inject
import mil.nga.sf.geojson.GeoJsonObject
import org.hikit.common.data.ConnectingWayPoint
import org.hikit.common.data.Position
import org.hikit.common.data.Trail
import org.hikit.common.data.TrailDAO
import org.hikit.web.data.OrderedSolution
import org.hikit.web.data.PositionNode
import java.util.*

class TrailsPathSolutionExplorer @Inject constructor(private val trailDao: TrailDAO,
                                                     private val positionHelper: PositionHelper,
                                                     private val geoTrailCache: GeoTrailCache) {


    fun explorePathsSolutions(startingTrail: Trail,
                              destination: Position): Array<OrderedSolution> {

        val openList = TreeSet<OrderedSolution>()
        val closedList = TreeSet<OrderedSolution>()

        // Add first successor 'S'
        val startingSuccessor = PositionNode(0.0,
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
            val connectingSuccessors = processed.trailPositionNodePath.trail.connectingWayPoints

            if (connectingSuccessors.isEmpty()) {
                closedList.add(processed)
                continue
            }
            // Calculate each distance
            // Currently we only consider endingPositions
            connectingSuccessors.forEach {
                val parentNode = processed.trailPositionNodePath
                val costToNextPoint = calculateDistanceBetweenParentAndSuccessor(parentNode, it)
                val heuristicValue = getManhattanDistanceFromTrailsPoints(it.position, destination)
                val trail = getSuccessorTrailFromMemory(it)

                val successor = PositionNode(
                        costToNextPoint,
                        heuristicValue,
                        it.position,
                        trail,
                        parentNode)

                val newSolution = OrderedSolution(successor)
                // Check if goal -> final Pos
                if (positionHelper.isGoalOnTrail(it, trail, destination))
                    closedList.add(newSolution) else openList.add(newSolution)
            }
        }
        return closedList.toTypedArray()
    }

    private fun calculateDistanceBetweenParentAndSuccessor(previousPositionNode: PositionNode, it: ConnectingWayPoint): Double {
        val trailPostcode = previousPositionNode.trail.postCode.get(0) // TODO
        val trailCode = previousPositionNode.trail.code
        geoTrailCache.addElementUnlessExists(trailPostcode, trailCode, previousPositionNode.trail)
        val trailGeoObject: GeoJsonObject = geoTrailCache.getElement(trailPostcode, trailCode)
        return previousPositionNode.costSoFar + positionHelper.getDistanceBetweenPointsOnTrailInM(trailGeoObject, previousPositionNode.position, it.position)
    }

    fun getDirectTrails(startingTrailsWithinRange: List<Trail>, trailsWithRequestedDestination: Set<Trail>) =
            startingTrailsWithinRange.intersect(trailsWithRequestedDestination).toList()

    private fun getSuccessorTrailFromMemory(connectingWayPoint: ConnectingWayPoint) =
            trailDao.getTrailByCodeAndPostcodeCountry(connectingWayPoint.connectingTo.postcode, connectingWayPoint.connectingTo.trailCode)


    private fun getManhattanDistanceFromTrailsPoints(position: Position, positionTo: Position) = PositionProcessor.distanceBetweenPoint(position, positionTo)
}
