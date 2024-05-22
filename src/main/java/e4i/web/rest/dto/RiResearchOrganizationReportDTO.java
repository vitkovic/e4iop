package e4i.web.rest.dto;

import java.util.List;

import e4i.domain.RiResearchOrganization;

public class RiResearchOrganizationReportDTO {
	
	private Long id;
    private String type;
    private String name;
    private String address;
    private OrganizationDetailsReportDTO details;
    private List<ServiceStatisticResultDTO> services; 
	
    public RiResearchOrganizationReportDTO() {
		super();
	}
    
    public RiResearchOrganizationReportDTO(RiResearchOrganization r) {
		this.id = r.getId();
		this.name = r.getName();
		if(r.getPuOrganization().getNioInstitution() != null) {
			this.address = r.getPuOrganization().getNioInstitution().getAddress();
		}else if(r.getPuOrganization() != null) {
			this.address = r.getPuOrganization().getAddress();
		}
	}
    
    public RiResearchOrganizationReportDTO(Long id, String name, String address1, String address2) {
		this.id = id;
		this.name = name;
		if(address1 != null && !address1.equals(""))
			this.address = address1;
		else if(address2 != null && !address2.equals(""))
			this.address = address2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public List<ServiceStatisticResultDTO> getServices() {
		return services;
	}

	public void setServices(List<ServiceStatisticResultDTO> services) {
		this.services = services;
	}

	public OrganizationDetailsReportDTO getDetails() {
		return details;
	}

	public void setDetails(OrganizationDetailsReportDTO details) {
		this.details = details;
	} 
    
    
    

}
