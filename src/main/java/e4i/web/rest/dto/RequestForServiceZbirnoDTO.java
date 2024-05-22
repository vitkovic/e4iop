package e4i.web.rest.dto;

import e4i.domain.RequestForService;
import e4i.domain.RequestRespond;
import e4i.domain.ServiceOfferRespond;

public class RequestForServiceZbirnoDTO {

	private RequestForService requestForService;
	private RequestRespond requestRespond;
	private ServiceOfferRespond serviceOfferRespond;
	
	
	
	
	public RequestForServiceZbirnoDTO() {
		super();
	}
	public RequestForServiceZbirnoDTO(RequestForService requestForService, RequestRespond requestRespond,
			ServiceOfferRespond serviceOfferRespond) {
		super();
		this.requestForService = requestForService;
		this.requestRespond = requestRespond;
		this.serviceOfferRespond = serviceOfferRespond;
	}
	public RequestForService getRequestForService() {
		return requestForService;
	}
	public void setRequestForService(RequestForService requestForService) {
		this.requestForService = requestForService;
	}
	public RequestRespond getRequestRespond() {
		return requestRespond;
	}
	public void setRequestRespond(RequestRespond requestRespond) {
		this.requestRespond = requestRespond;
	}
	public ServiceOfferRespond getServiceOfferRespond() {
		return serviceOfferRespond;
	}
	public void setServiceOfferRespond(ServiceOfferRespond serviceOfferRespond) {
		this.serviceOfferRespond = serviceOfferRespond;
	}
	
	
}
