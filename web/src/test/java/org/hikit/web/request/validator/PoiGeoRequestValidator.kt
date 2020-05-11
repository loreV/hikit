package org.hikit.web.request.validator

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.hikit.common.data.Coordinates
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.data.poi.PoiTypes
import org.hikit.common.data.validator.CoordinatesValidator
import org.hikit.common.data.validator.Validator
import org.hikit.web.controller.PoiController
import org.hikit.web.request.PoiGeoRequest
import org.junit.Test
import spark.Request

class PoiGeoRequestValidatorTest {
    @Test
    fun `if latitude oob then should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val anyRequestWNotValidLat = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 0.0,\n" +
                "    \"longitude\": -100.0\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\",\n" +
                "  \"types\": []\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLat
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLat, PoiGeoRequest::class.java) } returns
                PoiGeoRequest(Coordinates(listOf(0.0, -100.0)), 100,
                        UnitOfMeasurement.km, emptyList())

        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(CoordinatesValidator.latitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if longitude oob then should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val anyRequestWNotValidLon = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 0,\n" +
                "    \"longitude\"-100\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\",\n" +
                "  \"types\": []\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLon
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLon, PoiGeoRequest::class.java) } returns
                PoiGeoRequest(Coordinates(listOf(-100.0, 0.0)), 100,
                        UnitOfMeasurement.km, emptyList())

        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(CoordinatesValidator.longitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if both longitude and latitude oob then should provide error messages`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val anyRequestWNotValidLatLong = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": -100.0,\n" +
                "    \"longitude\": 100.0\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\",\n" +
                "  \"types\": []\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLatLong
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLatLong, PoiGeoRequest::class.java) } returns
                PoiGeoRequest(Coordinates(listOf(100.0, -100.0)), 100,
                        UnitOfMeasurement.km, emptyList())

        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 2)
        assert(errorMessages.contains(CoordinatesValidator.longitudeValueOutOfBoundErrorMessage))
        assert(errorMessages.contains(CoordinatesValidator.latitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if wrongly formatted request should provide error messages`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val anyRequestNotValid = "i am not well-formed"
        every { mockRequest.body() } returns anyRequestNotValid
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestNotValid, PoiGeoRequest::class.java) } throws JsonSyntaxException("")

        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.requestMalformedErrorMessage))

    }

    @Test
    fun `if empty request should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        every { mockRequest.queryParamsValues(PoiController.PARAM_TYPES) } returns emptyArray()
        every { mockRequest.body() } returns ""

        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.noRequestBodyErrorMessage))
    }

    @Test
    fun `if request contains POI params but no valid values should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val sot = PoiGeoRequestValidator(mockGsonBeanHelper, mockPoisTypes)
        val anyRequestWNotValidLat = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 0.0,\n" +
                "    \"longitude\": 90.0\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\",\n" +
                "  \"types\": ['castle']\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLat
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockPoisTypes.allPoiTypes } returns setOf("castle")
        every { mockGson.fromJson(anyRequestWNotValidLat, PoiGeoRequest::class.java) } returns
                PoiGeoRequest(Coordinates(listOf(90.0, 0.0)), 100,
                        UnitOfMeasurement.km, listOf("notExistingPoi"))

        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.noPoiSupportedErrorMessage))
    }


}