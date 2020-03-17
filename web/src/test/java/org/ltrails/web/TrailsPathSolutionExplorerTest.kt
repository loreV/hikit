package org.ltrails.web

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
        val mockTrailDao: TrailDAO = mock()
        val mockPositionHelper: PositionHelper = mock()
        val mockCache: GeoTrailCache = mock()

        val startingTrail: Trail = mock()

        val anyPostcode = "anyPostcode"
        val anyTrailCode = "anyTrailCode"

        val mockStartPosition: Position = mock()
        val mockStartCoords: Coordinates = mock()
        whenever(mockStartCoords.latitude).thenReturn(44.491423646640975)
        whenever(mockStartCoords.longitude).thenReturn(11.245107650756836)
        whenever(mockStartPosition.coords).thenReturn(mockStartCoords)

        val mockFinalPosition: Position = mock()
        val mockFinalCoords: Coordinates = mock()

        whenever(mockFinalCoords.latitude).thenReturn(44.4890050991292)
        whenever(mockFinalCoords.longitude).thenReturn(11.251587867736815)
        whenever(mockFinalPosition.coords).thenReturn(mockFinalCoords)
        whenever(mockFinalPosition.postCode).thenReturn(anyPostcode)

        whenever(startingTrail.postCode).thenReturn(anyPostcode)
        whenever(startingTrail.code).thenReturn(anyTrailCode)
        whenever(startingTrail.startPos).thenReturn(mockStartPosition)

        // Only one way to go
        val mockConnectingWayPoint: ConnectingWayPoint = mock()
        whenever(mockConnectingWayPoint.position).thenReturn(mockFinalPosition)
        val onlyOneConnectingWayPoint = mutableListOf(mockConnectingWayPoint)

        val geoTrail: GeoJsonObject = mock()
        whenever(mockCache.getElement(anyPostcode, anyTrailCode)).thenReturn(geoTrail)

        whenever(mockPositionHelper
                .getDistanceBetweenPointsOnTrailInM(geoTrail,
                        mockStartPosition,
                        mockFinalPosition)).thenReturn(580.78)


        whenever(startingTrail.connectingWayPoints).thenReturn(onlyOneConnectingWayPoint)

        val trailsPathSolutionExplorer = TrailsPathSolutionExplorer(mockTrailDao, mockPositionHelper, mockCache)

        trailsPathSolutionExplorer.explorePathsSolutions(startingTrail, mockFinalPosition)

        verify(mockCache, times(1)).addElementUnlessExists(anyPostcode, anyTrailCode, startingTrail)
    }


}