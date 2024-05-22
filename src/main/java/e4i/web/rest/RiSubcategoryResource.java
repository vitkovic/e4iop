package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiSubcategory;
import e4i.repository.RiSubcategoryRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiSubcategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiSubcategoryResource {

    private final Logger log = LoggerFactory.getLogger(RiSubcategoryResource.class);

    private static final String ENTITY_NAME = "riSubcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiSubcategoryRepository riSubcategoryRepository;

    public RiSubcategoryResource(RiSubcategoryRepository riSubcategoryRepository) {
        this.riSubcategoryRepository = riSubcategoryRepository;
    }

    /**
     * {@code POST  /ri-subcategories} : Create a new riSubcategory.
     *
     * @param riSubcategory the riSubcategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riSubcategory, or with status {@code 400 (Bad Request)} if the riSubcategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-subcategories")
    public ResponseEntity<RiSubcategory> createRiSubcategory(@RequestBody RiSubcategory riSubcategory) throws URISyntaxException {
        log.debug("REST request to save RiSubcategory : {}", riSubcategory);
        if (riSubcategory.getId() != null) {
            throw new BadRequestAlertException("A new riSubcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiSubcategory result = riSubcategoryRepository.save(riSubcategory);
        return ResponseEntity.created(new URI("/api/ri-subcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-subcategories} : Updates an existing riSubcategory.
     *
     * @param riSubcategory the riSubcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riSubcategory,
     * or with status {@code 400 (Bad Request)} if the riSubcategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riSubcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-subcategories")
    public ResponseEntity<RiSubcategory> updateRiSubcategory(@RequestBody RiSubcategory riSubcategory) throws URISyntaxException {
        log.debug("REST request to update RiSubcategory : {}", riSubcategory);
        if (riSubcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiSubcategory result = riSubcategoryRepository.save(riSubcategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riSubcategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-subcategories} : get all the riSubcategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riSubcategories in body.
     */
    @GetMapping("/ri-subcategories")
    public List<RiSubcategory> getAllRiSubcategories() {
        log.debug("REST request to get all RiSubcategories");
        return riSubcategoryRepository.findAll();
    }

    /**
     * {@code GET  /ri-subcategories/:id} : get the "id" riSubcategory.
     *
     * @param id the id of the riSubcategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riSubcategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-subcategories/{id}")
    public ResponseEntity<RiSubcategory> getRiSubcategory(@PathVariable Long id) {
        log.debug("REST request to get RiSubcategory : {}", id);
        Optional<RiSubcategory> riSubcategory = riSubcategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riSubcategory);
    }

    /**
     * {@code DELETE  /ri-subcategories/:id} : delete the "id" riSubcategory.
     *
     * @param id the id of the riSubcategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-subcategories/{id}")
    public ResponseEntity<Void> deleteRiSubcategory(@PathVariable Long id) {
        log.debug("REST request to delete RiSubcategory : {}", id);
        riSubcategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
