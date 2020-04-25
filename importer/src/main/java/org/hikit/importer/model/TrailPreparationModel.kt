package org.hikit.importer.model

import org.hikit.common.data.CoordinatesWithAltitude

data class TrailPreparationModel constructor(val name: String,
                                             val description: String,
                                             val firstPos: CoordinatesWithAltitude,
                                             val lastPos: CoordinatesWithAltitude,
                                             val position: List<CoordinatesWithAltitude>)