package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ServiceType;
import e4i.repository.ServiceTypeRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ServiceType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeResource.class);

    private static final String ENTITY_NAME = "serviceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeResource(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    /**
     * {@code POST  /service-types} : Create a new serviceType.
     *
     * @param serviceType the serviceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceType, or with status {@code 400 (Bad Request)} if the serviceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-types")
    public ResponseEntity<ServiceType> createServiceType(@RequestBody ServiceType serviceType) throws URISyntaxException {
        log.debug("REST request to save ServiceType : {}", serviceType);
        if (serviceType.getId() != null) {
            throw new BadRequestAlertException("A new serviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceType result = serviceTypeRepository.save(serviceType);
        return ResponseEntity.created(new URI("/api/service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-types} : Updates an existing serviceType.
     *
     * @param serviceType the serviceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceType,
     * or with status {@code 400 (Bad Request)} if the serviceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-types")
    public ResponseEntity<ServiceType> updateServiceType(@RequestBody ServiceType serviceType) throws URISyntaxException {
        log.debug("REST request to update ServiceType : {}", serviceType);
        if (serviceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceType result = serviceTypeRepository.save(serviceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-types} : get all the serviceTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceTypes in body.
     */
    @GetMapping("/service-types")
    public List<ServiceType> getAllServiceTypes() {
        log.debug("REST request to get all ServiceTypes");
        return serviceTypeRepository.findAll();
    }

    /**
     * {@code GET  /service-types/:id} : get the "id" serviceType.
     *
     * @param id the id of the serviceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-types/{id}")
    public ResponseEntity<ServiceType> getServiceType(@PathVariable Long id) {
        log.debug("REST request to get ServiceType : {}", id);
        Optional<ServiceType> serviceType = serviceTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceType);
    }

    /**
     * {@code DELETE  /service-types/:id} : delete the "id" serviceType.
     *
     * @param id the id of the serviceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-types/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceType : {}", id);
        serviceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
