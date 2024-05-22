package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CollaborationRating.
 */
@Entity
@Table(name = "collaboration_rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollaborationRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Long number;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "ratingOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationOffers = new HashSet<>();

    @OneToMany(mappedBy = "ratingRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public CollaborationRating number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public CollaborationRating description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public CollaborationRating isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public CollaborationRating isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Collaboration> getCollaborationOffers() {
        return collaborationOffers;
    }

    public CollaborationRating collaborationOffers(Set<Collaboration> collaborations) {
        this.collaborationOffers = collaborations;
        return this;
    }

    public CollaborationRating addCollaborationOffer(Collaboration collaboration) {
        this.collaborationOffers.add(collaboration);
        collaboration.setRatingOffer(this);
        return this;
    }

    public CollaborationRating removeCollaborationOffer(Collaboration collaboration) {
        this.collaborationOffers.remove(collaboration);
        collaboration.setRatingOffer(null);
        return this;
    }

    public void setCollaborationOffers(Set<Collaboration> collaborations) {
        this.collaborationOffers = collaborations;
    }

    public Set<Collaboration> getCollaborationRequests() {
        return collaborationRequests;
    }

    public CollaborationRating collaborationRequests(Set<Collaboration> collaborations) {
        this.collaborationRequests = collaborations;
        return this;
    }

    public CollaborationRating addCollaborationRequest(Collaboration collaboration) {
        this.collaborationRequests.add(collaboration);
        collaboration.setRatingRequest(this);
        return this;
    }

    public CollaborationRating removeCollaborationRequest(Collaboration collaboration) {
        this.collaborationRequests.remove(collaboration);
        collaboration.setRatingRequest(null);
        return this;
    }

    public void setCollaborationRequests(Set<Collaboration> collaborations) {
        this.collaborationRequests = collaborations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollaborationRating)) {
            return false;
        }
        return id != null && id.equals(((CollaborationRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollaborationRating{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", description='" + getDescription() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
