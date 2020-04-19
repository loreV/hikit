package org.hikit.importer.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.hikit.common.data.DataSource;
import org.hikit.importer.controller.ImporterController;
import spark.Spark;

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
    private final ImporterController importerController;


    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port,
                                final ImporterController importerController,
                                final DataSource dataSource) {
        this.importerController = importerController;
        this.dataSource = dataSource;
        Spark.staticFiles.location("/public"); // Static files
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
        importerController.init();
    }
}
