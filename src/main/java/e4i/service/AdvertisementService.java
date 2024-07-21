package e4i.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementStatus;
import e4i.domain.Collaboration;
import e4i.domain.Company;
import e4i.domain.PortalUser;
import e4i.domain.User;
import e4i.repository.AdvertisementRepository;
import e4i.repository.PortalUserRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link Advertisement}.
 */
@Service
@Transactional
public class AdvertisementService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementService.class);

    private final AdvertisementRepository advertisementRepository;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    AdvertisementStatusService advertisementStatusService; 

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    /**
     * Save a advertisement.
     *
     * @param advertisement the entity to save.
     * @return the persisted entity.
     */
    public Advertisement save(Advertisement advertisement) {
        log.debug("Request to save Advertisement : {}", advertisement);
        
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return advertisementRepository.save(advertisement);
        } else {
       	 Optional<User> currentUser = userService.getUserWithAuthorities();
         User user = currentUser.get();             
         PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
         Company company = pUser.getCompany();
         advertisement.setCompany(company);
         
         return advertisementRepository.save(advertisement);
        }
    }

    /**
     * Get all the advertisements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Advertisement> findAll(Pageable pageable) {
        log.debug("Request to get all Advertisements");
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return advertisementRepository.findAll(pageable);
        } else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             return advertisementRepository.findByCompanyId(pUser.getCompany().getId() ,pageable);
             
//             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//             if(userRoles.contains("RPRIPO")) {
//            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//            	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//            	 return out;
//             }else if(userRoles.contains("PA")) {
//            	 return researchInfrastructureRepository.findAll(pageable);
//             }else if(userRoles.contains("RPRI")) {
//            	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//             }else {
//            	 return null;
//             }
        }   
    }
    
    /**
     * Get all the advertisements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Advertisement> findAllFree(Pageable pageable) {
        log.debug("Request to get all Advertisements");
        //if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return advertisementRepository.findAll(pageable);
      //  } else {
       /*
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             return advertisementRepository.findByCompanyId(pUser.getCompany().getId() ,pageable);
             
//             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//             if(userRoles.contains("RPRIPO")) {
//            	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//            	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//            	 return out;
//             }else if(userRoles.contains("PA")) {
//            	 return researchInfrastructureRepository.findAll(pageable);
//             }else if(userRoles.contains("RPRI")) {
//            	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//             }else {
//            	 return null;
//             }
        }
        */   
    }

    /* Get all the advertisements.
    *
    * @param pageable the pagination information.
    * @return the list of entities.
    */
   @Transactional(readOnly = true)
   public Page<Advertisement> findAllBySearch(String search, Long category, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       log.debug("Request to get all Advertisements");
       if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
    	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	  if (category != 1) 
    		  return advertisementRepository.findAllBySearchAdmin(search,pageable);
    	  else 
    		  return advertisementRepository.findAllBySearchAdminbyCategory(search,category,pageable);
       } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
    	 
    	   if (category != 1) 
           	return advertisementRepository.findAllBySearchAdminbyCategory(search,category ,pageable);
           else
           	return advertisementRepository.findAllBySearchAdmin(search, pageable);
           
    	   
       } else {
    	   
    	   
    	   if (category != 1) 
              	return advertisementRepository.findAllBySearchAdminbyCategory(search,category ,pageable);
              else
              	return advertisementRepository.findAllBySearchAdmin(search, pageable);
    	   
    	/*   
    	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
       	    Optional<User> currentUser = userService.getUserWithAuthorities();
            User user = currentUser.get();
            
            PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
            if (category != 1) 
            	return advertisementRepository.findSearchAllByCompanyIdbyCategory(search, pUser.getCompany().getId(), category ,pageable);
            else
            	return advertisementRepository.findSearchAllByCompanyId(search, pUser.getCompany().getId() ,pageable);
            
//            List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//            if(userRoles.contains("RPRIPO")) {
//           	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//           	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//           	 return out;
//            }else if(userRoles.contains("PA")) {
//           	 return researchInfrastructureRepository.findAll(pageable);
//            }else if(userRoles.contains("RPRI")) {
//           	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//            }else {
//           	 return null;
//            }*/
       }   
   }
  
   
   /* Get all the advertisements.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<Advertisement> findAllBySearchStatus(String search, Long status, Long category, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
      log.debug("Request to get all Advertisements");
      if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
   	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
     	  if (category != 1) 
   		  return advertisementRepository.findAllBySearchAdminStatus(search,status,pageable);
   	  else 
   		  return advertisementRepository.findAllBySearchAdminbyCategoryStatus(search, status, category,pageable);
      } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
   	 
   	   if (category != 1) 
          	return advertisementRepository.findAllBySearchAdminbyCategoryStatus(search,status, category ,pageable);
          else
          	return advertisementRepository.findAllBySearchAdminStatus(search,status, pageable);
          
   	   
      } else {
    	  
    	  

      	   if (category != 1) 
             	return advertisementRepository.findAllBySearchAdminbyCategoryStatus(search,status, category ,pageable);
             else
             	return advertisementRepository.findAllBySearchAdminStatus(search,status, pageable);
      	   
      	   /*
   	   
   	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      	    Optional<User> currentUser = userService.getUserWithAuthorities();
           User user = currentUser.get();
           
           PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
           if (category != 1) 
           	return advertisementRepository.findSearchAllByCompanyIdbyCategoryStatus(search, pUser.getCompany().getId(), status,  category ,pageable);
           else
           	return advertisementRepository.findSearchAllByCompanyIdStatus(search, status, pUser.getCompany().getId() ,pageable);
           
//           List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//           if(userRoles.contains("RPRIPO")) {
//          	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//          	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//          	 return out;
//           }else if(userRoles.contains("PA")) {
//          	 return researchInfrastructureRepository.findAll(pageable);
//           }else if(userRoles.contains("RPRI")) {
//          	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//           }else {
//          	 return null;
//           }*/
      }   
  }
  
  
  /* Get all the advertisements.
  *
  * @param pageable the pagination information.
  * @return the list of entities.
  */
 @Transactional(readOnly = true)
 public Page<Advertisement> findAllBySearchType(Long type, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
     log.debug("Request to get all Advertisements");
     if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
  	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	
  		  return advertisementRepository.findAllBySearchAdminType(type,pageable);
  	
     } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
  	 
  	     	return advertisementRepository.findAllBySearchAdminType(type ,pageable);
         
  	   
     } else {
  	   
    	 
    		return advertisementRepository.findAllBySearchAdminType(type ,pageable);
    	/*	
  	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
     	    Optional<User> currentUser = userService.getUserWithAuthorities();
          User user = currentUser.get();
          
          PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
         
          	return advertisementRepository.findSearchAllByCompanyIdbyType(type, pUser.getCompany().getId(), pageable);
          
//          List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//          if(userRoles.contains("RPRIPO")) {
//         	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//         	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//         	 return out;
//          }else if(userRoles.contains("PA")) {
//         	 return researchInfrastructureRepository.findAll(pageable);
//          }else if(userRoles.contains("RPRI")) {
//         	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//          }else {
//         	 return null;
//          }*/
     }   
 }

 /* Get all the advertisements.
 *
 * @param pageable the pagination information.
 * @return the list of entities.
 */
