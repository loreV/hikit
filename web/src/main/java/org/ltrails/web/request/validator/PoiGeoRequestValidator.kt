package org.ltrails.web.request.validator

import com.google.gson.JsonSyntaxException
import com.google.inject.Inject
import org.ltrails.common.data.helper.GsonBeanHelper
import org.ltrails.common.data.poi.PoiTypes
import org.ltrails.web.controller.PoiController
import org.ltrails.web.request.PoiGeoRequest
import spark.Request

class PoiGeoRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper,
                                                 private val poiTypes: PoiTypes) : Validator<Request> {
    override fun validate(request: Request): List<String> {
        if (request.body().isBlank()) {
            return listOf(Validator.noRequestBodyErrorMessage)
        }
        return try {
            val poiGeoRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), PoiGeoRequest::class.java)
            val listOfErrorMessages = mutableListOf<String>()
            if (poiGeoRequest.coords.longitude > Validator.limitLongLat || poiGeoRequest.coords.longitude < -Validator.limitLongLat) {
                listOfErrorMessages.add(Validator.longitudeValueOutOfBoundErrorMessage)
            }
            if (poiGeoRequest.coords.latitude > Validator.limitLongLat || poiGeoRequest.coords.latitude < -Validator.limitLongLat) {
                listOfErrorMessages.add(Validator.latitudeValueOutOfBoundErrorMessage)
            }

            if (poiGeoRequest.types.isNotEmpty()) {
                if (isNoOnePoiSupported(request.queryParamsValues(PoiController.PARAM_TYPES)))
                    return listOf(Validator.noPoiSupportedErrorMessage)
            }

            listOfErrorMessages
        } catch (e: JsonSyntaxException) {
            listOf(Validator.requestMalformedErrorMessage)
        }

    }

    private fun isNoOnePoiSupported(pois: Array<String>): Boolean {
        return pois.intersect(poiTypes.allPoiTypes).isEmpty()
    }
}