package org.hikit.web.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.JsonUtil;
import org.hikit.common.data.RouteResult;
import org.hikit.common.response.RoutePlanResponse;
import org.hikit.common.response.Status;
import org.hikit.web.RouteManager;
import org.hikit.web.request.RoutePlanRequest;
import org.hikit.web.request.validator.RouteCoordRequestValidator;
import org.jetbrains.annotations.NotNull;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hikit.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.hikit.web.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.post;

public class RouteController {

    private final Logger LOG = getLogger(TrailController.class.getName());

    private static final String PREFIX = API_PREFIX + "/route";

    private final RouteManager routeManager;
    private final RouteCoordRequestValidator routeCoordValidator;

    @Inject
    public RouteController(final RouteCoordRequestValidator routeCoordRequestValidator,
                           final RouteManager routeManager) {
        this.routeManager = routeManager;
        this.routeCoordValidator = routeCoordRequestValidator;
    }

    private RoutePlanResponse plan(Request request, Response response) {
        response.type(ACCEPT_TYPE);
        Set<String> errorMessages = routeCoordValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        return buildRouteResponse(routeManager.plan(
                new Gson().fromJson(request.body(), RoutePlanRequest.class)));
    }

    @NotNull
    private RoutePlanResponse buildRouteResponse(final List<RouteResult> routes) {
        RoutePlanResponse.PlanResultResponseBuilder routeResultBuilder =
                RoutePlanResponse.PlanResultResponseBuilder.aPlanResultResponse()
                        .withPlanningResults(routes);
        if (routes.isEmpty()) {
            return routeResultBuilder
                    .withMessages(Collections.singleton("No routes found"))
                    .withStatus(Status.ERROR).build();
        }
        return routeResultBuilder
                .withMessages(Collections.emptySet())
                .withStatus(Status.OK).build();
    }

    @NotNull
    private RoutePlanResponse buildErrorResponse(final Set<String> errorMessages) {
        return RoutePlanResponse.PlanResultResponseBuilder.aPlanResultResponse()
                .withPlanningResults(Collections.emptyList())
                .withMessages(errorMessages)
                .withStatus(Status.ERROR).build();
    }

    public void init() {
        post(format("%s/plan", PREFIX), ACCEPT_TYPE, this::plan, JsonUtil.json());
        LOG.info("Route CONTROLLER Started");
    }
}
