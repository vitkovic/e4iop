package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RiSubcategory.
 */
@Entity
@Table(name = "ri_subcategory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiSubcategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "subcategory_en")
    private String subcategoryEn;

    @Column(name = "sub_category_cyr")
    private String subCategoryCyr;

    @ManyToMany(mappedBy = "subcategories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ResearchInfrastructure> infrastructures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public RiSubcategory subcategory(String subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubcategoryEn() {
        return subcategoryEn;
    }

    public RiSubcategory subcategoryEn(String subcategoryEn) {
        this.subcategoryEn = subcategoryEn;
        return this;
    }

    public void setSubcategoryEn(String subcategoryEn) {
        this.subcategoryEn = subcategoryEn;
    }

    public String getSubCategoryCyr() {
        return subCategoryCyr;
    }

    public RiSubcategory subCategoryCyr(String subCategoryCyr) {
        this.subCategoryCyr = subCategoryCyr;
        return this;
    }

    public void setSubCategoryCyr(String subCategoryCyr) {
        this.subCategoryCyr = subCategoryCyr;
    }

    public Set<ResearchInfrastructure> getInfrastructures() {
        return infrastructures;
    }

    public RiSubcategory infrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
        return this;
    }

    public RiSubcategory addInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.add(researchInfrastructure);
        researchInfrastructure.getSubcategories().add(this);
        return this;
    }

    public RiSubcategory removeInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.remove(researchInfrastructure);
        researchInfrastructure.getSubcategories().remove(this);
        return this;
    }

    public void setInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiSubcategory)) {
            return false;
        }
        return id != null && id.equals(((RiSubcategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiSubcategory{" +
            "id=" + getId() +
            ", subcategory='" + getSubcategory() + "'" +
            ", subcategoryEn='" + getSubcategoryEn() + "'" +
            ", subCategoryCyr='" + getSubCategoryCyr() + "'" +
            "}";
    }
}
