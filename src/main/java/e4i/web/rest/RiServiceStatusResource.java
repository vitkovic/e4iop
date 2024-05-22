package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiServiceStatus;
import e4i.repository.RiServiceStatusRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiServiceStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiServiceStatusResource {

    private final Logger log = LoggerFactory.getLogger(RiServiceStatusResource.class);

    private static final String ENTITY_NAME = "riServiceStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiServiceStatusRepository riServiceStatusRepository;

    public RiServiceStatusResource(RiServiceStatusRepository riServiceStatusRepository) {
        this.riServiceStatusRepository = riServiceStatusRepository;
    }

    /**
     * {@code POST  /ri-service-statuses} : Create a new riServiceStatus.
     *
     * @param riServiceStatus the riServiceStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riServiceStatus, or with status {@code 400 (Bad Request)} if the riServiceStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-service-statuses")
    public ResponseEntity<RiServiceStatus> createRiServiceStatus(@RequestBody RiServiceStatus riServiceStatus) throws URISyntaxException {
        log.debug("REST request to save RiServiceStatus : {}", riServiceStatus);
        if (riServiceStatus.getId() != null) {
            throw new BadRequestAlertException("A new riServiceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiServiceStatus result = riServiceStatusRepository.save(riServiceStatus);
        return ResponseEntity.created(new URI("/api/ri-service-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-service-statuses} : Updates an existing riServiceStatus.
     *
     * @param riServiceStatus the riServiceStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riServiceStatus,
     * or with status {@code 400 (Bad Request)} if the riServiceStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riServiceStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-service-statuses")
    public ResponseEntity<RiServiceStatus> updateRiServiceStatus(@RequestBody RiServiceStatus riServiceStatus) throws URISyntaxException {
        log.debug("REST request to update RiServiceStatus : {}", riServiceStatus);
        if (riServiceStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiServiceStatus result = riServiceStatusRepository.save(riServiceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riServiceStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-service-statuses} : get all the riServiceStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riServiceStatuses in body.
     */
    @GetMapping("/ri-service-statuses")
    public List<RiServiceStatus> getAllRiServiceStatuses() {
        log.debug("REST request to get all RiServiceStatuses");
        return riServiceStatusRepository.findAll();
    }

    /**
     * {@code GET  /ri-service-statuses/:id} : get the "id" riServiceStatus.
     *
     * @param id the id of the riServiceStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riServiceStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-service-statuses/{id}")
    public ResponseEntity<RiServiceStatus> getRiServiceStatus(@PathVariable Long id) {
        log.debug("REST request to get RiServiceStatus : {}", id);
        Optional<RiServiceStatus> riServiceStatus = riServiceStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riServiceStatus);
    }

    /**
     * {@code DELETE  /ri-service-statuses/:id} : delete the "id" riServiceStatus.
     *
     * @param id the id of the riServiceStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-service-statuses/{id}")
    public ResponseEntity<Void> deleteRiServiceStatus(@PathVariable Long id) {
        log.debug("REST request to delete RiServiceStatus : {}", id);
        riServiceStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
