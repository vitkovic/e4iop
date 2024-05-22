package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.ResearchInfrastructure;
import e4i.repository.ResearchInfrastructureRepositoryExtension;
import e4i.service.ResearchInfrastructureService;
import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.ResearchInfrastructure}.
 */
@RestController
@RequestMapping("/api")
public class ResearchInfrastructureResourceExtension {

    private final Logger log = LoggerFactory.getLogger(ResearchInfrastructureResourceExtension.class);

    private static final String ENTITY_NAME = "researchInfrastructure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResearchInfrastructureRepositoryExtension researchInfrastructureRepositoryExtension;

    public ResearchInfrastructureResourceExtension(ResearchInfrastructureRepositoryExtension researchInfrastructureRepositoryExtension) {
        this.researchInfrastructureRepositoryExtension = researchInfrastructureRepositoryExtension;
    }

    /**
     * {@code GET  /research-infrastructures/:name} : get the "name" researchInfrastructure(S).
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-infrastructures-autocomplete/{name}")
    public  List<ResearchInfrastructure> getResearchInfrastructure(@PathVariable String name) {
        log.debug("REST request to get ResearchInfrastructure : {}", name);
        System.out.println("u metodi.........................." + name + "$$$$$");
        System.out.println("u metodi.........................." + name + "###################################################");
        List<ResearchInfrastructure> researchInfrastructures = researchInfrastructureRepositoryExtension.findResearchInfrastructureByNameSr(name.toUpperCase());
        return researchInfrastructures;
    }
   
    /**
     * {@code GET  /research-infrastructures/en/:name} : get the "name" researchInfrastructure(S).
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-infrastructures-autocomplete/en/{name}")
    public  List<ResearchInfrastructure> getResearchInfrastructureEn(@PathVariable String name) {
        log.debug("REST request to get ResearchInfrastructure : {}", name);
        System.out.println("u metodi.........................." + name + "$$$$$");
        System.out.println("u metodi.........................." + name + "###################################################");
        List<ResearchInfrastructure> researchInfrastructures = researchInfrastructureRepositoryExtension.findResearchInfrastructureByNameEn(name.toUpperCase());
        return researchInfrastructures;
    }
   
    
}
