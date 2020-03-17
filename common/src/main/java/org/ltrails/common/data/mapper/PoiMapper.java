package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.Poi;
import org.ltrails.common.data.helper.GsonBeanHelper;

public class PoiMapper implements Mapper<Poi> {

    private final GsonBeanHelper gsonBeanHelper;
    private final PositionMapper positionMapper;
    private final GeoMapper geoMapper;

    @Inject
    public PoiMapper(GsonBeanHelper gsonBeanHelper,
                     PositionMapper positionMapper,
                     GeoMapper geoMapper) {
        this.gsonBeanHelper = gsonBeanHelper;
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
                .build();
    }

}
