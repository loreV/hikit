package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.Position;

public class PositionMapper implements Mapper<Position> {

    final CoordinatesMapper coordinatesMapper;

    @Inject
    public PositionMapper(CoordinatesMapper coordinatesMapper) {
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Position mapToObject(Document document) {
        return Position.PositionBuilder.aPosition()
                .withCoords(coordinatesMapper.mapToObject(document.get(Position.COORD, Document.class)))
                .withAlt(document.getDouble(Position.ALTITUDE))
                .withPostCode(document.getString(Position.POSTCODE))
                .withCounty(document.getString(Position.COUNTY))
                .withArea(document.getString(Position.AREA))
                .withCity(document.getString(Position.CITY))
                .build();
    }
}
