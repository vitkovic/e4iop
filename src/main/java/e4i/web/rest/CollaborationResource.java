package e4i.web.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementStatus;
import e4i.domain.Collaboration;
import e4i.domain.CollaborationRating;
import e4i.domain.Message;
import e4i.domain.PortalUser;
import e4i.domain.Thread;
import e4i.repository.CollaborationRepository;
import e4i.service.AdvertisementService;
import e4i.service.CollaborationRatingService;
import e4i.service.CollaborationService;
import e4i.service.MailService;
import e4i.service.MessageService;
import e4i.service.PortalUserService;
import e4i.service.ThreadService;
import e4i.web.rest.dto.NotificationMailDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.Collaboration}.
 */
@RestController
@RequestMapping("/api")
public class CollaborationResource {

    private final Logger log = LoggerFactory.getLogger(CollaborationResource.class);

    private static final String ENTITY_NAME = "collaboration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

//    @Autowired
    private final CollaborationService collaborationService;
    
    @Autowired
    CollaborationRepository collaborationRepository;
    
    @Autowired
    PortalUserService portalUserService;

    @Autowired
    AdvertisementService advertisementService;
    
    @Autowired
    ThreadService threadService;
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    CollaborationRatingService collaborationRatingService;
    
//    @Autowired
    private final MailService mailService;
     
    public CollaborationResource(MailService mailService, CollaborationService collaborationService) {
		this.collaborationService = collaborationService;
		this.mailService = mailService;
    }

