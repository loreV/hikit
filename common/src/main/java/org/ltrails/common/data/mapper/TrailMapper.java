package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.ltrails.common.data.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TrailMapper implements Mapper<Trail> {

    private final PositionMapper positionMapper;
    private final TrailReferenceMapper trailReferenceMapper;
    private final PoiMapper poiMapper;
    private final GeoMapper geoMapper;

    @Inject
    public TrailMapper(PositionMapper positionMapper,
                       TrailReferenceMapper trailReferenceMapper,
                       PoiMapper poiMapper,
                       GeoMapper geoMapper) {
        this.positionMapper = positionMapper;
        this.trailReferenceMapper = trailReferenceMapper;
        this.poiMapper = poiMapper;
        this.geoMapper = geoMapper;
    }

    @Override
    public Trail mapToObject(Document doc) {
        return Trail.TrailBuilder.aTrail()
                .withName(doc.getString(Trail.NAME))
                .withDescription(doc.getString(Trail.DESCRIPTION))
                .withCode(doc.getString(Trail.CODE))
                .withStartPos(getPos(doc, Trail.START_POS))
                .withFinalPos(getPos(doc, Trail.FINAL_POS))
                .withGeo(geoMapper.mapToObject(doc.get(Trail.GEO, Document.class)))
                .withTrackLength(doc.getDouble(Trail.TRACK_LENGTH))
                .withEta(doc.getDouble(Trail.ETA))
                .withPois(getPois(doc))
                .withClassification(getClassification(doc))
                .withConnectingTrails(getConnectingTrail(doc))
                .withCountry(doc.getString(Trail.COUNTRY))
                .withPois(getPois(doc))
                .build();
    }

    private List<Poi> getPois(Document doc) {
        final List<Document> list = doc.get(Trail.POIS, List.class);
        return list.stream().map(poiMapper::mapToObject).collect(Collectors.toList());
    }

    private List<ConnectingWayPoint> getConnectingTrail(Document rootDoc) {
        final List<Document> connectingTrails = rootDoc.get(Trail.CONNECTING_TRAILS, List.class);
        if (connectingTrails.isEmpty()) {
            return Collections.emptyList();
        }
        return connectingTrails.stream().map(document -> ConnectingWayPoint.ConnectingWayPointBuilder.aConnectingWayPoint()
                .withPosition(getPos(document, ConnectingWayPoint.POSITION))
                .withConnectingTo(getTrailRef(document, ConnectingWayPoint.POSITION_CONNECTING_TO))
                .build()).collect(Collectors.toList());
    }

    @NotNull
    private Position getPos(Document doc, String fieldName) {
        final Document pos = doc.get(fieldName, Document.class);
        return positionMapper.mapToObject(pos);
    }

    private TrailReference getTrailRef(Document doc, String fieldName) {
        final Document trailRef = doc.get(fieldName, Document.class);
        return trailReferenceMapper.mapToObject(trailRef);
    }

    private TrailClassification getClassification(Document doc) {
        final String classification = doc.getString(Trail.CLASSIFICATION);
        return TrailClassification.valueOf(classification);
    }

}
