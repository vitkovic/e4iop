package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementSubcategory;
import e4i.service.AdvertisementSubcategoryService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementSubcategory}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementSubcategoryResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSubcategoryResource.class);

    private static final String ENTITY_NAME = "advertisementSubcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementSubcategoryService advertisementSubcategoryService;

    public AdvertisementSubcategoryResource(AdvertisementSubcategoryService advertisementSubcategoryService) {
        this.advertisementSubcategoryService = advertisementSubcategoryService;
    }

    /**
     * {@code POST  /advertisement-subcategories} : Create a new advertisementSubcategory.
     *
     * @param advertisementSubcategory the advertisementSubcategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementSubcategory, or with status {@code 400 (Bad Request)} if the advertisementSubcategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-subcategories")
    public ResponseEntity<AdvertisementSubcategory> createAdvertisementSubcategory(@Valid @RequestBody AdvertisementSubcategory advertisementSubcategory) throws URISyntaxException {
        log.debug("REST request to save AdvertisementSubcategory : {}", advertisementSubcategory);
        if (advertisementSubcategory.getId() != null) {
            throw new BadRequestAlertException("A new advertisementSubcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementSubcategory result = advertisementSubcategoryService.save(advertisementSubcategory);
        return ResponseEntity.created(new URI("/api/advertisement-subcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-subcategories} : Updates an existing advertisementSubcategory.
     *
     * @param advertisementSubcategory the advertisementSubcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementSubcategory,
     * or with status {@code 400 (Bad Request)} if the advertisementSubcategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementSubcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-subcategories")
    public ResponseEntity<AdvertisementSubcategory> updateAdvertisementSubcategory(@Valid @RequestBody AdvertisementSubcategory advertisementSubcategory) throws URISyntaxException {
        log.debug("REST request to update AdvertisementSubcategory : {}", advertisementSubcategory);
        if (advertisementSubcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementSubcategory result = advertisementSubcategoryService.save(advertisementSubcategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementSubcategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-subcategories} : get all the advertisementSubcategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementSubcategories in body.
     */
    @GetMapping("/advertisement-subcategories")
    public List<AdvertisementSubcategory> getAllAdvertisementSubcategories() {
        log.debug("REST request to get all AdvertisementSubcategories");
        return advertisementSubcategoryService.findAll();
    }

    /**
     * {@code GET  /advertisement-subcategories/:id} : get the "id" advertisementSubcategory.
     *
     * @param id the id of the advertisementSubcategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementSubcategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-subcategories/{id}")
    public ResponseEntity<AdvertisementSubcategory> getAdvertisementSubcategory(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementSubcategory : {}", id);
        Optional<AdvertisementSubcategory> advertisementSubcategory = advertisementSubcategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementSubcategory);
    }

    /**
     * {@code DELETE  /advertisement-subcategories/:id} : delete the "id" advertisementSubcategory.
     *
     * @param id the id of the advertisementSubcategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-subcategories/{id}")
    public ResponseEntity<Void> deleteAdvertisementSubcategory(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementSubcategory : {}", id);
        advertisementSubcategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
