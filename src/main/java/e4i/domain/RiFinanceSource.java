package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RiFinanceSource.
 */
@Entity
@Table(name = "ri_finance_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiFinanceSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ApiModelProperty(value = "Serbian Latin mode")
    @Column(name = "source_sr")
    private String sourceSr;

    @ApiModelProperty(value = "Serbian Cyrillic mode")
    @Column(name = "source_src")
    private String sourceSrc;

    @ApiModelProperty(value = "English mode")
    @Column(name = "source_en")
    private String sourceEn;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceSr() {
        return sourceSr;
    }

    public void setSourceSr(String sourceSr) {
        this.sourceSr = sourceSr;
    }

    public String getSourceSrc() {
        return sourceSrc;
    }

    public void setSourceSrc(String sourceSrc) {
        this.sourceSrc = sourceSrc;
    }

    public String getSourceEn() {
        return sourceEn;
    }

    public void setSourceEn(String sourceEn) {
        this.sourceEn = sourceEn;
    }

    // equals, hashCode, and toString methods

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiFinanceSource)) {
            return false;
        }
        return id != null && id.equals(((RiFinanceSource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiFinanceSource{" +
            "id=" + getId() +
            ", sourceSr='" + getSourceSr() + "'" +
            ", sourceSrc='" + getSourceSrc() + "'" +
            ", sourceEn='" + getSourceEn() + "'" +
            "}";
    }
}
