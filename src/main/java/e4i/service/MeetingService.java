package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Meeting;
import e4i.repository.MeetingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Meeting}.
 */
@Service
@Transactional
public class MeetingService {

    private final Logger log = LoggerFactory.getLogger(MeetingService.class);

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    /**
     * Save a meeting.
     *
     * @param meeting the entity to save.
     * @return the persisted entity.
     */
    public Meeting save(Meeting meeting) {
        log.debug("Request to save Meeting : {}", meeting);
        return meetingRepository.save(meeting);
    }

    /**
     * Get all the meetings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Meeting> findAll() {
        log.debug("Request to get all Meetings");
        return meetingRepository.findAll();
    }


    /**
     * Get one meeting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Meeting> findOne(Long id) {
        log.debug("Request to get Meeting : {}", id);
        return meetingRepository.findById(id);
    }

    /**
     * Delete the meeting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Meeting : {}", id);
        meetingRepository.deleteById(id);
    }
}
