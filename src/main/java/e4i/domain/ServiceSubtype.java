package e4i.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceSubtype.
 */
@Entity
@Table(name = "service_subtype")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceSubtype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "subtype_en")
    private String subtypeEn;

    @Column(name = "subtype_cyr")
    private String subtypeCyr;
    
    

    public ServiceSubtype(Long id, String subtype, String subtypeEn, String subtypeCyr) {
		super();
		this.id = id;
		this.subtype = subtype;
		this.subtypeEn = subtypeEn;
		this.subtypeCyr = subtypeCyr;
	}

	public ServiceSubtype() {
		super();
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtype() {
        return subtype;
    }

    public ServiceSubtype subtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtypeEn() {
        return subtypeEn;
    }

    public ServiceSubtype subtypeEn(String subtypeEn) {
        this.subtypeEn = subtypeEn;
        return this;
    }

    public void setSubtypeEn(String subtypeEn) {
        this.subtypeEn = subtypeEn;
    }

    public String getSubtypeCyr() {
        return subtypeCyr;
    }

    public ServiceSubtype subtypeCyr(String subtypeCyr) {
        this.subtypeCyr = subtypeCyr;
        return this;
    }

    public void setSubtypeCyr(String subtypeCyr) {
        this.subtypeCyr = subtypeCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceSubtype)) {
            return false;
        }
        return id != null && id.equals(((ServiceSubtype) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceSubtype{" +
            "id=" + getId() +
            ", subtype='" + getSubtype() + "'" +
            ", subtypeEn='" + getSubtypeEn() + "'" +
            ", subtypeCyr='" + getSubtypeCyr() + "'" +
            "}";
    }
}
