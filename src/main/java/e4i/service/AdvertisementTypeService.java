package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementType;
import e4i.repository.AdvertisementTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementType}.
 */
@Service
@Transactional
public class AdvertisementTypeService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementTypeService.class);

    private final AdvertisementTypeRepository advertisementTypeRepository;

    public AdvertisementTypeService(AdvertisementTypeRepository advertisementTypeRepository) {
        this.advertisementTypeRepository = advertisementTypeRepository;
    }

    /**
     * Save a advertisementType.
     *
     * @param advertisementType the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementType save(AdvertisementType advertisementType) {
        log.debug("Request to save AdvertisementType : {}", advertisementType);
        return advertisementTypeRepository.save(advertisementType);
    }

    /**
     * Get all the advertisementTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementType> findAll() {
        log.debug("Request to get all AdvertisementTypes");
        return advertisementTypeRepository.findAll();
    }


    /**
     * Get one advertisementType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementType> findOne(Long id) {
        log.debug("Request to get AdvertisementType : {}", id);
        return advertisementTypeRepository.findById(id);
    }

    /**
     * Delete the advertisementType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementType : {}", id);
        advertisementTypeRepository.deleteById(id);
    }
}
