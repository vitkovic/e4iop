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
import e4i.repository.RequestRespondRepository;
import e4i.repository.RequestStateRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link e4i.domain.RequestRespond}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequestRespondResource {

    private final Logger log = LoggerFactory.getLogger(RequestRespondResource.class);

    private static final String ENTITY_NAME = "requestRespond";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestRespondRepository requestRespondRepository;
    private final RequestStateRepository requestStateRepository;

   
    public RequestRespondResource(RequestRespondRepository requestRespondRepository,
			RequestStateRepository requestStateRepository) {
		this.requestRespondRepository = requestRespondRepository;
		this.requestStateRepository = requestStateRepository;
	}

	/**
     * {@code POST  /request-responds} : Create a new requestRespond.
     *
     * @param requestRespond the requestRespond to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestRespond, or with status {@code 400 (Bad Request)} if the requestRespond has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-responds")
    public ResponseEntity<RequestRespond> createRequestRespond(@RequestBody RequestRespond requestRespond) throws URISyntaxException {
        log.debug("REST request to save RequestRespond : {}", requestRespond);
        if (requestRespond.getId() != null) {
            throw new BadRequestAlertException("A new requestRespond cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        RequestState requestStateClosed = requestStateRepository.findByStateEn("closed");
        RequestState requestStateOpened = requestStateRepository.findByStateEn("opened");
        if(requestRespond.getRequestAnswer().getAnswerEn().equals("Request accepted") || 
        		requestRespond.getRequestAnswer().getAnswerEn().equals("Request denied")) {
        	RequestForService requestForService = requestRespond.getRequestForService();
        	requestForService.setState(requestStateClosed);
        }else {
        	RequestForService requestForService = requestRespond.getRequestForService();
        	requestForService.setState(requestStateOpened);
        }        	
        	
        RequestRespond result = requestRespondRepository.save(requestRespond);
        return ResponseEntity.created(new URI("/api/request-responds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-responds} : Updates an existing requestRespond.
     *
     * @param requestRespond the requestRespond to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestRespond,
     * or with status {@code 400 (Bad Request)} if the requestRespond is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestRespond couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-responds")
    public ResponseEntity<RequestRespond> updateRequestRespond(@RequestBody RequestRespond requestRespond) throws URISyntaxException {
        log.debug("REST request to update RequestRespond : {}", requestRespond);
        if (requestRespond.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        RequestState requestStateClosed = requestStateRepository.findByStateEn("closed");
        RequestState requestStateOpened = requestStateRepository.findByStateEn("opened");
        if(requestRespond.getRequestAnswer().getAnswerEn().equals("Request accepted") || 
        		requestRespond.getRequestAnswer().getAnswerEn().equals("Request denied")) {
        	RequestForService requestForService = requestRespond.getRequestForService();
        	requestForService.setState(requestStateClosed);
        }else {
        	RequestForService requestForService = requestRespond.getRequestForService();
        	requestForService.setState(requestStateOpened);
        }
        
        RequestRespond result = requestRespondRepository.save(requestRespond);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestRespond.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-responds} : get all the requestResponds.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestResponds in body.
     */
    @GetMapping("/request-responds")
    public List<RequestRespond> getAllRequestResponds(@RequestParam(required = false) String filter) {
        if ("calendar-is-null".equals(filter)) {
            log.debug("REST request to get all RequestResponds where calendar is null");
            return StreamSupport
                .stream(requestRespondRepository.findAll().spliterator(), false)
                .filter(requestRespond -> requestRespond.getCalendar() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all RequestResponds");
        return requestRespondRepository.findAll();
    }

    /**
     * {@code GET  /request-responds/:id} : get the "id" requestRespond.
     *
     * @param id the id of the requestRespond to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestRespond, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-responds/{id}")
    public ResponseEntity<RequestRespond> getRequestRespond(@PathVariable Long id) {
        log.debug("REST request to get RequestRespond : {}", id);
        Optional<RequestRespond> requestRespond = requestRespondRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestRespond);
    }

    /**
     * {@code DELETE  /request-responds/:id} : delete the "id" requestRespond.
     *
     * @param id the id of the requestRespond to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-responds/{id}")
    public ResponseEntity<Void> deleteRequestRespond(@PathVariable Long id) {
        log.debug("REST request to delete RequestRespond : {}", id);
        requestRespondRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/request-responds/find-for-service/{serviceId}")                  
    public ResponseEntity<RequestRespond> getRequestRespondForService(@PathVariable Long serviceId) {
        log.debug("REST request to get RequestRespond for service: {}", serviceId);
        Optional<RequestRespond> requestRespond = requestRespondRepository.findByRequestForServiceId(serviceId);        
        return ResponseUtil.wrapOrNotFound(requestRespond);
    }
}
