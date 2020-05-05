package org.hikit.importer.validator

import org.hikit.common.data.ConnectingWayPoint
import org.hikit.common.data.validator.Validator

class ConnectingWayPointsCreationValidator : Validator<ConnectingWayPoint> {
    companion object {
        const val connectingWayPointError = "A connecting waypoint set not valid"
    }

    override fun validate(request: ConnectingWayPoint): Set<String> {
        return emptySet()
    }

}