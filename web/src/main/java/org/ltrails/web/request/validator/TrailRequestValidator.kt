package org.ltrails.web.request.validator

import spark.Request

class TrailRequestValidator : Validator<Request> {

    val PARAM_TRAIL_CODE = "trailCode"
    val PARAM_POST_CODE = "postCode"
    val PARAM_COUNTRY = "country"

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