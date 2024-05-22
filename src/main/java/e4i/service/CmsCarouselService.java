package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsCarousel;
import e4i.repository.CmsCarouselRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsCarousel}.
 */
@Service
@Transactional
public class CmsCarouselService {

    private final Logger log = LoggerFactory.getLogger(CmsCarouselService.class);

    private final CmsCarouselRepository cmsCarouselRepository;

    public CmsCarouselService(CmsCarouselRepository cmsCarouselRepository) {
        this.cmsCarouselRepository = cmsCarouselRepository;
    }

    /**
     * Save a cmsCarousel.
     *
     * @param cmsCarousel the entity to save.
     * @return the persisted entity.
     */
    public CmsCarousel save(CmsCarousel cmsCarousel) {
        log.debug("Request to save CmsCarousel : {}", cmsCarousel);
        return cmsCarouselRepository.save(cmsCarousel);
    }

    /**
     * Get all the cmsCarousels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsCarousel> findAll(Pageable pageable) {
        log.debug("Request to get all CmsCarousels");
        return cmsCarouselRepository.findAll(pageable);
    }


    /**
     * Get one cmsCarousel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsCarousel> findOne(Long id) {
        log.debug("Request to get CmsCarousel : {}", id);
        return cmsCarouselRepository.findById(id);
    }

    /**
     * Delete the cmsCarousel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsCarousel : {}", id);
        cmsCarouselRepository.deleteById(id);
    }
}
