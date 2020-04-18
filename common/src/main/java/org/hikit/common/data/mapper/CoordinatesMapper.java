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
}
