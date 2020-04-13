package org.ltrails.web.request.validator

interface Validator<T> {
    companion object {
        const val noParamErrorMessage = "No params specified."
        const val noPoiSupportedErrorMessage = "No requested POIs are available/supported"

        const val limitLongLat = 90
        const val longitudeValueOutOfBoundErrorMessage = "Longitude value out of bound"
        const val latitudeValueOutOfBoundErrorMessage = "Longitude value out of bound"
        const val noRequestBodyErrorMessage = "No request found in method body"
        const val requestMalformedErrorMessage = "The request is malformed"
    }

    fun validate(request: T): List<String>
    fun getParams(request: T): List<String> {
        return emptyList()
    }
}