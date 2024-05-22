package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementSubsubcategory;
import e4i.repository.AdvertisementSubsubcategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementSubsubcategory}.
 */
@Service
@Transactional
public class AdvertisementSubsubcategoryService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSubsubcategoryService.class);

    private final AdvertisementSubsubcategoryRepository advertisementSubsubcategoryRepository;

    public AdvertisementSubsubcategoryService(AdvertisementSubsubcategoryRepository advertisementSubsubcategoryRepository) {
        this.advertisementSubsubcategoryRepository = advertisementSubsubcategoryRepository;
    }

    /**
     * Save a advertisementSubsubcategory.
     *
     * @param advertisementSubsubcategory the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementSubsubcategory save(AdvertisementSubsubcategory advertisementSubsubcategory) {
        log.debug("Request to save AdvertisementSubsubcategory : {}", advertisementSubsubcategory);
        return advertisementSubsubcategoryRepository.save(advertisementSubsubcategory);
    }

    /**
     * Get all the advertisementSubsubcategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementSubsubcategory> findAll() {
        log.debug("Request to get all AdvertisementSubsubcategories");
        return advertisementSubsubcategoryRepository.findAll();
    }


    /**
     * Get one advertisementSubsubcategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementSubsubcategory> findOne(Long id) {
        log.debug("Request to get AdvertisementSubsubcategory : {}", id);
        return advertisementSubsubcategoryRepository.findById(id);
    }

    /**
     * Delete the advertisementSubsubcategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementSubsubcategory : {}", id);
        advertisementSubsubcategoryRepository.deleteById(id);
    }
}
