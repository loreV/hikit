package org.ltrails.common.data;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.ltrails.common.data.helper.PoiDAOHelper;
import org.ltrails.common.data.mapper.Mapper;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.ltrails.common.data.TrailDAO.*;

public class PoiDAO {

    private final static int RESULT_LIMIT = 50;

    private final MongoCollection<Document> collection;
    private final Mapper<Poi> dataPointMapper;


    public PoiDAO(final DataSource dataSource,
                  final Mapper<Poi> dataPointMapper) {
        this.collection = dataSource.getDB().getCollection(Poi.COLLECTION_NAME);
        this.dataPointMapper = dataPointMapper;
    }

    public List<Poi> executeQueryAndGetResult(final Document doc) {
        return toPoiList(collection.find(doc).limit(RESULT_LIMIT));
    }

    public List<Poi> getPosByPositionAndTypes(final double longitude,
                                              final double latitude,
                                              final int meters,
                                              final List<String> types) {
        final Document filterByNear = new Document(Poi.POSITION,
                new Document(NEAR_OPERATOR,
                        new Document(RESOLVED_COORDINATES, Arrays.asList(longitude, latitude)))
                        .append($_MIN_DISTANCE_FILTER, 0).append($_MAX_M_DISTANCE_FILTER, meters))
                .append(PoiDAOHelper.IN_OPERATOR, types);
        return toPoiList(collection.find(filterByNear));
    }

    private List<Poi> toPoiList(final FindIterable<Document> documents) {
        return Lists.newArrayList(documents).stream().map(dataPointMapper::mapToObject).collect(toList());
    }
}
