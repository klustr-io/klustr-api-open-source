
package io.klustr.schemas.console.features;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * CompanyReference
 * <p>
 * The core provider of this project feature can be first or third party
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "company_id",
    "company_name",
    "company_type"
})
@Generated("jsonschema2pojo")
public class CompanyReference {

    /**
     * The company ID
     * 
     */
    @JsonProperty("company_id")
    @JsonPropertyDescription("The company ID")
    private String companyId;
    /**
     * The name of the company providing this feature.
     * 
     */
    @JsonProperty("company_name")
    @JsonPropertyDescription("The name of the company providing this feature.")
    private String companyName;
    @JsonProperty("company_type")
    private CompanyType companyType;

    /**
     * The company ID
     * 
     */
    @JsonProperty("company_id")
    public String getCompanyId() {
        return companyId;
    }

    /**
     * The company ID
     * 
     */
    @JsonProperty("company_id")
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public CompanyReference withCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    /**
     * The name of the company providing this feature.
     * 
     */
    @JsonProperty("company_name")
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The name of the company providing this feature.
     * 
     */
    @JsonProperty("company_name")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyReference withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @JsonProperty("company_type")
    public CompanyType getCompanyType() {
        return companyType;
    }

    @JsonProperty("company_type")
    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public CompanyReference withCompanyType(CompanyType companyType) {
        this.companyType = companyType;
        return this;
    }

}
