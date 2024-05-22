package e4i.web.rest.dto;

import e4i.domain.RiCategory;

public class InfrastructureCategoryDTO {
	
	private RiCategory riCategoryDto;
	private long riCategoryNumber;
	
	public RiCategory getRiCategoryDto() {
		return riCategoryDto;
	}

	public void setRiCategoryDto(RiCategory riCategoryDto) {
		this.riCategoryDto = riCategoryDto;
	}

	

	public long getRiCategoryNumber() {
		return riCategoryNumber;
	}

	public void setRiCategoryNumber(long riCategoryNumber) {
		this.riCategoryNumber = riCategoryNumber;
	}

	public InfrastructureCategoryDTO(long riCategoryNumber, RiCategory riCategoryDto ) {
		super();
		this.riCategoryDto = riCategoryDto;
		this.riCategoryNumber = riCategoryNumber;
	}
	
	
	
}
