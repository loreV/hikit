package org.ltrails.web

import io.mockk.every
import io.mockk.mockkClass
import mil.nga.sf.geojson.GeoJsonObject
import org.junit.Test
import org.ltrails.common.data.*

class TrailsPathSolutionExplorerTest {

    val twoPointsGeo = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[11.245107650756836,44.491423646640975,0],[11.251587867736815,44.4890050991292,0]]}}]}"

    /**
     * Two point path:
     * A (start) -> B (end)
     * GEO - 2 points.
     */
    @Test
    fun `in simple hike with only one solution explore path solution`() {
        val mockTrailDao = mockkClass(TrailDAO::class)
        val mockPositionHelper = mockkClass(PositionHelper::class)
        val mockCache = mockkClass(GeoTrailCache::class)

        val startingTrail = mockkClass(Trail::class)

        val anyPostcode = "anyPostcode"
        val anyTrailCode = "anyTrailCode"

        val mockStartPosition = mockkClass(Position::class)
        val mockStartCoords = mockkClass(Coordinates::class)
        every { mockStartCoords.latitude } returns 44.491423646640975
        every { mockStartCoords.longitude } returns 11.245107650756836
        every { mockStartPosition.coords } returns mockStartCoords

        val mockFinalPosition = mockkClass(Position::class)
        val mockFinalCoords = mockkClass(Coordinates::class)

        every { mockFinalCoords.latitude } returns 44.4890050991292
        every { mockFinalCoords.longitude } returns 11.251587867736815
        every { mockFinalPosition.coords } returns mockFinalCoords
        every { mockFinalPosition.postCode } returns anyPostcode

        every { startingTrail.postCode } returns anyPostcode
        every { startingTrail.code } returns anyTrailCode
        every { startingTrail.startPos } returns mockStartPosition

        // Only one way to go
        val mockConnectingWayPoint = mockkClass(ConnectingWayPoint::class)
        every { mockConnectingWayPoint.position } returns mockFinalPosition
        val onlyOneConnectingWayPoint = mutableListOf(mockConnectingWayPoint)

        val geoTrail: GeoJsonObject = mockkClass(GeoJsonObject::class)
        every { mockCache.getElement(anyPostcode, anyTrailCode) } returns (geoTrail)

        every {
            mockPositionHelper
                    .getDistanceBetweenPointsOnTrailInM(geoTrail,
                            mockStartPosition,
                            mockFinalPosition)
        } returns (580.78)


        every { startingTrail.connectingWayPoints } returns (onlyOneConnectingWayPoint)

        val trailsPathSolutionExplorer = TrailsPathSolutionExplorer(mockTrailDao, mockPositionHelper, mockCache)
//        verify { mockCache.xaddElementUnlessExists(anyPostcode, anyTrailCode, startingTrail) }

        trailsPathSolutionExplorer.explorePathsSolutions(startingTrail, mockFinalPosition)

    }


}