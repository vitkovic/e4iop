package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RiCategory.
 */
@Entity
@Table(name = "ri_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Access to physical and e-infrastructure\nAggregators and integrators\nProcessing and analysis\nSharing and discovery\nTraining and support
     */
    @ApiModelProperty(value = "Access to physical and e-infrastructure\nAggregators and integrators\nProcessing and analysis\nSharing and discovery\nTraining and support")
    @Column(name = "category")
    private String category;

    @Column(name = "category_en")
    private String categoryEn;

    @Column(name = "category_cyr")
    private String categoryCyr;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public RiCategory category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryEn() {
        return categoryEn;
    }

    public RiCategory categoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
        return this;
    }

    public void setCategoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
    }

    public String getCategoryCyr() {
        return categoryCyr;
    }

    public RiCategory categoryCyr(String categoryCyr) {
        this.categoryCyr = categoryCyr;
        return this;
    }

    public void setCategoryCyr(String categoryCyr) {
        this.categoryCyr = categoryCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiCategory)) {
            return false;
        }
        return id != null && id.equals(((RiCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiCategory{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", categoryEn='" + getCategoryEn() + "'" +
            ", categoryCyr='" + getCategoryCyr() + "'" +
            "}";
    }
}
