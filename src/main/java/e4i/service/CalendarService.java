package e4i.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.Meeting;
import e4i.domain.MeetingParticipant;
import e4i.domain.MeetingParticipantNonB2B;
import e4i.domain.MeetingParticipantStatus;
import e4i.repository.MeetingParticipantRepository;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;


@Service
@Transactional
public class CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarService.class);
    
    
    @Autowired
    MeetingParticipantService meetingParticipantService;
    
    @Autowired
    MeetingParticipantNonB2BService meetingParticipantNonB2BService;
    
    @Autowired
    MeetingParticipantRepository meetingParticipantRepository;
    
    @Transactional
	public Map<String, String> getCalendarEventColor(Long meetingId) {
        log.debug("Request to get determine color for calendar event for Meeting {}", meetingId);
        Boolean exists = meetingParticipantRepository.existsAcceptedParticipantOrNoNonOrganizers(meetingId, MeetingParticipantStatus.ACCEPTED);
        
        Map<String, String> colorMap = new HashMap<>();
        if (exists) {
            colorMap.put("color", "rgb(73, 217, 67)"); // greenish
            colorMap.put("textColor", "white");
        } else {
            colorMap.put("color", "rgb(239, 239, 44)"); // yellowish
            colorMap.put("textColor", "black");
        }

        return colorMap;
    }
    
    
	public ByteArrayResource createICS(Meeting meeting) {
        log.debug("Request to export ICS for meeting : {}", meeting.getTitle());
        
    	Calendar calendar = new Calendar();
    	calendar.add(new ProdId("E4I portal"));
    	calendar.add(ImmutableVersion.VERSION_2_0);
    	calendar.add(ImmutableCalScale.GREGORIAN);

    	String eventName = meeting.getTitle();
    	Instant startDate = meeting.getDatetimeStart();
    	Instant endDate = meeting.getDatetimeEnd();
    	String location = meeting.getLocation();
    	String description = "Sastanak zakazan na B2B portalu.";
    	
    	VEvent vEvent = new VEvent(startDate, endDate, eventName);
    	
    	if (location != null && !location.isBlank()) {
        	Location eventLocation = new Location(location);
        	vEvent.add(eventLocation);
    	}
    	
    	if (meeting.getAdvertisement() != null) {
    		description = description + "\n\nOglas: " + meeting.getAdvertisement().getTitle();
    	}
    	
    	String meetingDescription = meeting.getDescription();
    	if (meetingDescription != null && !meetingDescription.isBlank()) {
    		description = description + "\n\nOpis: " + meetingDescription;
    	}
		Description eventDescription = new Description(description);
    	vEvent.add(eventDescription);
    		
    	List<MeetingParticipant> meetingParticipants = meetingParticipantService.findAllByMeetingId(meeting.getId());
    	List<MeetingParticipantNonB2B> meetingParticipantsNonB2B = meetingParticipantNonB2BService.findAllByMeetingId(meeting.getId());
    	
    	for (MeetingParticipant participant : meetingParticipants) {
    		if (participant.isIsOrganizer()) {
    			// Koji mail staviti?
    	    	Organizer organizer = new Organizer(URI.create(""));
    	    	organizer.add(new Cn(participant.getCompany().getName()));
    	    	vEvent.add(organizer);
    		} else {
    			// Koji mail staviti?
    	    	Attendee attendee = new Attendee(URI.create(""));
    	    	
    	    	if (meeting.getAdvertisement() != null && meeting.getAdvertisement().getCompany().getId().equals(participant.getCompany().getId())) {
        	    	attendee.add(Role.REQ_PARTICIPANT);
    	    	} else {
        	    	attendee.add(Role.OPT_PARTICIPANT);    	    		
    	    	}
    	    	
    	    	if (participant.getStatus().getStatusEn().equalsIgnoreCase(MeetingParticipantStatus.ACCEPTED)) {
        	    	attendee.add(PartStat.ACCEPTED);
    	    	} else if (participant.getStatus().getStatusEn().equalsIgnoreCase(MeetingParticipantStatus.REJECTED)) {
        	    	attendee.add(PartStat.DECLINED);
    	    	} else if (participant.getStatus().getStatusEn().equalsIgnoreCase(MeetingParticipantStatus.NO_RESPONSE)){
    	    		attendee.add(PartStat.NEEDS_ACTION);
    	    	}
    	    	
    	    	attendee.add(new Cn(participant.getCompany().getName()));
    	    	vEvent.add(attendee);
    		}
    	}
    	
    	for (MeetingParticipantNonB2B participant : meetingParticipantsNonB2B) {
    		Attendee attendee = new Attendee(URI.create("mailto:" + participant.getEmail()));
    		attendee.add(new Cn(participant.getEmail()));
    		attendee.add(Role.OPT_PARTICIPANT);
    		attendee.add(PartStat.NEEDS_ACTION);
    		
    		vEvent.add(attendee);
    	}
    	
    	calendar.add(vEvent);

        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, baos);
            
            return new ByteArrayResource(baos.toByteArray());
        } catch (IOException e) {
        	throw new RuntimeException("Greška prilikom generisanja ics fajla", e);
        }    
	}
}
