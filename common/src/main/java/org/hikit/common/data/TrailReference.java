package org.hikit.common.data;

public class TrailReference {

    public static String TRAIL_CODE = "trailCode";
    public static String POSTCODE = "postCode";
    public static String COUNTRY = "country";

    private String trailCode;
    private String postcode;
    private String country;

    public TrailReference(final String trailCode,
                          final String postcode,
                          final String country) {
        this.trailCode = trailCode;
        this.postcode = postcode;
        this.country = country;
    }

    public String getTrailCode() {
        return trailCode;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public static final class TrailReferenceBuilder {
        private String code;
        private String postcode;
        private String country;

        private TrailReferenceBuilder() {
        }

        public static TrailReferenceBuilder aTrailReference() {
            return new TrailReferenceBuilder();
        }

        public TrailReferenceBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public TrailReferenceBuilder withPostCode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public TrailReferenceBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public TrailReference build() {
            return new TrailReference(code, postcode, country);
        }
    }
}
