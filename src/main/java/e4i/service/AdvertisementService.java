package e4i.service;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
    
   
    //private final AdvertisementStatus advertisementStatus = null; 
    
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
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        advertisementRepository
            .findAllByActivatedLater(Calendar.getInstance().getTime().toString())
            .forEach(adv -> {
                log.debug("Activating advertisement {}", adv);
                Optional<AdvertisementStatus> advertisementStatusOptional = advertisementStatusService.findOne(Long.getLong("3551"));
                AdvertisementStatus advertisementStatus = advertisementStatusOptional.get();
                adv.setStatus(advertisementStatus);
                Advertisement result = this.save(adv);
         
            });
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
        
        Page<Advertisement> advertisements;
        if (userService.isCurrentUserInRole(AuthoritiesConstants.ADMIN, AuthoritiesConstants.CMS_ADMIN)) {              
        	advertisements = advertisementRepository.findAllAdvertisements(pageable);
        	
        	// Could this be done within a query???
        	advertisements.getContent().forEach(advertisement -> {
        		Hibernate.initialize(advertisement.getKinds());
            });
        	
        	return advertisements;
        } else if (userService.isCurrentUserInRole(AuthoritiesConstants.COMPANY_ADMIN, AuthoritiesConstants.COMPANY_USER)) {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();
             
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   " + pUser);
             
             if (pUser.getCompany() != null) {
            	 advertisements = advertisementRepository.findByCompanyId(pUser.getCompany().getId() ,pageable);
                 
             	// Could this be done within a query???
             	advertisements.getContent().forEach(advertisement -> {
             		Hibernate.initialize(advertisement.getKinds());
                 });
             	
             	return advertisements;
             } else {
                 String errorMessage = String.format("Current PortalUser with ID {} is not associated with any Company!", pUser.getId());
                 throw new IllegalStateException(errorMessage);
             }
        } else {
            String errorMessage = "Current User has no required authorities!";
            throw new IllegalStateException(errorMessage);
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
        Page<Advertisement> advertisements = advertisementRepository.findAll(pageable);
        
    	// Could this be done within a query???
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    
    	return advertisements;
        
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
	  // System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       log.debug("Request to get all Advertisements");
       
       Page<Advertisement> advertisements = null;

       if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
    	  //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	  if (category == 1) 
      		advertisements = advertisementRepository.findAllBySearchAdmin(search,pageable);
    	  else 
    		  advertisements = advertisementRepository.findAllBySearchAdminbyCategory(search,category,pageable);
       } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
    	 
    	   if (category != 1) 
    		   advertisements = advertisementRepository.findAllBySearchAdminbyCategory(search,category ,pageable);
           else
        	   advertisements = advertisementRepository.findAllBySearchAdmin(search, pageable);
           
    	   
       } else {
    	   
    	   
    	   if (category != 1) 
    		   advertisements = advertisementRepository.findAllBySearchAdminbyCategory(search,category ,pageable);
              else
            	  advertisements = advertisementRepository.findAllBySearchAdmin(search, pageable);
    	   
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
       
   	// Could this be done within a query???
   	advertisements.getContent().forEach(advertisement -> {
   		Hibernate.initialize(advertisement.getKinds());
       });
   
   	return advertisements;
   }
   
   
   /* Get all the advertisements.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public Page<Advertisement> findAllBySearchSub(String search, Long category, Pageable pageable) {
	  // System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
      log.debug("Request to get all Advertisements");
      
      Page<Advertisement> advertisements = null;

      if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
   	  //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
     	  if (category == 1) 
     		advertisements = advertisementRepository.findAllBySearchAdmin(search,pageable);
   	  else 
   		  advertisements = advertisementRepository.findAllBySearchAdminbyCategorySub(search,category,pageable);
      } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
   	 
   	   if (category != 1) 
   		   advertisements = advertisementRepository.findAllBySearchAdminbyCategorySub(search,category ,pageable);
          else
       	   advertisements = advertisementRepository.findAllBySearchAdmin(search, pageable);
          
   	   
      } else {
   	   
   	   
   	   if (category != 1) 
   		   advertisements = advertisementRepository.findAllBySearchAdminbyCategorySub(search,category ,pageable);
             else
           	  advertisements = advertisementRepository.findAllBySearchAdmin(search, pageable);
   	   
   	/*   
   	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      	    Optional<User> currentUser = userService.getUserWithAuthorities();
           User user = currentUser.get();
           
           PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
           if (category != 1) 
           	return advertisementRepository.findSearchAllByCompanyIdbyCategory(search, pUser.getCompany().getId(), category ,pageable);
           else
           	return advertisementRepository.findSearchAllByCompanyId(search, pUser.getCompany().getId() ,pageable);
           
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
      
  	// Could this be done within a query???
  	advertisements.getContent().forEach(advertisement -> {
  		Hibernate.initialize(advertisement.getKinds());
      });
  
  	return advertisements;
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
 public Page<Advertisement> findAllBySearchStatusSub(String search, Long status, Long category, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
     log.debug("Request to get all Advertisements");
     if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
  	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	  if (category != 1) 
  		  return advertisementRepository.findAllBySearchAdminStatus(search,status,pageable);
  	  else 
  		  return advertisementRepository.findAllBySearchAdminbyCategoryStatusSub(search, status, category,pageable);
     } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
  	 
  	   if (category != 1) 
         	return advertisementRepository.findAllBySearchAdminbyCategoryStatusSub(search,status, category ,pageable);
         else
         	return advertisementRepository.findAllBySearchAdminStatus(search,status, pageable);
         
  	   
     } else {
   	  
   	  

     	   if (category != 1) 
            	return advertisementRepository.findAllBySearchAdminbyCategoryStatusSub(search,status, category ,pageable);
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
 
 /* Get all the advertisements.
 *
 * @param pageable the pagination information.
 * @return the list of entities.
 */
