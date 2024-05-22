package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceAccreditation.
 */
@Entity
@Table(name = "service_accreditation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceAccreditation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * accredited, non-accredited
     */
    @ApiModelProperty(value = "accredited, non-accredited")
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

    public ServiceAccreditation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMeaning() {
        return meaning;
    }

    public ServiceAccreditation meaning(String meaning) {
        this.meaning = meaning;
        return this;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public ServiceAccreditation statusEn(String statusEn) {
        this.statusEn = statusEn;
        return this;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusCyr() {
        return statusCyr;
    }

    public ServiceAccreditation statusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
        return this;
    }

    public void setStatusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
    }

    public String getMeaningEn() {
        return meaningEn;
    }

    public ServiceAccreditation meaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
        return this;
    }

    public void setMeaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
    }

    public String getMeaningCyr() {
        return meaningCyr;
    }

    public ServiceAccreditation meaningCyr(String meaningCyr) {
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
        if (!(o instanceof ServiceAccreditation)) {
            return false;
        }
        return id != null && id.equals(((ServiceAccreditation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceAccreditation{" +
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
