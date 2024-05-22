package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiStatus;
import e4i.repository.RiStatusRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiStatusResource {

    private final Logger log = LoggerFactory.getLogger(RiStatusResource.class);

    private static final String ENTITY_NAME = "riStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiStatusRepository riStatusRepository;

    public RiStatusResource(RiStatusRepository riStatusRepository) {
        this.riStatusRepository = riStatusRepository;
    }

    /**
     * {@code POST  /ri-statuses} : Create a new riStatus.
     *
     * @param riStatus the riStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riStatus, or with status {@code 400 (Bad Request)} if the riStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-statuses")
    public ResponseEntity<RiStatus> createRiStatus(@RequestBody RiStatus riStatus) throws URISyntaxException {
        log.debug("REST request to save RiStatus : {}", riStatus);
        if (riStatus.getId() != null) {
            throw new BadRequestAlertException("A new riStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiStatus result = riStatusRepository.save(riStatus);
        return ResponseEntity.created(new URI("/api/ri-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-statuses} : Updates an existing riStatus.
     *
     * @param riStatus the riStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riStatus,
     * or with status {@code 400 (Bad Request)} if the riStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-statuses")
    public ResponseEntity<RiStatus> updateRiStatus(@RequestBody RiStatus riStatus) throws URISyntaxException {
        log.debug("REST request to update RiStatus : {}", riStatus);
        if (riStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiStatus result = riStatusRepository.save(riStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-statuses} : get all the riStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riStatuses in body.
     */
    @GetMapping("/ri-statuses")
    public List<RiStatus> getAllRiStatuses() {
        log.debug("REST request to get all RiStatuses");
        return riStatusRepository.findAll();
    }

    /**
     * {@code GET  /ri-statuses/:id} : get the "id" riStatus.
     *
     * @param id the id of the riStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-statuses/{id}")
    public ResponseEntity<RiStatus> getRiStatus(@PathVariable Long id) {
        log.debug("REST request to get RiStatus : {}", id);
        Optional<RiStatus> riStatus = riStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riStatus);
    }

    /**
     * {@code DELETE  /ri-statuses/:id} : delete the "id" riStatus.
     *
     * @param id the id of the riStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-statuses/{id}")
    public ResponseEntity<Void> deleteRiStatus(@PathVariable Long id) {
        log.debug("REST request to delete RiStatus : {}", id);
        riStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
