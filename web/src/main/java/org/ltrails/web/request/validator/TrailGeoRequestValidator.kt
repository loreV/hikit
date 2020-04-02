package org.ltrails.web.request.validator

import spark.Request

class TrailGeoRequestValidator : Validator<Request> {

    override fun validate(request: Request): List<String> {
        return emptyList()
    }


}