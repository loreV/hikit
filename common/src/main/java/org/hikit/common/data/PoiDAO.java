package org.hikit.common.data;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.hikit.common.data.helper.PoiDAOHelper;
import org.hikit.common.data.mapper.Mapper;
import org.hikit.common.data.mapper.PoiMapper;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hikit.common.data.TrailDAO.*;

public class PoiDAO {

    private final static int RESULT_LIMIT = 50;
    public static final String RESOLVED_LOCATION = Poi.POSITION + ".location";

    private final MongoCollection<Document> collection;
    private final Mapper<Poi> dataPointMapper;

    @Inject
    public PoiDAO(final DataSource dataSource,
                  final PoiMapper dataPointMapper) {
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
        final Document filterByNear = new Document(RESOLVED_LOCATION,
                new Document(NEAR_OPERATOR,
                        new Document("coordinates", Arrays.asList(longitude, latitude)))
                        .append($_MIN_DISTANCE_FILTER, 0).append($_MAX_M_DISTANCE_FILTER, meters));
        if (!types.isEmpty()) {
            filterByNear.append(PoiDAOHelper.IN_OPERATOR, types);
        }
        return toPoiList(collection.find(filterByNear));
    }

    private List<Poi> toPoiList(final FindIterable<Document> documents) {
        return Lists.newArrayList(documents).stream().map(dataPointMapper::mapToObject).collect(toList());
    }
}
