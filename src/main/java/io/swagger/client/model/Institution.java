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
    "nioBroj",
    "naziv",
    "adresaSedista",
    "maticniBroj",
    "pib",
    "webAdresa",
    "vrstaNioNaziv",
    "jeOdNacionalnogZnacaja",
    "vlasnickiStatusNaziv",
    "rukovodilacIme",
    "rukovodilacPrezime",
    "rukovodilacIbi",
    "stanje",
    "novijeOd",
    "nioID",
    "nazivEngleski",
    "nadredjenaNioID",
    "akreditacije",
    "id",
    "version"
})
@Generated("jsonschema2pojo")
public class Institution {

    @JsonProperty("nioBroj")
    private String nioBroj;
    @JsonProperty("naziv")
    private String naziv;
    @JsonProperty("adresaSedista")
    private String adresaSedista;
    @JsonProperty("maticniBroj")
    private String maticniBroj;
    @JsonProperty("pib")
    private String pib;
    @JsonProperty("webAdresa")
    private String webAdresa;
    @JsonProperty("vrstaNioNaziv")
    private String vrstaNioNaziv;
    @JsonProperty("jeOdNacionalnogZnacaja")
    private Boolean jeOdNacionalnogZnacaja;
    @JsonProperty("vlasnickiStatusNaziv")
    private String vlasnickiStatusNaziv;
    @JsonProperty("rukovodilacIme")
    private Object rukovodilacIme;
    @JsonProperty("rukovodilacPrezime")
    private Object rukovodilacPrezime;
    @JsonProperty("rukovodilacIbi")
    private Object rukovodilacIbi;
    @JsonProperty("stanje")
    private String stanje;
    @JsonProperty("novijeOd")
    private String novijeOd;
    @JsonProperty("nioID")
    private Integer nioID;
    @JsonProperty("nazivEngleski")
    private String nazivEngleski;
    @JsonProperty("nadredjenaNioID")
    private Object nadredjenaNioID;
    @JsonProperty("akreditacije")
    private List<Akreditacije> akreditacije = new ArrayList<Akreditacije>();
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("nioBroj")
    public String getNioBroj() {
        return nioBroj;
    }

    @JsonProperty("nioBroj")
    public void setNioBroj(String nioBroj) {
        this.nioBroj = nioBroj;
    }

    public Institution withNioBroj(String nioBroj) {
        this.nioBroj = nioBroj;
        return this;
    }

    @JsonProperty("naziv")
    public String getNaziv() {
        return naziv;
    }

    @JsonProperty("naziv")
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Institution withNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    @JsonProperty("adresaSedista")
    public String getAdresaSedista() {
        return adresaSedista;
    }

    @JsonProperty("adresaSedista")
    public void setAdresaSedista(String adresaSedista) {
        this.adresaSedista = adresaSedista;
    }

    public Institution withAdresaSedista(String adresaSedista) {
        this.adresaSedista = adresaSedista;
        return this;
    }

    @JsonProperty("maticniBroj")
    public String getMaticniBroj() {
        return maticniBroj;
    }

