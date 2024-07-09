package e4i.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementSupporterStatus;
import e4i.repository.AdvertisementSupporterStatusRepository;

/**
 * Service Implementation for managing {@link AdvertisementSupporterStatus }.
 */
@Service
@Transactional
public class AdvertisementSupporterStatusService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSupporterStatusService.class);

    private final AdvertisementSupporterStatusRepository advertisementSupporterStatusRepository;

    public AdvertisementSupporterStatusService(AdvertisementSupporterStatusRepository advertisementSupporterStatusRepository) {
        this.advertisementSupporterStatusRepository = advertisementSupporterStatusRepository;
    }

    /**
     * Save an advertisementSupporterStatus.
     *
     * @param advertisementSupporterStatus the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementSupporterStatus save(AdvertisementSupporterStatus advertisementSupporterStatus) {
        log.debug("Request to save AdvertisementSupporterStatus : {}", advertisementSupporterStatus);
        return advertisementSupporterStatusRepository.save(advertisementSupporterStatus);
    }

    /**
     * Get all the advertisementSupporterStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementSupporterStatus> findAll() {
        log.debug("Request to get all AdvertisementSupporterStatuses");
        return advertisementSupporterStatusRepository.findAll();
    }

    /**
     * Get one advertisementSupporterStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementSupporterStatus> findOne(Long id) {
        log.debug("Request to get AdvertisementSupporterStatus : {}", id);
        return advertisementSupporterStatusRepository.findById(id);
    }

    /**
     * Delete the advertisementSupporterStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementSupporterStatus : {}", id);
        advertisementSupporterStatusRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<AdvertisementSupporterStatus> findOneByName(String name) {
        log.debug("Request to get AdvertisementSupporterStatus by name: {}", name);
        return advertisementSupporterStatusRepository.findByNameInAnyLanguage(name);
    }

    @Transactional(readOnly = true)
    public AdvertisementSupporterStatus getOneByName(String name) {
        log.debug("Request to get AdvertisementSupporterStatus by name: {}", name);
        Optional<AdvertisementSupporterStatus> advertisementSupporterStatusOptional = advertisementSupporterStatusRepository.findByNameInAnyLanguage(name);
        
        if (advertisementSupporterStatusOptional.isEmpty()) {
            String errorMessage = String.format("AdvertisementSupporterStatus with name={} could not be found", name);
            throw new EntityNotFoundException(errorMessage);
        }
       
        AdvertisementSupporterStatus advertisementSupporterStatus = advertisementSupporterStatusOptional.get();
        
        return advertisementSupporterStatus;
    }
}
