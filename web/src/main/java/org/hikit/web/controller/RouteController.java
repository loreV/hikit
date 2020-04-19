package org.hikit.web.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.JsonUtil;
import org.hikit.common.data.Poi;
import org.hikit.common.response.PlanResultResponse;
import org.hikit.common.response.PoiRestResponse;
import org.hikit.common.response.Status;
import org.hikit.web.RouteManager;
import org.hikit.web.request.PlanningRestRequest;
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

    private PlanResultResponse plan(Request request, Response response) {
        Set<String> errorMessages = routeCoordValidator.validate(request);

        response.type(ACCEPT_TYPE);
        return PlanResultResponse.PlanResultResponseBuilder.aPlanResultResponse()
                .withStatus(Status.OK)
                .withMessages(Collections.emptySet())
                .withPlanningResult(routeManager.plan(
                        new Gson().fromJson(request.body(), PlanningRestRequest.class))).build();
    }

    @NotNull
    private PoiRestResponse buildPoiResponse(final List<Poi> pois) {
        final PoiRestResponse.PoiRestResponseBuilder poiRestResponseBuilder = PoiRestResponse.
                PoiRestResponseBuilder.aPoiRestResponse().withTrails(pois);
        if (pois.isEmpty()) {
            return poiRestResponseBuilder
                    .withMessages(Collections.singleton("No POI found"))
                    .withStatus(Status.ERROR).build();
        }
        return poiRestResponseBuilder
                .withMessages(Collections.emptySet())
                .withStatus(Status.OK).build();
    }

    @NotNull
    private PoiRestResponse buildErrorResponse(final Set<String> errorMessages) {
        return PoiRestResponse.PoiRestResponseBuilder.aPoiRestResponse()
                .withTrails(Collections.emptyList())
                .withMessages(errorMessages)
                .withStatus(Status.ERROR).build();
    }

    public void init() {
        post(format("%s/plan", PREFIX), ACCEPT_TYPE, this::plan, JsonUtil.json());
        LOG.info("Route CONTROLLER Started");
    }
}
