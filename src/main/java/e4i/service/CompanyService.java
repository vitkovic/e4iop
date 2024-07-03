package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Advertisement;
import e4i.domain.Company;
import e4i.domain.PortalUser;
import e4i.domain.Thread;
import e4i.domain.User;
import e4i.repository.CompanyRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }


    /* Get all the advertisements.
    *
    * @param pageable the pagination information.
    * @return the list of entities.
    */
   @Transactional(readOnly = true)
   public Page<Company> findAllBySearch(String search, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       log.debug("Request to get all Advertisements");
       if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
    	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	
    		  return companyRepository.findAllSearch(search,pageable);
    	
       } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
    	      return companyRepository.findAllSearch(search,pageable);
       } else {	 
    	      return companyRepository.findAllSearch(search,pageable);
       } 
   }

    
    
    /**
     * Get all the companies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Company> findAllWithEagerRelationships(Pageable pageable) {
        return companyRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
    
    @Transactional
    public Company findOneByPortalUser(PortalUser portalUser) {
    	Company company = portalUser.getCompany();
        if (company != null) {
        	return company;
    	} else {
    		String errorMessage = String.format("Company for PortalUser with id={} could not be found.", portalUser.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    }
    
    @Transactional
    public Company findOneByThreadSender(Thread thread) {
    	Company company = thread.getCompanySender();
        if (company != null) {
        	return company;
    	} else {
    		String errorMessage = String.format("Company sender for Thread with id={} could not be found.", thread.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    }
    
    @Transactional
    public Company findOneByThreadReceiver(Thread thread) {
    	Company company = thread.getCompanyReceiver();
        if (company != null) {
        	return company;
    	} else {
    		String errorMessage = String.format("Company receiver for Thread with id={} could not be found.", thread.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    }
    
    @Transactional
    public Company getOneById(Long id) {
        log.debug("Request to get Company : {}", id);
        
        Optional<Company> companyOptional = companyRepository.findOneWithEagerRelationships(id);
        
        if (companyOptional.isPresent()) {
        	return companyOptional.get();
    	} else {
    		String errorMessage = String.format("Company with id={} could not be found.", id);
        	throw new EntityNotFoundException(errorMessage);
    	}
    }
}
