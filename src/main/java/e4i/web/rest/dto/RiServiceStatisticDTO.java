package e4i.web.rest.dto;

import java.time.LocalDate;

import javax.persistence.ColumnResult;

public class RiServiceStatisticDTO extends RiServiceDTOSearch {
	
	protected String requestRespondAnswer;
	protected String serviceOfferRespondAnswer;
	protected Long requestId;
	protected LocalDate requestDate;
	protected Integer requestRating;
	
	public RiServiceStatisticDTO(Long id, String nameSr, String nameEn, String nameSrCyr, String descriptionSr,
			String descriptionEn, String descriptionSrCyr, 
			Long infrastructureId, String infrastructureNameSr, String infrastructureNameEn,
			String infrastructureNameSrCyr, Long availabilityId, String availability, String availabilityEn,
			String availabilityCyr, Long subtypeId, String subtype, String subtypeEn, String subtypeCyr,
			String requestRespondAnswer, String serviceOfferRespondAnswer, Long requestId, LocalDate requestDate,
			Integer requestRating) 
	{
		super(id, nameSr, nameEn, nameSrCyr, descriptionSr, descriptionEn, descriptionSrCyr, null, null,
				null, infrastructureId, infrastructureNameSr, infrastructureNameEn, infrastructureNameSrCyr,
				availabilityId, availability, availabilityEn, availabilityCyr, subtypeId, subtype, subtypeEn,
				subtypeCyr);
		this.requestRespondAnswer = requestRespondAnswer;
		this.serviceOfferRespondAnswer = serviceOfferRespondAnswer;
		this.requestId = requestId;
		this.requestDate = requestDate;
		this.requestRating = requestRating;
	}

	public RiServiceStatisticDTO() {
		super();
	}

	public String getRequestRespondAnswer() {
		return requestRespondAnswer;
	}

	public void setRequestRespondAnswer(String requestRespondAnswer) {
		this.requestRespondAnswer = requestRespondAnswer;
	}

	public String getServiceOfferRespondAnswer() {
		return serviceOfferRespondAnswer;
	}

	public void setServiceOfferRespondAnswer(String serviceOfferRespondAnswer) {
		this.serviceOfferRespondAnswer = serviceOfferRespondAnswer;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public Integer getRequestRating() {
		return requestRating;
	}

	public void setRequestRating(Integer requestRating) {
		this.requestRating = requestRating;
	}
	
	
	
	

}
