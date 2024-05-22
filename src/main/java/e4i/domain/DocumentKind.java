package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DocumentKind.
 */
@Entity
@Table(name = "document_kind")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentKind implements Serializable {

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

    @OneToMany(mappedBy = "kind")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Document> documents = new HashSet<>();

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

    public DocumentKind type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsVisible() {
        return isVisible;
    }

    public DocumentKind isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean isIsDeleted() {
        return isDeleted;
    }

    public DocumentKind isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public DocumentKind documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public DocumentKind addDocument(Document document) {
        this.documents.add(document);
        document.setKind(this);
        return this;
    }

    public DocumentKind removeDocument(Document document) {
        this.documents.remove(document);
        document.setKind(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentKind)) {
            return false;
        }
        return id != null && id.equals(((DocumentKind) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentKind{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", isVisible='" + isIsVisible() + "'" +
            ", isDeleted='" + isIsDeleted() + "'" +
            "}";
    }
}
