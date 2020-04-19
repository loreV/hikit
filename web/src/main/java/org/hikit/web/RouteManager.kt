package org.hikit.web

import com.google.inject.Inject
import org.hikit.common.converter.MetricConverter
import org.hikit.common.data.*
import org.hikit.web.request.PlanningRestRequest
import java.util.*

class RouteManager @Inject constructor(private val trailDAO: TrailDAO,
                                       private val trailsPathExplorer: TrailsPathSolutionExplorer,
                                       private val metricConverter: MetricConverter) {

    fun plan(planning: PlanningRestRequest): List<RouteResult> {

        val trailsWithRequestedDestination = getTrailsWithRequestedDestination(planning)

        if (trailsWithRequestedDestination.isEmpty()) {
//            return PlanningResult.PlanningResultBuilder.aPlanningResult().buildEmpty()
        }

        val distanceInMeters = if (planning.unitOfMeasurement == UnitOfMeasurement.m)
            metricConverter.getMetersFromKm(planning.distanceFromPosition) else planning.distanceFromPosition


        val startingTrailsWithinRange = trailDAO.getTrailsByStartPosMetricDistance(planning.startPos.coords.longitude,
                planning.startPos.coords.latitude, distanceInMeters)

        // No close trails starts
        if (startingTrailsWithinRange.size == 0) {
//            return PlanningResult.PlanningResultBuilder.aPlanningResult().buildEmpty()
        }

        // Check only for direct trails
        if (planning.isDirectTrailsOnly) {
            val directTrails = trailsPathExplorer.getDirectTrails(startingTrailsWithinRange, trailsWithRequestedDestination)
            val elected = getElectedArbitrarily(directTrails)
//            getPlanningResult(getTrailsPathFromTrail(elected), getOptionalWithoutElected(directTrails, elected).map { trail -> getTrailsPathFromTrail(trail) })
        }

        // Take the first found trail ending position
        val destinationPos = trailsWithRequestedDestination.first().finalPos


        // TODO do this for either one trail or all of them.
//        val trailsPaths = trailsPathExplorer.explorePathsSolutions(,
//                destinationPos)


        return emptyList()
    }


    private fun getTrailsWithRequestedDestination(planning: PlanningRestRequest): Set<Trail> {
        val trailsHavingDestinationOrTagsNameLike = trailDAO.getTrailsHavingDestinationNameLike(planning.destination)
                .union(trailDAO.getTrailsHavingDestinationTagsLike(planning.destination))
        return if (planning.isSearchDescription) trailsHavingDestinationOrTagsNameLike.union(trailDAO.getTrailsHavingDescriptionLike(planning.destination))
        else trailsHavingDestinationOrTagsNameLike
    }


    private fun getPlanningResult(elected: TrailsPath, alternatives: List<TrailsPath>): RouteResult =
            RouteResult.PlanningResultBuilder.aPlanningResult()
                    .withWinningTrailsResult(elected)
                    .withOptionalAlternatives(alternatives).build()

    // TODO later elections should be based on some specific criteria.
    private fun getElectedArbitrarily(trails: Iterable<Trail>): Trail = trails.first()

    private fun getOptionalWithoutElected(trails: List<Trail>, elected: Trail): List<Trail> = trails.minus(elected)
    private fun getTrailsPathFromTrail(trail: Trail): TrailsPath =
            TrailsPath.TrailsPathBuilder
                    .aTrailsPath()
                    .withTrails(Collections.singletonList(trail))
                    .withConnectingPositions(Collections.emptyList())
                    .withDistance(trail.trackLength)
                    .withEta(trail.eta)
                    // TODO -> distance and ETA should be calculated
                    .withTotalElevationDown(0.0)
                    .withTotalElevationDown(0.0)
                    .build()

}