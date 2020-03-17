package org.ltrails.common.data;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.ltrails.common.data.mapper.Mapper;
import org.ltrails.common.data.mapper.TrailMapper;
import org.ltrails.common.data.mapper.TrailsDao;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.or;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class TrailDAO implements TrailsDao {

    private static final String RESOLVED_START_POS_COORDINATE = Trail.STARTPOS + "." + Position.COORD;
    private static final String RESOLVED_FINAL_POS_COORDINATE = Trail.FINALPOS + "." + Position.NAME;
    private static final String RESOLVED_FINAL_TAGS_COORDINATE = Trail.FINALPOS + "." + Position.TAGS;
    private static final String RESOLVED_FINAL_DESCRIPTION = Trail.FINALPOS + "." + Position.DESCRIPTION;
    private static final String RESOLVED_AREA_IN_DESTINATION = Trail.FINALPOS + "." + Position.AREA;
    private static final String RESOLVED_AREA_IN_START_POS = Trail.STARTPOS + "." + Position.AREA;

    private static final String RESOLVED_COORDINATES = "coordinates";
    private static final String NEAR_OPERATOR = "$near";
    private static final String $_MIN_DISTANCE_FILTER = "$minDistance";
    private static final String $_MAX_M_DISTANCE_FILTER = "$maxDistance";
    private static final String STARTING_STRING = "^%s\\.*";

    private final MongoCollection<Document> collection;
    private final Mapper<Trail> dataPointMapper;

    @Inject
    public TrailDAO(final DataSource dataSource,
                    final TrailMapper trailMapper) {
        this.collection = dataSource.getDB().getCollection(Trail.COLLECTION_NAME);
        this.dataPointMapper = trailMapper;
    }

    public List<Trail> getAllTrails() {
        return toTrailsList(collection.find(new Document()));
    }

    public Trail getTrailsByCodeAndPostcode(final String code, final String postcode) {
        final FindIterable<Document> documents = collection.find(new Document(Trail.POST_CODE, postcode).append(Trail.CODE, code));
        return toTrailsList(documents).stream().findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public List<Trail> getTrailsHavingDestinationNameLike(final String destination) {
        final FindIterable<Document> documents = collection.find(new Document(RESOLVED_FINAL_POS_COORDINATE,
                Pattern.compile(format(STARTING_STRING, destination), Pattern.CASE_INSENSITIVE)));
        return toTrailsList(documents);
    }

    public List<Trail> getTrailsHavingDestinationTagsLike(final String destination) {
        final FindIterable<Document> documents = collection.find(new Document(RESOLVED_FINAL_TAGS_COORDINATE, format(STARTING_STRING, destination)));
        return toTrailsList(documents);
    }

    public List<Trail> getTrailsHavingDescriptionLike(final String destination) {
        final FindIterable<Document> documents = collection.find(new Document(RESOLVED_FINAL_DESCRIPTION, format(STARTING_STRING, destination)));
        return toTrailsList(documents);
    }

    public List<Trail> getTrailsByStartPosMetricDistance(double longitude, double latitude, int meters) {
        final FindIterable<Document> documents = collection.find(
                new Document(RESOLVED_START_POS_COORDINATE,
                        new Document(NEAR_OPERATOR,
                                new Document(RESOLVED_COORDINATES, Arrays.asList(longitude, latitude)
                                )
                        ).append($_MIN_DISTANCE_FILTER, 0).append($_MAX_M_DISTANCE_FILTER, meters)));
        return toTrailsList(documents);
    }

    public List<Trail> getTrailsInArea(@NotNull String area) {
        final FindIterable<Document> documents = collection.find(
                or(
                        new Document(RESOLVED_AREA_IN_START_POS, area),
                        new Document(RESOLVED_AREA_IN_DESTINATION, area)
                ));
        return toTrailsList(documents);
    }

    @NotNull
    private List<Trail> toTrailsList(FindIterable<Document> documents) {
        return Lists.newArrayList(documents).stream().map(dataPointMapper::mapToObject).collect(toList());
    }
}
