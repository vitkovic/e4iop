package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.MeetingType;
import e4i.service.MeetingTypeService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.MeetingType}.
 */
@RestController
@RequestMapping("/api")
public class MeetingTypeResource {

    private final Logger log = LoggerFactory.getLogger(MeetingTypeResource.class);

    private static final String ENTITY_NAME = "meetingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingTypeService meetingTypeService;

    public MeetingTypeResource(MeetingTypeService meetingTypeService) {
        this.meetingTypeService = meetingTypeService;
    }

    /**
     * {@code POST  /meeting-types} : Create a new meetingType.
     *
     * @param meetingType the meetingType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingType, or with status {@code 400 (Bad Request)} if the meetingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-types")
    public ResponseEntity<MeetingType> createMeetingType(@Valid @RequestBody MeetingType meetingType) throws URISyntaxException {
        log.debug("REST request to save MeetingType : {}", meetingType);
        if (meetingType.getId() != null) {
            throw new BadRequestAlertException("A new meetingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingType result = meetingTypeService.save(meetingType);
        return ResponseEntity.created(new URI("/api/meeting-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-types} : Updates an existing meetingType.
     *
     * @param meetingType the meetingType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingType,
     * or with status {@code 400 (Bad Request)} if the meetingType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-types")
    public ResponseEntity<MeetingType> updateMeetingType(@Valid @RequestBody MeetingType meetingType) throws URISyntaxException {
        log.debug("REST request to update MeetingType : {}", meetingType);
        if (meetingType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingType result = meetingTypeService.save(meetingType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meeting-types} : get all the meetingTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetingTypes in body.
     */
    @GetMapping("/meeting-types")
    public List<MeetingType> getAllMeetingTypes() {
        log.debug("REST request to get all MeetingTypes");
        return meetingTypeService.findAll();
    }

    /**
     * {@code GET  /meeting-types/:id} : get the "id" meetingType.
     *
     * @param id the id of the meetingType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meeting-types/{id}")
    public ResponseEntity<MeetingType> getMeetingType(@PathVariable Long id) {
        log.debug("REST request to get MeetingType : {}", id);
        Optional<MeetingType> meetingType = meetingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingType);
    }

    /**
     * {@code DELETE  /meeting-types/:id} : delete the "id" meetingType.
     *
     * @param id the id of the meetingType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-types/{id}")
    public ResponseEntity<Void> deleteMeetingType(@PathVariable Long id) {
        log.debug("REST request to delete MeetingType : {}", id);
        meetingTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
