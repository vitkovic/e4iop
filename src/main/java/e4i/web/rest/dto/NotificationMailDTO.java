package e4i.web.rest.dto;

import java.util.List;

public class NotificationMailDTO {
	
	private String subject;
	private String content;
	private List<String> emails;
	private List<AttachmentDTO> attachments;
	
	
	public NotificationMailDTO(String subject, String content, List<String> emails, List<AttachmentDTO> attachments) {
		super();
		this.subject = subject;
		this.content = content;
		this.emails = emails;
		this.attachments = attachments;
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
	
    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }
    
    public void addAttachment(AttachmentDTO attachment) {
    	this.attachments.add(attachment);
    }
}
