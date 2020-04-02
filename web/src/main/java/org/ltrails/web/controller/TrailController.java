package org.ltrails.web.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.ltrails.common.JsonUtil;
import org.ltrails.common.data.Trail;
import org.ltrails.common.data.helper.GsonBeanHelper;
import org.ltrails.common.response.Status;
import org.ltrails.common.response.TrailRestResponse;
import org.ltrails.web.TrailManager;
import org.ltrails.web.request.TrailsGeoRequest;
import org.ltrails.web.request.validator.TrailGeoRequestValidator;
import org.ltrails.web.request.validator.TrailRequestValidator;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.post;


public class TrailController {

    private final Logger LOG = getLogger(TrailController.class.getName());
    private static final String PREFIX = API_PREFIX + "/trails";

    private final TrailManager trailManager;
    private final TrailRequestValidator trailRequestValidator;
    private final TrailGeoRequestValidator trailGeoRequestValidator;
    private final GsonBeanHelper gsonBeanHelper;

    @Inject
    public TrailController(final TrailManager trailManager,
                           final TrailRequestValidator trailRequestValidator,
                           final TrailGeoRequestValidator trailGeoRequestValidator,
                           final GsonBeanHelper gsonBeanHelper) {
        this.trailManager = trailManager;
        this.trailRequestValidator = trailRequestValidator;
        this.trailGeoRequestValidator = trailGeoRequestValidator;
        this.gsonBeanHelper = gsonBeanHelper;
    }

    private TrailRestResponse get(final Request request, final Response response) {
        final List<String> errorMessages = trailRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final List<Trail> trailsByTrailCode = trailManager.getByTrailPostCodeCountry(
                trailRequestValidator.getParams(request),
                request.queryMap().toMap());
        return trailRestResponseBuilder(trailsByTrailCode);
    }

    private TrailRestResponse getGeo(final Request request, final Response response) {
        List<String> errorMessages = trailGeoRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final TrailsGeoRequest trailsGeoRequest = gsonBeanHelper.getGsonBuilder().fromJson(request.body(), TrailsGeoRequest.class);
        final List<Trail> trailsNearby = trailManager.getByGeo(trailsGeoRequest.getCoords(),
                trailsGeoRequest.getDistance(),
                trailsGeoRequest.getUom());
        return trailRestResponseBuilder(trailsNearby);
    }

    @NotNull
    private TrailRestResponse trailRestResponseBuilder(final List<Trail> trails) {
        final TrailRestResponse.TrailRestResponseBuilder trailRestResponseBuilder = TrailRestResponse.
                TrailRestResponseBuilder.aTrailRestResponse().withTrails(trails);
        if (trails.isEmpty()) {
            return trailRestResponseBuilder
                    .withMessages(Collections.singletonList("No trails found"))
                    .withStatus(Status.ERROR).build();
        }
        return trailRestResponseBuilder
                .withMessages(Collections.emptyList())
                .withStatus(Status.OK).build();
    }

    @NotNull
    private TrailRestResponse buildErrorResponse(final List<String> errorMessages) {
        return TrailRestResponse.TrailRestResponseBuilder.aTrailRestResponse()
                .withTrails(Collections.emptyList())
                .withMessages(errorMessages)
                .withStatus(Status.ERROR).build();
    }

    public void init() {
        // get all
        Spark.get(format("%s/get", PREFIX), ACCEPT_TYPE, this::get, JsonUtil.json());
        post(format("%s/geo", PREFIX), ACCEPT_TYPE, this::getGeo, JsonUtil.json());

        LOG.info("Trail CONTROLLER Started");
    }


}
