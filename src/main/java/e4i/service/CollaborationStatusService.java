package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CollaborationStatus;
import e4i.repository.CollaborationStatusRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link CollaborationStatus}.
 */
@Service
@Transactional
public class CollaborationStatusService {

    private final Logger log = LoggerFactory.getLogger(CollaborationStatusService.class);

    private final CollaborationStatusRepository collaborationStatusRepository;

    public CollaborationStatusService(CollaborationStatusRepository collaborationStatusRepository) {
        this.collaborationStatusRepository = collaborationStatusRepository;
    }

    /**
     * Save a collaborationStatus.
     *
     * @param collaborationStatus the entity to save.
     * @return the persisted entity.
     */
    public CollaborationStatus save(CollaborationStatus collaborationStatus) {
        log.debug("Request to save CollaborationStatus : {}", collaborationStatus);
        return collaborationStatusRepository.save(collaborationStatus);
    }

    /**
     * Get all the collaborationStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CollaborationStatus> findAll() {
        log.debug("Request to get all CollaborationStatuses");
        return collaborationStatusRepository.findAll();
    }


    /**
     * Get one collaborationStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CollaborationStatus> findOne(Long id) {
        log.debug("Request to get CollaborationStatus : {}", id);
        return collaborationStatusRepository.findById(id);
    }

    /**
     * Delete the collaborationStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CollaborationStatus : {}", id);
        collaborationStatusRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<CollaborationStatus> findOneByStatus(String status) {
        log.debug("Request to get CollaborationStatus by status: {}", status);
        return collaborationStatusRepository.findByStatus(status);
    }
    
    @Transactional(readOnly = true)
    public CollaborationStatus getOneByAnyStatus(String status) {
        log.debug("Request to get CollaborationStatus by status: {}", status);
        Optional<CollaborationStatus> collaborationStatusOptional = collaborationStatusRepository.findByAnyStatus(status);
        
        if (collaborationStatusOptional.isEmpty()) {
    		String errorMessage = String.format("CollaborationStatus with status={} could not be found", status);
        	throw new EntityNotFoundException(errorMessage);
        }
       
        CollaborationStatus collaborationStatus = collaborationStatusOptional.get();
        
        return collaborationStatus;
    }
}
