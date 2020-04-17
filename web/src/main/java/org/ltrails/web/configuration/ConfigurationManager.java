package org.ltrails.web.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.ltrails.common.data.DataSource;
import org.ltrails.web.controller.PoiController;
import org.ltrails.web.controller.RouteController;
import org.ltrails.web.controller.SystemController;
import org.ltrails.web.controller.TrailController;

import javax.inject.Named;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private final DataSource dataSource;

    private static final String PORT_PROPERTY = "web-port";

    public static final String ACCEPT_TYPE = "application/json";


    /**
     * Controllers
     */
    private final TrailController trailController;
    private final PoiController poiController;
    private final SystemController systemController;
    private final RouteController routeController;


    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port,
                                final TrailController trailController,
                                final PoiController poiController,
                                final SystemController systemController,
                                final RouteController routeController,
                                final DataSource dataSource) {
        this.trailController = trailController;
        this.poiController = poiController;
        this.systemController = systemController;
        this.dataSource = dataSource;
        this.routeController = routeController;
        port(Integer.parseInt(port));
    }

    public void init() {
        startControllers();
        testConnectionWithDB();
        LOG.info(format("Configuration completed. Listening on port %s", port()));
    }

    private void testConnectionWithDB() {
        dataSource.getClient().listDatabases();
    }

    private void startControllers() {
        trailController.init();
        poiController.init();
        systemController.init();
        routeController.init();
    }
}
