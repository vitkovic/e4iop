package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PuoLegalStatus.
 */
@Entity
@Table(name = "puo_legal_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PuoLegalStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_cyr")
    private String descriptionCyr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public PuoLegalStatus value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public PuoLegalStatus description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public PuoLegalStatus descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionCyr() {
        return descriptionCyr;
    }

    public PuoLegalStatus descriptionCyr(String descriptionCyr) {
        this.descriptionCyr = descriptionCyr;
        return this;
    }

    public void setDescriptionCyr(String descriptionCyr) {
        this.descriptionCyr = descriptionCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PuoLegalStatus)) {
            return false;
        }
        return id != null && id.equals(((PuoLegalStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PuoLegalStatus{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", descriptionCyr='" + getDescriptionCyr() + "'" +
            "}";
    }
}
