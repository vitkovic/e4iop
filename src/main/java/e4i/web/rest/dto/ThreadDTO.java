package e4i.web.rest.dto;

import java.time.Instant;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementSupporter;
import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.Thread;

public class ThreadDTO {

	private Long id;
	private String subject;
	private Boolean isFromAdministration;
	private Company companySender;
	private Company companyReceiver;
	private Advertisement advertisement;
	private Collaboration collaboration;
	private Meeting meeting;
	private AdvertisementSupporter advertisementSupporter;
	private Long messageCount;
	private Instant lastMessageDatetime;
	private String lastMessageContent;
	private Boolean unreadExists;
	
	
	public ThreadDTO(
			Long id, String subject, Boolean isFromAdministration, 
			Company companySender, Company companyReceiver, Advertisement advertisement, 
			Collaboration collaboration, Meeting meeting, AdvertisementSupporter advertisementSupporter,
			Long messageCount, Instant lastMessageDatetime, String lastMessageContent, Boolean unreadExists
			) {
		super();
		this.id = id;
		this.subject = subject;
		this.isFromAdministration = isFromAdministration;
		this.companySender = companySender;
		this.companyReceiver = companyReceiver;
		this.advertisement = advertisement;
		this.collaboration = collaboration;
		this.meeting = meeting;
		this.setAdvertisementSupporter(advertisementSupporter);
		this.messageCount = messageCount;
		this.lastMessageDatetime = lastMessageDatetime;
		this.lastMessageContent = lastMessageContent;
		this.unreadExists = unreadExists;
	}
	
	public ThreadDTO() {
		// TODO Auto-generated constructor stub
	}

	public static ThreadDTO convertToDTO(Thread thread) {
		ThreadDTO threadDTO = new ThreadDTO();
		threadDTO.setId(thread.getId());
		threadDTO.setCompanySender(thread.getCompanySender());
		threadDTO.setCompanyReceiver(thread.getCompanyReceiver());
		threadDTO.setSubject(thread.getSubject());
		threadDTO.setIsFromAdministration(thread.isIsFromAdministration());
		
		return threadDTO;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public Boolean getIsFromAdministration() {
		return isFromAdministration;
	}


	public void setIsFromAdministration(Boolean isFromAdministration) {
		this.isFromAdministration = isFromAdministration;
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


	public Advertisement getAdvertisement() {
		return advertisement;
	}


	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}


	public Collaboration getCollaboration() {
		return collaboration;
	}

	public void setCollaboration(Collaboration collaboration) {
		this.collaboration = collaboration;
	}
	
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public AdvertisementSupporter getAdvertisementSupporter() {
		return advertisementSupporter;
	}

	public void setAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
		this.advertisementSupporter = advertisementSupporter;
	}

	public Long getMessageCount() {
		return messageCount;
	}


	public void setMessageCount(Long messageCount) {
		this.messageCount = messageCount;
	}


	public Instant getLastMessageDatetime() {
		return lastMessageDatetime;
	}


	public void setLastMessageDatetime(Instant lastMessageDatetime) {
		this.lastMessageDatetime = lastMessageDatetime;
	}


	public String getLastMessageContent() {
		return lastMessageContent;
	}


	public void setLastMessageContent(String lastMessageContent) {
		this.lastMessageContent = lastMessageContent;
	}

	public Boolean getUnreadExists() {
		return unreadExists;
	}

	public void setUnreadExists(Boolean unreadExists) {
		this.unreadExists = unreadExists;
	}
}
