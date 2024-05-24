package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Company;
import e4i.domain.PortalUser;
import e4i.domain.PortalUserOrganization;
import e4i.domain.PortalUserRole;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.PortalUserOrganizationRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.UserRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.NotAuthorizedException;
import e4i.web.rest.vm.PortalUserDTO;
import io.github.jhipster.security.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link PortalUser}.
 */
@Service
@Transactional
public class PortalUserService {

    private final Logger log = LoggerFactory.getLogger(PortalUserService.class);

    private final PortalUserRepository portalUserRepository;
    private final UserService userService;
    private final PortalUserOrganizationRepository portalUserOrganizationRepository;
    private final UserRepository userRepository;

    // Na ovaj nacin se izbegava cyclic dependency
//    @Autowired
    private final MailService mailService;
    
	

	public PortalUserService(PortalUserRepository portalUserRepository, UserService userService,
			PortalUserOrganizationRepository portalUserOrganizationRepository,
			UserRepository userRepository, MailService mailService) {
		this.portalUserRepository = portalUserRepository;
		this.userService = userService;
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	/**
     * Save a portalUser.
     *
     * @param portalUser the entity to save.
     * @return the persisted entity.
     */
    public PortalUser save(PortalUser portalUser) {
        log.debug("Request to save PortalUser : {}", portalUser);
        return portalUserRepository.save(portalUser);
    }

    /**
     * Get all the portalUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PortalUser> findAll(Pageable pageable) {
        log.debug("Request to get all PortalUsers");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){                
        	Page<PortalUser> out = portalUserRepository.findAll(pageable);
        	return out;
        }else {
        	Optional<User> currentUser = userService.getUserWithAuthorities();
            User user = currentUser.get();             
            PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
            List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
            
            if(userRoles.contains("RPRIPO")) {
            	Page<PortalUser> out = portalUserRepository.findByUserOrganizationId(pUser.getUserOrganization().getId(), pageable);
            	return out;
            }else if(userRoles.contains("PA")) {
            	Page<PortalUser> out = portalUserRepository.findAll(pageable);
            	return out;
            }else {
           	 	throw new NotAuthorizedException();           	 
            }
        }
    }


    /**
     * Get all the portalUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PortalUser> findAllWithEagerRelationships(Pageable pageable) {
        return portalUserRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one portalUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortalUser> findOne(Long id) {
        log.debug("Request to get PortalUser : {}", id);
        return portalUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the portalUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PortalUser : {}", id);
        portalUserRepository.deleteById(id);
    }
    
    
    /**************************************************************************************************************
     * Metod koji vraca uloge korisnika
     ***************************************************************************************************************/
    public List<String> getRoles() {    	
    	Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();         
        PortalUser pu = portalUserRepository.findByUserId(user.getId());
        if(pu != null && pu.getPortalUserRoles() != null) { 
        	List<String> out = new ArrayList<String>();
        	for(PortalUserRole pur : pu.getPortalUserRoles()) {
        		out.add(pur.getAbbreviation());
        	}
        	return out;        	
        }else {
        	return new ArrayList<String>();
        }
    }         
    
    public Long countAll() {
    	return portalUserRepository.count();
    }
    
    @Transactional(readOnly = true)
    public PortalUser findOneForActivation(Long id) {
        log.debug("Request to get PortalUserForActivation : {}", id);
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();             
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        List<String> userRoles = new ArrayList<String>();
        if(pUser != null)
        	userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || userRoles.contains("PA")){                
        	Optional<PortalUser> resultOptional  = portalUserRepository.findOneWithEagerRelationships(id);
        	PortalUser result = resultOptional.get();        	
        	return result;
        }else if(userRoles.contains("RPRIPO")) {
        	PortalUserOrganization rpripoOrg = pUser.getUserOrganization();
        	Optional<PortalUser> resultOptional  = portalUserRepository.findOneWithEagerRelationships(id);
        	PortalUser result = resultOptional.get();
        	if(result != null) {
    			if(pUser.getUserOrganization().getId() == result.getUserOrganization().getId()) {
	        		return result;
	        	}else {
	        		throw new NotAuthorizedException();
	        	}	
        	}else {
        		return null;
        	}
        } else {
        	throw new NotAuthorizedException();          	             
        }
    }
    
    //@Transactional(readOnly = true)
    public PortalUser saveRolesAndActivate(PortalUserDTO pdto) {
    	PortalUser out = null;
    	Optional<PortalUser> puOldOptional = portalUserRepository.findById(pdto.getId());
    	PortalUser puOld = puOldOptional.get();
    	if(puOld == null) {
    		throw new BadRequestAlertException("Invalid id", "PortalUseR", "idnull");    	
    	}
    	
    	PortalUser pu = new PortalUser(pdto);
    	
    	if(puOld.getUser().getActivated() == false && pdto.getUser().isActivated() == true) {
    		// slanje aktivacionog mejla
    		pu.setUser(puOld.getUser());
    		pu.getUser().setActivated(false);
            // new user gets registration key
            pu.getUser().setActivationKey(RandomUtil.generateActivationKey());
            User u = userRepository.save(pu.getUser());
            pu.setUser(u);
            out = portalUserRepository.save(pu);
            
            mailService.sendActivationEmail(out.getUser());    		
    	}else if(puOld.getUser().getActivated() == true && pdto.getUser().isActivated() == false) {
    		pu.setUser(puOld.getUser());
    		pu.getUser().setActivated(false); 
    		User u = userRepository.save(pu.getUser());
    		pu.setUser(u);
    		out = portalUserRepository.save(pu);
    	}else {
    		pu.setUser(puOld.getUser());
    		out = portalUserRepository.save(pu);
    	}
    	
    	
    		
    	return out;
    }
    
    @Transactional
    public PortalUser findCurrentPortalUser() {
        Optional<User> currentUserOptional = userService.getUserWithAuthorities();
        if (currentUserOptional.isEmpty()) {
        	throw new EntityNotFoundException("Current user could not be retrieved");
        }
        User currentUser = currentUserOptional.get();

        // Preraditi metodu "findByUserId" da vraca Optional
        PortalUser currentPortalUser= portalUserRepository.findByUserId(currentUser.getId());

        return currentPortalUser;
    }
    
    @Transactional
    public Company findCompanyByPortalUser(PortalUser portalUser) {
    	Optional<Company> companyOptional = portalUserRepository.findCompanyByPortalUserId(portalUser.getId());
    	if (companyOptional.isEmpty()) {
    		String errorMessage = String.format("Company for PortalUser with id={} could not be found.", portalUser.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    	
    	Company company = companyOptional.get();
    	
    	return company;
    }
    
    @Transactional
    public PortalUser findByUserId(String userId) {
    	PortalUser portalUser = portalUserRepository.findByUserId(userId);
    	
    	return portalUser;
    }
}
