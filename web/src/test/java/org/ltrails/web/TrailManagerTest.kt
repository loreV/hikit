package org.ltrails.web

import io.mockk.every
import io.mockk.mockk
import org.bson.Document
import org.junit.Test
import org.ltrails.common.converter.MetricConverter
import org.ltrails.common.data.Trail
import org.ltrails.common.data.Trail.CODE
import org.ltrails.common.data.Trail.COUNTRY
import org.ltrails.common.data.TrailDAO

class TrailManagerTest {

    private val anyPostcodeList = listOf("00001", "00002")

    @Test
    fun `get by trail, postCode and country should correctly address call to DAO`() {
        val mockTrailsDao = mockk<TrailDAO>()
        val mockMetricConverter = mockk<MetricConverter>()
        val mockDocument = mockk<Document>()
        val mockTrailsDaoHelper = mockk<TrailDaoHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper)

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
        val mockTrailsDaoHelper = mockk<TrailDaoHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper)

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
        val mockTrailsDaoHelper = mockk<TrailDaoHelper>()
        val sot = TrailManager(mockTrailsDao, mockMetricConverter, mockTrailsDaoHelper)

        val anyResultList = listOf(mockk<Trail>())

        every { mockTrailsDaoHelper.appendEqualFilter(any(), COUNTRY, "Italy") } returns mockDocument
        every { mockTrailsDao.executeQueryAndGetResult(mockDocument) } returns anyResultList
        sot.getByTrailPostCodeCountry("", emptyList(), "Italy")

    }

    @Test
    fun `get geo trail should correctly address call to DAO`() {
        // TODO
    }
}