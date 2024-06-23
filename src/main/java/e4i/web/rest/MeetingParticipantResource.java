package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.Company;
import e4i.domain.Meeting;
import e4i.domain.MeetingParticipant;
import e4i.domain.Message;
import e4i.domain.Thread;
import e4i.service.CalendarService;
import e4i.service.CompanyService;
import e4i.service.MailService;
import e4i.service.MeetingParticipantService;
import e4i.service.MeetingService;
import e4i.service.MessageService;
import e4i.service.ThreadService;
import e4i.web.rest.dto.CalendarEventDTO;
import e4i.web.rest.dto.NotificationMailDTO;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    MeetingService meetingService;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    ThreadService threadService;
    
    @Autowired
    MessageService messageService;
    
    private final MailService mailService;
    
    private final CalendarService calendarService;
    
    public MeetingParticipantResource(
    		MeetingParticipantService meetingParticipantService, 
    		MailService mailService, 
    		CalendarService calendarService) {
        this.meetingParticipantService = meetingParticipantService;
        this.mailService = mailService;
        this.calendarService = calendarService;
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
    
    @GetMapping("/meeting-participants/company-all/{companyId}")
    @Transactional
    public List<CalendarEventDTO> findAllNotRemovedForCompany(@PathVariable Long companyId) {
        log.debug("REST request to get all not removed MeetingParticipants for Company {}", companyId);   
        
        List<MeetingParticipant> meetingParticipants = meetingParticipantService.findAllByCompanyAndHasRemoved(companyId, false);
        
        List<CalendarEventDTO> calendarEvents = new ArrayList<>();
        for (MeetingParticipant mp : meetingParticipants) {
        	CalendarEventDTO event = new CalendarEventDTO();
        	event.setMeetingParticipant(mp);
        	event.setMeeting(mp.getMeeting());
        	
        	Map<String, String> colorMap = calendarService.getCalendarEventColor(mp.getMeeting().getId());
        	event.setColor(colorMap.get("color"));
        	event.setTextColor(colorMap.get("textColor"));
        	
        	calendarEvents.add(event);
        }
        
        return calendarEvents;
    }
    
    @GetMapping("/meeting-participants/meeting-all/{meetingId}")
    public List<MeetingParticipant> findAllForMeeting(@PathVariable Long meetingId) {
        log.debug("REST request to get all MeetingParticipants for Meeting {}", meetingId);   
        return meetingParticipantService.findAllByMeetingId(meetingId);
    }
    
    @GetMapping("/meeting-participants/meeting-company/{meetingId}/{companyId}")
    public MeetingParticipant findOneForMeetingAndCompany(
    		@PathVariable Long meetingId,
    		@PathVariable Long companyId
    		) {
        log.debug("REST request to find MeetingParticipants for Meeting {} and Company {}", meetingId, companyId);   
        return meetingParticipantService.findOneByMeetingAndCompany(meetingId, companyId);
    }
    
    @GetMapping("/meeting-participants/meeting-organizer/{meetingId}")
    public MeetingParticipant findCompanyOrganizerForMeeting(@PathVariable Long meetingId) {
        log.debug("REST request to get organizer company MeetingParticipant for Meeting {}", meetingId);   
        return meetingParticipantService.findOneByMeetingAndIsOrganizer(meetingId, true);
    }
    
    @PutMapping("/meeting-participants/accept/meeting-company/{meetingId}/{companyId}")
    public ResponseEntity<?> acceptMeetingForCompany(@PathVariable Long meetingId, @PathVariable Long companyId) {
        log.debug("REST request to accept Meeting {} for Company {}", meetingId, companyId);
        
        Optional<Boolean> isAcceptedOptional = meetingParticipantService.checkMeetingAccpetance(meetingId, companyId);
        
        if (isAcceptedOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No MeetingParticipant found for the given meetingId, companyId, and acceptance string");
        }
        
        Boolean isAccepted = isAcceptedOptional.get();
        
        if (isAccepted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Meeting has already been accepted by the company");
        }
        
        try {
            MeetingParticipant meetingParticipant = meetingParticipantService.acceptMeetingForCompany(meetingId, companyId);
            
            Meeting meeting = meetingService.getOne(meetingId);
            Company companyOrganizer = meetingParticipantService.findCompanyByMeetingAndIsOrganizer(meetingId, true);
            Company companyParticipant = companyService.getOneById(companyId);

            // send message to organiser company
        	Thread thread = threadService.createThreadForMeetingAcceptance(meeting, companyOrganizer);
        	Message message = messageService.createFirstMessageInThreadAcceptanceMeeting(thread, meeting, companyParticipant);
        
            // send email notifications organiser company
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForMeetingAcceptance(meeting, companyOrganizer, companyParticipant);
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
            return ResponseEntity.ok(meetingParticipant);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Server error!");
		}
    }
    
    @PutMapping("/meeting-participants/reject/meeting-company")
    public ResponseEntity<?> rejectMeetingForCompany(
    		@RequestParam Long meetingId, 
    		@RequestParam Long companyId,
    		@RequestParam String comment
    		) {
        log.debug("REST request to reject Meeting {} for Company {}", meetingId, companyId);
        
        Optional<Boolean> isRejectedOptional = meetingParticipantService.checkMeetingRejection(meetingId, companyId);
        
        if (isRejectedOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No MeetingParticipant found for the given meetingId, companyId, and rejection string");
        }
        
        Boolean isRejected = isRejectedOptional.get();
        
        if (isRejected) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Meeting has already been rejected by the company");
        }
        
        try {
            MeetingParticipant meetingParticipant = meetingParticipantService.rejectMeetingForCompany(meetingId, companyId);
            
            Meeting meeting = meetingService.getOne(meetingId);
            Company companyOrganizer = meetingParticipantService.findCompanyByMeetingAndIsOrganizer(meetingId, true);
            Company companyParticipant = companyService.getOneById(companyId);

            // send messages to organiser company
        	Thread thread = threadService.createThreadForMeetingRejection(meeting, companyOrganizer);
        	Message message = messageService.createFirstMessageInThreadRejectionMeeting(thread, meeting, companyParticipant, comment);
        
            // send email notifications participant companies
        	NotificationMailDTO mailDTO = mailService.createNotificationMailDTOForMeetingRejection(meeting, companyOrganizer, companyParticipant, comment);
        	if (!mailDTO.getEmails().isEmpty()) {
        		mailService.sendNotificationMail(mailDTO);
        	}
        	
            return ResponseEntity.ok(meetingParticipant);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Server error!");
		}
    }
    
    @GetMapping("/meeting-participants/check-no-response/meeting-company/{meetingId}/{companyId}")
    public ResponseEntity<?> checkNoResponseForMeetingAndCompany(@PathVariable Long meetingId, @PathVariable Long companyId) {
        log.debug("REST request to check response for Meeting {} for Company {}", meetingId, companyId);
        
        Optional<Boolean> noResponseOptional = meetingParticipantService.checkMeetingNoResponse(meetingId, companyId);
        
        if (noResponseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No MeetingParticipant found for the given meetingId, companyId, and no response string");
        }
        
        Boolean noResponse = noResponseOptional.get();

        return ResponseEntity.ok(noResponse);
    }
    
    @PutMapping("/meeting-participants/remove/meeting-company/{meetingId}/{companyId}")
    public MeetingParticipant removeMeetingForCompany(@PathVariable Long meetingId, @PathVariable Long companyId) {
        log.debug("REST request to remove Meeting {} for Company {}", meetingId, companyId);
        
        MeetingParticipant meetingParticipant = meetingParticipantService.removeMeetingForCompany(meetingId, companyId);
        
        return meetingParticipant;
    }
}
