package org.ltrails.web

import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkObject
import junit.framework.Assert.assertEquals
import mil.nga.sf.geojson.GeoJsonObject
import org.junit.Test
import org.ltrails.common.data.*

class TrailControllerPathSolutionExplorerTest {

    /**
     * Trail#  1
     * Length  -
     *      S --- F
     */
    @Test
    fun `one connecting point is the destination`() {

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

        every { startingTrail.postCode } returns arrayListOf(anyPostcode) // <- TODO should be only one postcode
        every { startingTrail.code } returns anyTrailCode
        every { startingTrail.startPos } returns mockStartPosition

        val mockTrailReference = mockkClass(TrailReference::class)
        every { mockTrailReference.trailCode } returns anyTrailCode
        every { mockTrailReference.postcode } returns anyPostcode

        // Only one way to go
        val mockConnectingWayPoint = mockkClass(ConnectingWayPoint::class)
        every { mockConnectingWayPoint.position } returns mockFinalPosition
        every { mockConnectingWayPoint.connectingTo } returns mockTrailReference

        val onlyOneConnectingWayPoint = mutableListOf(mockConnectingWayPoint)

        val mockGeoTrail: GeoJsonObject = mockkClass(GeoJsonObject::class)
        every { mockCache.addElementUnlessExists(anyPostcode, anyTrailCode, startingTrail) } returns Unit
        every { mockCache.getElement(anyPostcode, anyTrailCode) } returns (mockGeoTrail)

        mockkObject(PositionProcessor)
        // Starting
        every { PositionProcessor.distanceBetweenPoint(mockStartPosition, mockFinalPosition) } returns 20.0
        // With the following trail
        every { PositionProcessor.distanceBetweenPoint(mockFinalPosition, mockFinalPosition) } returns 0.0

        every {
            mockPositionHelper.getDistanceBetweenPointsOnTrailInM(mockGeoTrail,
                    mockStartPosition,
                    mockFinalPosition)
        } returns 580.78


        every { startingTrail.connectingWayPoints } returns (onlyOneConnectingWayPoint)


        val mockConnectingTrail = mockkClass(Trail::class)
        every { mockTrailDao.getTrailByCodeAndPostcodeCountry(anyPostcode, anyTrailCode) } returns mockConnectingTrail

        every { mockPositionHelper.isGoalOnTrail(mockConnectingWayPoint, mockConnectingTrail, mockFinalPosition) } returns true

        // Calls
        val trailsPathSolutionExplorer = TrailsPathSolutionExplorer(mockTrailDao, mockPositionHelper, mockCache)
        val explorePathsSolutions = trailsPathSolutionExplorer.explorePathsSolutions(startingTrail, mockFinalPosition)


        val solution = explorePathsSolutions.first()
        assertEquals(2, solution.size())
        assertEquals(mockStartPosition, solution.trailPositionNodePath.parent!!.position)
        assertEquals(20.0, solution.trailPositionNodePath.parent!!.heuristicsCost)
        assertEquals(mockFinalPosition, solution.trailPositionNodePath.position)
        assertEquals(580.78, solution.trailPositionNodePath.costSoFar)
    }

    /**
     * Trail#  1
     * Length  1
     *      S -- B               F (destination)
     */
    @Test
    fun `no trail brings there`() {
    }

    /**
     * Trail#  1      2
     * Length  1      2
     *      S -- B  ---- C        F (destination)
     *            \              /
     *             - - - E - - -
     * Length       4       7
     * Trail#       3       4
     */
    @Test
    fun `one longer trail brings there while shortest does not`() {
    }

    /**
     * Trail#  1        2
     * Length  1        5
     *      S -- B -------------- F (destination)
     *            \              /
     *             - - - E - - -
     * Length       1       6
     * Trail#       3       4
     */
    @Test
    fun `many connections are longer than less connections`() {
    }

    /**
     * Trail#  1    2    3    4
     * Length  1    1    1    1
     *      S -- B -- C -- D --- F (destination)
     *            \              /
     *             - - - E - - -
     * Length       3       2
     * Trail#       5       6
     */
    @Test
    fun `many connections are shorter than less connections`() {
    }
}