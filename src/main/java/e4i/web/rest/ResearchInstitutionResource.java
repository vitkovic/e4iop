package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.ResearchInstitution;
import e4i.repository.ResearchInstitutionRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.io.Console;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link e4i.domain.ResearchInstitution}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResearchInstitutionResource {

    private final Logger log = LoggerFactory.getLogger(ResearchInstitutionResource.class);

    private static final String ENTITY_NAME = "researchInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResearchInstitutionRepository researchInstitutionRepository;

    public ResearchInstitutionResource(ResearchInstitutionRepository researchInstitutionRepository) {
        this.researchInstitutionRepository = researchInstitutionRepository;
    }

    /**
     * {@code POST  /research-institutions} : Create a new researchInstitution.
     *
     * @param researchInstitution the researchInstitution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new researchInstitution, or with status {@code 400 (Bad Request)} if the researchInstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/research-institutions")
    public ResponseEntity<ResearchInstitution> createResearchInstitution(@RequestBody ResearchInstitution researchInstitution) throws URISyntaxException {
        log.debug("REST request to save ResearchInstitution : {}", researchInstitution);
        if (researchInstitution.getId() != null) {
            throw new BadRequestAlertException("A new researchInstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResearchInstitution result = researchInstitutionRepository.save(researchInstitution);
        return ResponseEntity.created(new URI("/api/research-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /research-institutions} : Updates an existing researchInstitution.
     *
     * @param researchInstitution the researchInstitution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated researchInstitution,
     * or with status {@code 400 (Bad Request)} if the researchInstitution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the researchInstitution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/research-institutions")
    public ResponseEntity<ResearchInstitution> updateResearchInstitution(@RequestBody ResearchInstitution researchInstitution) throws URISyntaxException {
        log.debug("REST request to update ResearchInstitution : {}", researchInstitution);
        if (researchInstitution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResearchInstitution result = researchInstitutionRepository.save(researchInstitution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, researchInstitution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /research-institutions} : get all the researchInstitutions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of researchInstitutions in body.
     */
    @GetMapping("/research-institutions")
    public List<ResearchInstitution> getAllResearchInstitutions(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
       /* if ("ripo-is-null".equals(filter)) {
            log.debug("REST request to get all ResearchInstitutions where ripo is null");
            return StreamSupport
                .stream(researchInstitutionRepository.findAll().spliterator(), false)
                .filter(researchInstitution -> researchInstitution.getRipo() == null)
                .collect(Collectors.toList());
        }
        */
        log.debug("REST request to get all ResearchInstitutions");
        return researchInstitutionRepository.findAllWithEagerRelationships();
    }
    
    
    /** Dodao Misic vadi sve institucije za padajuce liste, ali uredjene prema jeziku 
     * 
     * @param lang
     * @return
     */
    @GetMapping("/research-institutions-ordered/{lang}")
    public List<ResearchInstitution> findAllResearchInstitutions(@PathVariable String lang) {
        log.debug("REST request to get all ResearchInstitutions");
        if(lang.equals("sr"))
        	return researchInstitutionRepository.findByOrderByNameAsc();
        else if(lang.equals("src"))
        	return researchInstitutionRepository.findByOrderByNameCyrAsc();
        else
        	return researchInstitutionRepository.findByOrderByNameEnAsc();
    }

    /**
     * {@code GET  /research-institutions/:id} : get the "id" researchInstitution.
     *
     * @param id the id of the researchInstitution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInstitution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-institutions/{id}")
    public ResponseEntity<ResearchInstitution> getResearchInstitution(@PathVariable Long id) {
        log.debug("REST request to get ResearchInstitution : {}", id);
        Optional<ResearchInstitution> researchInstitution = researchInstitutionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(researchInstitution);
    }
    
    /**
     * {@code DELETE  /research-institutions/:id} : delete the "id" researchInstitution.
     *
     * @param id the id of the researchInstitution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/research-institutions/{id}")
    public ResponseEntity<Void> deleteResearchInstitution(@PathVariable Long id) {
        log.debug("REST request to delete ResearchInstitution : {}", id);
        researchInstitutionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
