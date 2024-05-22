package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsPage;
import e4i.repository.CmsPageRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsPage}.
 */
@Service
@Transactional
public class CmsPageService {

    private final Logger log = LoggerFactory.getLogger(CmsPageService.class);

    private final CmsPageRepository cmsPageRepository;

    public CmsPageService(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

    /**
     * Save a cmsPage.
     *
     * @param cmsPage the entity to save.
     * @return the persisted entity.
     */
    public CmsPage save(CmsPage cmsPage) {
        log.debug("Request to save CmsPage : {}", cmsPage);
        return cmsPageRepository.save(cmsPage);
    }

    /**
     * Get all the cmsPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsPage> findAll(Pageable pageable) {
        log.debug("Request to get all CmsPages");
        return cmsPageRepository.findAll(pageable);
    }


    /**
     * Get all the cmsPages with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CmsPage> findAllWithEagerRelationships(Pageable pageable) {
        return cmsPageRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one cmsPage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsPage> findOne(Long id) {
        log.debug("Request to get CmsPage : {}", id);
        return cmsPageRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cmsPage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsPage : {}", id);
        cmsPageRepository.deleteById(id);
    }
}
