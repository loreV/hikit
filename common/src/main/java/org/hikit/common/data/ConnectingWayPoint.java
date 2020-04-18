package org.hikit.common.data;

public class ConnectingWayPoint {

    public static String POSITION = "position";
    public static String POSITION_CONNECTING_TO = "connectingTrail";

    private Position position;
    private final TrailReference connectingTo;

    public ConnectingWayPoint(Position position,
                              TrailReference connectingTo) {
        this.position = position;
        this.connectingTo = connectingTo;
    }

    public Position getPosition() {
        return position;
    }

    public TrailReference getConnectingTo() {
        return connectingTo;
    }


    public static final class ConnectingWayPointBuilder {
        private Position position;
        private TrailReference connectingTo;

        private ConnectingWayPointBuilder() {
        }

        public static ConnectingWayPointBuilder aConnectingWayPoint() {
            return new ConnectingWayPointBuilder();
        }

        public ConnectingWayPointBuilder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public ConnectingWayPointBuilder withConnectingTo(TrailReference connectingTo) {
            this.connectingTo = connectingTo;
            return this;
        }

        public ConnectingWayPoint build() {
            return new ConnectingWayPoint(position, connectingTo);
        }
    }
}
