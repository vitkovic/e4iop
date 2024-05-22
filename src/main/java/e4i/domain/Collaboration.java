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
 * A Collaboration.
 */
@Entity
@Table(name = "collaboration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Collaboration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Instant datetime;
    
    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(name = "comment_offer")
    private String commentOffer;

    @Column(name = "comment_request")
    private String commentRequest;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborationOffers", allowSetters = true)
    private Company companyOffer;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborationRequests", allowSetters = true)
    private Company companyRequest;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborations", allowSetters = true)
    private Advertisement advertisement;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborationOffers", allowSetters = true)
    private CollaborationRating ratingOffer;

    @ManyToOne
    @JsonIgnoreProperties(value = "collaborationRequests", allowSetters = true)
    private CollaborationRating ratingRequest;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "collaborations", allowSetters = true)
    private CollaborationStatus status;
    
    @ManyToMany(mappedBy = "collaborations")
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

    public Instant getDatetime() {
        return datetime;
    }

    public Collaboration datetime(Instant datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getCommentOffer() {
        return commentOffer;
    }

    public Collaboration commentOffer(String commentOffer) {
        this.commentOffer = commentOffer;
        return this;
    }

    public void setCommentOffer(String commentOffer) {
        this.commentOffer = commentOffer;
    }

    public String getCommentRequest() {
        return commentRequest;
    }

    public Collaboration commentRequest(String commentRequest) {
        this.commentRequest = commentRequest;
        return this;
    }

    public void setCommentRequest(String commentRequest) {
        this.commentRequest = commentRequest;
    }

    public Company getCompanyOffer() {
        return companyOffer;
    }

    public Collaboration companyOffer(Company company) {
        this.companyOffer = company;
        return this;
    }

    public void setCompanyOffer(Company company) {
        this.companyOffer = company;
    }

    public Company getCompanyRequest() {
        return companyRequest;
    }

    public Collaboration companyRequest(Company company) {
        this.companyRequest = company;
        return this;
    }

    public void setCompanyRequest(Company company) {
        this.companyRequest = company;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public Collaboration advertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public CollaborationRating getRatingOffer() {
        return ratingOffer;
    }

    public Collaboration ratingOffer(CollaborationRating collaborationRating) {
        this.ratingOffer = collaborationRating;
        return this;
    }

    public void setRatingOffer(CollaborationRating collaborationRating) {
        this.ratingOffer = collaborationRating;
    }

    public CollaborationRating getRatingRequest() {
        return ratingRequest;
    }

    public Collaboration ratingRequest(CollaborationRating collaborationRating) {
        this.ratingRequest = collaborationRating;
        return this;
    }

    public void setRatingRequest(CollaborationRating collaborationRating) {
        this.ratingRequest = collaborationRating;
    }
    
    public CollaborationStatus getStatus() {
        return status;
    }

    public Collaboration status(CollaborationStatus collaborationStatus) {
        this.status = collaborationStatus;
        return this;
    }

    public void setStatus(CollaborationStatus collaborationStatus) {
        this.status = collaborationStatus;
    }
    
    public Set<Thread> getThreads() {
        return threads;
    }

    public Collaboration threads(Set<Thread> threads) {
        this.threads = threads;
        return this;
    }

    public Collaboration addThread(Thread thread) {
        this.threads.add(thread);
        thread.getCollaborations().add(this);
        return this;
    }

    public Collaboration removeThread(Thread thread) {
        this.threads.remove(thread);
        thread.getCollaborations().remove(this);
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
        if (!(o instanceof Collaboration)) {
            return false;
        }
        return id != null && id.equals(((Collaboration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaboration{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", isAccepted='" + getIsAccepted() + "'" +
            ", commentOffer='" + getCommentOffer() + "'" +
            ", commentRequest='" + getCommentRequest() + "'" +
            "}";
    }
}
