package org.hikit.web.controller

import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.hikit.common.data.Coordinates
import org.hikit.common.data.Poi
import org.hikit.common.data.Trail
import org.hikit.common.data.UnitOfMeasurement
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.response.Status
import org.hikit.web.PoiManager
import org.hikit.web.configuration.ConfigurationManager
import org.hikit.web.request.PoiGeoRequest
import org.hikit.web.request.validator.PoiGeoRequestValidator
import org.hikit.web.request.validator.PoiRequestValidator
import org.junit.Before
import org.junit.Test
import spark.QueryParamsMap
import spark.Request
import spark.Response

class PoiControllerTest {

    private val italyParam = "Italy"
    private val anyTwoPostcodes = "0001,0002"
    private val anyTrailNumber = "100"
    private val anyType = "monument,church"

    private var mockResponse: Response = mockk()

    @Before
    fun init() {
        every { mockResponse.type(ConfigurationManager.ACCEPT_TYPE) } returns Unit
    }

    /**
     * All three params
     */
    @Test
    fun `when calling GET with all parameters should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockQueryResponseMap = mockk<QueryParamsMap>()
        val mockQueryResponseMapTrailCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapTypes = mockk<QueryParamsMap>()
        val mockQueryResponseMapPostCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapCountry = mockk<QueryParamsMap>()

        val mockPoiManager = mockk<PoiManager>()
        val mockPoiRequestValidator = mockk<PoiRequestValidator>()
        val mockPoiGeoRequestValidator = mockk<PoiGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockPoiRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(PoiController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(PoiController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMap.get(PoiController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMap.get(PoiController.PARAM_TYPES) } returns mockQueryResponseMapTypes
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns anyTwoPostcodes
        every { mockQueryResponseMapTrailCode.value() } returns anyTrailNumber
        every { mockQueryResponseMapTypes.value() } returns anyType

        val expectedResultPoiList = listOf<Poi>(mockk())
        every { mockPoiManager.getByTrailPostCodeCountryType(anyTrailNumber, listOf("0001", "0002"), italyParam, listOf("monument", "church")) } returns expectedResultPoiList

        val sot = PoiController(mockPoiManager, mockPoiRequestValidator, mockPoiGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.pois == expectedResultPoiList)
    }

    /**
     * Just two params
     */
    @Test
    fun `when calling GET with two params should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockQueryResponseMap = mockk<QueryParamsMap>()
        val mockQueryResponseMapTrailCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapTypesCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapPostCode = mockk<QueryParamsMap>()
        val mockQueryResponseMapCountry = mockk<QueryParamsMap>()

        val mockPoiManager = mockk<PoiManager>()
        val mockPoiRequestValidator = mockk<PoiRequestValidator>()
        val mockPoiGeoRequestValidator = mockk<PoiGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockPoiRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(PoiController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(PoiController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMap.get(PoiController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMap.get(PoiController.PARAM_TYPES) } returns mockQueryResponseMapTypesCode
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns null
        every { mockQueryResponseMapTrailCode.value() } returns anyTrailNumber
        every { mockQueryResponseMapTypesCode.value() } returns null

        val expectedResultTrailList = listOf<Poi>(mockk())
        every { mockPoiManager.getByTrailPostCodeCountryType(anyTrailNumber, emptyList(), italyParam, emptyList()) } returns expectedResultTrailList

        val sot = PoiController(mockPoiManager, mockPoiRequestValidator, mockPoiGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.pois == expectedResultTrailList)
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
        val mockQueryResponseMapTypesCode = mockk<QueryParamsMap>()

        val mockPoiManager = mockk<PoiManager>()
        val mockTrailRequestValidator = mockk<PoiRequestValidator>()
        val mockTrailGeoRequestValidator = mockk<PoiGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockTrailRequestValidator.validate(mockRequest) } returns emptySet()

        every { mockRequest.queryMap() } returns mockQueryResponseMap

        every { mockQueryResponseMap.get(PoiController.PARAM_TRAIL_CODE) } returns mockQueryResponseMapTrailCode
        every { mockQueryResponseMap.get(PoiController.PARAM_COUNTRY) } returns mockQueryResponseMapCountry
        every { mockQueryResponseMap.get(PoiController.PARAM_POST_CODE) } returns mockQueryResponseMapPostCode
        every { mockQueryResponseMap.get(PoiController.PARAM_TYPES) } returns mockQueryResponseMapTypesCode
        every { mockQueryResponseMapCountry.value() } returns italyParam
        every { mockQueryResponseMapPostCode.value() } returns null
        every { mockQueryResponseMapTrailCode.value() } returns null
        every { mockQueryResponseMapTypesCode.value() } returns null

        val expectedResultTrailList = listOf<Poi>(mockk())
        every { mockPoiManager.getByTrailPostCodeCountryType("", emptyList(), italyParam, emptyList()) } returns expectedResultTrailList

        val sot = PoiController(mockPoiManager, mockTrailRequestValidator, mockTrailGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.pois == expectedResultTrailList)
    }

