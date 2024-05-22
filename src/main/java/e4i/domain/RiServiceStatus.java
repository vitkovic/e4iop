package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RiServiceStatus.
 */
@Entity
@Table(name = "ri_service_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiServiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Performed, In progress, Planed, Cancelled
     */
    @ApiModelProperty(value = "Performed, In progress, Planed, Cancelled")
    @Column(name = "status")
    private String status;

    /**
     * Service has been performed\nService execution is in progress\nService is planed\nThe planned service has been cancelled
     */
    @ApiModelProperty(value = "Service has been performed\nService execution is in progress\nService is planed\nThe planned service has been cancelled")
    @Column(name = "description")
    private String description;

    @Column(name = "status_en")
    private String statusEn;

    @Column(name = "status_cyr")
    private String statusCyr;

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

    public String getStatus() {
        return status;
    }

    public RiServiceStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public RiServiceStatus description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public RiServiceStatus statusEn(String statusEn) {
        this.statusEn = statusEn;
        return this;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusCyr() {
        return statusCyr;
    }

    public RiServiceStatus statusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
        return this;
    }

    public void setStatusCyr(String statusCyr) {
        this.statusCyr = statusCyr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public RiServiceStatus descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionCyr() {
        return descriptionCyr;
    }

    public RiServiceStatus descriptionCyr(String descriptionCyr) {
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
        if (!(o instanceof RiServiceStatus)) {
            return false;
        }
        return id != null && id.equals(((RiServiceStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiServiceStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", statusEn='" + getStatusEn() + "'" +
            ", statusCyr='" + getStatusCyr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", descriptionCyr='" + getDescriptionCyr() + "'" +
            "}";
    }
}
