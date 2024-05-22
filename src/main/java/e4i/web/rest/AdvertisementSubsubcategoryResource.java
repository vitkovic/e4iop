package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementSubsubcategory;
import e4i.service.AdvertisementSubsubcategoryService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementSubsubcategory}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementSubsubcategoryResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSubsubcategoryResource.class);

    private static final String ENTITY_NAME = "advertisementSubsubcategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementSubsubcategoryService advertisementSubsubcategoryService;

    public AdvertisementSubsubcategoryResource(AdvertisementSubsubcategoryService advertisementSubsubcategoryService) {
        this.advertisementSubsubcategoryService = advertisementSubsubcategoryService;
    }

    /**
     * {@code POST  /advertisement-subsubcategories} : Create a new advertisementSubsubcategory.
     *
     * @param advertisementSubsubcategory the advertisementSubsubcategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementSubsubcategory, or with status {@code 400 (Bad Request)} if the advertisementSubsubcategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-subsubcategories")
    public ResponseEntity<AdvertisementSubsubcategory> createAdvertisementSubsubcategory(@Valid @RequestBody AdvertisementSubsubcategory advertisementSubsubcategory) throws URISyntaxException {
        log.debug("REST request to save AdvertisementSubsubcategory : {}", advertisementSubsubcategory);
        if (advertisementSubsubcategory.getId() != null) {
            throw new BadRequestAlertException("A new advertisementSubsubcategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementSubsubcategory result = advertisementSubsubcategoryService.save(advertisementSubsubcategory);
        return ResponseEntity.created(new URI("/api/advertisement-subsubcategories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-subsubcategories} : Updates an existing advertisementSubsubcategory.
     *
     * @param advertisementSubsubcategory the advertisementSubsubcategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementSubsubcategory,
     * or with status {@code 400 (Bad Request)} if the advertisementSubsubcategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementSubsubcategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-subsubcategories")
    public ResponseEntity<AdvertisementSubsubcategory> updateAdvertisementSubsubcategory(@Valid @RequestBody AdvertisementSubsubcategory advertisementSubsubcategory) throws URISyntaxException {
        log.debug("REST request to update AdvertisementSubsubcategory : {}", advertisementSubsubcategory);
        if (advertisementSubsubcategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementSubsubcategory result = advertisementSubsubcategoryService.save(advertisementSubsubcategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementSubsubcategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-subsubcategories} : get all the advertisementSubsubcategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementSubsubcategories in body.
     */
    @GetMapping("/advertisement-subsubcategories")
    public List<AdvertisementSubsubcategory> getAllAdvertisementSubsubcategories() {
        log.debug("REST request to get all AdvertisementSubsubcategories");
        return advertisementSubsubcategoryService.findAll();
    }

    /**
     * {@code GET  /advertisement-subsubcategories/:id} : get the "id" advertisementSubsubcategory.
     *
     * @param id the id of the advertisementSubsubcategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementSubsubcategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-subsubcategories/{id}")
    public ResponseEntity<AdvertisementSubsubcategory> getAdvertisementSubsubcategory(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementSubsubcategory : {}", id);
        Optional<AdvertisementSubsubcategory> advertisementSubsubcategory = advertisementSubsubcategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementSubsubcategory);
    }

    /**
     * {@code DELETE  /advertisement-subsubcategories/:id} : delete the "id" advertisementSubsubcategory.
     *
     * @param id the id of the advertisementSubsubcategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-subsubcategories/{id}")
    public ResponseEntity<Void> deleteAdvertisementSubsubcategory(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementSubsubcategory : {}", id);
        advertisementSubsubcategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
