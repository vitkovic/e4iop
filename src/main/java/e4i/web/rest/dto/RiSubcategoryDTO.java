package e4i.web.rest.dto;

import e4i.domain.RiSubcategory;

public class RiSubcategoryDTO {

	private RiSubcategory riSubcategory;
	private long riSubcategoryNumber;
	
	public RiSubcategoryDTO(long riSubcategoryNumber, RiSubcategory riSubcategory ) {
		super();
		this.riSubcategory = riSubcategory;
		this.riSubcategoryNumber = riSubcategoryNumber;
	}
	public RiSubcategoryDTO() {
		super();
	}
	public RiSubcategory getRiSubcategory() {
		return riSubcategory;
	}
	public void setRiSubcategory(RiSubcategory riSubcategory) {
		this.riSubcategory = riSubcategory;
	}
	public long getRiSubcategoryNumber() {
		return riSubcategoryNumber;
	}
	public void setRiSubcategoryNumber(long riSubcategoryNumber) {
		this.riSubcategoryNumber = riSubcategoryNumber;
	}
	
	
	
}
