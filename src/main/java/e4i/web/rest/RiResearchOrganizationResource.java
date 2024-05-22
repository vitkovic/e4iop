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

import e4i.domain.PortalUser;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.service.RiResearchOrganizationService;
import e4i.service.UserService;
import e4i.web.rest.dto.OrganizationDetailsReportDTO;
import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.NioMustBeUniqueException;
import e4i.web.rest.errors.PuoMustBeUniqueException;
import e4i.web.rest.errors.RegistrationNumberAlreadyExistsException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiResearchOrganization}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiResearchOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(RiResearchOrganizationResource.class);

    private static final String ENTITY_NAME = "riResearchOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiResearchOrganizationRepository riResearchOrganizationRepository;
    private final RiServiceRepository riServiceRepository;
    private final ResearchInfrastructureRepository researchInfrastructureRepository;
    private final RiResearchOrganizationService riResearchOrganizationService;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;



	public RiResearchOrganizationResource(RiResearchOrganizationRepository riResearchOrganizationRepository,
			RiServiceRepository riServiceRepository, ResearchInfrastructureRepository researchInfrastructureRepository,
			RiResearchOrganizationService riResearchOrganizationService, UserService userService,
			PortalUserRepository portalUserRepository) {
		super();
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.riServiceRepository = riServiceRepository;
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.riResearchOrganizationService = riResearchOrganizationService;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
	}

	/**
     * {@code POST  /ri-research-organizations} : Create a new riResearchOrganization.
     *
     * @param riResearchOrganization the riResearchOrganization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riResearchOrganization, or with status {@code 400 (Bad Request)} if the riResearchOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-research-organizations")
    public ResponseEntity<RiResearchOrganization> createRiResearchOrganization(@Valid @RequestBody RiResearchOrganization riResearchOrganization) throws URISyntaxException {
        log.debug("REST request to save RiResearchOrganization : {}", riResearchOrganization);
        if (riResearchOrganization.getId() != null) {
            throw new BadRequestAlertException("A new riResearchOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        RiResearchOrganization result = riResearchOrganizationService.save(riResearchOrganization);
        return ResponseEntity.created(new URI("/api/ri-research-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-research-organizations} : Updates an existing riResearchOrganization.
     *
     * @param riResearchOrganization the riResearchOrganization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riResearchOrganization,
     * or with status {@code 400 (Bad Request)} if the riResearchOrganization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riResearchOrganization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-research-organizations")
    public ResponseEntity<RiResearchOrganization> updateRiResearchOrganization(@Valid @RequestBody RiResearchOrganization riResearchOrganization) throws URISyntaxException {
        log.debug("REST request to update RiResearchOrganization : {}", riResearchOrganization);
        if (riResearchOrganization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        RiResearchOrganization result = riResearchOrganizationService.update(riResearchOrganization);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riResearchOrganization.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-research-organizations} : get all the riResearchOrganizations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riResearchOrganizations in body.
     */
    @GetMapping("/ri-research-organizations")
    public ResponseEntity<List<RiResearchOrganization>> getAllRiResearchOrganizations(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RiResearchOrganizations");
        Page<RiResearchOrganization> page;
        
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	page = riResearchOrganizationRepository.findAll(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 page = riResearchOrganizationRepository.findByManagerId(pUser.getId(), pageable);
             }else if(userRoles.contains("PA")) {
             	page = riResearchOrganizationRepository.findAll(pageable);
             }else {
            	 return null;
             }
        }        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ri-research-organizations/:id} : get the "id" riResearchOrganization.
     *
     * @param id the id of the riResearchOrganization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riResearchOrganization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-research-organizations/{id}")
    public ResponseEntity<RiResearchOrganization> getRiResearchOrganization(@PathVariable Long id) {
        log.debug("REST request to get RiResearchOrganization : {}", id);
        Optional<RiResearchOrganization> riResearchOrganization = riResearchOrganizationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(riResearchOrganization);
    }

    /**
     * {@code DELETE  /ri-research-organizations/:id} : delete the "id" riResearchOrganization.
     *
     * @param id the id of the riResearchOrganization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-research-organizations/{id}")
    public ResponseEntity<Void> deleteRiResearchOrganization(@PathVariable Long id) {
        log.debug("REST request to delete RiResearchOrganization : {}", id);
        riResearchOrganizationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
   
    
   
    
    
}
