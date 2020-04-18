package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.Position;

public class PositionMapper implements Mapper<Position> {

    final CoordinatesMapper coordinatesMapper;

    @Inject
    public PositionMapper(CoordinatesMapper coordinatesMapper) {
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Position mapToObject(Document document) {
        return Position.PositionBuilder.aPosition()
                .withCoords(coordinatesMapper.mapToObject(document.get(Position.COORDS, Document.class)))
                .withAlt(document.getDouble(Position.ALTITUDE))
                .withPostCode(document.getString(Position.POSTCODE))
                .build();
    }
}
