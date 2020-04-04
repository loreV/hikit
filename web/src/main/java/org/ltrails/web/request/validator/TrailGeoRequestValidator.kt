package org.ltrails.web.request.validator

import com.google.gson.JsonSyntaxException
import org.ltrails.common.data.helper.GsonBeanHelper
import org.ltrails.web.request.TrailsGeoRequest
import spark.Request

class TrailGeoRequestValidator constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request> {


    override fun validate(request: Request): List<String> {
        if (request.body().isBlank()) {
            return listOf("No request found in method body")
        }
        return try {
            val trailGeoRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), TrailsGeoRequest::class.java)
            val listOfErrorMessages = mutableListOf<String>()
            if (trailGeoRequest.coords.longitude > 90 || trailGeoRequest.coords.longitude < -90) {
                listOfErrorMessages.add("Longitude value out of bound")
            }
            if (trailGeoRequest.coords.latitude > 90 || trailGeoRequest.coords.latitude < -90) {
                listOfErrorMessages.add("Latitude value out of bound")
            }
            listOfErrorMessages
        } catch (e: JsonSyntaxException) {
            listOf("The request is malformed")
        }
    }


}