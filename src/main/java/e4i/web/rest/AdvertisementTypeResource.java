package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementType;
import e4i.service.AdvertisementTypeService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementType}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementTypeResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementTypeResource.class);

    private static final String ENTITY_NAME = "advertisementType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementTypeService advertisementTypeService;

    public AdvertisementTypeResource(AdvertisementTypeService advertisementTypeService) {
        this.advertisementTypeService = advertisementTypeService;
    }

    /**
     * {@code POST  /advertisement-types} : Create a new advertisementType.
     *
     * @param advertisementType the advertisementType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementType, or with status {@code 400 (Bad Request)} if the advertisementType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-types")
    public ResponseEntity<AdvertisementType> createAdvertisementType(@Valid @RequestBody AdvertisementType advertisementType) throws URISyntaxException {
        log.debug("REST request to save AdvertisementType : {}", advertisementType);
        if (advertisementType.getId() != null) {
            throw new BadRequestAlertException("A new advertisementType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementType result = advertisementTypeService.save(advertisementType);
        return ResponseEntity.created(new URI("/api/advertisement-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-types} : Updates an existing advertisementType.
     *
     * @param advertisementType the advertisementType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementType,
     * or with status {@code 400 (Bad Request)} if the advertisementType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-types")
    public ResponseEntity<AdvertisementType> updateAdvertisementType(@Valid @RequestBody AdvertisementType advertisementType) throws URISyntaxException {
        log.debug("REST request to update AdvertisementType : {}", advertisementType);
        if (advertisementType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementType result = advertisementTypeService.save(advertisementType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-types} : get all the advertisementTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementTypes in body.
     */
    @GetMapping("/advertisement-types")
    public List<AdvertisementType> getAllAdvertisementTypes() {
        log.debug("REST request to get all AdvertisementTypes");
        return advertisementTypeService.findAll();
    }

    /**
     * {@code GET  /advertisement-types/:id} : get the "id" advertisementType.
     *
     * @param id the id of the advertisementType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-types/{id}")
    public ResponseEntity<AdvertisementType> getAdvertisementType(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementType : {}", id);
        Optional<AdvertisementType> advertisementType = advertisementTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementType);
    }

    /**
     * {@code DELETE  /advertisement-types/:id} : delete the "id" advertisementType.
     *
     * @param id the id of the advertisementType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-types/{id}")
    public ResponseEntity<Void> deleteAdvertisementType(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementType : {}", id);
        advertisementTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
