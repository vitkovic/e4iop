package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ServiceSubtype;
import e4i.repository.ServiceSubtypeRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ServiceSubtype}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceSubtypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSubtypeResource.class);

    private static final String ENTITY_NAME = "serviceSubtype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceSubtypeRepository serviceSubtypeRepository;

    public ServiceSubtypeResource(ServiceSubtypeRepository serviceSubtypeRepository) {
        this.serviceSubtypeRepository = serviceSubtypeRepository;
    }

    /**
     * {@code POST  /service-subtypes} : Create a new serviceSubtype.
     *
     * @param serviceSubtype the serviceSubtype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceSubtype, or with status {@code 400 (Bad Request)} if the serviceSubtype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-subtypes")
    public ResponseEntity<ServiceSubtype> createServiceSubtype(@RequestBody ServiceSubtype serviceSubtype) throws URISyntaxException {
        log.debug("REST request to save ServiceSubtype : {}", serviceSubtype);
        if (serviceSubtype.getId() != null) {
            throw new BadRequestAlertException("A new serviceSubtype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceSubtype result = serviceSubtypeRepository.save(serviceSubtype);
        return ResponseEntity.created(new URI("/api/service-subtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-subtypes} : Updates an existing serviceSubtype.
     *
     * @param serviceSubtype the serviceSubtype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceSubtype,
     * or with status {@code 400 (Bad Request)} if the serviceSubtype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceSubtype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-subtypes")
    public ResponseEntity<ServiceSubtype> updateServiceSubtype(@RequestBody ServiceSubtype serviceSubtype) throws URISyntaxException {
        log.debug("REST request to update ServiceSubtype : {}", serviceSubtype);
        if (serviceSubtype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceSubtype result = serviceSubtypeRepository.save(serviceSubtype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceSubtype.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-subtypes} : get all the serviceSubtypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceSubtypes in body.
     */
    @GetMapping("/service-subtypes")
    public List<ServiceSubtype> getAllServiceSubtypes() {
        log.debug("REST request to get all ServiceSubtypes");
        return serviceSubtypeRepository.findAll();
    }

    /**
     * {@code GET  /service-subtypes/:id} : get the "id" serviceSubtype.
     *
     * @param id the id of the serviceSubtype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceSubtype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-subtypes/{id}")
    public ResponseEntity<ServiceSubtype> getServiceSubtype(@PathVariable Long id) {
        log.debug("REST request to get ServiceSubtype : {}", id);
        Optional<ServiceSubtype> serviceSubtype = serviceSubtypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceSubtype);
    }

    /**
     * {@code DELETE  /service-subtypes/:id} : delete the "id" serviceSubtype.
     *
     * @param id the id of the serviceSubtype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-subtypes/{id}")
    public ResponseEntity<Void> deleteServiceSubtype(@PathVariable Long id) {
        log.debug("REST request to delete ServiceSubtype : {}", id);
        serviceSubtypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
