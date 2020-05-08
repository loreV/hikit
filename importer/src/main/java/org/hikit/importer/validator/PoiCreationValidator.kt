package org.hikit.importer.validator

import org.hikit.common.data.Poi
import org.hikit.common.data.validator.Validator

class PoiCreationValidator : Validator<Poi> {
    // TODO in H0019
    override fun validate(request: Poi): Set<String> {
        return emptySet()
    }

}