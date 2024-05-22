package e4i.web.rest.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.RequestForService;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiCalendar;
import e4i.domain.ScientificSubdomain;
import e4i.domain.ServiceAccreditation;
import e4i.domain.ServiceAvailability;
import e4i.domain.ServiceSubtype;
import io.swagger.annotations.ApiModelProperty;

public class RiServiceDTOSearch {

	protected Long id;
    protected String nameSr;
    protected String nameEn;
    protected String descriptionSr;
    protected String descriptionEn;
    protected String keywords;
    protected String keywordsEn;
    protected String keywordsCyr;
    protected String accreditationOrganizationName;
    protected String termsOfUseSr;
    protected String termsOfUseEn;
    protected String privacyPolicySr;
    protected String privacyPolicyEn;
    protected String accessPolicySr;
    protected String accessPolicyEn;
    protected String pricingPolicySr;
    protected String pricingPolicyEn;
    protected String nameSrCyr;
    protected String termsOfUseSrCyr;
    protected String accessPolicySrCyr;
    protected String pricingPolicySrCyr;
    protected String descriptionSrCyr;
    protected ResearchInfrastructure researchInfrastructure;
    protected ServiceAvailability availability;
    protected ServiceSubtype subtypes;
    
    
	public RiServiceDTOSearch(Long id, String nameSr, String nameEn, String nameSrCyr,
			String descriptionSr, String descriptionEn, String descriptionSrCyr,
			String keywords, String keywordsEn, String keywordsCyr, 
			Long infrastructureId, String infrastructureNameSr, String infrastructureNameEn,String infrastructureNameSrCyr,
			Long availabilityId,String availability, String availabilityEn, String availabilityCyr, Long subtypeId,
			String subtype, String subtypeEn,String subtypeCyr			
			) {
		
		this.id = id;
		this.nameSr = nameSr;
		this.nameEn = nameEn;
		this.descriptionSr = descriptionSr;
		this.descriptionEn = descriptionEn;
		this.keywords = keywords;
		this.keywordsEn = keywordsEn;
		this.keywordsCyr = keywordsCyr;		
		this.nameSrCyr = nameSrCyr;		
		this.descriptionSrCyr = descriptionSrCyr;
		
		this.researchInfrastructure = new ResearchInfrastructure(infrastructureId, infrastructureNameSr, 
				infrastructureNameEn, infrastructureNameSrCyr);
		
		this.availability = new ServiceAvailability(availabilityId, availability, availabilityEn, availabilityCyr);
		this.subtypes = new ServiceSubtype(subtypeId, subtype, subtypeEn, subtypeCyr);		
		
	}
	public RiServiceDTOSearch() {
		super();
	}
	
	public RiServiceDTOSearch(Long id, String nameSr, String nameEn, String nameSrCyr,
			String descriptionSr, String descriptionEn, String descriptionSrCyr,
			String keywords, String keywordsEn, String keywordsCyr, ResearchInfrastructure rinf,
			ServiceAvailability sa, ServiceSubtype ss) {
		
		this.id = id;
		this.nameSr = nameSr;
		this.nameEn = nameEn;
		this.descriptionSr = descriptionSr;
		this.descriptionEn = descriptionEn;
		this.keywords = keywords;
		this.keywordsEn = keywordsEn;
		this.keywordsCyr = keywordsCyr;		
		this.nameSrCyr = nameSrCyr;		
		this.descriptionSrCyr = descriptionSrCyr;
		
		this.researchInfrastructure = rinf;		
		this.availability = sa;
		this.subtypes = ss;		
		
	}
	
	
	public ServiceAvailability getAvailability() {
		return availability;
	}
	public void setAvailability(ServiceAvailability availability) {
		this.availability = availability;
	}
	public ServiceSubtype getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(ServiceSubtype subtypes) {
		this.subtypes = subtypes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameSr() {
		return nameSr;
	}
	public void setNameSr(String nameSr) {
		this.nameSr = nameSr;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getDescriptionSr() {
		return descriptionSr;
	}
	public void setDescriptionSr(String descriptionSr) {
		this.descriptionSr = descriptionSr;
	}
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	public String getAccreditationOrganizationName() {
		return accreditationOrganizationName;
	}
	public void setAccreditationOrganizationName(String accreditationOrganizationName) {
		this.accreditationOrganizationName = accreditationOrganizationName;
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
	public String getPrivacyPolicySr() {
		return privacyPolicySr;
	}
	public void setPrivacyPolicySr(String privacyPolicySr) {
		this.privacyPolicySr = privacyPolicySr;
	}
	public String getPrivacyPolicyEn() {
		return privacyPolicyEn;
	}
	public void setPrivacyPolicyEn(String privacyPolicyEn) {
		this.privacyPolicyEn = privacyPolicyEn;
	}
	public String getAccessPolicySr() {
		return accessPolicySr;
	}
	public void setAccessPolicySr(String accessPolicySr) {
		this.accessPolicySr = accessPolicySr;
	}
	public String getAccessPolicyEn() {
		return accessPolicyEn;
	}
	public void setAccessPolicyEn(String accessPolicyEn) {
		this.accessPolicyEn = accessPolicyEn;
	}
	public String getPricingPolicySr() {
		return pricingPolicySr;
	}
	public void setPricingPolicySr(String pricingPolicySr) {
		this.pricingPolicySr = pricingPolicySr;
	}
	public String getPricingPolicyEn() {
		return pricingPolicyEn;
	}
	public void setPricingPolicyEn(String pricingPolicyEn) {
		this.pricingPolicyEn = pricingPolicyEn;
	}
	public String getNameSrCyr() {
		return nameSrCyr;
	}
	public void setNameSrCyr(String nameSrCyr) {
		this.nameSrCyr = nameSrCyr;
	}
	public String getTermsOfUseSrCyr() {
		return termsOfUseSrCyr;
	}
	public void setTermsOfUseSrCyr(String termsOfUseSrCyr) {
		this.termsOfUseSrCyr = termsOfUseSrCyr;
	}
	public String getAccessPolicySrCyr() {
		return accessPolicySrCyr;
	}
	public void setAccessPolicySrCyr(String accessPolicySrCyr) {
		this.accessPolicySrCyr = accessPolicySrCyr;
	}
	public String getPricingPolicySrCyr() {
		return pricingPolicySrCyr;
	}
	public void setPricingPolicySrCyr(String pricingPolicySrCyr) {
		this.pricingPolicySrCyr = pricingPolicySrCyr;
	}
	public String getDescriptionSrCyr() {
		return descriptionSrCyr;
	}
	public void setDescriptionSrCyr(String descriptionSrCyr) {
		this.descriptionSrCyr = descriptionSrCyr;
	}
	public ResearchInfrastructure getResearchInfrastructure() {
		return researchInfrastructure;
	}
	public void setResearchInfrastructure(ResearchInfrastructure researchInfrastructure) {
		this.researchInfrastructure = researchInfrastructure;
	}
    
    
    
}
