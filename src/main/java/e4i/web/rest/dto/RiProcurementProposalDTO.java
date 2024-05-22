package e4i.web.rest.dto;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.PortalUser;
import e4i.domain.RiProcurementProposal;
import io.swagger.annotations.ApiModelProperty;

public class RiProcurementProposalDTO {

	private Long id;
    private ZonedDateTime date;
    private String name;
    private String technicalSpecification;
    private String purpose;
    private PortalUser portalUser;
    private boolean possibleEdit = false;
    private Integer likes;
    private Integer dislikes;
    
    public RiProcurementProposalDTO(Long id, ZonedDateTime date, String name, String technicalSpecification, String purpose,  
			 String firstName, String lastName, String puserId, boolean editable, int likes, int dislikes) {
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
    
    public RiProcurementProposalDTO(RiProcurementProposal rpp, boolean editable) {
    	this.id = rpp.getId();
    	this.date = rpp.getDate();
    	this.name = rpp.getName();
    	this.technicalSpecification = rpp.getTechnicalSpecification();
    	this.purpose = rpp.getPurpose();
    	this.portalUser = rpp.getPortalUser();
    	this.possibleEdit = editable;  
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

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
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

	public void setPossibleEdit(boolean possibleEdit) {
		this.possibleEdit = possibleEdit;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}
    
    
}
