package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.MeetingParticipant;
import e4i.service.MeetingParticipantService;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.MeetingParticipant}.
 */
@RestController
@RequestMapping("/api")
public class MeetingParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantResource.class);

    private static final String ENTITY_NAME = "meetingParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingParticipantService meetingParticipantService;

    public MeetingParticipantResource(MeetingParticipantService meetingParticipantService) {
        this.meetingParticipantService = meetingParticipantService;
    }

    /**
     * {@code POST  /meeting-participants} : Create a new meetingParticipant.
     *
     * @param meetingParticipant the meetingParticipant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingParticipant, or with status {@code 400 (Bad Request)} if the meetingParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-participants")
    public ResponseEntity<MeetingParticipant> createMeetingParticipant(@RequestBody MeetingParticipant meetingParticipant) throws URISyntaxException {
        log.debug("REST request to save MeetingParticipant : {}", meetingParticipant);
        if (meetingParticipant.getId() != null) {
            throw new BadRequestAlertException("A new meetingParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingParticipant result = meetingParticipantService.save(meetingParticipant);
        return ResponseEntity.created(new URI("/api/meeting-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-participants} : Updates an existing meetingParticipant.
     *
     * @param meetingParticipant the meetingParticipant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingParticipant,
     * or with status {@code 400 (Bad Request)} if the meetingParticipant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingParticipant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-participants")
    public ResponseEntity<MeetingParticipant> updateMeetingParticipant(@RequestBody MeetingParticipant meetingParticipant) throws URISyntaxException {
        log.debug("REST request to update MeetingParticipant : {}", meetingParticipant);
        if (meetingParticipant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingParticipant result = meetingParticipantService.save(meetingParticipant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingParticipant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meeting-participants} : get all the meetingParticipants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetingParticipants in body.
     */
    @GetMapping("/meeting-participants")
    public List<MeetingParticipant> getAllMeetingParticipants() {
        log.debug("REST request to get all MeetingParticipants");
        return meetingParticipantService.findAll();
    }

    /**
     * {@code GET  /meeting-participants/:id} : get the "id" meetingParticipant.
     *
     * @param id the id of the meetingParticipant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingParticipant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meeting-participants/{id}")
    public ResponseEntity<MeetingParticipant> getMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to get MeetingParticipant : {}", id);
        Optional<MeetingParticipant> meetingParticipant = meetingParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingParticipant);
    }

    /**
     * {@code DELETE  /meeting-participants/:id} : delete the "id" meetingParticipant.
     *
     * @param id the id of the meetingParticipant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-participants/{id}")
    public ResponseEntity<Void> deleteMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MeetingParticipant : {}", id);
        meetingParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
