package org.ltrails.common.data;

public class TrailReference {
    private String code;
    private String postcode;

    public TrailReference(String code, String postcode) {
        this.code = code;
        this.postcode = postcode;
    }

    public String getCode() {
        return code;
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
