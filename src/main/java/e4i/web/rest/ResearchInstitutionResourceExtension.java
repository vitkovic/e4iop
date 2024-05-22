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
import e4i.repository.ResearchInstitutionRepositoryExtension;
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
public class ResearchInstitutionResourceExtension {

    private final Logger log = LoggerFactory.getLogger(ResearchInstitutionResourceExtension.class);

   // private static final String ENTITY_NAME = "researchInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResearchInstitutionRepositoryExtension researchInstitutionRepositoryExtension;

    public ResearchInstitutionResourceExtension(ResearchInstitutionRepositoryExtension researchInstitutionRepositoryExtension) {
        this.researchInstitutionRepositoryExtension = researchInstitutionRepositoryExtension;
    }

  
    /**
     * {@code GET  /research-institutions/:name} : get the "name" researchInstitution(S).
     *
     * @param id the id of the researchInstitution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInstitution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-institutions-autocomplete/{name}")
    public  List<ResearchInstitution> getResearchInstitution(@PathVariable String name) {
        log.debug("REST request to get ResearchInstitution : {} !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!^^^^^^^^^^^^^^^^^^^^^^^^^^^^&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&", name);
        System.out.print("u metodi.........................." + name + "$$$$$");
        List<ResearchInstitution> researchInstitutions = researchInstitutionRepositoryExtension.findResearchInstitutionByName(name.toUpperCase());
        return researchInstitutions;
    }

  
}
