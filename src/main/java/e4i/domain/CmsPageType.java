package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CmsPageType.
 */
@Entity
@Table(name = "cms_page_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsPageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsPage> cmsPages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public CmsPageType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public CmsPageType isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public CmsPageType isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<CmsPage> getCmsPages() {
        return cmsPages;
    }

    public CmsPageType cmsPages(Set<CmsPage> cmsPages) {
        this.cmsPages = cmsPages;
        return this;
    }

    public CmsPageType addCmsPage(CmsPage cmsPage) {
        this.cmsPages.add(cmsPage);
        cmsPage.setType(this);
        return this;
    }

    public CmsPageType removeCmsPage(CmsPage cmsPage) {
        this.cmsPages.remove(cmsPage);
        cmsPage.setType(null);
        return this;
    }

    public void setCmsPages(Set<CmsPage> cmsPages) {
        this.cmsPages = cmsPages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CmsPageType)) {
            return false;
        }
        return id != null && id.equals(((CmsPageType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CmsPageType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
