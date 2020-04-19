package org.hikit.web.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.JsonUtil;
import org.hikit.common.data.Trail;
import org.hikit.common.data.helper.GsonBeanHelper;
import org.hikit.common.response.Status;
import org.hikit.common.response.TrailRestResponse;
import org.hikit.web.TrailManager;
import org.hikit.web.request.TrailsGeoRequest;
import org.hikit.web.request.validator.TrailGeoRequestValidator;
import org.hikit.web.request.validator.TrailRequestValidator;
import org.jetbrains.annotations.NotNull;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hikit.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.hikit.web.configuration.ConfigurationManager.ACCEPT_TYPE;


public class TrailController implements PublicController {

    private final Logger LOG = getLogger(TrailController.class.getName());

    private static final String PREFIX = API_PREFIX + "/trails";

    public static final String COMMA_ARRAY_ELEM_SEP = ",";
    public static String PARAM_TRAIL_CODE = "trailCode";
    public static String PARAM_POST_CODE = "postCode";
    public static String PARAM_COUNTRY = "country";


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

    public TrailRestResponse get(final Request request, final Response response) {
        final Set<String> errorMessages = trailRequestValidator.validate(request);
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
        response.type(ACCEPT_TYPE);
        return trailRestResponseBuilder(trailsByTrailCode);
    }

    public TrailRestResponse getGeo(final Request request, final Response response) {
        final Set<String> errorMessages = trailGeoRequestValidator.validate(request);
        if (!errorMessages.isEmpty()) {
            return buildErrorResponse(errorMessages);
        }
        final TrailsGeoRequest trailsGeoRequest = Objects.requireNonNull(gsonBeanHelper.getGsonBuilder())
                .fromJson(request.body(), TrailsGeoRequest.class);
        final List<Trail> trailsNearby = trailManager.getByGeo(trailsGeoRequest.getCoords(),
                trailsGeoRequest.getDistance().intValue(),
                trailsGeoRequest.getUom());
        response.type(ACCEPT_TYPE);
        return trailRestResponseBuilder(trailsNearby);
    }

    @NotNull
    private TrailRestResponse trailRestResponseBuilder(final List<Trail> trails) {
        final TrailRestResponse.TrailRestResponseBuilder trailRestResponseBuilder = TrailRestResponse.
                TrailRestResponseBuilder.aTrailRestResponse().withTrails(trails);
        if (trails.isEmpty()) {
            return trailRestResponseBuilder
                    .withMessages(Collections.singleton("No trails found"))
                    .withStatus(Status.ERROR).build();
        }
        return trailRestResponseBuilder
                .withMessages(Collections.emptySet())
                .withStatus(Status.OK).build();
    }

    @NotNull
    private TrailRestResponse buildErrorResponse(final Set<String> errorMessages) {
        return TrailRestResponse.TrailRestResponseBuilder.aTrailRestResponse()
                .withTrails(Collections.emptyList())
                .withMessages(errorMessages)
                .withStatus(Status.ERROR).build();
    }

    public void init() {
        Spark.get(format("%s", PREFIX), ACCEPT_TYPE, this::get, JsonUtil.json());
        Spark.post(format("%s/geo", PREFIX), ACCEPT_TYPE, this::getGeo, JsonUtil.json());
        LOG.info("Trail CONTROLLER Started");
    }


}
