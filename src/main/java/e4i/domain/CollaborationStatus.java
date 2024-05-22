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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "collaboration_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollaborationStatus implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborations = new HashSet<>();
    

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public CollaborationStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public CollaborationStatus isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public CollaborationStatus isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Collaboration> getCollaborations() {
        return collaborations;
    }

    public CollaborationStatus collaborations(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
        return this;
    }

    public CollaborationStatus addAdvertisement(Collaboration collaboration) {
        this.collaborations.add(collaboration);
        collaboration.setStatus(this);
        return this;
    }

    public CollaborationStatus removeAdvertisement(Collaboration collaboration) {
        this.collaborations.remove(collaboration);
        collaboration.setStatus(null);
        return this;
    }

    public void setAdvertisements(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollaborationStatus)) {
            return false;
        }
        return id != null && id.equals(((CollaborationStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollaborationStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
