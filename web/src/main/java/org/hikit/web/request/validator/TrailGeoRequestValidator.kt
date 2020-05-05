package org.hikit.web.request.validator

import com.google.gson.JsonSyntaxException
import com.google.inject.Inject
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.data.validator.Validator
import org.hikit.common.data.validator.Validator.Companion.noRequestBodyErrorMessage
import org.hikit.common.data.validator.Validator.Companion.requestMalformedErrorMessage
import org.hikit.web.request.TrailsGeoRequest
import spark.Request


class TrailGeoRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request>, CoordinatesValidator {

    override fun validate(request: Request): Set<String> {
        if (request.body().isBlank()) {
            return setOf(noRequestBodyErrorMessage)
        }
        return try {
            val trailGeoRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), TrailsGeoRequest::class.java)
            val listOfErrorMessages = mutableListOf<String>()
            val validateLongitude = validateCoordinates(trailGeoRequest.coords.longitude, CoordinatesValidator.Companion.CoordDimension.LONGITUDE)
            if (validateLongitude.isNotEmpty()) listOfErrorMessages.add(validateLongitude)
            val validateLatitude = validateCoordinates(trailGeoRequest.coords.latitude, CoordinatesValidator.Companion.CoordDimension.LATITUDE)
            if (validateLatitude.isNotEmpty()) listOfErrorMessages.add(validateLatitude)

            listOfErrorMessages.toSet()
        } catch (e: JsonSyntaxException) {
            setOf(requestMalformedErrorMessage)
        }
    }


}