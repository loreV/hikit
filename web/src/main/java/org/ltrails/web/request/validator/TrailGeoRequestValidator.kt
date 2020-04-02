package org.ltrails.web.request.validator

import com.google.gson.JsonSyntaxException
import org.ltrails.common.data.helper.GsonBeanHelper
import org.ltrails.web.request.TrailsGeoRequest
import spark.Request

class TrailGeoRequestValidator constructor(val gsonBeanHelper: GsonBeanHelper) : Validator<Request> {


    override fun validate(request: Request): List<String> {
        if (request.body().isBlank()) {
            return listOf("No request found in method body")
        }
        // TODO: do not un-serialize this object twice
        try {
            gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), TrailsGeoRequest::class.java)
        } catch (e: JsonSyntaxException) {
            return listOf("The request is malformed")
        }
        return emptyList()
    }


}