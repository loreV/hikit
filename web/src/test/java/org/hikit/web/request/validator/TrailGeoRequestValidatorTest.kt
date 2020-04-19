package org.hikit.web.request.validator

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.mockk.every
import io.mockk.mockk
import org.hikit.common.data.Coordinates
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.web.request.TrailsGeoRequest
import org.junit.Test
import spark.Request

class TrailGeoRequestValidatorTest {

    @Test
    fun `if longitude oob then should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val sot = TrailGeoRequestValidator(mockGsonBeanHelper)
        val anyRequestWNotValidLat = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": -100,\n" +
                "    \"longitude\": 0\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\"\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLat

        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLat, TrailsGeoRequest::class.java) } returns
                TrailsGeoRequest(Coordinates(-100.0, 0.0), 100, UnitOfMeasurement.km)

        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(CoordinatesValidator.longitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if latitude oob then should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val anyRequestWNotValidLon = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 0,\n" +
                "    \"longitude\": -100\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\"\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLon

        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLon, TrailsGeoRequest::class.java) } returns
                TrailsGeoRequest(Coordinates(0.0, -100.0), 100, UnitOfMeasurement.km)

        val sot = TrailGeoRequestValidator(mockGsonBeanHelper)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(CoordinatesValidator.latitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if both longitude and latitude oob then should provide error messages`() {
        val mockRequest = mockk<Request>()
        val mockGson = mockk<Gson>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val anyRequestWNotValidLatLong = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 100,\n" +
                "    \"longitude\": -100\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\"\n" +
                "}"
        every { mockRequest.body() } returns anyRequestWNotValidLatLong

        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestWNotValidLatLong, TrailsGeoRequest::class.java) } returns
                TrailsGeoRequest(Coordinates(100.0, -100.0), 100, UnitOfMeasurement.km)

        val sot = TrailGeoRequestValidator(mockGsonBeanHelper)
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
        val anyRequestNotValid = "i am not well-formed"

        every { mockRequest.body() } returns anyRequestNotValid
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockGson.fromJson(anyRequestNotValid, TrailsGeoRequest::class.java) } throws JsonSyntaxException("")

        val sot = TrailGeoRequestValidator(mockGsonBeanHelper)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.requestMalformedErrorMessage))

    }

    @Test
    fun `if empty request should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        every { mockRequest.body() } returns ""

        val sot = TrailGeoRequestValidator(mockGsonBeanHelper)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.noRequestBodyErrorMessage))
    }
}