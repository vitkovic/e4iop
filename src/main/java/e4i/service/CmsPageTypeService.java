package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsPageType;
import e4i.repository.CmsPageTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsPageType}.
 */
@Service
@Transactional
public class CmsPageTypeService {

    private final Logger log = LoggerFactory.getLogger(CmsPageTypeService.class);

    private final CmsPageTypeRepository cmsPageTypeRepository;

    public CmsPageTypeService(CmsPageTypeRepository cmsPageTypeRepository) {
        this.cmsPageTypeRepository = cmsPageTypeRepository;
    }

    /**
     * Save a cmsPageType.
     *
     * @param cmsPageType the entity to save.
     * @return the persisted entity.
     */
    public CmsPageType save(CmsPageType cmsPageType) {
        log.debug("Request to save CmsPageType : {}", cmsPageType);
        return cmsPageTypeRepository.save(cmsPageType);
    }

    /**
     * Get all the cmsPageTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CmsPageType> findAll() {
        log.debug("Request to get all CmsPageTypes");
        return cmsPageTypeRepository.findAll();
    }


    /**
     * Get one cmsPageType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsPageType> findOne(Long id) {
        log.debug("Request to get CmsPageType : {}", id);
        return cmsPageTypeRepository.findById(id);
    }

    /**
     * Delete the cmsPageType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsPageType : {}", id);
        cmsPageTypeRepository.deleteById(id);
    }
}
