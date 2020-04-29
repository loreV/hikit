package org.hikit.common.data.mapper;

import org.bson.Document;
import org.hikit.common.data.TrailReference;

public class TrailReferenceMapper implements Mapper<TrailReference> {
    @Override
    public TrailReference mapToObject(final Document document) {
        return TrailReference.TrailReferenceBuilder.aTrailReference()
                .withPostCode(document.getString(TrailReference.POSTCODE))
                .withCode(document.getString(TrailReference.TRAIL_CODE))
                .build();
    }

    @Override
    public Document mapToDocument(TrailReference object) {
        return new Document(TrailReference.POSTCODE, object.getPostcode())
                .append(TrailReference.TRAIL_CODE, object.getTrailCode());
    }
}
