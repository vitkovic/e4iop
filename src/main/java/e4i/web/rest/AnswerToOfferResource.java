package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.AnswerToOffer;
import e4i.repository.AnswerToOfferRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.AnswerToOffer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnswerToOfferResource {

    private final Logger log = LoggerFactory.getLogger(AnswerToOfferResource.class);

    private static final String ENTITY_NAME = "answerToOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerToOfferRepository answerToOfferRepository;

    public AnswerToOfferResource(AnswerToOfferRepository answerToOfferRepository) {
        this.answerToOfferRepository = answerToOfferRepository;
    }

    /**
     * {@code POST  /answer-to-offers} : Create a new answerToOffer.
     *
     * @param answerToOffer the answerToOffer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerToOffer, or with status {@code 400 (Bad Request)} if the answerToOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-to-offers")
    public ResponseEntity<AnswerToOffer> createAnswerToOffer(@RequestBody AnswerToOffer answerToOffer) throws URISyntaxException {
        log.debug("REST request to save AnswerToOffer : {}", answerToOffer);
        if (answerToOffer.getId() != null) {
            throw new BadRequestAlertException("A new answerToOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerToOffer result = answerToOfferRepository.save(answerToOffer);
        return ResponseEntity.created(new URI("/api/answer-to-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-to-offers} : Updates an existing answerToOffer.
     *
     * @param answerToOffer the answerToOffer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerToOffer,
     * or with status {@code 400 (Bad Request)} if the answerToOffer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerToOffer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-to-offers")
    public ResponseEntity<AnswerToOffer> updateAnswerToOffer(@RequestBody AnswerToOffer answerToOffer) throws URISyntaxException {
        log.debug("REST request to update AnswerToOffer : {}", answerToOffer);
        if (answerToOffer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerToOffer result = answerToOfferRepository.save(answerToOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, answerToOffer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-to-offers} : get all the answerToOffers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerToOffers in body.
     */
    @GetMapping("/answer-to-offers")
    public List<AnswerToOffer> getAllAnswerToOffers() {
        log.debug("REST request to get all AnswerToOffers");
        return answerToOfferRepository.findAll();
    }

    /**
     * {@code GET  /answer-to-offers/:id} : get the "id" answerToOffer.
     *
     * @param id the id of the answerToOffer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerToOffer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-to-offers/{id}")
    public ResponseEntity<AnswerToOffer> getAnswerToOffer(@PathVariable Long id) {
        log.debug("REST request to get AnswerToOffer : {}", id);
        Optional<AnswerToOffer> answerToOffer = answerToOfferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(answerToOffer);
    }

    /**
     * {@code DELETE  /answer-to-offers/:id} : delete the "id" answerToOffer.
     *
     * @param id the id of the answerToOffer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-to-offers/{id}")
    public ResponseEntity<Void> deleteAnswerToOffer(@PathVariable Long id) {
        log.debug("REST request to delete AnswerToOffer : {}", id);
        answerToOfferRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
