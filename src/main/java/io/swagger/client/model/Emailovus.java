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
    "emailAdresa",
    "nioBroj",
    "istrazivacZaNaucniSoftverID",
    "nioID",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Emailovus {

    @JsonProperty("emailAdresa")
    private String emailAdresa;
    @JsonProperty("nioBroj")
    private String nioBroj;
    @JsonProperty("istrazivacZaNaucniSoftverID")
    private Integer istrazivacZaNaucniSoftverID;
    @JsonProperty("nioID")
    private Integer nioID;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("emailAdresa")
    public String getEmailAdresa() {
        return emailAdresa;
    }

    @JsonProperty("emailAdresa")
    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public Emailovus withEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
        return this;
    }

    @JsonProperty("nioBroj")
    public String getNioBroj() {
        return nioBroj;
    }

    @JsonProperty("nioBroj")
    public void setNioBroj(String nioBroj) {
        this.nioBroj = nioBroj;
    }

    public Emailovus withNioBroj(String nioBroj) {
        this.nioBroj = nioBroj;
        return this;
    }

    @JsonProperty("istrazivacZaNaucniSoftverID")
    public Integer getIstrazivacZaNaucniSoftverID() {
        return istrazivacZaNaucniSoftverID;
    }

    @JsonProperty("istrazivacZaNaucniSoftverID")
    public void setIstrazivacZaNaucniSoftverID(Integer istrazivacZaNaucniSoftverID) {
        this.istrazivacZaNaucniSoftverID = istrazivacZaNaucniSoftverID;
    }

    public Emailovus withIstrazivacZaNaucniSoftverID(Integer istrazivacZaNaucniSoftverID) {
        this.istrazivacZaNaucniSoftverID = istrazivacZaNaucniSoftverID;
        return this;
    }

    @JsonProperty("nioID")
    public Integer getNioID() {
        return nioID;
    }

    @JsonProperty("nioID")
    public void setNioID(Integer nioID) {
        this.nioID = nioID;
    }

    public Emailovus withNioID(Integer nioID) {
        this.nioID = nioID;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Emailovus withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Emailovus withVersion(Integer version) {
        this.version = version;
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

    public Emailovus withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Emailovus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("emailAdresa");
        sb.append('=');
        sb.append(((this.emailAdresa == null)?"<null>":this.emailAdresa));
        sb.append(',');
        sb.append("nioBroj");
        sb.append('=');
        sb.append(((this.nioBroj == null)?"<null>":this.nioBroj));
        sb.append(',');
        sb.append("istrazivacZaNaucniSoftverID");
        sb.append('=');
        sb.append(((this.istrazivacZaNaucniSoftverID == null)?"<null>":this.istrazivacZaNaucniSoftverID));
        sb.append(',');
        sb.append("nioID");
        sb.append('=');
        sb.append(((this.nioID == null)?"<null>":this.nioID));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
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
        result = ((result* 31)+((this.nioID == null)? 0 :this.nioID.hashCode()));
        result = ((result* 31)+((this.istrazivacZaNaucniSoftverID == null)? 0 :this.istrazivacZaNaucniSoftverID.hashCode()));
        result = ((result* 31)+((this.nioBroj == null)? 0 :this.nioBroj.hashCode()));
        result = ((result* 31)+((this.emailAdresa == null)? 0 :this.emailAdresa.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Emailovus) == false) {
            return false;
        }
        Emailovus rhs = ((Emailovus) other);
        return ((((((((this.nioID == rhs.nioID)||((this.nioID!= null)&&this.nioID.equals(rhs.nioID)))&&((this.istrazivacZaNaucniSoftverID == rhs.istrazivacZaNaucniSoftverID)||((this.istrazivacZaNaucniSoftverID!= null)&&this.istrazivacZaNaucniSoftverID.equals(rhs.istrazivacZaNaucniSoftverID))))&&((this.nioBroj == rhs.nioBroj)||((this.nioBroj!= null)&&this.nioBroj.equals(rhs.nioBroj))))&&((this.emailAdresa == rhs.emailAdresa)||((this.emailAdresa!= null)&&this.emailAdresa.equals(rhs.emailAdresa))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))));
    }

}
