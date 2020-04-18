package org.hikit.web.request.validator

import com.google.inject.Inject
import org.hikit.common.data.poi.PoiTypes
import org.hikit.web.controller.PoiController
import spark.Request

class PoiRequestValidator @Inject constructor(private val poiTypes: PoiTypes) : Validator<Request> {

    private val allParams = listOf(PoiController.PARAM_TRAIL_CODE, PoiController.PARAM_POST_CODE,
            PoiController.PARAM_COUNTRY, PoiController.PARAM_TYPES)

    override fun validate(request: Request): List<String> {
        val queryParams = request.queryParams()
        val intersect = queryParams.intersect(allParams)
        if (intersect.isEmpty()) {
            return listOf(Validator.noParamErrorMessage)
        }

        val isPoiParamSpecified = intersect.contains(PoiController.PARAM_TYPES)
        if (isPoiParamSpecified) {
            if (isNoOnePoiSupported(request.queryParamsValues(PoiController.PARAM_TYPES)))
                return listOf(Validator.noPoiSupportedErrorMessage)
        }
        return emptyList()
    }

    private fun isNoOnePoiSupported(pois: Array<String>): Boolean {
        return pois.intersect(poiTypes.allPoiTypes).isEmpty()
    }


    override fun getParams(request: Request): List<String> {
        return request.queryParams().intersect(allParams).toList()
    }
}