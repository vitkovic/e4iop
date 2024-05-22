package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ServiceAccreditation;
import e4i.repository.ServiceAccreditationRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ServiceAccreditation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceAccreditationResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAccreditationResource.class);

    private static final String ENTITY_NAME = "serviceAccreditation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceAccreditationRepository serviceAccreditationRepository;

    public ServiceAccreditationResource(ServiceAccreditationRepository serviceAccreditationRepository) {
        this.serviceAccreditationRepository = serviceAccreditationRepository;
    }

    /**
     * {@code POST  /service-accreditations} : Create a new serviceAccreditation.
     *
     * @param serviceAccreditation the serviceAccreditation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceAccreditation, or with status {@code 400 (Bad Request)} if the serviceAccreditation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-accreditations")
    public ResponseEntity<ServiceAccreditation> createServiceAccreditation(@RequestBody ServiceAccreditation serviceAccreditation) throws URISyntaxException {
        log.debug("REST request to save ServiceAccreditation : {}", serviceAccreditation);
        if (serviceAccreditation.getId() != null) {
            throw new BadRequestAlertException("A new serviceAccreditation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceAccreditation result = serviceAccreditationRepository.save(serviceAccreditation);
        return ResponseEntity.created(new URI("/api/service-accreditations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-accreditations} : Updates an existing serviceAccreditation.
     *
     * @param serviceAccreditation the serviceAccreditation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceAccreditation,
     * or with status {@code 400 (Bad Request)} if the serviceAccreditation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceAccreditation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-accreditations")
    public ResponseEntity<ServiceAccreditation> updateServiceAccreditation(@RequestBody ServiceAccreditation serviceAccreditation) throws URISyntaxException {
        log.debug("REST request to update ServiceAccreditation : {}", serviceAccreditation);
        if (serviceAccreditation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceAccreditation result = serviceAccreditationRepository.save(serviceAccreditation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceAccreditation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-accreditations} : get all the serviceAccreditations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceAccreditations in body.
     */
    @GetMapping("/service-accreditations")
    public List<ServiceAccreditation> getAllServiceAccreditations() {
        log.debug("REST request to get all ServiceAccreditations");
        return serviceAccreditationRepository.findAll();
    }

    /**
     * {@code GET  /service-accreditations/:id} : get the "id" serviceAccreditation.
     *
     * @param id the id of the serviceAccreditation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceAccreditation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-accreditations/{id}")
    public ResponseEntity<ServiceAccreditation> getServiceAccreditation(@PathVariable Long id) {
        log.debug("REST request to get ServiceAccreditation : {}", id);
        Optional<ServiceAccreditation> serviceAccreditation = serviceAccreditationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceAccreditation);
    }

    /**
     * {@code DELETE  /service-accreditations/:id} : delete the "id" serviceAccreditation.
     *
     * @param id the id of the serviceAccreditation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-accreditations/{id}")
    public ResponseEntity<Void> deleteServiceAccreditation(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAccreditation : {}", id);
        serviceAccreditationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
