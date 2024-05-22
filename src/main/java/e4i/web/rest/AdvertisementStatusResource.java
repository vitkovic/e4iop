package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementStatus;
import e4i.service.AdvertisementStatusService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementStatus}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementStatusResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementStatusResource.class);

    private static final String ENTITY_NAME = "advertisementStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementStatusService advertisementStatusService;

    public AdvertisementStatusResource(AdvertisementStatusService advertisementStatusService) {
        this.advertisementStatusService = advertisementStatusService;
    }

    /**
     * {@code POST  /advertisement-statuses} : Create a new advertisementStatus.
     *
     * @param advertisementStatus the advertisementStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementStatus, or with status {@code 400 (Bad Request)} if the advertisementStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-statuses")
    public ResponseEntity<AdvertisementStatus> createAdvertisementStatus(@Valid @RequestBody AdvertisementStatus advertisementStatus) throws URISyntaxException {
        log.debug("REST request to save AdvertisementStatus : {}", advertisementStatus);
        if (advertisementStatus.getId() != null) {
            throw new BadRequestAlertException("A new advertisementStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementStatus result = advertisementStatusService.save(advertisementStatus);
        return ResponseEntity.created(new URI("/api/advertisement-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-statuses} : Updates an existing advertisementStatus.
     *
     * @param advertisementStatus the advertisementStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementStatus,
     * or with status {@code 400 (Bad Request)} if the advertisementStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-statuses")
    public ResponseEntity<AdvertisementStatus> updateAdvertisementStatus(@Valid @RequestBody AdvertisementStatus advertisementStatus) throws URISyntaxException {
        log.debug("REST request to update AdvertisementStatus : {}", advertisementStatus);
        if (advertisementStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementStatus result = advertisementStatusService.save(advertisementStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-statuses} : get all the advertisementStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementStatuses in body.
     */
    @GetMapping("/advertisement-statuses")
    public List<AdvertisementStatus> getAllAdvertisementStatuses() {
        log.debug("REST request to get all AdvertisementStatuses");
        return advertisementStatusService.findAll();
    }

    /**
     * {@code GET  /advertisement-statuses/:id} : get the "id" advertisementStatus.
     *
     * @param id the id of the advertisementStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-statuses/{id}")
    public ResponseEntity<AdvertisementStatus> getAdvertisementStatus(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementStatus : {}", id);
        Optional<AdvertisementStatus> advertisementStatus = advertisementStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementStatus);
    }

    /**
     * {@code DELETE  /advertisement-statuses/:id} : delete the "id" advertisementStatus.
     *
     * @param id the id of the advertisementStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-statuses/{id}")
    public ResponseEntity<Void> deleteAdvertisementStatus(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementStatus : {}", id);
        advertisementStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
