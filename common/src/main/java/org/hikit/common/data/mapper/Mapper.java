package org.hikit.common.data.mapper;


import org.bson.Document;

public interface Mapper<T> {
    T mapToObject(Document document);
}
