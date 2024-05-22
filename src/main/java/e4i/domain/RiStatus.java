package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RiStatus.
 */
@Entity
@Table(name = "ri_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * available, temporary unavailable, unavailable
     */
    @ApiModelProperty(value = "available, temporary unavailable, unavailable")
    @Column(name = "status")
    private String status;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "status_en")
    private String statusEn;

    @Column(name = "status_cyr")
    private String statusCyr;

    @Column(name = "meaning_en")
    private String meaningEn;

    @Column(name = "meaning_cyr")
    private String meaningCyr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public RiStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMeaning() {
        return meaning;
    }

    public RiStatus meaning(String meaning) {
        this.meaning = meaning;
        return this;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public RiStatus statusEn(String statusEn) {
        this.statusEn = statusEn;
        return this;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusCyr() {
        return statusCyr;
    }

    public RiStatus statusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
        return this;
    }

    public void setStatusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
    }

    public String getMeaningEn() {
        return meaningEn;
    }

    public RiStatus meaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
        return this;
    }

    public void setMeaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
    }

    public String getMeaningCyr() {
        return meaningCyr;
    }

    public RiStatus meaningCyr(String meaningCyr) {
        this.meaningCyr = meaningCyr;
        return this;
    }

    public void setMeaningCyr(String meaningCyr) {
        this.meaningCyr = meaningCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiStatus)) {
            return false;
        }
        return id != null && id.equals(((RiStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", meaning='" + getMeaning() + "'" +
            ", statusEn='" + getStatusEn() + "'" +
            ", statusCyr='" + getStatusCyr() + "'" +
            ", meaningEn='" + getMeaningEn() + "'" +
            ", meaningCyr='" + getMeaningCyr() + "'" +
            "}";
    }
}
