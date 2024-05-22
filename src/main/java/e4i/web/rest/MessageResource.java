package e4i.web.rest;

import e4i.domain.Message;
import e4i.domain.User;
import e4i.domain.PortalUser;
import e4i.domain.Thread;
import e4i.repository.MessageRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.ThreadRepository;
import e4i.service.MailService;
import e4i.service.MessageService;
import e4i.service.UserService;
import e4i.web.rest.dto.NotificationMailDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.Message}.
 */
@RestController
@RequestMapping("/api")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    private static final String ENTITY_NAME = "message";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

//    @Autowired
    private final MessageService messageService;
    
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    ThreadRepository threadRepository;
    
    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    UserService userService;
    
//    @Autowired
    private final MailService mailService;

    public MessageResource(MessageService messageService, MailService mailService) {
		this.messageService = messageService;
//        this.messageService = messageService;
		this.mailService = mailService;
    }

    /**
     * {@code POST  /messages} : Create a new message.
     *
     * @param message the message to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new message, or with status {@code 400 (Bad Request)} if the message has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) throws URISyntaxException {
        log.debug("REST request to save Message : {}", message);
        if (message.getId() != null) {
            throw new BadRequestAlertException("A new message cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Message result = messageService.save(message);
        return ResponseEntity.created(new URI("/api/messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /messages} : Updates an existing message.
     *
     * @param message the message to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated message,
     * or with status {@code 400 (Bad Request)} if the message is not valid,
     * or with status {@code 500 (Internal Server Error)} if the message couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/messages")
    public ResponseEntity<Message> updateMessage(@Valid @RequestBody Message message) throws URISyntaxException {
        log.debug("REST request to update Message : {}", message);
        if (message.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Message result = messageService.save(message);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, message.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /messages} : get all the messages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messages in body.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(Pageable pageable) {
        log.debug("REST request to get a page of Messages");
        Page<Message> page = messageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /messages/:id} : get the "id" message.
     *
     * @param id the id of the message to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the message, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        log.debug("REST request to get Message : {}", id);
        Optional<Message> message = messageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(message);
    }

    /**
     * {@code DELETE  /messages/:id} : delete the "id" message.
     *
     * @param id the id of the message to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        log.debug("REST request to delete Message : {}", id);
        messageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/messages/thread/{threadId}")
    @Transactional
    public ResponseEntity<List<Message>> getAllMessagesByThread(@PathVariable Long threadId) {
        log.debug("REST request to get a all of Messages for threadId {}", threadId);
        
        Optional<User> currentUserOptional = userService.getUserWithAuthorities();
        User currentUser = currentUserOptional.get();
        PortalUser currentPortalUser = portalUserRepository.findByUserId(currentUser.getId());
        Thread thread = threadRepository.getOne(threadId);
        
        List<Message> messages = new ArrayList<>();
        if (thread.getCompanyReceiver().getId() == currentPortalUser.getCompany().getId()) {
        	messages = messageRepository.findAllByThreadIdAndIsDeletedReceiverOrderByDatetimeAsc(threadId, false);
        } else if (thread.getCompanySender().getId() == currentPortalUser.getCompany().getId()) {
        	messages = messageRepository.findAllByThreadIdAndIsDeletedSenderOrderByDatetimeAsc(threadId, false);	
        }
        
        for (Message message : messages) {
        	if ((message.getPortalUserSender().getId() != currentPortalUser.getId()) && !message.isIsRead()) {
        		message.setIsRead(true);
        		messageRepository.save(message);
        	}
        }

//        List<Message> messages = messageRepository.findAllByThreadIdOrderByDatetimeDesc(threadId);
        
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(messages);
    }
    
    @GetMapping("/messages/thread/count/{threadId}")
    public ResponseEntity<Long> getCountByThread(@PathVariable Long threadId) {
        log.debug("REST request to get a all of Messages for threadId {}", threadId);
                
        Long count = messageRepository.countByThreadId(threadId);
        
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(count);
    }
    
    @PostMapping("/messages/thread")
    public ResponseEntity<Message> createMessageInThread(
		@RequestParam String content, 
		@RequestParam String senderId, 
		@RequestParam("threadId") Long threadId
    ) throws URISyntaxException {
        log.debug("REST request to create Message in thread: {}",  threadId);
          
        try {
            Message message = messageService.createNewMessageInThread(content, threadId, senderId);
        	
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForNewMessage(message);
        	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
            return ResponseEntity.created(new URI("/api/messages/" + message.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, message.getId().toString()))
                    .body(message);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
		}
        

    }
}
