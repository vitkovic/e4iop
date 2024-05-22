package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ResearchInfrastructure.
 */
@Entity
@Table(name = "research_infrastructure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)


@SqlResultSetMappings({
	@SqlResultSetMapping(name="ri_query", 
			classes={
			@ConstructorResult(
			     targetClass=ResearchInfrastructureDTOSearch.class,			      
			       columns={
			          @ColumnResult(name="id", type=Long.class),
			          @ColumnResult(name="descriptionSr", type=String.class),
			          @ColumnResult(name="descriptionEn", type=String.class),
			          @ColumnResult(name="descriptionSrCyr", type=String.class),
			          @ColumnResult(name="nameSr", type=String.class),
			          @ColumnResult(name="nameEn", type=String.class),
			          @ColumnResult(name="nameSrCyr", type=String.class),
			          @ColumnResult(name="riWebsite", type=String.class),
			          @ColumnResult(name="keywords", type=String.class),
			          @ColumnResult(name="keywordsEn", type=String.class),
			          @ColumnResult(name="keywordsCyr", type=String.class),
			          @ColumnResult(name="statusId", type=Long.class),
			          @ColumnResult(name="status", type=String.class),
			          @ColumnResult(name="statusCyr", type=String.class),
			          @ColumnResult(name="statusEn", type=String.class),
			          @ColumnResult(name="accessModeId", type=Long.class),
			          @ColumnResult(name="accessMode", type=String.class),
			          @ColumnResult(name="accessModeCyr", type=String.class),
			          @ColumnResult(name="accessModeEn", type=String.class),
			          @ColumnResult(name="accessTypeId", type=Long.class),
			          @ColumnResult(name="accessType", type=String.class),
			          @ColumnResult(name="accessTypeCyr", type=String.class),
			          @ColumnResult(name="accessTypeEn", type=String.class),
			          @ColumnResult(name="ownerId", type=Long.class),
			          @ColumnResult(name="ownerName", type=String.class),
			          @ColumnResult(name="managerId", type=Long.class),
			          @ColumnResult(name="userId", type=Long.class),
			          @ColumnResult(name="managerFirstName", type=String.class),
			          @ColumnResult(name="managerLastName", type=String.class),
			          }
			   )
			}
	)
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="ResearchInfrastructure.searchByEnglishKeywords", 
			query="SELECT distinct(r.id) as id, r.description_sr as descriptionSr, r.description_en as descriptionEn, r.description_sr_cyr as descriptionSrCyr, "
			  		+ " r.name_sr as nameSr, r.name_en as nameEn, r.name_sr_cyr as nameSrCyr, r.ri_website as riWebsite, "
			  		+ "	r.keywords as keywords, r.keywords_en as keywordsEn, r.keywords_cyr as keywordsCyr, "
			  		+ " riStatus.id as statusId, riStatus.status as status, riStatus.status_cyr as statusCyr, riStatus.status_en as statusEn,"
			  		+ " riAccessMode.id as accessModeId, riAccessMode.mode as accessMode, riAccessMode.mode_cyr as accessModeCyr, riAccessMode.mode_en as accessModeEn, "			  		
					+ " riAccessType.id as accessTypeId, riAccessType.type as accessType, riAccessType.type_cyr as accessTypeCyr, riAccessType.type_en as accessTypeEn, "
			  		+ " riResearchOrganization.id as ownerId, riResearchOrganization.name as ownerName, "
			  		+ " portalUser.id as managerId, "
			  		+ " jhiUser.id as userId, jhiUser.first_name as managerFirstName, jhiUser.last_name as managerLastName"			  		
			  		+ "	FROM research_infrastructure r "
			  		+ " left join research_infrastructure_subdomains ris on ris.research_infrastructure_id = r.id "			 
			  		+ " left join ri_status riStatus on riStatus.id = r.status_id "
					+ " left join ri_access_mode riAccessMode on riAccessMode.id = r.access_mode_id "
					+ " left join ri_access_type riAccessType on riAccessType.id = r.access_type_id "
					+ " left join ri_research_organization riResearchOrganization on riResearchOrganization.id = r.owner_id"
					+ " left join portal_user portalUser on portalUser.id = r.manager_id"
					+ " left join jhi_user jhiUser on jhiUser.id = portalUser.user_id"			
					+ "	WHERE (1 = :keywordsNotExist or (r.english_search @@ to_tsquery('english', :keywords )))"
					+ " and (1 = :subdomainsNotExist or (ris.subdomains_id in ( :subdomains )))", resultSetMapping="ri_query"),
	
	@NamedNativeQuery(name="ResearchInfrastructure.searchBySerbianKeywords", 
			query="SELECT distinct(r.id) as id, r.description_sr as descriptionSr, r.description_en as descriptionEn, r.description_sr_cyr as descriptionSrCyr, "
			  		+ " r.name_sr as nameSr, r.name_en as nameEn, r.name_sr_cyr as nameSrCyr, r.ri_website as riWebsite, "
			  		+ "	r.keywords as keywords, r.keywords_en as keywordsEn, r.keywords_cyr as keywordsCyr, "
					+ " riStatus.id as statusId, riStatus.status as status, riStatus.status_cyr as statusCyr, riStatus.status_en as statusEn,"
			  		+ " riAccessMode.id as accessModeId, riAccessMode.mode as accessMode, riAccessMode.mode_cyr as accessModeCyr, riAccessMode.mode_en as accessModeEn, "
					+ " riAccessType.id as accessTypeId, riAccessType.type as accessType, riAccessType.type_cyr as accessTypeCyr, riAccessType.type_en as accessTypeEn, "
			  		+ " riResearchOrganization.id as ownerId, riResearchOrganization.name as ownerName, "
			  		+ " portalUser.id as managerId, "
			  		+ " jhiUser.id as userId, jhiUser.first_name as managerFirstName, jhiUser.last_name as managerLastName"	  		
			  		+ "	FROM research_infrastructure r "
			  		+ " left join research_infrastructure_subdomains ris on ris.research_infrastructure_id = r.id "
			  		+ " left join ri_status riStatus on riStatus.id = r.status_id "
					+ " left join ri_access_mode riAccessMode on riAccessMode.id = r.access_mode_id "
					+ " left join ri_access_type riAccessType on riAccessType.id = r.access_type_id "
					+ " left join ri_research_organization riResearchOrganization on riResearchOrganization.id = r.owner_id"
					+ " left join portal_user portalUser on portalUser.id = r.manager_id"
					+ " left join jhi_user jhiUser on jhiUser.id = portalUser.user_id"
			  		+ "	WHERE (1 = :keywordsNotExist or (r.serbian_search @@ to_tsquery('serbian', :keywords )))"
			  		+ " and (1 = :subdomainsNotExist or (ris.subdomains_id in ( :subdomains )))", resultSetMapping="ri_query")
	})

