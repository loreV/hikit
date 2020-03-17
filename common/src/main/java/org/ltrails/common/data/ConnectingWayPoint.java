package org.ltrails.common.data;

public class ConnectingWayPoint {

    public static final String CODE = "code";
    public static String POSITION = "connectingPosition";
    public static String POSITION_CONNECTING_FROM = "connectingTo";
    public static String POSITION_CONNECTING_TO = "connectingFrom";

    private Position position;
    private final TrailReference connectingTo;
    private final TrailReference connectingFrom;
    private final String code;
    private final String area;
    private final String county;

    public ConnectingWayPoint(Position position,
                              TrailReference connectingTo,
                              TrailReference connectingFrom,
                              String code, String county,
                              String area) {
        this.position = position;
        this.connectingTo = connectingTo;
        this.connectingFrom = connectingFrom;
        this.code = code;
        this.county = county;
        this.area = area;

    }

    public String getArea() {
        return area;
    }

    public String getCounty() {
        return county;
    }

    public Position getPosition() {
        return position;
    }

    public TrailReference getConnectingTo() {
        return connectingTo;
    }

    public TrailReference getConnectingFrom() {
        return connectingFrom;
    }

    public String getCode() {
        return code;
    }


    public static final class ConnectingWayPointBuilder {
        private Position position;
        private TrailReference connectingTo;
        private TrailReference connectingFrom;
        private String code;
        private String area;
        private String county;

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

        public ConnectingWayPointBuilder withConnectingFrom(TrailReference connectingFrom) {
            this.connectingFrom = connectingFrom;
            return this;
        }

        public ConnectingWayPointBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ConnectingWayPointBuilder withArea(String area) {
            this.area = area;
            return this;
        }

        public ConnectingWayPointBuilder withCounty(String county) {
            this.county = county;
            return this;
        }

        public ConnectingWayPoint build() {
            return new ConnectingWayPoint(position, connectingTo, connectingFrom, code, county, area);
        }
    }
}
