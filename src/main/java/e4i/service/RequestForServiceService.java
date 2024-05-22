package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUser;
import e4i.domain.RequestForService;
import e4i.domain.RequestRespond;
import e4i.domain.RequestState;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiResearchOrganization;
import e4i.domain.RiService;
import e4i.domain.ServiceOfferRespond;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.RequestForServiceRepository;
import e4i.repository.RequestRespondRepository;
import e4i.repository.RequestStateRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.repository.ServiceOfferRespondRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.web.rest.dto.RequestForServiceZbirnoDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link RequestForService}.
 */
@Service
@Transactional
public class RequestForServiceService {

    private final Logger log = LoggerFactory.getLogger(RequestForServiceService.class);

    private final RequestForServiceRepository requestForServiceRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;
    private final ResearchInfrastructureRepository researchInfrastructureRepository;   
    private final RiServiceRepository riServiceRepository;
    private final RequestRespondRepository requestRespondRepository;
    private final ServiceOfferRespondRepository serviceOfferRespondRepository;
    private final RequestStateRepository requestStateRepository;
	

	public RequestForServiceService(RequestForServiceRepository requestForServiceRepository, UserService userService,
			PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository,
			ResearchInfrastructureRepository researchInfrastructureRepository, RiServiceRepository riServiceRepository,
			RequestRespondRepository requestRespondRepository,
			ServiceOfferRespondRepository serviceOfferRespondRepository,
			RequestStateRepository requestStateRepository) {
		this.requestForServiceRepository = requestForServiceRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.riServiceRepository = riServiceRepository;
		this.requestRespondRepository = requestRespondRepository;
		this.serviceOfferRespondRepository = serviceOfferRespondRepository;
		this.requestStateRepository = requestStateRepository;
	}

	/**
     * Save a requestForService.
     *
     * @param requestForService the entity to save.
     * @return the persisted entity.
     */
    public RequestForService save(RequestForService requestForService) {
        log.debug("Request to save RequestForService : {}", requestForService);
        if(requestForService.getDate() == null)
        	requestForService.setDate(ZonedDateTime.now());
        
        RequestState requestStateOpened = requestStateRepository.findByStateEn("opened");
        requestForService.setState(requestStateOpened);// setuje se stanje opened
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return requestForServiceRepository.save(requestForService);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             
             if(userRoles.contains("PA")) {
            	 return requestForServiceRepository.save(requestForService);
             }else {// obican user i RPRI i RPRIPO se u ovom slucaju smatraju obicnim korisnicima
            	 requestForService.setUsers(pUser);
                 requestForService.setOrganizations(pUser.getUserOrganization());
                 return requestForServiceRepository.save(requestForService);
             }
        }
        
    }

    /**
     * Get all the requestForServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestForService> findAll(Pageable pageable) {
        log.debug("Request to get all RequestForServices");
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return requestForServiceRepository.findAll(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByOwnerId(rio.getId());
            	 List<RiService> ris = riServiceRepository.findByResearchInfrastructureIn(riList);
            	 
            	 List<RiService> servicesForUser = riServiceRepository.findForRequestsFromGivenUser(pUser.getId());
            	 ris.addAll(servicesForUser);
            	 
            	 return requestForServiceRepository.findByServicesIn(ris, pageable);            	 
             }else if(userRoles.contains("PA")) {
            	 return requestForServiceRepository.findAll(pageable);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByManagerId(pUser.getId());            	 
            	 List<RiService> ris = riServiceRepository.findByResearchInfrastructureIn(riList);
            	 
            	 List<RiService> servicesForUser = riServiceRepository.findForRequestsFromGivenUser(pUser.getId());
            	 ris.addAll(servicesForUser);
            	 
            	 Page<RequestForService> out = requestForServiceRepository.findByServicesIn(ris, pageable);
            	 return out;
             }else {
            	 return requestForServiceRepository.findByUsersId(pUser.getId(), pageable);
             }
        }
        
        
        
    }
    /**
     * Vraca zahteve koji su zavrseni i za koje moze da se glasa
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<RequestForService> findAllRating(Pageable pageable) {
        log.debug("Request to get all RequestForServices");
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return requestForServiceRepository.findByEndTimeBefore(ZonedDateTime.now(), pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("PA")) {
            	 return requestForServiceRepository.findByEndTimeBefore(ZonedDateTime.now(),pageable);
             }else {
            	 return requestForServiceRepository.findByUsersIdAndEndTimeBefore(pUser.getId(),ZonedDateTime.now(),  pageable);
             }
        }                        
    }



    /**
     *  Get all the requestForServices where Respond is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<RequestForService> findAllWhereRespondIsNull() {
        log.debug("Request to get all requestForServices where Respond is null");
        return StreamSupport
            .stream(requestForServiceRepository.findAll().spliterator(), false)
            .filter(requestForService -> requestForService.getRespond() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one requestForService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestForService> findOne(Long id) {
        log.debug("Request to get RequestForService : {}", id);
        return requestForServiceRepository.findById(id);
    }
    
    
    public int checkOpenedForGivenService(Long serviceId) {
        log.debug("Request to check number of RequestForService for given service : {}", serviceId);
        List<RequestForService> requests = null;
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
        	requests = requestForServiceRepository.findByServicesIdAndStateId(serviceId, 1L);        	
        }else {
        	Optional<User> currentUser = userService.getUserWithAuthorities();
            User user = currentUser.get();            
            
            PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
            List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
            
            if(userRoles.contains("PA")) {
            	requests = requestForServiceRepository.findByServicesIdAndStateId(serviceId, 1L);
            }else {
            	requests = requestForServiceRepository.findByServicesIdAndUsersIdAndStateId(serviceId, pUser.getId(), 1L);            	
            }
        }
        if(requests != null && !requests.isEmpty()) {
        	return requests.size();
        }else
        	return 0;
    }

    /**
     * Delete the requestForService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequestForService : {}", id);
        requestForServiceRepository.deleteById(id);
    }
    
    public List<RequestForService> findOpenedForGivenServiceId(Long serviceId) {
        log.debug("Request to get all RequestForService for given service : {}", serviceId);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return requestForServiceRepository.findByServicesIdAndStateId(serviceId, 1L);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             
             if(userRoles.contains("PA")) {
            	 return requestForServiceRepository.findByServicesIdAndStateId(serviceId, 1L);
             }else {
            	 return requestForServiceRepository.findByServicesIdAndUsersIdAndStateId(serviceId, pUser.getId(), 1L);
             }
        }
    }
    
    public RequestForServiceZbirnoDTO getZbirno(Long id) {
    	RequestForServiceZbirnoDTO requestForServiceZbirnoDTO = new RequestForServiceZbirnoDTO();
    	
    	Optional<RequestForService> requestForServiceO = requestForServiceRepository.findById(id);
    	RequestForService requestForService = requestForServiceO.get();
    	requestForServiceZbirnoDTO.setRequestForService(requestForService);
    	
    	Optional<RequestRespond> requestRespondO = requestRespondRepository.findByRequestForServiceId(id);
    	requestRespondO.ifPresent(requestRespond -> {
    		requestForServiceZbirnoDTO.setRequestRespond(requestRespond);
    		Optional<ServiceOfferRespond> serviceOfferRespondO = serviceOfferRespondRepository.findByRequestRespondId(requestRespond.getId()); 
    		serviceOfferRespondO.ifPresent(serviceOfferRespond -> {
    			requestForServiceZbirnoDTO.setServiceOfferRespond(serviceOfferRespond);
    		});    		
    	});
    	
    	
    	return requestForServiceZbirnoDTO;
    }
}
