package org.hikit.common.helper;

import io.jenetics.jpx.GPX;

import java.io.IOException;
import java.nio.file.Path;

public class GpxHelper {

    public GPX readFromFile(Path path) throws IOException {
        return GPX.read(path);
    }

    ;

}
