package e4i.domain;

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
 * A CmsPage.
 */
@Entity
@Table(name = "cms_page")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsPage implements Serializable {

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

    @Column(name = "content")
    private String content;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "cms_page_document",
               joinColumns = @JoinColumn(name = "cms_page_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsPages", allowSetters = true)
    private CmsPageType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsPageCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsPageModifieds", allowSetters = true)
    private PortalUser modifiedBy;

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

    public CmsPage createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public CmsPage modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getTitle() {
        return title;
    }

    public CmsPage title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public CmsPage content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public CmsPage documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public CmsPage addDocument(Document document) {
        this.documents.add(document);
        document.getCmsPages().add(this);
        return this;
    }

    public CmsPage removeDocument(Document document) {
        this.documents.remove(document);
        document.getCmsPages().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public CmsPageType getType() {
        return type;
    }

    public CmsPage type(CmsPageType cmsPageType) {
        this.type = cmsPageType;
        return this;
    }

    public void setType(CmsPageType cmsPageType) {
        this.type = cmsPageType;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public CmsPage createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public CmsPage modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CmsPage)) {
            return false;
        }
        return id != null && id.equals(((CmsPage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CmsPage{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
