package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CmsAdvertisementCategory.
 */
@Entity
@Table(name = "cms_advertisement_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsAdvertisementCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsAdvertisementCategoryCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsAdvertisementCategoryModifieds", allowSetters = true)
    private PortalUser modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsAdvertisementCategories", allowSetters = true)
    private AdvertisementCategory advertisementCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsAdvertisementCategories", allowSetters = true)
    private Document image;

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

    public CmsAdvertisementCategory createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public CmsAdvertisementCategory modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public CmsAdvertisementCategory createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public CmsAdvertisementCategory modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }

    public AdvertisementCategory getAdvertisementCategory() {
        return advertisementCategory;
    }

    public CmsAdvertisementCategory advertisementCategory(AdvertisementCategory advertisementCategory) {
        this.advertisementCategory = advertisementCategory;
        return this;
    }

    public void setAdvertisementCategory(AdvertisementCategory advertisementCategory) {
        this.advertisementCategory = advertisementCategory;
    }

    public Document getImage() {
        return image;
    }

    public CmsAdvertisementCategory image(Document document) {
        this.image = document;
        return this;
    }

    public void setImage(Document document) {
        this.image = document;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CmsAdvertisementCategory)) {
            return false;
        }
        return id != null && id.equals(((CmsAdvertisementCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CmsAdvertisementCategory{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            "}";
    }
}
