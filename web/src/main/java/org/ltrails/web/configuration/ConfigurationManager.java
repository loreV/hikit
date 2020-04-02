package org.ltrails.web.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.ltrails.common.data.DataSource;
import org.ltrails.web.controller.SystemController;
import org.ltrails.web.controller.TrailController;

import javax.inject.Named;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private final SystemController systemController;
    private final DataSource dataSource;

    private static final String PORT_PROPERTY = "web-port";

    public static final String ACCEPT_TYPE = "application/json";


    /**
     * Controllers
     */
    private final TrailController dataPointDAO;

    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port,
                                final TrailController trailControllerController,
                                final SystemController systemController,
                                final DataSource dataSource) {
        this.dataPointDAO = trailControllerController;
        this.systemController = systemController;
        this.dataSource = dataSource;
        port(Integer.parseInt(port));
    }

    public void init() {
        startController();
        testConnectionWithDB();
        LOG.info(format("Configuration completed. Listening on port %s", port()));

    }

    private void testConnectionWithDB() {
        dataSource.getClient().listDatabases();
    }

    private void startController() {
        dataPointDAO.init();
        systemController.init();
    }
}
