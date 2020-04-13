package org.ltrails.common.data.helper

import org.bson.Document

interface DAOHelper {

    companion object {
        const val IN_OPERATOR = "\$in"
    }

    fun appendEqualFilter(doc: Document,
                          key: String,
                          value: String): Document {
        return doc.append(key, value)
    }

    fun appendIn(doc: Document,
                 key: String,
                 values: List<String>): Document {
        return doc.append(key, Document(IN_OPERATOR, values))
    }
}