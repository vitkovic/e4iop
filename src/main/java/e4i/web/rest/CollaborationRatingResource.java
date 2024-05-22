package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.CollaborationRating;
import e4i.service.CollaborationRatingService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CollaborationRating}.
 */
@RestController
@RequestMapping("/api")
public class CollaborationRatingResource {

    private final Logger log = LoggerFactory.getLogger(CollaborationRatingResource.class);

    private static final String ENTITY_NAME = "collaborationRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollaborationRatingService collaborationRatingService;

    public CollaborationRatingResource(CollaborationRatingService collaborationRatingService) {
        this.collaborationRatingService = collaborationRatingService;
    }

    /**
     * {@code POST  /collaboration-ratings} : Create a new collaborationRating.
     *
     * @param collaborationRating the collaborationRating to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collaborationRating, or with status {@code 400 (Bad Request)} if the collaborationRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collaboration-ratings")
    public ResponseEntity<CollaborationRating> createCollaborationRating(@Valid @RequestBody CollaborationRating collaborationRating) throws URISyntaxException {
        log.debug("REST request to save CollaborationRating : {}", collaborationRating);
        if (collaborationRating.getId() != null) {
            throw new BadRequestAlertException("A new collaborationRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollaborationRating result = collaborationRatingService.save(collaborationRating);
        return ResponseEntity.created(new URI("/api/collaboration-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collaboration-ratings} : Updates an existing collaborationRating.
     *
     * @param collaborationRating the collaborationRating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collaborationRating,
     * or with status {@code 400 (Bad Request)} if the collaborationRating is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collaborationRating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collaboration-ratings")
    public ResponseEntity<CollaborationRating> updateCollaborationRating(@Valid @RequestBody CollaborationRating collaborationRating) throws URISyntaxException {
        log.debug("REST request to update CollaborationRating : {}", collaborationRating);
        if (collaborationRating.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CollaborationRating result = collaborationRatingService.save(collaborationRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collaborationRating.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collaboration-ratings} : get all the collaborationRatings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collaborationRatings in body.
     */
    @GetMapping("/collaboration-ratings")
    public List<CollaborationRating> getAllCollaborationRatings() {
        log.debug("REST request to get all CollaborationRatings");
        return collaborationRatingService.findAll();
    }

    /**
     * {@code GET  /collaboration-ratings/:id} : get the "id" collaborationRating.
     *
     * @param id the id of the collaborationRating to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collaborationRating, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collaboration-ratings/{id}")
    public ResponseEntity<CollaborationRating> getCollaborationRating(@PathVariable Long id) {
        log.debug("REST request to get CollaborationRating : {}", id);
        Optional<CollaborationRating> collaborationRating = collaborationRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collaborationRating);
    }

    /**
     * {@code DELETE  /collaboration-ratings/:id} : delete the "id" collaborationRating.
     *
     * @param id the id of the collaborationRating to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collaboration-ratings/{id}")
    public ResponseEntity<Void> deleteCollaborationRating(@PathVariable Long id) {
        log.debug("REST request to delete CollaborationRating : {}", id);
        collaborationRatingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
