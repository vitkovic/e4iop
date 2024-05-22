package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.CmsQuestion;
import e4i.service.CmsQuestionService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsQuestion}.
 */
@RestController
@RequestMapping("/api")
public class CmsQuestionResource {

    private final Logger log = LoggerFactory.getLogger(CmsQuestionResource.class);

    private static final String ENTITY_NAME = "cmsQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsQuestionService cmsQuestionService;

    public CmsQuestionResource(CmsQuestionService cmsQuestionService) {
        this.cmsQuestionService = cmsQuestionService;
    }

    /**
     * {@code POST  /cms-questions} : Create a new cmsQuestion.
     *
     * @param cmsQuestion the cmsQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsQuestion, or with status {@code 400 (Bad Request)} if the cmsQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-questions")
    public ResponseEntity<CmsQuestion> createCmsQuestion(@Valid @RequestBody CmsQuestion cmsQuestion) throws URISyntaxException {
        log.debug("REST request to save CmsQuestion : {}", cmsQuestion);
        if (cmsQuestion.getId() != null) {
            throw new BadRequestAlertException("A new cmsQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsQuestion result = cmsQuestionService.save(cmsQuestion);
        return ResponseEntity.created(new URI("/api/cms-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-questions} : Updates an existing cmsQuestion.
     *
     * @param cmsQuestion the cmsQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsQuestion,
     * or with status {@code 400 (Bad Request)} if the cmsQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-questions")
    public ResponseEntity<CmsQuestion> updateCmsQuestion(@Valid @RequestBody CmsQuestion cmsQuestion) throws URISyntaxException {
        log.debug("REST request to update CmsQuestion : {}", cmsQuestion);
        if (cmsQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsQuestion result = cmsQuestionService.save(cmsQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-questions} : get all the cmsQuestions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsQuestions in body.
     */
    @GetMapping("/cms-questions")
    public ResponseEntity<List<CmsQuestion>> getAllCmsQuestions(Pageable pageable) {
        log.debug("REST request to get a page of CmsQuestions");
        Page<CmsQuestion> page = cmsQuestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-questions/:id} : get the "id" cmsQuestion.
     *
     * @param id the id of the cmsQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-questions/{id}")
    public ResponseEntity<CmsQuestion> getCmsQuestion(@PathVariable Long id) {
        log.debug("REST request to get CmsQuestion : {}", id);
        Optional<CmsQuestion> cmsQuestion = cmsQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsQuestion);
    }

    /**
     * {@code DELETE  /cms-questions/:id} : delete the "id" cmsQuestion.
     *
     * @param id the id of the cmsQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-questions/{id}")
    public ResponseEntity<Void> deleteCmsQuestion(@PathVariable Long id) {
        log.debug("REST request to delete CmsQuestion : {}", id);
        cmsQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
