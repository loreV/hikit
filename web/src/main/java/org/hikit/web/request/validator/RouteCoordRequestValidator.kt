package org.hikit.web.request.validator

import com.google.gson.JsonSyntaxException
import com.google.inject.Inject
import org.hikit.common.data.helper.GsonBeanHelper
import org.hikit.common.data.validator.CoordinatesValidator
import org.hikit.common.data.validator.Validator
import org.hikit.web.request.RoutePlanRequest
import spark.Request

class RouteCoordRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request>, CoordinatesValidator {
    override fun validate(request: Request): Set<String> {
        if (request.body().isBlank()) {
            return setOf(Validator.noRequestBodyErrorMessage)
        }
        return try {
            val routeCoordRequest = gsonBeanHelper.gsonBuilder!!.fromJson(request.body(), RoutePlanRequest::class.java)
            val listOfErrorMessages = mutableListOf<String>()

            val validateStartLongitude = validateCoordinates(routeCoordRequest.startPos.coordinates.longitude, CoordinatesValidator.Companion.CoordDimension.LONGITUDE)
            if (validateStartLongitude.isNotEmpty()) listOfErrorMessages.add(validateStartLongitude)
            val validateDestLongitude = validateCoordinates(routeCoordRequest.finalPos.coordinates.longitude, CoordinatesValidator.Companion.CoordDimension.LONGITUDE)
            if (validateDestLongitude.isNotEmpty()) listOfErrorMessages.add(validateDestLongitude)

            val validateStartLatitude = validateCoordinates(routeCoordRequest.startPos.coordinates.latitude, CoordinatesValidator.Companion.CoordDimension.LATITUDE)
            if (validateStartLatitude.isNotEmpty()) listOfErrorMessages.add(validateStartLatitude)
            val validateDestLatitude = validateCoordinates(routeCoordRequest.startPos.coordinates.latitude, CoordinatesValidator.Companion.CoordDimension.LATITUDE)
            if (validateDestLatitude.isNotEmpty()) listOfErrorMessages.add(validateDestLatitude)

            listOfErrorMessages.toSet()
        } catch (e: JsonSyntaxException) {
            setOf(Validator.requestMalformedErrorMessage)
        }
    }

}