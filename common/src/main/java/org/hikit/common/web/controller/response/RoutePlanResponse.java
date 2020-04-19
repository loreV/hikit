package org.hikit.common.web.controller.response;

import org.hikit.common.data.RouteResult;

import java.util.List;
import java.util.Set;

public class RoutePlanResponse extends RESTResponse {

    private List<RouteResult> routeResults;

    private RoutePlanResponse(final List<RouteResult> routeResults,
                              final Status status,
                              final Set<String> messages) {
        super(status, messages);
        this.routeResults = routeResults;
    }

    public List<RouteResult> getPlanningResult() {
        return routeResults;
    }

    public static final class PlanResultResponseBuilder {
        private Status status;
        private List<RouteResult> routeResult;
        private Set<String> messages;

        private PlanResultResponseBuilder() {
        }

        public static PlanResultResponseBuilder aPlanResultResponse() {
            return new PlanResultResponseBuilder();
        }

        public PlanResultResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public PlanResultResponseBuilder withPlanningResults(List<RouteResult> routeResult) {
            this.routeResult = routeResult;
            return this;
        }

        public PlanResultResponseBuilder withMessages(Set<String> messages) {
            this.messages = messages;
            return this;
        }

        public RoutePlanResponse build() {
            return new RoutePlanResponse(routeResult, status, messages);
        }
    }
}
