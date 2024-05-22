package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.MeetingType;
import e4i.repository.MeetingTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MeetingType}.
 */
@Service
@Transactional
public class MeetingTypeService {

    private final Logger log = LoggerFactory.getLogger(MeetingTypeService.class);

    private final MeetingTypeRepository meetingTypeRepository;

    public MeetingTypeService(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    /**
     * Save a meetingType.
     *
     * @param meetingType the entity to save.
     * @return the persisted entity.
     */
    public MeetingType save(MeetingType meetingType) {
        log.debug("Request to save MeetingType : {}", meetingType);
        return meetingTypeRepository.save(meetingType);
    }

    /**
     * Get all the meetingTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingType> findAll() {
        log.debug("Request to get all MeetingTypes");
        return meetingTypeRepository.findAll();
    }


    /**
     * Get one meetingType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeetingType> findOne(Long id) {
        log.debug("Request to get MeetingType : {}", id);
        return meetingTypeRepository.findById(id);
    }

    /**
     * Delete the meetingType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeetingType : {}", id);
        meetingTypeRepository.deleteById(id);
    }
}
