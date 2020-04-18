package org.hikit.web.data

class OrderedSolution(val trailPositionNodePath: PositionNode) : Comparable<OrderedSolution> {

    override fun compareTo(other: OrderedSolution): Int = if (this.f() < other.f()) -1 else 1

    fun size(): Int {
        var s = 1
        var node = trailPositionNodePath
        while (!node.isTopParent()) {
            node = trailPositionNodePath.parent!!
            s += 1
        }
        return s
    }

    // TODO
    fun toArray(): Array<PositionNode> {
        throw NotImplementedError()
    }


    private fun f(): Double = trailPositionNodePath.costSoFar + trailPositionNodePath.heuristicsCost

}
