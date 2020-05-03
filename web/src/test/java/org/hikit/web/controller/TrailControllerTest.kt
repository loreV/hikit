package org.hikit.web.controller

import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.hikit.common.data.Coordinates
import org.hikit.common.data.Trail
import org.hikit.common.data.TrailDistance
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.web.controller.response.Status
import org.hikit.web.TrailManager
import org.hikit.web.configuration.ConfigurationManager.ACCEPT_TYPE
import org.hikit.web.request.TrailsGeoRequest
import org.hikit.web.request.validator.TrailGeoRequestValidator
import org.hikit.web.request.validator.TrailRequestValidator
import org.junit.Before
import org.junit.Test
import spark.QueryParamsMap
import spark.Request
import spark.Response

class TrailControllerTest {

    private val anyLimit: Int = 10
    private val italyParam = "Italy"
    private val anyTwoPostcodes = "0001,0002"
    private val anyTrailNumber = "100"

    private var mockResponse: Response = mockk()

    @Before
    fun init() {
        every { mockResponse.type(ACCEPT_TYPE) } returns Unit
    }


    /**
     * All three params
     */
    @Test
    fun `when calling GET with all parameters should call manager accordingly`() {
        val mockRequest = mockk<Request>()

        val mockQueryResponseMap = mockk<QueryParamsMap>()
        val mockQueryResponseMapTrailCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapPostCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapCountry = mockk<QueryParamsMap>()

        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockTrailRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(TrailController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(TrailController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMap.get(TrailController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns anyTwoPostcodes
        every { mockQueryResponseMapTrailCode.value() } returns anyTrailNumber

        val expectedResultTrailList = listOf<Trail>(mockk())
        every { mockTrailManager.getByTrailPostCodeCountry(anyTrailNumber, listOf("0001", "0002"), italyParam) } returns expectedResultTrailList

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.trails == expectedResultTrailList)
    }

    /**
     * Just two params
     */
    @Test
    fun `when calling GET with two params should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockQueryResponseMap = mockk<QueryParamsMap>()
        val mockQueryResponseMapTrailCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapPostCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapCountry = mockk<QueryParamsMap>()

        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockTrailRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(TrailController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(TrailController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMap.get(TrailController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns null
        every { mockQueryResponseMapTrailCode.value() } returns anyTrailNumber

        val expectedResultTrailList = listOf<Trail>(mockk())
        every { mockTrailManager.getByTrailPostCodeCountry(anyTrailNumber, emptyList(), italyParam) } returns expectedResultTrailList

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.trails == expectedResultTrailList)
    }

    /**
     * Only one param
     */
    @Test
    fun `when calling GET with one param only should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockQueryResponseMap = mockk<QueryParamsMap>()
        val mockQueryResponseMapTrailCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapPostCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapCountry = mockk<QueryParamsMap>()

        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockTrailRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(TrailController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(TrailController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMap.get(TrailController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns null
        every { mockQueryResponseMapTrailCode.value() } returns null

        val expectedResultTrailList = listOf<Trail>(mockk())
        every { mockTrailManager.getByTrailPostCodeCountry("", emptyList(), italyParam) } returns expectedResultTrailList

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.trails == expectedResultTrailList)
    }

    /**
     * No param
     */
    @Test
    fun `when validation fail should give error and report messages`() {
        val mockRequest = mockk<Request>()
        val mockResponse = mockk<Response>()

        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        val errorMessages = setOf("Error!")

        every { mockTrailRequestValidator.validate(mockRequest) } returns errorMessages

        val expectedResultTrailList = emptyList<Trail>()

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.contains("Error!"))
        assert(response.status == Status.ERROR)
        assert(response.trails == expectedResultTrailList)
    }


    /**
     *  Geo Request
     */
    @Test
    fun `when calling POST geo correctly should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()
        val requestBody = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 50,\n" +
                "    \"longitude\": 50\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\"\n" +
                "}"

        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val mockGson = mockk<Gson>()
        val mockTrailGeoRequest = mockk<TrailsGeoRequest>()

        val expectedCoordinate = Coordinates(listOf(50.0, 50.0))
        val expectedTrails = listOf(mockk<TrailDistance>())

        every { mockTrailGeoRequest.coords } returns expectedCoordinate
        every { mockTrailGeoRequest.uom } returns UnitOfMeasurement.km
        every { mockTrailGeoRequest.distance } returns 100

        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockTrailGeoRequestValidator.validate(mockRequest) } returns emptySet()
        every { mockRequest.body() } returns requestBody
        every { mockGson.fromJson(requestBody, TrailsGeoRequest::class.java) } returns mockTrailGeoRequest
        every { mockTrailManager.getByGeo(expectedCoordinate, 100, UnitOfMeasurement.km, false, anyLimit) } returns expectedTrails

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.getGeo(mockRequest, mockResponse)

        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.trails == expectedTrails)
    }

    /**
     *  Geo Request with wrong
     */
    @Test
    fun `when calling POST geo with wrong request format should NOT call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockTrailManager = mockk<TrailManager>()
        val mockTrailRequestValidator = mockk<TrailRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<TrailGeoRequestValidator>()

        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockTrailGeoRequestValidator.validate(mockRequest) } returns setOf("error")

        val sot = TrailController(mockTrailManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.getGeo(mockRequest, mockResponse)

        assert(response.messages.contains("error"))
        assert(response.status == Status.ERROR)
        assert(response.trails.isEmpty())

    }


}