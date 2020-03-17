package org.ltrails.web

import com.google.inject.Inject
import org.ltrails.common.converter.MetricConverter
import org.ltrails.common.data.PlanningResult
import org.ltrails.common.data.Trail
import org.ltrails.common.data.TrailDAO
import org.ltrails.common.data.TrailsPath
import org.ltrails.common.data.mapper.UnitOfMeasurement
import org.ltrails.web.request.PlanningRequest
import java.util.*

class TrailProcessor @Inject constructor(private val trailDAO: TrailDAO,
                                         private val trailsPathExplorer: TrailsPathSolutionExplorer,
                                         private val metricConverter: MetricConverter) {

    val allTrails: List<Trail> get() = trailDAO.allTrails

    fun plan(planningRequest: PlanningRequest): PlanningResult? {

        val trailsWithRequestedDestination = getTrailsWithRequestedDestination(planningRequest)

        if (trailsWithRequestedDestination.isEmpty()) {
            return PlanningResult.PlanningResultBuilder.aPlanningResult().buildEmpty()
        }

        val distanceInMeters = if (planningRequest.unitOfMeasurement == UnitOfMeasurement.m)
            metricConverter.getMetersFromKm(planningRequest.distanceFromPosition) else planningRequest.distanceFromPosition


        val startingTrailsWithinRange = trailDAO.getTrailsByStartPosMetricDistance(planningRequest.startPos.coords.longitude,
                planningRequest.startPos.coords.latitude, distanceInMeters)

        // No close trails starts
        if (startingTrailsWithinRange.size == 0) {
            return PlanningResult.PlanningResultBuilder.aPlanningResult().buildEmpty()
        }

        // Check only for direct trails
        if (planningRequest.isDirectTrailsOnly) {
            val directTrails = trailsPathExplorer.getDirectTrails(startingTrailsWithinRange, trailsWithRequestedDestination)
            val elected = getElectedArbitrarily(directTrails)
            return getPlanningResult(getTrailsPathFromTrail(elected), getOptionalWithoutElected(directTrails, elected).map { trail -> getTrailsPathFromTrail(trail) })
        }

        // Take the first found trail ending position
        val destinationPos = trailsWithRequestedDestination.first().finalPos


        // TODO do this for either one trail or all of them.
//        val trailsPaths = trailsPathExplorer.explorePathsSolutions(,
//                destinationPos)


        return null
    }


    private fun getTrailsWithRequestedDestination(planningRequest: PlanningRequest): Set<Trail> {
        val trailsHavingDestinationOrTagsNameLike = trailDAO.getTrailsHavingDestinationNameLike(planningRequest.destination)
                .union(trailDAO.getTrailsHavingDestinationTagsLike(planningRequest.destination))
        return if (planningRequest.isSearchDescription) trailsHavingDestinationOrTagsNameLike.union(trailDAO.getTrailsHavingDescriptionLike(planningRequest.destination))
        else trailsHavingDestinationOrTagsNameLike
    }


    private fun getPlanningResult(elected: TrailsPath, alternatives: List<TrailsPath>): PlanningResult =
            PlanningResult.PlanningResultBuilder.aPlanningResult()
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