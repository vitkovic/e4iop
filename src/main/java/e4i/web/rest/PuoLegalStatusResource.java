package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.PuoLegalStatus;
import e4i.repository.PuoLegalStatusRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.PuoLegalStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PuoLegalStatusResource {

    private final Logger log = LoggerFactory.getLogger(PuoLegalStatusResource.class);

    private static final String ENTITY_NAME = "puoLegalStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuoLegalStatusRepository puoLegalStatusRepository;

    public PuoLegalStatusResource(PuoLegalStatusRepository puoLegalStatusRepository) {
        this.puoLegalStatusRepository = puoLegalStatusRepository;
    }

    /**
     * {@code POST  /puo-legal-statuses} : Create a new puoLegalStatus.
     *
     * @param puoLegalStatus the puoLegalStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puoLegalStatus, or with status {@code 400 (Bad Request)} if the puoLegalStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puo-legal-statuses")
    public ResponseEntity<PuoLegalStatus> createPuoLegalStatus(@RequestBody PuoLegalStatus puoLegalStatus) throws URISyntaxException {
        log.debug("REST request to save PuoLegalStatus : {}", puoLegalStatus);
        if (puoLegalStatus.getId() != null) {
            throw new BadRequestAlertException("A new puoLegalStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuoLegalStatus result = puoLegalStatusRepository.save(puoLegalStatus);
        return ResponseEntity.created(new URI("/api/puo-legal-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puo-legal-statuses} : Updates an existing puoLegalStatus.
     *
     * @param puoLegalStatus the puoLegalStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puoLegalStatus,
     * or with status {@code 400 (Bad Request)} if the puoLegalStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puoLegalStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puo-legal-statuses")
    public ResponseEntity<PuoLegalStatus> updatePuoLegalStatus(@RequestBody PuoLegalStatus puoLegalStatus) throws URISyntaxException {
        log.debug("REST request to update PuoLegalStatus : {}", puoLegalStatus);
        if (puoLegalStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuoLegalStatus result = puoLegalStatusRepository.save(puoLegalStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, puoLegalStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puo-legal-statuses} : get all the puoLegalStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puoLegalStatuses in body.
     */
    @GetMapping("/puo-legal-statuses")
    public List<PuoLegalStatus> getAllPuoLegalStatuses() {
        log.debug("REST request to get all PuoLegalStatuses");
        return puoLegalStatusRepository.findAll();
    }

    /**
     * {@code GET  /puo-legal-statuses/:id} : get the "id" puoLegalStatus.
     *
     * @param id the id of the puoLegalStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puoLegalStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puo-legal-statuses/{id}")
    public ResponseEntity<PuoLegalStatus> getPuoLegalStatus(@PathVariable Long id) {
        log.debug("REST request to get PuoLegalStatus : {}", id);
        Optional<PuoLegalStatus> puoLegalStatus = puoLegalStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(puoLegalStatus);
    }

    /**
     * {@code DELETE  /puo-legal-statuses/:id} : delete the "id" puoLegalStatus.
     *
     * @param id the id of the puoLegalStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puo-legal-statuses/{id}")
    public ResponseEntity<Void> deletePuoLegalStatus(@PathVariable Long id) {
        log.debug("REST request to delete PuoLegalStatus : {}", id);
        puoLegalStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
