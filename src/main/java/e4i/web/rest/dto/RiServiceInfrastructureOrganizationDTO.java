package e4i.web.rest.dto;

public class RiServiceInfrastructureOrganizationDTO {

	private Long serviceId;
	private Long infraStructureId;
	private Long organizationId;
	
	private String serviceNameSr;
	private String serviceNameSrCyr;
	private String serviceNameSrEn;
	
	private String serviceDescriptionSr;
	private String serviceDescriptionSrCyr;
	private String serviceDescriptionEn;
	
	private String infrastructureNameSr;
	private String infrastructureNameSrCyr;
	private String infrastructureNameEn;
	
	private String organizationNameSr;
	private String organizationNameSrCyr;
	private String organizationNameEn;
	
	public RiServiceInfrastructureOrganizationDTO() {
		super();
	}

	public RiServiceInfrastructureOrganizationDTO(Long serviceId, Long infraStructureId, Long organizationId,
			String serviceNameSr, String serviceNameSrCyr, String serviceNameSrEn, String serviceDescriptionSr,
			String serviceDescriptionSrCyr, String serviceDescriptionEn, String infrastructureNameSr,
			String infrastructureNameSrCyr, String infrastructureNameEn, String organizationNameSr,
			String organizationNameSrCyr, String organizationNameEn) {
		this.serviceId = serviceId;
		this.infraStructureId = infraStructureId;
		this.organizationId = organizationId;
		this.serviceNameSr = serviceNameSr;
		this.serviceNameSrCyr = serviceNameSrCyr;
		this.serviceNameSrEn = serviceNameSrEn;
		this.serviceDescriptionSr = serviceDescriptionSr;
		this.serviceDescriptionSrCyr = serviceDescriptionSrCyr;
		this.serviceDescriptionEn = serviceDescriptionEn;
		this.infrastructureNameSr = infrastructureNameSr;
		this.infrastructureNameSrCyr = infrastructureNameSrCyr;
		this.infrastructureNameEn = infrastructureNameEn;
		this.organizationNameSr = organizationNameSr;
		this.organizationNameSrCyr = organizationNameSrCyr;
		this.organizationNameEn = organizationNameEn;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getInfraStructureId() {
		return infraStructureId;
	}

	public void setInfraStructureId(Long infraStructureId) {
		this.infraStructureId = infraStructureId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getServiceNameSr() {
		return serviceNameSr;
	}

	public void setServiceNameSr(String serviceNameSr) {
		this.serviceNameSr = serviceNameSr;
	}

	public String getServiceNameSrCyr() {
		return serviceNameSrCyr;
	}

	public void setServiceNameSrCyr(String serviceNameSrCyr) {
		this.serviceNameSrCyr = serviceNameSrCyr;
	}

	public String getServiceNameSrEn() {
		return serviceNameSrEn;
	}

	public void setServiceNameSrEn(String serviceNameSrEn) {
		this.serviceNameSrEn = serviceNameSrEn;
	}

	public String getServiceDescriptionSr() {
		return serviceDescriptionSr;
	}

	public void setServiceDescriptionSr(String serviceDescriptionSr) {
		this.serviceDescriptionSr = serviceDescriptionSr;
	}

	public String getServiceDescriptionSrCyr() {
		return serviceDescriptionSrCyr;
	}

	public void setServiceDescriptionSrCyr(String serviceDescriptionSrCyr) {
		this.serviceDescriptionSrCyr = serviceDescriptionSrCyr;
	}

	public String getServiceDescriptionEn() {
		return serviceDescriptionEn;
	}

	public void setServiceDescriptionEn(String serviceDescriptionEn) {
		this.serviceDescriptionEn = serviceDescriptionEn;
	}

	public String getInfrastructureNameSr() {
		return infrastructureNameSr;
	}

	public void setInfrastructureNameSr(String infrastructureNameSr) {
		this.infrastructureNameSr = infrastructureNameSr;
	}

	public String getInfrastructureNameSrCyr() {
		return infrastructureNameSrCyr;
	}

	public void setInfrastructureNameSrCyr(String infrastructureNameSrCyr) {
		this.infrastructureNameSrCyr = infrastructureNameSrCyr;
	}

	public String getInfrastructureNameEn() {
		return infrastructureNameEn;
	}

	public void setInfrastructureNameEn(String infrastructureNameEn) {
		this.infrastructureNameEn = infrastructureNameEn;
	}

	public String getOrganizationNameSr() {
		return organizationNameSr;
	}

	public void setOrganizationNameSr(String organizationNameSr) {
		this.organizationNameSr = organizationNameSr;
	}

	public String getOrganizationNameSrCyr() {
		return organizationNameSrCyr;
	}

	public void setOrganizationNameSrCyr(String organizationNameSrCyr) {
		this.organizationNameSrCyr = organizationNameSrCyr;
	}

	public String getOrganizationNameEn() {
		return organizationNameEn;
	}

	public void setOrganizationNameEn(String organizationNameEn) {
		this.organizationNameEn = organizationNameEn;
	}
	
	
	
}
