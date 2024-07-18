package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.MeetingParticipantNonB2B;
import e4i.domain.Message;
import e4i.domain.Thread;
import e4i.service.CalendarService;
import e4i.service.CompanyService;
import e4i.service.MailService;
import e4i.service.MeetingParticipantNonB2BService;
import e4i.service.MeetingParticipantService;
import e4i.service.MeetingService;
import e4i.service.MessageService;
import e4i.service.ThreadService;
import e4i.web.rest.dto.NotificationMailDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.Meeting}.
 */
@RestController
@RequestMapping("/api")
public class MeetingResource {

    private final Logger log = LoggerFactory.getLogger(MeetingResource.class);

    private static final String ENTITY_NAME = "meeting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingService meetingService;
    
    @Autowired
    MeetingParticipantService meetingParticipantService;
    
    @Autowired
    MeetingParticipantNonB2BService meetingParticipantNonB2BService;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    ThreadService threadService;
    
    @Autowired
    MessageService messageService;
    
    @Autowired
    CalendarService calendarService;
    
    private final MailService mailService;

    public MeetingResource(MeetingService meetingService, MailService mailService) {
        this.meetingService = meetingService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /meetings} : Create a new meeting.
     *
     * @param meeting the meeting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meeting, or with status {@code 400 (Bad Request)} if the meeting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meetings")
    public ResponseEntity<Meeting> createMeeting(@Valid @RequestBody Meeting meeting) throws URISyntaxException {
        log.debug("REST request to save Meeting : {}", meeting);
        if (meeting.getId() != null) {
            throw new BadRequestAlertException("A new meeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Meeting result = meetingService.save(meeting);
        return ResponseEntity.created(new URI("/api/meetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meetings} : Updates an existing meeting.
     *
     * @param meeting the meeting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meeting,
     * or with status {@code 400 (Bad Request)} if the meeting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meeting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meetings")
    public ResponseEntity<Meeting> updateMeeting(@Valid @RequestBody Meeting meeting) throws URISyntaxException {
        log.debug("REST request to update Meeting : {}", meeting);
        if (meeting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Meeting result = meetingService.save(meeting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meeting.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meetings} : get all the meetings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetings in body.
     */
    @GetMapping("/meetings")
    public List<Meeting> getAllMeetings() {
        log.debug("REST request to get all Meetings");
        return meetingService.findAll();
    }

    /**
     * {@code GET  /meetings/:id} : get the "id" meeting.
     *
     * @param id the id of the meeting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meeting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meetings/{id}")
    public ResponseEntity<Meeting> getMeeting(@PathVariable Long id) {
        log.debug("REST request to get Meeting : {}", id);
        Optional<Meeting> meeting = meetingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meeting);
    }

    /**
     * {@code DELETE  /meetings/:id} : delete the "id" meeting.
     *
     * @param id the id of the meeting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        log.debug("REST request to delete Meeting : {}", id);
        meetingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/meetings/new")
    public ResponseEntity<Meeting> createMeetingWithParticipants(
    		@RequestBody Meeting meeting,
    		@RequestParam Long organizerId,
    		@RequestParam List<Long> participantIds,
    		@RequestParam List<String> nonB2BparticipantIds
    		) throws URISyntaxException {
        log.debug("REST request to create new Meeting organized by company : {}", organizerId);
        if (meeting.getId() != null) {
            throw new BadRequestAlertException("A new meeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        try {
        	meeting.setMeetingParticipants(new HashSet<>());
        	meeting.setThreads(new HashSet<>());
            Meeting newMeeting = meetingService.save(meeting);
            
            Company companyOrganizer = companyService.getOneById(organizerId);
            meetingParticipantService.addMeetingOrganizer(newMeeting, companyOrganizer);
            
            for (Long companyId : participantIds) {
            	Company companyParticipant = companyService.getOneById(companyId);
            	meetingParticipantService.addMeetingParticipant(newMeeting, companyParticipant);

                // send messages to participant companies
            	Thread thread = threadService.createThreadForNewMeeting(newMeeting, companyParticipant);
            	Message message = messageService.createFirstMessageInThreadMeeting(thread, newMeeting, companyOrganizer);
            
                // send email notifications participant companies
            	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForMeetingInvitation(
            			newMeeting, companyOrganizer, companyParticipant);
            	if (!mailDTO.getEmails().isEmpty()) {
            		mailService.sendNotificationMail(mailDTO);
            	}           
            }
            
            for (String email : nonB2BparticipantIds) {
            	meetingParticipantNonB2BService.addMeetingParticipant(newMeeting, email);
            }
            
            if (!nonB2BparticipantIds.isEmpty()) {
            	ByteArrayResource attachment = calendarService.createICS(meeting);
            	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForMeetingInvitationNonB2B(
            			newMeeting, companyOrganizer, nonB2BparticipantIds, attachment);
            	mailService.sendNotificationMail(mailDTO);
            }
        		                        
            return ResponseEntity.created(new URI("/api/meetings/new/" + newMeeting.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, newMeeting.getId().toString()))
                .body(newMeeting);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
    }
    
    @PutMapping("/meetings/edit")
    @Transactional
    public ResponseEntity<Meeting> editMeetingWithParticipants(
    		@RequestBody Meeting meetingWithUpdates,
    		@RequestParam Long meetingId,
    		@RequestParam List<Long> participantIdsToAdd,
    		@RequestParam List<Long> participantIdsToRemove,
    		@RequestParam List<String> nonB2BparticipantIdsToAdd,
    		@RequestParam List<String> nonB2BparticipantIdsToRemove
    		) throws URISyntaxException {
        log.debug("REST request to edit Meeting: {}", meetingId);
        if (meetingId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        try {
            Meeting editedMeeting = meetingService.editMeetingWithParticipants(meetingId, meetingWithUpdates);
            
            for (Long id : participantIdsToAdd) {
            	Company companyParticipant = companyService.getOneById(id);
            	meetingParticipantService.addMeetingParticipant(editedMeeting, companyParticipant);
            }
            
            for (Long id : participantIdsToRemove) {
            	Company companyParticipant = companyService.getOneById(id);
            	meetingParticipantService.deleteMeetingParticipant(editedMeeting, companyParticipant);
            }
            
            for (String email : nonB2BparticipantIdsToAdd) {
            	meetingParticipantNonB2BService.addMeetingParticipant(editedMeeting, email);
            }
            
            for (String email : nonB2BparticipantIdsToRemove) {
            	meetingParticipantNonB2BService.deleteMeetingParticipant(editedMeeting, email);
            }
            
            return ResponseEntity.created(new URI("/api/meetings/edit/" + editedMeeting.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, editedMeeting.getId().toString()))
                .body(editedMeeting);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		}
    }
    
    @GetMapping("/meetings/company/{companyId}")
    public List<Meeting> getAllMeetingsForCompany(@PathVariable Long companyId) {
        log.debug("REST request to get all Meetings for Company {}", companyId);
        return meetingService.findAllForCompany(companyId);
    }
    
    @PostMapping("/meetings/create-ics/{meetingId}")
    public ResponseEntity<Resource> createICSForMeeting(@PathVariable Long meetingId) {
        log.debug("REST request to create ICS for Meeting {}", meetingId);
        
        try {
            Meeting meeting = meetingService.getOne(meetingId);
            Resource out = calendarService.createICS(meeting);
   		 	String icsReport = "b2b_sastanak.ics";
            
            return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + icsReport)
			        .contentType(MediaType.parseMediaType("text/calendar")).body(out);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		} 
    }
    
    @Deprecated
    @PutMapping("/meetings/update-notes")
    public ResponseEntity<Meeting> updateMeetingNotes(@RequestParam Long meetingId, @RequestParam String notes) {
        log.debug("REST request update notes for Meeting {}", meetingId);
        
        try {
            Meeting meeting = meetingService.getOne(meetingId);
            meeting.setNotes(notes);
            Meeting result = meetingService.save(meeting);

            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingId.toString()))
                    .body(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.noContent().build();
		} 
    }
}
