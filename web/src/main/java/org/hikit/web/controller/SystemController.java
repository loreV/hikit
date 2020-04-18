package org.hikit.web.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.JsonUtil;
import org.hikit.common.response.RESTResponse;
import org.hikit.common.response.Status;
import org.hikit.web.configuration.ConfigurationManager;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Collections;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.hikit.common.configuration.ConfigurationProperties.API_PREFIX;


public class SystemController {

    private final Logger LOG = getLogger(TrailController.class.getName());

    private static final String PREFIX = API_PREFIX + "/system";

    @Inject
    public SystemController() {
    }

    private RESTResponse test(final Request request, final Response response) {
        return new RESTResponse(Status.OK, Collections.emptyList());
    }

    public void init() {
        Spark.get(format("%s/", PREFIX), ConfigurationManager.ACCEPT_TYPE, this::test, JsonUtil.json());
        LOG.info("System CONTROLLER Started");
    }


}
