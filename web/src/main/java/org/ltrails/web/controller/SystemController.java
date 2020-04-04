package org.ltrails.web.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.ltrails.common.JsonUtil;
import org.ltrails.common.response.RESTResponse;
import org.ltrails.common.response.Status;
import spark.Request;
import spark.Response;

import java.util.Collections;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.ltrails.web.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.get;


public class SystemController {

    private final Logger LOG = getLogger(TrailController.class.getName());

    private static final String PREFIX = "/system";

    @Inject
    public SystemController() {
    }

    private RESTResponse test(final Request request, final Response response) {
        return new RESTResponse(Status.OK, Collections.emptyList());
    }

    public void init() {
        get(format("%s/", PREFIX), ACCEPT_TYPE, this::test, JsonUtil.json());
        LOG.info("System CONTROLLER Started");
    }


}
