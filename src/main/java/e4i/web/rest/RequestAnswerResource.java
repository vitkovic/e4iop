package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.RequestAnswer;
import e4i.repository.RequestAnswerRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RequestAnswer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequestAnswerResource {

    private final Logger log = LoggerFactory.getLogger(RequestAnswerResource.class);

    private static final String ENTITY_NAME = "requestAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestAnswerRepository requestAnswerRepository;

    public RequestAnswerResource(RequestAnswerRepository requestAnswerRepository) {
        this.requestAnswerRepository = requestAnswerRepository;
    }

    /**
     * {@code POST  /request-answers} : Create a new requestAnswer.
     *
     * @param requestAnswer the requestAnswer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestAnswer, or with status {@code 400 (Bad Request)} if the requestAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-answers")
    public ResponseEntity<RequestAnswer> createRequestAnswer(@RequestBody RequestAnswer requestAnswer) throws URISyntaxException {
        log.debug("REST request to save RequestAnswer : {}", requestAnswer);
        if (requestAnswer.getId() != null) {
            throw new BadRequestAlertException("A new requestAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestAnswer result = requestAnswerRepository.save(requestAnswer);
        return ResponseEntity.created(new URI("/api/request-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-answers} : Updates an existing requestAnswer.
     *
     * @param requestAnswer the requestAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestAnswer,
     * or with status {@code 400 (Bad Request)} if the requestAnswer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-answers")
    public ResponseEntity<RequestAnswer> updateRequestAnswer(@RequestBody RequestAnswer requestAnswer) throws URISyntaxException {
        log.debug("REST request to update RequestAnswer : {}", requestAnswer);
        if (requestAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestAnswer result = requestAnswerRepository.save(requestAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestAnswer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-answers} : get all the requestAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestAnswers in body.
     */
    @GetMapping("/request-answers")
    public List<RequestAnswer> getAllRequestAnswers() {
        log.debug("REST request to get all RequestAnswers");
        return requestAnswerRepository.findAll();
    }

    /**
     * {@code GET  /request-answers/:id} : get the "id" requestAnswer.
     *
     * @param id the id of the requestAnswer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestAnswer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-answers/{id}")
    public ResponseEntity<RequestAnswer> getRequestAnswer(@PathVariable Long id) {
        log.debug("REST request to get RequestAnswer : {}", id);
        Optional<RequestAnswer> requestAnswer = requestAnswerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(requestAnswer);
    }

    /**
     * {@code DELETE  /request-answers/:id} : delete the "id" requestAnswer.
     *
     * @param id the id of the requestAnswer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-answers/{id}")
    public ResponseEntity<Void> deleteRequestAnswer(@PathVariable Long id) {
        log.debug("REST request to delete RequestAnswer : {}", id);
        requestAnswerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
