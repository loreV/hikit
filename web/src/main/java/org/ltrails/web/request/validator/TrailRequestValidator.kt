package org.ltrails.web.request.validator

import org.ltrails.web.controller.TrailController.*
import spark.Request

class TrailRequestValidator : Validator<Request> {

    private val allParams = listOf(PARAM_TRAIL_CODE, PARAM_POST_CODE, PARAM_COUNTRY)

    override fun validate(request: Request): List<String> {
        val queryParams = request.queryParams()
        val intersect = queryParams.intersect(allParams)
        if (intersect.isEmpty()) {
            return listOf("No params specified.")
        }

        return emptyList()
    }

    override fun getParams(request: Request): List<String> {
        return request.queryParams().intersect(allParams).toList()
    }


}