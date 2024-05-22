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
    "zvanje",
    "datumSticanjaZvanja",
    "datumIstekaZvanja",
    "nioBroj",
    "istrazivacZaNaucniSoftverID",
    "naucnaGranaID",
    "naucnaGranaNaziv",
    "naucnaOblastID",
    "naucnaOblastNaziv",
    "uzaNaucnaOblast",
    "nioID",
    "obrazovnoNaucnoPoljeID",
    "obrazovnoNaucnoPoljeNaziv",
    "obrazovnoNaucnaOblastID",
    "obrazovnoNaucnaOblastNaziv",
    "tipZvanja",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Zvanja {

    @JsonProperty("zvanje")
    private String zvanje;
    @JsonProperty("datumSticanjaZvanja")
    private String datumSticanjaZvanja;
    @JsonProperty("datumIstekaZvanja")
    private String datumIstekaZvanja;
    @JsonProperty("nioBroj")
    private String nioBroj;
    @JsonProperty("istrazivacZaNaucniSoftverID")
    private Integer istrazivacZaNaucniSoftverID;
    @JsonProperty("naucnaGranaID")
    private Integer naucnaGranaID;
    @JsonProperty("naucnaGranaNaziv")
    private String naucnaGranaNaziv;
    @JsonProperty("naucnaOblastID")
    private Integer naucnaOblastID;
    @JsonProperty("naucnaOblastNaziv")
    private String naucnaOblastNaziv;
    @JsonProperty("uzaNaucnaOblast")
    private Object uzaNaucnaOblast;
    @JsonProperty("nioID")
    private Integer nioID;
    @JsonProperty("obrazovnoNaucnoPoljeID")
    private Object obrazovnoNaucnoPoljeID;
    @JsonProperty("obrazovnoNaucnoPoljeNaziv")
    private Object obrazovnoNaucnoPoljeNaziv;
    @JsonProperty("obrazovnoNaucnaOblastID")
    private Object obrazovnoNaucnaOblastID;
    @JsonProperty("obrazovnoNaucnaOblastNaziv")
    private Object obrazovnoNaucnaOblastNaziv;
    @JsonProperty("tipZvanja")
    private String tipZvanja;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("zvanje")
    public String getZvanje() {
        return zvanje;
    }

    @JsonProperty("zvanje")
    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }

    public Zvanja withZvanje(String zvanje) {
        this.zvanje = zvanje;
        return this;
    }

    @JsonProperty("datumSticanjaZvanja")
    public String getDatumSticanjaZvanja() {
        return datumSticanjaZvanja;
    }

    @JsonProperty("datumSticanjaZvanja")
    public void setDatumSticanjaZvanja(String datumSticanjaZvanja) {
        this.datumSticanjaZvanja = datumSticanjaZvanja;
    }

    public Zvanja withDatumSticanjaZvanja(String datumSticanjaZvanja) {
        this.datumSticanjaZvanja = datumSticanjaZvanja;
        return this;
    }

    @JsonProperty("datumIstekaZvanja")
    public String getDatumIstekaZvanja() {
        return datumIstekaZvanja;
    }

    @JsonProperty("datumIstekaZvanja")
    public void setDatumIstekaZvanja(String datumIstekaZvanja) {
        this.datumIstekaZvanja = datumIstekaZvanja;
    }

    public Zvanja withDatumIstekaZvanja(String datumIstekaZvanja) {
        this.datumIstekaZvanja = datumIstekaZvanja;
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

    public Zvanja withNioBroj(String nioBroj) {
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

    public Zvanja withIstrazivacZaNaucniSoftverID(Integer istrazivacZaNaucniSoftverID) {
        this.istrazivacZaNaucniSoftverID = istrazivacZaNaucniSoftverID;
        return this;
    }

    @JsonProperty("naucnaGranaID")
    public Integer getNaucnaGranaID() {
        return naucnaGranaID;
    }

    @JsonProperty("naucnaGranaID")
    public void setNaucnaGranaID(Integer naucnaGranaID) {
        this.naucnaGranaID = naucnaGranaID;
    }

    public Zvanja withNaucnaGranaID(Integer naucnaGranaID) {
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

    public Zvanja withNaucnaGranaNaziv(String naucnaGranaNaziv) {
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

    public Zvanja withNaucnaOblastID(Integer naucnaOblastID) {
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

    public Zvanja withNaucnaOblastNaziv(String naucnaOblastNaziv) {
        this.naucnaOblastNaziv = naucnaOblastNaziv;
        return this;
    }

    @JsonProperty("uzaNaucnaOblast")
    public Object getUzaNaucnaOblast() {
        return uzaNaucnaOblast;
    }

    @JsonProperty("uzaNaucnaOblast")
    public void setUzaNaucnaOblast(Object uzaNaucnaOblast) {
        this.uzaNaucnaOblast = uzaNaucnaOblast;
    }

    public Zvanja withUzaNaucnaOblast(Object uzaNaucnaOblast) {
        this.uzaNaucnaOblast = uzaNaucnaOblast;
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

    public Zvanja withNioID(Integer nioID) {
        this.nioID = nioID;
        return this;
    }

    @JsonProperty("obrazovnoNaucnoPoljeID")
    public Object getObrazovnoNaucnoPoljeID() {
        return obrazovnoNaucnoPoljeID;
    }

    @JsonProperty("obrazovnoNaucnoPoljeID")
    public void setObrazovnoNaucnoPoljeID(Object obrazovnoNaucnoPoljeID) {
        this.obrazovnoNaucnoPoljeID = obrazovnoNaucnoPoljeID;
    }

    public Zvanja withObrazovnoNaucnoPoljeID(Object obrazovnoNaucnoPoljeID) {
        this.obrazovnoNaucnoPoljeID = obrazovnoNaucnoPoljeID;
        return this;
    }

    @JsonProperty("obrazovnoNaucnoPoljeNaziv")
    public Object getObrazovnoNaucnoPoljeNaziv() {
        return obrazovnoNaucnoPoljeNaziv;
    }

    @JsonProperty("obrazovnoNaucnoPoljeNaziv")
    public void setObrazovnoNaucnoPoljeNaziv(Object obrazovnoNaucnoPoljeNaziv) {
        this.obrazovnoNaucnoPoljeNaziv = obrazovnoNaucnoPoljeNaziv;
    }

    public Zvanja withObrazovnoNaucnoPoljeNaziv(Object obrazovnoNaucnoPoljeNaziv) {
        this.obrazovnoNaucnoPoljeNaziv = obrazovnoNaucnoPoljeNaziv;
        return this;
    }

    @JsonProperty("obrazovnoNaucnaOblastID")
    public Object getObrazovnoNaucnaOblastID() {
        return obrazovnoNaucnaOblastID;
    }

    @JsonProperty("obrazovnoNaucnaOblastID")
    public void setObrazovnoNaucnaOblastID(Object obrazovnoNaucnaOblastID) {
        this.obrazovnoNaucnaOblastID = obrazovnoNaucnaOblastID;
    }

    public Zvanja withObrazovnoNaucnaOblastID(Object obrazovnoNaucnaOblastID) {
        this.obrazovnoNaucnaOblastID = obrazovnoNaucnaOblastID;
        return this;
    }

    @JsonProperty("obrazovnoNaucnaOblastNaziv")
    public Object getObrazovnoNaucnaOblastNaziv() {
        return obrazovnoNaucnaOblastNaziv;
    }

    @JsonProperty("obrazovnoNaucnaOblastNaziv")
    public void setObrazovnoNaucnaOblastNaziv(Object obrazovnoNaucnaOblastNaziv) {
        this.obrazovnoNaucnaOblastNaziv = obrazovnoNaucnaOblastNaziv;
    }

    public Zvanja withObrazovnoNaucnaOblastNaziv(Object obrazovnoNaucnaOblastNaziv) {
        this.obrazovnoNaucnaOblastNaziv = obrazovnoNaucnaOblastNaziv;
        return this;
    }

    @JsonProperty("tipZvanja")
    public String getTipZvanja() {
        return tipZvanja;
    }

    @JsonProperty("tipZvanja")
    public void setTipZvanja(String tipZvanja) {
        this.tipZvanja = tipZvanja;
    }

    public Zvanja withTipZvanja(String tipZvanja) {
        this.tipZvanja = tipZvanja;
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

    public Zvanja withId(Integer id) {
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

    public Zvanja withVersion(Integer version) {
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

    public Zvanja withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Zvanja.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("zvanje");
        sb.append('=');
        sb.append(((this.zvanje == null)?"<null>":this.zvanje));
        sb.append(',');
        sb.append("datumSticanjaZvanja");
        sb.append('=');
        sb.append(((this.datumSticanjaZvanja == null)?"<null>":this.datumSticanjaZvanja));
        sb.append(',');
        sb.append("datumIstekaZvanja");
        sb.append('=');
        sb.append(((this.datumIstekaZvanja == null)?"<null>":this.datumIstekaZvanja));
        sb.append(',');
        sb.append("nioBroj");
        sb.append('=');
        sb.append(((this.nioBroj == null)?"<null>":this.nioBroj));
        sb.append(',');
        sb.append("istrazivacZaNaucniSoftverID");
        sb.append('=');
        sb.append(((this.istrazivacZaNaucniSoftverID == null)?"<null>":this.istrazivacZaNaucniSoftverID));
        sb.append(',');
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
        sb.append("uzaNaucnaOblast");
        sb.append('=');
        sb.append(((this.uzaNaucnaOblast == null)?"<null>":this.uzaNaucnaOblast));
        sb.append(',');
        sb.append("nioID");
        sb.append('=');
        sb.append(((this.nioID == null)?"<null>":this.nioID));
        sb.append(',');
        sb.append("obrazovnoNaucnoPoljeID");
        sb.append('=');
        sb.append(((this.obrazovnoNaucnoPoljeID == null)?"<null>":this.obrazovnoNaucnoPoljeID));
        sb.append(',');
        sb.append("obrazovnoNaucnoPoljeNaziv");
        sb.append('=');
        sb.append(((this.obrazovnoNaucnoPoljeNaziv == null)?"<null>":this.obrazovnoNaucnoPoljeNaziv));
        sb.append(',');
        sb.append("obrazovnoNaucnaOblastID");
        sb.append('=');
        sb.append(((this.obrazovnoNaucnaOblastID == null)?"<null>":this.obrazovnoNaucnaOblastID));
        sb.append(',');
        sb.append("obrazovnoNaucnaOblastNaziv");
        sb.append('=');
        sb.append(((this.obrazovnoNaucnaOblastNaziv == null)?"<null>":this.obrazovnoNaucnaOblastNaziv));
        sb.append(',');
        sb.append("tipZvanja");
        sb.append('=');
        sb.append(((this.tipZvanja == null)?"<null>":this.tipZvanja));
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
        result = ((result* 31)+((this.datumSticanjaZvanja == null)? 0 :this.datumSticanjaZvanja.hashCode()));
        result = ((result* 31)+((this.tipZvanja == null)? 0 :this.tipZvanja.hashCode()));
        result = ((result* 31)+((this.nioBroj == null)? 0 :this.nioBroj.hashCode()));
        result = ((result* 31)+((this.uzaNaucnaOblast == null)? 0 :this.uzaNaucnaOblast.hashCode()));
        result = ((result* 31)+((this.obrazovnoNaucnaOblastNaziv == null)? 0 :this.obrazovnoNaucnaOblastNaziv.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.naucnaOblastID == null)? 0 :this.naucnaOblastID.hashCode()));
        result = ((result* 31)+((this.nioID == null)? 0 :this.nioID.hashCode()));
        result = ((result* 31)+((this.obrazovnoNaucnoPoljeNaziv == null)? 0 :this.obrazovnoNaucnoPoljeNaziv.hashCode()));
        result = ((result* 31)+((this.istrazivacZaNaucniSoftverID == null)? 0 :this.istrazivacZaNaucniSoftverID.hashCode()));
        result = ((result* 31)+((this.naucnaGranaNaziv == null)? 0 :this.naucnaGranaNaziv.hashCode()));
        result = ((result* 31)+((this.naucnaOblastNaziv == null)? 0 :this.naucnaOblastNaziv.hashCode()));
        result = ((result* 31)+((this.datumIstekaZvanja == null)? 0 :this.datumIstekaZvanja.hashCode()));
        result = ((result* 31)+((this.obrazovnoNaucnoPoljeID == null)? 0 :this.obrazovnoNaucnoPoljeID.hashCode()));
        result = ((result* 31)+((this.obrazovnoNaucnaOblastID == null)? 0 :this.obrazovnoNaucnaOblastID.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.zvanje == null)? 0 :this.zvanje.hashCode()));
        result = ((result* 31)+((this.naucnaGranaID == null)? 0 :this.naucnaGranaID.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Zvanja) == false) {
            return false;
        }
        Zvanja rhs = ((Zvanja) other);
        return ((((((((((((((((((((this.datumSticanjaZvanja == rhs.datumSticanjaZvanja)||((this.datumSticanjaZvanja!= null)&&this.datumSticanjaZvanja.equals(rhs.datumSticanjaZvanja)))&&((this.tipZvanja == rhs.tipZvanja)||((this.tipZvanja!= null)&&this.tipZvanja.equals(rhs.tipZvanja))))&&((this.nioBroj == rhs.nioBroj)||((this.nioBroj!= null)&&this.nioBroj.equals(rhs.nioBroj))))&&((this.uzaNaucnaOblast == rhs.uzaNaucnaOblast)||((this.uzaNaucnaOblast!= null)&&this.uzaNaucnaOblast.equals(rhs.uzaNaucnaOblast))))&&((this.obrazovnoNaucnaOblastNaziv == rhs.obrazovnoNaucnaOblastNaziv)||((this.obrazovnoNaucnaOblastNaziv!= null)&&this.obrazovnoNaucnaOblastNaziv.equals(rhs.obrazovnoNaucnaOblastNaziv))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.naucnaOblastID == rhs.naucnaOblastID)||((this.naucnaOblastID!= null)&&this.naucnaOblastID.equals(rhs.naucnaOblastID))))&&((this.nioID == rhs.nioID)||((this.nioID!= null)&&this.nioID.equals(rhs.nioID))))&&((this.obrazovnoNaucnoPoljeNaziv == rhs.obrazovnoNaucnoPoljeNaziv)||((this.obrazovnoNaucnoPoljeNaziv!= null)&&this.obrazovnoNaucnoPoljeNaziv.equals(rhs.obrazovnoNaucnoPoljeNaziv))))&&((this.istrazivacZaNaucniSoftverID == rhs.istrazivacZaNaucniSoftverID)||((this.istrazivacZaNaucniSoftverID!= null)&&this.istrazivacZaNaucniSoftverID.equals(rhs.istrazivacZaNaucniSoftverID))))&&((this.naucnaGranaNaziv == rhs.naucnaGranaNaziv)||((this.naucnaGranaNaziv!= null)&&this.naucnaGranaNaziv.equals(rhs.naucnaGranaNaziv))))&&((this.naucnaOblastNaziv == rhs.naucnaOblastNaziv)||((this.naucnaOblastNaziv!= null)&&this.naucnaOblastNaziv.equals(rhs.naucnaOblastNaziv))))&&((this.datumIstekaZvanja == rhs.datumIstekaZvanja)||((this.datumIstekaZvanja!= null)&&this.datumIstekaZvanja.equals(rhs.datumIstekaZvanja))))&&((this.obrazovnoNaucnoPoljeID == rhs.obrazovnoNaucnoPoljeID)||((this.obrazovnoNaucnoPoljeID!= null)&&this.obrazovnoNaucnoPoljeID.equals(rhs.obrazovnoNaucnoPoljeID))))&&((this.obrazovnoNaucnaOblastID == rhs.obrazovnoNaucnaOblastID)||((this.obrazovnoNaucnaOblastID!= null)&&this.obrazovnoNaucnaOblastID.equals(rhs.obrazovnoNaucnaOblastID))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.zvanje == rhs.zvanje)||((this.zvanje!= null)&&this.zvanje.equals(rhs.zvanje))))&&((this.naucnaGranaID == rhs.naucnaGranaID)||((this.naucnaGranaID!= null)&&this.naucnaGranaID.equals(rhs.naucnaGranaID))));
    }

}
