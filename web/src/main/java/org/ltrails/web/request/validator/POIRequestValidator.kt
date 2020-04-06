package org.ltrails.web.request.validator

import com.google.inject.Inject
import org.ltrails.common.data.helper.GsonBeanHelper
import spark.Request

class POIRequestValidator @Inject constructor(private val gsonBeanHelper: GsonBeanHelper) : Validator<Request> {
    override fun validate(request: Request): List<String> {
        TODO("Not yet implemented")
    }
}