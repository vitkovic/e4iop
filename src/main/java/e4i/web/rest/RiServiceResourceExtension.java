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
import e4i.domain.RiService;
import e4i.repository.RiServiceRepositoryExtension;
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
 * REST controller for managing {@link e4i.domain.RiService}.
 */
@RestController
@RequestMapping("/api")
public class RiServiceResourceExtension {

    private final Logger log = LoggerFactory.getLogger(RiServiceResourceExtension.class);

    private static final String ENTITY_NAME = "riService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiServiceRepositoryExtension riServiceRepositoryExtension;

    public RiServiceResourceExtension(RiServiceRepositoryExtension riServiceRepositoryExtension) {
        this.riServiceRepositoryExtension = riServiceRepositoryExtension;
    }


     
    /**
     * {@code GET  /ri-services-autocomplete/:name} : get the "name" researchInfrastructure(S).
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-services-autocomplete/{name}")
    public  List<RiService> getRiService(@PathVariable String name) {
        log.debug("REST request to get ResearchInfrastructure : {}", name);
        System.out.println("u metodi.........................." + name + "$$$$$");
        System.out.println("u metodi.........................." + name + "###################################################");
        List<RiService> riService = this.riServiceRepositoryExtension.findRiServiceByNameSr(name.toUpperCase());
        return riService;
    }
   
    /**
     * {@code GET  /ri-services-autocomplete/en/:name} : get the "name" researchInfrastructure(S).
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-services-autocomplete/en/{name}")
    public  List<RiService> getRiServiceEn(@PathVariable String name) {
        log.debug("REST request to get ResearchInfrastructure : {}", name);
        System.out.println("u metodi.........................." + name + "$$$$$");
        System.out.println("u metodi.........................." + name + "###################################################");
        List<RiService> riService =this.riServiceRepositoryExtension.findRiServiceByNameEn(name.toUpperCase());
        return riService;
    }
    
    
    

  
}
