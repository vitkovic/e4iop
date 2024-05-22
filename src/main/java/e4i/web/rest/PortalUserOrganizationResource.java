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

import e4i.domain.PortalUserOrganization;
import e4i.domain.ResearchInstitution;
import e4i.repository.PortalUserOrganizationRepository;
import e4i.service.PortalUserOrganizationService;
import e4i.web.rest.dto.OrganizationDetailsReportDTO;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.PibAlreadyExistsException;
import e4i.web.rest.errors.RegistrationNumberAlreadyExistsException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.swagger.client.ApiException;
import io.swagger.client.api.RequestHandler;
import io.swagger.client.model.Company__1;

/**
 * REST controller for managing {@link e4i.domain.PortalUserOrganization}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PortalUserOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(PortalUserOrganizationResource.class);

    private static final String ENTITY_NAME = "portalUserOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortalUserOrganizationRepository portalUserOrganizationRepository;
    private final PortalUserOrganizationService portalUserOrganizationService;

    public PortalUserOrganizationResource(PortalUserOrganizationRepository portalUserOrganizationRepository,
			PortalUserOrganizationService portalUserOrganizationService) {
		super();
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
		this.portalUserOrganizationService = portalUserOrganizationService;
	}

	/**
     * {@code POST  /portal-user-organizations} : Create a new portalUserOrganization.
     *
     * @param portalUserOrganization the portalUserOrganization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portalUserOrganization, or with status {@code 400 (Bad Request)} if the portalUserOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portal-user-organizations")
    public ResponseEntity<PortalUserOrganization> createPortalUserOrganization(@Valid @RequestBody PortalUserOrganization portalUserOrganization) throws URISyntaxException {
        log.debug("REST request to save PortalUserOrganization : {}", portalUserOrganization);
        if (portalUserOrganization.getId() != null) {
            throw new BadRequestAlertException("A new portalUserOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<PortalUserOrganization> pu = portalUserOrganizationRepository.findByRegistrationNumber(portalUserOrganization.getRegistrationNumber());
        pu.ifPresent(puo -> {  // ako postoji organizacija sa tim maticnim brojem      	
        	throw new RegistrationNumberAlreadyExistsException();        	
        });
        
        Optional<PortalUserOrganization> pu1 = portalUserOrganizationRepository.findByPib(portalUserOrganization.getPib());
        pu1.ifPresent(puo -> {  // ako postoji organizacija sa tim maticnim brojem      	
        	throw new PibAlreadyExistsException();        	
        });
        
        
        portalUserOrganization.setNames();
        PortalUserOrganization result = portalUserOrganizationRepository.save(portalUserOrganization);
        return ResponseEntity.created(new URI("/api/portal-user-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portal-user-organizations} : Updates an existing portalUserOrganization.
     *
     * @param portalUserOrganization the portalUserOrganization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portalUserOrganization,
     * or with status {@code 400 (Bad Request)} if the portalUserOrganization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portalUserOrganization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portal-user-organizations")
    public ResponseEntity<PortalUserOrganization> updatePortalUserOrganization(@Valid @RequestBody PortalUserOrganization portalUserOrganization) throws URISyntaxException {
        log.debug("REST request to update PortalUserOrganization : {}", portalUserOrganization);
        if (portalUserOrganization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        portalUserOrganization.setNames();
        PortalUserOrganization result = portalUserOrganizationRepository.save(portalUserOrganization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portalUserOrganization.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /portal-user-organizations} : get all the portalUserOrganizations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portalUserOrganizations in body.
     */
    @GetMapping("/portal-user-organizations")
    public ResponseEntity<List<PortalUserOrganization>> getAllPortalUserOrganizations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("researchorganization-is-null".equals(filter)) {
            log.debug("REST request to get all PortalUserOrganizations where researchOrganization is null");
            return new ResponseEntity<>(StreamSupport
                .stream(portalUserOrganizationRepository.findAll().spliterator(), false)
                .filter(portalUserOrganization -> portalUserOrganization.getResearchOrganization() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of PortalUserOrganizations");
        Page<PortalUserOrganization> page = portalUserOrganizationService.findAll(pageable);
        //Page<PortalUserOrganization> page = portalUserOrganizationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    /**
     * {@code GET  /portal-user-organizations/:instid} : get the "instid" ResearchInstitution.
     *
     * @param id the id of the portalUserOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-organizations-institutions/{instid}")
    public ResponseEntity<PortalUserOrganization> getPortalUserOrganizationByInstitutions(@PathVariable Long instid) {
        log.debug("REST request to get PortalUserOrganization : {}", instid);
        Optional<PortalUserOrganization> portalUserOrganization = portalUserOrganizationRepository.findByNioInstitutionId(instid);
        return ResponseUtil.wrapOrNotFound(portalUserOrganization);
    }
    
    
    
    /**
     * {@code GET  /portal-user-organizations/:id} : get the "id" portalUserOrganization.
     *
     * @param id the id of the portalUserOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-organizations/{id}")
    public ResponseEntity<PortalUserOrganization> getPortalUserOrganization(@PathVariable Long id) {
        log.debug("REST request to get PortalUserOrganization : {}", id);
        Optional<PortalUserOrganization> portalUserOrganization = portalUserOrganizationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portalUserOrganization);
    }
    
    
    

    /**
     * {@code GET  /portal-user-organizations/:id} : get the "id" portalUserOrganization.
     *
     * @param id the id of the portalUserOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-organizations-from-db/{keys}")
    public List<PortalUserOrganization> getPortalUserOrganizationFromDb(@PathVariable String keys) {
        log.debug("REST request to get PortalUserOrganization : {}", keys);
        List<PortalUserOrganization> portalUserOrganisations = portalUserOrganizationRepository.findByRegistrationNumberList(keys.toUpperCase());
        return portalUserOrganisations;
    }
    
    /**
     * {@code GET  /portal-user-organizations/:keys} : list of db organisations by registration number
     *
     * @param id the id of the portalUserOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-organizations-from-api/{keys}")
    public PortalUserOrganization getPortalUserOrganizationFromApiMb(@PathVariable String keys) {
        log.debug("REST request to get PortalUserOrganization : {}", keys);
        RequestHandler jsonhandler = new RequestHandler();
        List<Company__1> companies = null;
        PortalUserOrganization portalUserOrganisations = null;
        try {
			companies = jsonhandler.getCompanies(keys + ";1,3,6");
			//System.out.println(companies);
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
        if (companies.size() > 0) 
        	portalUserOrganisations = this.getOrganisation(companies.get(0));
        
        return portalUserOrganisations;
    }
    
    
    
    public PortalUserOrganization getOrganisation(Company__1 company) {
    	PortalUserOrganization puo = new PortalUserOrganization();
    	
    	puo.setLegalNameEn(company.getName());
    	puo.setLegalNameSr(company.getName());
    	puo.setRegistrationNumber(company.getRegNo());
    	puo.setPib(company.getTaxNo());
   
    	return puo;
    	
    	
    }
    
    /**
     * {@code DELETE  /portal-user-organizations/:id} : delete the "id" portalUserOrganization.
     *
     * @param id the id of the portalUserOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portal-user-organizations/{id}")
    public ResponseEntity<Void> deletePortalUserOrganization(@PathVariable Long id) {
        log.debug("REST request to delete PortalUserOrganization : {}", id);
        portalUserOrganizationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * Metodi slicni kao gore, ali potrebni kod pristupa bez prijave
     * @return
     */
    
    @GetMapping("/portal-user-organizations-pregled/without-pagination")
    public List<PortalUserOrganization> getAllPortalUserOrganizationsWithoutPagination() {        
        log.debug("REST request to get a page of PortalUserOrganizations");
        List<PortalUserOrganization> page = portalUserOrganizationRepository.findAll();        
        return page;
    }    
    
    @GetMapping("/portal-user-organizations-pregled")
    public ResponseEntity<List<PortalUserOrganization>> getAllPortalUserOrganizationsPregled(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("researchorganization-is-null".equals(filter)) {
            log.debug("REST request to get all PortalUserOrganizations where researchOrganization is null");
            return new ResponseEntity<>(StreamSupport
                .stream(portalUserOrganizationRepository.findAll().spliterator(), false)
                .filter(portalUserOrganization -> portalUserOrganization.getResearchOrganization() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of PortalUserOrganizations");
        Page<PortalUserOrganization> page = portalUserOrganizationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/portal-user-organizations-pregled/{id}")
    public ResponseEntity<PortalUserOrganization> getPortalUserOrganizationPregled(@PathVariable Long id) {
        log.debug("REST request to get PortalUserOrganization : {}", id);
        Optional<PortalUserOrganization> portalUserOrganization = portalUserOrganizationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portalUserOrganization);
    }
    
    /**
     * Broj organizacija u bazi
     * @param id
     * @return
     */
    @GetMapping("/portal-user-organizations-pregled/count")
    public ResponseEntity<Long> countPortalUserOrganizationsPregled() {
        log.debug("REST request to get count of organizations: {}");
        Long count = portalUserOrganizationRepository.count();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }
    
    /**
     * Vadi sve organizacije na portalu koje odgovaraju kriterijumima. 
     * Poziva se kad se na strani izvestaji izabere dugme potrazi. 
     * @param search
     * @return
     */
    @PostMapping("/portal-user-organizations/search-report")
    public List<RiResearchOrganizationReportDTO> searchForReport(@RequestBody SearchDTO search) {
    	log.debug("REST request to search Organizations");
        
    	List<RiResearchOrganizationReportDTO> out = portalUserOrganizationService.searchForReport(search);
    	return out;                
    }
    
    /**
     * Vadi sve Organizacije na portalu.
     * Poziva se kad se dodje na stranu izvestaji
     * @return
     */
    
    @GetMapping("/portal-user-organizations/report")
    public List<RiResearchOrganizationReportDTO> getAllForReport() {
    	log.debug("REST request to search Organizations");
    	List<RiResearchOrganizationReportDTO> out = portalUserOrganizationRepository.findAllForReport();
    	return out;        
    }
    
    /**
     * Vadi statisticke detalje za zadatu PUO
     * Poziva se kad se kliokne dugme details na strani izvestaji
     * @param id
     * @return
     */    
    @PostMapping("/portal-user-organizations/report/details")
    public OrganizationDetailsReportDTO getDetailsForRiOrganization(@RequestBody SearchDTO search) {
    	log.debug("REST request to get details");    	
    	OrganizationDetailsReportDTO out = portalUserOrganizationService.getDetailsForRiOrganization(search);
    	if(out == null)
    		return new OrganizationDetailsReportDTO();
    	return out;        	   
    }
    
    @GetMapping("/portal-user-organizations/search/{name}/{language}")
    public  List<PortalUserOrganization> getPortalUserOrganizationsByLegalNameSearch(@PathVariable String name, @PathVariable String language) {
    	
    	List<PortalUserOrganization> portalUserOrganizations = new ArrayList<>();
    	if (language.equals("sr")) {
            portalUserOrganizations = portalUserOrganizationRepository.findByLegalNameSrContainingIgnoreCase(name);
    	} else if (language.equals("src")) {
            portalUserOrganizations = portalUserOrganizationRepository.findByLegalNameSrCyrContainingIgnoreCase(name);
    	} else if (language.equals("en")) {
            portalUserOrganizations = portalUserOrganizationRepository.findByLegalNameEnContainingIgnoreCase(name);
    	} else {
    		System.out.println("Unexpected parameter value, 'language=" + language + "', in REST API 'getPortalUserOrganizationsByLegalNameSearch'.");
    	}
    	
        return portalUserOrganizations;
    }
}
