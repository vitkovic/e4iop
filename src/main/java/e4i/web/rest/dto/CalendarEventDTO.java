package e4i.web.rest.dto;

import e4i.domain.Meeting;
import e4i.domain.MeetingParticipant;

public class CalendarEventDTO {
	
	private Meeting meeting;
	private MeetingParticipant meetingParticipant;
	private String color;
	private String textColor;
	
	public CalendarEventDTO() {	
	}
	
	public CalendarEventDTO(Meeting meeting, MeetingParticipant meetingParticipant, String color, String textColor) {
		super();
		this.meeting = meeting;
		this.meetingParticipant = meetingParticipant;
		this.color = color;
		this.textColor = textColor;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public MeetingParticipant getMeetingParticipant() {
		return meetingParticipant;
	}

	public void setMeetingParticipant(MeetingParticipant meetingParticipant) {
		this.meetingParticipant = meetingParticipant;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
}
