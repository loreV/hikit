package org.hikit.web

import io.mockk.every
import io.mockk.mockk
import org.bson.Document
import org.hikit.common.TrailManager
import org.hikit.common.converter.MetricConverter
import org.hikit.common.data.Coordinates
import org.hikit.common.data.Trail
import org.hikit.common.data.Trail.CODE
import org.hikit.common.data.Trail.COUNTRY
import org.hikit.common.data.TrailDAO
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.TrailDAOHelper
import org.hikit.common.service.AltitudeServiceHelper
import org.junit.Test

class TrailManagerTest {

    private val anyLimit = 10
    private val anyPostcodeList = listOf("00001", "00002")

    @Test
    fun `get by trail, postCode and country should correctly address call to DAO`() {
        val mockTrailsDao = mockk<TrailDAO>()
        val mockMetricConverter = mockk<MetricConverter>()
        val mockDocument = mockk<Document>()
        val mockTrailsDaoHelper = mockk<TrailDAOHelper>()
        val mockAltitudeServiceHelper = mockk<AltitudeServiceHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper, mockAltitudeServiceHelper)

        val anyResultList = listOf(mockk<Trail>())

        every { mockTrailsDaoHelper.appendOrFilterOnStartAndFinalPost(any(), anyPostcodeList) } returns mockDocument
        every { mockTrailsDaoHelper.appendEqualFilter(any(), COUNTRY, "Italy") } returns mockDocument
        every { mockTrailsDaoHelper.appendEqualFilter(any(), CODE, "100") } returns mockDocument
        every { mockTrailsDao.executeQueryAndGetResult(mockDocument) } returns anyResultList
        sot.getByTrailPostCodeCountry("100", anyPostcodeList, "Italy")
    }

    @Test
    fun `get trail by postCode and country should correctly address call to DAO`() {
        val mockTrailsDao = mockk<TrailDAO>()
        val mockMetricConverter = mockk<MetricConverter>()
        val mockDocument = mockk<Document>()
        val mockTrailsDaoHelper = mockk<TrailDAOHelper>()
        val mockAltitudeServiceHelper = mockk<AltitudeServiceHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper, mockAltitudeServiceHelper)

        val anyResultList = listOf(mockk<Trail>())

        every { mockTrailsDaoHelper.appendOrFilterOnStartAndFinalPost(any(), anyPostcodeList) } returns mockDocument
        every { mockTrailsDaoHelper.appendEqualFilter(any(), COUNTRY, "Italy") } returns mockDocument
        every { mockTrailsDao.executeQueryAndGetResult(mockDocument) } returns anyResultList
        sot.getByTrailPostCodeCountry("", anyPostcodeList, "Italy")
    }

    @Test
    fun `get trail by country should correctly address call to DAO`() {
        val mockTrailsDao = mockk<TrailDAO>()
        val mockMetricConverter = mockk<MetricConverter>()
        val mockDocument = mockk<Document>()
        val mockTrailsDaoHelper = mockk<TrailDAOHelper>()
        val mockAltitudeServiceHelper = mockk<AltitudeServiceHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper, mockAltitudeServiceHelper)

        val anyResultList = listOf(mockk<Trail>())

        every { mockTrailsDaoHelper.appendEqualFilter(any(), COUNTRY, "Italy") } returns mockDocument
        every { mockTrailsDao.executeQueryAndGetResult(mockDocument) } returns anyResultList
        sot.getByTrailPostCodeCountry("", emptyList(), "Italy")

    }

    @Test
    fun `get geo trail should correctly address call to DAO`() {
        val mockTrailsDao = mockk<TrailDAO>()
        val mockMetricConverter = mockk<MetricConverter>()
        val mockCoordinates = mockk<Coordinates>()
        val mockTrailsDaoHelper = mockk<TrailDAOHelper>()
        val mockAltitudeServiceHelper = mockk<AltitudeServiceHelper>()

        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper, mockAltitudeServiceHelper)

        val expectedLatitudeAndLongitude = 0.0
        every { mockCoordinates.longitude } returns expectedLatitudeAndLongitude
        every { mockCoordinates.latitude } returns expectedLatitudeAndLongitude
        every { mockMetricConverter.getMetersFromKm(50) } returns 50_000
        every { mockTrailsDao.getTrailsByStartPosMetricDistance(expectedLatitudeAndLongitude, expectedLatitudeAndLongitude, 50_000, anyLimit) } returns emptyList()
        every { mockAltitudeServiceHelper.getAltitudeByLongLat(expectedLatitudeAndLongitude, expectedLatitudeAndLongitude) } returns 5.0

        sot.getByGeo(mockCoordinates, 50, UnitOfMeasurement.km, false, anyLimit)
    }
}