package e4i.web.rest;

import e4i.domain.Advertisement;
import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.Message;
import e4i.domain.Thread;
import e4i.repository.AdvertisementRepository;
import e4i.repository.CollaborationRepository;
import e4i.repository.CompanyRepository;
import e4i.repository.MessageRepository;
import e4i.repository.ThreadRepository;
import e4i.service.ThreadService;
import e4i.web.rest.dto.ThreadDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link e4i.domain.Thread}.
 */
@RestController
@RequestMapping("/api")
public class ThreadResource {

    private final Logger log = LoggerFactory.getLogger(ThreadResource.class);

    private static final String ENTITY_NAME = "thread";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThreadService threadService;
    
    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AdvertisementRepository advertisementRepository;
    
    @Autowired
    CompanyRepository companyRepository;
    
    @Autowired
    CollaborationRepository collaborationRepository;
    
    public ThreadResource(ThreadService threadService) {
        this.threadService = threadService;
    }

    /**
     * {@code POST  /threads} : Create a new thread.
     *
     * @param thread the thread to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thread, or with status {@code 400 (Bad Request)} if the thread has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/threads")
    public ResponseEntity<Thread> createThread(@Valid @RequestBody Thread thread) throws URISyntaxException {
        log.debug("REST request to save Thread : {}", thread);
        if (thread.getId() != null) {
            throw new BadRequestAlertException("A new thread cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thread result = threadService.save(thread);
        return ResponseEntity.created(new URI("/api/threads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /threads} : Updates an existing thread.
     *
     * @param thread the thread to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thread,
     * or with status {@code 400 (Bad Request)} if the thread is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thread couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/threads")
    public ResponseEntity<Thread> updateThread(@Valid @RequestBody Thread thread) throws URISyntaxException {
        log.debug("REST request to update Thread : {}", thread);
        if (thread.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Thread result = threadService.save(thread);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thread.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /threads} : get all the threads.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of threads in body.
     */
    @GetMapping("/threads")
    public ResponseEntity<List<Thread>> getAllThreads(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Threads");
        Page<Thread> page;
        if (eagerload) {
            page = threadService.findAllWithEagerRelationships(pageable);
        } else {
            page = threadService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /threads/:id} : get the "id" thread.
     *
     * @param id the id of the thread to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thread, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/threads/{id}")
    public ResponseEntity<Thread> getThread(@PathVariable Long id) {
        log.debug("REST request to get Thread : {}", id);
        Optional<Thread> thread = threadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thread);
    }

