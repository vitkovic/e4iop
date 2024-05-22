package e4i.web.rest.dto;

import e4i.domain.RiAccessType;

public class InfrastructureAccessTypeDTO {
	private RiAccessType riAccessType;
	private long riAccessTypeNumber;
	
	public InfrastructureAccessTypeDTO(long riAccessTypeNumber, RiAccessType riAccessType ) {
		super();
		this.riAccessType = riAccessType;
		this.riAccessTypeNumber = riAccessTypeNumber;
	}
	public InfrastructureAccessTypeDTO() {
		super();
	}
	public RiAccessType getRiAccessType() {
		return riAccessType;
	}
	public void setRiAccessType(RiAccessType riAccessType) {
		this.riAccessType = riAccessType;
	}
	public long getRiAccessTypeNumber() {
		return riAccessTypeNumber;
	}
	public void setRiAccessTypeNumber(long riAccessTypeNumber) {
		this.riAccessTypeNumber = riAccessTypeNumber;
	}
	
}
