package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.io.Serializable;

/**
 * A MeetingParticipant.
 */
@Entity
@Table(name = "meeting_participant_non_b2b")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingParticipantNonB2B implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = {"meetingParticipants", "meetingParticipantsNonB2B"}, allowSetters = true)
    private Meeting meeting;

    @Email
    @Size(min = 5, max = 254)
    @Column(name = "email")
    private String email;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
    public Meeting getMeeting() {
        return meeting;
    }

    public MeetingParticipantNonB2B meeting(Meeting meeting) {
        this.meeting = meeting;
        return this;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingParticipantNonB2B)) {
            return false;
        }
        return id != null && id.equals(((MeetingParticipantNonB2B) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeetingParticipant{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
