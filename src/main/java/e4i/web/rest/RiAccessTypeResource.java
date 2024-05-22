package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiAccessType;
import e4i.repository.RiAccessTypeRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiAccessType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiAccessTypeResource {

    private final Logger log = LoggerFactory.getLogger(RiAccessTypeResource.class);

    private static final String ENTITY_NAME = "riAccessType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiAccessTypeRepository riAccessTypeRepository;

    public RiAccessTypeResource(RiAccessTypeRepository riAccessTypeRepository) {
        this.riAccessTypeRepository = riAccessTypeRepository;
    }

    /**
     * {@code POST  /ri-access-types} : Create a new riAccessType.
     *
     * @param riAccessType the riAccessType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riAccessType, or with status {@code 400 (Bad Request)} if the riAccessType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-access-types")
    public ResponseEntity<RiAccessType> createRiAccessType(@RequestBody RiAccessType riAccessType) throws URISyntaxException {
        log.debug("REST request to save RiAccessType : {}", riAccessType);
        if (riAccessType.getId() != null) {
            throw new BadRequestAlertException("A new riAccessType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiAccessType result = riAccessTypeRepository.save(riAccessType);
        return ResponseEntity.created(new URI("/api/ri-access-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-access-types} : Updates an existing riAccessType.
     *
     * @param riAccessType the riAccessType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riAccessType,
     * or with status {@code 400 (Bad Request)} if the riAccessType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riAccessType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-access-types")
    public ResponseEntity<RiAccessType> updateRiAccessType(@RequestBody RiAccessType riAccessType) throws URISyntaxException {
        log.debug("REST request to update RiAccessType : {}", riAccessType);
        if (riAccessType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiAccessType result = riAccessTypeRepository.save(riAccessType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riAccessType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-access-types} : get all the riAccessTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riAccessTypes in body.
     */
    @GetMapping("/ri-access-types")
    public List<RiAccessType> getAllRiAccessTypes() {
        log.debug("REST request to get all RiAccessTypes");
        return riAccessTypeRepository.findAll();
    }

    /**
     * {@code GET  /ri-access-types/:id} : get the "id" riAccessType.
     *
     * @param id the id of the riAccessType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riAccessType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-access-types/{id}")
    public ResponseEntity<RiAccessType> getRiAccessType(@PathVariable Long id) {
        log.debug("REST request to get RiAccessType : {}", id);
        Optional<RiAccessType> riAccessType = riAccessTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riAccessType);
    }

    /**
     * {@code DELETE  /ri-access-types/:id} : delete the "id" riAccessType.
     *
     * @param id the id of the riAccessType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-access-types/{id}")
    public ResponseEntity<Void> deleteRiAccessType(@PathVariable Long id) {
        log.debug("REST request to delete RiAccessType : {}", id);
        riAccessTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
