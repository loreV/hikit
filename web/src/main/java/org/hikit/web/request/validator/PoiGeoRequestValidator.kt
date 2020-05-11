package org.hikit.web.request.validator

import com.google.gson.JsonSyntaxException
import com.google.inject.Inject
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.data.poi.PoiTypes
import org.hikit.common.data.validator.CoordinatesValidator
import org.hikit.common.data.validator.CoordinatesValidator.Companion.CoordDimension
import org.hikit.common.data.validator.Validator
import org.hikit.web.request.PoiGeoRequest
import spark.Request

class PoiGeoRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper,
                                                 private val poiTypes: PoiTypes) : Validator<Request>, CoordinatesValidator {
    override fun validate(request: Request): Set<String> {
        if (request.body().isBlank()) {
            return setOf(Validator.noRequestBodyErrorMessage)
        }
        return try {
            val poiGeoRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), PoiGeoRequest::class.java)
            val listOfErrorMessages = mutableSetOf<String>()

            val validateLongitude = validateCoordinates(poiGeoRequest.coords.longitude, CoordDimension.LONGITUDE)
            if (validateLongitude.isNotEmpty()) listOfErrorMessages.add(validateLongitude)
            val validateLatitude = validateCoordinates(poiGeoRequest.coords.latitude, CoordDimension.LATITUDE)
            if (validateLatitude.isNotEmpty()) listOfErrorMessages.add(validateLatitude)

            if (poiGeoRequest.types.isNotEmpty()) {
                if (isNoOnePoiSupported(poiGeoRequest.types))
                    return setOf(Validator.noPoiSupportedErrorMessage)
            }

            listOfErrorMessages.toSet()
        } catch (e: JsonSyntaxException) {
            setOf(Validator.requestMalformedErrorMessage)
        }

    }

    private fun isNoOnePoiSupported(pois: List<String>): Boolean {
        return pois.intersect(poiTypes.allPoiTypes).isEmpty()
    }
}