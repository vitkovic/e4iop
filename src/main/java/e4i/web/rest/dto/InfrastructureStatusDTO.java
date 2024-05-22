package e4i.web.rest.dto;

import e4i.domain.RiStatus;

public class InfrastructureStatusDTO {
	private RiStatus status;
	private Long statusNumber;
	
	public InfrastructureStatusDTO() {
		super();
	}

	public InfrastructureStatusDTO(Long statusNumber, RiStatus status ) {
		super();
		this.status = status;
		this.statusNumber = statusNumber;
	}

	public RiStatus getStatus() {
		return status;
	}

	public void setStatus(RiStatus status) {
		this.status = status;
	}

	public Long getStatusNumber() {
		return statusNumber;
	}

	public void setStatusNumber(Long statusNumber) {
		this.statusNumber = statusNumber;
	}

	
	
	
}
