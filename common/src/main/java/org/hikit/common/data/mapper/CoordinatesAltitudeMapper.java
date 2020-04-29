package org.hikit.common.data.mapper;

import org.bson.Document;
import org.hikit.common.data.Coordinates;
import org.hikit.common.data.CoordinatesWithAltitude;

public class CoordinatesAltitudeMapper implements Mapper<CoordinatesWithAltitude> {


    @Override
    public CoordinatesWithAltitude mapToObject(Document document) {
        return CoordinatesWithAltitude.CoordinatesWithAltitudeBuilder.aCoordinatesWithAltitude()
                .withLatitude(document.getDouble(Coordinates.LAT))
                .withLongitude(document.getDouble(Coordinates.LONG))
                .withAltitude(document.getDouble(CoordinatesWithAltitude.ALTITUDE)).build();
    }

    @Override
    public Document mapToDocument(CoordinatesWithAltitude object) {
        return new Document(Coordinates.LAT, object.getLatitude())
                .append(Coordinates.LONG, object.getLongitude())
                .append(CoordinatesWithAltitude.ALTITUDE, object.getAltitude());
    }
}
