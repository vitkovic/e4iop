package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiCategory;
import e4i.repository.RiCategoryRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiCategoryResource {

    private final Logger log = LoggerFactory.getLogger(RiCategoryResource.class);

    private static final String ENTITY_NAME = "riCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiCategoryRepository riCategoryRepository;

    public RiCategoryResource(RiCategoryRepository riCategoryRepository) {
        this.riCategoryRepository = riCategoryRepository;
    }

    /**
     * {@code POST  /ri-categories} : Create a new riCategory.
     *
     * @param riCategory the riCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riCategory, or with status {@code 400 (Bad Request)} if the riCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-categories")
    public ResponseEntity<RiCategory> createRiCategory(@RequestBody RiCategory riCategory) throws URISyntaxException {
        log.debug("REST request to save RiCategory : {}", riCategory);
        if (riCategory.getId() != null) {
            throw new BadRequestAlertException("A new riCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiCategory result = riCategoryRepository.save(riCategory);
        return ResponseEntity.created(new URI("/api/ri-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-categories} : Updates an existing riCategory.
     *
     * @param riCategory the riCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riCategory,
     * or with status {@code 400 (Bad Request)} if the riCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-categories")
    public ResponseEntity<RiCategory> updateRiCategory(@RequestBody RiCategory riCategory) throws URISyntaxException {
        log.debug("REST request to update RiCategory : {}", riCategory);
        if (riCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiCategory result = riCategoryRepository.save(riCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-categories} : get all the riCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riCategories in body.
     */
    @GetMapping("/ri-categories")
    public List<RiCategory> getAllRiCategories() {
        log.debug("REST request to get all RiCategories");
        return riCategoryRepository.findAll();
    }

    /**
     * {@code GET  /ri-categories/:id} : get the "id" riCategory.
     *
     * @param id the id of the riCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-categories/{id}")
    public ResponseEntity<RiCategory> getRiCategory(@PathVariable Long id) {
        log.debug("REST request to get RiCategory : {}", id);
        Optional<RiCategory> riCategory = riCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riCategory);
    }

    /**
     * {@code DELETE  /ri-categories/:id} : delete the "id" riCategory.
     *
     * @param id the id of the riCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-categories/{id}")
    public ResponseEntity<Void> deleteRiCategory(@PathVariable Long id) {
        log.debug("REST request to delete RiCategory : {}", id);
        riCategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
