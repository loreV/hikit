package org.ltrails.web.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.ltrails.common.JsonUtil;
import org.ltrails.common.response.PlanResultResponse;
import org.ltrails.common.response.Status;
import org.ltrails.common.response.TrailRestResponse;
import org.ltrails.web.TrailProcessor;
import org.ltrails.web.request.PlanningRequest;
import spark.Request;
import spark.Response;

import java.util.Collections;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.get;
import static spark.Spark.post;


public class TrailsController {

    private final Logger LOG = getLogger(TrailsController.class.getName());

    private static final String PREFIX = API_PREFIX + "/trails";

    private final TrailProcessor trailProcessor;

    @Inject
    public TrailsController(final TrailProcessor trailProcessor) {
        this.trailProcessor = trailProcessor;
    }

    private TrailRestResponse getAll(final Request request, final Response response) {
        response.type(ACCEPT_TYPE);
        return TrailRestResponse.TrailRestResponseBuilder.aTrailRestResponse()
                .withMessages(Collections.emptyList())
                .withStatus(Status.OK)
                .withTrails(trailProcessor.getAllTrails()).build();
    }

    private TrailRestResponse getByCode(final Request request, final Response response) {

        return TrailRestResponse.TrailRestResponseBuilder.aTrailRestResponse()
                .withMessages(Collections.emptyList())
                .withStatus(Status.OK)
                .withTrails(trailProcessor.getAllTrails()).build();
    }


    private PlanResultResponse plan(Request request, Response response) {
        response.type(ACCEPT_TYPE);
        return PlanResultResponse.PlanResultResponseBuilder.aPlanResultResponse()
                .withStatus(Status.OK)
                .withMessages(Collections.emptyList())
                .withPlanningResult(trailProcessor.plan(
                        new Gson().fromJson(request.body(), PlanningRequest.class))).build();
    }

    public void init() {
        // get all
        get(format("%s", PREFIX), ACCEPT_TYPE, this::getAll, JsonUtil.json());
        get(format("%s/get", PREFIX), ACCEPT_TYPE, this::getByCode, JsonUtil.json());
        post(format("%s/plan", PREFIX), ACCEPT_TYPE, this::plan, JsonUtil.json());

        LOG.info("DataPoint CONTROLLER Started");
    }


}
