package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RiAccessMode.
 */
@Entity
@Table(name = "ri_access_mode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiAccessMode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * research-driven\nmarket-driven\nfree\nconditionally free\nwide
     */
    @ApiModelProperty(value = "research-driven\nmarket-driven\nfree\nconditionally free\nwide")
    @Column(name = "mode")
    private String mode;

    @Column(name = "mode_en")
    private String modeEn;

    @Column(name = "mode_cyr")
    private String modeCyr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public RiAccessMode mode(String mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeEn() {
        return modeEn;
    }

    public RiAccessMode modeEn(String modeEn) {
        this.modeEn = modeEn;
        return this;
    }

    public void setModeEn(String modeEn) {
        this.modeEn = modeEn;
    }

    public String getModeCyr() {
        return modeCyr;
    }

    public RiAccessMode modeCyr(String modeCyr) {
        this.modeCyr = modeCyr;
        return this;
    }

    public void setModeCyr(String modeCyr) {
        this.modeCyr = modeCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiAccessMode)) {
            return false;
        }
        return id != null && id.equals(((RiAccessMode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiAccessMode{" +
            "id=" + getId() +
            ", mode='" + getMode() + "'" +
            ", modeEn='" + getModeEn() + "'" +
            ", modeCyr='" + getModeCyr() + "'" +
            "}";
    }
}
