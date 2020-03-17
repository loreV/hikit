package org.ltrails.common.data;

import java.util.Collections;
import java.util.List;

public class PlanningResult {

    public final static String WINNING_TRAIL = "winningTrail";
    public final static String ALTERNATIVES = "alternativeTrails";

    private TrailsPath winningTrailsResult;
    private List<TrailsPath> optionalAlternatives;

    public PlanningResult(TrailsPath winningTrailsResult, List<TrailsPath> optionalAlternatives) {
        this.winningTrailsResult = winningTrailsResult;
        this.optionalAlternatives = optionalAlternatives;
    }

    public TrailsPath getWinningTrailsResult() {
        return winningTrailsResult;
    }

    public List<TrailsPath> getOptionalAlternatives() {
        return optionalAlternatives;
    }


    public static final class PlanningResultBuilder {
        private TrailsPath winningTrailsResult;
        private List<TrailsPath> optionalAlternatives;

        private PlanningResultBuilder() {
        }

        public static PlanningResultBuilder aPlanningResult() {
            return new PlanningResultBuilder();
        }

        public PlanningResultBuilder withWinningTrailsResult(TrailsPath winningTrailsResult) {
            this.winningTrailsResult = winningTrailsResult;
            return this;
        }

        public PlanningResultBuilder withOptionalAlternatives(List<TrailsPath> optionalAlternatives) {
            this.optionalAlternatives = optionalAlternatives;
            return this;
        }

        public PlanningResult build() {
            return new PlanningResult(winningTrailsResult, optionalAlternatives);
        }

        public PlanningResult buildEmpty() {
            return new PlanningResult(TrailsPath.TrailsPathBuilder.aTrailsPath().build(),
                    Collections.emptyList());
        }
    }
}
