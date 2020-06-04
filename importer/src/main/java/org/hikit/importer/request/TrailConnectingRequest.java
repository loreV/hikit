package org.hikit.importer.request;

import org.hikit.common.data.Coordinates;
import org.hikit.common.data.CoordinatesWithAltitude;

import java.util.List;

public class TrailConnectingRequest {
    List<CoordinatesWithAltitude> coordinatesWithAltitudes;

    public TrailConnectingRequest(List<CoordinatesWithAltitude> coordinatesWithAltitudes) {
        this.coordinatesWithAltitudes = coordinatesWithAltitudes;
    }

    public List<CoordinatesWithAltitude> getCoordinatesWithAltitudes() {
        return coordinatesWithAltitudes;
    }
}
