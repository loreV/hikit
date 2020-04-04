package org.ltrails.web.request.validator

import com.google.gson.JsonSyntaxException
import org.ltrails.common.data.helper.GsonBeanHelper
import org.ltrails.web.request.TrailsGeoRequest
import spark.Request

class TrailGeoRequestValidator constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request> {

    companion object {
        const val limitLongLat = 90

        const val longitudeValueOutOfBoundErrorMessage = "Longitude value out of bound"
        const val latitudeValueOutOfBoundErrorMessage = "Longitude value out of bound"
        const val noRequestBodyErrorMessage = "No request found in method body"
        const val requestMalformedErrorMessage = "The request is malformed"
    }

    override fun validate(request: Request): List<String> {
        if (request.body().isBlank()) {
            return listOf(noRequestBodyErrorMessage)
        }
        return try {
            val trailGeoRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), TrailsGeoRequest::class.java)
            val listOfErrorMessages = mutableListOf<String>()
            if (trailGeoRequest.coords.longitude > limitLongLat || trailGeoRequest.coords.longitude < -limitLongLat) {
                listOfErrorMessages.add(longitudeValueOutOfBoundErrorMessage)
            }
            if (trailGeoRequest.coords.latitude > limitLongLat || trailGeoRequest.coords.latitude < -limitLongLat) {
                listOfErrorMessages.add(latitudeValueOutOfBoundErrorMessage)
            }
            listOfErrorMessages
        } catch (e: JsonSyntaxException) {
            listOf(requestMalformedErrorMessage)
        }
    }


}