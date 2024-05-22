package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementCategory;
import e4i.service.AdvertisementCategoryService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementCategory}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementCategoryResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementCategoryResource.class);

    private static final String ENTITY_NAME = "advertisementCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementCategoryService advertisementCategoryService;

    public AdvertisementCategoryResource(AdvertisementCategoryService advertisementCategoryService) {
        this.advertisementCategoryService = advertisementCategoryService;
    }

    /**
     * {@code POST  /advertisement-categories} : Create a new advertisementCategory.
     *
     * @param advertisementCategory the advertisementCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementCategory, or with status {@code 400 (Bad Request)} if the advertisementCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-categories")
    public ResponseEntity<AdvertisementCategory> createAdvertisementCategory(@Valid @RequestBody AdvertisementCategory advertisementCategory) throws URISyntaxException {
        log.debug("REST request to save AdvertisementCategory : {}", advertisementCategory);
        if (advertisementCategory.getId() != null) {
            throw new BadRequestAlertException("A new advertisementCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementCategory result = advertisementCategoryService.save(advertisementCategory);
        return ResponseEntity.created(new URI("/api/advertisement-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-categories} : Updates an existing advertisementCategory.
     *
     * @param advertisementCategory the advertisementCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementCategory,
     * or with status {@code 400 (Bad Request)} if the advertisementCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-categories")
    public ResponseEntity<AdvertisementCategory> updateAdvertisementCategory(@Valid @RequestBody AdvertisementCategory advertisementCategory) throws URISyntaxException {
        log.debug("REST request to update AdvertisementCategory : {}", advertisementCategory);
        if (advertisementCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementCategory result = advertisementCategoryService.save(advertisementCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-categories} : get all the advertisementCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementCategories in body.
     */
    @GetMapping("/advertisement-categories")
    public List<AdvertisementCategory> getAllAdvertisementCategories() {
        log.debug("REST request to get all AdvertisementCategories");
        return advertisementCategoryService.findAll();
    }

    /**
     * {@code GET  /advertisement-categories/:id} : get the "id" advertisementCategory.
     *
     * @param id the id of the advertisementCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-categories/{id}")
    public ResponseEntity<AdvertisementCategory> getAdvertisementCategory(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementCategory : {}", id);
        Optional<AdvertisementCategory> advertisementCategory = advertisementCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementCategory);
    }

    /**
     * {@code DELETE  /advertisement-categories/:id} : delete the "id" advertisementCategory.
     *
     * @param id the id of the advertisementCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-categories/{id}")
    public ResponseEntity<Void> deleteAdvertisementCategory(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementCategory : {}", id);
        advertisementCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
