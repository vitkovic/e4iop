package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.RiServiceInfrastructureOrganizationDTO;
import e4i.web.rest.dto.RiServiceStatisticDTO;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A RiService.
 */
@Entity
@Table(name = "ri_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)


@SqlResultSetMappings({
	@SqlResultSetMapping(name="clp", 
			classes={
			@ConstructorResult(
			     targetClass=RiServiceDTOSearch.class,			      
			       columns={
			          @ColumnResult(name="id", type=Long.class),
			          @ColumnResult(name="nameSr", type=String.class),
			          @ColumnResult(name="nameEn", type=String.class),
			          @ColumnResult(name="nameSrCyr", type=String.class),
			          @ColumnResult(name="descriptionSr", type=String.class),
			          @ColumnResult(name="descriptionEn", type=String.class),
			          @ColumnResult(name="descriptionSrCyr", type=String.class),
			          @ColumnResult(name="keywords", type=String.class),
			          @ColumnResult(name="keywordsEn", type=String.class),
			          @ColumnResult(name="keywordsCyr", type=String.class),
			          @ColumnResult(name="infrastructureId", type=Long.class),
			          @ColumnResult(name="infrastructureNameSr", type=String.class),
			          @ColumnResult(name="infrastructureNameEn", type=String.class),
			          @ColumnResult(name="infrastructureNameSrCyr", type=String.class),
			          @ColumnResult(name="availabilityId", type=Long.class),
			          @ColumnResult(name="availability", type=String.class),
			          @ColumnResult(name="availabilityEn", type=String.class),
			          @ColumnResult(name="availabilityCyr", type=String.class),
			          @ColumnResult(name="subtypeId", type=Long.class),
			          @ColumnResult(name="subtype", type=String.class),
			          @ColumnResult(name="subtypeEn", type=String.class),
			          @ColumnResult(name="subtypeCyr", type=String.class),			          
			          }
			   )
			}
	),
	@SqlResultSetMapping(name="rss", 
	classes={
	@ConstructorResult(
	     targetClass=RiServiceStatisticDTO.class,			      
		       columns={
		          @ColumnResult(name="id", type=Long.class),
		          @ColumnResult(name="nameSr", type=String.class),
		          @ColumnResult(name="nameEn", type=String.class),
		          @ColumnResult(name="nameSrCyr", type=String.class),
		          @ColumnResult(name="descriptionSr", type=String.class),
		          @ColumnResult(name="descriptionEn", type=String.class),
		          @ColumnResult(name="descriptionSrCyr", type=String.class),
		          @ColumnResult(name="infrastructureId", type=Long.class),
		          @ColumnResult(name="infrastructureNameSr", type=String.class),
		          @ColumnResult(name="infrastructureNameEn", type=String.class),
		          @ColumnResult(name="infrastructureNameSrCyr", type=String.class),
		          @ColumnResult(name="availabilityId", type=Long.class),
		          @ColumnResult(name="availability", type=String.class),
		          @ColumnResult(name="availabilityEn", type=String.class),
		          @ColumnResult(name="availabilityCyr", type=String.class),
		          @ColumnResult(name="subtypeId", type=Long.class),
		          @ColumnResult(name="subtype", type=String.class),
		          @ColumnResult(name="subtypeEn", type=String.class),
		          @ColumnResult(name="subtypeCyr", type=String.class),			          
		          @ColumnResult(name="requestAnswerEn", type=String.class),
		          @ColumnResult(name="offerAnswerEn", type=String.class),
		          @ColumnResult(name="requestId", type=Long.class),
		          @ColumnResult(name="requestDate", type=LocalDate.class),
		          @ColumnResult(name="requestRating", type=Integer.class),
	          }
		)	
	}
	),
	
	@SqlResultSetMapping(name="rio", 
	classes={
	@ConstructorResult(
	     targetClass=RiServiceInfrastructureOrganizationDTO.class,			      
		       columns={
		          @ColumnResult(name="serviceId", type=Long.class),
		          @ColumnResult(name="infraStructureId", type=Long.class),
		          @ColumnResult(name="organizationId", type=Long.class),
		          @ColumnResult(name="serviceNameSr", type=String.class),
		          @ColumnResult(name="serviceNameSrCyr", type=String.class),
		          @ColumnResult(name="serviceNameEn", type=String.class),
		          @ColumnResult(name="serviceDescriptionSr", type=String.class),
		          @ColumnResult(name="serviceDescriptionSrCyr", type=String.class),
		          @ColumnResult(name="serviceDescriptionEn", type=String.class),		          
		          @ColumnResult(name="infrastructureNameSr", type=String.class),
		          @ColumnResult(name="infrastructureNameSrCyr", type=String.class),
		          @ColumnResult(name="infrastructureNameEn", type=String.class),
		          @ColumnResult(name="organizationNameSr", type=String.class),
		          @ColumnResult(name="organizationNameSrCyr", type=String.class),
		          @ColumnResult(name="organizationNameEn", type=String.class),
		        
	          }
		)	
	}
	)
	
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="RiService.searchBySerbianKeywords", 
			  query="SELECT distinct(r.id) as id, r.name_sr as nameSr, r.name_en as nameEn, r.name_sr_cyr as nameSrCyr, "
			  		+ "	r.description_sr as descriptionSr, r.description_en as descriptionEn, r.description_sr_cyr as descriptionSrCyr, "
			  		+ "	r.keywords as keywords, r.keywords_en as keywordsEn, r.keywords_cyr as keywordsCyr, "
			  		+ "	rin.id as infrastructureId, rin.name_sr as infrastructureNameSr, rin.name_en as infrastructureNameEn, "
			  		+ "	rin.name_sr_cyr as infrastructureNameSrCyr, "
			  		+ " sa.id as availabilityId, sa.availability as availability, sa.availability_en as availabilityEn, "
			  		+ " sa.availability_cyr as availabilityCyr, "
			  		+ " ss.id as subtypeId, ss.subtype as subtype, ss.subtype_en as subtypeEn, ss.subtype_cyr as subtypeCyr "
			  		+ "	FROM ri_service r "
			  		+ "	left join research_infrastructure rin on rin.id = r.research_infrastructure_id "
			  		+ " left join service_availability sa on sa.id = r.availability_id "
			  		+ " left join service_subtype ss on ss.id = r.subtypes_id "
					+ " left join ri_service_subdomains ris on ris.ri_service_id = r.id "
					+ "	WHERE (1 = :keywordsNotExist or (r.serbian_search @@ to_tsquery('serbian', :keywords ) "
					+ " or rin.serbian_search @@ to_tsquery('serbian', :keywords )))"
					+ " and (1 = :subdomainsNotExist or (ris.subdomains_id in ( :subdomains )))", resultSetMapping="clp"),
	
	@NamedNativeQuery(name="RiService.searchByEnglishKeywords", 
	  query="SELECT distinct(r.id) as id, r.name_sr as nameSr, r.name_en as nameEn, r.name_sr_cyr as nameSrCyr, "
	  		+ "	r.description_sr as descriptionSr, r.description_en as descriptionEn, r.description_sr_cyr as descriptionSrCyr, "
	  		+ "	r.keywords as keywords, r.keywords_en as keywordsEn, r.keywords_cyr as keywordsCyr, "
	  		+ "	rin.id as infrastructureId, rin.name_sr as infrastructureNameSr, rin.name_en as infrastructureNameEn, "
	  		+ "	rin.name_sr_cyr as infrastructureNameSrCyr, "
	  		+ " sa.id as availabilityId, sa.availability as availability, sa.availability_en as availabilityEn, "
	  		+ " sa.availability_cyr as availabilityCyr, "
	  		+ " ss.id as subtypeId, ss.subtype as subtype, ss.subtype_en as subtypeEn, ss.subtype_cyr as subtypeCyr "
	  		+ "	FROM ri_service r "
	  		+ "	left join research_infrastructure rin on rin.id = r.research_infrastructure_id "
	  		+ " left join service_availability sa on sa.id = r.availability_id "
	  		+ " left join service_subtype ss on ss.id = r.subtypes_id "
			+ " left join ri_service_subdomains ris on ris.ri_service_id = r.id "
			+ "	WHERE (1 = :keywordsNotExist or (r.english_search @@ to_tsquery('english', :keywords ) "
			+ " or rin.english_search @@ to_tsquery('english', :keywords )))"
			+ " and (1 = :subdomainsNotExist or (ris.subdomains_id in ( :subdomains )))", resultSetMapping="clp"),
	
	@NamedNativeQuery(name="RiService.getStatisticData", 
	  query="select ri.id, ri.name_sr as nameSr, ri.name_en as nameEn, ri.name_sr_cyr as nameSrCyr, "
	  		+ " ri.description_sr as descriptionSr, ri.description_en as descriptionEn, "
	  		+ "ri.description_sr_cyr as descriptionSrCyr, "
	  		+ "	rin.id as infrastructureId, rin.name_sr as infrastructureNameSr, rin.name_en as infrastructureNameEn, "
	  		+ "	rin.name_sr_cyr as infrastructureNameSrCyr, "
	  		+ " sa.id as availabilityId, sa.availability as availability, sa.availability_en as availabilityEn, "
	  		+ " sa.availability_cyr as availabilityCyr, "	  		
			+ " ss.id as subtypeId, ss.subtype as subtype, ss.subtype_en as subtypeEn, ss.subtype_cyr as subtypeCyr, "	  					
	  		+ "ra.answer_en as requestAnswerEn, "
	  		+ "ato.answer_en as offerAnswerEn,"
	  		+ "rf.id as requestId, rf.date as requestDate, rf.rating as requestRating "
	  		+ "from ri_service ri "
	  		+ "left join service_availability sa on sa.id = ri.availability_id "
	  		+ "left join service_subtype ss on ss.id = ri.subtypes_id "
	  		+ "left join research_infrastructure rin on rin.id = ri.research_infrastructure_id "
	  		+ "left join request_for_service rf on rf.services_id = ri.id "
	  		+ "left join request_respond rr on rf.id = rr.request_for_service_id "
	  		+ "left join request_answer ra on ra.id = rr.request_answer_id "
	  		+ "left join service_offer_respond sor on sor.request_respond_id = rr.id "
	  		+ "left join answer_to_offer ato on ato.id = sor.offer_answer_id "
	  		+ "order by ri.id", resultSetMapping="rss"),
	
	@NamedNativeQuery(name="RiService.getStatisticDataForResearchOrganization", 
	  query="select ri.id, ri.name_sr as nameSr, ri.name_en as nameEn, ri.name_sr_cyr as nameSrCyr, "
	  		+ " ri.description_sr as descriptionSr, ri.description_en as descriptionEn, "
	  		+ "ri.description_sr_cyr as descriptionSrCyr, "
	  		+ "	rin.id as infrastructureId, rin.name_sr as infrastructureNameSr, rin.name_en as infrastructureNameEn, "
	  		+ "	rin.name_sr_cyr as infrastructureNameSrCyr, "
	  		+ " sa.id as availabilityId, sa.availability as availability, sa.availability_en as availabilityEn, "
	  		+ " sa.availability_cyr as availabilityCyr, "	  		
			+ " ss.id as subtypeId, ss.subtype as subtype, ss.subtype_en as subtypeEn, ss.subtype_cyr as subtypeCyr, "	  					
	  		+ "ra.answer_en as requestAnswerEn, "
	  		+ "ato.answer_en as offerAnswerEn,"
	  		+ "rf.id as requestId, rf.date as requestDate, rf.rating as requestRating  "
	  		+ "from ri_service ri "
	  		+ "left join service_availability sa on sa.id = ri.availability_id "
	  		+ "left join service_subtype ss on ss.id = ri.subtypes_id "
	  		+ "left join research_infrastructure rin on rin.id = ri.research_infrastructure_id "
	  		+ "left join request_for_service rf on rf.services_id = ri.id "
	  		+ "left join request_respond rr on rf.id = rr.request_for_service_id "
	  		+ "left join request_answer ra on ra.id = rr.request_answer_id "
	  		+ "left join service_offer_respond sor on sor.request_respond_id = rr.id "
	  		+ "left join answer_to_offer ato on ato.id = sor.offer_answer_id "
	  		+ " where rin.owner_id = :id "
	  		+ "order by ri.id", resultSetMapping="rss"),
	
	@NamedNativeQuery(name="RiService.searchAllBySerbianKeywords", 
	  query="select sio.serviceid, sio.infrastructureId,sio.organizationid, "
	  		+ "ri.name_sr as serviceNameSr, ri.name_en as serviceNameEn, ri.name_sr_cyr as serviceNameSrCyr, "
	  		+ "ri.description_sr as serviceDescriptionSr, ri.description_sr_cyr as serviceDescriptionSrCyr, "
	  		+ "ri.description_en as serviceDescriptionEn, "
	  		+ "rs.name_sr as infrastructureNameSr, rs.name_sr_cyr as infrastructureNameSrCyr, rs.name_en as infrastructureNameEn, "
	  		+ "puo.legal_name_sr as organizationNameSr, puo.legal_name_sr_cyr as organizationNameSrCyr, "
	  		+ "puo.legal_name_en as organizationNameEn "
	  		+ "from service_infrastructure_organization sio "
	  		+ "left join ri_service ri on ri.id = sio.serviceid "
	  		+ "left join research_infrastructure rs on rs.id = sio.infrastructureid "
	  		+ " left join portal_user_organization puo on puo.id = sio.organizationId "
	  		+ "where sio.serbian_search @@ to_tsquery('serbian', :keywords )", resultSetMapping="rio"),
	
	@NamedNativeQuery(name="RiService.searchAllByEnglishKeywords", 
	  query="select sio.serviceid, sio.infrastructureId,sio.organizationid, "
	  		+ "ri.name_sr as serviceNameSr, ri.name_en as serviceNameEn, ri.name_sr_cyr as serviceNameSrCyr, "
	  		+ "ri.description_sr as serviceDescriptionSr, ri.description_sr_cyr as serviceDescriptionSrCyr, "
	  		+ "ri.description_en as serviceDescriptionEn, "
	  		+ "rs.name_sr as infrastructureNameSr, rs.name_sr_cyr as infrastructureNameSrCyr, rs.name_en as infrastructureNameEn, "
	  		+ "puo.legal_name_sr as organizationNameSr, puo.legal_name_sr_cyr as organizationNameSrCyr, "
	  		+ "puo.legal_name_en as organizationNameEn "
	  		+ "from service_infrastructure_organization sio "
	  		+ "left join ri_service ri on ri.id = sio.serviceid "
	  		+ "left join research_infrastructure rs on rs.id = sio.infrastructureid "
	  		+ " left join portal_user_organization puo on puo.id = sio.organizationId "
	  		+ "where sio.english_search @@ to_tsquery('english', :keywords )", resultSetMapping="rio"),
	
	})

