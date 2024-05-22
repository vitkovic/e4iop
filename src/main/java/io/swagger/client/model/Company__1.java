package io.swagger.client.model;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "regNo",
    "taxNo"
})
@Generated("jsonschema2pojo")
public class Company__1 {

    @JsonProperty("name")
    private String name;
    @JsonProperty("regNo")
    private String regNo;
    @JsonProperty("taxNo")
    private String taxNo;
    
    @JsonProperty("address")
    private String address;
    
    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Company__1 withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("regNo")
    public String getRegNo() {
        return regNo;
    }

    @JsonProperty("regNo")
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Company__1 withRegNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    @JsonProperty("taxNo")
    public String getTaxNo() {
        return taxNo;
    }

    @JsonProperty("taxNo")
    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public Company__1 withTaxNo(String taxNo) {
        this.taxNo = taxNo;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Company__1 withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Company__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("regNo");
        sb.append('=');
        sb.append(((this.regNo == null)?"<null>":this.regNo));
        sb.append(',');
        sb.append("taxNo");
        sb.append('=');
        sb.append(((this.taxNo == null)?"<null>":this.taxNo));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.regNo == null)? 0 :this.regNo.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.taxNo == null)? 0 :this.taxNo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Company__1) == false) {
            return false;
        }
        Company__1 rhs = ((Company__1) other);
        return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.regNo == rhs.regNo)||((this.regNo!= null)&&this.regNo.equals(rhs.regNo))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.taxNo == rhs.taxNo)||((this.taxNo!= null)&&this.taxNo.equals(rhs.taxNo))));
    }

}
