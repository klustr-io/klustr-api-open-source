
package io.klustr.schemas.persons.verification;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * VerificationEvidence
 * <p>
 * What and how information was verified.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "verification_id",
    "verification_status",
    "verification_method",
    "submit_date",
    "verification_date",
    "expiration_date",
    "vendor_data",
    "source_system",
    "reference_url"
})
@Generated("jsonschema2pojo")
public class VerificationEvidence {

    /**
     * The id that was used to verify.
     * 
     */
    @JsonProperty("verification_id")
    @JsonPropertyDescription("The id that was used to verify.")
    private String verificationId;
    /**
     * Who had the status
     * 
     */
    @JsonProperty("verification_status")
    @JsonPropertyDescription("Who had the status")
    private VerificationEvidence.VerificationStatus verificationStatus;
    /**
     * The method of verification
     * 
     */
    @JsonProperty("verification_method")
    @JsonPropertyDescription("The method of verification")
    private VerificationEvidence.VerificationMethod verificationMethod;
    /**
     * The date this verification was submitted
     * 
     */
    @JsonProperty("submit_date")
    @JsonPropertyDescription("The date this verification was submitted")
    private DateTime submitDate;
    /**
     * The date this verification was performed.
     * 
     */
    @JsonProperty("verification_date")
    @JsonPropertyDescription("The date this verification was performed.")
    private DateTime verificationDate;
    /**
     * The date this verification expires and needs to be re-performed.
     * 
     */
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("The date this verification expires and needs to be re-performed.")
    private DateTime expirationDate;
    /**
     * The vendor data associated with this for linkage and consistency.
     * 
     */
    @JsonProperty("vendor_data")
    @JsonPropertyDescription("The vendor data associated with this for linkage and consistency.")
    private String vendorData;
    /**
     * The system which perfomed this verification.
     * 
     */
    @JsonProperty("source_system")
    @JsonPropertyDescription("The system which perfomed this verification.")
    private String sourceSystem;
    /**
     * When applied the url of the data or document that was used to verified.
     * 
     */
    @JsonProperty("reference_url")
    @JsonPropertyDescription("When applied the url of the data or document that was used to verified.")
    private URI referenceUrl;

    /**
     * The id that was used to verify.
     * 
     */
    @JsonProperty("verification_id")
    public String getVerificationId() {
        return verificationId;
    }

    /**
     * The id that was used to verify.
     * 
     */
    @JsonProperty("verification_id")
    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public VerificationEvidence withVerificationId(String verificationId) {
        this.verificationId = verificationId;
        return this;
    }

