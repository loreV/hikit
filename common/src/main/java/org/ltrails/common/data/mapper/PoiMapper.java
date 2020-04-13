package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.Poi;
import org.ltrails.common.data.TrailReference;

import java.util.List;
import java.util.stream.Collectors;

public class PoiMapper implements Mapper<Poi> {

    private final PositionMapper positionMapper;
    private final GeoMapper geoMapper;
    private final TrailReferenceMapper trailReferenceMapper;

    @Inject
    public PoiMapper(final PositionMapper positionMapper,
                     final GeoMapper geoMapper,
                     final TrailReferenceMapper trailReferenceMapper) {
        this.positionMapper = positionMapper;
        this.geoMapper = geoMapper;
        this.trailReferenceMapper = trailReferenceMapper;
    }

    @Override
    public Poi mapToObject(Document document) {
        return Poi.PoiBuilder.aPoi()
                .withName(document.getString(Poi.NAME))
                .withDescription(document.getString(Poi.DESCRIPTION))
                .withGeo(geoMapper.mapToObject(document.get(Poi.GEO, Document.class)))
                .withPosition(positionMapper.mapToObject(document.get(Poi.POSITION, Document.class)))
                .withOtherNames(document.get(Poi.OTHER_NAMES, List.class))
                .withTags(document.get(Poi.TAGS, List.class))
                .withResourcesLinks(document.get(Poi.RESOURCES_LINKS, List.class))
                .withTypes(document.get(Poi.TYPES, List.class))
                .withTrailRef(getTrailsRef(document, Poi.TRAIL_REF))
                .withPostCode(document.getString(Poi.POST_CODE))
                .build();
    }

    private List<TrailReference> getTrailsRef(final Document doc, final String fieldName) {
        final List<Document> trailRef = doc.get(fieldName, List.class);
        return trailRef.stream().map(trailReferenceMapper::mapToObject).collect(Collectors.toList());
    }

}
