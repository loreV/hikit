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
    private final GeoMapper geoMapper;

    @Inject
    public TrailMapper(PositionMapper positionMapper,
                       TrailReferenceMapper trailReferenceMapper,
                       GeoMapper geoMapper) {
        this.positionMapper = positionMapper;
        this.trailReferenceMapper = trailReferenceMapper;
        this.geoMapper = geoMapper;

    }

    @Override
    public Trail mapToObject(Document doc) {
        return Trail.TrailBuilder.aTrail()
                .withName(doc.getString(Trail.NAME))
                .withDescription(doc.getString(Trail.DESCRIPTION))
                .withCode(doc.getString(Trail.CODE))
                .withStartPos(getPos(doc, Trail.STARTPOS))
                .withFinalPos(getPos(doc, Trail.FINALPOS))
                .withGeo(geoMapper.mapToObject(doc.get(Trail.GEO, Document.class)))
                .withReportedDifficulty(doc.getDouble(Trail.REPORTED_DIFFICULTY))
                .withCalculatedDifficulty(doc.getDouble(Trail.CALCULATED_DIFFICULTY))
                .withSuggestedTimeOfTheYear(doc.get(Trail.SUGGESTED_TIME_OF_YEAR, List.class))
                .withTrackLength(doc.getDouble(Trail.TRACK_LENGTH))
                .withEta(doc.getDouble(Trail.ETA))
                .withOfficialDifficulty(getOfficialDifficulty(doc))
                .withConnectingTrails(getConnectingTrail(doc))
                .withPois(getPois(doc))
                .build();
    }

    private List<Poi> getPois(Document doc) {
        // TODO
        return Collections.emptyList();
    }

    private List<ConnectingWayPoint> getConnectingTrail(Document rootDoc) {
        final List<Document> connectingTrails = rootDoc.get(Trail.CONNECTING_TRAILS, List.class);
        if (connectingTrails.isEmpty()) {
            return Collections.emptyList();
        }
        return connectingTrails.stream().map(document -> ConnectingWayPoint.ConnectingWayPointBuilder.aConnectingWayPoint()
                .withPosition(getPos(document, ConnectingWayPoint.POSITION))
                .withConnectingFrom(getTrailRef(document, ConnectingWayPoint.POSITION_CONNECTING_FROM))
                .withConnectingTo(getTrailRef(document, ConnectingWayPoint.POSITION_CONNECTING_TO))
                .withCode(document.getString(ConnectingWayPoint.CODE)).build()).collect(Collectors.toList());
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

    private OfficialDifficulty getOfficialDifficulty(Document doc) {
        final String officialDifficulty = doc.getString(Trail.OFFICIAL_DIFFICULTY);
        return OfficialDifficulty.valueOf(officialDifficulty);
    }

}
