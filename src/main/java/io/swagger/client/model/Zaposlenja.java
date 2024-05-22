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
    "datumOd",
    "datumDo",
    "nioBroj",
    "istrazivacZaNaucniSoftverID",
    "nioID",
    "procenatRO",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Zaposlenja {

    @JsonProperty("datumOd")
    private String datumOd;
    @JsonProperty("datumDo")
    private String datumDo;
    @JsonProperty("nioBroj")
    private String nioBroj;
    @JsonProperty("istrazivacZaNaucniSoftverID")
    private Integer istrazivacZaNaucniSoftverID;
    @JsonProperty("nioID")
    private Integer nioID;
    @JsonProperty("procenatRO")
    private Integer procenatRO;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("datumOd")
    public String getDatumOd() {
        return datumOd;
    }

    @JsonProperty("datumOd")
    public void setDatumOd(String datumOd) {
        this.datumOd = datumOd;
    }

    public Zaposlenja withDatumOd(String datumOd) {
        this.datumOd = datumOd;
        return this;
    }

    @JsonProperty("datumDo")
    public String getDatumDo() {
        return datumDo;
    }

    @JsonProperty("datumDo")
    public void setDatumDo(String datumDo) {
        this.datumDo = datumDo;
    }

    public Zaposlenja withDatumDo(String datumDo) {
        this.datumDo = datumDo;
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

    public Zaposlenja withNioBroj(String nioBroj) {
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

    public Zaposlenja withIstrazivacZaNaucniSoftverID(Integer istrazivacZaNaucniSoftverID) {
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

    public Zaposlenja withNioID(Integer nioID) {
        this.nioID = nioID;
        return this;
    }

    @JsonProperty("procenatRO")
    public Integer getProcenatRO() {
        return procenatRO;
    }

    @JsonProperty("procenatRO")
    public void setProcenatRO(Integer procenatRO) {
        this.procenatRO = procenatRO;
    }

    public Zaposlenja withProcenatRO(Integer procenatRO) {
        this.procenatRO = procenatRO;
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

    public Zaposlenja withId(Integer id) {
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

    public Zaposlenja withVersion(Integer version) {
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

    public Zaposlenja withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Zaposlenja.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("datumOd");
        sb.append('=');
        sb.append(((this.datumOd == null)?"<null>":this.datumOd));
        sb.append(',');
        sb.append("datumDo");
        sb.append('=');
        sb.append(((this.datumDo == null)?"<null>":this.datumDo));
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
        sb.append("procenatRO");
        sb.append('=');
        sb.append(((this.procenatRO == null)?"<null>":this.procenatRO));
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
        result = ((result* 31)+((this.datumDo == null)? 0 :this.datumDo.hashCode()));
        result = ((result* 31)+((this.procenatRO == null)? 0 :this.procenatRO.hashCode()));
        result = ((result* 31)+((this.nioBroj == null)? 0 :this.nioBroj.hashCode()));
        result = ((result* 31)+((this.datumOd == null)? 0 :this.datumOd.hashCode()));
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
        if ((other instanceof Zaposlenja) == false) {
            return false;
        }
        Zaposlenja rhs = ((Zaposlenja) other);
        return ((((((((((this.nioID == rhs.nioID)||((this.nioID!= null)&&this.nioID.equals(rhs.nioID)))&&((this.istrazivacZaNaucniSoftverID == rhs.istrazivacZaNaucniSoftverID)||((this.istrazivacZaNaucniSoftverID!= null)&&this.istrazivacZaNaucniSoftverID.equals(rhs.istrazivacZaNaucniSoftverID))))&&((this.datumDo == rhs.datumDo)||((this.datumDo!= null)&&this.datumDo.equals(rhs.datumDo))))&&((this.procenatRO == rhs.procenatRO)||((this.procenatRO!= null)&&this.procenatRO.equals(rhs.procenatRO))))&&((this.nioBroj == rhs.nioBroj)||((this.nioBroj!= null)&&this.nioBroj.equals(rhs.nioBroj))))&&((this.datumOd == rhs.datumOd)||((this.datumOd!= null)&&this.datumOd.equals(rhs.datumOd))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))));
    }

}
