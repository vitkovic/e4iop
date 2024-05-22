package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsSlider;
import e4i.repository.CmsSliderRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsSlider}.
 */
@Service
@Transactional
public class CmsSliderService {

    private final Logger log = LoggerFactory.getLogger(CmsSliderService.class);

    private final CmsSliderRepository cmsSliderRepository;

    public CmsSliderService(CmsSliderRepository cmsSliderRepository) {
        this.cmsSliderRepository = cmsSliderRepository;
    }

    /**
     * Save a cmsSlider.
     *
     * @param cmsSlider the entity to save.
     * @return the persisted entity.
     */
    public CmsSlider save(CmsSlider cmsSlider) {
        log.debug("Request to save CmsSlider : {}", cmsSlider);
        return cmsSliderRepository.save(cmsSlider);
    }

    /**
     * Get all the cmsSliders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsSlider> findAll(Pageable pageable) {
        log.debug("Request to get all CmsSliders");
        return cmsSliderRepository.findAll(pageable);
    }


    /**
     * Get one cmsSlider by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsSlider> findOne(Long id) {
        log.debug("Request to get CmsSlider : {}", id);
        return cmsSliderRepository.findById(id);
    }

    /**
     * Delete the cmsSlider by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsSlider : {}", id);
        cmsSliderRepository.deleteById(id);
    }
}
