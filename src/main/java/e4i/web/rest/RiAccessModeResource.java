package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiAccessMode;
import e4i.repository.RiAccessModeRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiAccessMode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiAccessModeResource {

    private final Logger log = LoggerFactory.getLogger(RiAccessModeResource.class);

    private static final String ENTITY_NAME = "riAccessMode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiAccessModeRepository riAccessModeRepository;

    public RiAccessModeResource(RiAccessModeRepository riAccessModeRepository) {
        this.riAccessModeRepository = riAccessModeRepository;
    }

    /**
     * {@code POST  /ri-access-modes} : Create a new riAccessMode.
     *
     * @param riAccessMode the riAccessMode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riAccessMode, or with status {@code 400 (Bad Request)} if the riAccessMode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-access-modes")
    public ResponseEntity<RiAccessMode> createRiAccessMode(@RequestBody RiAccessMode riAccessMode) throws URISyntaxException {
        log.debug("REST request to save RiAccessMode : {}", riAccessMode);
        if (riAccessMode.getId() != null) {
            throw new BadRequestAlertException("A new riAccessMode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiAccessMode result = riAccessModeRepository.save(riAccessMode);
        return ResponseEntity.created(new URI("/api/ri-access-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-access-modes} : Updates an existing riAccessMode.
     *
     * @param riAccessMode the riAccessMode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riAccessMode,
     * or with status {@code 400 (Bad Request)} if the riAccessMode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riAccessMode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-access-modes")
    public ResponseEntity<RiAccessMode> updateRiAccessMode(@RequestBody RiAccessMode riAccessMode) throws URISyntaxException {
        log.debug("REST request to update RiAccessMode : {}", riAccessMode);
        if (riAccessMode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiAccessMode result = riAccessModeRepository.save(riAccessMode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riAccessMode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-access-modes} : get all the riAccessModes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riAccessModes in body.
     */
    @GetMapping("/ri-access-modes")
    public List<RiAccessMode> getAllRiAccessModes() {
        log.debug("REST request to get all RiAccessModes");
        return riAccessModeRepository.findAll();
    }

    /**
     * {@code GET  /ri-access-modes/:id} : get the "id" riAccessMode.
     *
     * @param id the id of the riAccessMode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riAccessMode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-access-modes/{id}")
    public ResponseEntity<RiAccessMode> getRiAccessMode(@PathVariable Long id) {
        log.debug("REST request to get RiAccessMode : {}", id);
        Optional<RiAccessMode> riAccessMode = riAccessModeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riAccessMode);
    }

    /**
     * {@code DELETE  /ri-access-modes/:id} : delete the "id" riAccessMode.
     *
     * @param id the id of the riAccessMode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-access-modes/{id}")
    public ResponseEntity<Void> deleteRiAccessMode(@PathVariable Long id) {
        log.debug("REST request to delete RiAccessMode : {}", id);
        riAccessModeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
