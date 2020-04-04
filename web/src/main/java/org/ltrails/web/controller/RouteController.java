package org.ltrails.web.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.ltrails.common.JsonUtil;
import org.ltrails.common.response.PlanResultResponse;
import org.ltrails.common.response.Status;
import org.ltrails.web.RouteManager;
import org.ltrails.web.request.PlanningRestRequest;
import spark.Request;
import spark.Response;

import java.util.Collections;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;
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
