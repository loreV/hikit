package org.hikit.common.data.validator

class CountryValidator : Validator<String> {
    companion object {
        val countryList = setOf("italy")
        const val countryNotRecognisedError = "Country '%s' not recognized"
    }

    override fun validate(request: String): Set<String> {
        if (!countryList.contains(request.toLowerCase())) {
            setOf(countryNotRecognisedError)
        }
        return emptySet()
    }
}