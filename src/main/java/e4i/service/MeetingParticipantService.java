package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.MeetingParticipant;
import e4i.repository.MeetingParticipantRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MeetingParticipant}.
 */
@Service
@Transactional
public class MeetingParticipantService {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantService.class);

    private final MeetingParticipantRepository meetingParticipantRepository;

    public MeetingParticipantService(MeetingParticipantRepository meetingParticipantRepository) {
        this.meetingParticipantRepository = meetingParticipantRepository;
    }

    /**
     * Save a meetingParticipant.
     *
     * @param meetingParticipant the entity to save.
     * @return the persisted entity.
     */
    public MeetingParticipant save(MeetingParticipant meetingParticipant) {
        log.debug("Request to save MeetingParticipant : {}", meetingParticipant);
        return meetingParticipantRepository.save(meetingParticipant);
    }

    /**
     * Get all the meetingParticipants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingParticipant> findAll() {
        log.debug("Request to get all MeetingParticipants");
        return meetingParticipantRepository.findAll();
    }


    /**
     * Get one meetingParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingParticipant> findOne(Long id) {
        log.debug("Request to get MeetingParticipant : {}", id);
        return meetingParticipantRepository.findById(id);
    }

    /**
     * Delete the meetingParticipant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingParticipant : {}", id);
        meetingParticipantRepository.deleteById(id);
    }
}
