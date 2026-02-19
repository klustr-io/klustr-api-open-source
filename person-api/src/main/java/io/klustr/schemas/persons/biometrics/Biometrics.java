
package io.klustr.schemas.persons.biometrics;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Biometrics
 * <p>
 * The biometrics for the person
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "facial_recognition_photos"
})
@Generated("jsonschema2pojo")
public class Biometrics {

    /**
     * The photos that make up facial recognition.
     * 
     */
    @JsonProperty("facial_recognition_photos")
    @JsonPropertyDescription("The photos that make up facial recognition.")
    private List<BiometricFacialRecognition> facialRecognitionPhotos = new ArrayList<BiometricFacialRecognition>();

    /**
     * The photos that make up facial recognition.
     * 
     */
    @JsonProperty("facial_recognition_photos")
    public List<BiometricFacialRecognition> getFacialRecognitionPhotos() {
        return facialRecognitionPhotos;
    }

    /**
     * The photos that make up facial recognition.
     * 
     */
    @JsonProperty("facial_recognition_photos")
    public void setFacialRecognitionPhotos(List<BiometricFacialRecognition> facialRecognitionPhotos) {
        this.facialRecognitionPhotos = facialRecognitionPhotos;
    }

    public Biometrics withFacialRecognitionPhotos(List<BiometricFacialRecognition> facialRecognitionPhotos) {
        this.facialRecognitionPhotos = facialRecognitionPhotos;
        return this;
    }

}
