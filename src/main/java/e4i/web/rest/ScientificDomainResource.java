package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ScientificDomain;
import e4i.repository.ScientificDomainRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ScientificDomain}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScientificDomainResource {

    private final Logger log = LoggerFactory.getLogger(ScientificDomainResource.class);

    private static final String ENTITY_NAME = "scientificDomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificDomainRepository scientificDomainRepository;

    public ScientificDomainResource(ScientificDomainRepository scientificDomainRepository) {
        this.scientificDomainRepository = scientificDomainRepository;
    }

    /**
     * {@code POST  /scientific-domains} : Create a new scientificDomain.
     *
     * @param scientificDomain the scientificDomain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificDomain, or with status {@code 400 (Bad Request)} if the scientificDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-domains")
    public ResponseEntity<ScientificDomain> createScientificDomain(@RequestBody ScientificDomain scientificDomain) throws URISyntaxException {
        log.debug("REST request to save ScientificDomain : {}", scientificDomain);
        if (scientificDomain.getId() != null) {
            throw new BadRequestAlertException("A new scientificDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificDomain result = scientificDomainRepository.save(scientificDomain);
        return ResponseEntity.created(new URI("/api/scientific-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-domains} : Updates an existing scientificDomain.
     *
     * @param scientificDomain the scientificDomain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificDomain,
     * or with status {@code 400 (Bad Request)} if the scientificDomain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificDomain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-domains")
    public ResponseEntity<ScientificDomain> updateScientificDomain(@RequestBody ScientificDomain scientificDomain) throws URISyntaxException {
        log.debug("REST request to update ScientificDomain : {}", scientificDomain);
        if (scientificDomain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScientificDomain result = scientificDomainRepository.save(scientificDomain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scientificDomain.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scientific-domains} : get all the scientificDomains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificDomains in body.
     */
    @GetMapping("/scientific-domains")
    public List<ScientificDomain> getAllScientificDomains() {
        log.debug("REST request to get all ScientificDomains");
        return scientificDomainRepository.findAll();
    }
    
    @GetMapping("/scientific-domains/search")
    public List<ScientificDomain> getAllScientificDomainsForSearch() {
        log.debug("REST request to get all ScientificDomains");
        return scientificDomainRepository.fetchAllDomainsByJoinFetch();
    }

    /**
     * {@code GET  /scientific-domains/:id} : get the "id" scientificDomain.
     *
     * @param id the id of the scientificDomain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificDomain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-domains/{id}")
    public ResponseEntity<ScientificDomain> getScientificDomain(@PathVariable Long id) {
        log.debug("REST request to get ScientificDomain : {}", id);
        Optional<ScientificDomain> scientificDomain = scientificDomainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scientificDomain);
    }

    /**
     * {@code DELETE  /scientific-domains/:id} : delete the "id" scientificDomain.
     *
     * @param id the id of the scientificDomain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-domains/{id}")
    public ResponseEntity<Void> deleteScientificDomain(@PathVariable Long id) {
        log.debug("REST request to delete ScientificDomain : {}", id);
        scientificDomainRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
