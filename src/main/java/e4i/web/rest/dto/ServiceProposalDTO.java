package e4i.web.rest.dto;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.PortalUser;
import e4i.domain.RiProcurementProposal;
import io.swagger.annotations.ApiModelProperty;

public class ServiceProposalDTO {
	
	private Long id;
    private ZonedDateTime date;
    private String name;
    private String purpose;
    private PortalUser portalUser;
    private boolean possibleEdit = false;        
    private String description;
    private Integer likes;
    private Integer dislikes;
    
    public ServiceProposalDTO(Long id, ZonedDateTime date, String name, String description, String purpose,  
			 String firstName, String lastName, String puserId, boolean editable, int likes, int dislikes) {
    	this.id = id;
    	this.date = date;
    	this.name = name;
    	this.description = description;
    	this.purpose = purpose;
    	PortalUser pu = new PortalUser(puserId, puserId, firstName, lastName);
    	this.portalUser = pu;
    	this.possibleEdit = editable;    		
    	this.likes = likes;
    	this.dislikes = dislikes;
	} 
    
  

	public ServiceProposalDTO() {
		super();
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


	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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
    
    

}
