package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ScientificSubdomain.
 */
@Entity
@Table(name = "scientific_subdomain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScientificSubdomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subdomain")
    private String subdomain;

    @Column(name = "subdomain_en")
    private String subdomainEn;

    @Column(name = "subdomain_cyr")
    private String subdomainCyr;

    @ManyToOne
    @JsonIgnoreProperties(value = "subdomains", allowSetters = true)
    private ScientificDomain domain;

    @ManyToMany(mappedBy = "subdomains")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<RiResearchOrganization> organizations = new HashSet<>();

    @ManyToMany(mappedBy = "subdomains")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ResearchInfrastructure> infrastructures = new HashSet<>();

    @ManyToMany(mappedBy = "subdomains")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<RiService> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public ScientificSubdomain subdomain(String subdomain) {
        this.subdomain = subdomain;
        return this;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getSubdomainEn() {
        return subdomainEn;
    }

    public ScientificSubdomain subdomainEn(String subdomainEn) {
        this.subdomainEn = subdomainEn;
        return this;
    }

    public void setSubdomainEn(String subdomainEn) {
        this.subdomainEn = subdomainEn;
    }

    public String getSubdomainCyr() {
        return subdomainCyr;
    }

    public ScientificSubdomain subdomainCyr(String subdomainCyr) {
        this.subdomainCyr = subdomainCyr;
        return this;
    }

    public void setSubdomainCyr(String subdomainCyr) {
        this.subdomainCyr = subdomainCyr;
    }

    public ScientificDomain getDomain() {
        return domain;
    }

    public ScientificSubdomain domain(ScientificDomain scientificDomain) {
        this.domain = scientificDomain;
        return this;
    }

    public void setDomain(ScientificDomain scientificDomain) {
        this.domain = scientificDomain;
    }

    public Set<RiResearchOrganization> getOrganizations() {
        return organizations;
    }

    public ScientificSubdomain organizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.organizations = riResearchOrganizations;
        return this;
    }

    public ScientificSubdomain addOrganizations(RiResearchOrganization riResearchOrganization) {
        this.organizations.add(riResearchOrganization);
        riResearchOrganization.getSubdomains().add(this);
        return this;
    }

    public ScientificSubdomain removeOrganizations(RiResearchOrganization riResearchOrganization) {
        this.organizations.remove(riResearchOrganization);
        riResearchOrganization.getSubdomains().remove(this);
        return this;
    }

    public void setOrganizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.organizations = riResearchOrganizations;
    }

    public Set<ResearchInfrastructure> getInfrastructures() {
        return infrastructures;
    }

    public ScientificSubdomain infrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
        return this;
    }

    public ScientificSubdomain addInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.add(researchInfrastructure);
        researchInfrastructure.getSubdomains().add(this);
        return this;
    }

    public ScientificSubdomain removeInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.remove(researchInfrastructure);
        researchInfrastructure.getSubdomains().remove(this);
        return this;
    }

    public void setInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
    }

    public Set<RiService> getServices() {
        return services;
    }

    public ScientificSubdomain services(Set<RiService> riServices) {
        this.services = riServices;
        return this;
    }

    public ScientificSubdomain addServices(RiService riService) {
        this.services.add(riService);
        riService.getSubdomains().add(this);
        return this;
    }

    public ScientificSubdomain removeServices(RiService riService) {
        this.services.remove(riService);
        riService.getSubdomains().remove(this);
        return this;
    }

    public void setServices(Set<RiService> riServices) {
        this.services = riServices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScientificSubdomain)) {
            return false;
        }
        return id != null && id.equals(((ScientificSubdomain) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScientificSubdomain{" +
            "id=" + getId() +
            ", subdomain='" + getSubdomain() + "'" +
            ", subdomainEn='" + getSubdomainEn() + "'" +
            ", subdomainCyr='" + getSubdomainCyr() + "'" +
            "}";
    }
}
