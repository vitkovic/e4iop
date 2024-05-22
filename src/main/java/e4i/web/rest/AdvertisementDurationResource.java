package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementDuration;
import e4i.service.AdvertisementDurationService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementDuration}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementDurationResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementDurationResource.class);

    private static final String ENTITY_NAME = "advertisementDuration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementDurationService advertisementDurationService;

    public AdvertisementDurationResource(AdvertisementDurationService advertisementDurationService) {
        this.advertisementDurationService = advertisementDurationService;
    }

    /**
     * {@code POST  /advertisement-durations} : Create a new advertisementDuration.
     *
     * @param advertisementDuration the advertisementDuration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementDuration, or with status {@code 400 (Bad Request)} if the advertisementDuration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-durations")
    public ResponseEntity<AdvertisementDuration> createAdvertisementDuration(@Valid @RequestBody AdvertisementDuration advertisementDuration) throws URISyntaxException {
        log.debug("REST request to save AdvertisementDuration : {}", advertisementDuration);
        if (advertisementDuration.getId() != null) {
            throw new BadRequestAlertException("A new advertisementDuration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementDuration result = advertisementDurationService.save(advertisementDuration);
        return ResponseEntity.created(new URI("/api/advertisement-durations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-durations} : Updates an existing advertisementDuration.
     *
     * @param advertisementDuration the advertisementDuration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementDuration,
     * or with status {@code 400 (Bad Request)} if the advertisementDuration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementDuration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-durations")
    public ResponseEntity<AdvertisementDuration> updateAdvertisementDuration(@Valid @RequestBody AdvertisementDuration advertisementDuration) throws URISyntaxException {
        log.debug("REST request to update AdvertisementDuration : {}", advertisementDuration);
        if (advertisementDuration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementDuration result = advertisementDurationService.save(advertisementDuration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementDuration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-durations} : get all the advertisementDurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementDurations in body.
     */
    @GetMapping("/advertisement-durations")
    public List<AdvertisementDuration> getAllAdvertisementDurations() {
        log.debug("REST request to get all AdvertisementDurations");
        return advertisementDurationService.findAll();
    }

    /**
     * {@code GET  /advertisement-durations/:id} : get the "id" advertisementDuration.
     *
     * @param id the id of the advertisementDuration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementDuration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-durations/{id}")
    public ResponseEntity<AdvertisementDuration> getAdvertisementDuration(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementDuration : {}", id);
        Optional<AdvertisementDuration> advertisementDuration = advertisementDurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementDuration);
    }

    /**
     * {@code DELETE  /advertisement-durations/:id} : delete the "id" advertisementDuration.
     *
     * @param id the id of the advertisementDuration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-durations/{id}")
    public ResponseEntity<Void> deleteAdvertisementDuration(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementDuration : {}", id);
        advertisementDurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
