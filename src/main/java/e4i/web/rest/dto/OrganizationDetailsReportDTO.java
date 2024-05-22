package e4i.web.rest.dto;

public class OrganizationDetailsReportDTO { 
	
	private int infrastructureNumber;// broj infrastruktura
	private int serviceNumber; // broj publikovanih usluga
	private int  requiredServiceNumber; // broj tra\enih usluga 
	private int realizedServiceNumber; // broj realiyovanih usluga
	private int requestedServiceNumber; // broj usluga koje je NIO trazila od drugih
	private int offeredServiceNumber; // broj ponuda koje je nio dobila za svoje zahteve
	private int realizedServiceNumber1; // broj usluga koje NIO ugovorila po svojim zahtevima
	
	
	public OrganizationDetailsReportDTO() {
		super();
	}




	public OrganizationDetailsReportDTO(int infrastructureNumber, int serviceNumber, int requiredServiceNumber,
			int realizedServiceNumber, int requestedServiceNumber, int offeredServiceNumber,
			int realizedServiceNumber1) {
		this.infrastructureNumber = infrastructureNumber;
		this.serviceNumber = serviceNumber;
		this.requiredServiceNumber = requiredServiceNumber;
		this.realizedServiceNumber = realizedServiceNumber;
		this.requestedServiceNumber = requestedServiceNumber;
		this.offeredServiceNumber = offeredServiceNumber;
		this.realizedServiceNumber1 = realizedServiceNumber1;
	}




	public int getInfrastructureNumber() {
		return infrastructureNumber;
	}




	public void setInfrastructureNumber(int infrastructureNumber) {
		this.infrastructureNumber = infrastructureNumber;
	}




	public int getServiceNumber() {
		return serviceNumber;
	}


	public void setServiceNumber(int serviceNumber) {
		this.serviceNumber = serviceNumber;
	}


	public int getRequiredServiceNumber() {
		return requiredServiceNumber;
	}


	public void setRequiredServiceNumber(int requiredServiceNumber) {
		this.requiredServiceNumber = requiredServiceNumber;
	}


	public int getRealizedServiceNumber() {
		return realizedServiceNumber;
	}


	public void setRealizedServiceNumber(int realizedServiceNumber) {
		this.realizedServiceNumber = realizedServiceNumber;
	}


	public int getRequestedServiceNumber() {
		return requestedServiceNumber;
	}


	public void setRequestedServiceNumber(int requestedServiceNumber) {
		this.requestedServiceNumber = requestedServiceNumber;
	}


	public int getOfferedServiceNumber() {
		return offeredServiceNumber;
	}


	public void setOfferedServiceNumber(int offeredServiceNumber) {
		this.offeredServiceNumber = offeredServiceNumber;
	}


	public int getRealizedServiceNumber1() {
		return realizedServiceNumber1;
	}


	public void setRealizedServiceNumber1(int realizedServiceNumber1) {
		this.realizedServiceNumber1 = realizedServiceNumber1;
	}
	
	
	
	    

}
