package org.hikit.common.response;

import org.hikit.common.data.PlanningResult;

import java.util.Set;

public class PlanResultResponse extends RESTResponse {

    private PlanningResult planningResult;

    private PlanResultResponse(final PlanningResult planningResult,
                               final Status status,
                               final Set<String> messages) {
        super(status, messages);
        this.planningResult = planningResult;
    }

    public PlanningResult getPlanningResult() {
        return planningResult;
    }

    public static final class PlanResultResponseBuilder {
        private Status status;
        private PlanningResult planningResult;
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

        public PlanResultResponseBuilder withPlanningResult(PlanningResult planningResult) {
            this.planningResult = planningResult;
            return this;
        }

        public PlanResultResponseBuilder withMessages(Set<String> messages) {
            this.messages = messages;
            return this;
        }

        public PlanResultResponse build() {
            return new PlanResultResponse(planningResult, status, messages);
        }
    }
}
