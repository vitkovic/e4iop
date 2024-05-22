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
    "identifikator",
    "urlProfila",
    "vrstaIdentifikatoraNaziv",
    "istrazivacZaNaucniSoftverID",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Identifikatorus {

    @JsonProperty("identifikator")
    private String identifikator;
    @JsonProperty("urlProfila")
    private String urlProfila;
    @JsonProperty("vrstaIdentifikatoraNaziv")
    private String vrstaIdentifikatoraNaziv;
    @JsonProperty("istrazivacZaNaucniSoftverID")
    private Integer istrazivacZaNaucniSoftverID;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("identifikator")
    public String getIdentifikator() {
        return identifikator;
    }

    @JsonProperty("identifikator")
    public void setIdentifikator(String identifikator) {
        this.identifikator = identifikator;
    }

    public Identifikatorus withIdentifikator(String identifikator) {
        this.identifikator = identifikator;
        return this;
    }

    @JsonProperty("urlProfila")
    public String getUrlProfila() {
        return urlProfila;
    }

    @JsonProperty("urlProfila")
    public void setUrlProfila(String urlProfila) {
        this.urlProfila = urlProfila;
    }

    public Identifikatorus withUrlProfila(String urlProfila) {
        this.urlProfila = urlProfila;
        return this;
    }

    @JsonProperty("vrstaIdentifikatoraNaziv")
    public String getVrstaIdentifikatoraNaziv() {
        return vrstaIdentifikatoraNaziv;
    }

    @JsonProperty("vrstaIdentifikatoraNaziv")
    public void setVrstaIdentifikatoraNaziv(String vrstaIdentifikatoraNaziv) {
        this.vrstaIdentifikatoraNaziv = vrstaIdentifikatoraNaziv;
    }

    public Identifikatorus withVrstaIdentifikatoraNaziv(String vrstaIdentifikatoraNaziv) {
        this.vrstaIdentifikatoraNaziv = vrstaIdentifikatoraNaziv;
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

    public Identifikatorus withIstrazivacZaNaucniSoftverID(Integer istrazivacZaNaucniSoftverID) {
        this.istrazivacZaNaucniSoftverID = istrazivacZaNaucniSoftverID;
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

    public Identifikatorus withId(Integer id) {
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

    public Identifikatorus withVersion(Integer version) {
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

    public Identifikatorus withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Identifikatorus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("identifikator");
        sb.append('=');
        sb.append(((this.identifikator == null)?"<null>":this.identifikator));
        sb.append(',');
        sb.append("urlProfila");
        sb.append('=');
        sb.append(((this.urlProfila == null)?"<null>":this.urlProfila));
        sb.append(',');
        sb.append("vrstaIdentifikatoraNaziv");
        sb.append('=');
        sb.append(((this.vrstaIdentifikatoraNaziv == null)?"<null>":this.vrstaIdentifikatoraNaziv));
        sb.append(',');
        sb.append("istrazivacZaNaucniSoftverID");
        sb.append('=');
        sb.append(((this.istrazivacZaNaucniSoftverID == null)?"<null>":this.istrazivacZaNaucniSoftverID));
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
        result = ((result* 31)+((this.istrazivacZaNaucniSoftverID == null)? 0 :this.istrazivacZaNaucniSoftverID.hashCode()));
        result = ((result* 31)+((this.vrstaIdentifikatoraNaziv == null)? 0 :this.vrstaIdentifikatoraNaziv.hashCode()));
        result = ((result* 31)+((this.identifikator == null)? 0 :this.identifikator.hashCode()));
        result = ((result* 31)+((this.urlProfila == null)? 0 :this.urlProfila.hashCode()));
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
        if ((other instanceof Identifikatorus) == false) {
            return false;
        }
        Identifikatorus rhs = ((Identifikatorus) other);
        return ((((((((this.istrazivacZaNaucniSoftverID == rhs.istrazivacZaNaucniSoftverID)||((this.istrazivacZaNaucniSoftverID!= null)&&this.istrazivacZaNaucniSoftverID.equals(rhs.istrazivacZaNaucniSoftverID)))&&((this.vrstaIdentifikatoraNaziv == rhs.vrstaIdentifikatoraNaziv)||((this.vrstaIdentifikatoraNaziv!= null)&&this.vrstaIdentifikatoraNaziv.equals(rhs.vrstaIdentifikatoraNaziv))))&&((this.identifikator == rhs.identifikator)||((this.identifikator!= null)&&this.identifikator.equals(rhs.identifikator))))&&((this.urlProfila == rhs.urlProfila)||((this.urlProfila!= null)&&this.urlProfila.equals(rhs.urlProfila))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))));
    }

}
