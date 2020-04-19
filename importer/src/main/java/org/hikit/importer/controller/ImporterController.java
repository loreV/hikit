package org.hikit.importer.controller;

import org.hikit.common.JsonUtil;
import org.hikit.common.web.controller.PublicController;
import spark.Request;
import spark.Response;

import static java.lang.String.format;
import static org.hikit.importer.configuration.ConfigurationManager.ACCEPT_TYPE;
import static spark.Spark.post;

public class ImporterController implements PublicController {

    private final static String PREFIX = "/import";

    private Object geo(Request request, Response response) {
        return null;
    }

    public void init() {
        post(format("%s/geo", PREFIX), ACCEPT_TYPE, this::geo, JsonUtil.json());
    }
}
