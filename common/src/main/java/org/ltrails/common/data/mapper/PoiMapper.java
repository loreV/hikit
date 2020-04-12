package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.Poi;

import java.util.List;

public class PoiMapper implements Mapper<Poi> {

    private final PositionMapper positionMapper;
    private final GeoMapper geoMapper;

    @Inject
    public PoiMapper(PositionMapper positionMapper,
                     GeoMapper geoMapper) {
        this.positionMapper = positionMapper;
        this.geoMapper = geoMapper;
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
                .withTrailCodes(document.get(Poi.TRAIL_CODES, List.class))
                .withPostCode(document.getString(Poi.POST_CODE))
                .build();
    }

}
