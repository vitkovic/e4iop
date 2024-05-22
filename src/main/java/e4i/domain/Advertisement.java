package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Advertisement.
 */
@Entity
@Table(name = "advertisement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @NotNull
    @Column(name = "activation_datetime", nullable = false)
    private Instant activationDatetime;
    
    @Column(name = "expiration_datetime")
    private Instant expirationDatetime;
    
    @Column(name = "deletion_datetime")
    private Instant deletionDatetime;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "budget")
    private Long budget;

    @Column(name = "conditions")
    private String conditions;

    @OneToMany(mappedBy = "advertisement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvertisementSupporter> advertisementSupporters = new HashSet<>();

    @OneToMany(mappedBy = "advertisement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborations = new HashSet<>();

    @OneToMany(mappedBy = "advertisement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Meeting> meetings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "advertisement_document",
               joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementModifieds", allowSetters = true)
    private PortalUser modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private AdvertisementStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private AdvertisementDuration duration;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private AdvertisementType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private AdvertisementKind kind;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisements", allowSetters = true)
    private AdvertisementSubsubcategory subsubcategory;

    @ManyToMany(mappedBy = "advertisements")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Thread> threads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Advertisement createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public Advertisement modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Instant getActivationDatetime() {
        return activationDatetime;
    }

    public Advertisement activationDatetime(Instant activationDatetime) {
        this.activationDatetime = activationDatetime;
        return this;
    }

    public void setActivationDatetime(Instant activationDatetime) {
        this.activationDatetime = activationDatetime;
    }

    public Instant getExpirationDatetime() {
		return expirationDatetime;
	}

    public Advertisement expirationDatetime(Instant expirationDatetime) {
        this.expirationDatetime = expirationDatetime;
        return this;
    }

	public void setExpirationDatetime(Instant expirationDatetime) {
		this.expirationDatetime = expirationDatetime;
	}

	public Instant getDeletionDatetime() {
		return deletionDatetime;
	}
	
    public Advertisement deletionDatetime(Instant deletionDatetime) {
        this.deletionDatetime = deletionDatetime;
        return this;
    }

	public void setDeletionDatetime(Instant deletionDatetime) {
		this.deletionDatetime = deletionDatetime;
	}

	public String getTitle() {
        return title;
    }

    public Advertisement title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Advertisement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBudget() {
        return budget;
    }

    public Advertisement budget(Long budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getConditions() {
        return conditions;
    }

    public Advertisement conditions(String conditions) {
        this.conditions = conditions;
        return this;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Set<AdvertisementSupporter> getAdvertisementSupporters() {
        return advertisementSupporters;
    }

    public Advertisement advertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
        return this;
    }

    public Advertisement addAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.add(advertisementSupporter);
        advertisementSupporter.setAdvertisement(this);
        return this;
    }

    public Advertisement removeAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.remove(advertisementSupporter);
        advertisementSupporter.setAdvertisement(null);
        return this;
    }

    public void setAdvertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
    }

    public Set<Collaboration> getCollaborations() {
        return collaborations;
    }

    public Advertisement collaborations(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
        return this;
    }

    public Advertisement addCollaboration(Collaboration collaboration) {
        this.collaborations.add(collaboration);
        collaboration.setAdvertisement(this);
        return this;
    }

    public Advertisement removeCollaboration(Collaboration collaboration) {
        this.collaborations.remove(collaboration);
        collaboration.setAdvertisement(null);
        return this;
    }

    public void setCollaborations(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public Advertisement meetings(Set<Meeting> meetings) {
        this.meetings = meetings;
        return this;
    }

    public Advertisement addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
        meeting.setAdvertisement(this);
        return this;
    }

    public Advertisement removeMeeting(Meeting meeting) {
        this.meetings.remove(meeting);
        meeting.setAdvertisement(null);
        return this;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Advertisement documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Advertisement addDocument(Document document) {
        this.documents.add(document);
        document.getAdvertisements().add(this);
        return this;
    }

    public Advertisement removeDocument(Document document) {
        this.documents.remove(document);
        document.getAdvertisements().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public Advertisement createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public Advertisement modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }

    public Company getCompany() {
        return company;
    }

    public Advertisement company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AdvertisementStatus getStatus() {
        return status;
    }

    public Advertisement status(AdvertisementStatus advertisementStatus) {
        this.status = advertisementStatus;
        return this;
    }

    public void setStatus(AdvertisementStatus advertisementStatus) {
        this.status = advertisementStatus;
    }

    public AdvertisementDuration getDuration() {
        return duration;
    }

    public Advertisement duration(AdvertisementDuration advertisementDuration) {
        this.duration = advertisementDuration;
        return this;
    }

    public void setDuration(AdvertisementDuration advertisementDuration) {
        this.duration = advertisementDuration;
    }

    public AdvertisementType getType() {
        return type;
    }

    public Advertisement type(AdvertisementType advertisementType) {
        this.type = advertisementType;
        return this;
    }

    public void setType(AdvertisementType advertisementType) {
        this.type = advertisementType;
    }

    public AdvertisementKind getKind() {
        return kind;
    }

    public Advertisement kind(AdvertisementKind advertisementKind) {
        this.kind = advertisementKind;
        return this;
    }

    public void setKind(AdvertisementKind advertisementKind) {
        this.kind = advertisementKind;
    }

    public AdvertisementSubsubcategory getSubsubcategory() {
        return subsubcategory;
    }

    public Advertisement subsubcategory(AdvertisementSubsubcategory advertisementSubsubcategory) {
        this.subsubcategory = advertisementSubsubcategory;
        return this;
    }

    public void setSubsubcategory(AdvertisementSubsubcategory advertisementSubsubcategory) {
        this.subsubcategory = advertisementSubsubcategory;
    }

    public Set<Thread> getThreads() {
        return threads;
    }

    public Advertisement threads(Set<Thread> threads) {
        this.threads = threads;
        return this;
    }

    public Advertisement addThread(Thread thread) {
        this.threads.add(thread);
        thread.getAdvertisements().add(this);
        return this;
    }

    public Advertisement removeThread(Thread thread) {
        this.threads.remove(thread);
        thread.getAdvertisements().remove(this);
        return this;
    }

    public void setThreads(Set<Thread> threads) {
        this.threads = threads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Advertisement)) {
            return false;
        }
        return id != null && id.equals(((Advertisement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Advertisement{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", activationDatetime='" + getActivationDatetime() + "'" +
            ", expirationDatetime='" + getExpirationDatetime() + "'" +
            ", deletionDatetime='" + getDeletionDatetime() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", budget=" + getBudget() +
            ", conditions='" + getConditions() + "'" +
            "}";
    }
}
