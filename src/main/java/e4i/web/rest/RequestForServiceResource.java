package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.RequestForService;
import e4i.service.RequestForServiceService;
import e4i.web.rest.dto.RequestForServiceZbirnoDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link e4i.domain.RequestForService}.
 */
@RestController
@RequestMapping("/api")
public class RequestForServiceResource {

    private final Logger log = LoggerFactory.getLogger(RequestForServiceResource.class);

    private static final String ENTITY_NAME = "requestForService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestForServiceService requestForServiceService;

    public RequestForServiceResource(RequestForServiceService requestForServiceService) {
        this.requestForServiceService = requestForServiceService;
    }

    /**
     * {@code POST  /request-for-services} : Create a new requestForService.
     *
     * @param requestForService the requestForService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestForService, or with status {@code 400 (Bad Request)} if the requestForService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-for-services")
    public ResponseEntity<RequestForService> createRequestForService(@RequestBody RequestForService requestForService) throws URISyntaxException {
        log.debug("REST request to save RequestForService : {}", requestForService);
        if (requestForService.getId() != null) {
            throw new BadRequestAlertException("A new requestForService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestForService result = requestForServiceService.save(requestForService);
        return ResponseEntity.created(new URI("/api/request-for-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-for-services} : Updates an existing requestForService.
     *
     * @param requestForService the requestForService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestForService,
     * or with status {@code 400 (Bad Request)} if the requestForService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestForService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-for-services")
    public ResponseEntity<RequestForService> updateRequestForService(@RequestBody RequestForService requestForService) throws URISyntaxException {
        log.debug("REST request to update RequestForService : {}", requestForService);
        if (requestForService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestForService result = requestForServiceService.save(requestForService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestForService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-for-services} : get all the requestForServices.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestForServices in body.
     */
    @GetMapping("/request-for-services")
    public ResponseEntity<List<RequestForService>> getAllRequestForServices(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("respond-is-null".equals(filter)) {
            log.debug("REST request to get all RequestForServices where respond is null");
            return new ResponseEntity<>(requestForServiceService.findAllWhereRespondIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of RequestForServices");
        Page<RequestForService> page = requestForServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    /**
     * Vadi sve zahteve radi ocene
     * @param pageable
     * @return
     */
    @GetMapping("/request-for-services-rating")
    public ResponseEntity<List<RequestForService>> getAllRequestForServicesRating(Pageable pageable) {        
        log.debug("REST request to get a page of RequestForServices");
        Page<RequestForService> page = requestForServiceService.findAllRating(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-for-services/:id} : get the "id" requestForService.
     *
     * @param id the id of the requestForService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestForService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-for-services/{id}")
    public ResponseEntity<RequestForService> getRequestForService(@PathVariable Long id) {
        log.debug("REST request to get RequestForService : {}", id);
        Optional<RequestForService> requestForService = requestForServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestForService);
    }
    
    /**
     * Vraca sve otvorene zahteve, ako postoji, za zadati servis i trenutnog korisnika
     * @param id
     * @return
     */
    @GetMapping("/request-for-services/opened-for-given-service/{serviceId}")
    public List<RequestForService> getOpenedForService(@PathVariable Long serviceId) {
        log.debug("REST request to get RequestForService : {}", serviceId);
        List<RequestForService> requests = requestForServiceService.findOpenedForGivenServiceId(serviceId);
        return requests;
    } 
    // dodat jos jedan red koda

    /**
     * {@code DELETE  /request-for-services/:id} : delete the "id" requestForService.
     *
     * @param id the id of the requestForService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-for-services/{id}")
    public ResponseEntity<Void> deleteRequestForService(@PathVariable Long id) {
        log.debug("REST request to delete RequestForService : {}", id);
        requestForServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    /** 
     * Proverava koliko trenutni korisnik ima otvorenih zahteva za zadatom uslugom
     * @param id
     * @return
     */
    @GetMapping("/request-for-services/check-opened-for-given-service/{serviceId}")
    public ResponseEntity<Integer> checkOpenedForService(@PathVariable Long serviceId) {
        log.debug("REST request to get RequestForService : {}", serviceId);
        int count = requestForServiceService.checkOpenedForGivenService(serviceId);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
    
    /**
     * Za zadati zahtev vraca i ponudu i odgovor na ponudu (da se priakzu zbirno)
     * @param id
     * @return
     */
    @GetMapping("/request-for-services/zbirno/{id}")
    public ResponseEntity<RequestForServiceZbirnoDTO> getZbirno(@PathVariable Long id) {
        log.debug("REST request to get RequestForService : {}", id);
        
        RequestForServiceZbirnoDTO requestForServiceZbirno = requestForServiceService.getZbirno(id);
        
        return new ResponseEntity<RequestForServiceZbirnoDTO>(requestForServiceZbirno, HttpStatus.OK);
    }
}
