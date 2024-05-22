package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RipoActivityStatus;
import e4i.repository.RipoActivityStatusRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RipoActivityStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RipoActivityStatusResource {

    private final Logger log = LoggerFactory.getLogger(RipoActivityStatusResource.class);

    private static final String ENTITY_NAME = "ripoActivityStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RipoActivityStatusRepository ripoActivityStatusRepository;

    public RipoActivityStatusResource(RipoActivityStatusRepository ripoActivityStatusRepository) {
        this.ripoActivityStatusRepository = ripoActivityStatusRepository;
    }

    /**
     * {@code POST  /ripo-activity-statuses} : Create a new ripoActivityStatus.
     *
     * @param ripoActivityStatus the ripoActivityStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ripoActivityStatus, or with status {@code 400 (Bad Request)} if the ripoActivityStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ripo-activity-statuses")
    public ResponseEntity<RipoActivityStatus> createRipoActivityStatus(@RequestBody RipoActivityStatus ripoActivityStatus) throws URISyntaxException {
        log.debug("REST request to save RipoActivityStatus : {}", ripoActivityStatus);
        if (ripoActivityStatus.getId() != null) {
            throw new BadRequestAlertException("A new ripoActivityStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RipoActivityStatus result = ripoActivityStatusRepository.save(ripoActivityStatus);
        return ResponseEntity.created(new URI("/api/ripo-activity-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ripo-activity-statuses} : Updates an existing ripoActivityStatus.
     *
     * @param ripoActivityStatus the ripoActivityStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ripoActivityStatus,
     * or with status {@code 400 (Bad Request)} if the ripoActivityStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ripoActivityStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ripo-activity-statuses")
    public ResponseEntity<RipoActivityStatus> updateRipoActivityStatus(@RequestBody RipoActivityStatus ripoActivityStatus) throws URISyntaxException {
        log.debug("REST request to update RipoActivityStatus : {}", ripoActivityStatus);
        if (ripoActivityStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RipoActivityStatus result = ripoActivityStatusRepository.save(ripoActivityStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ripoActivityStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ripo-activity-statuses} : get all the ripoActivityStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ripoActivityStatuses in body.
     */
    @GetMapping("/ripo-activity-statuses")
    public List<RipoActivityStatus> getAllRipoActivityStatuses() {
        log.debug("REST request to get all RipoActivityStatuses");
        return ripoActivityStatusRepository.findAll();
    }

    /**
     * {@code GET  /ripo-activity-statuses/:id} : get the "id" ripoActivityStatus.
     *
     * @param id the id of the ripoActivityStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ripoActivityStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ripo-activity-statuses/{id}")
    public ResponseEntity<RipoActivityStatus> getRipoActivityStatus(@PathVariable Long id) {
        log.debug("REST request to get RipoActivityStatus : {}", id);
        Optional<RipoActivityStatus> ripoActivityStatus = ripoActivityStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ripoActivityStatus);
    }

    /**
     * {@code DELETE  /ripo-activity-statuses/:id} : delete the "id" ripoActivityStatus.
     *
     * @param id the id of the ripoActivityStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ripo-activity-statuses/{id}")
    public ResponseEntity<Void> deleteRipoActivityStatus(@PathVariable Long id) {
        log.debug("REST request to delete RipoActivityStatus : {}", id);
        ripoActivityStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
