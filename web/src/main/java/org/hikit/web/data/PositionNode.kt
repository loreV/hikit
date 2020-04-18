package org.hikit.web.data

import org.hikit.common.data.Position
import org.hikit.common.data.Trail


class PositionNode(val costSoFar: Double,
                   val heuristicsCost: Double,
                   val position: Position,
                   val trail: Trail,
                   val parent: PositionNode?) {
    fun isTopParent() = parent == null
}