package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementSupporter;
import e4i.domain.AdvertisementSupporterStatus;
import e4i.domain.Company;
import e4i.domain.MeetingParticipantStatus;
import e4i.repository.AdvertisementSupporterRepository;
import e4i.repository.AdvertisementSupporterStatusRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link AdvertisementSupporter}.
 */
@Service
@Transactional
public class AdvertisementSupporterService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSupporterService.class);

    private final AdvertisementSupporterRepository advertisementSupporterRepository;

    
    @Autowired
    AdvertisementSupporterStatusRepository advertisementSupporterStatusRepository;
    
    public AdvertisementSupporterService(AdvertisementSupporterRepository advertisementSupporterRepository) {
        this.advertisementSupporterRepository = advertisementSupporterRepository;
    }

    /**
     * Save a advertisementSupporter.
     *
     * @param advertisementSupporter the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementSupporter save(AdvertisementSupporter advertisementSupporter) {
        log.debug("Request to save AdvertisementSupporter : {}", advertisementSupporter);
        return advertisementSupporterRepository.save(advertisementSupporter);
    }

    /**
     * Get all the advertisementSupporters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementSupporter> findAll() {
        log.debug("Request to get all AdvertisementSupporters");
        return advertisementSupporterRepository.findAll();
    }


    /**
     * Get one advertisementSupporter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementSupporter> findOne(Long id) {
        log.debug("Request to get AdvertisementSupporter : {}", id);
        return advertisementSupporterRepository.findById(id);
    }

    /**
     * Delete the advertisementSupporter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementSupporter : {}", id);
        advertisementSupporterRepository.deleteById(id);
    }

    
	public AdvertisementSupporter addCompanySupporter(Advertisement advertisement, Company company) {
    	Optional<AdvertisementSupporterStatus> advertisementSupporterStatusOptional = advertisementSupporterStatusRepository
                .findByNameInAnyLanguage(AdvertisementSupporterStatus.NO_RESPONSE);

        if (advertisementSupporterStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.NO_RESPONSE);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        AdvertisementSupporterStatus advertisementSupporterStatus = advertisementSupporterStatusOptional.get();
		
		AdvertisementSupporter advertisementSupporter = new AdvertisementSupporter();
		advertisementSupporter.setAdvertisement(advertisement);
		advertisementSupporter.setCompany(company);
		advertisementSupporter.setStatus(advertisementSupporterStatus);
		advertisementSupporter.setHasAccepted(false);
		
		AdvertisementSupporter result = advertisementSupporterRepository.save(advertisementSupporter);
		
		return result;
	}
	
	public void removeCompanySupporter(Advertisement advertisement, Company company) {
		Optional<AdvertisementSupporter> advertisementSupporterOptional = 
				advertisementSupporterRepository.findByAdvertisementIdAndCompanyId(advertisement.getId(), company.getId());
	
		if (advertisementSupporterOptional.isPresent()) {
			AdvertisementSupporter advertisementSupporter = advertisementSupporterOptional.get();
			advertisementSupporterRepository.delete(advertisementSupporter);
		}
		
		return;
	}

	public List<AdvertisementSupporter> findAllByAdvertisementId(Advertisement advertisement) {
		
		return advertisementSupporterRepository.findAllByAdvertisementId(advertisement.getId());
	}

	public Optional<AdvertisementSupporter> findByAdvertisementIdAndCompanyId(Long advertisementId, Long companyId) {
		return advertisementSupporterRepository.findByAdvertisementIdAndCompanyId(advertisementId, companyId);
	}

	public AdvertisementSupporter acceptForCompany(AdvertisementSupporter advertisementSupporter) {
    	Optional<AdvertisementSupporterStatus> advertisementSupporterStatusOptional = advertisementSupporterStatusRepository
                .findByNameInAnyLanguage(AdvertisementSupporterStatus.ACCEPTED);

        if (advertisementSupporterStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.ACCEPTED);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        AdvertisementSupporterStatus advertisementSupporterStatus = advertisementSupporterStatusOptional.get();
	
		advertisementSupporter.setStatus(advertisementSupporterStatus);
		advertisementSupporter.setHasAccepted(true);
		
		return advertisementSupporterRepository.save(advertisementSupporter);
	}
	
	public AdvertisementSupporter rejectForCompany(AdvertisementSupporter advertisementSupporter) {
    	Optional<AdvertisementSupporterStatus> advertisementSupporterStatusOptional = advertisementSupporterStatusRepository
                .findByNameInAnyLanguage(AdvertisementSupporterStatus.REJECTED);

        if (advertisementSupporterStatusOptional.isEmpty()) {
    		String errorMessage = String.format("MeetinParticipantStatus {} could not be found.", MeetingParticipantStatus.REJECTED);
        	throw new EntityNotFoundException(errorMessage);
    	}
        
        AdvertisementSupporterStatus advertisementSupporterStatus = advertisementSupporterStatusOptional.get();
	
		advertisementSupporter.setStatus(advertisementSupporterStatus);
		advertisementSupporter.setHasAccepted(false);
		
		return advertisementSupporterRepository.save(advertisementSupporter);
	}

	public boolean checkNoResponse(AdvertisementSupporter advertisementSupporter) {
		return advertisementSupporter.getStatus().getNameEn().equals(AdvertisementSupporterStatus.NO_RESPONSE);
	}
}
