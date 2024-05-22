package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementDuration;
import e4i.repository.AdvertisementDurationRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementDuration}.
 */
@Service
@Transactional
public class AdvertisementDurationService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementDurationService.class);

    private final AdvertisementDurationRepository advertisementDurationRepository;

    public AdvertisementDurationService(AdvertisementDurationRepository advertisementDurationRepository) {
        this.advertisementDurationRepository = advertisementDurationRepository;
    }

    /**
     * Save a advertisementDuration.
     *
     * @param advertisementDuration the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementDuration save(AdvertisementDuration advertisementDuration) {
        log.debug("Request to save AdvertisementDuration : {}", advertisementDuration);
        return advertisementDurationRepository.save(advertisementDuration);
    }

    /**
     * Get all the advertisementDurations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementDuration> findAll() {
        log.debug("Request to get all AdvertisementDurations");
        return advertisementDurationRepository.findAll();
    }


    /**
     * Get one advertisementDuration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementDuration> findOne(Long id) {
        log.debug("Request to get AdvertisementDuration : {}", id);
        return advertisementDurationRepository.findById(id);
    }

    /**
     * Delete the advertisementDuration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementDuration : {}", id);
        advertisementDurationRepository.deleteById(id);
    }
}
