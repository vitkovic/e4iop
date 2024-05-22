package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A MeetingParticipant.
 */
@Entity
@Table(name = "meeting_participant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "has_accepted")
    private Boolean hasAccepted;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingParticipants", allowSetters = true)
    private Meeting meeting;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingParticipants", allowSetters = true)
    private PortalUser portalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasAccepted() {
        return hasAccepted;
    }

    public MeetingParticipant hasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
        return this;
    }

    public void setHasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public MeetingParticipant meeting(Meeting meeting) {
        this.meeting = meeting;
        return this;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    public MeetingParticipant portalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
        return this;
    }

    public void setPortalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingParticipant)) {
            return false;
        }
        return id != null && id.equals(((MeetingParticipant) o).id);
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
            ", hasAccepted='" + isHasAccepted() + "'" +
            "}";
    }
}
