package e4i.web.rest.dto;

public class ServiceStatisticResultDTO extends RiServiceDTOSearch {
	
	private int requestForServiceNumber;
	private int realizedRequestsForServiceNumber;
	protected double requestRating;
	
	public ServiceStatisticResultDTO(RiServiceStatisticDTO in,
			int requestForServiceNumber, int realizedRequestsForServiceNumber,
			double requestRating) {
		super(in.getId(), in.getNameSr(), in.getNameEn(), in.getNameSrCyr(), in.getDescriptionSr(), in.getDescriptionEn(), 
				in.getDescriptionSrCyr(), in.getKeywords(), in.getKeywordsEn(),
				in.getKeywordsCyr(), in.getResearchInfrastructure(), in.getAvailability(),
				in.getSubtypes());
		
		this.requestForServiceNumber = requestForServiceNumber;
		this.realizedRequestsForServiceNumber = realizedRequestsForServiceNumber;
		this.requestRating = requestRating;
	}

	public ServiceStatisticResultDTO() {
		super();
	}

	public int getRequestForServiceNumber() {
		return requestForServiceNumber;
	}

	public void setRequestForServiceNumber(int requestForServiceNumber) {
		this.requestForServiceNumber = requestForServiceNumber;
	}

	public int getRealizedRequestsForServiceNumber() {
		return realizedRequestsForServiceNumber;
	}

	public void setRealizedRequestsForServiceNumber(int realizedRequestsForServiceNumber) {
		this.realizedRequestsForServiceNumber = realizedRequestsForServiceNumber;
	}

	public double getRequestRating() {
		return requestRating;
	}

	public void setRequestRating(double requestRating) {
		this.requestRating = requestRating;
	}


	
	
	

}
