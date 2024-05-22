package e4i.web.rest.dto;

import e4i.domain.RiAccessMode;

public class InfrastructureAccessModeDTO {
	private RiAccessMode riAccessMode;
	private long riAccessModeNUmber;
	
	public InfrastructureAccessModeDTO(long riAccessModeNUmber, RiAccessMode riAccessMode) {
		super();
		this.riAccessMode = riAccessMode;
		this.riAccessModeNUmber = riAccessModeNUmber;
	}

	public InfrastructureAccessModeDTO() {
		super();
	}

	public RiAccessMode getRiAccessMode() {
		return riAccessMode;
	}

	public void setRiAccessMode(RiAccessMode riAccessMode) {
		this.riAccessMode = riAccessMode;
	}

	public long getRiAccessModeNUmber() {
		return riAccessModeNUmber;
	}

	public void setRiAccessModeNUmber(long riAccessModeNUmber) {
		this.riAccessModeNUmber = riAccessModeNUmber;
	}

	
	
	
}
