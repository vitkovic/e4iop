package e4i.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "meeting_participant_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingParticipantStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String ACCEPTED = "Invitation accepted";
    public static final String REJECTED = "Invitation rejected";
    public static final String NO_RESPONSE = "No response";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "status_sr", nullable = false)
    private String statusSr;

    @Column(name = "status_src", nullable = false)
    private String statusSrc;

    @Column(name = "status_en", nullable = false)
    private String statusEn;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusSr() {
        return statusSr;
    }
    
    public MeetingParticipantStatus statusSr(String statusSr) {
    	this.statusSr = statusSr;
    	return this;
    }

    public void setStatusSr(String statusSr) {
        this.statusSr = statusSr;
    }

    public String getStatusSrc() {
        return statusSrc;
    }

    public MeetingParticipantStatus statusSrc(String statusSrc) {
    	this.statusSrc = statusSrc;
    	return this;
    }

    public void setStatusSrc(String statusSrc) {
        this.statusSrc = statusSrc;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public MeetingParticipantStatus statusEn(String statusEn) {
    	this.statusEn = statusEn;
    	return this;
    }
    
    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }
    
    public MeetingParticipantStatus isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }
    
    public MeetingParticipantStatus isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<MeetingParticipant> getMeetingParticipants() {
        return meetingParticipants;
    }

    public MeetingParticipantStatus meetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
        return this;
    }

    public MeetingParticipantStatus addMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.add(meetingParticipant);
        meetingParticipant.setStatus(this);
        return this;
    }

    public MeetingParticipantStatus removeMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.remove(meetingParticipant);
        meetingParticipant.setStatus(null);
        return this;
    }    
    
    public void setMeetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingParticipantStatus)) {
            return false;
        }
        return id != null && id.equals(((MeetingParticipantStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeetingParticipantStatus{" +
            "id=" + getId() +
            ", statusSr='" + getStatusSr() + '\'' +
            ", statusSrc='" + getStatusSrc() + '\'' +
            ", statusEn='" + getStatusEn() + '\'' +
            ", isVisible=" + isIsVisible() +
            ", isDeleted=" + isIsDeleted() +
            '}';
    }
}
