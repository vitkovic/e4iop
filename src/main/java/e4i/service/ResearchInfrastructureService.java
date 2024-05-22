package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUser;
import e4i.domain.PortalUserRole;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.util.LatCyrUtils;
import e4i.util.Utility;
import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.NotAuthorizedException;
import e4i.web.rest.errors.RegistrationNumberAlreadyExistsException;
import e4i.web.rest.errors.ResearchOrganizationMustExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link ResearchInfrastructure}.
 */
@Service
@Transactional
public class ResearchInfrastructureService {

    private final Logger log = LoggerFactory.getLogger(ResearchInfrastructureService.class);

    private final ResearchInfrastructureRepository researchInfrastructureRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;
   


	public ResearchInfrastructureService(ResearchInfrastructureRepository researchInfrastructureRepository,
			UserService userService, PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository) {
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
	}

	/**
     * Save a researchInfrastructure.
     *
     * @param researchInfrastructure the entity to save.
     * @return the persisted entity.
     */
    public ResearchInfrastructure save(ResearchInfrastructure researchInfrastructure) {
        log.debug("Request to save ResearchInfrastructure : {}", researchInfrastructure);
        this.chageInfrastructureText(researchInfrastructure);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return researchInfrastructureRepository.save(researchInfrastructure);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();             
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 if(rio == null) {
            		 throw new ResearchOrganizationMustExistException();
            	 }
            	 researchInfrastructure.setOwner(rio);            	 
            	 return researchInfrastructureRepository.save(researchInfrastructure);
             }else if(userRoles.contains("PA")) {
            	 return researchInfrastructureRepository.save(researchInfrastructure);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 if(rio == null) {
            		 throw new ResearchOrganizationMustExistException();
            	 }
            	 
            	 researchInfrastructure.setOwner(rio); 
            	 
            	 researchInfrastructure.setManager(pUser);
            	 
            	 return researchInfrastructureRepository.save(researchInfrastructure);
             }else {
            	 //return researchInfrastructureRepository.save(researchInfrastructure);
            	 return null;
             }
        }
        
                
        
    }

    /**
     * Get all the researchInfrastructures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResearchInfrastructure> findAll(Pageable pageable) {
        log.debug("Request to get all ResearchInfrastructures");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return researchInfrastructureRepository.findAll(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
            	 return out;
             }else if(userRoles.contains("PA")) {
            	 return researchInfrastructureRepository.findAll(pageable);
             }else if(userRoles.contains("RPRI")) {
            	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
             }else {
            	 return null;
             }
        }
        
    }


    /**
     * Get all the researchInfrastructures with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ResearchInfrastructure> findAllWithEagerRelationships(Pageable pageable) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return researchInfrastructureRepository.findAllWithEagerRelationships(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             pUser.getPortalUserRoles();
             //researchInfrastructureRepository.findAllByOwnerId
             return researchInfrastructureRepository.findAllWithEagerRelationships(pageable);
        	
        }
    }
    
    @Transactional(readOnly = true)
    public Page<ResearchInfrastructure> findAllPregled(Pageable pageable) {
        log.debug("Request to get all ResearchInfrastructures");                      
        return researchInfrastructureRepository.findAll(pageable);        
    }

    public Page<ResearchInfrastructure> findAllWithEagerRelationshipsPregled(Pageable pageable) {
    	return researchInfrastructureRepository.findAllWithEagerRelationships(pageable);        
    }
    /**
     * Get one researchInfrastructure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResearchInfrastructure> findOne(Long id) {
        log.debug("Request to get ResearchInfrastructure : {}", id);
        return researchInfrastructureRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the researchInfrastructure by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResearchInfrastructure : {}", id);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	researchInfrastructureRepository.deleteById(id);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 Optional<ResearchInfrastructure> optionalRi = researchInfrastructureRepository.findOneWithEagerRelationships(id);
            	 optionalRi.ifPresent(ri -> {
            		 		if(ri.getOwner().getId() == rio.getId()) {// organizacije su iste, pa je dozvoljeno brisanje
            		 			researchInfrastructureRepository.deleteById(id);
            		 		}
            	 		});            	 
             }else if(userRoles.contains("PA")) {
            	 researchInfrastructureRepository.deleteById(id);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 Optional<ResearchInfrastructure> optionalRi = researchInfrastructureRepository.findOneWithEagerRelationships(id);
            	 optionalRi.ifPresent(ri -> {
            		 		if(ri.getOwner().getId() == rio.getId() && 
            		 				ri.getManager().getId() == pUser.getId()) {// organizacije su iste, pa je dozvoljeno brisanje
            		 			researchInfrastructureRepository.deleteById(id);
            		 		}
            	 		});  
             }else {            	 
            	 return ;
             }
        }
        
        
        
        
        
    }

    
    public List<ResearchInfrastructureDTOSearch> searchBySerbianKeywords(SearchDTO search){
    	String keywords = "";
    	int keywordsNotExist = 1;
    	int subdomainsNotExist = 1;
    	List<Long> subdomains = new ArrayList<Long>();
    	subdomains.add(1L);
    	if(!"".equals(search.getSearchTerms().trim())) {    		
    		keywords = search.getSearchTerms().trim().replaceAll("\\s+", " & ");
    		keywordsNotExist = 0;    		
    	}
    	if(search.getSelSubdomains() != null && !search.getSelSubdomains().isEmpty()) {
    		subdomainsNotExist = 0;
    		subdomains.clear();
    		subdomains.addAll(search.getSelSubdomains());
    	}    	
    	
    	return researchInfrastructureRepository.searchBySerbianKeywords(keywordsNotExist, keywords, subdomainsNotExist, subdomains);
    }
    
    public List<ResearchInfrastructureDTOSearch> searchByEnglishKeywords(SearchDTO search){
    	String keywords = "";
    	int keywordsNotExist = 1;
    	int subdomainsNotExist = 1;
    	List<Long> subdomains = new ArrayList<Long>();
    	subdomains.add(1L);
    	if(!"".equals(search.getSearchTerms().trim())) {    		
    		keywords = search.getSearchTerms().trim().replaceAll("\\s+", " & ");
    		keywordsNotExist = 0;    		
    	}
    	if(search.getSelSubdomains() != null && !search.getSelSubdomains().isEmpty()) {
    		subdomainsNotExist = 0;
    		subdomains.clear();
    		subdomains.addAll(search.getSelSubdomains());
    	}    	
    	
    	return researchInfrastructureRepository.searchByEnglishKeywords(keywordsNotExist, keywords, subdomainsNotExist, subdomains);
    }    

    
    /** 
     * Dodaje cirilicni ili latinicni opis i druge osobine
     * @param ri
     */
    private void chageInfrastructureText(ResearchInfrastructure ri) {    	    	
    	
    	if(ri.getDescriptionSr() != null && !"".equals(ri.getDescriptionSr())) {
    		if(Utility.containCyrillic(ri.getDescriptionSr())) {
    			ri.setDescriptionSrCyr(ri.getDescriptionSr());
    			ri.setDescriptionSr(LatCyrUtils.toLatin(ri.getDescriptionSr()));	
    		}else {
    			ri.setDescriptionSrCyr(LatCyrUtils.toCyrillic(ri.getDescriptionSr()));	
    		}			
		}else if(ri.getDescriptionSrCyr() != null && !"".equals(ri.getDescriptionSrCyr())) {
			if(Utility.containCyrillic(ri.getDescriptionSrCyr())) {
    			ri.setDescriptionSr(LatCyrUtils.toLatin(ri.getDescriptionSrCyr()));	
    		}else {
    			ri.setDescriptionSr(ri.getDescriptionSrCyr());
    			ri.setDescriptionSrCyr(LatCyrUtils.toCyrillic(ri.getDescriptionSrCyr()));    			    				
    		}
		}
    	
    	if(ri.getNameSr() != null && !"".equals(ri.getNameSr())) {
    		if(Utility.containCyrillic(ri.getNameSr())) {
    			ri.setNameSrCyr(ri.getNameSr());
    			ri.setNameSr(LatCyrUtils.toLatin(ri.getNameSr()));	
    		}else {
    			ri.setNameSrCyr(LatCyrUtils.toCyrillic(ri.getNameSr()));	
    		}			
		}else if(ri.getNameSrCyr() != null && !"".equals(ri.getNameSrCyr())) {
			if(Utility.containCyrillic(ri.getNameSrCyr())) {
    			ri.setNameSr(LatCyrUtils.toLatin(ri.getNameSrCyr()));	
    		}else {
    			ri.setNameSr(ri.getNameSrCyr());
    			ri.setNameSrCyr(LatCyrUtils.toCyrillic(ri.getNameSrCyr()));    			    				
    		}
		}
    	
    	if(ri.getTechnicalSpecificationSr() != null && !"".equals(ri.getTechnicalSpecificationSr())) {
    		if(Utility.containCyrillic(ri.getTechnicalSpecificationSr())) {
    			ri.setTechnicalSpecificationSrCyr(ri.getTechnicalSpecificationSr());
    			ri.setTechnicalSpecificationSr(LatCyrUtils.toLatin(ri.getTechnicalSpecificationSr()));	
    		}else {
    			ri.setTechnicalSpecificationSrCyr(LatCyrUtils.toCyrillic(ri.getTechnicalSpecificationSr()));	
    		}			
		}else if(ri.getTechnicalSpecificationSrCyr() != null && !"".equals(ri.getTechnicalSpecificationSrCyr())) {
			if(Utility.containCyrillic(ri.getTechnicalSpecificationSrCyr())) {
    			ri.setTechnicalSpecificationSr(LatCyrUtils.toLatin(ri.getTechnicalSpecificationSrCyr()));	
    		}else {
    			ri.setTechnicalSpecificationSr(ri.getTechnicalSpecificationSrCyr());
    			ri.setTechnicalSpecificationSrCyr(LatCyrUtils.toCyrillic(ri.getTechnicalSpecificationSrCyr()));    			    				
    		}
		}
    	
    	if(ri.getKeywords() != null && !"".equals(ri.getKeywords())) {
    		if(Utility.containCyrillic(ri.getKeywords())) {
    			ri.setKeywordsCyr(ri.getKeywords());
    			ri.setKeywords(LatCyrUtils.toLatin(ri.getKeywords()));	
    		}else {
    			ri.setKeywordsCyr(LatCyrUtils.toCyrillic(ri.getKeywords()));	
    		}			
		}else if(ri.getKeywordsCyr() != null && !"".equals(ri.getKeywordsCyr())) {
			if(Utility.containCyrillic(ri.getKeywordsCyr())) {
    			ri.setKeywords(LatCyrUtils.toLatin(ri.getKeywordsCyr()));	
    		}else {
    			ri.setKeywords(ri.getKeywordsCyr());
    			ri.setKeywordsCyr(LatCyrUtils.toCyrillic(ri.getKeywordsCyr()));    			    				
    		}
		}    	    
    }
    
    
    public Long countAll() {
    	return researchInfrastructureRepository.count();
    }
}
