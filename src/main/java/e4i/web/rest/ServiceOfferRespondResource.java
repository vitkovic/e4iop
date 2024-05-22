package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RequestForService;
import e4i.domain.RequestRespond;
import e4i.domain.RequestState;
import e4i.domain.ServiceOfferRespond;
import e4i.repository.RequestStateRepository;
import e4i.repository.ServiceOfferRespondRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ServiceOfferRespond}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceOfferRespondResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOfferRespondResource.class);

    private static final String ENTITY_NAME = "serviceOfferRespond";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOfferRespondRepository serviceOfferRespondRepository;
    private final RequestStateRepository requestStateRepository;

   

    public ServiceOfferRespondResource(ServiceOfferRespondRepository serviceOfferRespondRepository,
			RequestStateRepository requestStateRepository) {
		this.serviceOfferRespondRepository = serviceOfferRespondRepository;
		this.requestStateRepository = requestStateRepository;
	}

	/**
     * {@code POST  /service-offer-responds} : Create a new serviceOfferRespond.
     *
     * @param serviceOfferRespond the serviceOfferRespond to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOfferRespond, or with status {@code 400 (Bad Request)} if the serviceOfferRespond has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-offer-responds")
    public ResponseEntity<ServiceOfferRespond> createServiceOfferRespond(@RequestBody ServiceOfferRespond serviceOfferRespond) throws URISyntaxException {
        log.debug("REST request to save ServiceOfferRespond : {}", serviceOfferRespond);
        if (serviceOfferRespond.getId() != null) {
            throw new BadRequestAlertException("A new serviceOfferRespond cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        RequestState requestStateClosed = requestStateRepository.findByStateEn("closed");
        RequestState requestStateOpened = requestStateRepository.findByStateEn("opened");
        if(serviceOfferRespond.getOfferAnswer().getAnswerEn().equals("Offer accepted") || 
        		serviceOfferRespond.getOfferAnswer().getAnswerEn().equals("Offer denied")) {        	
        	RequestForService requestForService = serviceOfferRespond.getRequestRespond().getRequestForService();
        	requestForService.setState(requestStateClosed);
        }else {
        	RequestForService requestForService = serviceOfferRespond.getRequestRespond().getRequestForService();
        	requestForService.setState(requestStateOpened);
        }
        
        ServiceOfferRespond result = serviceOfferRespondRepository.save(serviceOfferRespond);
        return ResponseEntity.created(new URI("/api/service-offer-responds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-offer-responds} : Updates an existing serviceOfferRespond.
     *
     * @param serviceOfferRespond the serviceOfferRespond to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOfferRespond,
     * or with status {@code 400 (Bad Request)} if the serviceOfferRespond is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOfferRespond couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-offer-responds")
    public ResponseEntity<ServiceOfferRespond> updateServiceOfferRespond(@RequestBody ServiceOfferRespond serviceOfferRespond) throws URISyntaxException {
        log.debug("REST request to update ServiceOfferRespond : {}", serviceOfferRespond);
        if (serviceOfferRespond.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        RequestState requestStateClosed = requestStateRepository.findByStateEn("closed");
        RequestState requestStateOpened = requestStateRepository.findByStateEn("opened");

        if(serviceOfferRespond.getOfferAnswer().getAnswerEn().equals("Offer accepted") || 
        		serviceOfferRespond.getOfferAnswer().getAnswerEn().equals("Offer denied")) {        	
        	RequestForService requestForService = serviceOfferRespond.getRequestRespond().getRequestForService();
        	requestForService.setState(requestStateClosed);
        }else {
        	RequestForService requestForService = serviceOfferRespond.getRequestRespond().getRequestForService();
        	requestForService.setState(requestStateOpened);
        }

        
        ServiceOfferRespond result = serviceOfferRespondRepository.save(serviceOfferRespond);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOfferRespond.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-offer-responds} : get all the serviceOfferResponds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOfferResponds in body.
     */
    @GetMapping("/service-offer-responds")
    public List<ServiceOfferRespond> getAllServiceOfferResponds() {
        log.debug("REST request to get all ServiceOfferResponds");
        return serviceOfferRespondRepository.findAll();
    }

    /**
     * {@code GET  /service-offer-responds/:id} : get the "id" serviceOfferRespond.
     *
     * @param id the id of the serviceOfferRespond to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOfferRespond, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-offer-responds/{id}")
    public ResponseEntity<ServiceOfferRespond> getServiceOfferRespond(@PathVariable Long id) {
        log.debug("REST request to get ServiceOfferRespond : {}", id);
        Optional<ServiceOfferRespond> serviceOfferRespond = serviceOfferRespondRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceOfferRespond);
    }

    /**
     * Vra'a odgovor na ponudu za zadatu ponudu
     * @param requestRespondId
     * @return
     */
    @GetMapping("/service-offer-responds/find-for-request-respond/{requestRespondId}")                  
    public ResponseEntity<ServiceOfferRespond> getServiceOfferRespondForRequestRespond(@PathVariable Long requestRespondId) {
        log.debug("REST request to get ServiceOfferRespond : {}", requestRespondId);
        Optional<ServiceOfferRespond> serviceOfferRespond = serviceOfferRespondRepository.findByRequestRespondId(requestRespondId);
        return ResponseUtil.wrapOrNotFound(serviceOfferRespond);
    }
    
    /**
     * {@code DELETE  /service-offer-responds/:id} : delete the "id" serviceOfferRespond.
     *
     * @param id the id of the serviceOfferRespond to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-offer-responds/{id}")
    public ResponseEntity<Void> deleteServiceOfferRespond(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOfferRespond : {}", id);
        serviceOfferRespondRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
