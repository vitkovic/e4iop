package e4i.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUser;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.RequestForServiceRepository;
import e4i.repository.RequestRespondRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.web.rest.dto.OrganizationDetailsReportDTO;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.NioMustBeUniqueException;
import e4i.web.rest.errors.NotAuthorizedException;
import e4i.web.rest.errors.PuoMustBeUniqueException;

@Service
@Transactional
public class RiResearchOrganizationService {
	
    private final Logger log = LoggerFactory.getLogger(RiResearchOrganizationService.class);

    private final ResearchInfrastructureRepository researchInfrastructureRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;    
    private final RiServiceRepository riServiceRepository;
    private final RequestForServiceRepository requestForServiceRepository;
    private final RequestRespondRepository requestRespondRepository;

	
	public RiResearchOrganizationService(ResearchInfrastructureRepository researchInfrastructureRepository,
			UserService userService, PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository, RiServiceRepository riServiceRepository,
			RequestForServiceRepository requestForServiceRepository,
			RequestRespondRepository requestRespondRepository) {
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.riServiceRepository = riServiceRepository;
		this.requestForServiceRepository = requestForServiceRepository;
		this.requestRespondRepository = requestRespondRepository;
	}
	
	public RiResearchOrganization save(RiResearchOrganization input) throws URISyntaxException{
		
		Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        List<String> userRoles = new ArrayList<String>();
        if(pUser != null) {
        	userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
        }
        
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || userRoles.contains("PA")){
			 //provera da li za tu NIO vec postoji RIPO
	      //  Optional<RiResearchOrganization> oRi = riResearchOrganizationRepository.findByResearchInstitutionId(input.getResearchInstitution().getId());
	      //  oRi.ifPresent(ori -> {  // ako postoji organizacija za tu NIO      	
	      //  	throw new NioMustBeUniqueException();        	
	      //  });
	        
	      //provera da li za tu PUO vec postoji RIPO
	        RiResearchOrganization opRi = riResearchOrganizationRepository.findByPuOrganizationId(input.getPuOrganization().getId());
	        if(opRi != null) {	              	
	        	throw new PuoMustBeUniqueException();
	        }
	        		
	        
	        RiResearchOrganization result = riResearchOrganizationRepository.save(input);
	        return result;

	        
        }else {
        	throw new NotAuthorizedException();
        }			
	}


	public RiResearchOrganization update(RiResearchOrganization input) throws URISyntaxException{
		
		Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        List<String> userRoles = new ArrayList<String>();
        if(pUser != null) {
        	userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
        }
        
        //provera da li za tu PUO vec postoji RIPO
        RiResearchOrganization opRi = riResearchOrganizationRepository.findByPuOrganizationId(input.getPuOrganization().getId());
        if(input.getId().longValue() != opRi.getId().longValue()) {             		        	
        	throw new PuoMustBeUniqueException();        	
        }
        
		if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || userRoles.contains("PA")){
	        RiResearchOrganization result = riResearchOrganizationRepository.save(input);
	        return result;	        
        }else if(userRoles.contains("RPRIPO")) {
        	// provera da li je administrator bas te organizacije
        	List<RiResearchOrganization> list = riResearchOrganizationRepository.findByManagerId(pUser.getId());
        	for(RiResearchOrganization r : list) {
        		if(r.getId() == input.getId()) {
        			RiResearchOrganization result = riResearchOrganizationRepository.save(input);
        	        return result;	        
        		}
        	}
        	return null;
        }else {
        	throw new NotAuthorizedException();
        }
		
		
		
	}

	
	
	
    
    


}