    /**
     * Who had the status
     * 
     */
    @JsonProperty("verification_status")
    public VerificationEvidence.VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * Who had the status
     * 
     */
    @JsonProperty("verification_status")
    public void setVerificationStatus(VerificationEvidence.VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public VerificationEvidence withVerificationStatus(VerificationEvidence.VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    /**
     * The method of verification
     * 
     */
    @JsonProperty("verification_method")
    public VerificationEvidence.VerificationMethod getVerificationMethod() {
        return verificationMethod;
    }

    /**
     * The method of verification
     * 
     */
    @JsonProperty("verification_method")
    public void setVerificationMethod(VerificationEvidence.VerificationMethod verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public VerificationEvidence withVerificationMethod(VerificationEvidence.VerificationMethod verificationMethod) {
        this.verificationMethod = verificationMethod;
        return this;
    }

    /**
     * The date this verification was submitted
     * 
     */
    @JsonProperty("submit_date")
    public DateTime getSubmitDate() {
        return submitDate;
    }

    /**
     * The date this verification was submitted
     * 
     */
    @JsonProperty("submit_date")
    public void setSubmitDate(DateTime submitDate) {
        this.submitDate = submitDate;
    }

    public VerificationEvidence withSubmitDate(DateTime submitDate) {
        this.submitDate = submitDate;
        return this;
    }

    /**
     * The date this verification was performed.
     * 
     */
    @JsonProperty("verification_date")
    public DateTime getVerificationDate() {
        return verificationDate;
    }

    /**
     * The date this verification was performed.
     * 
     */
    @JsonProperty("verification_date")
    public void setVerificationDate(DateTime verificationDate) {
        this.verificationDate = verificationDate;
    }

    public VerificationEvidence withVerificationDate(DateTime verificationDate) {
        this.verificationDate = verificationDate;
        return this;
    }

    /**
     * The date this verification expires and needs to be re-performed.
     * 
     */
    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date this verification expires and needs to be re-performed.
     * 
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public VerificationEvidence withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    /**
     * The vendor data associated with this for linkage and consistency.
     * 
     */
    @JsonProperty("vendor_data")
    public String getVendorData() {
        return vendorData;
    }

    /**
     * The vendor data associated with this for linkage and consistency.
     * 
     */
    @JsonProperty("vendor_data")
    public void setVendorData(String vendorData) {
        this.vendorData = vendorData;
    }

    public VerificationEvidence withVendorData(String vendorData) {
        this.vendorData = vendorData;
        return this;
    }

    /**
     * The system which perfomed this verification.
     * 
     */
    @JsonProperty("source_system")
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * The system which perfomed this verification.
     * 
     */
    @JsonProperty("source_system")
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public VerificationEvidence withSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
        return this;
    }

    /**
     * When applied the url of the data or document that was used to verified.
     * 
     */
    @JsonProperty("reference_url")
    public URI getReferenceUrl() {
        return referenceUrl;
    }

    /**
     * When applied the url of the data or document that was used to verified.
     * 
     */
    @JsonProperty("reference_url")
    public void setReferenceUrl(URI referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public VerificationEvidence withReferenceUrl(URI referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }


    /**
     * The method of verification
     * 
     */
    @Generated("jsonschema2pojo")
    public enum VerificationMethod {


        /**
         * The method of verification was a government issued ID.
         * 
         */
        GOVERNMENT_ISSUED_ID("GOVERNMENT_ISSUED_ID"),

        /**
         * The method of verification was a utility bill for the person or household.
         * 
         */
        UTILITY_BILL("UTILITY_BILL"),

        /**
         * The method of verification was a digital token such as a digital passcode.
         * 
         */
        DIGITAL_CODE("DIGITAL_CODE"),

        /**
         * The method of verification was a digital token such as a digital passcode.
         * 
         */
        SENT_VERIFICATION_CODE("SENT_VERIFICATION_CODE"),

        /**
         * The method of verification was performed manually by a human inspection.
         * 
         */
        MANUAL("MANUAL"),

        /**
         * The information was not verified or not verification method was captured.
         * 
         */
        NOT_VERIFIED("NOT_VERIFIED"),

        /**
         * Legally binding and certified contract was provided as verification.
         * 
         */
        CONTRACT("CONTRACT");
        private final String value;
        private final static Map<String, VerificationEvidence.VerificationMethod> CONSTANTS = new HashMap<String, VerificationEvidence.VerificationMethod>();

        static {
            for (VerificationEvidence.VerificationMethod c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VerificationMethod(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static VerificationEvidence.VerificationMethod fromValue(String value) {
            VerificationEvidence.VerificationMethod constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Who had the status
     * 
     */
    @Generated("jsonschema2pojo")
    public enum VerificationStatus {

        PENDING("PENDING"),
        REJECTED("REJECTED"),
        APPROVED("APPROVED"),
        DRAFT("DRAFT");
        private final String value;
        private final static Map<String, VerificationEvidence.VerificationStatus> CONSTANTS = new HashMap<String, VerificationEvidence.VerificationStatus>();

        static {
            for (VerificationEvidence.VerificationStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VerificationStatus(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static VerificationEvidence.VerificationStatus fromValue(String value) {
            VerificationEvidence.VerificationStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
