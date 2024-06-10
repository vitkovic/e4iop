package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.MeetingParticipantStatus;
import e4i.service.MeetingParticipantStatusService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.MeetingParticipantStatus}.
 */
@RestController
@RequestMapping("/api")
public class MeetingParticipantStatusResource {
    private final Logger log = LoggerFactory.getLogger(MeetingParticipantStatusResource.class);

    private static final String ENTITY_NAME = "meetingParticipantStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingParticipantStatusService meetingParticipantStatusService;

    public MeetingParticipantStatusResource(MeetingParticipantStatusService meetingParticipantStatusService) {
        this.meetingParticipantStatusService = meetingParticipantStatusService;
    }

    @PostMapping("/meeting-participant-statuses")
    public ResponseEntity<MeetingParticipantStatus> createMeetingParticipantStatus(@Valid @RequestBody MeetingParticipantStatus meetingParticipantStatus) throws URISyntaxException {
        log.debug("REST request to save MeetingParticipantStatus : {}", meetingParticipantStatus);
        if (meetingParticipantStatus.getId() != null) {
            throw new BadRequestAlertException("A new meetingParticipantStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingParticipantStatus result = meetingParticipantStatusService.save(meetingParticipantStatus);
        return ResponseEntity.created(new URI("/api/meeting-participant-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/meeting-participant-statuses")
    public ResponseEntity<MeetingParticipantStatus> updateMeetingParticipantStatus(@Valid @RequestBody MeetingParticipantStatus meetingParticipantStatus) throws URISyntaxException {
        log.debug("REST request to update MeetingParticipantStatus : {}", meetingParticipantStatus);
        if (meetingParticipantStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingParticipantStatus result = meetingParticipantStatusService.save(meetingParticipantStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingParticipantStatus.getId().toString()))
            .body(result);
    }

    @GetMapping("/meeting-participant-statuses")
    public List<MeetingParticipantStatus> getAllMeetingParticipantStatuses() {
        log.debug("REST request to get all MeetingParticipantStatuses");
        return meetingParticipantStatusService.findAll();
    }

    @GetMapping("/meeting-participant-statuses/{id}")
    public ResponseEntity<MeetingParticipantStatus> getMeetingParticipantStatus(@PathVariable Long id) {
        log.debug("REST request to get MeetingParticipantStatus : {}", id);
        Optional<MeetingParticipantStatus> meetingParticipantStatus = meetingParticipantStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingParticipantStatus);
    }

    @DeleteMapping("/meeting-participant-statuses/{id}")
    public ResponseEntity<Void> deleteMeetingParticipantStatus(@PathVariable Long id) {
        log.debug("REST request to delete MeetingParticipantStatus : {}", id);
        meetingParticipantStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
