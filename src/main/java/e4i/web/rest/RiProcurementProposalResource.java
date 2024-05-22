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
import e4i.domain.RiProcurementProposal;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.InfrastructureProposalsLikesRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.RiProcurementProposalRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.service.UserService;
import e4i.web.rest.dto.RiProcurementProposalDTO;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.NotAuthorizedException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiProcurementProposal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiProcurementProposalResource {

    private final Logger log = LoggerFactory.getLogger(RiProcurementProposalResource.class);

    private static final String ENTITY_NAME = "riProcurementProposal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiProcurementProposalRepository riProcurementProposalRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final InfrastructureProposalsLikesRepository infrastructureProposalsLikesRepository;

	public RiProcurementProposalResource(RiProcurementProposalRepository riProcurementProposalRepository,
			UserService userService, PortalUserRepository portalUserRepository,
			InfrastructureProposalsLikesRepository infrastructureProposalsLikesRepository) {
		this.riProcurementProposalRepository = riProcurementProposalRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.infrastructureProposalsLikesRepository = infrastructureProposalsLikesRepository;
	}

	/**
     * {@code POST  /ri-procurement-proposals} : Create a new riProcurementProposal.
     *
     * @param riProcurementProposal the riProcurementProposal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riProcurementProposal, or with status {@code 400 (Bad Request)} if the riProcurementProposal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-procurement-proposals")
    public ResponseEntity<RiProcurementProposal> createRiProcurementProposal(@RequestBody RiProcurementProposal riProcurementProposal) throws URISyntaxException {
        log.debug("REST request to save RiProcurementProposal : {}", riProcurementProposal);
        if (riProcurementProposal.getId() != null) {
            throw new BadRequestAlertException("A new riProcurementProposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        riProcurementProposal.setPortalUser(pUser);
        riProcurementProposal.setDate(ZonedDateTime.now());
        
        RiProcurementProposal result = riProcurementProposalRepository.save(riProcurementProposal);
        return ResponseEntity.created(new URI("/api/ri-procurement-proposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-procurement-proposals} : Updates an existing riProcurementProposal.
     *
     * @param riProcurementProposal the riProcurementProposal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riProcurementProposal,
     * or with status {@code 400 (Bad Request)} if the riProcurementProposal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riProcurementProposal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-procurement-proposals")
    public ResponseEntity<RiProcurementProposal> updateRiProcurementProposal(@RequestBody RiProcurementProposal riProcurementProposal) throws URISyntaxException {
        log.debug("REST request to update RiProcurementProposal : {}", riProcurementProposal);
        if (riProcurementProposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiProcurementProposal result = riProcurementProposalRepository.save(riProcurementProposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riProcurementProposal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-procurement-proposals} : get all the riProcurementProposals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riProcurementProposals in body.
     */
    @GetMapping("/ri-procurement-proposals")
    public ResponseEntity<List<RiProcurementProposalDTO>> getAllRiProcurementProposals(Pageable pageable) {
        log.debug("REST request to get a page of RiProcurementProposals");
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();        
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        Page<RiProcurementProposalDTO> page = null;
        if(pUser != null)
        	page = riProcurementProposalRepository.pronadjiAllEditableForUser(pUser.getId(), pageable);
        else {
            if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            	page = riProcurementProposalRepository.findAllEditableForAdmin(pageable);
            }

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ri-procurement-proposals/:id} : get the "id" riProcurementProposal.
     *
     * @param id the id of the riProcurementProposal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riProcurementProposal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-procurement-proposals/{id}")
    public ResponseEntity<RiProcurementProposal> getRiProcurementProposal(@PathVariable Long id) {
        log.debug("REST request to get RiProcurementProposal : {}", id);
        Optional<RiProcurementProposal> riProcurementProposal = riProcurementProposalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riProcurementProposal);
    }

    /**
     * {@code DELETE  /ri-procurement-proposals/:id} : delete the "id" riProcurementProposal.
     *
     * @param id the id of the riProcurementProposal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-procurement-proposals/{id}")
    public ResponseEntity<Void> deleteRiProcurementProposal(@PathVariable Long id) {
        log.debug("REST request to delete RiProcurementProposal : {}", id);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){  
        	infrastructureProposalsLikesRepository.deleteByProposalId(id);
        	riProcurementProposalRepository.deleteById(id);
        	return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("PA")) {
            	 infrastructureProposalsLikesRepository.deleteByProposalId(id);
            	 riProcurementProposalRepository.deleteById(id);
            	 return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
             }else{           	 
            	 Optional<RiProcurementProposal> riOptional = riProcurementProposalRepository.findById(id);
            	 RiProcurementProposal ri = riOptional.get();
            	 if(ri != null) {
           			if(pUser.getId() == ri.getPortalUser().getId()) {
           				infrastructureProposalsLikesRepository.deleteByProposalId(id);
           				riProcurementProposalRepository.deleteById(id);
           				return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();            		 
           			}else {
           				throw new NotAuthorizedException();
           			}
            	 }else {
                     throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            	 }
             }
             
        }
        
        
    }
}
