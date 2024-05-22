package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdvertisementCategory.
 */
@Entity
@Table(name = "advertisement_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementCategory implements Serializable {

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

    @OneToMany(mappedBy = "advertisementCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvertisementSubcategory> advertisementSubcategories = new HashSet<>();

    @OneToMany(mappedBy = "advertisementCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsAdvertisementCategory> cmsAdvertisementCategories = new HashSet<>();

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

    public AdvertisementCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public AdvertisementCategory isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public AdvertisementCategory isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<AdvertisementSubcategory> getAdvertisementSubcategories() {
        return advertisementSubcategories;
    }

    public AdvertisementCategory advertisementSubcategories(Set<AdvertisementSubcategory> advertisementSubcategories) {
        this.advertisementSubcategories = advertisementSubcategories;
        return this;
    }

    public AdvertisementCategory addAdvertisementSubcategory(AdvertisementSubcategory advertisementSubcategory) {
        this.advertisementSubcategories.add(advertisementSubcategory);
        advertisementSubcategory.setAdvertisementCategory(this);
        return this;
    }

    public AdvertisementCategory removeAdvertisementSubcategory(AdvertisementSubcategory advertisementSubcategory) {
        this.advertisementSubcategories.remove(advertisementSubcategory);
        advertisementSubcategory.setAdvertisementCategory(null);
        return this;
    }

    public void setAdvertisementSubcategories(Set<AdvertisementSubcategory> advertisementSubcategories) {
        this.advertisementSubcategories = advertisementSubcategories;
    }

    public Set<CmsAdvertisementCategory> getCmsAdvertisementCategories() {
        return cmsAdvertisementCategories;
    }

    public AdvertisementCategory cmsAdvertisementCategories(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategories = cmsAdvertisementCategories;
        return this;
    }

    public AdvertisementCategory addCmsAdvertisementCategory(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategories.add(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setAdvertisementCategory(this);
        return this;
    }

    public AdvertisementCategory removeCmsAdvertisementCategory(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategories.remove(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setAdvertisementCategory(null);
        return this;
    }

    public void setCmsAdvertisementCategories(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategories = cmsAdvertisementCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementCategory)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
