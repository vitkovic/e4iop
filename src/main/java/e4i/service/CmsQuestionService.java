package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.CmsQuestion;
import e4i.repository.CmsQuestionRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CmsQuestion}.
 */
@Service
@Transactional
public class CmsQuestionService {

    private final Logger log = LoggerFactory.getLogger(CmsQuestionService.class);

    private final CmsQuestionRepository cmsQuestionRepository;

    public CmsQuestionService(CmsQuestionRepository cmsQuestionRepository) {
        this.cmsQuestionRepository = cmsQuestionRepository;
    }

    /**
     * Save a cmsQuestion.
     *
     * @param cmsQuestion the entity to save.
     * @return the persisted entity.
     */
    public CmsQuestion save(CmsQuestion cmsQuestion) {
        log.debug("Request to save CmsQuestion : {}", cmsQuestion);
        return cmsQuestionRepository.save(cmsQuestion);
    }

    /**
     * Get all the cmsQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CmsQuestion> findAll(Pageable pageable) {
        log.debug("Request to get all CmsQuestions");
        return cmsQuestionRepository.findAll(pageable);
    }


    /**
     * Get one cmsQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CmsQuestion> findOne(Long id) {
        log.debug("Request to get CmsQuestion : {}", id);
        return cmsQuestionRepository.findById(id);
    }

    /**
     * Delete the cmsQuestion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CmsQuestion : {}", id);
        cmsQuestionRepository.deleteById(id);
    }
}
