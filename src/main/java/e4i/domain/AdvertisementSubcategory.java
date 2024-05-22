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
 * A AdvertisementSubcategory.
 */
@Entity
@Table(name = "advertisement_subcategory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementSubcategory implements Serializable {

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

    @OneToMany(mappedBy = "advertisementSubcategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvertisementSubsubcategory> advertisementSubsubcategories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementSubcategories", allowSetters = true)
    private AdvertisementCategory advertisementCategory;

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

    public AdvertisementSubcategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public AdvertisementSubcategory isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public AdvertisementSubcategory isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<AdvertisementSubsubcategory> getAdvertisementSubsubcategories() {
        return advertisementSubsubcategories;
    }

    public AdvertisementSubcategory advertisementSubsubcategories(Set<AdvertisementSubsubcategory> advertisementSubsubcategories) {
        this.advertisementSubsubcategories = advertisementSubsubcategories;
        return this;
    }

    public AdvertisementSubcategory addAdvertisementSubsubcategory(AdvertisementSubsubcategory advertisementSubsubcategory) {
        this.advertisementSubsubcategories.add(advertisementSubsubcategory);
        advertisementSubsubcategory.setAdvertisementSubcategory(this);
        return this;
    }

    public AdvertisementSubcategory removeAdvertisementSubsubcategory(AdvertisementSubsubcategory advertisementSubsubcategory) {
        this.advertisementSubsubcategories.remove(advertisementSubsubcategory);
        advertisementSubsubcategory.setAdvertisementSubcategory(null);
        return this;
    }

    public void setAdvertisementSubsubcategories(Set<AdvertisementSubsubcategory> advertisementSubsubcategories) {
        this.advertisementSubsubcategories = advertisementSubsubcategories;
    }

    public AdvertisementCategory getAdvertisementCategory() {
        return advertisementCategory;
    }

    public AdvertisementSubcategory advertisementCategory(AdvertisementCategory advertisementCategory) {
        this.advertisementCategory = advertisementCategory;
        return this;
    }

    public void setAdvertisementCategory(AdvertisementCategory advertisementCategory) {
        this.advertisementCategory = advertisementCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementSubcategory)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementSubcategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementSubcategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
