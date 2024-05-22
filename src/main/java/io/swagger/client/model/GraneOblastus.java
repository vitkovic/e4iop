package io.swagger.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "naucnaGranaID",
    "naucnaGranaNaziv",
    "naucnaOblastID",
    "naucnaOblastNaziv",
    "nioZaNaucniSoftverAkreditacijaID",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class GraneOblastus {

    @JsonProperty("naucnaGranaID")
    private Integer naucnaGranaID;
    @JsonProperty("naucnaGranaNaziv")
    private String naucnaGranaNaziv;
    @JsonProperty("naucnaOblastID")
    private Integer naucnaOblastID;
    @JsonProperty("naucnaOblastNaziv")
    private String naucnaOblastNaziv;
    @JsonProperty("nioZaNaucniSoftverAkreditacijaID")
    private Integer nioZaNaucniSoftverAkreditacijaID;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("naucnaGranaID")
    public Integer getNaucnaGranaID() {
        return naucnaGranaID;
    }

    @JsonProperty("naucnaGranaID")
    public void setNaucnaGranaID(Integer naucnaGranaID) {
        this.naucnaGranaID = naucnaGranaID;
    }

    public GraneOblastus withNaucnaGranaID(Integer naucnaGranaID) {
        this.naucnaGranaID = naucnaGranaID;
        return this;
    }

    @JsonProperty("naucnaGranaNaziv")
    public String getNaucnaGranaNaziv() {
        return naucnaGranaNaziv;
    }

    @JsonProperty("naucnaGranaNaziv")
    public void setNaucnaGranaNaziv(String naucnaGranaNaziv) {
        this.naucnaGranaNaziv = naucnaGranaNaziv;
    }

    public GraneOblastus withNaucnaGranaNaziv(String naucnaGranaNaziv) {
        this.naucnaGranaNaziv = naucnaGranaNaziv;
        return this;
    }

    @JsonProperty("naucnaOblastID")
    public Integer getNaucnaOblastID() {
        return naucnaOblastID;
    }

    @JsonProperty("naucnaOblastID")
    public void setNaucnaOblastID(Integer naucnaOblastID) {
        this.naucnaOblastID = naucnaOblastID;
    }

    public GraneOblastus withNaucnaOblastID(Integer naucnaOblastID) {
        this.naucnaOblastID = naucnaOblastID;
        return this;
    }

    @JsonProperty("naucnaOblastNaziv")
    public String getNaucnaOblastNaziv() {
        return naucnaOblastNaziv;
    }

    @JsonProperty("naucnaOblastNaziv")
    public void setNaucnaOblastNaziv(String naucnaOblastNaziv) {
        this.naucnaOblastNaziv = naucnaOblastNaziv;
    }

    public GraneOblastus withNaucnaOblastNaziv(String naucnaOblastNaziv) {
        this.naucnaOblastNaziv = naucnaOblastNaziv;
        return this;
    }

    @JsonProperty("nioZaNaucniSoftverAkreditacijaID")
    public Integer getNioZaNaucniSoftverAkreditacijaID() {
        return nioZaNaucniSoftverAkreditacijaID;
    }

    @JsonProperty("nioZaNaucniSoftverAkreditacijaID")
    public void setNioZaNaucniSoftverAkreditacijaID(Integer nioZaNaucniSoftverAkreditacijaID) {
        this.nioZaNaucniSoftverAkreditacijaID = nioZaNaucniSoftverAkreditacijaID;
    }

    public GraneOblastus withNioZaNaucniSoftverAkreditacijaID(Integer nioZaNaucniSoftverAkreditacijaID) {
        this.nioZaNaucniSoftverAkreditacijaID = nioZaNaucniSoftverAkreditacijaID;
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

    public GraneOblastus withId(Integer id) {
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

    public GraneOblastus withVersion(Integer version) {
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

    public GraneOblastus withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GraneOblastus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("naucnaGranaID");
        sb.append('=');
        sb.append(((this.naucnaGranaID == null)?"<null>":this.naucnaGranaID));
        sb.append(',');
        sb.append("naucnaGranaNaziv");
        sb.append('=');
        sb.append(((this.naucnaGranaNaziv == null)?"<null>":this.naucnaGranaNaziv));
        sb.append(',');
        sb.append("naucnaOblastID");
        sb.append('=');
        sb.append(((this.naucnaOblastID == null)?"<null>":this.naucnaOblastID));
        sb.append(',');
        sb.append("naucnaOblastNaziv");
        sb.append('=');
        sb.append(((this.naucnaOblastNaziv == null)?"<null>":this.naucnaOblastNaziv));
        sb.append(',');
        sb.append("nioZaNaucniSoftverAkreditacijaID");
        sb.append('=');
        sb.append(((this.nioZaNaucniSoftverAkreditacijaID == null)?"<null>":this.nioZaNaucniSoftverAkreditacijaID));
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
        result = ((result* 31)+((this.naucnaOblastID == null)? 0 :this.naucnaOblastID.hashCode()));
        result = ((result* 31)+((this.nioZaNaucniSoftverAkreditacijaID == null)? 0 :this.nioZaNaucniSoftverAkreditacijaID.hashCode()));
        result = ((result* 31)+((this.naucnaGranaNaziv == null)? 0 :this.naucnaGranaNaziv.hashCode()));
        result = ((result* 31)+((this.naucnaOblastNaziv == null)? 0 :this.naucnaOblastNaziv.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.naucnaGranaID == null)? 0 :this.naucnaGranaID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GraneOblastus) == false) {
            return false;
        }
        GraneOblastus rhs = ((GraneOblastus) other);
        return (((((((((this.naucnaOblastID == rhs.naucnaOblastID)||((this.naucnaOblastID!= null)&&this.naucnaOblastID.equals(rhs.naucnaOblastID)))&&((this.nioZaNaucniSoftverAkreditacijaID == rhs.nioZaNaucniSoftverAkreditacijaID)||((this.nioZaNaucniSoftverAkreditacijaID!= null)&&this.nioZaNaucniSoftverAkreditacijaID.equals(rhs.nioZaNaucniSoftverAkreditacijaID))))&&((this.naucnaGranaNaziv == rhs.naucnaGranaNaziv)||((this.naucnaGranaNaziv!= null)&&this.naucnaGranaNaziv.equals(rhs.naucnaGranaNaziv))))&&((this.naucnaOblastNaziv == rhs.naucnaOblastNaziv)||((this.naucnaOblastNaziv!= null)&&this.naucnaOblastNaziv.equals(rhs.naucnaOblastNaziv))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.naucnaGranaID == rhs.naucnaGranaID)||((this.naucnaGranaID!= null)&&this.naucnaGranaID.equals(rhs.naucnaGranaID))));
    }

}
