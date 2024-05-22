package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsTestimonials;
import e4i.repository.CmsTestimonialsRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsTestimonials}.
 */
@Service
@Transactional
public class CmsTestimonialsService {

    private final Logger log = LoggerFactory.getLogger(CmsTestimonialsService.class);

    private final CmsTestimonialsRepository cmsTestimonialsRepository;

    public CmsTestimonialsService(CmsTestimonialsRepository cmsTestimonialsRepository) {
        this.cmsTestimonialsRepository = cmsTestimonialsRepository;
    }

    /**
     * Save a cmsTestimonials.
     *
     * @param cmsTestimonials the entity to save.
     * @return the persisted entity.
     */
    public CmsTestimonials save(CmsTestimonials cmsTestimonials) {
        log.debug("Request to save CmsTestimonials : {}", cmsTestimonials);
        return cmsTestimonialsRepository.save(cmsTestimonials);
    }

    /**
     * Get all the cmsTestimonials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsTestimonials> findAll(Pageable pageable) {
        log.debug("Request to get all CmsTestimonials");
        return cmsTestimonialsRepository.findAll(pageable);
    }


    /**
     * Get one cmsTestimonials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsTestimonials> findOne(Long id) {
        log.debug("Request to get CmsTestimonials : {}", id);
        return cmsTestimonialsRepository.findById(id);
    }

    /**
     * Delete the cmsTestimonials by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsTestimonials : {}", id);
        cmsTestimonialsRepository.deleteById(id);
    }
}
