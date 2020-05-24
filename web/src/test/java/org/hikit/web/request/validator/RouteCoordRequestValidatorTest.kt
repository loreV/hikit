package org.hikit.web.request.validator

import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.hikit.common.data.Coordinates
import org.hikit.common.data.CoordinatesDelta
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.data.validator.CoordinatesValidator
import org.hikit.common.data.validator.Validator
import org.hikit.web.request.RoutePlanRequest
import org.junit.Test
import spark.Request

class RouteCoordRequestValidatorTest {

    @Test
    fun `when request has lat out of bound, should fail`() {
        val mockGson = mockk<Gson>()
        val mockRoutePlannRequest = mockk<RoutePlanRequest>()
        val mockCoordinatesDelta = mockk<CoordinatesDelta>()
        val mockCoordinates = mockk<Coordinates>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val request = mockk<Request>()
        val requestBody = "{\n" +
                "  \"startPos\": {\n" +
                "    \"coordinates\": {\n" +
                "      \"latitude\": -100,\n" +
                "      \"longitude\": 0\n" + // out of bound
                "    },\n" +
                "    \"deltaDistance\": 0,\n" +
                "    \"uom\": \"m\"\n" +
                "  },\n" +
                "  \"finalPos\": {\n" +
                "    \"coordinates\": {\n" +
                "      \"latitude\": -100,\n" +
                "      \"longitude\": 0\n" +
                "    },\n" +
                "    \"deltaDistance\": 0,\n" +
                "    \"uom\": \"m\"\n" +
                "  }\n" +
                "}"
        every { request.body() } returns requestBody
        every { mockGson.fromJson(requestBody, RoutePlanRequest::class.java) } returns mockRoutePlannRequest
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockRoutePlannRequest.startPos } returns mockCoordinatesDelta
        every { mockRoutePlannRequest.finalPos } returns mockCoordinatesDelta // equal for shortening tests
        every { mockCoordinatesDelta.coordinates } returns mockCoordinates
        every { mockCoordinates.longitude } returns 0.0
        every { mockCoordinates.latitude } returns -100.0

        val sot = RouteCoordRequestValidator(mockGsonBeanHelper)

        val errorMessages = sot.validate(request)

        assert(errorMessages.isNotEmpty())
        assert(errorMessages.contains(CoordinatesValidator.latitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `when request has long out of bound, should fail`() {
        val mockGson = mockk<Gson>()
        val mockRoutePlannRequest = mockk<RoutePlanRequest>()
        val mockCoordinatesDelta = mockk<CoordinatesDelta>()
        val mockCoordinates = mockk<Coordinates>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val request = mockk<Request>()
        val requestBody = "{\n" +
                "  \"startPos\": {\n" +
                "    \"coordinates\": {\n" +
                "      \"latitude\": 0,\n" +
                "      \"longitude\": -100\n" + // out of bound
                "    },\n" +
                "    \"deltaDistance\": 0,\n" +
                "    \"uom\": \"m\"\n" +
                "  },\n" +
                "  \"finalPos\": {\n" +
                "    \"coordinates\": {\n" +
                "      \"latitude\": 0,\n" +
                "      \"longitude\": -200\n" +
                "    },\n" +
                "    \"deltaDistance\": 0,\n" +
                "    \"uom\": \"m\"\n" +
                "  }\n" +
                "}"
        every { request.body() } returns requestBody
        every { mockGson.fromJson(requestBody, RoutePlanRequest::class.java) } returns mockRoutePlannRequest
        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockRoutePlannRequest.startPos } returns mockCoordinatesDelta
        every { mockRoutePlannRequest.finalPos } returns mockCoordinatesDelta // equal for shortening tests
        every { mockCoordinatesDelta.coordinates } returns mockCoordinates
        every { mockCoordinates.longitude } returns -200.00
        every { mockCoordinates.latitude } returns 0.0

        val sot = RouteCoordRequestValidator(mockGsonBeanHelper)

        val errorMessages = sot.validate(request)

        assert(errorMessages.isNotEmpty())
        assert(errorMessages.contains(CoordinatesValidator.longitudeValueOutOfBoundErrorMessage))
    }

    @Test
    fun `if empty request should provide error message`() {
        val mockRequest = mockk<Request>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        every { mockRequest.body() } returns ""

        val sot = RouteCoordRequestValidator(mockGsonBeanHelper)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages.contains(Validator.noRequestBodyErrorMessage))
    }


}