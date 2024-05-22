package io.swagger.client.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    "ibi",
    "ime",
    "prezime",
    "stanje",
    "novijeOd",
    "arID",
    "risID",
    "pol",
    "datumRodjenja",
    "identifikatori",
    "zvanja",
    "zaposlenja",
    "emailovi",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Researcher {

    @JsonProperty("ibi")
    private String ibi;
    @JsonProperty("ime")
    private String ime;
    @JsonProperty("prezime")
    private String prezime;
    @JsonProperty("stanje")
    private String stanje;
    @JsonProperty("novijeOd")
    private String novijeOd;
    @JsonProperty("arID")
    private Object arID;
    @JsonProperty("risID")
    private Object risID;
    @JsonProperty("pol")
    private String pol;
    @JsonProperty("datumRodjenja")
    private String datumRodjenja;
    @JsonProperty("identifikatori")
    private List<Identifikatorus> identifikatori = new ArrayList<Identifikatorus>();
    @JsonProperty("zvanja")
    private List<Zvanja> zvanja = new ArrayList<Zvanja>();
    @JsonProperty("zaposlenja")
    private List<Zaposlenja> zaposlenja = new ArrayList<Zaposlenja>();
    @JsonProperty("emailovi")
    private List<Emailovus> emailovi = new ArrayList<Emailovus>();
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("ibi")
    public String getIbi() {
        return ibi;
    }

    @JsonProperty("ibi")
    public void setIbi(String ibi) {
        this.ibi = ibi;
    }

    public Researcher withIbi(String ibi) {
        this.ibi = ibi;
        return this;
    }

    @JsonProperty("ime")
    public String getIme() {
        return ime;
    }

    @JsonProperty("ime")
    public void setIme(String ime) {
        this.ime = ime;
    }

    public Researcher withIme(String ime) {
        this.ime = ime;
        return this;
    }

    @JsonProperty("prezime")
    public String getPrezime() {
        return prezime;
    }

    @JsonProperty("prezime")
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Researcher withPrezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    @JsonProperty("stanje")
    public String getStanje() {
        return stanje;
    }

    @JsonProperty("stanje")
    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public Researcher withStanje(String stanje) {
        this.stanje = stanje;
        return this;
    }

    @JsonProperty("novijeOd")
    public String getNovijeOd() {
        return novijeOd;
    }

    @JsonProperty("novijeOd")
    public void setNovijeOd(String novijeOd) {
        this.novijeOd = novijeOd;
    }

    public Researcher withNovijeOd(String novijeOd) {
        this.novijeOd = novijeOd;
        return this;
    }

    @JsonProperty("arID")
    public Object getArID() {
        return arID;
    }

    @JsonProperty("arID")
    public void setArID(Object arID) {
        this.arID = arID;
    }

    public Researcher withArID(Object arID) {
        this.arID = arID;
        return this;
    }

    @JsonProperty("risID")
    public Object getRisID() {
        return risID;
    }

    @JsonProperty("risID")
    public void setRisID(Object risID) {
        this.risID = risID;
    }

    public Researcher withRisID(Object risID) {
        this.risID = risID;
        return this;
    }

    @JsonProperty("pol")
    public String getPol() {
        return pol;
    }

    @JsonProperty("pol")
    public void setPol(String pol) {
        this.pol = pol;
    }

    public Researcher withPol(String pol) {
        this.pol = pol;
        return this;
    }

    @JsonProperty("datumRodjenja")
    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    @JsonProperty("datumRodjenja")
    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Researcher withDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
        return this;
    }

    @JsonProperty("identifikatori")
    public List<Identifikatorus> getIdentifikatori() {
        return identifikatori;
    }

    @JsonProperty("identifikatori")
    public void setIdentifikatori(List<Identifikatorus> identifikatori) {
        this.identifikatori = identifikatori;
    }

    public Researcher withIdentifikatori(List<Identifikatorus> identifikatori) {
        this.identifikatori = identifikatori;
        return this;
    }

    @JsonProperty("zvanja")
    public List<Zvanja> getZvanja() {
        return zvanja;
    }

    @JsonProperty("zvanja")
    public void setZvanja(List<Zvanja> zvanja) {
        this.zvanja = zvanja;
    }

    public Researcher withZvanja(List<Zvanja> zvanja) {
        this.zvanja = zvanja;
        return this;
    }

    @JsonProperty("zaposlenja")
    public List<Zaposlenja> getZaposlenja() {
        return zaposlenja;
    }

    @JsonProperty("zaposlenja")
    public void setZaposlenja(List<Zaposlenja> zaposlenja) {
        this.zaposlenja = zaposlenja;
    }

    public Researcher withZaposlenja(List<Zaposlenja> zaposlenja) {
        this.zaposlenja = zaposlenja;
        return this;
    }

    @JsonProperty("emailovi")
    public List<Emailovus> getEmailovi() {
        return emailovi;
    }

    @JsonProperty("emailovi")
    public void setEmailovi(List<Emailovus> emailovi) {
        this.emailovi = emailovi;
    }

    public Researcher withEmailovi(List<Emailovus> emailovi) {
        this.emailovi = emailovi;
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

    public Researcher withId(Integer id) {
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

    public Researcher withVersion(Integer version) {
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

    public Researcher withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Researcher.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("ibi");
        sb.append('=');
        sb.append(((this.ibi == null)?"<null>":this.ibi));
        sb.append(',');
        sb.append("ime");
        sb.append('=');
        sb.append(((this.ime == null)?"<null>":this.ime));
        sb.append(',');
        sb.append("prezime");
        sb.append('=');
        sb.append(((this.prezime == null)?"<null>":this.prezime));
        sb.append(',');
        sb.append("stanje");
        sb.append('=');
        sb.append(((this.stanje == null)?"<null>":this.stanje));
        sb.append(',');
        sb.append("novijeOd");
        sb.append('=');
        sb.append(((this.novijeOd == null)?"<null>":this.novijeOd));
        sb.append(',');
        sb.append("arID");
        sb.append('=');
        sb.append(((this.arID == null)?"<null>":this.arID));
        sb.append(',');
        sb.append("risID");
        sb.append('=');
        sb.append(((this.risID == null)?"<null>":this.risID));
        sb.append(',');
        sb.append("pol");
        sb.append('=');
        sb.append(((this.pol == null)?"<null>":this.pol));
        sb.append(',');
        sb.append("datumRodjenja");
        sb.append('=');
        sb.append(((this.datumRodjenja == null)?"<null>":this.datumRodjenja));
        sb.append(',');
        sb.append("identifikatori");
        sb.append('=');
        sb.append(((this.identifikatori == null)?"<null>":this.identifikatori));
        sb.append(',');
        sb.append("zvanja");
        sb.append('=');
        sb.append(((this.zvanja == null)?"<null>":this.zvanja));
        sb.append(',');
        sb.append("zaposlenja");
        sb.append('=');
        sb.append(((this.zaposlenja == null)?"<null>":this.zaposlenja));
        sb.append(',');
        sb.append("emailovi");
        sb.append('=');
        sb.append(((this.emailovi == null)?"<null>":this.emailovi));
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
        result = ((result* 31)+((this.ime == null)? 0 :this.ime.hashCode()));
        result = ((result* 31)+((this.prezime == null)? 0 :this.prezime.hashCode()));
        result = ((result* 31)+((this.risID == null)? 0 :this.risID.hashCode()));
        result = ((result* 31)+((this.identifikatori == null)? 0 :this.identifikatori.hashCode()));
        result = ((result* 31)+((this.arID == null)? 0 :this.arID.hashCode()));
        result = ((result* 31)+((this.datumRodjenja == null)? 0 :this.datumRodjenja.hashCode()));
        result = ((result* 31)+((this.stanje == null)? 0 :this.stanje.hashCode()));
        result = ((result* 31)+((this.pol == null)? 0 :this.pol.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.novijeOd == null)? 0 :this.novijeOd.hashCode()));
        result = ((result* 31)+((this.ibi == null)? 0 :this.ibi.hashCode()));
        result = ((result* 31)+((this.emailovi == null)? 0 :this.emailovi.hashCode()));
        result = ((result* 31)+((this.zvanja == null)? 0 :this.zvanja.hashCode()));
        result = ((result* 31)+((this.zaposlenja == null)? 0 :this.zaposlenja.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Researcher) == false) {
            return false;
        }
        Researcher rhs = ((Researcher) other);
        return (((((((((((((((((this.ime == rhs.ime)||((this.ime!= null)&&this.ime.equals(rhs.ime)))&&((this.prezime == rhs.prezime)||((this.prezime!= null)&&this.prezime.equals(rhs.prezime))))&&((this.risID == rhs.risID)||((this.risID!= null)&&this.risID.equals(rhs.risID))))&&((this.identifikatori == rhs.identifikatori)||((this.identifikatori!= null)&&this.identifikatori.equals(rhs.identifikatori))))&&((this.arID == rhs.arID)||((this.arID!= null)&&this.arID.equals(rhs.arID))))&&((this.datumRodjenja == rhs.datumRodjenja)||((this.datumRodjenja!= null)&&this.datumRodjenja.equals(rhs.datumRodjenja))))&&((this.stanje == rhs.stanje)||((this.stanje!= null)&&this.stanje.equals(rhs.stanje))))&&((this.pol == rhs.pol)||((this.pol!= null)&&this.pol.equals(rhs.pol))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.novijeOd == rhs.novijeOd)||((this.novijeOd!= null)&&this.novijeOd.equals(rhs.novijeOd))))&&((this.ibi == rhs.ibi)||((this.ibi!= null)&&this.ibi.equals(rhs.ibi))))&&((this.emailovi == rhs.emailovi)||((this.emailovi!= null)&&this.emailovi.equals(rhs.emailovi))))&&((this.zvanja == rhs.zvanja)||((this.zvanja!= null)&&this.zvanja.equals(rhs.zvanja))))&&((this.zaposlenja == rhs.zaposlenja)||((this.zaposlenja!= null)&&this.zaposlenja.equals(rhs.zaposlenja))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
