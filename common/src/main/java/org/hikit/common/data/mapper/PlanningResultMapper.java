package org.hikit.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.hikit.common.data.RouteResult;
import org.hikit.common.data.TrailsPath;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlanningResultMapper implements Mapper<RouteResult> {

    final TrailsPathMapper trailsPathMapper;

    @Inject
    public PlanningResultMapper(TrailsPathMapper trailsPathMapper) {
        this.trailsPathMapper = trailsPathMapper;
    }

    @Override
    public RouteResult mapToObject(Document document) {
        return RouteResult.PlanningResultBuilder.aPlanningResult()
                .withWinningTrailsResult(trailsPathMapper.mapToObject(
                        document.get(RouteResult.WINNING_TRAIL, Document.class)))
                .withOptionalAlternatives(getOptionalAlternatives(document))
                .build();
    }

    private List<TrailsPath> getOptionalAlternatives(Document document) {
        List<Document> list = document.get(RouteResult.ALTERNATIVES, List.class);
        return list.stream().map(doc -> trailsPathMapper.mapToObject(doc)).collect(toList());
    }
}
