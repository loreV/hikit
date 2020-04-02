package org.ltrails.common.data;

public class TrailReference {

    public static String TRAIL_CODE = "trailCode";
    public static String POSTCODE = "postCode";

    private String trailCode;
    private String postcode;

    public TrailReference(String trailCode, String postcode) {
        this.trailCode = trailCode;
        this.postcode = postcode;
    }

    public String getTrailCode() {
        return trailCode;
    }

    public String getPostcode() {
        return postcode;
    }


    public static final class TrailReferenceBuilder {
        private String code;
        private String postcode;

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

        public TrailReference build() {
            return new TrailReference(code, postcode);
        }
    }
}