    /**
     * No param
     */
    @Test
    fun `when validation fail should give error and report messages`() {
        val mockRequest = mockk<Request>()

        val mockPoiManager = mockk<PoiManager>()
        val mockPoiRequestValidator = mockk<PoiRequestValidator>()
        val mockPoiGeoRequestValidator = mockk<PoiGeoRequestValidator>()
        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        val errorMessages = setOf("Error!")

        every { mockPoiRequestValidator.validate(mockRequest) } returns errorMessages

        val expectedResultTrailList = emptyList<Trail>()

        val sot = PoiController(mockPoiManager, mockPoiRequestValidator, mockPoiGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.get(mockRequest, mockResponse)
        assert(response.messages.contains("Error!"))
        assert(response.status == Status.ERROR)
        assert(response.pois == expectedResultTrailList)
    }


    /**
     *  Geo Request
     */
    @Test
    fun `when calling POST geo correctly should call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockPoiManager = mockk<PoiManager>()
        val mockPoiRequestValidator = mockk<PoiRequestValidator>()
        val mockPoiGeoRequestValidator = mockk<PoiGeoRequestValidator>()
        val requestBody = "{\n" +
                "  \"coords\": {\n" +
                "    \"latitude\": 11.301413,\n" +
                "    \"longitude\": 44.486066\n" +
                "  },\n" +
                "  \"distance\": 100,\n" +
                "  \"uom\": \"km\",\n" +
                "  \"types\": [\"monument\", \"fountain\"]\n" +
                "}"

        val mockGsonBeanHelper = mockk<GsonBeanHelper>()
        val mockGson = mockk<Gson>()
        val mockPoiGeoRequest = mockk<PoiGeoRequest>()

        val expectedCoordinate = Coordinates(50.0, 50.0)
        val expectedPois = listOf(mockk<Poi>())
        val expectedTypes = listOf("monument", "fountain")

        every { mockPoiGeoRequest.coords } returns expectedCoordinate
        every { mockPoiGeoRequest.uom } returns UnitOfMeasurement.km
        every { mockPoiGeoRequest.distance } returns 100
        every { mockPoiGeoRequest.types } returns expectedTypes

        every { mockGsonBeanHelper.gsonBuilder } returns mockGson
        every { mockPoiGeoRequestValidator.validate(mockRequest) } returns emptySet()
        every { mockRequest.body() } returns requestBody
        every { mockGson.fromJson(requestBody, PoiGeoRequest::class.java) } returns mockPoiGeoRequest
        every { mockPoiManager.getByGeo(expectedCoordinate, 100, UnitOfMeasurement.km, expectedTypes) } returns expectedPois

        val sot = PoiController(mockPoiManager, mockPoiRequestValidator, mockPoiGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.getGeo(mockRequest, mockResponse)

        assert(response.messages.isEmpty())
        assert(response.status == Status.OK)
        assert(response.pois == expectedPois)
    }

    /**
     *  Geo Request with wrong
     */
    @Test
    fun `when calling POST geo with wrong request format should NOT call manager accordingly`() {
        val mockRequest = mockk<Request>()
        val mockPoiManager = mockk<PoiManager>()
        val mockPoiRequestValidator = mockk<PoiRequestValidator>()
        val mockPoiGeoRequestValidator = mockk<PoiGeoRequestValidator>()

        val mockGsonBeanHelper = mockk<GsonBeanHelper>()

        every { mockPoiGeoRequestValidator.validate(mockRequest) } returns setOf("error")

        val sot = PoiController(mockPoiManager, mockPoiRequestValidator, mockPoiGeoRequestValidator, mockGsonBeanHelper)
        val response = sot.getGeo(mockRequest, mockResponse)

        assert(response.messages.contains("error"))
        assert(response.status == Status.ERROR)
        assert(response.pois.isEmpty())

    }


}