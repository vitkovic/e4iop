package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CollaborationRating;
import e4i.repository.CollaborationRatingRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link CollaborationRating}.
 */
@Service
@Transactional
public class CollaborationRatingService {

    private final Logger log = LoggerFactory.getLogger(CollaborationRatingService.class);

    private final CollaborationRatingRepository collaborationRatingRepository;

    public CollaborationRatingService(CollaborationRatingRepository collaborationRatingRepository) {
        this.collaborationRatingRepository = collaborationRatingRepository;
    }

    /**
     * Save a collaborationRating.
     *
     * @param collaborationRating the entity to save.
     * @return the persisted entity.
     */
    public CollaborationRating save(CollaborationRating collaborationRating) {
        log.debug("Request to save CollaborationRating : {}", collaborationRating);
        return collaborationRatingRepository.save(collaborationRating);
    }

    /**
     * Get all the collaborationRatings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CollaborationRating> findAll() {
        log.debug("Request to get all CollaborationRatings");
        return collaborationRatingRepository.findAll();
    }


    /**
     * Get one collaborationRating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollaborationRating> findOne(Long id) {
        log.debug("Request to get CollaborationRating : {}", id);
        return collaborationRatingRepository.findById(id);
    }

    /**
     * Delete the collaborationRating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CollaborationRating : {}", id);
        collaborationRatingRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public CollaborationRating findOneById(Long id) {
        log.debug("Request to get CollaborationRating : {}", id);
        
        Optional<CollaborationRating> collaborationRatingOptional = collaborationRatingRepository.findById(id);
        
    	if (collaborationRatingOptional.isEmpty()) {
    		String errorMessage = String.format("CollaborationRating with id={} could not be found", id);
        	throw new EntityNotFoundException(errorMessage);
        }
    	
    	CollaborationRating collaborationRating = collaborationRatingOptional.get();
        
        return collaborationRating;
    }
}
