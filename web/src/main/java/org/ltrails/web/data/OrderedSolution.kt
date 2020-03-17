package org.ltrails.web.data

class OrderedSolution constructor(val trailPositionPath: SuccessorPosition) : Comparable<OrderedSolution> {

    private var f: Double = 0.0

    override fun compareTo(other: OrderedSolution): Int = if (this.f < other.f) -1 else 1

    fun getParentNode(): SuccessorPosition =
            if (trailPositionPath.isTopParent()) trailPositionPath
            else trailPositionPath.parent!!

}
