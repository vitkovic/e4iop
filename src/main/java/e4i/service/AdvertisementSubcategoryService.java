package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementSubcategory;
import e4i.repository.AdvertisementSubcategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementSubcategory}.
 */
@Service
@Transactional
public class AdvertisementSubcategoryService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementSubcategoryService.class);

    private final AdvertisementSubcategoryRepository advertisementSubcategoryRepository;

    public AdvertisementSubcategoryService(AdvertisementSubcategoryRepository advertisementSubcategoryRepository) {
        this.advertisementSubcategoryRepository = advertisementSubcategoryRepository;
    }

    /**
     * Save a advertisementSubcategory.
     *
     * @param advertisementSubcategory the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementSubcategory save(AdvertisementSubcategory advertisementSubcategory) {
        log.debug("Request to save AdvertisementSubcategory : {}", advertisementSubcategory);
        return advertisementSubcategoryRepository.save(advertisementSubcategory);
    }

    /**
     * Get all the advertisementSubcategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementSubcategory> findAll() {
        log.debug("Request to get all AdvertisementSubcategories");
        return advertisementSubcategoryRepository.findAll();
    }


    /**
     * Get one advertisementSubcategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementSubcategory> findOne(Long id) {
        log.debug("Request to get AdvertisementSubcategory : {}", id);
        return advertisementSubcategoryRepository.findById(id);
    }

    /**
     * Delete the advertisementSubcategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementSubcategory : {}", id);
        advertisementSubcategoryRepository.deleteById(id);
    }
}
