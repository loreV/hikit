package org.ltrails.web.data

import org.ltrails.common.data.Position
import org.ltrails.common.data.Trail


class SuccessorPosition(val costSoFar: Double,
                        val heuristicsCost: Double,
                        val position: Position,
                        val trail: Trail,
                        val parent: SuccessorPosition?) {
    fun isTopParent() = parent == null
}