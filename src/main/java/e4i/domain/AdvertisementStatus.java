package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdvertisementStatus.
 */
@Entity
@Table(name = "advertisement_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ACTIVE = "Активан";
    public static final String INACTIVE = "Неактиван";
    public static final String ARCHIVED = "Архивиран";

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
    private Set<Advertisement> advertisements = new HashSet<>();

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

    public AdvertisementStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public AdvertisementStatus isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public AdvertisementStatus isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public AdvertisementStatus advertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public AdvertisementStatus addAdvertisement(Advertisement advertisement) {
        this.advertisements.add(advertisement);
        advertisement.setStatus(this);
        return this;
    }

    public AdvertisementStatus removeAdvertisement(Advertisement advertisement) {
        this.advertisements.remove(advertisement);
        advertisement.setStatus(null);
        return this;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementStatus)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
