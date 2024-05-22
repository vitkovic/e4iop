package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.Akreditacije;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A ResearchInstitution.
 */
@Entity
@Table(name = "research_institution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResearchInstitution implements Serializable {

    private static final long serialVersionUID = 1L;
    

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    // JSON from eNauka API
    @Column(name = "number")
    private String number;
    @Column(name = "address")
    private String address;
    @Column(name = "registerNumber")
    private String registerNumber;
    @Column(name = "vatNumber")
    private String vatNumber;
    @Column(name = "webAdress")
    private String webAdress;
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "ownerStructureName")
    private String ownerStructureName;
    @Column(name = "ceoName")
    private String ceoName;
    @Column(name = "ceoLastName")
    private String ceoLastName;
    @Column(name = "ceoIbi")
    private String ceoIbi;
    @Column(name = "state")
    private String state;
    
    @Column(name = "nameEn")
    private String nameEn;

 
    @Column(name = "nameCyr")
    private String nameCyr;
    
    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "research_institution_researchers",
               joinColumns = @JoinColumn(name = "research_institution_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "researchers_id", referencedColumnName = "id"))
    private Set<Researcher> researchers = new HashSet<>();

   
    
    @OneToOne(mappedBy = "nioInstitution")
    @JsonIgnore
    private PortalUserOrganization portalUserOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNameCyr() {
		return nameCyr;
	}

	public void setNameCyr(String nameCyr) {
		this.nameCyr = nameCyr;
	}

	public ResearchInstitution name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Researcher> getResearchers() {
        return researchers;
    }

    public ResearchInstitution researchers(Set<Researcher> researchers) {
        this.researchers = researchers;
        return this;
    }

    public ResearchInstitution addResearchers(Researcher researcher) {
        this.researchers.add(researcher);
        researcher.getInstitutions().add(this);
        return this;
    }

    public ResearchInstitution removeResearchers(Researcher researcher) {
        this.researchers.remove(researcher);
        researcher.getInstitutions().remove(this);
        return this;
    }

    public void setResearchers(Set<Researcher> researchers) {
        this.researchers = researchers;
    }

   
    
    public String getNumber() {
  		return number;
  	}

  	public void setNumber(String number) {
  		this.number = number;
  	}

  	public String getAddress() {
  		return address;
  	}

  	public void setAddress(String address) {
  		this.address = address;
  	}

  	public String getRegisterNumber() {
  		return registerNumber;
  	}

  	public void setRegisterNumber(String registerNumber) {
  		this.registerNumber = registerNumber;
  	}

  	public String getVatNumber() {
  		return vatNumber;
  	}

  	public void setVatNumber(String vatNumber) {
  		this.vatNumber = vatNumber;
  	}

  	public String getWebAdress() {
  		return webAdress;
  	}

  	public void setWebAdress(String webAdress) {
  		this.webAdress = webAdress;
  	}

  	public String getTypeName() {
  		return typeName;
  	}

  	public void setTypeName(String typeName) {
  		this.typeName = typeName;
  	}

  	public String getOwnerStructureName() {
  		return ownerStructureName;
  	}

  	public void setOwnerStructureName(String ownerStructureName) {
  		this.ownerStructureName = ownerStructureName;
  	}

  	public String getCeoName() {
  		return ceoName;
  	}

  	public void setCeoName(String ceoName) {
  		this.ceoName = ceoName;
  	}

  	public String getCeoLastName() {
  		return ceoLastName;
  	}

  	public void setCeoLastName(String ceoLastName) {
  		this.ceoLastName = ceoLastName;
  	}

  	public String getCeoIbi() {
  		return ceoIbi;
  	}

  	public void setCeoIbi(String ceoIbi) {
  		this.ceoIbi = ceoIbi;
  	}

  	public String getState() {
  		return state;
  	}

  	public void setState(String state) {
  		this.state = state;
  	}

  	public String getNameEn() {
  		return nameEn;
  	}

  	public void setNameEn(String nameEn) {
  		this.nameEn = nameEn;
  	}

 
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResearchInstitution)) {
            return false;
        }
        return id != null && id.equals(((ResearchInstitution) o).id);
    }

    public PortalUserOrganization getPortalUserOrganization() {
		return portalUserOrganization;
	}

	public void setPortalUserOrganization(PortalUserOrganization portalUserOrganization) {
		this.portalUserOrganization = portalUserOrganization;
	}

	@Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResearchInstitution{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
