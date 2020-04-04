package org.ltrails.web

import org.bson.Document
import org.ltrails.common.data.Position
import org.ltrails.common.data.Trail

object TrailDaoHelper {

    fun appendEqualFilter(doc: Document,
                          key: String,
                          value: String): Document {
        return Document(key, value)
    }

    fun appendOrFilterOnStartAndFinalPost(doc: Document, postCodes: List<String>): Document {
        return doc.append("\$or", listOf(
                Document(Trail.START_POS + "." + Position.POSTCODE, Document("\$in", postCodes)),
                Document(Trail.FINAL_POS + "." + Position.POSTCODE, Document("\$in", postCodes))
        ))
    }

}