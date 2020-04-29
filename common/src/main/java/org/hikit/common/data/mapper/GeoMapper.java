package org.hikit.common.data.mapper;

import com.google.gson.JsonElement;
import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.helper.GsonBeanHelper;

public class GeoMapper implements Mapper<JsonElement> {

    public static final String GEO = "geo";

    private final GsonBeanHelper gsonBeanHelper;

    @Inject
    public GeoMapper(final GsonBeanHelper gsonBeanHelper) {
        this.gsonBeanHelper = gsonBeanHelper;
    }

    @Override
    public JsonElement mapToObject(Document document) {
        return (gsonBeanHelper.getGsonBuilder()).toJsonTree(document);
    }

    @Override
    public Document mapToDocument(JsonElement object) {
        return new Document(GEO, object.toString());
    }


}
