package org.hikit.common.data.mapper;

import org.bson.Document;
import org.hikit.common.data.Coordinates;

public class CoordinatesMapper implements Mapper<Coordinates> {

    @Override
    public Coordinates mapToObject(Document document) {
        return Coordinates.CoordinateBuilder.aCoordinate()
                .withLongitude(document.getDouble(Coordinates.LONG))
                .withLatitude(document.getDouble(Coordinates.LAT))
                .build();
    }

    @Override
    public Document mapToDocument(Coordinates object) {
        return new Document(Coordinates.LAT, object.getLatitude())
                .append(Coordinates.LONG, object.getLongitude());
    }
}
