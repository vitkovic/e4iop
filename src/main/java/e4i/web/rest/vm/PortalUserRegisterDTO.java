package e4i.web.rest.vm;

import javax.persistence.Column;

import e4i.domain.PortalUserOrganization;
import e4i.domain.PortalUserStatus;
import e4i.domain.PuoLegalStatus;
import e4i.domain.ResearchInstitution;
import e4i.domain.Researcher;

public class PortalUserRegisterDTO extends ManagedUserVM {
	
	private String phone;    
    private String position;
    private String organizationNameSr;
    private String organizationNameEn;
    private String organizationShortName;
    private String organizationWebAddress;
    PuoLegalStatus organizationLegalStatus;
    private String organizationPib;
    private String organizationMaticniBroj;
    private String organizationKeywords;
    private String organizationAddress;
    private String organizationAddressPostalCode;
    private String organizationAddressCity;
    private String organizationAddressCountry;
    private ResearchInstitution organizationRI;
    private PortalUserOrganization portalUserOrganization;
    private PortalUserStatus portalUserStatus;
    private Researcher researcher;


	public ResearchInstitution getOrganizationRI() {
		return organizationRI;
	}

	public void setOrganizationRI(ResearchInstitution organizationRI) {
		this.organizationRI = organizationRI;
	}

	public PortalUserStatus getPortalUserStatus() {
		return portalUserStatus;
	}

	public void setPortalUserStatus(PortalUserStatus portalUserStatus) {
		this.portalUserStatus = portalUserStatus;
	}

	public PortalUserOrganization getPortalUserOrganization() {
		return portalUserOrganization;
	}

	public void setPortalUserOrganization(PortalUserOrganization portalUserOrganization) {
		this.portalUserOrganization = portalUserOrganization;
	}

	public PortalUserRegisterDTO() {
		
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getOrganizationNameSr() {
		return organizationNameSr;
	}

	public void setOrganizationNameSr(String organizationNameSr) {
		this.organizationNameSr = organizationNameSr;
	}

	public String getOrganizationNameEn() {
		return organizationNameEn;
	}

	public void setOrganizationNameEn(String organizationNameEn) {
		this.organizationNameEn = organizationNameEn;
	}

	public String getOrganizationShortName() {
		return organizationShortName;
	}

	public void setOrganizationShortName(String organizationShortName) {
		this.organizationShortName = organizationShortName;
	}

	public String getOrganizationWebAddress() {
		return organizationWebAddress;
	}

	public void setOrganizationWebAddress(String organizationWebAddress) {
		this.organizationWebAddress = organizationWebAddress;
	}

	public PuoLegalStatus getOrganizationLegalStatus() {
		return organizationLegalStatus;
	}

	public void setOrganizationLegalStatus(PuoLegalStatus organizationLegalStatus) {
		this.organizationLegalStatus = organizationLegalStatus;
	}

	public String getOrganizationPib() {
		return organizationPib;
	}

	public void setOrganizationPib(String organizationPib) {
		this.organizationPib = organizationPib;
	}

	public String getOrganizationMaticniBroj() {
		return organizationMaticniBroj;
	}

	public void setOrganizationMaticniBroj(String organizationMaticniBroj) {
		this.organizationMaticniBroj = organizationMaticniBroj;
	}

	public String getOrganizationKeywords() {
		return organizationKeywords;
	}

	public void setOrganizationKeywords(String organizationKeywords) {
		this.organizationKeywords = organizationKeywords;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getOrganizationAddressPostalCode() {
		return organizationAddressPostalCode;
	}

	public void setOrganizationAddressPostalCode(String organizationAddressPostalCode) {
		this.organizationAddressPostalCode = organizationAddressPostalCode;
	}

	public String getOrganizationAddressCity() {
		return organizationAddressCity;
	}

	public void setOrganizationAddressCity(String organizationAddressCity) {
		this.organizationAddressCity = organizationAddressCity;
	}

	public String getOrganizationAddressCountry() {
		return organizationAddressCountry;
	}

	public void setOrganizationAddressCountry(String organizationAddressCountry) {
		this.organizationAddressCountry = organizationAddressCountry;
	}
    
	public Researcher getResearcher() {
		return researcher;
	}

	public void setResearcher(Researcher researcher) {
		this.researcher = researcher;
	}
    

}
