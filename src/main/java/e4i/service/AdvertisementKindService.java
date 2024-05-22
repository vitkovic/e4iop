package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementKind;
import e4i.repository.AdvertisementKindRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementKind}.
 */
@Service
@Transactional
public class AdvertisementKindService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementKindService.class);

    private final AdvertisementKindRepository advertisementKindRepository;

    public AdvertisementKindService(AdvertisementKindRepository advertisementKindRepository) {
        this.advertisementKindRepository = advertisementKindRepository;
    }

    /**
     * Save a advertisementKind.
     *
     * @param advertisementKind the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementKind save(AdvertisementKind advertisementKind) {
        log.debug("Request to save AdvertisementKind : {}", advertisementKind);
        return advertisementKindRepository.save(advertisementKind);
    }

    /**
     * Get all the advertisementKinds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementKind> findAll() {
        log.debug("Request to get all AdvertisementKinds");
        return advertisementKindRepository.findAll();
    }


    /**
     * Get one advertisementKind by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementKind> findOne(Long id) {
        log.debug("Request to get AdvertisementKind : {}", id);
        return advertisementKindRepository.findById(id);
    }

    /**
     * Delete the advertisementKind by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementKind : {}", id);
        advertisementKindRepository.deleteById(id);
    }
}