public class RiService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name_sr")
    private String nameSr;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "description_sr")
    private String descriptionSr;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "keywords_en")
    private String keywordsEn;
    
    @Column(name = "keywords_cyr")
    private String keywordsCyr;

    @Column(name = "accreditation_organization_name")
    private String accreditationOrganizationName;

    /**
     * Link to the Terms of use in Serbian in Policy document repository
     */
    @ApiModelProperty(value = "Link to the Terms of use in Serbian in Policy document repository")
    @Column(name = "terms_of_use_sr")
    private String termsOfUseSr;

    /**
     * Link to the Terms of use in English in Policy document repository
     */
    @ApiModelProperty(value = "Link to the Terms of use in English in Policy document repository")
    @Column(name = "terms_of_use_en")
    private String termsOfUseEn;

    /**
     * Link to the privacy policy in Serbian in Policy document repository
     */
    @ApiModelProperty(value = "Link to the privacy policy in Serbian in Policy document repository")
    @Column(name = "privacy_policy_sr")
    private String privacyPolicySr;

    @Column(name = "privacy_policy_en")
    private String privacyPolicyEn;

    /**
     * Link to the Access policy in Serbian in Policy document repository
     */
    @ApiModelProperty(value = "Link to the Access policy in Serbian in Policy document repository")
    @Column(name = "access_policy_sr")
    private String accessPolicySr;

    @Column(name = "access_policy_en")
    private String accessPolicyEn;

    /**
     * Link to the Pricing policy in Serbian in Policy document repository
     */
    @ApiModelProperty(value = "Link to the Pricing policy in Serbian in Policy document repository")
    @Column(name = "pricing_policy_sr")
    private String pricingPolicySr;

    @Column(name = "pricing_policy_en")
    private String pricingPolicyEn;

    @Column(name = "name_sr_cyr")
    private String nameSrCyr;

    @Column(name = "terms_of_use_sr_cyr")
    private String termsOfUseSrCyr;

    @Column(name = "access_policy_sr_cyr")
    private String accessPolicySrCyr;

    @Column(name = "pricing_policy_sr_cyr")
    private String pricingPolicySrCyr;

    @Column(name = "description_sr_cyr")
    private String descriptionSrCyr;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "currency")
    private String currency;

    @OneToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RequestForService> requests = new HashSet<>();

    @OneToMany(mappedBy = "service")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiCalendar> calendars = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "riServices", allowSetters = true)
    private ServiceAvailability availability;

    @ManyToOne
    @JsonIgnoreProperties(value = "riServices", allowSetters = true)
    private ServiceAccreditation accredation;

    @ManyToOne
    @JsonIgnoreProperties(value = "riServices", allowSetters = true)
    private ServiceSubtype subtypes;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ri_service_subdomains",
               joinColumns = @JoinColumn(name = "ri_service_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subdomains_id", referencedColumnName = "id"))
    private Set<ScientificSubdomain> subdomains = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "services", allowSetters = true)
    private ResearchInfrastructure researchInfrastructure;

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

    public RiService nameSr(String nameSr) {
        this.nameSr = nameSr;
        return this;
    }

    public void setNameSr(String nameSr) {
        this.nameSr = nameSr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public RiService nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDescriptionSr() {
        return descriptionSr;
    }

    public RiService descriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
        return this;
    }

    public void setDescriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public RiService descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
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

	public String getKeywords() {
        return keywords;
    }

    public RiService keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAccreditationOrganizationName() {
        return accreditationOrganizationName;
    }

    public RiService accreditationOrganizationName(String accreditationOrganizationName) {
        this.accreditationOrganizationName = accreditationOrganizationName;
        return this;
    }

    public void setAccreditationOrganizationName(String accreditationOrganizationName) {
        this.accreditationOrganizationName = accreditationOrganizationName;
    }

    public String getTermsOfUseSr() {
        return termsOfUseSr;
    }

    public RiService termsOfUseSr(String termsOfUseSr) {
        this.termsOfUseSr = termsOfUseSr;
        return this;
    }

    public void setTermsOfUseSr(String termsOfUseSr) {
        this.termsOfUseSr = termsOfUseSr;
    }

    public String getTermsOfUseEn() {
        return termsOfUseEn;
    }

    public RiService termsOfUseEn(String termsOfUseEn) {
        this.termsOfUseEn = termsOfUseEn;
        return this;
    }

    public void setTermsOfUseEn(String termsOfUseEn) {
        this.termsOfUseEn = termsOfUseEn;
    }

    public String getPrivacyPolicySr() {
        return privacyPolicySr;
    }

    public RiService privacyPolicySr(String privacyPolicySr) {
        this.privacyPolicySr = privacyPolicySr;
        return this;
    }

    public void setPrivacyPolicySr(String privacyPolicySr) {
        this.privacyPolicySr = privacyPolicySr;
    }

    public String getPrivacyPolicyEn() {
        return privacyPolicyEn;
    }

    public RiService privacyPolicyEn(String privacyPolicyEn) {
        this.privacyPolicyEn = privacyPolicyEn;
        return this;
    }

    public void setPrivacyPolicyEn(String privacyPolicyEn) {
        this.privacyPolicyEn = privacyPolicyEn;
    }

    public String getAccessPolicySr() {
        return accessPolicySr;
    }

    public RiService accessPolicySr(String accessPolicySr) {
        this.accessPolicySr = accessPolicySr;
        return this;
    }

    public void setAccessPolicySr(String accessPolicySr) {
        this.accessPolicySr = accessPolicySr;
    }

    public String getAccessPolicyEn() {
        return accessPolicyEn;
    }

    public RiService accessPolicyEn(String accessPolicyEn) {
        this.accessPolicyEn = accessPolicyEn;
        return this;
    }

    public void setAccessPolicyEn(String accessPolicyEn) {
        this.accessPolicyEn = accessPolicyEn;
    }

    public String getPricingPolicySr() {
        return pricingPolicySr;
    }

    public RiService pricingPolicySr(String pricingPolicySr) {
        this.pricingPolicySr = pricingPolicySr;
        return this;
    }

    public void setPricingPolicySr(String pricingPolicySr) {
        this.pricingPolicySr = pricingPolicySr;
    }

    public String getPricingPolicyEn() {
        return pricingPolicyEn;
    }

    public RiService pricingPolicyEn(String pricingPolicyEn) {
        this.pricingPolicyEn = pricingPolicyEn;
        return this;
    }

    public void setPricingPolicyEn(String pricingPolicyEn) {
        this.pricingPolicyEn = pricingPolicyEn;
    }

    public String getNameSrCyr() {
        return nameSrCyr;
    }

    public RiService nameSrCyr(String nameSrCyr) {
        this.nameSrCyr = nameSrCyr;
        return this;
    }

    public void setNameSrCyr(String nameSrCyr) {
        this.nameSrCyr = nameSrCyr;
    }

    public String getTermsOfUseSrCyr() {
        return termsOfUseSrCyr;
    }

    public RiService termsOfUseSrCyr(String termsOfUseSrCyr) {
        this.termsOfUseSrCyr = termsOfUseSrCyr;
        return this;
    }

    public void setTermsOfUseSrCyr(String termsOfUseSrCyr) {
        this.termsOfUseSrCyr = termsOfUseSrCyr;
    }

    public String getAccessPolicySrCyr() {
        return accessPolicySrCyr;
    }

    public RiService accessPolicySrCyr(String accessPolicySrCyr) {
        this.accessPolicySrCyr = accessPolicySrCyr;
        return this;
    }

    public void setAccessPolicySrCyr(String accessPolicySrCyr) {
        this.accessPolicySrCyr = accessPolicySrCyr;
    }

    public String getPricingPolicySrCyr() {
        return pricingPolicySrCyr;
    }

    public RiService pricingPolicySrCyr(String pricingPolicySrCyr) {
        this.pricingPolicySrCyr = pricingPolicySrCyr;
        return this;
    }

    public void setPricingPolicySrCyr(String pricingPolicySrCyr) {
        this.pricingPolicySrCyr = pricingPolicySrCyr;
    }

    public String getDescriptionSrCyr() {
        return descriptionSrCyr;
    }

    public RiService descriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
        return this;
    }

    public void setDescriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
    }

    public Set<RequestForService> getRequests() {
        return requests;
    }

    public RiService requests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
        return this;
    }

    public RiService addRequests(RequestForService requestForService) {
        this.requests.add(requestForService);
        requestForService.setServices(this);
        return this;
    }

    public RiService removeRequests(RequestForService requestForService) {
        this.requests.remove(requestForService);
        requestForService.setServices(null);
        return this;
    }

    public void setRequests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
    }

    public Set<RiCalendar> getCalendars() {
        return calendars;
    }

    public RiService calendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
        return this;
    }

    public RiService addCalendars(RiCalendar riCalendar) {
        this.calendars.add(riCalendar);
        riCalendar.setService(this);
        return this;
    }

    public RiService removeCalendars(RiCalendar riCalendar) {
        this.calendars.remove(riCalendar);
        riCalendar.setService(null);
        return this;
    }

    public void setCalendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
    }

    public ServiceAvailability getAvailability() {
        return availability;
    }

    public RiService availability(ServiceAvailability serviceAvailability) {
        this.availability = serviceAvailability;
        return this;
    }

    public void setAvailability(ServiceAvailability serviceAvailability) {
        this.availability = serviceAvailability;
    }

    public ServiceAccreditation getAccredation() {
        return accredation;
    }

    public RiService accredation(ServiceAccreditation serviceAccreditation) {
        this.accredation = serviceAccreditation;
        return this;
    }

    public void setAccredation(ServiceAccreditation serviceAccreditation) {
        this.accredation = serviceAccreditation;
    }

    public ServiceSubtype getSubtypes() {
        return subtypes;
    }

    public RiService subtypes(ServiceSubtype serviceSubtype) {
        this.subtypes = serviceSubtype;
        return this;
    }

    public void setSubtypes(ServiceSubtype serviceSubtype) {
        this.subtypes = serviceSubtype;
    }

    public Set<ScientificSubdomain> getSubdomains() {
        return subdomains;
    }

    public RiService subdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
        return this;
    }

    public RiService addSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.add(scientificSubdomain);
        scientificSubdomain.getServices().add(this);
        return this;
    }

    public RiService removeSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.remove(scientificSubdomain);
        scientificSubdomain.getServices().remove(this);
        return this;
    }

    public void setSubdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
    }

    public ResearchInfrastructure getResearchInfrastructure() {
        return researchInfrastructure;
    }

    public RiService researchInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.researchInfrastructure = researchInfrastructure;
        return this;
    }

    public void setResearchInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.researchInfrastructure = researchInfrastructure;
    }
    
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiService)) {
            return false;
        }
        return id != null && id.equals(((RiService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiService{" +
            "id=" + getId() +
            ", nameSr='" + getNameSr() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", descriptionSr='" + getDescriptionSr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", accreditationOrganizationName='" + getAccreditationOrganizationName() + "'" +
            ", termsOfUseSr='" + getTermsOfUseSr() + "'" +
            ", termsOfUseEn='" + getTermsOfUseEn() + "'" +
            ", privacyPolicySr='" + getPrivacyPolicySr() + "'" +
            ", privacyPolicyEn='" + getPrivacyPolicyEn() + "'" +
            ", accessPolicySr='" + getAccessPolicySr() + "'" +
            ", accessPolicyEn='" + getAccessPolicyEn() + "'" +
            ", pricingPolicySr='" + getPricingPolicySr() + "'" +
            ", pricingPolicyEn='" + getPricingPolicyEn() + "'" +
            ", nameSrCyr='" + getNameSrCyr() + "'" +
            ", termsOfUseSrCyr='" + getTermsOfUseSrCyr() + "'" +
            ", accessPolicySrCyr='" + getAccessPolicySrCyr() + "'" +
            ", pricingPolicySrCyr='" + getPricingPolicySrCyr() + "'" +
            ", descriptionSrCyr='" + getDescriptionSrCyr() + "'" +
            "}";
    }
}
