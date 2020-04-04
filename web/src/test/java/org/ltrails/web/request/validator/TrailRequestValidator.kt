package org.ltrails.web.request.validator

import io.mockk.every
import io.mockk.mockkClass
import org.junit.Test
import org.ltrails.web.controller.TrailController
import spark.Request

class TrailRequestValidatorTest {

    @Test
    fun `if not specified message should provide error message`() {
        val mockRequest = mockkClass(Request::class)
        every { mockRequest.queryParams() } returns HashSet()
        val sot = TrailRequestValidator()
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages[0] == TrailRequestValidator.noParamErrorMessage)
    }

    @Test
    fun `if specified message does not contain valid params should provide error messages`() {
        val mockRequest = mockkClass(Request::class)
        val someParams = HashSet<String>()
        someParams.add("a param")
        someParams.add("another param")
        every { mockRequest.queryParams() } returns someParams
        val sot = TrailRequestValidator()
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.size == 1)
        assert(errorMessages[0] == TrailRequestValidator.noParamErrorMessage)
    }

    @Test
    fun `if specified message does contain at least one valid param should not provide error messages`() {
        val mockRequest = mockkClass(Request::class)
        val hashSet = HashSet<String>()
        hashSet.add(TrailController.PARAM_TRAIL_CODE)
        every { mockRequest.queryParams() } returns hashSet
        val sot = TrailRequestValidator()
        val errorMessages = sot.validate(mockRequest)

        assert(errorMessages.isEmpty())
    }


}