package org.ltrails.common.data.mapper;

import org.bson.Document;
import org.ltrails.common.data.TrailReference;

public class TrailReferenceMapper implements Mapper<TrailReference> {
    @Override
    public TrailReference mapToObject(final Document document) {
        return TrailReference.TrailReferenceBuilder.aTrailReference()
                .withPostCode(document.getString(TrailReference.POSTCODE))
                .withCode(document.getString(TrailReference.TRAIL_CODE))
                .build();
    }
}
