package org.hikit.common.data.helper

import org.bson.Document
import org.hikit.common.data.Position
import org.hikit.common.data.Trail

class TrailDAOHelper : DAOHelper {

    fun appendOrFilterOnStartAndFinalPost(doc: Document, postCodes: List<String>): Document {
        return doc.append("\$or", listOf(
                Document(Trail.START_POS + "." + Position.POSTCODE, Document("\$in", postCodes)),
                Document(Trail.FINAL_POS + "." + Position.POSTCODE, Document("\$in", postCodes))
        ))
    }

}