package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.CollaborationStatus;
import e4i.service.CollaborationStatusService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CollaborationStatus}.
 */
@RestController
@RequestMapping("/api")
public class CollaborationStatusResource {
	private final Logger log = LoggerFactory.getLogger(CollaborationStatusResource.class);

    private static final String ENTITY_NAME = "collaborationStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaborationStatusService collaborationStatusService;

    public CollaborationStatusResource(CollaborationStatusService collaborationStatusService) {
        this.collaborationStatusService = collaborationStatusService;
    }

    /**
     * {@code POST  /collaboration-statuses} : Create a new collaborationStatus.
     *
     * @param collaborationStatus the collaborationStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaborationStatus, or with status {@code 400 (Bad Request)} if the collaborationStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaboration-statuses")
    public ResponseEntity<CollaborationStatus> createCollaborationStatus(@Valid @RequestBody CollaborationStatus collaborationStatus) throws URISyntaxException {
        log.debug("REST request to save CollaborationStatus : {}", collaborationStatus);
        if (collaborationStatus.getId() != null) {
            throw new BadRequestAlertException("A new collaborationStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollaborationStatus result = collaborationStatusService.save(collaborationStatus);
        return ResponseEntity.created(new URI("/api/collaboration-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collaboration-statuses} : Updates an existing collaborationStatus.
     *
     * @param collaborationStatus the collaborationStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborationStatus,
     * or with status {@code 400 (Bad Request)} if the collaborationStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaborationStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaboration-statuses")
    public ResponseEntity<CollaborationStatus> updateCollaborationStatus(@Valid @RequestBody CollaborationStatus collaborationStatus) throws URISyntaxException {
        log.debug("REST request to update CollaborationStatus : {}", collaborationStatus);
        if (collaborationStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollaborationStatus result = collaborationStatusService.save(collaborationStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaborationStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collaboration-statuses} : get all the collaborationStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborationStatuses in body.
     */
    @GetMapping("/collaboration-statuses")
    public List<CollaborationStatus> getAllAdvertisementStatuses() {
        log.debug("REST request to get all AdvertisementStatuses");
        return collaborationStatusService.findAll();
    }

    /**
     * {@code GET  /collaboration-statuses/:id} : get the "id" collaborationStatus.
     *
     * @param id the id of the collaborationStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaborationStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaboration-statuses/{id}")
    public ResponseEntity<CollaborationStatus> getCollaborationStatus(@PathVariable Long id) {
        log.debug("REST request to get collaborationStatus : {}", id);
        Optional<CollaborationStatus> collaborationStatus = collaborationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaborationStatus);
    }

    /**
     * {@code DELETE  /collaboration-statuses/:id} : delete the "id" collaborationStatus.
     *
     * @param id the id of the collaborationStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaboration-statuses/{id}")
    public ResponseEntity<Void> deleteCollaborationStatus(@PathVariable Long id) {
        log.debug("REST request to delete CollaborationStatus : {}", id);
        collaborationStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
