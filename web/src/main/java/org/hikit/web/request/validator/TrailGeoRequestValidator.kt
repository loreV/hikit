package org.hikit.web.request.validator

import com.google.gson.JsonSyntaxException
import com.google.inject.Inject
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.web.request.TrailsGeoRequest
import org.hikit.web.request.validator.Validator.Companion.latitudeValueOutOfBoundErrorMessage
import org.hikit.web.request.validator.Validator.Companion.limitLongLat
import org.hikit.web.request.validator.Validator.Companion.longitudeValueOutOfBoundErrorMessage
import org.hikit.web.request.validator.Validator.Companion.noRequestBodyErrorMessage
import org.hikit.web.request.validator.Validator.Companion.requestMalformedErrorMessage
import spark.Request


class TrailGeoRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request> {

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