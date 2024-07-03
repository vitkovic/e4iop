package e4i.service;

import e4i.domain.CmsNews;
import e4i.repository.CmsNewsRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsNews}.
 */
@Service
@Transactional
public class CmsNewsService {

    private final Logger log = LoggerFactory.getLogger(CmsNewsService.class);

    private final CmsNewsRepository cmsNewsRepository;

    @Autowired
    public CmsNewsService(CmsNewsRepository cmsNewsRepository) {
        this.cmsNewsRepository = cmsNewsRepository;
    }

    /**
     * Save a cmsNews.
     *
     * @param cmsNews the entity to save.
     * @return the persisted entity.
     */
    public CmsNews createOrUpdateCmsNews(CmsNews cmsNews) {
        log.debug("Request to save CmsNews : {}", cmsNews);
        return cmsNewsRepository.save(cmsNews);
    }

    /**
     * Get all the cmsNews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsNews> getAllCmsNews(Pageable pageable) {
        log.debug("Request to get all CmsNews");
        return cmsNewsRepository.findAll(pageable);
    }

    /**
     * Get one cmsNews by id.
     *
     * @param id the id of the entity.
     * @return Optional containing the entity, or empty if not found.
     */
    @Transactional(readOnly = true)
    public Optional<CmsNews> getCmsNewsById(Long id) {
        log.debug("Request to get CmsNews : {}", id);
        return cmsNewsRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<CmsNews> findOne(Long id) {
        log.debug("Request to get Advertisement : {}", id);
        return cmsNewsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cmsNews by id.
     *
     * @param id the id of the entity.
     */
    public void deleteCmsNews(Long id) {
        log.debug("Request to delete CmsNews : {}", id);
        cmsNewsRepository.deleteById(id);
    }
    
    /* Get all the advertisements.
    *
    * @param pageable the pagination information.
    * @return the list of entities.
    */
   @Transactional(readOnly = true)
   public Page<CmsNews> findAllBySearch(String search, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       log.debug("Request to get all Advertisements");
       if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
    	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	
    		  return cmsNewsRepository.findAllSearch(search,pageable);
    	
       } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
    	      return cmsNewsRepository.findAllSearch(search,pageable);
       } else {	 
    	      return cmsNewsRepository.findAllSearch(search,pageable);
       } 
   }
    
    
}
