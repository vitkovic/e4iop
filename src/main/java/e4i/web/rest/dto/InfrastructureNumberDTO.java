package e4i.web.rest.dto;

import java.util.List;

public class InfrastructureNumberDTO {	
	
	private Long infrastructureNumber;
	private List<InfrastructureStatusDTO> numberPerStatuses;
	private List<InfrastructureAccessModeDTO> numberPeraccessModes;
	private List<InfrastructureAccessTypeDTO> numberPerAccessTypes;
	private List<InfrastructureCategoryDTO> numberPerCategories;
	private List<RiSubcategoryDTO> numberPerSubcategory;
	

	public InfrastructureNumberDTO() {
		super();
	}

	

	public InfrastructureNumberDTO(Long infrastructureNumber, List<InfrastructureStatusDTO> numberPerStatuses,
			List<InfrastructureAccessModeDTO> numberPeraccessModes,
			List<InfrastructureAccessTypeDTO> numberPerAccessTypes, List<InfrastructureCategoryDTO> numberPerCategories,
			List<RiSubcategoryDTO> numberPerSubcategory) {
		super();
		this.infrastructureNumber = infrastructureNumber;
		this.numberPerStatuses = numberPerStatuses;
		this.numberPeraccessModes = numberPeraccessModes;
		this.numberPerAccessTypes = numberPerAccessTypes;
		this.numberPerCategories = numberPerCategories;
		this.numberPerSubcategory = numberPerSubcategory;
	}



	public Long getInfrastructureNumber() {
		return infrastructureNumber;
	}


	public void setInfrastructureNumber(Long infrastructureNumber) {
		this.infrastructureNumber = infrastructureNumber;
	}


	public List<InfrastructureStatusDTO> getNumberPerStatuses() {
		return numberPerStatuses;
	}


	public void setNumberPerStatuses(List<InfrastructureStatusDTO> numberPerStatuses) {
		this.numberPerStatuses = numberPerStatuses;
	}


	public List<InfrastructureAccessModeDTO> getNumberPeraccessModes() {
		return numberPeraccessModes;
	}


	public void setNumberPeraccessModes(List<InfrastructureAccessModeDTO> numberPeraccessModes) {
		this.numberPeraccessModes = numberPeraccessModes;
	}


	public List<InfrastructureAccessTypeDTO> getNumberPerAccessTypes() {
		return numberPerAccessTypes;
	}


	public void setNumberPerAccessTypes(List<InfrastructureAccessTypeDTO> numberPerAccessTypes) {
		this.numberPerAccessTypes = numberPerAccessTypes;
	}


	public List<InfrastructureCategoryDTO> getNumberPerCategories() {
		return numberPerCategories;
	}


	public void setNumberPerCategories(List<InfrastructureCategoryDTO> numberPerCategories) {
		this.numberPerCategories = numberPerCategories;
	}


	public List<RiSubcategoryDTO> getNumberPerSubcategory() {
		return numberPerSubcategory;
	}


	public void setNumberPerSubcategory(List<RiSubcategoryDTO> numberPerSubcategory) {
		this.numberPerSubcategory = numberPerSubcategory;
	}

	
	

}
