package org.ltrails.common.data;

public class CoordinatesDelta {

    private Coordinates coordinates;
    private double deltaDistance;
    private UnitOfMeasurement unitOfMeasurement;

    public CoordinatesDelta(final Coordinates coordinates,
                            final double deltaDistance,
                            final UnitOfMeasurement unitOfMeasurement) {
        this.coordinates = coordinates;
        this.deltaDistance = deltaDistance;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getDeltaDistance() {
        return deltaDistance;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }


    public static final class CoordinatesDeltaBuilder {
        private Coordinates coordinates;
        private double deltaDistance;
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

        public CoordinatesDeltaBuilder withDeltaDistance(double deltaDistance) {
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
