package e4i.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Meeting;
import e4i.domain.MeetingParticipantNonB2B;
import e4i.repository.MeetingParticipantNonB2BRepository;
import e4i.repository.MeetingRepository;

/**
 * Service Implementation for managing {@link MeetingParticipantNonB2B}.
 */
@Service
@Transactional
public class MeetingParticipantNonB2BService {
    private final Logger log = LoggerFactory.getLogger(MeetingParticipantNonB2BService.class);

    private final MeetingParticipantNonB2BRepository meetingParticipantNonB2BRepository;
    
    @Autowired
    MeetingRepository meetingRepository;
    
    public MeetingParticipantNonB2BService(MeetingParticipantNonB2BRepository meetingParticipantNonB2BRepository) {
        this.meetingParticipantNonB2BRepository = meetingParticipantNonB2BRepository;
    }
    
    /**
     * Save a meetingParticipantNonB2B.
     *
     * @param meetingParticipantNonB2B the entity to save.
     * @return the persisted entity.
     */
    public MeetingParticipantNonB2B save(MeetingParticipantNonB2B meetingParticipantNonB2B) {
        log.debug("Request to save MeetingParticipantNonB2B : {}", meetingParticipantNonB2B);
        return meetingParticipantNonB2BRepository.save(meetingParticipantNonB2B);
    }
    

    /**
     * Get all the meetingParticipantNonB2Bs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingParticipantNonB2B> findAll() {
        log.debug("Request to get all MeetingParticipantNonB2B");
        return meetingParticipantNonB2BRepository.findAll();
    }


    /**
     * Get one meetingParticipantNonB2B by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingParticipantNonB2B> findOne(Long id) {
        log.debug("Request to get MeetingParticipantNonB2B : {}", id);
        return meetingParticipantNonB2BRepository.findById(id);
    }

    /**
     * Delete the meetingParticipantNonB2B by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingParticipantNonB2B : {}", id);
        meetingParticipantNonB2BRepository.deleteById(id);
    }
    
    @Transactional
    public MeetingParticipantNonB2B addMeetingParticipant(Meeting meeting, String email) {
    	log.debug("Request to add MeetingParticipantNonB2B for Meeting {} with email {}.", meeting.getId(), email);
    	
    	MeetingParticipantNonB2B meetingParticipantNonB2B = new MeetingParticipantNonB2B();
    	meetingParticipantNonB2B.setMeeting(meeting);
    	meetingParticipantNonB2B.setEmail(email);
    	
    	MeetingParticipantNonB2B result = meetingParticipantNonB2BRepository.save(meetingParticipantNonB2B);
    	if (meeting.getMeetingParticipantsNonB2B().isEmpty()) {
    		meeting.setMeetingParticipantsNonB2B(new HashSet<>());
    	}
    	meeting.addMeetingParticipantNonB2B(result);
//    	meeting.getMeetingParticipantsNonB2B().add(result);
    	meetingRepository.save(meeting);
    	
    	log.debug("Request to save participant MeetingParticipantNonB2B : {}", result);
        return result;
    }
    
    @Transactional
    public void deleteMeetingParticipant(Meeting meeting, String email) {
    	log.debug("Request to add MeetingParticipantNonB2B for Meeting {} with email {}.", meeting.getId(), email);
    	
    	Optional<MeetingParticipantNonB2B> meetingParticipantNonB2BOptional = meetingParticipantNonB2BRepository.findOneByMeetingIdAndEmail(meeting.getId(), email);
    
        if (meetingParticipantNonB2BOptional.isEmpty()) {
    		String errorMessage = String.format("MeetingParticipantNonB2B for Meeting {} with email {} could not be found.", meeting.getId(), email);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        MeetingParticipantNonB2B meetingParticipantNonB2B = meetingParticipantNonB2BOptional.get();
    	meeting.removeMeetingParticipantNonB2B(meetingParticipantNonB2B);
//        meeting.getMeetingParticipantsNonB2B().remove(meetingParticipantNonB2B);
    	meetingRepository.save(meeting);
    	meetingParticipantNonB2BRepository.deleteById(meetingParticipantNonB2B.getId());
    }

	public List<MeetingParticipantNonB2B> findAllByMeetingId(Long meetingId) {
		return meetingParticipantNonB2BRepository.findAllByMeetingId(meetingId);
	}
}
