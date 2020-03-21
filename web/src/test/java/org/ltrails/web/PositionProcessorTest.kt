package org.ltrails.web

import io.mockk.every
import io.mockk.mockkClass
import mil.nga.sf.geojson.FeatureConverter
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.ltrails.common.data.Coordinates
import org.ltrails.common.data.Position

class PositionHelperTest {

    private val fourPointHike = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[11.244678497314451,44.49154610207528,1],[11.251072883605957,44.48923471237884,1],[11.255149841308594,44.4873059329883,1],[11.259291172027588,44.48432079153089,1]]}}]}"

    @Test
    fun `given a simple two point hike, should calculate distance from start`() {
        val shortHike = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[11.245107650756836,44.491423646640975,0],[11.251587867736815,44.4890050991292,0]]}}]}"
        val toGeoJsonObject = FeatureConverter.toGeoJsonObject(shortHike)

        // Start pos
        val mockStartPosition = mockkClass(Position::class)
        val mockCoords = mockkClass(Coordinates::class)

        every { mockStartPosition.coords } returns mockCoords
        every { mockCoords.latitude } returns 44.491423646640975
        every { mockCoords.longitude } returns 11.245107650756836

        // Destination
        val mockConnectingPosition = mockkClass(Position::class)
        val mockCoordsConnecting = mockkClass(Coordinates::class)

        every { mockConnectingPosition.coords } returns mockCoordsConnecting
        every { mockCoordsConnecting.latitude } returns 44.4890050991292
        every { mockCoordsConnecting.longitude } returns 11.251587867736815

        val distanceBetweenPointsOnTrail = PositionHelper().getDistanceBetweenPointsOnTrailInM(toGeoJsonObject, mockStartPosition, mockConnectingPosition)
        assertEquals(580.78, distanceBetweenPointsOnTrail, 1.0)
    }

    @Test
    fun `given a simple four point hike, should calculate distance from start`() {
        val toGeoJsonObject = FeatureConverter.toGeoJsonObject(fourPointHike)

        // Start pos
        val mockStartPosition = mockkClass(Position::class)
        val mockCoords = mockkClass(Coordinates::class)

        every { mockStartPosition.coords } returns mockCoords
        every { mockCoords.latitude } returns 44.49154610207528
        every { mockCoords.longitude } returns 11.244678497314451

        // Destination
        val mockConnectingPosition = mockkClass(Position::class)
        val mockCoordsConnecting = mockkClass(Coordinates::class)

        every { mockConnectingPosition.coords } returns mockCoordsConnecting
        every { mockCoordsConnecting.latitude } returns 44.48432079153089
        every { mockCoordsConnecting.longitude } returns 11.259291172027588

        val distanceBetweenPointsOnTrail = PositionHelper().getDistanceBetweenPointsOnTrailInM(toGeoJsonObject, mockStartPosition, mockConnectingPosition)
        assertEquals(1425.30, distanceBetweenPointsOnTrail, 1.0)
    }

    @Test
    fun `given a simple four point hike, should calculate distance from middle`() {
        val toGeoJsonObject = FeatureConverter.toGeoJsonObject(fourPointHike)

        // Start pos
        val mockStartPosition = mockkClass(Position::class)
        val mockCoords = mockkClass(Coordinates::class)

        every { mockStartPosition.coords } returns mockCoords
        every { mockCoords.latitude } returns 44.48923471237884
        every { mockCoords.longitude } returns 11.251072883605957
        // Destination
        val mockConnectingPosition = mockkClass(Position::class)
        val mockCoordsConnecting = mockkClass(Coordinates::class)

        every { mockConnectingPosition.coords } returns mockCoordsConnecting
        every { mockCoordsConnecting.latitude } returns 44.48432079153089
        every { mockCoordsConnecting.longitude } returns 11.259291172027588

        val distanceBetweenPointsOnTrail = PositionHelper().getDistanceBetweenPointsOnTrailInM(toGeoJsonObject, mockStartPosition, mockConnectingPosition)
        assertEquals(856.00, distanceBetweenPointsOnTrail, 1.0)
    }

    @Test
    fun `given a simple four point hike, should calculate 0 distance for same point`() {
        val toGeoJsonObject = FeatureConverter.toGeoJsonObject(fourPointHike)

        // Start pos
        val mockStartPosition = mockkClass(Position::class)
        val mockCoords = mockkClass(Coordinates::class)

        every { mockStartPosition.coords } returns mockCoords
        every { mockCoords.latitude } returns 44.48432079153089 // Same as connecting point
        every { mockCoords.longitude } returns 11.259291172027588 // Same as connecting point
        // Destination
        val mockConnectingPosition = mockkClass(Position::class)
        val mockCoordsConnecting = mockkClass(Coordinates::class)

        every { mockConnectingPosition.coords } returns mockCoordsConnecting
        every { mockCoordsConnecting.latitude } returns 44.48432079153089
        every { mockCoordsConnecting.longitude } returns 11.259291172027588

        val distanceBetweenPointsOnTrail = PositionHelper().getDistanceBetweenPointsOnTrailInM(toGeoJsonObject, mockStartPosition, mockConnectingPosition)
        assertEquals(0.0, distanceBetweenPointsOnTrail, 1.0)
    }

    @Test
    fun `given a simple four point hike, should throw exception cause points do not exist`() {
        val toGeoJsonObject = FeatureConverter.toGeoJsonObject(fourPointHike)

        // Start pos
        val mockStartPosition = mockkClass(Position::class)
        val mockCoords = mockkClass(Coordinates::class)

        every { mockStartPosition.coords } returns mockCoords
        every { mockCoords.latitude } returns 44.4843207915308 // Not existing!
        every { mockCoords.longitude } returns 11.25929117202758 // Not existing!
        // Destination
        val anyMock = mockkClass(Position::class)
        val anyCoord = mockkClass(Coordinates::class)

        every { anyMock.coords } returns anyCoord
        every { anyCoord.latitude } returns 44.48432079153089
        every { anyCoord.longitude } returns 11.259291172027588

        try {
            PositionHelper().getDistanceBetweenPointsOnTrailInM(toGeoJsonObject, mockStartPosition, anyMock)
            fail()
        } catch (e: IllegalStateException) {
        }
    }
}