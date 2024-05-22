package e4i.web.rest.dto;

public class InfrastructureStatisticDTO {
	
	private InfrastructureNumberDTO infrastructureNumberDto;
	private long serviceNumber;
	private long requestForServiceNumber;
	private long requestRespondNUmber;
	private long realizedRequestForServceNUmber;
	
	
	
	public InfrastructureStatisticDTO(InfrastructureNumberDTO infrastructureNumberDto, long serviceNumber,
			long requestForServiceNumber, long requestRespondNUmber, long realizedRequestForServceNUmber) {
		super();
		this.infrastructureNumberDto = infrastructureNumberDto;
		this.serviceNumber = serviceNumber;
		this.requestForServiceNumber = requestForServiceNumber;
		this.requestRespondNUmber = requestRespondNUmber;
		this.realizedRequestForServceNUmber = realizedRequestForServceNUmber;
	}
	public InfrastructureStatisticDTO() {
		super();
	}
	public InfrastructureNumberDTO getInfrastructureNumberDto() {
		return infrastructureNumberDto;
	}
	public void setInfrastructureNumberDto(InfrastructureNumberDTO infrastructureNumberDto) {
		this.infrastructureNumberDto = infrastructureNumberDto;
	}
	public long getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(long serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public long getRequestForServiceNumber() {
		return requestForServiceNumber;
	}
	public void setRequestForServiceNumber(long requestForServiceNumber) {
		this.requestForServiceNumber = requestForServiceNumber;
	}
	public long getRequestRespondNUmber() {
		return requestRespondNUmber;
	}
	public void setRequestRespondNUmber(long requestRespondNUmber) {
		this.requestRespondNUmber = requestRespondNUmber;
	}
	public long getRealizedRequestForServceNUmber() {
		return realizedRequestForServceNUmber;
	}
	public void setRealizedRequestForServceNUmber(long realizedRequestForServceNUmber) {
		this.realizedRequestForServceNUmber = realizedRequestForServceNUmber;
	}
	
	
	

}
