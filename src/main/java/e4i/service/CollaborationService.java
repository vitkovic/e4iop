package e4i.service;

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
        
        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.PENDING); 
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

        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.ACCEPTED); 
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
        CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.REJECTED); 
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
    public Page<Collaboration> findAllFilteredByCompanyAndStatus(Long companyId, List<Long> statusIds, List<Boolean> collaborationSideFlags,  Pageable pageable) {    	
    	if (collaborationSideFlags.size() != 2) {
    		String errorMessage = String.format("List 'collaborationSideflags' must be of length 2, but is {}", collaborationSideFlags.size());
        	throw new IllegalArgumentException(errorMessage);
    	}
    	
    	return collaborationRepository.findAllFilteredByCompanyAndStatus(companyId, statusIds, collaborationSideFlags.get(0), collaborationSideFlags.get(1), pageable);
    }

    
    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompany(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyAndStatus(companyId, collaborationStatus.getId(), pageable);
    }

    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompanyOffer(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyOfferAndStatus(companyId, collaborationStatus.getId(), pageable);
    }
    
    @Deprecated
    @Transactional
    public Page<Collaboration> findAllAcceptedCollaborationsForCompanyRequest(Long companyId, Pageable pageable) {
    	CollaborationStatus collaborationStatus = collaborationStatusService.getOneByStatus(CollaborationStatus.ACCEPTED); 
    	
    	return collaborationRepository.findAllByCompanyRequestAndStatus(companyId, collaborationStatus.getId(), pageable);
    }
    
}
