package org.ltrails.common.data.mapper;

import com.google.inject.Inject;
import org.bson.Document;
import org.ltrails.common.data.PlanningResult;
import org.ltrails.common.data.TrailsPath;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlanningResultMapper implements Mapper<PlanningResult> {

    final TrailsPathMapper trailsPathMapper;

    @Inject
    public PlanningResultMapper(TrailsPathMapper trailsPathMapper) {
        this.trailsPathMapper = trailsPathMapper;
    }

    @Override
    public PlanningResult mapToObject(Document document) {
        return PlanningResult.PlanningResultBuilder.aPlanningResult()
                .withWinningTrailsResult(trailsPathMapper.mapToObject(
                        document.get(PlanningResult.WINNING_TRAIL, Document.class)))
                .withOptionalAlternatives(getOptionalAlternatives(document))
                .build();
    }

    private List<TrailsPath> getOptionalAlternatives(Document document) {
        List<Document> list = document.get(PlanningResult.ALTERNATIVES, List.class);
        return list.stream().map(doc -> trailsPathMapper.mapToObject(doc)).collect(toList());
    }
}
