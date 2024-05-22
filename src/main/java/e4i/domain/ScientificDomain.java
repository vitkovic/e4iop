package e4i.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Scientific domains. It is possible to select multiple domains
 */
@ApiModel(description = "Scientific domains. It is possible to select multiple domains")
@Entity
@Table(name = "scientific_domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScientificDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "domain")
    private String domain;

    @Column(name = "domain_en")
    private String domainEn;

    @Column(name = "domain_cyr")
    private String domainCyr;

    @OneToMany(mappedBy = "domain")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ScientificSubdomain> subdomains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public ScientificDomain domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainEn() {
        return domainEn;
    }

    public ScientificDomain domainEn(String domainEn) {
        this.domainEn = domainEn;
        return this;
    }

    public void setDomainEn(String domainEn) {
        this.domainEn = domainEn;
    }

    public String getDomainCyr() {
        return domainCyr;
    }

    public ScientificDomain domainCyr(String domainCyr) {
        this.domainCyr = domainCyr;
        return this;
    }

    public void setDomainCyr(String domainCyr) {
        this.domainCyr = domainCyr;
    }

    public Set<ScientificSubdomain> getSubdomains() {
        return subdomains;
    }

    public ScientificDomain subdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
        return this;
    }

    public ScientificDomain addSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.add(scientificSubdomain);
        scientificSubdomain.setDomain(this);
        return this;
    }

    public ScientificDomain removeSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.remove(scientificSubdomain);
        scientificSubdomain.setDomain(null);
        return this;
    }

    public void setSubdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScientificDomain)) {
            return false;
        }
        return id != null && id.equals(((ScientificDomain) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScientificDomain{" +
            "id=" + getId() +
            ", domain='" + getDomain() + "'" +
            ", domainEn='" + getDomainEn() + "'" +
            ", domainCyr='" + getDomainCyr() + "'" +
            "}";
    }
}