public class ResearchInfrastructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Name of research infrastructure in Serbian
     */
    @ApiModelProperty(value = "Name of research infrastructure in Serbian")
    @Column(name = "name_sr")
    private String nameSr;

    /**
     * Name of research infrastructure in English
     */
    @ApiModelProperty(value = "Name of research infrastructure in English")
    @Column(name = "name_en")
    private String nameEn;

    /**
     * Name of the unit that manage RI. Example: Laboratory for nano materials
     */
    @ApiModelProperty(value = "Name of the unit that manage RI. Example: Laboratory for nano materials")
    @Column(name = "department_name")
    private String departmentName;

    /**
     * URL of the RI logo
     */
    @ApiModelProperty(value = "URL of the RI logo")
    @Column(name = "ri_logo")
    private String riLogo;

    /**
     * URL of the RI website
     */
    @ApiModelProperty(value = "URL of the RI website")
    @Column(name = "ri_website")
    private String riWebsite;

    /**
     * Description of the RI including purpose in Serbian
     */
    @ApiModelProperty(value = "Description of the RI including purpose in Serbian")
    @Column(name = "description_sr")
    private String descriptionSr;

    /**
     * Description of the RI including purpose in English
     */
    @ApiModelProperty(value = "Description of the RI including purpose in English")
    @Column(name = "description_en")
    private String descriptionEn;

    /**
     * Technical specification of the RI in Serbian
     */
    @ApiModelProperty(value = "Technical specification of the RI in Serbian")
    @Column(name = "technical_specification_sr")
    private String technicalSpecificationSr;

    /**
     * Technical specification of the RI in English
     */
    @ApiModelProperty(value = "Technical specification of the RI in English")
    @Column(name = "technical_specification_en")
    private String technicalSpecificationEn;

    /**
     * Link to web page with full technical specification in Serbian
     */
    @ApiModelProperty(value = "Link to web page with full technical specification in Serbian")
    @Column(name = "technical_specification_link_sr")
    private String technicalSpecificationLinkSr;

    /**
     * Link to web page with full technical specification in English
     */
    @ApiModelProperty(value = "Link to web page with full technical specification in English")
    @Column(name = "technical_specification_link_en")
    private String technicalSpecificationLinkEn;

    /**
     * Link to the RI user manual and documentation in Serbian
     */
    @ApiModelProperty(value = "Link to the RI user manual and documentation in Serbian")
    @Column(name = "user_manual_link_sr")
    private String userManualLinkSr;

    /**
     * Link to the RI user manual and documentation in English
     */
    @ApiModelProperty(value = "Link to the RI user manual and documentation in English")
    @Column(name = "user_manual_link_en")
    private String userManualLinkEn;

    /**
     * keywords related to RI
     */
    @ApiModelProperty(value = "keywords related to RI")
    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "keywords_en")
    private String keywordsEn;
    
    @Column(name = "keywords_cyr")
    private String keywordsCyr;

    @Column(name = "name_sr_cyr")
    private String nameSrCyr;

    @Column(name = "description_sr_cyr")
    private String descriptionSrCyr;

    @Column(name = "technical_specification_sr_cyr")
    private String technicalSpecificationSrCyr;

    @Column(name = "technical_specification_link_sr_cyr")
    private String technicalSpecificationLinkSrCyr;

    @Column(name = "user_manual_link_sr_cyr")
    private String userManualLinkSrCyr;
    
    @Column(name = "terms_of_use_sr")
    private String termsOfUseSr;

    /**
     * Link to the Terms of use in English in Policy document repository
     */
    @ApiModelProperty(value = "Link to the Terms of use in English in Policy document repository")
    @Column(name = "terms_of_use_en")
    private String termsOfUseEn;

    @Column(name = "terms_of_use_sr_cyr")
    private String termsOfUseSrCyr;
    
    @Column(name = "acquisition_year")// godina nabavke opreme
    private Integer acquisitionYear;
    
    @Column(name = "purchase_value_euro")// nabavma vrednost u E
    private Double purchaseValueEuro;
    
    @Column(name = "purchase_value_din")// nabavna vrednost u din
    private Double purchaseValueDin;
    
    @Column(name = "finance_source")// izvor finansiranja
    private String financeSource;
    
    @Column(name = "project_name")// projekat iz kojeg je oprema kupljena
    private String projectName;

    @OneToMany(mappedBy = "researchInfrastructure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiService> services = new HashSet<>();
    
    @OneToMany(mappedBy = "researchInfrastructure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InfrastructureImage> images = new HashSet<>();    

    @OneToMany(mappedBy = "researchInfrastructure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiCalendar> calendars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "researchInfrastructures", allowSetters = true)
    private RiStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "researchInfrastructures", allowSetters = true)
    private RiAccessMode accessMode;

    @ManyToOne
    @JsonIgnoreProperties(value = "researchInfrastructures", allowSetters = true)
    private RiAccessType accessType;

    @ManyToOne
    @JsonIgnoreProperties(value = "researchInfrastructures", allowSetters = true)
    private RiCategory category;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "research_infrastructure_subdomains",
               joinColumns = @JoinColumn(name = "research_infrastructure_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subdomains_id", referencedColumnName = "id"))
    private Set<ScientificSubdomain> subdomains = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "research_infrastructure_subcategories",
               joinColumns = @JoinColumn(name = "research_infrastructure_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subcategories_id", referencedColumnName = "id"))
    private Set<RiSubcategory> subcategories = new HashSet<>();

    
    public ResearchInfrastructure() {
	}

    

	@ManyToOne
    @JsonIgnoreProperties(value = "infrastructures", allowSetters = true)
    private RiResearchOrganization owner;

    @ManyToOne
    @JsonIgnoreProperties(value = "managedInfrastructures", allowSetters = true)
    private PortalUser manager;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactInfrastructures", allowSetters = true)
    private PortalUser contactPerson;
    

    
    
	public ResearchInfrastructure(Long id, String nameSr, String nameEn, String nameSrCyr ) {
		this.id = id;
		this.nameSr = nameSr;
		this.nameEn = nameEn;
		this.nameSrCyr = nameSrCyr;
	}




    public String getTermsOfUseSr() {
		return termsOfUseSr;
	}




	public void setTermsOfUseSr(String termsOfUseSr) {
		this.termsOfUseSr = termsOfUseSr;
	}




	public String getTermsOfUseEn() {
		return termsOfUseEn;
	}




	public void setTermsOfUseEn(String termsOfUseEn) {
		this.termsOfUseEn = termsOfUseEn;
	}




	public String getTermsOfUseSrCyr() {
		return termsOfUseSrCyr;
	}




	public void setTermsOfUseSrCyr(String termsOfUseSrCyr) {
		this.termsOfUseSrCyr = termsOfUseSrCyr;
	}




	public Set<InfrastructureImage> getImages() {
		return images;
	}




	public void setImages(Set<InfrastructureImage> images) {
		this.images = images;
	}




	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSr() {
        return nameSr;
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

	public ResearchInfrastructure nameSr(String nameSr) {
        this.nameSr = nameSr;
        return this;
    }

    public void setNameSr(String nameSr) {
        this.nameSr = nameSr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public ResearchInfrastructure nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public ResearchInfrastructure departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRiLogo() {
        return riLogo;
    }

    public ResearchInfrastructure riLogo(String riLogo) {
        this.riLogo = riLogo;
        return this;
    }

    public void setRiLogo(String riLogo) {
        this.riLogo = riLogo;
    }

    public String getRiWebsite() {
        return riWebsite;
    }

    public ResearchInfrastructure riWebsite(String riWebsite) {
        this.riWebsite = riWebsite;
        return this;
    }

    public void setRiWebsite(String riWebsite) {
        this.riWebsite = riWebsite;
    }

    public String getDescriptionSr() {
        return descriptionSr;
    }

    public ResearchInfrastructure descriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
        return this;
    }

    public void setDescriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public ResearchInfrastructure descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getTechnicalSpecificationSr() {
        return technicalSpecificationSr;
    }

    public ResearchInfrastructure technicalSpecificationSr(String technicalSpecificationSr) {
        this.technicalSpecificationSr = technicalSpecificationSr;
        return this;
    }

    public void setTechnicalSpecificationSr(String technicalSpecificationSr) {
        this.technicalSpecificationSr = technicalSpecificationSr;
    }

    public String getTechnicalSpecificationEn() {
        return technicalSpecificationEn;
    }

    public ResearchInfrastructure technicalSpecificationEn(String technicalSpecificationEn) {
        this.technicalSpecificationEn = technicalSpecificationEn;
        return this;
    }

    public void setTechnicalSpecificationEn(String technicalSpecificationEn) {
        this.technicalSpecificationEn = technicalSpecificationEn;
    }

    public String getTechnicalSpecificationLinkSr() {
        return technicalSpecificationLinkSr;
    }

    public ResearchInfrastructure technicalSpecificationLinkSr(String technicalSpecificationLinkSr) {
        this.technicalSpecificationLinkSr = technicalSpecificationLinkSr;
        return this;
    }

    public void setTechnicalSpecificationLinkSr(String technicalSpecificationLinkSr) {
        this.technicalSpecificationLinkSr = technicalSpecificationLinkSr;
    }

    public String getTechnicalSpecificationLinkEn() {
        return technicalSpecificationLinkEn;
    }

    public ResearchInfrastructure technicalSpecificationLinkEn(String technicalSpecificationLinkEn) {
        this.technicalSpecificationLinkEn = technicalSpecificationLinkEn;
        return this;
    }

    public void setTechnicalSpecificationLinkEn(String technicalSpecificationLinkEn) {
        this.technicalSpecificationLinkEn = technicalSpecificationLinkEn;
    }

    public String getUserManualLinkSr() {
        return userManualLinkSr;
    }

    public ResearchInfrastructure userManualLinkSr(String userManualLinkSr) {
        this.userManualLinkSr = userManualLinkSr;
        return this;
    }

    public void setUserManualLinkSr(String userManualLinkSr) {
        this.userManualLinkSr = userManualLinkSr;
    }

    public String getUserManualLinkEn() {
        return userManualLinkEn;
    }

    public ResearchInfrastructure userManualLinkEn(String userManualLinkEn) {
        this.userManualLinkEn = userManualLinkEn;
        return this;
    }

    public void setUserManualLinkEn(String userManualLinkEn) {
        this.userManualLinkEn = userManualLinkEn;
    }

    public String getKeywords() {
        return keywords;
    }

    public ResearchInfrastructure keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getNameSrCyr() {
        return nameSrCyr;
    }

    public ResearchInfrastructure nameSrCyr(String nameSrCyr) {
        this.nameSrCyr = nameSrCyr;
        return this;
    }

    public void setNameSrCyr(String nameSrCyr) {
        this.nameSrCyr = nameSrCyr;
    }

    public String getDescriptionSrCyr() {
        return descriptionSrCyr;
    }

    public ResearchInfrastructure descriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
        return this;
    }

    public void setDescriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
    }

    public String getTechnicalSpecificationSrCyr() {
        return technicalSpecificationSrCyr;
    }

    public ResearchInfrastructure technicalSpecificationSrCyr(String technicalSpecificationSrCyr) {
        this.technicalSpecificationSrCyr = technicalSpecificationSrCyr;
        return this;
    }

    public void setTechnicalSpecificationSrCyr(String technicalSpecificationSrCyr) {
        this.technicalSpecificationSrCyr = technicalSpecificationSrCyr;
    }

    public String getTechnicalSpecificationLinkSrCyr() {
        return technicalSpecificationLinkSrCyr;
    }

    public ResearchInfrastructure technicalSpecificationLinkSrCyr(String technicalSpecificationLinkSrCyr) {
        this.technicalSpecificationLinkSrCyr = technicalSpecificationLinkSrCyr;
        return this;
    }

    public void setTechnicalSpecificationLinkSrCyr(String technicalSpecificationLinkSrCyr) {
        this.technicalSpecificationLinkSrCyr = technicalSpecificationLinkSrCyr;
    }

    public String getUserManualLinkSrCyr() {
        return userManualLinkSrCyr;
    }

    public ResearchInfrastructure userManualLinkSrCyr(String userManualLinkSrCyr) {
        this.userManualLinkSrCyr = userManualLinkSrCyr;
        return this;
    }

    public void setUserManualLinkSrCyr(String userManualLinkSrCyr) {
        this.userManualLinkSrCyr = userManualLinkSrCyr;
    }

    public Set<RiService> getServices() {
        return services;
    }

    public ResearchInfrastructure services(Set<RiService> riServices) {
        this.services = riServices;
        return this;
    }

    public ResearchInfrastructure addServices(RiService riService) {
        this.services.add(riService);
        riService.setResearchInfrastructure(this);
        return this;
    }

    public ResearchInfrastructure removeServices(RiService riService) {
        this.services.remove(riService);
        riService.setResearchInfrastructure(null);
        return this;
    }

    public void setServices(Set<RiService> riServices) {
        this.services = riServices;
    }

    public Set<RiCalendar> getCalendars() {
        return calendars;
    }

    public ResearchInfrastructure calendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
        return this;
    }

    public ResearchInfrastructure addCalendars(RiCalendar riCalendar) {
        this.calendars.add(riCalendar);
        riCalendar.setResearchInfrastructure(this);
        return this;
    }

    public ResearchInfrastructure removeCalendars(RiCalendar riCalendar) {
        this.calendars.remove(riCalendar);
        riCalendar.setResearchInfrastructure(null);
        return this;
    }

    public void setCalendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
    }

    public RiStatus getStatus() {
        return status;
    }

    public ResearchInfrastructure status(RiStatus riStatus) {
        this.status = riStatus;
        return this;
    }

    public void setStatus(RiStatus riStatus) {
        this.status = riStatus;
    }

    public RiAccessMode getAccessMode() {
        return accessMode;
    }

    public ResearchInfrastructure accessMode(RiAccessMode riAccessMode) {
        this.accessMode = riAccessMode;
        return this;
    }

    public void setAccessMode(RiAccessMode riAccessMode) {
        this.accessMode = riAccessMode;
    }

    public RiAccessType getAccessType() {
        return accessType;
    }

    public ResearchInfrastructure accessType(RiAccessType riAccessType) {
        this.accessType = riAccessType;
        return this;
    }

    public void setAccessType(RiAccessType riAccessType) {
        this.accessType = riAccessType;
    }

    public RiCategory getCategory() {
        return category;
    }

    public ResearchInfrastructure category(RiCategory riCategory) {
        this.category = riCategory;
        return this;
    }

    public void setCategory(RiCategory riCategory) {
        this.category = riCategory;
    }

    public Set<ScientificSubdomain> getSubdomains() {
        return subdomains;
    }

    public ResearchInfrastructure subdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
        return this;
    }

    public ResearchInfrastructure addSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.add(scientificSubdomain);
        scientificSubdomain.getInfrastructures().add(this);
        return this;
    }

    public ResearchInfrastructure removeSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.remove(scientificSubdomain);
        scientificSubdomain.getInfrastructures().remove(this);
        return this;
    }

    public void setSubdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
    }

    public Set<RiSubcategory> getSubcategories() {
        return subcategories;
    }

    public ResearchInfrastructure subcategories(Set<RiSubcategory> riSubcategories) {
        this.subcategories = riSubcategories;
        return this;
    }

    public ResearchInfrastructure addSubcategories(RiSubcategory riSubcategory) {
        this.subcategories.add(riSubcategory);
        riSubcategory.getInfrastructures().add(this);
        return this;
    }

    public ResearchInfrastructure removeSubcategories(RiSubcategory riSubcategory) {
        this.subcategories.remove(riSubcategory);
        riSubcategory.getInfrastructures().remove(this);
        return this;
    }

    public void setSubcategories(Set<RiSubcategory> riSubcategories) {
        this.subcategories = riSubcategories;
    }

    public RiResearchOrganization getOwner() {
        return owner;
    }

    public ResearchInfrastructure owner(RiResearchOrganization riResearchOrganization) {
        this.owner = riResearchOrganization;
        return this;
    }

    public void setOwner(RiResearchOrganization riResearchOrganization) {
        this.owner = riResearchOrganization;
    }

    public PortalUser getManager() {
        return manager;
    }

    public ResearchInfrastructure manager(PortalUser portalUser) {
        this.manager = portalUser;
        return this;
    }

    public void setManager(PortalUser portalUser) {
        this.manager = portalUser;
    }

    public PortalUser getContactPerson() {
        return contactPerson;
    }

    public ResearchInfrastructure contactPerson(PortalUser portalUser) {
        this.contactPerson = portalUser;
        return this;
    }

    public void setContactPerson(PortalUser portalUser) {
        this.contactPerson = portalUser;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Integer getAcquisitionYear() {
		return acquisitionYear;
	}




	public void setAcquisitionYear(Integer acquisitionYear) {
		this.acquisitionYear = acquisitionYear;
	}




	public Double getPurchaseValueEuro() {
		return purchaseValueEuro;
	}




	public void setPurchaseValueEuro(Double purchaseValueEuro) {
		this.purchaseValueEuro = purchaseValueEuro;
	}




	public Double getPurchaseValueDin() {
		return purchaseValueDin;
	}




	public void setPurchaseValueDin(Double purchaseValueDin) {
		this.purchaseValueDin = purchaseValueDin;
	}




	public String getFinanceSource() {
		return financeSource;
	}




	public void setFinanceSource(String financeSource) {
		this.financeSource = financeSource;
	}




	public String getProjectName() {
		return projectName;
	}




	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}




	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResearchInfrastructure)) {
            return false;
        }
        return id != null && id.equals(((ResearchInfrastructure) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResearchInfrastructure{" +
            "id=" + getId() +
            ", nameSr='" + getNameSr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", departmentName='" + getDepartmentName() + "'" +
            ", riLogo='" + getRiLogo() + "'" +
            ", riWebsite='" + getRiWebsite() + "'" +
            ", descriptionSr='" + getDescriptionSr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", technicalSpecificationSr='" + getTechnicalSpecificationSr() + "'" +
            ", technicalSpecificationEn='" + getTechnicalSpecificationEn() + "'" +
            ", technicalSpecificationLinkSr='" + getTechnicalSpecificationLinkSr() + "'" +
            ", technicalSpecificationLinkEn='" + getTechnicalSpecificationLinkEn() + "'" +
            ", userManualLinkSr='" + getUserManualLinkSr() + "'" +
            ", userManualLinkEn='" + getUserManualLinkEn() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", nameSrCyr='" + getNameSrCyr() + "'" +
            ", descriptionSrCyr='" + getDescriptionSrCyr() + "'" +
            ", technicalSpecificationSrCyr='" + getTechnicalSpecificationSrCyr() + "'" +
            ", technicalSpecificationLinkSrCyr='" + getTechnicalSpecificationLinkSrCyr() + "'" +
            ", userManualLinkSrCyr='" + getUserManualLinkSrCyr() + "'" +
            "}";
    }
}
