package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.ConnectingWayPoint;

public class ConnectingWayPointMapper implements Mapper<ConnectingWayPoint> {

    private final PositionMapper positionMapper;
    private final TrailReferenceMapper trailReferenceMapper;

    @Inject
    public ConnectingWayPointMapper(PositionMapper positionMapper,
                                    TrailReferenceMapper trailReferenceMapper) {
        this.positionMapper = positionMapper;
        this.trailReferenceMapper = trailReferenceMapper;
    }

    @Override
    public ConnectingWayPoint mapToObject(Document document) {
        return ConnectingWayPoint.ConnectingWayPointBuilder.aConnectingWayPoint()
                .withPosition(positionMapper.mapToObject(document.get(ConnectingWayPoint.POSITION, Document.class)))
                .withConnectingTo(trailReferenceMapper.mapToObject(document.get(ConnectingWayPoint.POSITION_CONNECTING_TO, Document.class)))
                .build();
    }

    @Override
    public Document mapToDocument(ConnectingWayPoint object) {
        return new Document(ConnectingWayPoint.POSITION, object.getPosition()).append(ConnectingWayPoint.POSITION_CONNECTING_TO,
                trailReferenceMapper.mapToDocument(object.getConnectingTo()));
    }
}
