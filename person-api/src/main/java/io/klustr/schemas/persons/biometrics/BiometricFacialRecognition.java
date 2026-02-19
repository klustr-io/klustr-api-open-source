
package io.klustr.schemas.persons.biometrics;

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
 * BiometricFacialRecognition
 * <p>
 * Metadata regarding biometric facial recognition.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "url",
    "status",
    "status_details",
    "creation_date",
    "expiration_date"
})
@Generated("jsonschema2pojo")
public class BiometricFacialRecognition {

    /**
     * The unique ID of this facial recognition record.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this facial recognition record.")
    private String id;
    /**
     * The url for the facial recognition image.
     * 
     */
    @JsonProperty("url")
    @JsonPropertyDescription("The url for the facial recognition image.")
    private String url;
    /**
     * BiometricFacialRecognitionStatus
     * <p>
     * The status of this facial recognition.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this facial recognition.")
    private BiometricFacialRecognition.BiometricFacialRecognitionStatus status;
    /**
     * The code or status details that help with diagnosing an issue with the validation of this biometric
     * 
     */
    @JsonProperty("status_details")
    @JsonPropertyDescription("The code or status details that help with diagnosing an issue with the validation of this biometric")
    private String statusDetails;
    /**
     * The date this record was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this record was created.")
    private DateTime creationDate;
    /**
     * The date that this record expires.
     * 
     */
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("The date that this record expires.")
    private DateTime expirationDate;

    /**
     * The unique ID of this facial recognition record.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this facial recognition record.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BiometricFacialRecognition withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The url for the facial recognition image.
     * 
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * The url for the facial recognition image.
     * 
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public BiometricFacialRecognition withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * BiometricFacialRecognitionStatus
     * <p>
     * The status of this facial recognition.
     * 
     */
    @JsonProperty("status")
    public BiometricFacialRecognition.BiometricFacialRecognitionStatus getStatus() {
        return status;
    }

    /**
     * BiometricFacialRecognitionStatus
     * <p>
     * The status of this facial recognition.
     * 
     */
    @JsonProperty("status")
    public void setStatus(BiometricFacialRecognition.BiometricFacialRecognitionStatus status) {
        this.status = status;
    }

    public BiometricFacialRecognition withStatus(BiometricFacialRecognition.BiometricFacialRecognitionStatus status) {
        this.status = status;
        return this;
    }

    /**
     * The code or status details that help with diagnosing an issue with the validation of this biometric
     * 
     */
    @JsonProperty("status_details")
    public String getStatusDetails() {
        return statusDetails;
    }

    /**
     * The code or status details that help with diagnosing an issue with the validation of this biometric
     * 
     */
    @JsonProperty("status_details")
    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public BiometricFacialRecognition withStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
        return this;
    }

    /**
     * The date this record was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this record was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public BiometricFacialRecognition withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date that this record expires.
     * 
     */
    @JsonProperty("expiration_date")
    public DateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date that this record expires.
     * 
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BiometricFacialRecognition withExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }


    /**
     * BiometricFacialRecognitionStatus
     * <p>
     * The status of this facial recognition.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum BiometricFacialRecognitionStatus {


        /**
         * The facial recognition is drafted but not submitted.
         * 
         */
        DRAFT("DRAFT"),

        /**
         * The persons residency is based on a resident visa with an expiration.
         * 
         */
        SUBMITTED("SUBMITTED"),

        /**
         * The facial recognition is under review.
         * 
         */
        REVIEWING("REVIEWING"),

        /**
         * The facial recognition is invalid a new photo or evidence should be retaken.
         * 
         */
        INVALID("INVALID"),

        /**
         * The status is valid and available.
         * 
         */
        VALID("VALID");
        private final String value;
        private final static Map<String, BiometricFacialRecognition.BiometricFacialRecognitionStatus> CONSTANTS = new HashMap<String, BiometricFacialRecognition.BiometricFacialRecognitionStatus>();

        static {
            for (BiometricFacialRecognition.BiometricFacialRecognitionStatus c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        BiometricFacialRecognitionStatus(String value) {
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
        public static BiometricFacialRecognition.BiometricFacialRecognitionStatus fromValue(String value) {
            BiometricFacialRecognition.BiometricFacialRecognitionStatus constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
