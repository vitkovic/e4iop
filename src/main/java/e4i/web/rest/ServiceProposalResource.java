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
import e4i.domain.RiProcurementProposal;
import e4i.domain.ServiceProposal;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.ServiceProposalRepository;
import e4i.repository.ServiceProposalsLikesRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.service.UserService;
import e4i.web.rest.dto.RiProcurementProposalDTO;
import e4i.web.rest.dto.ServiceProposalDTO;
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
 * REST controller for managing {@link e4i.domain.ServiceProposal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ServiceProposalResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProposalResource.class);

    private static final String ENTITY_NAME = "serviceProposal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceProposalRepository serviceProposalRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final ServiceProposalsLikesRepository serviceProposalsLikesRepository;

	public ServiceProposalResource(ServiceProposalRepository serviceProposalRepository, UserService userService,
			PortalUserRepository portalUserRepository,
			ServiceProposalsLikesRepository serviceProposalsLikesRepository) {
		super();
		this.serviceProposalRepository = serviceProposalRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.serviceProposalsLikesRepository = serviceProposalsLikesRepository;
	}

	/**
     * {@code POST  /service-proposals} : Create a new serviceProposal.
     *
     * @param serviceProposal the serviceProposal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceProposal, or with status {@code 400 (Bad Request)} if the serviceProposal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-proposals")
    public ResponseEntity<ServiceProposal> createServiceProposal(@RequestBody ServiceProposal serviceProposal) throws URISyntaxException {
        log.debug("REST request to save ServiceProposal : {}", serviceProposal);
        if (serviceProposal.getId() != null) {
            throw new BadRequestAlertException("A new serviceProposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        serviceProposal.setPortalUser(pUser);
        serviceProposal.setDate(ZonedDateTime.now());
        
        ServiceProposal result = serviceProposalRepository.save(serviceProposal);
        return ResponseEntity.created(new URI("/api/service-proposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-proposals} : Updates an existing serviceProposal.
     *
     * @param serviceProposal the serviceProposal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceProposal,
     * or with status {@code 400 (Bad Request)} if the serviceProposal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceProposal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-proposals")
    public ResponseEntity<ServiceProposal> updateServiceProposal(@RequestBody ServiceProposal serviceProposal) throws URISyntaxException {
        log.debug("REST request to update ServiceProposal : {}", serviceProposal);
        if (serviceProposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceProposal result = serviceProposalRepository.save(serviceProposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceProposal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-proposals} : get all the serviceProposals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceProposals in body.
     */
    @GetMapping("/service-proposals")
    public ResponseEntity<List<ServiceProposalDTO>> getAllServiceProposals(Pageable pageable) {
        log.debug("REST request to get a page of ServiceProposals");
        //Page<ServiceProposal> page = serviceProposalRepository.findAll(pageable);
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();        
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        Page<ServiceProposalDTO> page = null;
        if(pUser != null)
        	page = serviceProposalRepository.pronadjiAllEditableForUser(pUser.getId(), pageable);
        else {
            if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            	page = serviceProposalRepository.findAllEditableForAdmin(pageable);
            }

        }
        
        
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-proposals/:id} : get the "id" serviceProposal.
     *
     * @param id the id of the serviceProposal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceProposal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-proposals/{id}")
    public ResponseEntity<ServiceProposal> getServiceProposal(@PathVariable Long id) {
        log.debug("REST request to get ServiceProposal : {}", id);
        Optional<ServiceProposal> serviceProposal = serviceProposalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceProposal);
    }

    /**
     * {@code DELETE  /service-proposals/:id} : delete the "id" serviceProposal.
     *
     * @param id the id of the serviceProposal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-proposals/{id}")
    public ResponseEntity<Void> deleteServiceProposal(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProposal : {}", id);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){  
        	serviceProposalsLikesRepository.deleteByProposalId(id);
        	serviceProposalRepository.deleteById(id);
        	return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("PA")) {
            	 serviceProposalsLikesRepository.deleteByProposalId(id);
            	 serviceProposalRepository.deleteById(id);
            	 return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
             }else{           	 
            	 Optional<ServiceProposal> riOptional = serviceProposalRepository.findById(id);
            	 ServiceProposal ri = riOptional.get();
            	 if(ri != null) {
           			if(pUser.getId() == ri.getPortalUser().getId()) {
           				serviceProposalsLikesRepository.deleteByProposalId(id);
           				serviceProposalRepository.deleteById(id);
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
