package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CmsQuestion.
 */
@Entity
@Table(name = "cms_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsQuestion implements Serializable {

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
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsQuestionCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "cmsQuestionModifieds", allowSetters = true)
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

    public CmsQuestion createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public CmsQuestion modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getQuestion() {
        return question;
    }

    public CmsQuestion question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public CmsQuestion answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public CmsQuestion createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public CmsQuestion modifiedBy(PortalUser portalUser) {
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
        if (!(o instanceof CmsQuestion)) {
            return false;
        }
        return id != null && id.equals(((CmsQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CmsQuestion{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
