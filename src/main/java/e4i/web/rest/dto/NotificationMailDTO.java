package e4i.web.rest.dto;

import java.util.List;

public class NotificationMailDTO {
	
	private String subject;
	private String content;
	private List<String> emails;
	
	
	public NotificationMailDTO(String subject, String content, List<String> emails) {
		super();
		this.subject = subject;
		this.content = content;
		this.emails = emails;
	}

	public NotificationMailDTO() {
		// TODO Auto-generated constructor stub
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

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	
	
	
	
	

}
