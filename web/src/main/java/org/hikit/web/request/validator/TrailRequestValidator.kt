package org.hikit.web.request.validator

import org.hikit.common.data.validator.Validator
import org.hikit.common.data.validator.Validator.Companion.noParamErrorMessage
import org.hikit.web.controller.TrailController.*
import spark.Request

class TrailRequestValidator : Validator<Request> {

    private val allParams = listOf(PARAM_TRAIL_CODE, PARAM_POST_CODE, PARAM_COUNTRY)

    override fun validate(request: Request): Set<String> {
        val queryParams = request.queryParams()
        val intersect = queryParams.intersect(allParams)
        if (intersect.isEmpty()) {
            return setOf(noParamErrorMessage)
        }

        return emptySet()
    }

    override fun getParams(request: Request): List<String> {
        return request.queryParams().intersect(allParams).toList()
    }


}