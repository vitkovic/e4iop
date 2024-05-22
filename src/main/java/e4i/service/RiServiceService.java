package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUser;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiResearchOrganization;
import e4i.domain.RiService;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.util.LatCyrUtils;
import e4i.util.Utility;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.RiServiceInfrastructureOrganizationDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RiService}.
 */
@Service
@Transactional
public class RiServiceService {

    private final Logger log = LoggerFactory.getLogger(RiServiceService.class);

    private final RiServiceRepository riServiceRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;
    private final ResearchInfrastructureRepository researchInfrastructureRepository;

  
	public RiServiceService(RiServiceRepository riServiceRepository, UserService userService,
			PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository,
			ResearchInfrastructureRepository researchInfrastructureRepository) {
		this.riServiceRepository = riServiceRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.researchInfrastructureRepository = researchInfrastructureRepository;
	}

	/**
     * Save a riService.
     *
     * @param riService the entity to save.
     * @return the persisted entity.
     */
    public RiService save(RiService riService) {
        log.debug("Request to save RiService : {}", riService);
        
        this.chageServiceText(riService);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return riServiceRepository.save(riService);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByOwnerId(rio.getId());
            	 if(riList.contains(riService.getResearchInfrastructure())) {
            		 return riServiceRepository.save(riService);
            	 }else {
                     throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema"); 
            	 }
             }else if(userRoles.contains("PA")) {
            	 return riServiceRepository.save(riService);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByManagerId(pUser.getId());            	 
            	 if(riList.contains(riService.getResearchInfrastructure())) {
            		 return riServiceRepository.save(riService);
            	 }else {
                     throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema"); 
            	 }            	             	 
             }else {
            	 throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema");
             }
        }
        
        
        
        
        
    }

    /**
     * Get all the riServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RiService> findAll(Pageable pageable) {
        log.debug("Request to get all RiServices"); 
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return riServiceRepository.findAll(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByOwnerId(rio.getId());            	 
            	 Page<RiService> out = riServiceRepository.findByResearchInfrastructureIn(riList, pageable);
            	 return out;
             }else if(userRoles.contains("PA")) {
            	 return riServiceRepository.findAll(pageable);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByManagerId(pUser.getId());            	 
            	 Page<RiService> out = riServiceRepository.findByResearchInfrastructureIn(riList, pageable);
            	 return out;            	             	 
             }else {
            	 return null;
             }
        }
    }


    /**
     * Get all the riServices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RiService> findAllWithEagerRelationships(Pageable pageable) {
        return riServiceRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one riService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RiService> findOne(Long id) {
        log.debug("Request to get RiService : {}", id);
        return riServiceRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the riService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RiService : {}", id);
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	riServiceRepository.deleteById(id);
        }else {        	
        	Optional<RiService> optionalRiService = riServiceRepository.findById(id);
        	RiService riService =optionalRiService.get();
        	
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             if(userRoles.contains("RPRIPO")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByOwnerId(rio.getId());
            	 if(riList.contains(riService.getResearchInfrastructure())) {
            		 riServiceRepository.deleteById(id);
            	 }else {
                     throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema"); 
            	 }
             }else if(userRoles.contains("PA")) {
            	 riServiceRepository.deleteById(id);
             }else if(userRoles.contains("RPRI")) {
            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
            	 List<ResearchInfrastructure> riList = researchInfrastructureRepository.findByManagerId(pUser.getId());            	 
            	 if(riList.contains(riService.getResearchInfrastructure())) {
            		 riServiceRepository.deleteById(id);
            	 }else {
                     throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema"); 
            	 }            	             	 
             }else {
            	 throw new BadRequestAlertException("Nemate pravo na upis", "RiService", "nema");
             }
        }        
    }
    
    public List<RiServiceDTOSearch> searchBySerbianKeywords(SearchDTO search){
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
    	
    	return riServiceRepository.searchBySerbianKeywords(keywordsNotExist, keywords, subdomainsNotExist, subdomains);
    }
    
    public List<RiServiceInfrastructureOrganizationDTO> searchAllBySerbianKeywords(SearchDTO search){
    	String keywords = "";
    	int keywordsNotExist = 1;
    	int subdomainsNotExist = 1;
    	List<Long> subdomains = new ArrayList<Long>();
    	subdomains.add(1L);
    	if(!"".equals(search.getSearchTerms().trim())) {
    		keywords = search.getSearchTerms().trim().replaceAll("\\s+", " & ");   		
    		keywordsNotExist = 0;    		
    	}    	   	
    	
    	return riServiceRepository.searchAllBySerbianKeywords(keywords);
    }
    
    public List<RiServiceInfrastructureOrganizationDTO> searchAllByEnglishKeywords(SearchDTO search){
    	String keywords = "";
    	List<Long> subdomains = new ArrayList<Long>();
    	subdomains.add(1L);
    	if(!"".equals(search.getSearchTerms().trim())) {
    		keywords = search.getSearchTerms().trim().replaceAll("\\s+", " & ");  		    		
    	}    	   	
    	
    	return riServiceRepository.searchAllByEnglishKeywords(keywords);
    }
    
    public List<RiServiceDTOSearch> searchByEnglishKeywords(SearchDTO search){
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
    	
    	return riServiceRepository.searchByEnglishKeywords(keywordsNotExist, keywords, subdomainsNotExist, subdomains);    	
    }
    
    /** 
     * Dodaje cirilicni ili latinicni opis i druge osobine
     * @param ri
     */
    private void chageServiceText(RiService ri) { 
    	
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
    
    /**
     * Prikazuju se sve usluge, ali mogu samo da se gledaju, ne i da se menjaju
     * @param pageable
     * @return
     */
    public Page<RiService> findAllPregled(Pageable pageable) {
        log.debug("Request to get all RiServices");                      
        return riServiceRepository.findAll(pageable);        
    }
    
    public Long countAll() {
    	return riServiceRepository.count();
    }
}
