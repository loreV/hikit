package org.ltrails.web.data.helper

import org.bson.Document

interface DAOHelper {

    fun appendEqualFilter(doc: Document,
                          key: String,
                          value: String): Document {
        return doc.append(key, value)
    }

    fun appendIn(doc: Document,
                 key: String,
                 values: List<String>): Document {
        return doc.append(key, Document("\$in", values))
    }
}