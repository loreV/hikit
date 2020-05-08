package org.hikit.importer.validator

import com.google.inject.Inject
import org.hikit.common.data.ConnectingWayPoint
import org.hikit.common.data.validator.Validator

class ConnectingWayPointsCreationValidator @Inject constructor(private val positionCreationValidator: PositionCreationValidator) : Validator<ConnectingWayPoint> {

    override fun validate(request: ConnectingWayPoint): Set<String> {
        val errors = mutableSetOf<String>()

        errors.addAll(positionCreationValidator.validate(request.position))
        return errors
    }

}