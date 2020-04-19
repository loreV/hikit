package org.hikit.web.request.validator

interface Validator<T> {
    companion object {
        const val noParamErrorMessage = "No params specified."
        const val noPoiSupportedErrorMessage = "No requested POIs are available/supported"

        const val noRequestBodyErrorMessage = "No request found in method body"
        const val requestMalformedErrorMessage = "The request is malformed"
    }

    fun validate(request: T): Set<String>
    fun getParams(request: T): List<String> {
        return emptyList()
    }
}