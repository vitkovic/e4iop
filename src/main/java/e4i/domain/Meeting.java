package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Meeting.
 */
@Entity
@Table(name = "meeting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @Column(name = "is_acepted")
    private Boolean isAcepted;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "comment")
    private String comment;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "meeting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "meetingOrganizers", allowSetters = true)
    private PortalUser portalUserOrganizer;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetings", allowSetters = true)
    private Advertisement advertisement;

    @ManyToOne
    @JsonIgnoreProperties(value = "meetings", allowSetters = true)
    private MeetingType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public Meeting datetime(Instant datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Boolean isIsAcepted() {
        return isAcepted;
    }

    public Meeting isAcepted(Boolean isAcepted) {
        this.isAcepted = isAcepted;
        return this;
    }

    public void setIsAcepted(Boolean isAcepted) {
        this.isAcepted = isAcepted;
    }

    public String getTitle() {
        return title;
    }

    public Meeting title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Meeting description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public Meeting comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNotes() {
        return notes;
    }

    public Meeting notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<MeetingParticipant> getMeetingParticipants() {
        return meetingParticipants;
    }

    public Meeting meetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
        return this;
    }

    public Meeting addMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.add(meetingParticipant);
        meetingParticipant.setMeeting(this);
        return this;
    }

    public Meeting removeMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.remove(meetingParticipant);
        meetingParticipant.setMeeting(null);
        return this;
    }

    public void setMeetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }

    public PortalUser getPortalUserOrganizer() {
        return portalUserOrganizer;
    }

    public Meeting portalUserOrganizer(PortalUser portalUser) {
        this.portalUserOrganizer = portalUser;
        return this;
    }

    public void setPortalUserOrganizer(PortalUser portalUser) {
        this.portalUserOrganizer = portalUser;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Meeting advertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public MeetingType getType() {
        return type;
    }

    public Meeting type(MeetingType meetingType) {
        this.type = meetingType;
        return this;
    }

    public void setType(MeetingType meetingType) {
        this.type = meetingType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        return id != null && id.equals(((Meeting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meeting{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", isAcepted='" + isIsAcepted() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", comment='" + getComment() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
