package org.hikit.importer.validator

import org.hikit.common.data.Position
import org.hikit.common.data.validator.Validator

class PositionCreationValidator : Validator<Position> {
    override fun validate(request: Position): Set<String> {
        return emptySet()
    }

}