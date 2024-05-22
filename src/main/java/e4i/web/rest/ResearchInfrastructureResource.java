package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.InfrastructureImage;
import e4i.domain.ResearchInfrastructure;
import e4i.repository.InfrastructureImageRepository;
import e4i.service.FilesStorageService;
import e4i.service.ResearchInfrastructureService;
import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link e4i.domain.ResearchInfrastructure}.
 */
@RestController
@RequestMapping("/api")
public class ResearchInfrastructureResource {

    private final Logger log = LoggerFactory.getLogger(ResearchInfrastructureResource.class);

    private static final String ENTITY_NAME = "researchInfrastructure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    FilesStorageService storageService;

    private final ResearchInfrastructureService researchInfrastructureService;
    private final InfrastructureImageRepository infrastructureImageRepository;

    

    public ResearchInfrastructureResource(ResearchInfrastructureService researchInfrastructureService,
			InfrastructureImageRepository infrastructureImageRepository) {
		super();
		this.researchInfrastructureService = researchInfrastructureService;
		this.infrastructureImageRepository = infrastructureImageRepository;
	}

	/**
     * {@code POST  /research-infrastructures} : Create a new researchInfrastructure.
     *
     * @param researchInfrastructure the researchInfrastructure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new researchInfrastructure, or with status {@code 400 (Bad Request)} if the researchInfrastructure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/research-infrastructures")
    public ResponseEntity<ResearchInfrastructure> createResearchInfrastructure(@RequestBody ResearchInfrastructure researchInfrastructure) throws URISyntaxException {
        log.debug("REST request to save ResearchInfrastructure : {}", researchInfrastructure);
        if (researchInfrastructure.getId() != null) {
            throw new BadRequestAlertException("A new researchInfrastructure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResearchInfrastructure result = researchInfrastructureService.save(researchInfrastructure);
        return ResponseEntity.created(new URI("/api/research-infrastructures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /research-infrastructures} : Updates an existing researchInfrastructure.
     *
     * @param researchInfrastructure the researchInfrastructure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated researchInfrastructure,
     * or with status {@code 400 (Bad Request)} if the researchInfrastructure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the researchInfrastructure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/research-infrastructures")
    public ResponseEntity<ResearchInfrastructure> updateResearchInfrastructure(@RequestBody ResearchInfrastructure researchInfrastructure) throws URISyntaxException {
        log.debug("REST request to update ResearchInfrastructure : {}", researchInfrastructure);
        if (researchInfrastructure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResearchInfrastructure result = researchInfrastructureService.save(researchInfrastructure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, researchInfrastructure.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /research-infrastructures} : get all the researchInfrastructures.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of researchInfrastructures in body.
     */
    @GetMapping("/research-infrastructures")
    public ResponseEntity<List<ResearchInfrastructure>> getAllResearchInfrastructures(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ResearchInfrastructures");
        Page<ResearchInfrastructure> page;
        if (eagerload) {
            page = researchInfrastructureService.findAllWithEagerRelationships(pageable);
        } else {
            page = researchInfrastructureService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /research-infrastructures/:id} : get the "id" researchInfrastructure.
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-infrastructures/{id}")
    public ResponseEntity<ResearchInfrastructure> getResearchInfrastructure(@PathVariable Long id) {
        log.debug("REST request to get ResearchInfrastructure : {}", id);
        Optional<ResearchInfrastructure> researchInfrastructure = researchInfrastructureService.findOne(id);
        ResearchInfrastructure ri = researchInfrastructure.get();
        Set<InfrastructureImage> images = infrastructureImageRepository.findByResearchInfrastructure(ri);
        if(images != null && images.size() > 0)
        	ri.setImages(images);
        return ResponseEntity.ok().body(ri);
        //return ResponseUtil.wrapOrNotFound(researchInfrastructure);
    }

    /**
     * {@code DELETE  /research-infrastructures/:id} : delete the "id" researchInfrastructure.
     *
     * @param id the id of the researchInfrastructure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/research-infrastructures/{id}")
    public ResponseEntity<Void> deleteResearchInfrastructure(@PathVariable Long id) {
        log.debug("REST request to delete ResearchInfrastructure : {}", id);
        researchInfrastructureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    /**
     * Metodi za dobijanje svih infrastruktura, koriste se kod prgleda. Svi imaju pristup, ali ne mogu da menjaju
     * @param pageable
     * @param eagerload
     * @return
     */
    @GetMapping("/research-infrastructures-pregled")
    public ResponseEntity<List<ResearchInfrastructure>> getAllResearchInfrastructuresPregled(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
    	log.debug("REST request to get a page of ResearchInfrastructures");
        Page<ResearchInfrastructure> page;
        if (eagerload) {
            page = researchInfrastructureService.findAllWithEagerRelationshipsPregled(pageable);
        } else {
            page = researchInfrastructureService.findAllPregled(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @PostMapping("/research-infrastructures-pregled/search")
    public List<ResearchInfrastructureDTOSearch> getAllRIPregledSearch(@RequestBody SearchDTO search) {
    	log.debug("REST request to search Research Infrastructures");
        if(search.getCurrentLanguage().equals("en"))
        	return researchInfrastructureService.searchByEnglishKeywords(search);
        else {
        	List<ResearchInfrastructureDTOSearch> out = researchInfrastructureService.searchBySerbianKeywords(search);
        	return out;
        }
        
    }

    /**
     * {@code GET  /research-infrastructures/:id} : get the "id" researchInfrastructure.
     *
     * @param id the id of the researchInfrastructure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the researchInfrastructure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/research-infrastructures-pregled/{id}")
    public ResponseEntity<ResearchInfrastructure> getResearchInfrastructurePregled(@PathVariable Long id) {
        log.debug("REST request to get ResearchInfrastructure : {}", id);
        Optional<ResearchInfrastructure> researchInfrastructure = researchInfrastructureService.findOne(id);
        ResearchInfrastructure ri = researchInfrastructure.get();
        Set<InfrastructureImage> images = infrastructureImageRepository.findByResearchInfrastructure(ri);
        if(images != null && images.size() > 0)
        	ri.setImages(images);
        return ResponseUtil.wrapOrNotFound(researchInfrastructure);
    }
    
    /**
     * Broj infrastruktura u bazi
     * @param id
     * @return
     */
    @GetMapping("/research-infrastructures-pregled/count")
    public ResponseEntity<Long> countResearchInfrastructurePregled() {
        log.debug("REST request to get ResearchInfrastructure : {}");
        Long count = researchInfrastructureService.countAll();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }
    
    
    @DeleteMapping("/research-infrastructures/images/{imageId}")
    @Transactional
    public ResponseEntity<Set<InfrastructureImage>> deleteImageForResearchInfrastructure(@PathVariable Long imageId) {
        log.debug("REST request to delete Image for ResearchInfrastructure : {}", imageId);
        
        Optional<InfrastructureImage> imgOptional = infrastructureImageRepository.findById(imageId);
        InfrastructureImage img = imgOptional.get();
        ResearchInfrastructure ri = img.getResearchInfrastructure();
        
        infrastructureImageRepository.deleteById(imageId);
        storageService.deleteImage(img.getFilename());
        
        Set<InfrastructureImage> images = infrastructureImageRepository.findByResearchInfrastructure(ri);
        return ResponseEntity.ok().body(images);
    }
    
    
   
    
}
