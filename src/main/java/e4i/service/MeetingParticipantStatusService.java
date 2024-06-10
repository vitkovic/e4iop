package e4i.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.MeetingParticipantStatus;
import e4i.repository.MeetingParticipantStatusRepository;

/**
 * Service Implementation for managing {@link MeetingParticipantStatus }.
 */
@Service
@Transactional
public class MeetingParticipantStatusService {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantStatusService.class);

    private final MeetingParticipantStatusRepository meetingParticipantStatusRepository;

    public MeetingParticipantStatusService(MeetingParticipantStatusRepository meetingParticipantStatusRepository) {
        this.meetingParticipantStatusRepository = meetingParticipantStatusRepository;
    }

    /**
     * Save a meetingParticipantStatus.
     *
     * @param meetingParticipantStatus the entity to save.
     * @return the persisted entity.
     */
    public MeetingParticipantStatus save(MeetingParticipantStatus meetingParticipantStatus) {
        log.debug("Request to save MeetingParticipantStatus : {}", meetingParticipantStatus);
        return meetingParticipantStatusRepository.save(meetingParticipantStatus);
    }

    /**
     * Get all the meetingParticipantStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingParticipantStatus> findAll() {
        log.debug("Request to get all MeetingParticipantStatus");
        return meetingParticipantStatusRepository.findAll();
    }


    /**
     * Get one meetingParticipantStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingParticipantStatus> findOne(Long id) {
        log.debug("Request to get MeetingParticipantStatus : {}", id);
        return meetingParticipantStatusRepository.findById(id);
    }

    /**
     * Delete the meetingParticipantStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingParticipantStatus : {}", id);
        meetingParticipantStatusRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<MeetingParticipantStatus> findOneByStatus(String status) {
        log.debug("Request to get MeetingParticipantStatus by status: {}", status);
        return meetingParticipantStatusRepository.findByStatusInAnyLanguage(status);
    }
    
    @Transactional(readOnly = true)
    public MeetingParticipantStatus getOneByStatus(String status) {
        log.debug("Request to get MeetingParticipantStatus by status: {}", status);
        Optional<MeetingParticipantStatus> meetingParticipantStatusOptional = meetingParticipantStatusRepository.findByStatusInAnyLanguage(status);
        
        if (meetingParticipantStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetingParticipantStatus with status={} could not be found", status);
        	throw new EntityNotFoundException(errorMessage);
        }
       
        MeetingParticipantStatus meetingParticipantStatus = meetingParticipantStatusOptional.get();
        
        return meetingParticipantStatus;
    }
}
