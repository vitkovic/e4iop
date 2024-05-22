package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CmsCarousel.
 */
@Entity
@Table(name = "cms_carousel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsCarousel implements Serializable {

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
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "new_tab")
    private Boolean newTab;

    @Column(name = "ordinal_number")
    private Long ordinalNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsCarouselCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsCarouselModifieds", allowSetters = true)
    private PortalUser modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsCarouses", allowSetters = true)
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

    public CmsCarousel createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public CmsCarousel modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getTitle() {
        return title;
    }

    public CmsCarousel title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public CmsCarousel link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean isNewTab() {
        return newTab;
    }

    public CmsCarousel newTab(Boolean newTab) {
        this.newTab = newTab;
        return this;
    }

    public void setNewTab(Boolean newTab) {
        this.newTab = newTab;
    }

    public Long getOrdinalNumber() {
        return ordinalNumber;
    }

    public CmsCarousel ordinalNumber(Long ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
        return this;
    }

    public void setOrdinalNumber(Long ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public CmsCarousel createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public CmsCarousel modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }

    public Document getImage() {
        return image;
    }

    public CmsCarousel image(Document document) {
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
        if (!(o instanceof CmsCarousel)) {
            return false;
        }
        return id != null && id.equals(((CmsCarousel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CmsCarousel{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", link='" + getLink() + "'" +
            ", newTab='" + isNewTab() + "'" +
            ", ordinalNumber=" + getOrdinalNumber() +
            "}";
    }
}
