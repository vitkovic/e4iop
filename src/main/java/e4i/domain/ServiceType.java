package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceType.
 */
@Entity
@Table(name = "service_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "type_en")
    private String typeEn;

    @Column(name = "type_cyr")
    private String typeCyr;

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

    public ServiceType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeEn() {
        return typeEn;
    }

    public ServiceType typeEn(String typeEn) {
        this.typeEn = typeEn;
        return this;
    }

    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    public String getTypeCyr() {
        return typeCyr;
    }

    public ServiceType typeCyr(String typeCyr) {
        this.typeCyr = typeCyr;
        return this;
    }

    public void setTypeCyr(String typeCyr) {
        this.typeCyr = typeCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceType)) {
            return false;
        }
        return id != null && id.equals(((ServiceType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", typeEn='" + getTypeEn() + "'" +
            ", typeCyr='" + getTypeCyr() + "'" +
            "}";
    }
}
