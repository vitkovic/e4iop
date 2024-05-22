package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ServiceAvailability;
import e4i.repository.ServiceAvailabilityRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ServiceAvailability}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceAvailabilityResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAvailabilityResource.class);

    private static final String ENTITY_NAME = "serviceAvailability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAvailabilityRepository serviceAvailabilityRepository;

    public ServiceAvailabilityResource(ServiceAvailabilityRepository serviceAvailabilityRepository) {
        this.serviceAvailabilityRepository = serviceAvailabilityRepository;
    }

    /**
     * {@code POST  /service-availabilities} : Create a new serviceAvailability.
     *
     * @param serviceAvailability the serviceAvailability to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAvailability, or with status {@code 400 (Bad Request)} if the serviceAvailability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-availabilities")
    public ResponseEntity<ServiceAvailability> createServiceAvailability(@RequestBody ServiceAvailability serviceAvailability) throws URISyntaxException {
        log.debug("REST request to save ServiceAvailability : {}", serviceAvailability);
        if (serviceAvailability.getId() != null) {
            throw new BadRequestAlertException("A new serviceAvailability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAvailability result = serviceAvailabilityRepository.save(serviceAvailability);
        return ResponseEntity.created(new URI("/api/service-availabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-availabilities} : Updates an existing serviceAvailability.
     *
     * @param serviceAvailability the serviceAvailability to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAvailability,
     * or with status {@code 400 (Bad Request)} if the serviceAvailability is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAvailability couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-availabilities")
    public ResponseEntity<ServiceAvailability> updateServiceAvailability(@RequestBody ServiceAvailability serviceAvailability) throws URISyntaxException {
        log.debug("REST request to update ServiceAvailability : {}", serviceAvailability);
        if (serviceAvailability.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAvailability result = serviceAvailabilityRepository.save(serviceAvailability);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAvailability.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-availabilities} : get all the serviceAvailabilities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAvailabilities in body.
     */
    @GetMapping("/service-availabilities")
    public List<ServiceAvailability> getAllServiceAvailabilities() {
        log.debug("REST request to get all ServiceAvailabilities");
        return serviceAvailabilityRepository.findAll();
    }

    /**
     * {@code GET  /service-availabilities/:id} : get the "id" serviceAvailability.
     *
     * @param id the id of the serviceAvailability to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAvailability, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-availabilities/{id}")
    public ResponseEntity<ServiceAvailability> getServiceAvailability(@PathVariable Long id) {
        log.debug("REST request to get ServiceAvailability : {}", id);
        Optional<ServiceAvailability> serviceAvailability = serviceAvailabilityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceAvailability);
    }

    /**
     * {@code DELETE  /service-availabilities/:id} : delete the "id" serviceAvailability.
     *
     * @param id the id of the serviceAvailability to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-availabilities/{id}")
    public ResponseEntity<Void> deleteServiceAvailability(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAvailability : {}", id);
        serviceAvailabilityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
