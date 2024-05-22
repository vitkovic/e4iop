package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AdvertisementKind;
import e4i.service.AdvertisementKindService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AdvertisementKind}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementKindResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementKindResource.class);

    private static final String ENTITY_NAME = "advertisementKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementKindService advertisementKindService;

    public AdvertisementKindResource(AdvertisementKindService advertisementKindService) {
        this.advertisementKindService = advertisementKindService;
    }

    /**
     * {@code POST  /advertisement-kinds} : Create a new advertisementKind.
     *
     * @param advertisementKind the advertisementKind to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisementKind, or with status {@code 400 (Bad Request)} if the advertisementKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisement-kinds")
    public ResponseEntity<AdvertisementKind> createAdvertisementKind(@Valid @RequestBody AdvertisementKind advertisementKind) throws URISyntaxException {
        log.debug("REST request to save AdvertisementKind : {}", advertisementKind);
        if (advertisementKind.getId() != null) {
            throw new BadRequestAlertException("A new advertisementKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvertisementKind result = advertisementKindService.save(advertisementKind);
        return ResponseEntity.created(new URI("/api/advertisement-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisement-kinds} : Updates an existing advertisementKind.
     *
     * @param advertisementKind the advertisementKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisementKind,
     * or with status {@code 400 (Bad Request)} if the advertisementKind is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisementKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisement-kinds")
    public ResponseEntity<AdvertisementKind> updateAdvertisementKind(@Valid @RequestBody AdvertisementKind advertisementKind) throws URISyntaxException {
        log.debug("REST request to update AdvertisementKind : {}", advertisementKind);
        if (advertisementKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvertisementKind result = advertisementKindService.save(advertisementKind);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisementKind.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisement-kinds} : get all the advertisementKinds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisementKinds in body.
     */
    @GetMapping("/advertisement-kinds")
    public List<AdvertisementKind> getAllAdvertisementKinds() {
        log.debug("REST request to get all AdvertisementKinds");
        return advertisementKindService.findAll();
    }

    /**
     * {@code GET  /advertisement-kinds/:id} : get the "id" advertisementKind.
     *
     * @param id the id of the advertisementKind to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisementKind, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisement-kinds/{id}")
    public ResponseEntity<AdvertisementKind> getAdvertisementKind(@PathVariable Long id) {
        log.debug("REST request to get AdvertisementKind : {}", id);
        Optional<AdvertisementKind> advertisementKind = advertisementKindService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisementKind);
    }

    /**
     * {@code DELETE  /advertisement-kinds/:id} : delete the "id" advertisementKind.
     *
     * @param id the id of the advertisementKind to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisement-kinds/{id}")
    public ResponseEntity<Void> deleteAdvertisementKind(@PathVariable Long id) {
        log.debug("REST request to delete AdvertisementKind : {}", id);
        advertisementKindService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
