package e4i.web.rest;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e4i.domain.Message;
import e4i.domain.Thread;
import e4i.repository.MessageRepository;
import e4i.repository.ThreadRepository;
import e4i.service.MailService;
import e4i.service.MessageService;
import e4i.web.rest.dto.InquiryDTO;
import e4i.web.rest.dto.NotificationMailDTO;
import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class InquiryResource {
	private final Logger log = LoggerFactory.getLogger(InquiryResource.class);

    private static final String ENTITY_NAME = "thread/message";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
	
	@Autowired
    ThreadRepository threadRepository;

    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    MailService mailService;
    
    
    @PostMapping("/inquiry")
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO inquiryDTO) throws URISyntaxException {
        log.debug("REST request to create new Inqiury");
        
        Thread thread = new Thread();
        thread.setSubject(inquiryDTO.getSubject());
        thread.setCompanySender(inquiryDTO.getCompanySender());
        thread.setCompanyReceiver(inquiryDTO.getCompanyReceiver());
        
        // Proveriti da li je ogals null na bolji nacin
        try {
        	thread.addAdvertisement(inquiryDTO.getAdvertisement());	
        } catch (Exception e) {
		}
        
        Thread newThread = threadRepository.save(thread);
        
        Message message = new Message();
        message.setThread(newThread);
        message.setDatetime(inquiryDTO.getDatetime());
        message.setContent(inquiryDTO.getContent());
        message.setPortalUserSender(inquiryDTO.getPortalUserSender());
        message.setIsRead(false);
        message.setIsDeletedSender(false);
        message.setIsDeletedReceiver(false);
        
        Message newMessage = messageRepository.save(message);
        
        String param = newThread.getId().toString() + "/" + newMessage.getId().toString();
        
        try {
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForNewMessage(newMessage);
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
        } catch (Exception e) {
        	e.printStackTrace();
		}
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, param))
            .body(inquiryDTO);
    }
}