    @JsonProperty("maticniBroj")
    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    public Institution withMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
        return this;
    }

    @JsonProperty("pib")
    public String getPib() {
        return pib;
    }

    @JsonProperty("pib")
    public void setPib(String pib) {
        this.pib = pib;
    }

    public Institution withPib(String pib) {
        this.pib = pib;
        return this;
    }

    @JsonProperty("webAdresa")
    public String getWebAdresa() {
        return webAdresa;
    }

    @JsonProperty("webAdresa")
    public void setWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
    }

    public Institution withWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
        return this;
    }

    @JsonProperty("vrstaNioNaziv")
    public String getVrstaNioNaziv() {
        return vrstaNioNaziv;
    }

    @JsonProperty("vrstaNioNaziv")
    public void setVrstaNioNaziv(String vrstaNioNaziv) {
        this.vrstaNioNaziv = vrstaNioNaziv;
    }

    public Institution withVrstaNioNaziv(String vrstaNioNaziv) {
        this.vrstaNioNaziv = vrstaNioNaziv;
        return this;
    }

    @JsonProperty("jeOdNacionalnogZnacaja")
    public Boolean getJeOdNacionalnogZnacaja() {
        return jeOdNacionalnogZnacaja;
    }

    @JsonProperty("jeOdNacionalnogZnacaja")
    public void setJeOdNacionalnogZnacaja(Boolean jeOdNacionalnogZnacaja) {
        this.jeOdNacionalnogZnacaja = jeOdNacionalnogZnacaja;
    }

    public Institution withJeOdNacionalnogZnacaja(Boolean jeOdNacionalnogZnacaja) {
        this.jeOdNacionalnogZnacaja = jeOdNacionalnogZnacaja;
        return this;
    }

    @JsonProperty("vlasnickiStatusNaziv")
    public String getVlasnickiStatusNaziv() {
        return vlasnickiStatusNaziv;
    }

    @JsonProperty("vlasnickiStatusNaziv")
    public void setVlasnickiStatusNaziv(String vlasnickiStatusNaziv) {
        this.vlasnickiStatusNaziv = vlasnickiStatusNaziv;
    }

    public Institution withVlasnickiStatusNaziv(String vlasnickiStatusNaziv) {
        this.vlasnickiStatusNaziv = vlasnickiStatusNaziv;
        return this;
    }

    @JsonProperty("rukovodilacIme")
    public Object getRukovodilacIme() {
        return rukovodilacIme;
    }

    @JsonProperty("rukovodilacIme")
    public void setRukovodilacIme(Object rukovodilacIme) {
        this.rukovodilacIme = rukovodilacIme;
    }

    public Institution withRukovodilacIme(Object rukovodilacIme) {
        this.rukovodilacIme = rukovodilacIme;
        return this;
    }

    @JsonProperty("rukovodilacPrezime")
    public Object getRukovodilacPrezime() {
        return rukovodilacPrezime;
    }

    @JsonProperty("rukovodilacPrezime")
    public void setRukovodilacPrezime(Object rukovodilacPrezime) {
        this.rukovodilacPrezime = rukovodilacPrezime;
    }

    public Institution withRukovodilacPrezime(Object rukovodilacPrezime) {
        this.rukovodilacPrezime = rukovodilacPrezime;
        return this;
    }

    @JsonProperty("rukovodilacIbi")
    public Object getRukovodilacIbi() {
        return rukovodilacIbi;
    }

    @JsonProperty("rukovodilacIbi")
    public void setRukovodilacIbi(Object rukovodilacIbi) {
        this.rukovodilacIbi = rukovodilacIbi;
    }

    public Institution withRukovodilacIbi(Object rukovodilacIbi) {
        this.rukovodilacIbi = rukovodilacIbi;
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

    public Institution withStanje(String stanje) {
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

    public Institution withNovijeOd(String novijeOd) {
        this.novijeOd = novijeOd;
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

    public Institution withNioID(Integer nioID) {
        this.nioID = nioID;
        return this;
    }

    @JsonProperty("nazivEngleski")
    public String getNazivEngleski() {
        return nazivEngleski;
    }

    @JsonProperty("nazivEngleski")
    public void setNazivEngleski(String nazivEngleski) {
        this.nazivEngleski = nazivEngleski;
    }

    public Institution withNazivEngleski(String nazivEngleski) {
        this.nazivEngleski = nazivEngleski;
        return this;
    }

    @JsonProperty("nadredjenaNioID")
    public Object getNadredjenaNioID() {
        return nadredjenaNioID;
    }

    @JsonProperty("nadredjenaNioID")
    public void setNadredjenaNioID(Object nadredjenaNioID) {
        this.nadredjenaNioID = nadredjenaNioID;
    }

    public Institution withNadredjenaNioID(Object nadredjenaNioID) {
        this.nadredjenaNioID = nadredjenaNioID;
        return this;
    }

    @JsonProperty("akreditacije")
    public List<Akreditacije> getAkreditacije() {
        return akreditacije;
    }

    @JsonProperty("akreditacije")
    public void setAkreditacije(List<Akreditacije> akreditacije) {
        this.akreditacije = akreditacije;
    }

    public Institution withAkreditacije(List<Akreditacije> akreditacije) {
        this.akreditacije = akreditacije;
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

    public Institution withId(Integer id) {
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

    public Institution withVersion(Integer version) {
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

    public Institution withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Institution.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("nioBroj");
        sb.append('=');
        sb.append(((this.nioBroj == null)?"<null>":this.nioBroj));
        sb.append(',');
        sb.append("naziv");
        sb.append('=');
        sb.append(((this.naziv == null)?"<null>":this.naziv));
        sb.append(',');
        sb.append("adresaSedista");
        sb.append('=');
        sb.append(((this.adresaSedista == null)?"<null>":this.adresaSedista));
        sb.append(',');
        sb.append("maticniBroj");
        sb.append('=');
        sb.append(((this.maticniBroj == null)?"<null>":this.maticniBroj));
        sb.append(',');
        sb.append("pib");
        sb.append('=');
        sb.append(((this.pib == null)?"<null>":this.pib));
        sb.append(',');
        sb.append("webAdresa");
        sb.append('=');
        sb.append(((this.webAdresa == null)?"<null>":this.webAdresa));
        sb.append(',');
        sb.append("vrstaNioNaziv");
        sb.append('=');
        sb.append(((this.vrstaNioNaziv == null)?"<null>":this.vrstaNioNaziv));
        sb.append(',');
        sb.append("jeOdNacionalnogZnacaja");
        sb.append('=');
        sb.append(((this.jeOdNacionalnogZnacaja == null)?"<null>":this.jeOdNacionalnogZnacaja));
        sb.append(',');
        sb.append("vlasnickiStatusNaziv");
        sb.append('=');
        sb.append(((this.vlasnickiStatusNaziv == null)?"<null>":this.vlasnickiStatusNaziv));
        sb.append(',');
        sb.append("rukovodilacIme");
        sb.append('=');
        sb.append(((this.rukovodilacIme == null)?"<null>":this.rukovodilacIme));
        sb.append(',');
        sb.append("rukovodilacPrezime");
        sb.append('=');
        sb.append(((this.rukovodilacPrezime == null)?"<null>":this.rukovodilacPrezime));
        sb.append(',');
        sb.append("rukovodilacIbi");
        sb.append('=');
        sb.append(((this.rukovodilacIbi == null)?"<null>":this.rukovodilacIbi));
        sb.append(',');
        sb.append("stanje");
        sb.append('=');
        sb.append(((this.stanje == null)?"<null>":this.stanje));
        sb.append(',');
        sb.append("novijeOd");
        sb.append('=');
        sb.append(((this.novijeOd == null)?"<null>":this.novijeOd));
        sb.append(',');
        sb.append("nioID");
        sb.append('=');
        sb.append(((this.nioID == null)?"<null>":this.nioID));
        sb.append(',');
        sb.append("nazivEngleski");
        sb.append('=');
        sb.append(((this.nazivEngleski == null)?"<null>":this.nazivEngleski));
        sb.append(',');
        sb.append("nadredjenaNioID");
        sb.append('=');
        sb.append(((this.nadredjenaNioID == null)?"<null>":this.nadredjenaNioID));
        sb.append(',');
        sb.append("akreditacije");
        sb.append('=');
        sb.append(((this.akreditacije == null)?"<null>":this.akreditacije));
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
        result = ((result* 31)+((this.nadredjenaNioID == null)? 0 :this.nadredjenaNioID.hashCode()));
        result = ((result* 31)+((this.stanje == null)? 0 :this.stanje.hashCode()));
        result = ((result* 31)+((this.akreditacije == null)? 0 :this.akreditacije.hashCode()));
        result = ((result* 31)+((this.naziv == null)? 0 :this.naziv.hashCode()));
        result = ((result* 31)+((this.pib == null)? 0 :this.pib.hashCode()));
        result = ((result* 31)+((this.nioBroj == null)? 0 :this.nioBroj.hashCode()));
        result = ((result* 31)+((this.nazivEngleski == null)? 0 :this.nazivEngleski.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.rukovodilacIbi == null)? 0 :this.rukovodilacIbi.hashCode()));
        result = ((result* 31)+((this.novijeOd == null)? 0 :this.novijeOd.hashCode()));
        result = ((result* 31)+((this.nioID == null)? 0 :this.nioID.hashCode()));
        result = ((result* 31)+((this.rukovodilacIme == null)? 0 :this.rukovodilacIme.hashCode()));
        result = ((result* 31)+((this.webAdresa == null)? 0 :this.webAdresa.hashCode()));
        result = ((result* 31)+((this.rukovodilacPrezime == null)? 0 :this.rukovodilacPrezime.hashCode()));
        result = ((result* 31)+((this.maticniBroj == null)? 0 :this.maticniBroj.hashCode()));
        result = ((result* 31)+((this.vlasnickiStatusNaziv == null)? 0 :this.vlasnickiStatusNaziv.hashCode()));
        result = ((result* 31)+((this.adresaSedista == null)? 0 :this.adresaSedista.hashCode()));
        result = ((result* 31)+((this.vrstaNioNaziv == null)? 0 :this.vrstaNioNaziv.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.jeOdNacionalnogZnacaja == null)? 0 :this.jeOdNacionalnogZnacaja.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Institution) == false) {
            return false;
        }
        Institution rhs = ((Institution) other);
        return ((((((((((((((((((((((this.nadredjenaNioID == rhs.nadredjenaNioID)||((this.nadredjenaNioID!= null)&&this.nadredjenaNioID.equals(rhs.nadredjenaNioID)))&&((this.stanje == rhs.stanje)||((this.stanje!= null)&&this.stanje.equals(rhs.stanje))))&&((this.akreditacije == rhs.akreditacije)||((this.akreditacije!= null)&&this.akreditacije.equals(rhs.akreditacije))))&&((this.naziv == rhs.naziv)||((this.naziv!= null)&&this.naziv.equals(rhs.naziv))))&&((this.pib == rhs.pib)||((this.pib!= null)&&this.pib.equals(rhs.pib))))&&((this.nioBroj == rhs.nioBroj)||((this.nioBroj!= null)&&this.nioBroj.equals(rhs.nioBroj))))&&((this.nazivEngleski == rhs.nazivEngleski)||((this.nazivEngleski!= null)&&this.nazivEngleski.equals(rhs.nazivEngleski))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.rukovodilacIbi == rhs.rukovodilacIbi)||((this.rukovodilacIbi!= null)&&this.rukovodilacIbi.equals(rhs.rukovodilacIbi))))&&((this.novijeOd == rhs.novijeOd)||((this.novijeOd!= null)&&this.novijeOd.equals(rhs.novijeOd))))&&((this.nioID == rhs.nioID)||((this.nioID!= null)&&this.nioID.equals(rhs.nioID))))&&((this.rukovodilacIme == rhs.rukovodilacIme)||((this.rukovodilacIme!= null)&&this.rukovodilacIme.equals(rhs.rukovodilacIme))))&&((this.webAdresa == rhs.webAdresa)||((this.webAdresa!= null)&&this.webAdresa.equals(rhs.webAdresa))))&&((this.rukovodilacPrezime == rhs.rukovodilacPrezime)||((this.rukovodilacPrezime!= null)&&this.rukovodilacPrezime.equals(rhs.rukovodilacPrezime))))&&((this.maticniBroj == rhs.maticniBroj)||((this.maticniBroj!= null)&&this.maticniBroj.equals(rhs.maticniBroj))))&&((this.vlasnickiStatusNaziv == rhs.vlasnickiStatusNaziv)||((this.vlasnickiStatusNaziv!= null)&&this.vlasnickiStatusNaziv.equals(rhs.vlasnickiStatusNaziv))))&&((this.adresaSedista == rhs.adresaSedista)||((this.adresaSedista!= null)&&this.adresaSedista.equals(rhs.adresaSedista))))&&((this.vrstaNioNaziv == rhs.vrstaNioNaziv)||((this.vrstaNioNaziv!= null)&&this.vrstaNioNaziv.equals(rhs.vrstaNioNaziv))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.jeOdNacionalnogZnacaja == rhs.jeOdNacionalnogZnacaja)||((this.jeOdNacionalnogZnacaja!= null)&&this.jeOdNacionalnogZnacaja.equals(rhs.jeOdNacionalnogZnacaja))));
    }

}
