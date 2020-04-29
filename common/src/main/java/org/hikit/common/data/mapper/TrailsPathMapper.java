package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.Position;
import org.hikit.common.data.Trail;
import org.hikit.common.data.TrailsPath;

import java.util.List;
import java.util.stream.Collectors;

public class TrailsPathMapper implements Mapper<TrailsPath> {

    private TrailMapper trailMapper;
    private PositionMapper positionMapper;

    @Inject
    public TrailsPathMapper(TrailMapper trailMapper,
                            PositionMapper positionMapper) {
        this.trailMapper = trailMapper;
        this.positionMapper = positionMapper;
    }

    @Override
    public TrailsPath mapToObject(Document document) {
        return TrailsPath.TrailsPathBuilder.aTrailsPath()
                .withTrails(getTrails(document))
                .withConnectingPositions(getConnectingPosition(document))
                .withEta(document.getDouble(TrailsPath.ETA))
                .withDistance(document.getDouble(TrailsPath.DISTANCE))
                .withTotalElevationDown(document.getDouble(TrailsPath.TOTAL_ELEVATION_DOWN))
                .withTotalElevationUp(document.getDouble(TrailsPath.TOTAL_ELEVATION_UP))
                .build();
    }

    @Override
    public Document mapToDocument(TrailsPath object) {
        return new Document(TrailsPath.TRAILS, object.getTrails().stream().map(trailMapper::mapToDocument).collect(Collectors.toList()))
                .append(TrailsPath.CONNECTING_POSITIONS, object.getConnectingPositions().stream().map(positionMapper::mapToDocument).collect(Collectors.toList()))
                .append(TrailsPath.ETA, object.getEta())
                .append(TrailsPath.DISTANCE, object.getDistance())
                .append(TrailsPath.TOTAL_ELEVATION_DOWN, object.getTotalElevationDown())
                .append(TrailsPath.TOTAL_ELEVATION_UP, object.getTotalElevationUp());
    }

    private List<Position> getConnectingPosition(Document document) {
        List<Document> list = document.get(TrailsPath.CONNECTING_POSITIONS, List.class);
        return list.stream().map(doc -> positionMapper.mapToObject(doc)).collect(Collectors.toList());
    }

    private List<Trail> getTrails(Document document) {
        List<Document> list = document.get(TrailsPath.TRAILS, List.class);
        return list.stream().map(doc -> trailMapper.mapToObject(doc)).collect(Collectors.toList());
    }

}
