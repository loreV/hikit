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
import org.ltrails.web.request.validator.POIGeoRequestValidator;
import org.ltrails.web.request.validator.POIRequestValidator;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;


public class POIController {

    private final Logger LOG = getLogger(POIController.class.getName());

    private static final String PREFIX = API_PREFIX + "/trails";

    public static final String COMMA_ARRAY_ELEM_SEP = ",";
    public static String PARAM_TRAIL_CODE = "trailCode";
    public static String PARAM_POST_CODE = "postCode";
    public static String PARAM_COUNTRY = "country";
    public static String PARAM_TYPES = "types";


    private final TrailManager trailManager;
    private final POIRequestValidator trailRequestValidator;
    private final POIGeoRequestValidator trailGeoRequestValidator;
    private final GsonBeanHelper gsonBeanHelper;

    @Inject
    public POIController(final TrailManager trailManager,
                         final POIRequestValidator trailRequestValidator,
                         final POIGeoRequestValidator trailGeoRequestValidator,
                         final GsonBeanHelper gsonBeanHelper) {
        this.trailManager = trailManager;
        this.trailRequestValidator = trailRequestValidator;
        this.trailGeoRequestValidator = trailGeoRequestValidator;
        this.gsonBeanHelper = gsonBeanHelper;
    }

    public TrailRestResponse get(final Request request, final Response response) {
        final List<String> errorMessages = trailRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final String code = request.queryMap().get(PARAM_TRAIL_CODE).value();
        final String postCodes = request.queryMap().get(PARAM_POST_CODE).value();
        final String country = request.queryMap().get(PARAM_COUNTRY).value();

        final List<Trail> trailsByTrailCode = trailManager.getByTrailPostCodeCountry(
                isBlank(code) ? EMPTY : code,
                !isEmpty(postCodes) ? asList(postCodes.split(COMMA_ARRAY_ELEM_SEP)) :
                        Collections.emptyList(),
                isBlank(country) ? EMPTY : country);
        return trailRestResponseBuilder(trailsByTrailCode);
    }

    public TrailRestResponse getGeo(final Request request, final Response response) {
        final List<String> errorMessages = trailGeoRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final TrailsGeoRequest trailsGeoRequest = Objects.requireNonNull(gsonBeanHelper.getGsonBuilder())
                .fromJson(request.body(), TrailsGeoRequest.class);
        final List<Trail> trailsNearby = trailManager.getByGeo(trailsGeoRequest.getCoords(),
                trailsGeoRequest.getDistance().intValue(),
                trailsGeoRequest.getUom());
        return trailRestResponseBuilder(trailsNearby);
    }

    @NotNull
    private TrailRestResponse trailRestResponseBuilder(final List<Trail> trails) {
        final TrailRestResponse.TrailRestResponseBuilder trailRestResponseBuilder = TrailRestResponse.
                TrailRestResponseBuilder.aTrailRestResponse().withTrails(trails);
        if (trails.isEmpty()) {
            return trailRestResponseBuilder
                    .withMessages(Collections.singletonList("No POI found"))
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

    public void initEndpoint() {
        Spark.get(format("%s", PREFIX), ACCEPT_TYPE, this::get, JsonUtil.json());
        Spark.post(format("%s/geo", PREFIX), ACCEPT_TYPE, this::getGeo, JsonUtil.json());
        LOG.info("POI CONTROLLER Started");
    }


}
