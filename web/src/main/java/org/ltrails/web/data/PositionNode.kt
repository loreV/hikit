package org.ltrails.web.data

import org.ltrails.common.data.Position
import org.ltrails.common.data.Trail


class PositionNode(val costSoFar: Double,
                   val heuristicsCost: Double,
                   val position: Position,
                   val trail: Trail,
                   val parent: PositionNode?) {
    fun isTopParent() = parent == null
}