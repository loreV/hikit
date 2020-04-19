package org.hikit.common.data;

public class CoordinatesDelta {

    private Coordinates coordinates;
    private int deltaDistance;
    private UnitOfMeasurement unitOfMeasurement;

    public CoordinatesDelta(final Coordinates coordinates,
                            final int deltaDistance,
                            final UnitOfMeasurement unitOfMeasurement) {
        this.coordinates = coordinates;
        this.deltaDistance = deltaDistance;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getDeltaDistance() {
        return deltaDistance;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }


    public static final class CoordinatesDeltaBuilder {
        private Coordinates coordinates;
        private int deltaDistance;
        private UnitOfMeasurement unitOfMeasurement;

        private CoordinatesDeltaBuilder() {
        }

        public static CoordinatesDeltaBuilder aCoordinatesDelta() {
            return new CoordinatesDeltaBuilder();
        }

        public CoordinatesDeltaBuilder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public CoordinatesDeltaBuilder withDeltaDistance(int deltaDistance) {
            this.deltaDistance = deltaDistance;
            return this;
        }

        public CoordinatesDeltaBuilder withUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
            this.unitOfMeasurement = unitOfMeasurement;
            return this;
        }

        public CoordinatesDelta build() {
            return new CoordinatesDelta(coordinates, deltaDistance, unitOfMeasurement);
        }
    }
}
