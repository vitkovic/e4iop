package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementSupporter;
import e4i.domain.Company;
import e4i.domain.Message;
import e4i.domain.Thread;
import e4i.service.AdvertisementService;
import e4i.service.AdvertisementSupporterService;
import e4i.service.CompanyService;
import e4i.service.MailService;
import e4i.service.MessageService;
import e4i.service.ThreadService;
import e4i.web.rest.dto.NotificationMailDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementSupporter}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementSupporterResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSupporterResource.class);

    private static final String ENTITY_NAME = "advertisementSupporter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    AdvertisementService AdvertisementService;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    ThreadService threadService;
    
    @Autowired
    MessageService messageService;
    
    private final MailService mailService;

    private final AdvertisementSupporterService advertisementSupporterService;

    public AdvertisementSupporterResource(
    		AdvertisementSupporterService advertisementSupporterService,
    		MailService mailService) {
        this.advertisementSupporterService = advertisementSupporterService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /advertisement-supporters} : Create a new advertisementSupporter.
     *
     * @param advertisementSupporter the advertisementSupporter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementSupporter, or with status {@code 400 (Bad Request)} if the advertisementSupporter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-supporters")
    public ResponseEntity<AdvertisementSupporter> createAdvertisementSupporter(@RequestBody AdvertisementSupporter advertisementSupporter) throws URISyntaxException {
        log.debug("REST request to save AdvertisementSupporter : {}", advertisementSupporter);
        if (advertisementSupporter.getId() != null) {
            throw new BadRequestAlertException("A new advertisementSupporter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementSupporter result = advertisementSupporterService.save(advertisementSupporter);
        return ResponseEntity.created(new URI("/api/advertisement-supporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-supporters} : Updates an existing advertisementSupporter.
     *
     * @param advertisementSupporter the advertisementSupporter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementSupporter,
     * or with status {@code 400 (Bad Request)} if the advertisementSupporter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementSupporter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-supporters")
    public ResponseEntity<AdvertisementSupporter> updateAdvertisementSupporter(@RequestBody AdvertisementSupporter advertisementSupporter) throws URISyntaxException {
        log.debug("REST request to update AdvertisementSupporter : {}", advertisementSupporter);
        if (advertisementSupporter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementSupporter result = advertisementSupporterService.save(advertisementSupporter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementSupporter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-supporters} : get all the advertisementSupporters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementSupporters in body.
     */
    @GetMapping("/advertisement-supporters")
    public List<AdvertisementSupporter> getAllAdvertisementSupporters() {
        log.debug("REST request to get all AdvertisementSupporters");
        return advertisementSupporterService.findAll();
    }

    /**
     * {@code GET  /advertisement-supporters/:id} : get the "id" advertisementSupporter.
     *
     * @param id the id of the advertisementSupporter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementSupporter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-supporters/{id}")
    public ResponseEntity<AdvertisementSupporter> getAdvertisementSupporter(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementSupporter : {}", id);
        Optional<AdvertisementSupporter> advertisementSupporter = advertisementSupporterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementSupporter);
    }

    /**
     * {@code DELETE  /advertisement-supporters/:id} : delete the "id" advertisementSupporter.
     *
     * @param id the id of the advertisementSupporter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-supporters/{id}")
    public ResponseEntity<Void> deleteAdvertisementSupporter(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementSupporter : {}", id);
        advertisementSupporterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/advertisement-supporters/manage")
    public ResponseEntity<Void> manageAdvertisementSupporters(
    		@RequestParam Long advertisementId,
    		@RequestParam List<Long> companyIdsToAdd,
    		@RequestParam List<Long> companyIdsToRemove
    		) throws URISyntaxException {
        log.debug("REST request to add or remove  AdvertisementSupporters for AdvertisementId : {}", advertisementId);

        
        Optional<Advertisement> advertisementOptional = AdvertisementService.findOne(advertisementId);
        
        if (advertisementOptional.isEmpty()) {	
        	return ResponseEntity.badRequest().build();
        }
        
        Advertisement advertisement = advertisementOptional.get();
        
        for (Long companyId : companyIdsToAdd) {
        	Optional<Company> companyOptional = companyService.findOne(companyId);
        	
        	if (companyOptional.isPresent()) {
        		Company company = companyOptional.get();
        		AdvertisementSupporter advertisementSupporter =  advertisementSupporterService.addCompanySupporter(advertisement, company);
        		
            	Thread thread = threadService.createThreadForNewSupporter(advertisementSupporter);
            	Message message = messageService.createFirstMessageInThreadSupporter(thread, advertisementSupporter);
            
            	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForSupporterInvitation(
            			advertisementSupporter);
            	if (!mailDTO.getEmails().isEmpty()) {
            		mailService.sendNotificationMail(mailDTO);
            	}  
        	}
        }
        
        for (Long companyId : companyIdsToRemove) {
        	Optional<Company> companyOptional = companyService.findOne(companyId);
        	
        	if (companyOptional.isPresent()) {
        		Company company = companyOptional.get();
        		advertisementSupporterService.removeCompanySupporter(advertisement, company);
        	}
        }
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/advertisement-supporters/retrieve/{advertisementId}")
    public ResponseEntity<List<AdvertisementSupporter>> retrieveAdvertisementSupporters(@PathVariable Long advertisementId) throws URISyntaxException {
        log.debug("REST request to retrieve AdvertisementSupporters for AdvertisementId : {}", advertisementId);
        
        Optional<Advertisement> advertisementOptional = AdvertisementService.findOne(advertisementId);
        
        if (advertisementOptional.isEmpty()) {	
        	return ResponseEntity.badRequest().build();
        }
        
        Advertisement advertisement = advertisementOptional.get();
        
        List<AdvertisementSupporter> supporters = advertisementSupporterService.findAllByAdvertisementId(advertisement);
        
        return ResponseEntity.ok(supporters);
    }
    
    @PutMapping("/advertisement-supporters/accept/{advertisementId}/{companyId}")
    public ResponseEntity<?> acceptSupportForCompany(@PathVariable Long advertisementId, @PathVariable Long companyId) {
        log.debug("REST request to accept AdvertisementSupporter for Advertisement {} and Company {}", advertisementId, companyId);
        
        Optional<AdvertisementSupporter> advertisementSupporterOptional = advertisementSupporterService.findByAdvertisementIdAndCompanyId(advertisementId, companyId);
        if (advertisementSupporterOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No AdvertisementSupporter found for the given advertisementId and companyId");
        }
        
        AdvertisementSupporter advertisementSupporter = advertisementSupporterOptional.get();
        if (!advertisementSupporterService.checkNoResponse(advertisementSupporter)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(advertisementSupporter);
        }
        
        try {
        	AdvertisementSupporter result = advertisementSupporterService.acceptForCompany(advertisementSupporter);
        	
        	Thread thread = threadService.createThreadForSupporterAcceptance(advertisementSupporter);
        	Message message = messageService.createFirstMessageInThreadAcceptanceSupporter(thread, advertisementSupporter);
        
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForSupporterAcceptance(advertisementSupporter);
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
            return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Server error!");
		}
    }
    
    @PutMapping("/advertisement-supporters/reject/{advertisementId}/{companyId}")
    public ResponseEntity<?> rejectSupportForCompany(@PathVariable Long advertisementId, @PathVariable Long companyId) {
        log.debug("REST request to reject AdvertisementSupporter for Advertisement {} and Company {}", advertisementId, companyId);
        
        Optional<AdvertisementSupporter> advertisementSupporterOptional = advertisementSupporterService.findByAdvertisementIdAndCompanyId(advertisementId, companyId);
        if (advertisementSupporterOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No AdvertisementSupporter found for the given advertisementId and companyId");
        }
        
        AdvertisementSupporter advertisementSupporter = advertisementSupporterOptional.get();
        if (!advertisementSupporterService.checkNoResponse(advertisementSupporter)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(advertisementSupporter);
        }
        
        try {
        	AdvertisementSupporter result = advertisementSupporterService.rejectForCompany(advertisementSupporter);
        	
        	Thread thread = threadService.createThreadForSupporterRejection(advertisementSupporter);
        	Message message = messageService.createFirstMessageInThreadRejectionSupporter(thread, advertisementSupporter);
        
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForSupporterRejection(advertisementSupporter);
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
            return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Server error!");
		}
    }
}
