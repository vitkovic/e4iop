package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsAdvertisementCategory;
import e4i.repository.CmsAdvertisementCategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsAdvertisementCategory}.
 */
@Service
@Transactional
public class CmsAdvertisementCategoryService {

    private final Logger log = LoggerFactory.getLogger(CmsAdvertisementCategoryService.class);

    private final CmsAdvertisementCategoryRepository cmsAdvertisementCategoryRepository;

    public CmsAdvertisementCategoryService(CmsAdvertisementCategoryRepository cmsAdvertisementCategoryRepository) {
        this.cmsAdvertisementCategoryRepository = cmsAdvertisementCategoryRepository;
    }

    /**
     * Save a cmsAdvertisementCategory.
     *
     * @param cmsAdvertisementCategory the entity to save.
     * @return the persisted entity.
     */
    public CmsAdvertisementCategory save(CmsAdvertisementCategory cmsAdvertisementCategory) {
        log.debug("Request to save CmsAdvertisementCategory : {}", cmsAdvertisementCategory);
        return cmsAdvertisementCategoryRepository.save(cmsAdvertisementCategory);
    }

    /**
     * Get all the cmsAdvertisementCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CmsAdvertisementCategory> findAll() {
        log.debug("Request to get all CmsAdvertisementCategories");
        return cmsAdvertisementCategoryRepository.findAll();
    }


    /**
     * Get one cmsAdvertisementCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsAdvertisementCategory> findOne(Long id) {
        log.debug("Request to get CmsAdvertisementCategory : {}", id);
        return cmsAdvertisementCategoryRepository.findById(id);
    }

    /**
     * Delete the cmsAdvertisementCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsAdvertisementCategory : {}", id);
        cmsAdvertisementCategoryRepository.deleteById(id);
    }
}
