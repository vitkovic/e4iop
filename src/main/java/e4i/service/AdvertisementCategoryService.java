package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.AdvertisementCategory;
import e4i.repository.AdvertisementCategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AdvertisementCategory}.
 */
@Service
@Transactional
public class AdvertisementCategoryService {

    private final Logger log = LoggerFactory.getLogger(AdvertisementCategoryService.class);

    private final AdvertisementCategoryRepository advertisementCategoryRepository;

    public AdvertisementCategoryService(AdvertisementCategoryRepository advertisementCategoryRepository) {
        this.advertisementCategoryRepository = advertisementCategoryRepository;
    }

    /**
     * Save a advertisementCategory.
     *
     * @param advertisementCategory the entity to save.
     * @return the persisted entity.
     */
    public AdvertisementCategory save(AdvertisementCategory advertisementCategory) {
        log.debug("Request to save AdvertisementCategory : {}", advertisementCategory);
        return advertisementCategoryRepository.save(advertisementCategory);
    }

    /**
     * Get all the advertisementCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdvertisementCategory> findAll() {
        log.debug("Request to get all AdvertisementCategories");
        return advertisementCategoryRepository.findAll();
    }


    /**
     * Get one advertisementCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdvertisementCategory> findOne(Long id) {
        log.debug("Request to get AdvertisementCategory : {}", id);
        return advertisementCategoryRepository.findById(id);
    }

    /**
     * Delete the advertisementCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdvertisementCategory : {}", id);
        advertisementCategoryRepository.deleteById(id);
    }
}
