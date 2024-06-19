package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RiFinanceSource; // Adjust import to use RiFinanceSource
import e4i.repository.RiFinanceSourceRepository; // Adjust import to use RiFinanceSourceRepository
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiFinanceSource}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiFinanceSourceResource {

    private final Logger log = LoggerFactory.getLogger(RiFinanceSourceResource.class);

    private static final String ENTITY_NAME = "riFinanceSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiFinanceSourceRepository riFinanceSourceRepository;

    public RiFinanceSourceResource(RiFinanceSourceRepository riFinanceSourceRepository) {
        this.riFinanceSourceRepository = riFinanceSourceRepository;
    }

    /**
     * {@code POST  /ri-finance-sources} : Create a new riFinanceSource.
     *
     * @param riFinanceSource the riFinanceSource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riFinanceSource, or with status {@code 400 (Bad Request)} if the riFinanceSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-finance-sources")
    public ResponseEntity<RiFinanceSource> createRiFinanceSource(@RequestBody RiFinanceSource riFinanceSource) throws URISyntaxException {
        log.debug("REST request to save RiFinanceSource : {}", riFinanceSource);
        if (riFinanceSource.getId() != null) {
            throw new BadRequestAlertException("A new riFinanceSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiFinanceSource result = riFinanceSourceRepository.save(riFinanceSource);
        return ResponseEntity.created(new URI("/api/ri-finance-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-finance-sources} : Updates an existing riFinanceSource.
     *
     * @param riFinanceSource the riFinanceSource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riFinanceSource,
     * or with status {@code 400 (Bad Request)} if the riFinanceSource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riFinanceSource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-finance-sources")
    public ResponseEntity<RiFinanceSource> updateRiFinanceSource(@RequestBody RiFinanceSource riFinanceSource) throws URISyntaxException {
        log.debug("REST request to update RiFinanceSource : {}", riFinanceSource);
        if (riFinanceSource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiFinanceSource result = riFinanceSourceRepository.save(riFinanceSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riFinanceSource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-finance-sources} : get all the riFinanceSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riFinanceSources in body.
     */
    @GetMapping("/ri-finance-sources")
    public List<RiFinanceSource> getAllRiFinanceSources() {
        log.debug("REST request to get all RiFinanceSources");
        return riFinanceSourceRepository.findAll();
    }

    /**
     * {@code GET  /ri-finance-sources/:id} : get the "id" riFinanceSource.
     *
     * @param id the id of the riFinanceSource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riFinanceSource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-finance-sources/{id}")
    public ResponseEntity<RiFinanceSource> getRiFinanceSource(@PathVariable Long id) {
        log.debug("REST request to get RiFinanceSource : {}", id);
        Optional<RiFinanceSource> riFinanceSource = riFinanceSourceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riFinanceSource);
    }

    /**
     * {@code DELETE  /ri-finance-sources/:id} : delete the "id" riFinanceSource.
     *
     * @param id the id of the riFinanceSource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-finance-sources/{id}")
    public ResponseEntity<Void> deleteRiFinanceSource(@PathVariable Long id) {
        log.debug("REST request to delete RiFinanceSource : {}", id);
        riFinanceSourceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
