package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.util.LatCyrUtils;
import e4i.util.Utility;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.vm.PortalUserRegisterDTO;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PortalUserOrganization.
 */
@Entity
@Table(name = "portal_user_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SqlResultSetMappings({
	@SqlResultSetMapping(name="puo_query", 
			classes={
			@ConstructorResult(
			     targetClass=RiResearchOrganizationReportDTO.class,			      
			       columns={
			          @ColumnResult(name="id", type=Long.class),
			          @ColumnResult(name="name", type=String.class),
			          @ColumnResult(name="address1", type=String.class),
			          @ColumnResult(name="address2", type=String.class),
			          }
			   )
			}
	)
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="PortalUserOrganization.findAllForReport", 
			query="SELECT puo.id, puo.legal_name_sr as name, ri.address as address1, puo.address as address2 "
					+ "  FROM portal_user_organization puo "
					+ "  left join research_institution ri on puo.nio_institution_id = ri.id  "
					+ "", resultSetMapping="puo_query"),
	
	@NamedNativeQuery(name="PortalUserOrganization.findForSearchSr", 
	query="SELECT puo.id, puo.legal_name_sr as name, ri.address as address1, puo.address as address2 "
			+ " from portal_user_organization puo "		
			+ " left join research_institution ri on puo.nio_institution_id = ri.id  "
			+ " where (1 = :keywordsNotRequested or (lower(ri.name_cyr) LIKE :name or lower(ri.name) LIKE :name "
			+ " or lower(puo.legal_name_sr) LIKE :name or lower(puo.legal_name_sr_cyr) LIKE :name))"
			+ " and (1 = :nioNotRequested or (puo.nio_institution_id is not null))"
			+ " and (1 = :firmNotRequested or (puo.nio_institution_id is null))", resultSetMapping="puo_query"),

})

