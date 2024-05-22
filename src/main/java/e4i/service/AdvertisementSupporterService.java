package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementSupporter;
import e4i.repository.AdvertisementSupporterRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementSupporter}.
 */
@Service
@Transactional
public class AdvertisementSupporterService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSupporterService.class);

    private final AdvertisementSupporterRepository advertisementSupporterRepository;

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
}
