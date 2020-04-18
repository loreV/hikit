package org.hikit.web.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.JsonUtil;
import org.hikit.common.response.PlanResultResponse;
import org.hikit.common.response.Status;
import org.hikit.web.RouteManager;
import org.hikit.web.request.PlanningRestRequest;
import spark.Request;
import spark.Response;

import java.util.Collections;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hikit.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.hikit.web.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.post;

public class RouteController {

    private final Logger LOG = getLogger(TrailController.class.getName());

    private static final String PREFIX = API_PREFIX + "/route";

    private final RouteManager routeManager;

    @Inject
    public RouteController(final RouteManager routeManager) {
        this.routeManager = routeManager;
    }

    private PlanResultResponse plan(Request request, Response response) {
        response.type(ACCEPT_TYPE);
        return PlanResultResponse.PlanResultResponseBuilder.aPlanResultResponse()
                .withStatus(Status.OK)
                .withMessages(Collections.emptyList())
                .withPlanningResult(routeManager.plan(
                        new Gson().fromJson(request.body(), PlanningRestRequest.class))).build();
    }

    public void init() {
        post(format("%s/plan", PREFIX), ACCEPT_TYPE, this::plan, JsonUtil.json());
        LOG.info("Route CONTROLLER Started");
    }
}
