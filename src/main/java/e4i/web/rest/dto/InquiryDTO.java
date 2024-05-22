package e4i.web.rest.dto;

import java.time.Instant;

import e4i.domain.Advertisement;
import e4i.domain.Company;
import e4i.domain.PortalUser;


public class InquiryDTO {

	private Advertisement advertisement;
	private Instant datetime;
	private String subject;
	private String content;
	private Company companySender;
	private Company companyReceiver;
	private PortalUser portalUserSender;
	

	public InquiryDTO(Advertisement advertisement, Instant datetime, String subject, String content,
			Company companySender, Company companyReceiver, PortalUser portalUserSender) {
		super();
		this.advertisement = advertisement;
		this.datetime = datetime;
		this.subject = subject;
		this.content = content;
		this.companySender = companySender;
		this.companyReceiver = companyReceiver;
		this.portalUserSender = portalUserSender;
	}


	public InquiryDTO() {
		// TODO Auto-generated constructor stub
	}


	public Advertisement getAdvertisement() {
		return advertisement;
	}


	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}


	public Instant getDatetime() {
		return datetime;
	}


	public void setDatetime(Instant datetime) {
		this.datetime = datetime;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Company getCompanySender() {
		return companySender;
	}


	public void setCompanySender(Company companySender) {
		this.companySender = companySender;
	}


	public Company getCompanyReceiver() {
		return companyReceiver;
	}


	public void setCompanyReceiver(Company companyReceiver) {
		this.companyReceiver = companyReceiver;
	}


	public PortalUser getPortalUserSender() {
		return portalUserSender;
	}


	public void setPortalUserSender(PortalUser portalUserSender) {
		this.portalUserSender = portalUserSender;
	}
}