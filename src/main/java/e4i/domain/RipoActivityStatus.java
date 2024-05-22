package e4i.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Research Infrastructure Activity Status
 */
@ApiModel(description = "Research Infrastructure Activity Status")
@Entity
@Table(name = "ripo_activity_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RipoActivityStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Active, Temporary non-active, Closed
     */
    @ApiModelProperty(value = "Active, Temporary non-active, Closed")
    @Column(name = "status")
    private String status;
    
    @Column(name = "status_en")
    private String statusEn;
    
    @Column(name = "status_cyr")
    private String statusCyr;

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

    public RipoActivityStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getStatusEn() {
		return statusEn;
	}

	public void setStatusEn(String statusEn) {
		this.statusEn = statusEn;
	}

	public String getStatusCyr() {
		return statusCyr;
	}

	public void setStatusCyr(String statusCyr) {
		this.statusCyr = statusCyr;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RipoActivityStatus)) {
            return false;
        }
        return id != null && id.equals(((RipoActivityStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RipoActivityStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
