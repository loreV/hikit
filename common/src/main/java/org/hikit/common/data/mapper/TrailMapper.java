package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.*;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class TrailMapper implements Mapper<Trail> {

    private final PositionMapper positionMapper;
    private final TrailReferenceMapper trailReferenceMapper;
    private final PoiMapper poiMapper;
    private final CoordinatesAltitudeMapper coordinatesAltitudeMapper;
    private final ConnectingWayPointMapper connectingWayPointMapper;

    @Inject
    public TrailMapper(PositionMapper positionMapper,
                       TrailReferenceMapper trailReferenceMapper,
                       PoiMapper poiMapper,
                       CoordinatesAltitudeMapper coordinatesAltitudeMapper,
                       ConnectingWayPointMapper connectingWayPointMapper) {
        this.positionMapper = positionMapper;
        this.trailReferenceMapper = trailReferenceMapper;
        this.poiMapper = poiMapper;
        this.coordinatesAltitudeMapper = coordinatesAltitudeMapper;
        this.connectingWayPointMapper = connectingWayPointMapper;
    }

    @Override
    public Trail mapToObject(Document doc) {
        return Trail.TrailBuilder.aTrail()
                .withName(doc.getString(Trail.NAME))
                .withPostCodes(doc.get(Trail.POST_CODE, List.class))
                .withDescription(doc.getString(Trail.DESCRIPTION))
                .withCode(doc.getString(Trail.CODE))
                .withStartPos(getPos(doc, Trail.START_POS))
                .withFinalPos(getPos(doc, Trail.FINAL_POS))
                .withTrackLength(doc.getDouble(Trail.TRACK_LENGTH))
                .withEta(doc.getDouble(Trail.ETA))
                .withPois(getPois(doc))
                .withClassification(getClassification(doc))
                .withConnectingTrails(getConnectingTrail(doc))
                .withCountry(doc.getString(Trail.COUNTRY))
                .withCoordinates(getCoordinatesWithAltitude(doc))
                .build();
    }

    @Override
    public Document mapToDocument(Trail object) {
        return new Document(Trail.NAME, object.getName())
                .append(Trail.DESCRIPTION, object.getDescription())
                .append(Trail.CODE, object.getCode())
                .append(Trail.POST_CODE, object.getPostCodes())
                .append(Trail.START_POS, object.getStartPos())
                .append(Trail.FINAL_POS, object.getFinalPos())
                .append(Trail.TRACK_LENGTH, object.getTrackLength())
                .append(Trail.ETA, object.getEta())
                .append(Trail.POIS, object.getPois().stream().map(poiMapper::mapToDocument).collect(toList()))
                .append(Trail.CLASSIFICATION, object.getTrailClassification().toString())
                .append(Trail.CONNECTING_TRAILS, object.getConnectingWayPoints().stream().map(connectingWayPointMapper::mapToDocument).collect(toList()))
                .append(Trail.COUNTRY, object.getCountry())
                .append(Trail.COORDINATES_WITH_ALTITUDE, object.getCoordinates().stream().map(coordinatesAltitudeMapper::mapToDocument).collect(toList()));
    }

    private List<CoordinatesWithAltitude> getCoordinatesWithAltitude(Document doc) {
        final List<Document> list = doc.get(Trail.COORDINATES_WITH_ALTITUDE, List.class);
        return list.stream().map(coordinatesAltitudeMapper::mapToObject).collect(toList());
    }

    private List<Poi> getPois(Document doc) {
        final List<Document> list = doc.get(Trail.POIS, List.class);
        return list.stream().map(poiMapper::mapToObject).collect(toList());
    }

    private List<ConnectingWayPoint> getConnectingTrail(Document rootDoc) {
        final List<Document> connectingTrails = rootDoc.get(Trail.CONNECTING_TRAILS, List.class);
        if (connectingTrails.isEmpty()) {
            return Collections.emptyList();
        }
        return connectingTrails.stream().map(connectingWayPointMapper::mapToObject).collect(toList());
    }

    private Position getPos(Document doc, String fieldName) {
        final Document pos = doc.get(fieldName, Document.class);
        return positionMapper.mapToObject(pos);
    }

    private TrailClassification getClassification(Document doc) {
        final String classification = doc.getString(Trail.CLASSIFICATION);
        return TrailClassification.valueOf(classification);
    }

}
