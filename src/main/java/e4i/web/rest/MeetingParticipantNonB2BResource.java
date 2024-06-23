package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.MeetingParticipantNonB2B;
import e4i.service.MeetingParticipantNonB2BService;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.MeetingParticipantNonB2B}.
 */
@RestController
@RequestMapping("/api")
public class MeetingParticipantNonB2BResource {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantNonB2BResource.class);

    private static final String ENTITY_NAME = "meetingParticipantNonB2B";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingParticipantNonB2BService meetingParticipantNonB2BService;

    public MeetingParticipantNonB2BResource(MeetingParticipantNonB2BService meetingParticipantNonB2BService) {
        this.meetingParticipantNonB2BService = meetingParticipantNonB2BService;
    }

    /**
     * {@code POST  /meeting-participants-non-b2b} : Create a new meetingParticipantNonB2B.
     *
     * @param meetingParticipantNonB2B the meetingParticipantNonB2B to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingParticipantNonB2B, or with status {@code 400 (Bad Request)} if the meetingParticipantNonB2B has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-participants-non-b2b")
    public ResponseEntity<MeetingParticipantNonB2B> createMeetingParticipantNonB2B(@RequestBody MeetingParticipantNonB2B meetingParticipantNonB2B) throws URISyntaxException {
        log.debug("REST request to save MeetingParticipantNonB2B : {}", meetingParticipantNonB2B);
        if (meetingParticipantNonB2B.getId() != null) {
            throw new BadRequestAlertException("A new meetingParticipantNonB2B cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingParticipantNonB2B result = meetingParticipantNonB2BService.save(meetingParticipantNonB2B);
        return ResponseEntity.created(new URI("/api/meeting-participants-non-b2b/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-participants-non-b2b} : Updates an existing meetingParticipantNonB2B.
     *s
     * @param meetingParticipantNonB2B the meetingParticipantNonB2B to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingParticipantNonB2B,
     * or with status {@code 400 (Bad Request)} if the meetingParticipantNonB2B is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingParticipantNonB2B couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-participants-non-b2b")
    public ResponseEntity<MeetingParticipantNonB2B> updateMeetingParticipantNonB2B(@RequestBody MeetingParticipantNonB2B meetingParticipantNonB2B) throws URISyntaxException {
        log.debug("REST request to update MeetingParticipantNonB2B : {}", meetingParticipantNonB2B);
        if (meetingParticipantNonB2B.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingParticipantNonB2B result = meetingParticipantNonB2BService.save(meetingParticipantNonB2B);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingParticipantNonB2B.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meeting-participants-non-b2b} : get all the meetingParticipantsNonB2B.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetingParticipantsNonB2B in body.
     */
    @GetMapping("/meeting-participants-non-b2b")
    public List<MeetingParticipantNonB2B> getAllMeetingParticipantsNonB2B() {
        log.debug("REST request to get all MeetingParticipantsNonB2B");
        return meetingParticipantNonB2BService.findAll();
    }

    /**
     * {@code GET  /meeting-participants-non-b2b/:id} : get the "id" meetingParticipantNonB2B.
     *
     * @param id the id of the meetingParticipantNonB2B to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingParticipantNonB2B, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meeting-participants-non-b2b/{id}")
    public ResponseEntity<MeetingParticipantNonB2B> getMeetingParticipantNonB2B(@PathVariable Long id) {
        log.debug("REST request to get MeetingParticipantNonB2B : {}", id);
        Optional<MeetingParticipantNonB2B> meetingParticipantNonB2B = meetingParticipantNonB2BService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingParticipantNonB2B);
    }

    /**
     * {@code DELETE  /meeting-participants-non-b2b/:id} : delete the "id" meetingParticipantNonB2B.
     *
     * @param id the id of the meetingParticipantNonB2B to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-participants-non-b2b/{id}")
    public ResponseEntity<Void> deleteMeetingParticipantNonB2B(@PathVariable Long id) {
        log.debug("REST request to delete MeetingParticipantNonB2B : {}", id);
        meetingParticipantNonB2BService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
      
    @GetMapping("/meeting-participants-non-b2b/meeting-all/{meetingId}")
    public List<MeetingParticipantNonB2B> findAllForMeeting(@PathVariable Long meetingId) {
        log.debug("REST request to get all MeetingParticipantsNonB2B for Meeting {}", meetingId);   
        return meetingParticipantNonB2BService.findAllByMeetingId(meetingId);
    }
    
//    @GetMapping("/meeting-participants-non-b2b/meeting-email/{meetingId}/{email}")
//    public MeetingParticipantNonB2B findOneForMeetingAndEmail(
//    		@PathVariable Long meetingId,
//    		@PathVariable String email
//    		) {
//        log.debug("REST request to find MeetingParticipantsNonB2B for Meeting {} and Company {}", meetingId, companyId);   
//        return meetingParticipantNonB2BService.findOneByMeetingAndEmail(meetingId, email);
//    }
    
}
