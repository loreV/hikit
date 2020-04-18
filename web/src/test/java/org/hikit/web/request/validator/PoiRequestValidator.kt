package org.hikit.web.request.validator

import io.mockk.every
import io.mockk.mockkClass
import org.hikit.common.data.poi.PoiTypes
import org.hikit.web.controller.PoiController
import org.junit.Test
import spark.Request

class PoiRequestValidatorTest {

    private val anyPoiType = "any"

    @Test
    fun `if not specified message should provide error message`() {
        val mockRequest = mockkClass(Request::class)
        val mockPoisTypes = mockkClass(PoiTypes::class)
        every { mockRequest.queryParams() } returns HashSet()
        every { mockPoisTypes.allPoiTypes } returns setOf(anyPoiType)
        val sot = PoiRequestValidator(mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages[0] == Validator.noParamErrorMessage)
    }

    @Test
    fun `if request does not contain valid params should cause error messages`() {
        val mockRequest = mockkClass(Request::class)
        val someParams = HashSet<String>()
        val mockPoisTypes = mockkClass(PoiTypes::class)
        every { mockPoisTypes.allPoiTypes } returns setOf(anyPoiType)
        someParams.add("a param")
        someParams.add("another param")
        every { mockRequest.queryParams() } returns someParams
        val sot = PoiRequestValidator(mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages[0] == Validator.noParamErrorMessage)
    }

    @Test
    fun `if specified message does contain at least one valid param should not cause error messages`() {
        val mockRequest = mockkClass(Request::class)
        val hashSet = HashSet<String>()
        hashSet.add(PoiController.PARAM_TRAIL_CODE)
        val mockPoisTypes = mockkClass(PoiTypes::class)
        every { mockPoisTypes.allPoiTypes } returns setOf(anyPoiType)
        every { mockRequest.queryParams() } returns hashSet
        val sot = PoiRequestValidator(mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.isEmpty())
    }

    @Test
    fun `if request contains a POI param, but not listed should cause error messages`() {
        val mockRequest = mockkClass(Request::class)
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val someParams = HashSet<String>()
        every { mockPoisTypes.allPoiTypes } returns setOf("Castle")
        someParams.add(PoiController.PARAM_TYPES)
        every { mockRequest.queryParams() } returns someParams
        every { mockRequest.queryParamsValues(PoiController.PARAM_TYPES) } returns arrayOf("i do not exist!")
        val sot = PoiRequestValidator(mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages[0] == Validator.noPoiSupportedErrorMessage)
    }

    @Test
    fun `if request contains a POI param and listed should validate`() {
        val mockRequest = mockkClass(Request::class)
        val mockPoisTypes = mockkClass(PoiTypes::class)
        val someParams = HashSet<String>()
        every { mockPoisTypes.allPoiTypes } returns setOf("Castle")
        someParams.add(PoiController.PARAM_TYPES)
        every { mockRequest.queryParams() } returns someParams
        every { mockRequest.queryParamsValues(PoiController.PARAM_TYPES) } returns arrayOf("Castle")
        val sot = PoiRequestValidator(mockPoisTypes)
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.isEmpty())
    }


}