package org.ltrails.common.data.mapper;

import org.bson.Document;
import org.ltrails.common.data.Position;
import org.ltrails.common.data.Trail;
import org.ltrails.common.data.TrailReference;

public class TrailReferenceMapper implements Mapper<TrailReference> {
    @Override
    public TrailReference mapToObject(Document document) {
        return TrailReference.TrailReferenceBuilder.aTrailReference()
                .withPostCode(document.getString(Position.POSTCODE))
                .withCode(document.getString(Trail.CODE))
                .build();
    }
}
