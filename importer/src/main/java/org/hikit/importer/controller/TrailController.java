package org.hikit.importer.controller;

import com.google.inject.Inject;
import org.hikit.common.JsonUtil;
import org.hikit.common.web.controller.PublicController;
import org.hikit.importer.GpxManager;
import org.hikit.importer.model.TrailPreparationModel;
import org.hikit.importer.service.TrailImporterManager;
import spark.Request;
import spark.Response;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import static java.lang.String.format;
import static org.hikit.common.configuration.ConfigurationProperties.API_PREFIX;
import static org.hikit.importer.configuration.ConfigurationManager.TMP_FOLDER;
import static org.hikit.importer.configuration.ConfigurationManager.UPLOAD_DIR;
import static spark.Spark.get;
import static spark.Spark.post;

public class TrailController implements PublicController {

    private final static Logger LOGGER = Logger.getLogger(TrailController.class.getName());
    private final static String PREFIX = API_PREFIX + "/trail";

    public static final String MULTI_PART_JETTY_CONFIG = "org.eclipse.jetty.multipartConfig";
    public static final String FILE_INPUT_NAME = "gpxFile";
    public static final String CANNOT_READ_ERROR_MESSAGE = "Could not read GPX file.";

    private final GpxManager gpxManager;
    private final TrailImporterManager trailImporterManager;

    @Inject
    public TrailController(final GpxManager gpxManager,
                           final TrailImporterManager trailImporterManager) {
        this.gpxManager = gpxManager;
        this.trailImporterManager = trailImporterManager;
    }

    // trai/gpx
    private TrailPreparationModel readGpxFile(final Request request,
                                              final Response response) throws IOException {
        final Path tempFile = Files.createTempFile(UPLOAD_DIR.toPath(), "", "");
        request.attribute(MULTI_PART_JETTY_CONFIG, new MultipartConfigElement(String.format("/%s", TMP_FOLDER)));

        try (final InputStream input = request.raw().getPart(FILE_INPUT_NAME).getInputStream()) {
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (final ServletException e) {
            LOGGER.warning(CANNOT_READ_ERROR_MESSAGE + e.getMessage());
        }
        return gpxManager.getTrailPreparationFromGpx(tempFile);
    }

    // trail/import
    private TrailPreparationModel importTrail(final Request request,
                                              final Response response) throws IOException {
        throw new NotImplementedException();
    }


    public void init() {
        get(format("%s/crossing", PREFIX), this::readGpxFile, JsonUtil.json());
        post(format("%s/gpx", PREFIX), this::readGpxFile, JsonUtil.json());
        post(format("%s/import", PREFIX), this::readGpxFile, JsonUtil.json());
    }

}
