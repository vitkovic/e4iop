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
import e4i.domain.Collaboration;
import e4i.domain.CollaborationRating;
import e4i.domain.CollaborationStatus;
import e4i.domain.PortalUser;
import e4i.repository.CollaborationRepository;
import e4i.web.rest.dto.CompanyRatingsDTO;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link Collaboration}.
 */
@Service
@Transactional
public class CollaborationService {

    private final Logger log = LoggerFactory.getLogger(CollaborationService.class);

    private final CollaborationRepository collaborationRepository;
    
    @Autowired
    CollaborationStatusService collaborationStatusService;
    
    public CollaborationService(CollaborationRepository collaborationRepository) {
        this.collaborationRepository = collaborationRepository;
    }

    /**
     * Save a collaboration.
     *
     * @param collaboration the entity to save.
     * @return the persisted entity.
     */
    public Collaboration save(Collaboration collaboration) {
        log.debug("Request to save Collaboration : {}", collaboration);
        return collaborationRepository.save(collaboration);
    }

    /**
     * Get all the collaborations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Collaboration> findAll(Pageable pageable) {
        log.debug("Request to get all Collaborations");
        return collaborationRepository.findAll(pageable);
    }


    /**
     * Get all the collaborations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Collaboration> findAllByStatusQ(Long status, Pageable pageable) {
        log.debug("Request to get all Collaborations");
        return collaborationRepository.findAllByStatusQ(status, pageable);
    }

    /**
     * Get all the collaborations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Collaboration> findAllByStatusandDates(String from, String to, Long status, Pageable pageable) {
        log.debug("Request to get all Collaborations");
        return collaborationRepository.findAllByStatusQandDates(from, to, status, pageable);
    }
    
    
    /**
     * Get one collaboration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Collaboration> findOne(Long id) {
        log.debug("Request to get Collaboration : {}", id);
        return collaborationRepository.findById(id);
    }

    /**
     * Delete the collaboration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Collaboration : {}", id);
        collaborationRepository.deleteById(id);
    }
    
    @Transactional
    public Collaboration createCollaborationForAdvertisementAndPortalUserCompany(Advertisement advertisement, PortalUser portalUser) {
             
        Collaboration collaboration = new Collaboration();
        collaboration.setAdvertisement(advertisement);
        collaboration.setCompanyOffer(advertisement.getCompany());
        collaboration.setCompanyRequest(portalUser.getCompany());
        collaboration.setDatetime(Instant.now());
        
        // "isAccepted" is not used anymore, instead, use "status"
//        collaboration.setIsAccepted(false);
        
        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.PENDING); 
        collaboration.setStatus(collaborationStatus);

        Collaboration result = this.save(collaboration);
        
        return result; 
    };
    
    @Transactional
    public Collaboration confirmCollaboration(Long id) {
    	Optional<Collaboration> collaborationOptional = collaborationRepository.findById(id);
        
    	if (collaborationOptional.isEmpty()) {
    		String errorMessage = String.format("Collaboration with id={} could not be found", id);
        	throw new EntityNotFoundException(errorMessage);
        }
    	
    	Collaboration collaboration = collaborationOptional.get();

    	// "isAccepted" is not used anymore, instead, use "status"
//    	collaboration.setIsAccepted(true);

        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.ACCEPTED); 
        collaboration.setStatus(collaborationStatus);
    	collaboration.setDatetime(Instant.now());
    	
    	Collaboration result = collaborationRepository.save(collaboration);
    	
    	return result;
    }
    
    @Transactional
    public Collaboration cancelCollaboration(Long id) {
    	Optional<Collaboration> collaborationOptional = collaborationRepository.findById(id);
        
    	if (collaborationOptional.isEmpty()) {
    		String errorMessage = String.format("Collaboration with id={} could not be found", id);
        	throw new EntityNotFoundException(errorMessage);
        }
    	
    	Collaboration collaboration = collaborationOptional.get();
        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.REJECTED); 
        collaboration.setStatus(collaborationStatus);
    	collaboration.setDatetime(Instant.now());
    	
    	Collaboration result = collaborationRepository.save(collaboration);
    	
    	return result;
    }
    
    @Transactional
    public Collaboration updateCollaborationRatingForCompanyOffer(Long collaborationId, CollaborationRating rating, String comment) {
    	Optional<Collaboration> collaborationOptional = collaborationRepository.findById(collaborationId);
        
    	if (collaborationOptional.isEmpty()) {
    		String errorMessage = String.format("Collaboration with id={} could not be found", collaborationId);
        	throw new EntityNotFoundException(errorMessage);
        }
    	
    	Collaboration collaboration = collaborationOptional.get();
    	collaboration.setRatingOffer(rating);
    	collaboration.setCommentOffer(comment);
    	
    	Collaboration result = collaborationRepository.save(collaboration);
    	
    	return result;
    }
    
    @Transactional
    public Collaboration updateCollaborationRatingForCompanyRequest(Long collaborationId, CollaborationRating rating, String comment) {
    	Optional<Collaboration> collaborationOptional = collaborationRepository.findById(collaborationId);
        
    	if (collaborationOptional.isEmpty()) {
    		String errorMessage = String.format("Collaboration with id={} could not be found", collaborationId);
        	throw new EntityNotFoundException(errorMessage);
        }
    	
    	Collaboration collaboration = collaborationOptional.get();
    	collaboration.setRatingRequest(rating);
    	collaboration.setCommentRequest(comment);
    	
    	Collaboration result = collaborationRepository.save(collaboration);
    	
    	return result;
    }
    
    @Transactional
    public Long countPendingCollaborationsForAdvertisement(Long advertisementId) { 	
    	return collaborationRepository.countByAdvertisementIdAndStatusStatus(advertisementId, CollaborationStatus.PENDING);
    }
    
    @Transactional
    public List<Collaboration> findAllPendingCollaborationsForAdvertisement(Long advertisementId) { 	
    	return collaborationRepository.findAllByAdvertisementIdAndStatusStatus(advertisementId, CollaborationStatus.PENDING);
    }
    
    @Transactional
    public Page<Collaboration> findAllFilteredForCompany(
    		Long companyId,
    		List<Long> statusIds,
    		List<Boolean> collaborationSideFlags,
    		List<Boolean> ratingSideFlags,
    		Pageable pageable) {    	
    	if (collaborationSideFlags.size() != 2) {
    		String errorMessage = String.format("List 'collaborationSideflags' must be of length 2, but is {}", collaborationSideFlags.size());
        	throw new IllegalArgumentException(errorMessage);
    	}
    	
    	if (ratingSideFlags.size() != 2) {
    		String errorMessage = String.format("List 'ratingSideFlags' must be of length 2, but is {}", ratingSideFlags.size());
        	throw new IllegalArgumentException(errorMessage);
    	}
    	
    	Page<Collaboration> collaborationsPage = collaborationRepository.findAllFilteredForCompany(
    			companyId, statusIds, collaborationSideFlags.get(0), collaborationSideFlags.get(1),
    			ratingSideFlags.get(0), ratingSideFlags.get(1), pageable
    			);
    	
    	// Could this be done within a query???
    	collaborationsPage.getContent().forEach(collaboration -> {
    		Hibernate.initialize(collaboration.getAdvertisement().getKinds());
        });
    	
    	return collaborationsPage;
    }
    
    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompany(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyAndStatus(companyId, collaborationStatus.getId(), pageable);
    }

    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompanyOffer(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyOfferAndStatus(companyId, collaborationStatus.getId(), pageable);
    }
    
    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompanyRequest(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByAnyStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyRequestAndStatus(companyId, collaborationStatus.getId(), pageable);
    }

    @Transactional
	public List<Collaboration> findAllAcceptedCollaborationsForCompany(Long companyId) {
    	String status = CollaborationStatus.ACCEPTED;
    	
		return collaborationRepository.findByCompanyOfferIdOrCompanyRequestIdAndStatusStatusEn(companyId, companyId, status);
	}

    @Transactional
	public CompanyRatingsDTO getCompanyRatings(List<Collaboration> collaborations, Long companyId) {
		Long totalRatings = 0L;
		Long totalRatings1 = 0L;
		Long totalRatings2 = 0L;
		Long totalRatings3 = 0L;
		Long totalRatings4 = 0L;
		
		for (Collaboration collaboration : collaborations) {
			CollaborationRating rating = null;
			
			if (collaboration.getCompanyOffer().getId().equals(companyId) && collaboration.getRatingRequest() != null) {
				rating = collaboration.getRatingRequest();					
			} else if (collaboration.getCompanyRequest().getId().equals(companyId) && collaboration.getRatingOffer() != null) {
				rating = collaboration.getRatingOffer();
			}
			
			if (rating == null) {
				continue;
			}
			
			totalRatings += 1;
			if (rating.getNumber().equals(1L)) {
				totalRatings1 += 1;
			} else if (rating.getNumber().equals(2L)) {
				totalRatings2 += 1;
			} else if (rating.getNumber().equals(3L)) {
				totalRatings3 += 1;
			} else if (rating.getNumber().equals(4L)) {
				totalRatings4 += 1;
			}
		}
		
	    DecimalFormat df = new DecimalFormat("#.#");
		
		Long totalSum = totalRatings1 * 1 + totalRatings2 * 2 + totalRatings3 * 3 + totalRatings4 * 4;
		Double averageRating = totalRatings > 0 ? Double.valueOf(df.format(((double) totalSum / totalRatings))) : 0;
		
		Double percentageRating1 = totalRatings > 0 ? Double.valueOf(df.format(((double)  totalRatings1 / totalRatings) * 100)) : 0;
		Double percentageRating2 = totalRatings > 0 ? Double.valueOf(df.format(((double)  totalRatings2 / totalRatings) * 100)) : 0;
		Double percentageRating3 = totalRatings > 0 ? Double.valueOf(df.format(((double)  totalRatings3 / totalRatings) * 100)) : 0;
		Double percentageRating4 = totalRatings > 0 ? Double.valueOf(df.format(((double)  totalRatings4 / totalRatings) * 100)) : 0;

		CompanyRatingsDTO companyRatingsDTO = new CompanyRatingsDTO();
		companyRatingsDTO.setTotalRatings(totalRatings);
		companyRatingsDTO.setTotalRatings1(totalRatings1);
		companyRatingsDTO.setTotalRatings2(totalRatings2);
		companyRatingsDTO.setTotalRatings3(totalRatings3);
		companyRatingsDTO.setTotalRatings4(totalRatings4);
		companyRatingsDTO.setAverageRating(averageRating);
		companyRatingsDTO.setPercentageRating1(percentageRating1);
		companyRatingsDTO.setPercentageRating2(percentageRating2);
		companyRatingsDTO.setPercentageRating3(percentageRating3);
		companyRatingsDTO.setPercentageRating4(percentageRating4);
		
		return companyRatingsDTO;
	}

    @Transactional
	public List<Collaboration> findAllRatedCollaborationsForCompany(Long companyId) {
		return collaborationRepository.findAllByCompanyAndRatingAndStatus(companyId, CollaborationStatus.ACCEPTED);
	}

    @Transactional
	public Page<Collaboration> findPageOfRatedCollaborationsForCompany(Long companyId, Pageable pageable) {
		return collaborationRepository.findPageByCompanyAndRatingAndStatus(companyId, CollaborationStatus.ACCEPTED, pageable);
	}
    
}
