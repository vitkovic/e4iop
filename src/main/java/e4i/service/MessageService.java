package e4i.service;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementStatus;
import e4i.domain.AdvertisementSupporter;
import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.Message;
import e4i.domain.PortalUser;
import e4i.domain.Thread;
import e4i.repository.MessageRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.ThreadRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link Message}.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;
    
    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    ThreadRepository threadRepository;
    
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Save a message.
     *
     * @param message the entity to save.
     * @return the persisted entity.
     */
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        return messageRepository.save(message);
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Message> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messageRepository.findAll(pageable);
    }


    /**
     * Get one message by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Message> findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findById(id);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.deleteById(id);
    }
    
    
    /**
     * To delete older messages - 180 days
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
    	
    	LocalDate date = LocalDate.now().minusDays(180);
    	
        messageRepository
            .findAllByAfterDays(date.toString())
            .forEach(msg -> {
                log.debug("Deleting messages {}", msg);
                if (msg instanceof Message) {
                	this.delete(((Message)msg).getId());
                }
         
            });
    }			
    
    
    
    
    
    @Transactional
    public Message createNewMessageInThread(Thread thread, String content, PortalUser portalUserSender, Long companyId) {
    	log.debug("Request to create new message in Thread {} from PortalUser {}", thread.getId(), portalUserSender.getId());
    	  
        Message message = new Message();        
        message.setThread(thread);
        message.setPortalUserSender(portalUserSender);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsDeletedSender(false);
        message.setIsDeletedReceiver(false);
        
        if (thread.getCompanyReceiver() != null && thread.getCompanyReceiver().getId().equals(companyId)) {
        	message.setIsReadReceiver(true);
            message.setIsReadSender(false);
        } else if (thread.getCompanySender() != null && thread.getCompanySender().getId().equals(companyId)) {
        	message.setIsReadReceiver(false);
            message.setIsReadSender(true);
        } else {
        	message.setIsReadReceiver(false);
            message.setIsReadSender(false);
        }
        
        Message result = this.save(message);
        
        return result;
    }
        
    @Deprecated
    @Transactional
    public Message createFirstMessageInThreadCollaboration(Thread thread, Collaboration collaboration, PortalUser portalUser) {
    	
    	String content = "Kompanija '" + thread.getCompanySender().getName() + "' "
    			+ "Vam je u uputila zahtev za saradnju za oglas '" + collaboration.getAdvertisement().getTitle() + "'. "
    			+ "Saradnju možete potvrditi klikom na opciju 'Potvrdi saradnju' u zaglavlju ove poruke.";
//    			+ "Ovo je automatski generisana poruka. "
//    			+ "Odgovor na ovu poruku biće poslat kompaniji koja je uputila zahtev za saradnju.";
    	
    	Message message = new Message();
        message.setThread(thread);
        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(false);
        message.setIsDeletedSender(false);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
    }
    
    @Deprecated
    @Transactional
    public Message createConfirmMessageInThreadCollaboration(Thread thread, Collaboration collaboration, PortalUser portalUser) {
    	
    	String content = "Kompanija '" + thread.getCompanyReceiver().getName() + "' "
    			+ "je u potvrdila zahtev za saradnju za oglas '" + collaboration.getAdvertisement().getTitle() + "'. ";
    	
    	Message message = new Message();
        message.setThread(thread);
        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(false);
        message.setIsDeletedSender(false);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
    }
    
    @Deprecated
    @Transactional
    public Message createCancelMessageInThreadCollaboration(Thread thread, Collaboration collaboration, PortalUser portalUser) {
    	
    	String content = "Kompanija '" + thread.getCompanyReceiver().getName() + "' "
    			+ "je u odbila zahtev za saradnju za oglas '" + collaboration.getAdvertisement().getTitle() + "'. ";
    	
    	Message message = new Message();
        message.setThread(thread);
        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(false);
        message.setIsDeletedSender(false);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
    }
    
    @Transactional
    public PortalUser findPortalUserSenderByMessage(Message message) {
    	Optional<PortalUser> portalUserOptional = messageRepository.findPortalUserSenderByMessageId(message.getId());
    	if (portalUserOptional.isEmpty()) {
    		String errorMessage = String.format("PortalUser sender for Message with id={} could not be found.", message.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    	
    	PortalUser portalUser = portalUserOptional.get();
    	
    	return portalUser;
    }
    
    @Transactional
    public Thread findThreadByMessage(Message message) {
    	Optional<Thread> threadOptional = messageRepository.findThreadByMessageId(message.getId());
    	if (threadOptional.isEmpty()) {
    		String errorMessage = String.format("Thread for Message with id={} could not be found.", message.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    	
    	Thread thread = threadOptional.get();
    	
    	return thread;
    }
    
    @Transactional
	public Message createFirstMessageInThreadMeeting(Thread thread, Meeting meeting, Company companyOrganizer) {
//    	String companyText = "Kompanija: " + companyOrganizer.getName() + "\n";    	
//    	String titleText = "Sastanak: " + meeting.getTitle() + "\n";
//    	String advertisementText = (meeting.getAdvertisement() != null) ? 
//    		    "Oglas: " + meeting.getAdvertisement().getTitle() + "\n" : "\n";
    	String infoText = "\nImate poziv za novi sastanak na B2B portalu.\n"
    			+ "\nPoziv na sastanak možete potvrditi ili otkazati klikom na odgovorajuće opcije u ovoj poruci ili sa stranice 'Kalendar'."
    			+ "\n Ostale detalje sastanka možete pogledati sa stranice 'Kalendar'.";
    	
    	String content = infoText;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}

    @Transactional
	public Message createFirstMessageInThreadAcceptanceMeeting(Thread thread, Meeting meeting, Company company) {
    	String infoText = "\nKompanija " + company.getName() + " je prihvatila poziv za sastanak na B2B portalu.\n"
    			+ "\nDetalje svih zakazanih sastanaka možete pogledati sa stranice 'Kalendar'.";
    	
    	String content = infoText;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}
    
    @Transactional
	public Message createFirstMessageInThreadRejectionMeeting(
			Thread thread, 
			Meeting meeting,
			Company company, 
			String comment
			) {
    	String infoText = "\nKompanija " + company.getName() + " je odbila poziv za sastanak na B2B portalu.\n"
    			+ "\nDetalje svih zakazanih sastanaka možete pogledati sa stranice 'Kalendar'.";
    	
    	String content = infoText + "\n\n" + comment;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}

    @Transactional
	public Message createFirstMessageInThreadSupporter(Thread thread, AdvertisementSupporter advertisementSupporter) {
    	String infoText = "\nImate poziv za zajedničko oglašavanje na B2B portalu.\n"
    			+ "\nPoziv možete potvrditi klikom na odgovorajuću opciju u ovoj poruci.\n\n";
    	
    	String content = infoText;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}

    @Transactional
	public Message createFirstMessageInThreadAcceptanceSupporter(Thread thread,
			AdvertisementSupporter advertisementSupporter) {
    	String infoText = "\nKompanija " + advertisementSupporter.getCompany().getName() 
    			+ " je prihvatila poziv za zajedničko oglašavanje na B2B portalu.\n";
    	
    	String content = infoText;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}

    @Transactional
	public Message createFirstMessageInThreadRejectionSupporter(Thread thread,
			AdvertisementSupporter advertisementSupporter) {
    	String infoText = "\nKompanija " + advertisementSupporter.getCompany().getName() 
    			+ " je odbila poziv za zajedničko oglašavanje na B2B portalu.\n";
    	
    	String content = infoText;
    	
    	Message message = new Message();
        message.setThread(thread);
//        message.setPortalUserSender(portalUser);
        message.setContent(content);
        message.setDatetime(Instant.now());
        message.setIsReadReceiver(false);
        message.setIsReadSender(true);
        message.setIsDeletedSender(true);
        message.setIsDeletedReceiver(false);
    	
    	Message result = this.save(message);
    	
    	return result;
	}
}