@Transactional(readOnly = true)
public Page<Advertisement> findAllBySearchCompany(Long type, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    log.debug("Request to get all Advertisements");
    if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
 	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
   	
 		  return advertisementRepository.findAllBySearchAdminCompany(type,pageable);
 	
    } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
 	 
 	     	return advertisementRepository.findAllBySearchAdminCompany(type ,pageable);
        
 	   
    } else {
 	   
   	 
   		return advertisementRepository.findAllBySearchAdminCompany(type ,pageable);
   	/*	
 	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	    Optional<User> currentUser = userService.getUserWithAuthorities();
         User user = currentUser.get();
         
         PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        
         	return advertisementRepository.findSearchAllByCompanyIdbyType(type, pUser.getCompany().getId(), pageable);
         
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
//         }*/
    }   
}

/* Get all the advertisements.
*
* @param pageable the pagination information.
* @return the list of entities.
*/
@Transactional(readOnly = true)
public Page<Advertisement> findAllBySearchKind(Long kind, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    log.debug("Request to get all Advertisements");
    if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
 	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
   	
 		  return advertisementRepository.findAllBySearchAdminKind(kind,pageable);
 	
    } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
 	 
 	     	return advertisementRepository.findAllBySearchAdminKind(kind ,pageable);
        
 	   
    } else {
 	   
   	 
   		return advertisementRepository.findAllBySearchAdminKind(kind ,pageable);
   	/*	
 	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    	    Optional<User> currentUser = userService.getUserWithAuthorities();
         User user = currentUser.get();
         
         PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        
         	return advertisementRepository.findSearchAllByCompanyIdbyType(type, pUser.getCompany().getId(), pageable);
         
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
//         }*/
    }   
}
/* Get all the advertisements.
*
* @param pageable the pagination information.
* @return the list of entities.
*/
@Transactional(readOnly = true)
public Page<Advertisement> findAllBySearchDates(String from, String to, Pageable pageable) {
	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
   log.debug("Request to get all Advertisements");
   if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
  	
		  return advertisementRepository.findAllBySearchAdminDates(from, to, pageable);
	
   } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
	 
	     	return advertisementRepository.findAllBySearchAdminDates(from, to ,pageable);
       
	   
   } else {
	   
  	 
  		return advertisementRepository.findAllBySearchAdminDates(from, to ,pageable);
  	/*	
	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
   	    Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
       
        	return advertisementRepository.findSearchAllByCompanyIdbyType(type, pUser.getCompany().getId(), pageable);
        
//        List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
//        if(userRoles.contains("RPRIPO")) {
//       	 RiResearchOrganization rio = riResearchOrganizationRepository.findByPuOrganizationId(pUser.getUserOrganization().getId());
//       	 Page<ResearchInfrastructure> out = researchInfrastructureRepository.findByOwnerId(rio.getId(), pageable);
//       	 return out;
//        }else if(userRoles.contains("PA")) {
//       	 return researchInfrastructureRepository.findAll(pageable);
//        }else if(userRoles.contains("RPRI")) {
//       	 return researchInfrastructureRepository.findByManagerId(pUser.getId(), pageable);
//        }else {
//       	 return null;
//        }*/
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

    Page<Advertisement> advertisements = null;
    
    if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){ 
 	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
   	
 	 advertisements = advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
 	
    } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS)) {
 	 
    	advertisements = advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
        
 	   
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
  
    	advertisements = advertisementRepository.findAllBySearchAdminTypeStatus(type,status, pageable);
 
    }   
    
   	// Could this be done within a query???
   	advertisements.getContent().forEach(advertisement -> {
   		Hibernate.initialize(advertisement.getKinds());
       });
   
   	return advertisements;
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
    	Page<Advertisement> advertisements = advertisementRepository.findAllByStatusId(statusId, pageable);
    	
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
    }
    
    @Transactional
    public Page<Advertisement> findAllByCompanyIdAndTypeId(Long companyId, Long typeId, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandTypeId(companyId, typeId, pageable);
    	
    	// Could this be done within a query???
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
    }
    

	public Page<Advertisement> findAllForCompanyByStatusAndType(Long companyId, String status, String type,
			Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandStatusAndType(companyId, status, type, pageable);
    	
    	// Could this be done within a query???
    	advertisements.getContent().forEach(advertisement -> {
    		Hibernate.initialize(advertisement.getKinds());
        });
    	
    	return advertisements;
	}

    @Transactional
	public Page<Advertisement> findAllByCompanyIdandStatus(Long companyId, String status, Pageable pageable) {
    	Page<Advertisement> advertisements = advertisementRepository.findAllByCompanyIdandAnyStatus(companyId, status, pageable);
    	
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
