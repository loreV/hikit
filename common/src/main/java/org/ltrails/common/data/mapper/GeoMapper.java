package org.ltrails.common.data.mapper;

import com.google.gson.JsonElement;
import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.helper.GsonBeanHelper;

public class GeoMapper implements Mapper<JsonElement> {

    private final GsonBeanHelper gsonBeanHelper;

    @Inject
    public GeoMapper(final GsonBeanHelper gsonBeanHelper) {
        this.gsonBeanHelper = gsonBeanHelper;
    }

    @Override
    public JsonElement mapToObject(Document document) {
        return (gsonBeanHelper.getGsonBuilder()).toJsonTree(document);
    }
}
