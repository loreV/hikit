package org.hikit.common.data.poi

class PoiTypes {

    private val anthropologicalPoiTypes = listOf("castle", "cemetery", "village", "court", "villa", "church", "fountain", "bridge", "museum")
    private val naturalisticPoiTypes = listOf("waterSource", "spring", "tree")
    private val hikePoiTypes = listOf("water", "lodge", "bivouac", "camping", "agritourism")

    val allPoiTypes = anthropologicalPoiTypes.union(naturalisticPoiTypes).union(hikePoiTypes)
}