package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.Poi;
import org.hikit.common.data.TrailReference;

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
                .withGeo(geoMapper.mapToObject(document.get(GeoMapper.GEO, Document.class)))
                .withPosition(positionMapper.mapToObject(document.get(Poi.POSITION, Document.class)))
                .withOtherNames(document.get(Poi.OTHER_NAMES, List.class))
                .withTags(document.get(Poi.TAGS, List.class))
                .withResourcesLinks(document.get(Poi.RESOURCES_LINKS, List.class))
                .withTypes(document.get(Poi.TYPES, List.class))
                .withTrailRef(getTrailsRef(document, Poi.TRAIL_REF))
                .withPostCode(document.getString(Poi.POST_CODE))
                .build();
    }

    @Override
    public Document mapToDocument(Poi object) {
        return new Document(Poi.NAME, object.getName())
                .append(Poi.POSITION, positionMapper.mapToDocument(object.getPosition()))
                .append(Poi.DESCRIPTION, object.getDescription())
                .append(Poi.OTHER_NAMES, object.getOtherNames())
                .append(Poi.POST_CODE, object.getPostCode())
                .append(GeoMapper.GEO, object.getGeo())
                .append(Poi.RESOURCES_LINKS, object.getResourcesLinks())
                .append(Poi.TAGS, object.getTags())
                .append(Poi.TRAIL_REF, object.getTrailReferences().stream()
                        .map(trailReferenceMapper::mapToDocument).collect(Collectors.toList()))
                .append(Poi.TYPES, object.getTypes());
    }

    private List<TrailReference> getTrailsRef(final Document doc, final String fieldName) {
        final List<Document> trailRef = doc.get(fieldName, List.class);
        return trailRef.stream().map(trailReferenceMapper::mapToObject).collect(Collectors.toList());
    }

}
