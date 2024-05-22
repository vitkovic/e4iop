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

import e4i.domain.RiService;
import e4i.service.RiServiceService;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.RiServiceInfrastructureOrganizationDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiService}.
 */
@RestController
@RequestMapping("/api")
public class RiServiceResource {

    private final Logger log = LoggerFactory.getLogger(RiServiceResource.class);

    private static final String ENTITY_NAME = "riService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiServiceService riServiceService;

    public RiServiceResource(RiServiceService riServiceService) {
        this.riServiceService = riServiceService;
    }

    /**
     * {@code POST  /ri-services} : Create a new riService.
     *
     * @param riService the riService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riService, or with status {@code 400 (Bad Request)} if the riService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-services")
    public ResponseEntity<RiService> createRiService(@RequestBody RiService riService) throws URISyntaxException {
        log.debug("REST request to save RiService : {}", riService);
        if (riService.getId() != null) {
            throw new BadRequestAlertException("A new riService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiService result = riServiceService.save(riService);
        return ResponseEntity.created(new URI("/api/ri-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-services} : Updates an existing riService.
     *
     * @param riService the riService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riService,
     * or with status {@code 400 (Bad Request)} if the riService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-services")
    public ResponseEntity<RiService> updateRiService(@RequestBody RiService riService) throws URISyntaxException {
        log.debug("REST request to update RiService : {}", riService);
        if (riService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiService result = riServiceService.save(riService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-services} : get all the riServices.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riServices in body.
     */
    @GetMapping("/ri-services")
    public ResponseEntity<List<RiService>> getAllRiServices(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RiServices");
        Page<RiService> page;
        if (eagerload) {
            page = riServiceService.findAllWithEagerRelationships(pageable);
        } else {
            page = riServiceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ri-services/:id} : get the "id" riService.
     *
     * @param id the id of the riService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-services/{id}")
    public ResponseEntity<RiService> getRiService(@PathVariable Long id) {
        log.debug("REST request to get RiService : {}", id);
        Optional<RiService> riService = riServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riService);
    }

    /**
     * {@code DELETE  /ri-services/:id} : delete the "id" riService.
     *
     * @param id the id of the riService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-services/{id}")
    public ResponseEntity<Void> deleteRiService(@PathVariable Long id) {
        log.debug("REST request to delete RiService : {}", id);
        riServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    /**
     * Api za pregled. Razlicit od onog gore samo po tome sto svako moze da pristupi. 
     * @param pageable
     * @param eagerload
     * @return
     */
    @GetMapping("/ri-services-pregled")
    public ResponseEntity<List<RiService>> getAllRiServicesPregled(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RiServices");
        Page<RiService> page;
        if (eagerload) {
            page = riServiceService.findAllWithEagerRelationships(pageable);
        } else {
            page = riServiceService.findAllPregled(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @PostMapping("/ri-services-pregled/search")
    public List<RiServiceDTOSearch> getAllRiServicesPregledSerbianSearch(@RequestBody SearchDTO search) {
        log.debug("REST request to search RiServices");
        if(search.getCurrentLanguage().equals("en"))
        	return riServiceService.searchByEnglishKeywords(search);
        else
        	return riServiceService.searchBySerbianKeywords(search);
        
    }
    
    @PostMapping("/ri-services-homepage/search")
    public List<RiServiceInfrastructureOrganizationDTO> getAllRiServicesHomepageSearch(@RequestBody SearchDTO search) {
        log.debug("REST request to search RiServices");
        if(search.getCurrentLanguage().equals("en"))
        	return riServiceService.searchAllByEnglishKeywords(search);
        else
        	return riServiceService.searchAllBySerbianKeywords(search);
        
    }

    /**
     * {@code GET  /ri-services/:id} : get the "id" riService.
     *
     * @param id the id of the riService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-services-pregled/{id}")
    public ResponseEntity<RiService> getRiServicePregled(@PathVariable Long id) {
        log.debug("REST request to get RiService : {}", id);
        Optional<RiService> riService = riServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riService);
    }
    
    /**
     * Broj usluga u bazi
     * @param id
     * @return
     */
    @GetMapping("/ri-services-pregled/count")
    public ResponseEntity<Long> countRiServicesPregled() {
        log.debug("REST request to get count of riservices: {}");
        Long count = riServiceService.countAll();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }
}