    /**
     * {@code POST  /collaborations} : Create a new collaboration.
     *
     * @param collaboration the collaboration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaboration, or with status {@code 400 (Bad Request)} if the collaboration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaborations")
    public ResponseEntity<Collaboration> createCollaboration(@Valid @RequestBody Collaboration collaboration) throws URISyntaxException {
        log.debug("REST request to save Collaboration : {}", collaboration);
        if (collaboration.getId() != null) {
            throw new BadRequestAlertException("A new collaboration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collaboration result = collaborationService.save(collaboration);
        return ResponseEntity.created(new URI("/api/collaborations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collaborations} : Updates an existing collaboration.
     *
     * @param collaboration the collaboration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaboration,
     * or with status {@code 400 (Bad Request)} if the collaboration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaboration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaborations")
    public ResponseEntity<Collaboration> updateCollaboration(@Valid @RequestBody Collaboration collaboration) throws URISyntaxException {
        log.debug("REST request to update Collaboration : {}", collaboration);
        if (collaboration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Collaboration result = collaborationService.save(collaboration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collaborations} : get all the collaborations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborations in body.
     */
    @GetMapping("/collaborations")
    public ResponseEntity<List<Collaboration>> getAllCollaborations(Pageable pageable) {
        log.debug("REST request to get a page of Collaborations");
        Page<Collaboration> page = collaborationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collaborations/:id} : get the "id" collaboration.
     *
     * @param id the id of the collaboration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaboration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaborations/{id}")
    public ResponseEntity<Collaboration> getCollaboration(@PathVariable Long id) {
        log.debug("REST request to get Collaboration : {}", id);
        Optional<Collaboration> collaboration = collaborationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaboration);
    }

    /**
     * {@code DELETE  /collaborations/:id} : delete the "id" collaboration.
     *
     * @param id the id of the collaboration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaborations/{id}")
    public ResponseEntity<Void> deleteCollaboration(@PathVariable Long id) {
        log.debug("REST request to delete Collaboration : {}", id);
        collaborationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/collaborations/company")
    public ResponseEntity<List<Collaboration>> getAllCollaborationsForCompany(
    		Pageable pageable,
    		@RequestParam Long companyId) {
        log.debug("REST request to get a page of Collaborations for company");

        Page<Collaboration> page = collaborationService.findAllAcceptedCollaborationsForCompany(companyId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/collaborations/company-offer")
    public ResponseEntity<List<Collaboration>> getAllCollaborationsForCompanyOffer(
    		Pageable pageable,
    		@RequestParam Long companyId) {
        log.debug("REST request to get a page of Collaborations for company offer");
        
        Page<Collaboration> page = collaborationService.findAllAcceptedCollaborationsForCompanyOffer(companyId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/collaborations/company-request")
    public ResponseEntity<List<Collaboration>> getAllCollaborationsForCompanyRequest(
    		Pageable pageable,
    		@RequestParam Long companyId) {
        log.debug("REST request to get a page of Collaborations for company request");

        Page<Collaboration> page = collaborationService.findAllAcceptedCollaborationsForCompanyRequest(companyId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @PostMapping("/collaborations/request/{advertisementId}")
    public ResponseEntity<Collaboration> createCollaborationForAdvertisement(@PathVariable Long advertisementId) {
        log.debug("REST request to create Collaboration for advertisement : {}", advertisementId);
        
        try {
        	PortalUser portalUser = portalUserService.findCurrentPortalUser();
            Advertisement advertisement = advertisementService.findOneByIdFromOptional(advertisementId);
        	Collaboration collaboration = collaborationService.createCollaborationForAdvertisementAndPortalUserCompany(advertisement, portalUser);
        	Thread thread = threadService.createThreadForCollaboration(collaboration);
        	Message message = messageService.createFirstMessageInThreadCollaboration(thread, collaboration, portalUser);
        	
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationRequest(message, collaboration);
        	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
            
        	return ResponseEntity.created(new URI("/api/collaborations/advertisement/" + advertisementId))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
                    .body(collaboration);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
    
    @PutMapping("/collaborations/confirm/{collaborationId}")
    public ResponseEntity<Collaboration> confirmCollaborationForAdvertisement(@PathVariable Long collaborationId) {
        log.debug("REST request to confirm Collaboration: {}", collaborationId);
        
        try {
        	// confirm collaboration
        	Collaboration collaboration = collaborationService.confirmCollaboration(collaborationId);

        	// make ad inactive
        	Advertisement advertisement = advertisementService.findOneByCollaboration(collaboration);
        	advertisement = advertisementService.changeStatus(advertisement, AdvertisementStatus.INACTIVE);
        	
        	// send message in thread
        	PortalUser portalUser = portalUserService.findCurrentPortalUser();
            Thread thread = threadService.getThreadForCollaboration(collaboration);
        	Message message = messageService.createConfirmMessageInThreadCollaboration(thread, collaboration, portalUser);

        	// send email notification
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationConfirm(message, collaboration);
        	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}

        	return ResponseEntity.created(new URI("/api/collaborations/advertisement/" + collaborationId))
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
                    .body(collaboration);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
    
    @PutMapping("/collaborations/cancel/{collaborationId}")
    public ResponseEntity<Collaboration> cancelCollaborationForAdvertisement(@PathVariable Long collaborationId) {
        log.debug("REST request to confirm Collaboration: {}", collaborationId);
        
        try {
        	// cancel collaboration
        	Collaboration collaboration = collaborationService.cancelCollaboration(collaborationId);
       	
        	// send message in thread
        	PortalUser portalUser = portalUserService.findCurrentPortalUser();
            Thread thread = threadService.getThreadForCollaboration(collaboration);
        	Message message = messageService.createCancelMessageInThreadCollaboration(thread, collaboration, portalUser);

        	// send email notification
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationCancel(message, collaboration);
        	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}

        	return ResponseEntity.created(new URI("/api/collaborations/advertisement/" + collaborationId))
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
                    .body(collaboration);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
    
    @GetMapping("/collaborations/count-pending-for-advertisement/{advertisementId}")
    public ResponseEntity<Long> countPendingCollaborationsForAdvertisement(@PathVariable Long advertisementId) {
        log.debug("REST request to count pending Collaborations for Advertisement: {}", advertisementId);
                
        try {
        	Long count = collaborationService.countPendingCollaborationsForAdvertisement(advertisementId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/collaborations/cancel-pending-for-advertisement/{advertisementId}")
    public ResponseEntity<List<Collaboration>> cancelPendingCollaborationsForAdvertisement(@PathVariable Long advertisementId) {
        log.debug("REST request to cancel pending Collaborations for Advertisement: {}", advertisementId);
        
        try {
        	List<Collaboration> collaborations = collaborationService.findAllPendingCollaborationsForAdvertisement(advertisementId);

        	
            for (Collaboration collaboration : collaborations) {
            	// cancel collaboration
            	collaborationService.cancelCollaboration(collaboration.getId());
            	
            	// send message in thread
            	PortalUser portalUser = portalUserService.findCurrentPortalUser();
                Thread thread = threadService.getThreadForCollaboration(collaboration);
            	Message message = messageService.createCancelMessageInThreadCollaboration(thread, collaboration, portalUser);

            	// send email notification
            	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationCancel(message, collaboration);
            	
            	if (!mailDTO.getEmails().isEmpty()) {
            		mailService.sendNotificationMail(mailDTO);
            	}
            }
        	
        	return ResponseEntity.created(new URI("/api/collaborations/cancel-pending-for-advertisement/" + advertisementId))
//                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementId.toString())
                    .body(collaborations);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
    
    @PutMapping("/collaborations/rate-offer")
    public ResponseEntity<Collaboration> rateCollaborationForCompanyOffer(
    		@RequestParam Long collaborationId,
    		@RequestParam Long ratingId,
    		@RequestParam String comment
    ) throws URISyntaxException  {
        log.debug("REST request to rate copmanyOffer for Collaboration: {} with rating: {}", collaborationId, ratingId);
        
        try {
        	CollaborationRating rating = collaborationRatingService.findOneById(ratingId);
        	Collaboration collaboration = collaborationService.updateCollaborationRatingForCompanyOffer(collaborationId, rating, comment);
        	
        	// Ocenu je dao oglasivac, pa se mail salje traziocu
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationRatingCompanyRequest(collaboration);
        	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}

        	return ResponseEntity.created(new URI("/api/collaborations/rate-offer"))
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
                    .body(collaboration);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
    
    @PutMapping("/collaborations/rate-request")
    public ResponseEntity<Collaboration> rateCollaborationForCompanyRequest(
    		@RequestParam Long collaborationId,
    		@RequestParam Long ratingId,
    		@RequestParam String comment
	) throws URISyntaxException  {
        log.debug("REST request to rate copmanyRequest for Collaboration: {} with rating: {}", collaborationId, ratingId);
        
        try {
        	CollaborationRating rating = collaborationRatingService.findOneById(ratingId);
        	Collaboration collaboration = collaborationService.updateCollaborationRatingForCompanyRequest(collaborationId, rating, comment);
        	
        	// Ocenu je dao trazilac, pa se mail salje oglasivacu
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForCollaborationRatingCompanyOffer(collaboration);
         	
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}

        	return ResponseEntity.created(new URI("/api/collaborations/rate-request"))
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaboration.getId().toString()))
                    .body(collaboration);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.noContent().build();
        }    
    }
}
