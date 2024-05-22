package e4i.web.rest;

	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.Optional;

	import javax.validation.Valid;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.http.ResponseEntity;
	import org.springframework.transaction.annotation.Transactional;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

import e4i.domain.PortalUser;
import e4i.domain.ServiceProposalsLikes;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.ServiceProposalsLikesRepository;
import e4i.service.UserService;
import e4i.web.rest.dto.ServiceProposalsLikesDTO;
import e4i.web.rest.errors.BadRequestAlertException;

	@RestController
	@RequestMapping("/api")
	@Transactional
	public class ServiceProposalsLikeResource {
		
	    private final Logger log = LoggerFactory.getLogger(InfrastructureProposalsLikeResource.class);

	    private static final String ENTITY_NAME = "infrastructureProposalsLikes";

	    @Value("${jhipster.clientApp.name}")
	    private String applicationName;

	    private final ServiceProposalsLikesRepository serviceProposalsLikesRepository;
	    private final UserService userService;
	    private final PortalUserRepository portalUserRepository;


		public ServiceProposalsLikeResource(
				ServiceProposalsLikesRepository serviceProposalsLikesRepository, UserService userService,
				PortalUserRepository portalUserRepository) {
			super();
			this.serviceProposalsLikesRepository = serviceProposalsLikesRepository;
			this.userService = userService;
			this.portalUserRepository = portalUserRepository;
		}


		@PostMapping("/service-proposals-likes")
	    public ResponseEntity<ServiceProposalsLikesDTO> createInfrastructureProposalsLikes(@Valid @RequestBody ServiceProposalsLikes ipl) throws URISyntaxException {
	        log.debug("REST request to save Like : {}", ipl);
	        if (ipl.getId() != null) {
	            throw new BadRequestAlertException("A new Like cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        
	        Optional<User> currentUser = userService.getUserWithAuthorities();
	        User user = currentUser.get();
	        

	        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
	        Optional<ServiceProposalsLikes> ipl1 = serviceProposalsLikesRepository.findByPortalUserIdAndProposalId(pUser.getId(), ipl.getProposal().getId());
	        
	        ipl1.ifPresentOrElse(iplResult -> {//ako je korisnik vec glasao za taj predlog
	        	
		        	if(ipl.getNlike() == 0)
		        		iplResult.setNlike(null);
		        	else
		        		iplResult.setNlike(ipl.getNlike());
		        	
		        	if(ipl.getNdislike() == 0)
		        		iplResult.setNdislike(null);
		        	else
		        		iplResult.setNdislike(ipl.getNdislike());
		        	
		        	serviceProposalsLikesRepository.save(iplResult);        	
	        	},
	        	() -> {
	        		ipl.setPortalUser(pUser);
	        		
	        		if(ipl.getNlike() == 0)
		        		ipl.setNlike(null);
		        	
		        	if(ipl.getNdislike() == 0)
		        		ipl.setNdislike(null);
	        		
		        	serviceProposalsLikesRepository.save(ipl);
	        	}
	        );
	        
	        ServiceProposalsLikesDTO out = serviceProposalsLikesRepository.countLikes(ipl.getProposal().getId());
	        
	        return ResponseEntity.ok().body(out);
	    }
	    
	    


	

}
