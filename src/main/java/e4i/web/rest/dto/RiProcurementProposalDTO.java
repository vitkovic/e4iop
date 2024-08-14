package e4i.web.rest.dto;

import java.time.Instant;

import e4i.domain.PortalUser;
import e4i.domain.RiProcurementProposal;


public class RiProcurementProposalDTO {

	private Long id;
    private Instant date;
    private String name;
    private String technicalSpecification;
    private String purpose;
    private PortalUser portalUser;
    private Boolean possibleEdit = false;
    private Long likes;
    private Long dislikes;
    public String firstName;
    public String lastName;
    public String pid;
    
    public RiProcurementProposalDTO(Long id, Instant date, String name, String technicalSpecification, String purpose,  
			 String firstName, String lastName, String puserId, Boolean editable, Long likes, Long dislikes) {
    	this.id = id;
    	this.date = date;
    	this.name = name;
    	this.technicalSpecification = technicalSpecification;
    	this.purpose = purpose;
    	PortalUser pu = new PortalUser(puserId, puserId, firstName, lastName);
    	this.portalUser = pu;
    	this.possibleEdit = editable;    			
    	this.likes = likes;
    	this.dislikes = dislikes;
	} 
    
   
    
	public RiProcurementProposalDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTechnicalSpecification() {
		return technicalSpecification;
	}

	public void setTechnicalSpecification(String technicalSpecification) {
		this.technicalSpecification = technicalSpecification;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public PortalUser getPortalUser() {
		return portalUser;
	}

	public void setPortalUser(PortalUser portalUser) {
		this.portalUser = portalUser;
	}

	public boolean isPossibleEdit() {
		return possibleEdit;
	}

	public void setPossibleEdit(Boolean possibleEdit) {
		this.possibleEdit = possibleEdit;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getDislikes() {
		return dislikes;
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