    /**
     * {@code DELETE  /threads/:id} : delete the "id" thread.
     *
     * @param id the id of the thread to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/threads/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        log.debug("REST request to delete Thread : {}", id);
        threadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/threads/company")
    @Transactional
    public ResponseEntity<List<ThreadDTO>> getAllThreadsForCompany(
    		Pageable pageable, 
    		@RequestParam Long companyId) {
    	log.debug("REST request to get a page of Threads for company: {}", companyId);
    	  	
    	Company company = companyRepository.getOne(companyId);
    	
        Page<Thread> page = threadRepository.findAllByCompany(companyId, false, pageable);
        
        List<ThreadDTO> threaDTOList = page.getContent().stream()
                .map(t -> convertToDTO(t, company))
                .collect(Collectors.toList());
        
        Page<ThreadDTO> pageDTO = new PageImpl<>(threaDTOList, page.getPageable(), page.getTotalElements());
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageDTO);
        return ResponseEntity.ok().headers(headers).body(pageDTO.getContent());
    }
    
    @DeleteMapping("/threads/delete-empty/{id}")
    @Transactional
    public ResponseEntity<Boolean> deleteThreadWithNoMessages(@PathVariable Long id) {
        log.debug("REST request to delete Thread : {}, if there are no messages", id);
        
        Long messageCount = messageRepository.countByThreadId(id);
        
        String responseMessage = "";
        Boolean isDeleted = false;
        if (messageCount == 0) {
            
        	Thread thread = threadRepository.getOne(id);
        	
        	Optional<Advertisement> advertisementOptional = advertisementRepository.findOneByThreads(thread);
        	
        	if (advertisementOptional.isPresent()) {
            	Advertisement advertisement = advertisementOptional.get();        	
            	advertisement.getThreads().remove(thread);
            	thread.getAdvertisements().remove(advertisement);        		
        	}
        	
        	threadService.delete(id);
        		
            responseMessage = ENTITY_NAME + " with id " +  id + " has been deleted.";
            isDeleted = true;
        } else {
        	responseMessage = "There are messages in " + ENTITY_NAME + " " +  id + ".";
        	isDeleted = false;
        }        
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, responseMessage, id.toString())).body(isDeleted);
    }
    
    @PutMapping("/threads/hide-user-messages/{id}/{isDeletedSender}")
    @Transactional
    public ResponseEntity<Void> deleteMessagesInThreadForPortalUser(@PathVariable Long id, @PathVariable Boolean isDeletedSender) {
        log.debug("REST request to hide user messages in Thread : {}", id);
        
        List<Message> messages = messageRepository.findAllByThreadId(id);
        
        for (Message message : messages) {
            if (isDeletedSender) {
            	message.setIsDeletedSender(true);
            } else {
            	message.setIsDeletedReceiver(true);
            }

            Message result = messageRepository.save(message);
            if (result.isIsDeletedSender() == result.isIsDeletedReceiver()) {
            messageRepository.delete(result);	
            }
        }
        
        String responseMessage = "";
        
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, responseMessage, id.toString())).build();
    }
    
    @GetMapping("/threads/company-sender")
    @Transactional
    public ResponseEntity<List<ThreadDTO>> getAllThreadsForCompanySender(
    		Pageable pageable, 
    		@RequestParam Long companyId) {
    	log.debug("REST request to get a page of Threads for companySender: {}", companyId);
    	
    	Company company = companyRepository.getOne(companyId);
    	
        Page<Thread> page = threadRepository.findAllByCompanySender(companyId, false, pageable);
        
        List<ThreadDTO> threaDTOList = page.getContent().stream()
                .map(t -> convertToDTO(t, company))
                .collect(Collectors.toList());
        
        Page<ThreadDTO> pageDTO = new PageImpl<>(threaDTOList, page.getPageable(), page.getTotalElements());
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageDTO);
        return ResponseEntity.ok().headers(headers).body(pageDTO.getContent());
    }
    
    @GetMapping("/threads/company-receiver")
    @Transactional
    public ResponseEntity<List<ThreadDTO>> getAllThreadsForCompanyReceiver(
    		Pageable pageable, 
    		@RequestParam Long companyId) {
    	log.debug("REST request to get a page of Threads for companyReceiver: {}", companyId);
    	
    	Company company = companyRepository.getOne(companyId);
    	
        Page<Thread> page = threadRepository.findAllByCompanyReceiver(companyId, false, pageable);
        
        List<ThreadDTO> threaDTOList = page.getContent().stream()
                .map(t -> convertToDTO(t, company))
                .collect(Collectors.toList());
        
        Page<ThreadDTO> pageDTO = new PageImpl<>(threaDTOList, page.getPageable(), page.getTotalElements());
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pageDTO);
        return ResponseEntity.ok().headers(headers).body(pageDTO.getContent());
    }
    
	public ThreadDTO convertToDTO(Thread thread, Company company) {
		Long id = thread.getId();

		ThreadDTO threadDTO = new ThreadDTO();
		threadDTO.setId(id);
		threadDTO.setCompanySender(thread.getCompanySender());
		threadDTO.setCompanyReceiver(thread.getCompanyReceiver());
		threadDTO.setSubject(thread.getSubject());
		
		Optional<Message> latestMessageOptional = Optional.empty();
		Optional<Message> unreadMessageOptional = Optional.empty();
		if (thread.getCompanySender().getId() == company.getId()) {
			threadDTO.setMessageCount(messageRepository.countByThreadIdAndIsDeletedSender(id, false));
			latestMessageOptional = messageRepository.findFirstByThreadIdAndIsDeletedSenderOrderByDatetimeDesc(id, false);
	        unreadMessageOptional = messageRepository
	        		.findFirstByThreadIdAndPortalUserSenderCompanyIdNotAndIsReadAndIsDeletedSender(id, company.getId(), false, false);
		} else if (thread.getCompanyReceiver().getId() == company.getId()) {
			threadDTO.setMessageCount(messageRepository.countByThreadIdAndIsDeletedReceiver(id, false));
			latestMessageOptional = messageRepository.findFirstByThreadIdAndIsDeletedReceiverOrderByDatetimeDesc(id, false);
	        unreadMessageOptional = messageRepository
	        		.findFirstByThreadIdAndPortalUserSenderCompanyIdNotAndIsReadAndIsDeletedReceiver(id, company.getId(), false, false);
		}
        if (latestMessageOptional.isPresent()) {
            Message latestMessage = latestMessageOptional.get();
            threadDTO.setLastMessageContent(latestMessage.getContent());
            threadDTO.setLastMessageDatetime(latestMessage.getDatetime());
        }
        
        threadDTO.setUnreadExists(unreadMessageOptional.isPresent());
        
        Optional<Advertisement> advertisementOptional = advertisementRepository.findOneByThreads(thread);
        if (advertisementOptional.isPresent()) {
        	Advertisement advertisement = advertisementOptional.get();
        	threadDTO.setAdvertisement(advertisement);        	
        }
        
        Optional<Collaboration> collaborationOptional = collaborationRepository.findOneByThreads(thread);
        if (collaborationOptional.isPresent()) {
        	Collaboration collaboration = collaborationOptional.get();
        	threadDTO.setCollaboration(collaboration);
        	threadDTO.setAdvertisement(collaboration.getAdvertisement());
        }
		
		return threadDTO;
	}

}