@Transactional(readOnly = true)
public Page<Advertisement> findAllBySearchTypeStatus(Long type, Long status, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    log.debug("Request to get all Advertisements");
    if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
 	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
   	
 		  return advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
 	
    } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
 	 
    	 return advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
        
 	   
    } else {
 	   /*
 	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	    Optional<User> currentUser = userService.getUserWithAuthorities();
         User user = currentUser.get();
         
         PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        
         	return advertisementRepository.findSearchAllByCompanyIdbyTypeStatus(type, pUser.getCompany().getId(),status, pageable);
         
//         List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//         if(userRoles.contains("RPRIPO")) {
//        	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//        	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//        	 return out;
//         }else if(userRoles.contains("PA")) {
//        	 return researchInfrastructureRepository.findAll(pageable);
//         }else if(userRoles.contains("RPRI")) {
//        	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//         }else {
//        	 return null;
//         }
 */
  
    		return advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
 
    }   
}

    /**
     * Get all the advertisements with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Advertisement> findAllWithEagerRelationships(Pageable pageable) {
        return advertisementRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one advertisement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Advertisement> findOne(Long id) {
        log.debug("Request to get Advertisement : {}", id);
        return advertisementRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the advertisement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Advertisement : {}", id);
        advertisementRepository.deleteById(id);
    }
    
    @Transactional
    public Advertisement findOneByIdFromOptional(Long id) {
    	Optional<Advertisement> advertisementOptional = this.findOne(id);
        if (advertisementOptional.isEmpty()) {
    		String errorMessage = String.format("Advertisement with id={} could not be found", id);
        	throw new EntityNotFoundException(errorMessage);
        }
        
        Advertisement advertisement = advertisementOptional.get();
        
        return advertisement;
    }
    
    @Transactional
    public Advertisement findOneByCollaboration(Collaboration collaboration) {
        Advertisement advertisement = collaboration.getAdvertisement();
        if (advertisement != null) {
        	return advertisement;
    	} else {
    		String errorMessage = String.format("Advertisement for collaboration with id={} could not be found.", collaboration.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
    }
    
    @Transactional
    public Advertisement changeStatus(Advertisement advertisement, String status) {
    	Optional<AdvertisementStatus> advertisementStatusOptional = advertisementStatusService.findOneByAnyStatus(status);
        if (advertisementStatusOptional.isEmpty()) {
    		String errorMessage = String.format("AdvertisementStatus with status={} could not be found", status);
        	throw new EntityNotFoundException(errorMessage);
        }
        
        AdvertisementStatus advertisementStatus = advertisementStatusOptional.get();
        advertisement.setStatus(advertisementStatus);
        
        Advertisement result = this.save(advertisement);
        
        return result;
    }
    
    @Transactional
    public Page<Advertisement> findAllByCompanyIdAndStatusId(Long companyId, Long statusId, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandStatusId(companyId, statusId, pageable);
    	
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
    }
    
    @Transactional
    public Page<Advertisement> findAllByCompanyIdAndNotStatusId(Long companyId, Long statusId, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandNotStatusId(companyId, statusId, pageable);
    	
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
    }
    
    @Transactional
    public Page<Advertisement> findAllByStatusId(Long statusId, Pageable pageable) {
    	return advertisementRepository.findAllByStatusId(statusId, pageable);
    }
    
    @Transactional
    public Page<Advertisement> findAllByCompanyIdAndTypeId(Long companyId, Long typeId, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandTypeId(companyId, typeId, pageable);
    	
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
    }

    @Transactional
	public Page<Advertisement> findAllByCompanyIdandStatus(Long companyId, String status, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandStatus(companyId, status, pageable);
    	
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
	}
    
    @Transactional
	public Long getCountAllForCompany(Long companyId) {
        log.debug("Request to get count for all Advertisments for Company : {}", companyId);
        return advertisementRepository.countByCompanyIdAndNotStatus(companyId, AdvertisementStatus.ARCHIVED);
	}
    
    @Transactional
	public Long getCountActiveForCompany(Long companyId) {        
        log.debug("Request to get count for active Advertisments for Company : {}", companyId);
        return advertisementRepository.countByCompanyIdAndStatus(companyId, AdvertisementStatus.ACTIVE);
	}
    
    @Transactional
	public Long getCountInactiveForCompany(Long companyId) {        
        log.debug("Request to get count for inactive Advertisments for Company : {}", companyId);
        return advertisementRepository.countByCompanyIdAndStatus(companyId, AdvertisementStatus.INACTIVE);
	}
}
