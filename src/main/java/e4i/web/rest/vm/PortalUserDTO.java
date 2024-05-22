package e4i.web.rest.vm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.PortalUserOrganization;
import e4i.domain.PortalUserRole;
import e4i.domain.RequestForService;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.Researcher;
import e4i.domain.RiCalendar;
import e4i.domain.RiProcurementProposal;
import e4i.domain.RiResearchOrganization;
import e4i.domain.ServiceProposal;
import e4i.domain.User;

public class PortalUserDTO {	
	
	private String id;
    private String phone;
    private String position;
    private Researcher researcher;
    private ManagedUserVM user;
    private Set<PortalUserRole> portalUserRoles = new HashSet<>();
    private PortalUserOrganization userOrganization;
    
    
	public PortalUserDTO() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Researcher getResearcher() {
		return researcher;
	}
	public void setResearcher(Researcher researcher) {
		this.researcher = researcher;
	}
	public ManagedUserVM getUser() {
		return user;
	}
	public void setUser(ManagedUserVM user) {
		this.user = user;
	}
	public Set<PortalUserRole> getPortalUserRoles() {
		return portalUserRoles;
	}
	public void setPortalUserRoles(Set<PortalUserRole> portalUserRoles) {
		this.portalUserRoles = portalUserRoles;
	}
	public PortalUserOrganization getUserOrganization() {
		return userOrganization;
	}
	public void setUserOrganization(PortalUserOrganization userOrganization) {
		this.userOrganization = userOrganization;
	}
    
    

}
