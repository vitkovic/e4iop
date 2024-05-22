package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdvertisementSubsubcategory.
 */
@Entity
@Table(name = "advertisement_subsubcategory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementSubsubcategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "subsubcategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Advertisement> advertisements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementSubsubcategories", allowSetters = true)
    private AdvertisementSubcategory advertisementSubcategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AdvertisementSubsubcategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public AdvertisementSubsubcategory isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public AdvertisementSubsubcategory isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public AdvertisementSubsubcategory advertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public AdvertisementSubsubcategory addAdvertisement(Advertisement advertisement) {
        this.advertisements.add(advertisement);
        advertisement.setSubsubcategory(this);
        return this;
    }

    public AdvertisementSubsubcategory removeAdvertisement(Advertisement advertisement) {
        this.advertisements.remove(advertisement);
        advertisement.setSubsubcategory(null);
        return this;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public AdvertisementSubcategory getAdvertisementSubcategory() {
        return advertisementSubcategory;
    }

    public AdvertisementSubsubcategory advertisementSubcategory(AdvertisementSubcategory advertisementSubcategory) {
        this.advertisementSubcategory = advertisementSubcategory;
        return this;
    }

    public void setAdvertisementSubcategory(AdvertisementSubcategory advertisementSubcategory) {
        this.advertisementSubcategory = advertisementSubcategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementSubsubcategory)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementSubsubcategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementSubsubcategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
