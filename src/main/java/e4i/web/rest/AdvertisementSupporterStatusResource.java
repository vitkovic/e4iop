package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementSupporterStatus;
import e4i.service.AdvertisementSupporterStatusService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementSupporterStatus}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementSupporterStatusResource {
    private final Logger log = LoggerFactory.getLogger(AdvertisementSupporterStatusResource.class);

    private static final String ENTITY_NAME = "advertisementSupporterStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementSupporterStatusService advertisementSupporterStatusService;

    public AdvertisementSupporterStatusResource(AdvertisementSupporterStatusService advertisementSupporterStatusService) {
        this.advertisementSupporterStatusService = advertisementSupporterStatusService;
    }

    @PostMapping("/advertisement-supporter-statuses")
    public ResponseEntity<AdvertisementSupporterStatus> createAdvertisementSupporterStatus(@Valid @RequestBody AdvertisementSupporterStatus advertisementSupporterStatus) throws URISyntaxException {
        log.debug("REST request to save AdvertisementSupporterStatus : {}", advertisementSupporterStatus);
        if (advertisementSupporterStatus.getId() != null) {
            throw new BadRequestAlertException("A new advertisementSupporterStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementSupporterStatus result = advertisementSupporterStatusService.save(advertisementSupporterStatus);
        return ResponseEntity.created(new URI("/api/advertisement-supporter-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/advertisement-supporter-statuses")
    public ResponseEntity<AdvertisementSupporterStatus> updateAdvertisementSupporterStatus(@Valid @RequestBody AdvertisementSupporterStatus advertisementSupporterStatus) throws URISyntaxException {
        log.debug("REST request to update AdvertisementSupporterStatus : {}", advertisementSupporterStatus);
        if (advertisementSupporterStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementSupporterStatus result = advertisementSupporterStatusService.save(advertisementSupporterStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementSupporterStatus.getId().toString()))
            .body(result);
    }

    @GetMapping("/advertisement-supporter-statuses")
    public List<AdvertisementSupporterStatus> getAllAdvertisementSupporterStatuses() {
        log.debug("REST request to get all AdvertisementSupporterStatuses");
        return advertisementSupporterStatusService.findAll();
    }

    @GetMapping("/advertisement-supporter-statuses/{id}")
    public ResponseEntity<AdvertisementSupporterStatus> getAdvertisementSupporterStatus(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementSupporterStatus : {}", id);
        Optional<AdvertisementSupporterStatus> advertisementSupporterStatus = advertisementSupporterStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementSupporterStatus);
    }

    @DeleteMapping("/advertisement-supporter-statuses/{id}")
    public ResponseEntity<Void> deleteAdvertisementSupporterStatus(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementSupporterStatus : {}", id);
        advertisementSupporterStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
