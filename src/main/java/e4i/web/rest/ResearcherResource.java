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
import e4i.domain.Researcher;
import e4i.repository.ResearcherRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link e4i.domain.Researcher}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResearcherResource {

    private final Logger log = LoggerFactory.getLogger(ResearcherResource.class);

    private static final String ENTITY_NAME = "researcher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResearcherRepository researcherRepository;

    public ResearcherResource(ResearcherRepository researcherRepository) {
        this.researcherRepository = researcherRepository;
    }

    /**
     * {@code POST  /researchers} : Create a new researcher.
     *
     * @param researcher the researcher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new researcher, or with status {@code 400 (Bad Request)} if the researcher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/researchers")
    public ResponseEntity<Researcher> createResearcher(@RequestBody Researcher researcher) throws URISyntaxException {
        log.debug("REST request to save Researcher : {}", researcher);
        if (researcher.getId() != null) {
            throw new BadRequestAlertException("A new researcher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Researcher result = researcherRepository.save(researcher);
        return ResponseEntity.created(new URI("/api/researchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /researchers} : Updates an existing researcher.
     *
     * @param researcher the researcher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated researcher,
     * or with status {@code 400 (Bad Request)} if the researcher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the researcher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/researchers")
    public ResponseEntity<Researcher> updateResearcher(@RequestBody Researcher researcher) throws URISyntaxException {
        log.debug("REST request to update Researcher : {}", researcher);
        if (researcher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Researcher result = researcherRepository.save(researcher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, researcher.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /researchers} : get all the researchers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of researchers in body.
     */
    @GetMapping("/researchers")
    public List<Researcher> getAllResearchers(@RequestParam(required = false) String filter) {
        if ("portaluser-is-null".equals(filter)) {
            log.debug("REST request to get all Researchers where portalUser is null");
            return StreamSupport
                .stream(researcherRepository.findAll().spliterator(), false)
                .filter(researcher -> researcher.getPortalUser() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Researchers");
        return researcherRepository.findAll();
    }

    /**
     * {@code GET  /researchers/:id} : get the "id" researcher.
     *
     * @param id the id of the researcher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researcher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/researchers/{id}")
    public ResponseEntity<Researcher> getResearcher(@PathVariable Long id) {
        log.debug("REST request to get Researcher : {}", id);
        Optional<Researcher> researcher = researcherRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(researcher);
    }

    /**
     * {@code DELETE  /researchers/:id} : delete the "id" researcher.
     *
     * @param id the id of the researcher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/researchers/{id}")
    public ResponseEntity<Void> deleteResearcher(@PathVariable Long id) {
        log.debug("REST request to delete Researcher : {}", id);
        researcherRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /** Dodao Vitko vadi sve institucije za padajuce liste, ali uredjene prema jeziku 
     * 
     * @param lang
     * @return
     */
    @GetMapping("/researchers-ordered/{lang}/{instid}")
    public List<Researcher> findAllResearchInstitutions(@PathVariable String lang, @PathVariable Long instid) {
        log.debug("REST request to get all ResearchInstitutions");
        if(lang.equals("sr")) {
        	List<Researcher> ris =  researcherRepository.findResearchersForInstitutionsAsc(instid);
        	return ris;
        } else if(lang.equals("src"))
        	return researcherRepository.findResearchersForInstitutionsAsc(instid);
        else
        	return researcherRepository.findResearchersForInstitutionsAsc(instid);
    }
    
    
    
}
