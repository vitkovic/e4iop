package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementStatus;
import e4i.repository.AdvertisementStatusRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementStatus}.
 */
@Service
@Transactional
public class AdvertisementStatusService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementStatusService.class);

    private final AdvertisementStatusRepository advertisementStatusRepository;

    public AdvertisementStatusService(AdvertisementStatusRepository advertisementStatusRepository) {
        this.advertisementStatusRepository = advertisementStatusRepository;
    }

    /**
     * Save a advertisementStatus.
     *
     * @param advertisementStatus the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementStatus save(AdvertisementStatus advertisementStatus) {
        log.debug("Request to save AdvertisementStatus : {}", advertisementStatus);
        return advertisementStatusRepository.save(advertisementStatus);
    }

    /**
     * Get all the advertisementStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementStatus> findAll() {
        log.debug("Request to get all AdvertisementStatuses");
        return advertisementStatusRepository.findAll();
    }


    /**
     * Get one advertisementStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementStatus> findOne(Long id) {
        log.debug("Request to get AdvertisementStatus : {}", id);
        return advertisementStatusRepository.findById(id);
    }

    /**
     * Delete the advertisementStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementStatus : {}", id);
        advertisementStatusRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<AdvertisementStatus> findOneByStatus(String status) {
        log.debug("Request to get AdvertisementStatus by status: {}", status);
        return advertisementStatusRepository.findByStatus(status);
    }
}