public class PortalUserOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Legal name of PUO in Serbian
     */
    @ApiModelProperty(value = "Legal name of PUO in Serbian")
    @Column(name = "legal_name_sr")
    private String legalNameSr;

    /**
     * Legal name of PUO in English
     */
    @ApiModelProperty(value = "Legal name of PUO in English")
    @Column(name = "legal_name_en")
    private String legalNameEn;

    /**
     * Short name - abbreviation
     */
    @ApiModelProperty(value = "Short name - abbreviation")
    @Column(name = "short_name")
    private String shortName;

    @Column(name = "web_address")
    private String webAddress;

    /**
     * Tax identification number
     */
    @Size(min = 9, max = 9)
    @ApiModelProperty(value = "Tax identification number")
    @Column(name = "pib", length = 9)
    private String pib;

    /**
     * registration number (matični broj) assigned by Statistical Office of the Republic of Serbia
     */
    @Size(max = 8)
    @ApiModelProperty(value = "registration number (matični broj) assigned by Statistical Office of the Republic of Serbia")
    @Column(name = "registration_number", length = 8)
    private String registrationNumber;

    /**
     * Keywords related to PUO. Used for search function of the portal
     */
    @ApiModelProperty(value = "Keywords related to PUO. Used for search function of the portal")
    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "keywords_en")
    private String keywordsEn;
    
    @Column(name = "keywords_cyr")
    private String keywordsCyr;

    /**
     * Streat name and number of the PUO
     */
    @ApiModelProperty(value = "Streat name and number of the PUO")
    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;
    
   /**
     * Default value Serbia
     */
    @ApiModelProperty(value = "Default value Serbia")
    @Column(name = "country")
    private String country;

    @Column(name = "legal_name_sr_cyr")
    private String legalNameSrCyr;

    @OneToMany(mappedBy = "userOrganization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PortalUser> portalUsers = new HashSet<>();

    @OneToMany(mappedBy = "organizations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RequestForService> requests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUserOrganizations", allowSetters = true)
    private PuoLegalStatus legalStatus;

    @OneToOne(mappedBy = "puOrganization")
    @JsonIgnore
    private RiResearchOrganization researchOrganization;
    
    @OneToOne
    @JoinColumn(name="nio_institution_id", unique=true)
    private ResearchInstitution nioInstitution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    
    
    public Long getId() {
        return id;
    }

    public PortalUserOrganization() {
	}

    
	public PortalUserOrganization(PortalUserRegisterDTO reg) {
		if(Utility.containCyrillic(reg.getOrganizationNameSr())) {
			this.legalNameSrCyr = reg.getOrganizationNameSr();
			this.legalNameSr = LatCyrUtils.toLatin(reg.getOrganizationNameSr());
		}else {
			this.legalNameSr = reg.getOrganizationNameSr();
			this.legalNameSrCyr = LatCyrUtils.toCyrillic(reg.getOrganizationNameSr());
		}
		this.legalNameEn = reg.getOrganizationNameEn();
		this.shortName = reg.getOrganizationShortName();
		this.address = reg.getOrganizationAddress();
		this.city = reg.getOrganizationAddressCity();
		this.country = reg.getOrganizationAddressCountry();
		this.postalCode = reg.getOrganizationAddressPostalCode();
		this.keywords = reg.getOrganizationKeywords();
		this.legalStatus = reg.getOrganizationLegalStatus();
		this.pib = reg.getOrganizationPib();
		this.registrationNumber = reg.getOrganizationMaticniBroj();
		this.webAddress = reg.getOrganizationWebAddress();	
		this.nioInstitution = reg.getOrganizationRI();
	}
	
	public void setNames() {
		if(Utility.containCyrillic(this.legalNameSr)) {
			this.legalNameSrCyr = this.legalNameSr;
			this.legalNameSr = LatCyrUtils.toLatin(this.legalNameSr);
		}else {			
			this.legalNameSrCyr = LatCyrUtils.toCyrillic(this.legalNameSr);
		}
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getLegalNameSr() {
        return legalNameSr;
    }

    public PortalUserOrganization legalNameSr(String legalNameSr) {
        this.legalNameSr = legalNameSr;
        return this;
    }

    public void setLegalNameSr(String legalNameSr) {
        this.legalNameSr = legalNameSr;
    }

    public String getLegalNameEn() {
        return legalNameEn;
    }

    public PortalUserOrganization legalNameEn(String legalNameEn) {
        this.legalNameEn = legalNameEn;
        return this;
    }

    public void setLegalNameEn(String legalNameEn) {
        this.legalNameEn = legalNameEn;
    }

    public String getShortName() {
        return shortName;
    }

    public PortalUserOrganization shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public PortalUserOrganization webAddress(String webAddress) {
        this.webAddress = webAddress;
        return this;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getPib() {
        return pib;
    }

    public PortalUserOrganization pib(String pib) {
        this.pib = pib;
        return this;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public PortalUserOrganization registrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getKeywords() {
        return keywords;
    }

    public PortalUserOrganization keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAddress() {
        return address;
    }

    public PortalUserOrganization address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getKeywordsEn() {
		return keywordsEn;
	}

	public void setKeywordsEn(String keywordsEn) {
		this.keywordsEn = keywordsEn;
	}

	public String getKeywordsCyr() {
		return keywordsCyr;
	}

	public void setKeywordsCyr(String keywordsCyr) {
		this.keywordsCyr = keywordsCyr;
	}

	public String getPostalCode() {
        return postalCode;
    }

    public PortalUserOrganization postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public PortalUserOrganization city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public PortalUserOrganization country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLegalNameSrCyr() {
        return legalNameSrCyr;
    }

    public PortalUserOrganization legalNameSrCyr(String legalNameSrCyr) {
        this.legalNameSrCyr = legalNameSrCyr;
        return this;
    }

    public void setLegalNameSrCyr(String legalNameSrCyr) {
        this.legalNameSrCyr = legalNameSrCyr;
    }

    public Set<PortalUser> getPortalUsers() {
        return portalUsers;
    }

    public PortalUserOrganization portalUsers(Set<PortalUser> portalUsers) {
        this.portalUsers = portalUsers;
        return this;
    }

    public PortalUserOrganization addPortalUsers(PortalUser portalUser) {
        this.portalUsers.add(portalUser);
        portalUser.setUserOrganization(this);
        return this;
    }

    public PortalUserOrganization removePortalUsers(PortalUser portalUser) {
        this.portalUsers.remove(portalUser);
        portalUser.setUserOrganization(null);
        return this;
    }

    public void setPortalUsers(Set<PortalUser> portalUsers) {
        this.portalUsers = portalUsers;
    }

    public Set<RequestForService> getRequests() {
        return requests;
    }

    public PortalUserOrganization requests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
        return this;
    }

    public PortalUserOrganization addRequests(RequestForService requestForService) {
        this.requests.add(requestForService);
        requestForService.setOrganizations(this);
        return this;
    }

    public PortalUserOrganization removeRequests(RequestForService requestForService) {
        this.requests.remove(requestForService);
        requestForService.setOrganizations(null);
        return this;
    }

    public void setRequests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
    }

    public PuoLegalStatus getLegalStatus() {
        return legalStatus;
    }

    public PortalUserOrganization legalStatus(PuoLegalStatus puoLegalStatus) {
        this.legalStatus = puoLegalStatus;
        return this;
    }

    public void setLegalStatus(PuoLegalStatus puoLegalStatus) {
        this.legalStatus = puoLegalStatus;
    }

    public RiResearchOrganization getResearchOrganization() {
        return researchOrganization;
    }

    public PortalUserOrganization researchOrganization(RiResearchOrganization riResearchOrganization) {
        this.researchOrganization = riResearchOrganization;
        return this;
    }

    public void setResearchOrganization(RiResearchOrganization riResearchOrganization) {
        this.researchOrganization = riResearchOrganization;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public ResearchInstitution getNioInstitution() {
		return nioInstitution;
	}

	public void setNioInstitution(ResearchInstitution nioInstitution) {
		this.nioInstitution = nioInstitution;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortalUserOrganization)) {
            return false;
        }
        return id != null && id.equals(((PortalUserOrganization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortalUserOrganization{" +
            "id=" + getId() +
            ", legalNameSr='" + getLegalNameSr() + "'" +
            ", legalNameEn='" + getLegalNameEn() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", webAddress='" + getWebAddress() + "'" +
            ", pib='" + getPib() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", legalNameSrCyr='" + getLegalNameSrCyr() + "'" +
            "}";
    }
}
