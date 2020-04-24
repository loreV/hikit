package org.hikit.importer.controller;

import com.google.inject.Inject;
import org.hikit.common.JsonUtil;
import org.hikit.common.web.controller.PublicController;
import org.hikit.importer.GpxManager;
import org.hikit.importer.model.TrailPreparationModel;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import static java.lang.String.format;
import static org.hikit.importer.configuration.ConfigurationManager.UPLOAD_DIR;
import static spark.Spark.post;

public class ImporterController implements PublicController {

    private final static Logger LOGGER = Logger.getLogger(ImporterController.class.getName());

    private final static String PREFIX = "/import";
    public static final String MULTI_PART_JETTY_CONFIG = "org.eclipse.jetty.multipartConfig";

    private final GpxManager gpxManager;

    @Inject
    public ImporterController(final GpxManager gpxManager) {

        this.gpxManager = gpxManager;
    }

    // import/gpx
    private TrailPreparationModel readGpxFile(final Request request,
                                              final Response response) throws IOException {
        final Path tempFile = Files.createTempFile(UPLOAD_DIR.toPath(), "", "");
        request.attribute(MULTI_PART_JETTY_CONFIG, new MultipartConfigElement("/temp"));

        try (final InputStream input = request.raw().getPart("gpxFile").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (final ServletException e) {
            LOGGER.warning("Could not read GPX file." + e.getMessage());
        }
        return gpxManager.getTrailPreparationFromGpx(tempFile);
    }

    public void init() {
        post(format("%s", PREFIX), this::readGpxFile, JsonUtil.json());
    }
}
