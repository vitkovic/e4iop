package io.swagger.client.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    "brojAktaOAkreditaciji",
    "datumAktaOAkreditaciji",
    "datumIstekaAkreditacije",
    "nioZaNaucniSoftverID",
    "graneOblasti",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Akreditacije {

    @JsonProperty("brojAktaOAkreditaciji")
    private String brojAktaOAkreditaciji;
    @JsonProperty("datumAktaOAkreditaciji")
    private String datumAktaOAkreditaciji;
    @JsonProperty("datumIstekaAkreditacije")
    private String datumIstekaAkreditacije;
    @JsonProperty("nioZaNaucniSoftverID")
    private Integer nioZaNaucniSoftverID;
    @JsonProperty("graneOblasti")
    private List<GraneOblastus> graneOblasti = new ArrayList<GraneOblastus>();
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("brojAktaOAkreditaciji")
    public String getBrojAktaOAkreditaciji() {
        return brojAktaOAkreditaciji;
    }

    @JsonProperty("brojAktaOAkreditaciji")
    public void setBrojAktaOAkreditaciji(String brojAktaOAkreditaciji) {
        this.brojAktaOAkreditaciji = brojAktaOAkreditaciji;
    }

    public Akreditacije withBrojAktaOAkreditaciji(String brojAktaOAkreditaciji) {
        this.brojAktaOAkreditaciji = brojAktaOAkreditaciji;
        return this;
    }

    @JsonProperty("datumAktaOAkreditaciji")
    public String getDatumAktaOAkreditaciji() {
        return datumAktaOAkreditaciji;
    }

    @JsonProperty("datumAktaOAkreditaciji")
    public void setDatumAktaOAkreditaciji(String datumAktaOAkreditaciji) {
        this.datumAktaOAkreditaciji = datumAktaOAkreditaciji;
    }

    public Akreditacije withDatumAktaOAkreditaciji(String datumAktaOAkreditaciji) {
        this.datumAktaOAkreditaciji = datumAktaOAkreditaciji;
        return this;
    }

    @JsonProperty("datumIstekaAkreditacije")
    public String getDatumIstekaAkreditacije() {
        return datumIstekaAkreditacije;
    }

    @JsonProperty("datumIstekaAkreditacije")
    public void setDatumIstekaAkreditacije(String datumIstekaAkreditacije) {
        this.datumIstekaAkreditacije = datumIstekaAkreditacije;
    }

    public Akreditacije withDatumIstekaAkreditacije(String datumIstekaAkreditacije) {
        this.datumIstekaAkreditacije = datumIstekaAkreditacije;
        return this;
    }

    @JsonProperty("nioZaNaucniSoftverID")
    public Integer getNioZaNaucniSoftverID() {
        return nioZaNaucniSoftverID;
    }

    @JsonProperty("nioZaNaucniSoftverID")
    public void setNioZaNaucniSoftverID(Integer nioZaNaucniSoftverID) {
        this.nioZaNaucniSoftverID = nioZaNaucniSoftverID;
    }

    public Akreditacije withNioZaNaucniSoftverID(Integer nioZaNaucniSoftverID) {
        this.nioZaNaucniSoftverID = nioZaNaucniSoftverID;
        return this;
    }

    @JsonProperty("graneOblasti")
    public List<GraneOblastus> getGraneOblasti() {
        return graneOblasti;
    }

    @JsonProperty("graneOblasti")
    public void setGraneOblasti(List<GraneOblastus> graneOblasti) {
        this.graneOblasti = graneOblasti;
    }

    public Akreditacije withGraneOblasti(List<GraneOblastus> graneOblasti) {
        this.graneOblasti = graneOblasti;
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

    public Akreditacije withId(Integer id) {
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

    public Akreditacije withVersion(Integer version) {
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

    public Akreditacije withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Akreditacije.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("brojAktaOAkreditaciji");
        sb.append('=');
        sb.append(((this.brojAktaOAkreditaciji == null)?"<null>":this.brojAktaOAkreditaciji));
        sb.append(',');
        sb.append("datumAktaOAkreditaciji");
        sb.append('=');
        sb.append(((this.datumAktaOAkreditaciji == null)?"<null>":this.datumAktaOAkreditaciji));
        sb.append(',');
        sb.append("datumIstekaAkreditacije");
        sb.append('=');
        sb.append(((this.datumIstekaAkreditacije == null)?"<null>":this.datumIstekaAkreditacije));
        sb.append(',');
        sb.append("nioZaNaucniSoftverID");
        sb.append('=');
        sb.append(((this.nioZaNaucniSoftverID == null)?"<null>":this.nioZaNaucniSoftverID));
        sb.append(',');
        sb.append("graneOblasti");
        sb.append('=');
        sb.append(((this.graneOblasti == null)?"<null>":this.graneOblasti));
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
        result = ((result* 31)+((this.brojAktaOAkreditaciji == null)? 0 :this.brojAktaOAkreditaciji.hashCode()));
        result = ((result* 31)+((this.nioZaNaucniSoftverID == null)? 0 :this.nioZaNaucniSoftverID.hashCode()));
        result = ((result* 31)+((this.datumIstekaAkreditacije == null)? 0 :this.datumIstekaAkreditacije.hashCode()));
        result = ((result* 31)+((this.graneOblasti == null)? 0 :this.graneOblasti.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.datumAktaOAkreditaciji == null)? 0 :this.datumAktaOAkreditaciji.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Akreditacije) == false) {
            return false;
        }
        Akreditacije rhs = ((Akreditacije) other);
        return (((((((((this.brojAktaOAkreditaciji == rhs.brojAktaOAkreditaciji)||((this.brojAktaOAkreditaciji!= null)&&this.brojAktaOAkreditaciji.equals(rhs.brojAktaOAkreditaciji)))&&((this.nioZaNaucniSoftverID == rhs.nioZaNaucniSoftverID)||((this.nioZaNaucniSoftverID!= null)&&this.nioZaNaucniSoftverID.equals(rhs.nioZaNaucniSoftverID))))&&((this.datumIstekaAkreditacije == rhs.datumIstekaAkreditacije)||((this.datumIstekaAkreditacije!= null)&&this.datumIstekaAkreditacije.equals(rhs.datumIstekaAkreditacije))))&&((this.graneOblasti == rhs.graneOblasti)||((this.graneOblasti!= null)&&this.graneOblasti.equals(rhs.graneOblasti))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.datumAktaOAkreditaciji == rhs.datumAktaOAkreditaciji)||((this.datumAktaOAkreditaciji!= null)&&this.datumAktaOAkreditaciji.equals(rhs.datumAktaOAkreditaciji))));
    }

}
