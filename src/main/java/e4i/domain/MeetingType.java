package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MeetingType.
 */
@Entity
@Table(name = "meeting_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MeetingType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Meeting> meetings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public MeetingType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public MeetingType isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public MeetingType isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public MeetingType meetings(Set<Meeting> meetings) {
        this.meetings = meetings;
        return this;
    }

    public MeetingType addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
        meeting.setType(this);
        return this;
    }

    public MeetingType removeMeeting(Meeting meeting) {
        this.meetings.remove(meeting);
        meeting.setType(null);
        return this;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeetingType)) {
            return false;
        }
        return id != null && id.equals(((MeetingType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeetingType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
