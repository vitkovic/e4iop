package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.MeetingParticipant;
import e4i.domain.MeetingParticipantStatus;
import e4i.repository.MeetingParticipantRepository;
import e4i.repository.MeetingParticipantStatusRepository;
import e4i.repository.MeetingRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link MeetingParticipant}.
 */
@Service
@Transactional
public class MeetingParticipantService {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantService.class);

    private final MeetingParticipantRepository meetingParticipantRepository;
    
    @Autowired
    MeetingRepository meetingRepository;
    
    @Autowired
    MeetingParticipantStatusRepository meetingParticipantStatusRepository;

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
    
    @Transactional
    public MeetingParticipant addMeetingOrganizer(Meeting meeting, Company company) {
    	
    	Optional<MeetingParticipantStatus> acceptedStatusOptional = meetingParticipantStatusRepository
                .findByStatusInAnyLanguage(MeetingParticipantStatus.ACCEPTED);

        if (acceptedStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.ACCEPTED);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        MeetingParticipantStatus acceptedStatus = acceptedStatusOptional.get();
    	
    	MeetingParticipant meetingOrganizer = new MeetingParticipant();
    	meetingOrganizer.setMeeting(meeting);
    	meetingOrganizer.setCompany(company);
    	meetingOrganizer.setStatus(acceptedStatus);
    	meetingOrganizer.setIsOrganizer(true);
    	meetingOrganizer.setHasAccepted(true);
    	meetingOrganizer.setHasRemoved(false);
    	
    	MeetingParticipant result = meetingParticipantRepository.save(meetingOrganizer);
    	meeting.addMeetingParticipant(result);
    	meetingRepository.save(meeting);
    	
    	log.debug("Request to save organizer MeetingParticipant : {}", meetingOrganizer);
        return meetingParticipantRepository.save(meetingOrganizer);
    }
    
    @Transactional
    public MeetingParticipant addMeetingParticipant(Meeting meeting, Company company) {
    	
    	Optional<MeetingParticipantStatus> noResponseStatusOptional = meetingParticipantStatusRepository
                .findByStatusInAnyLanguage(MeetingParticipantStatus.NO_RESPONSE);

        if (noResponseStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.NO_RESPONSE);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        MeetingParticipantStatus noResponseStatus = noResponseStatusOptional.get();
    	
    	MeetingParticipant meetingParticipant = new MeetingParticipant();
    	meetingParticipant.setMeeting(meeting);
    	meetingParticipant.setCompany(company);
    	meetingParticipant.setStatus(noResponseStatus);
    	meetingParticipant.setIsOrganizer(false);
    	meetingParticipant.setHasAccepted(false);
    	meetingParticipant.setHasRemoved(false);
    	
    	MeetingParticipant result = meetingParticipantRepository.save(meetingParticipant);
    	meeting.addMeetingParticipant(result);
    	meetingRepository.save(meeting);
    	
    	log.debug("Request to save participant MeetingParticipant : {}", meetingParticipant);
        return meetingParticipantRepository.save(meetingParticipant);
    }
    
    @Transactional
    public void deleteMeetingParticipant(Meeting meeting, Company company) {
    	log.debug("Request to delete MeetingParticipant for Meeting {} and Company {}.", meeting.getId(), company.getId());
    	
    	Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meeting.getId(), company.getId());
    
        if (meetingParticipantOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipant for Meeting {} and Company {} could not be found.", meeting.getId(), company.getId());
        	throw new EntityNotFoundException(errorMessage);
    	}
        
    	MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
    	meeting.removeMeetingParticipant(meetingParticipant);
    	meetingRepository.save(meeting);
    	meetingParticipantRepository.deleteById(meetingParticipant.getId());
    }
    
    @Transactional(readOnly = true)
    public List<MeetingParticipant> findAllByCompanyAndHasRemoved(Long companyId, Boolean hasRemoved) {
        log.debug("Request to get all not removed MeetingParticipants for Company {}", companyId);
        return meetingParticipantRepository.findAllByCompanyIdAndHasRemoved(companyId, hasRemoved);
    }
    
    @Transactional(readOnly = true)
    public List<MeetingParticipant> findAllByMeetingId(Long meetingId) {
        log.debug("Request to get all MeetingParticipants for Meeting {}", meetingId);
        return meetingParticipantRepository.findAllByMeetingId(meetingId);
    }
    
    @Transactional(readOnly = true)
    public MeetingParticipant findOneByMeetingAndIsOrganizer(Long meetingId, Boolean isOrganizer) {
        log.debug("Request to get company organizer MeetingParticipants for Meeting {}", meetingId);
        return meetingParticipantRepository.findOneByMeetingIdAndIsOrganizer(meetingId, isOrganizer);
    }
    
    @Transactional
    public MeetingParticipant acceptMeetingForCompany(Long meetingId, Long companyId) {
        log.debug("Request to accept Meeting {} for Company {}", meetingId, companyId);
        
        Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
        
        if (meetingParticipantOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipant for Meeting {} and Company {} could not be found.", meetingId, companyId);
        	throw new EntityNotFoundException(errorMessage);
        }
        
    	Optional<MeetingParticipantStatus> acceptedStatusOptional = meetingParticipantStatusRepository
                .findByStatusInAnyLanguage(MeetingParticipantStatus.ACCEPTED);

        if (acceptedStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.ACCEPTED);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
        MeetingParticipantStatus acceptedStatus = acceptedStatusOptional.get();
        
    	meetingParticipant.setHasAccepted(true);
    	meetingParticipant.setStatus(acceptedStatus);
    	MeetingParticipant result = meetingParticipantRepository.save(meetingParticipant);  	
    	
    	return result;
    }
    
    @Transactional
    public MeetingParticipant rejectMeetingForCompany(Long meetingId, Long companyId) {
        log.debug("Request to accept Meeting {} for Company {}", meetingId, companyId);
        
        Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
        
        if (meetingParticipantOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipant for Meeting {} and Company {} could not be found.", meetingId, companyId);
        	throw new EntityNotFoundException(errorMessage);
        }
        
    	Optional<MeetingParticipantStatus> rejectedStatusOptional = meetingParticipantStatusRepository
                .findByStatusInAnyLanguage(MeetingParticipantStatus.REJECTED);

        if (rejectedStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.REJECTED);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
        MeetingParticipantStatus rejectedStatus = rejectedStatusOptional.get();
        
    	meetingParticipant.setStatus(rejectedStatus);
    	meetingParticipant.setHasRemoved(true);
    	MeetingParticipant result = meetingParticipantRepository.save(meetingParticipant);  	
    	
    	return result;
    }
    
    @Transactional
    public MeetingParticipant removeMeetingForCompany(Long meetingId, Long companyId) {
        log.debug("Request to remove Meeting {} for Company {}", meetingId, companyId);
        
        Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
        
        if (meetingParticipantOptional.isPresent()) {
        	MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
        	meetingParticipant.setHasRemoved(true);
        	MeetingParticipant result = meetingParticipantRepository.save(meetingParticipant);  	
        	
        	return result;
    	} else {
    		String errorMessage = String.format("MeetinParticipant for Meeting {} and Company {} could not be found.", meetingId, companyId);
        	throw new EntityNotFoundException(errorMessage);
    	}
    }

    @Transactional
	public Optional<Boolean> checkMeetingAccpetance(Long meetingId, Long companyId) {   	
    	Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
    	
    	if (meetingParticipantOptional.isEmpty()) {
    		return Optional.empty();
    	} 
    	
    	MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
    	Boolean hasAccepted = meetingParticipant.getStatus().getStatusEn().equals(MeetingParticipantStatus.ACCEPTED);
    	
    	return Optional.of(hasAccepted);
//    	return meetingParticipantRepository.checkIfStatusByMeetingIdAndCompanyId(meetingId, companyId, MeetingParticipantStatus.ACCEPTED);
	}
    
    @Transactional
	public Optional<Boolean> checkMeetingRejection(Long meetingId, Long companyId) {
    	Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
    	
    	if (meetingParticipantOptional.isEmpty()) {
    		return Optional.empty();
    	} 
    	
    	MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
    	Boolean hasRejected = meetingParticipant.getStatus().getStatusEn().equals(MeetingParticipantStatus.REJECTED);
    	
    	return Optional.of(hasRejected);
//    	return meetingParticipantRepository.checkIfStatusByMeetingIdAndCompanyId(meetingId, companyId, MeetingParticipantStatus.REJECTED);
	}
    
    @Transactional
	public Optional<Boolean> checkMeetingNoResponse(Long meetingId, Long companyId) {
    	Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
    	
    	if (meetingParticipantOptional.isEmpty()) {
    		return Optional.empty();
    	} 
    	
    	MeetingParticipant meetingParticipant = meetingParticipantOptional.get();
    	Boolean hasNoResponse = meetingParticipant.getStatus().getStatusEn().equals(MeetingParticipantStatus.NO_RESPONSE);
    	
    	return Optional.of(hasNoResponse);
//		return meetingParticipantRepository.checkIfStatusByMeetingIdAndCompanyId(meetingId, companyId, MeetingParticipantStatus.NO_RESPONSE);
	}

	@Transactional
	public MeetingParticipant findOneByMeetingAndCompany(Long meetingId, Long companyId) {
        Optional<MeetingParticipant> meetingParticipantOptional = meetingParticipantRepository.findOneByMeetingIdAndCompanyId(meetingId, companyId);
        
        if (meetingParticipantOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipant for Meeting {} and Company {} could not be found.", meetingId, companyId);
        	throw new EntityNotFoundException(errorMessage);
        }
        
		return meetingParticipantOptional.get();
	}

	@Transactional
	public Company findCompanyByMeetingAndIsOrganizer(Long meetingId, Boolean isOrganiser) {
        log.debug("Request to get company organizer MeetingParticipants for Meeting {}", meetingId);
        MeetingParticipant meetingParticipant = meetingParticipantRepository.findOneByMeetingIdAndIsOrganizer(meetingId, isOrganiser);
        
        return meetingParticipant.getCompany();
	}


}
