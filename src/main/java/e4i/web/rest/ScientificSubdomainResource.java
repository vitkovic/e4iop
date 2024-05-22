package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ScientificSubdomain;
import e4i.repository.ScientificSubdomainRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ScientificSubdomain}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScientificSubdomainResource {

    private final Logger log = LoggerFactory.getLogger(ScientificSubdomainResource.class);

    private static final String ENTITY_NAME = "scientificSubdomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificSubdomainRepository scientificSubdomainRepository;

    public ScientificSubdomainResource(ScientificSubdomainRepository scientificSubdomainRepository) {
        this.scientificSubdomainRepository = scientificSubdomainRepository;
    }

    /**
     * {@code POST  /scientific-subdomains} : Create a new scientificSubdomain.
     *
     * @param scientificSubdomain the scientificSubdomain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificSubdomain, or with status {@code 400 (Bad Request)} if the scientificSubdomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-subdomains")
    public ResponseEntity<ScientificSubdomain> createScientificSubdomain(@RequestBody ScientificSubdomain scientificSubdomain) throws URISyntaxException {
        log.debug("REST request to save ScientificSubdomain : {}", scientificSubdomain);
        if (scientificSubdomain.getId() != null) {
            throw new BadRequestAlertException("A new scientificSubdomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificSubdomain result = scientificSubdomainRepository.save(scientificSubdomain);
        return ResponseEntity.created(new URI("/api/scientific-subdomains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-subdomains} : Updates an existing scientificSubdomain.
     *
     * @param scientificSubdomain the scientificSubdomain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificSubdomain,
     * or with status {@code 400 (Bad Request)} if the scientificSubdomain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificSubdomain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-subdomains")
    public ResponseEntity<ScientificSubdomain> updateScientificSubdomain(@RequestBody ScientificSubdomain scientificSubdomain) throws URISyntaxException {
        log.debug("REST request to update ScientificSubdomain : {}", scientificSubdomain);
        if (scientificSubdomain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScientificSubdomain result = scientificSubdomainRepository.save(scientificSubdomain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scientificSubdomain.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scientific-subdomains} : get all the scientificSubdomains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificSubdomains in body.
     */
    @GetMapping("/scientific-subdomains")
    public List<ScientificSubdomain> getAllScientificSubdomains() {
        log.debug("REST request to get all ScientificSubdomains");
        return scientificSubdomainRepository.findAll();
    }

    /**
     * {@code GET  /scientific-subdomains/:id} : get the "id" scientificSubdomain.
     *
     * @param id the id of the scientificSubdomain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificSubdomain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-subdomains/{id}")
    public ResponseEntity<ScientificSubdomain> getScientificSubdomain(@PathVariable Long id) {
        log.debug("REST request to get ScientificSubdomain : {}", id);
        Optional<ScientificSubdomain> scientificSubdomain = scientificSubdomainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scientificSubdomain);
    }

    /**
     * {@code DELETE  /scientific-subdomains/:id} : delete the "id" scientificSubdomain.
     *
     * @param id the id of the scientificSubdomain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-subdomains/{id}")
    public ResponseEntity<Void> deleteScientificSubdomain(@PathVariable Long id) {
        log.debug("REST request to delete ScientificSubdomain : {}", id);
        scientificSubdomainRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
