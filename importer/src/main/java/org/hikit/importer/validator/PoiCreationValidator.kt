package org.hikit.importer.validator

import org.hikit.common.data.Poi
import org.hikit.common.data.validator.Validator

class PoiCreationValidator : Validator<Poi> {

    companion object {
        const val poiError = "A POI set not valid"
    }

    override fun validate(request: Poi): Set<String> {
        return emptySet()
    }

}