package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RiResearchOrganization.
 */
@Entity
@Table(name = "ri_research_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SqlResultSetMappings({
	@SqlResultSetMapping(name="ri_ro_query", 
			classes={
			@ConstructorResult(
			     targetClass=RiResearchOrganizationReportDTO.class,			      
			       columns={
			          @ColumnResult(name="id", type=Long.class),
			          @ColumnResult(name="name", type=String.class),
			          @ColumnResult(name="address1", type=String.class),
			          @ColumnResult(name="address2", type=String.class),
			          }
			   )
			}
	)
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="RiResearchOrganization.findAllForReport", 
			query="SELECT r.id, r.name, ri.address as address1, puo.address as address2 "
					+ "  FROM ri_research_organization r "					
					+ "  left join portal_user_organization puo on puo.id = r.pu_organization_id"
					+ "  left join research_institution ri on puo.nio_institution_id = ri.id  "
					+ "", resultSetMapping="ri_ro_query"),
	
	@NamedNativeQuery(name="RiResearchOrganization.findForSearchSr", 
	query="SELECT r.id, r.name, ri.address as address1, puo.address as address2 "
			+ " FROM ri_research_organization r "			
			+ " left join portal_user_organization puo on puo.id = r.pu_organization_id "
			+ " left join research_institution ri on puo.nio_institution_id = r.id  "
			+ " where (1 = :keywordsNotRequested or (lower(r.name) LIKE :name or lower(ri.name_cyr) LIKE :name or lower(ri.name) LIKE :name "
			+ " or lower(puo.legal_name_sr) LIKE :name or lower(puo.legal_name_sr_cyr) LIKE :name))"
			+ " and (1 = :nioNotRequested or (puo.nio_institution_id is not null))"
			+ " and (1 = :firmNotRequested or (puo.nio_institution_id is null))", resultSetMapping="ri_ro_query"),

})

public class RiResearchOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Full legal name of RIPO
     */
    @ApiModelProperty(value = "Full legal name of RIPO")
    @Column(name = "name")
    private String name;

    @Size(max = 1000)
    @Column(name = "description_sr", length = 1000)
    private String descriptionSr;

    @Size(max = 1000)
    @Column(name = "decsription_en", length = 1000)
    private String decsriptionEn;

    /**
     * Link to te logo
     */
    @ApiModelProperty(value = "Link to te logo")
    @Column(name = "logo")
    private String logo;

    @Column(name = "description_sr_cyr")
    private String descriptionSrCyr;

    @OneToOne
    @JoinColumn(unique = true)
    private PortalUserOrganization puOrganization;

   

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ResearchInfrastructure> infrastructures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "riResearchOrganizations", allowSetters = true)
    private RipoActivityStatus activityStatus;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "ri_research_organization_subdomains",
               joinColumns = @JoinColumn(name = "ri_research_organization_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subdomains_id", referencedColumnName = "id"))
    private Set<ScientificSubdomain> subdomains = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "managedOrganizations", allowSetters = true)
    private PortalUser manager;

    @ManyToOne
    @JsonIgnoreProperties(value = "contactOrganizations", allowSetters = true)
    private PortalUser contactPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RiResearchOrganization name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionSr() {
        return descriptionSr;
    }

    public RiResearchOrganization descriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
        return this;
    }

    public void setDescriptionSr(String descriptionSr) {
        this.descriptionSr = descriptionSr;
    }

    public String getDecsriptionEn() {
        return decsriptionEn;
    }

    public RiResearchOrganization decsriptionEn(String decsriptionEn) {
        this.decsriptionEn = decsriptionEn;
        return this;
    }

    public void setDecsriptionEn(String decsriptionEn) {
        this.decsriptionEn = decsriptionEn;
    }

    public String getLogo() {
        return logo;
    }

    public RiResearchOrganization logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescriptionSrCyr() {
        return descriptionSrCyr;
    }

    public RiResearchOrganization descriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
        return this;
    }

    public void setDescriptionSrCyr(String descriptionSrCyr) {
        this.descriptionSrCyr = descriptionSrCyr;
    }

    public PortalUserOrganization getPuOrganization() {
        return puOrganization;
    }

    public RiResearchOrganization puOrganization(PortalUserOrganization portalUserOrganization) {
        this.puOrganization = portalUserOrganization;
        return this;
    }

    public void setPuOrganization(PortalUserOrganization portalUserOrganization) {
        this.puOrganization = portalUserOrganization;
    }

   

    public Set<ResearchInfrastructure> getInfrastructures() {
        return infrastructures;
    }

    public RiResearchOrganization infrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
        return this;
    }

    public RiResearchOrganization addInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.add(researchInfrastructure);
        researchInfrastructure.setOwner(this);
        return this;
    }

    public RiResearchOrganization removeInfrastructures(ResearchInfrastructure researchInfrastructure) {
        this.infrastructures.remove(researchInfrastructure);
        researchInfrastructure.setOwner(null);
        return this;
    }

    public void setInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.infrastructures = researchInfrastructures;
    }

    public RipoActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public RiResearchOrganization activityStatus(RipoActivityStatus ripoActivityStatus) {
        this.activityStatus = ripoActivityStatus;
        return this;
    }

    public void setActivityStatus(RipoActivityStatus ripoActivityStatus) {
        this.activityStatus = ripoActivityStatus;
    }

    public Set<ScientificSubdomain> getSubdomains() {
        return subdomains;
    }

    public RiResearchOrganization subdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
        return this;
    }

    public RiResearchOrganization addSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.add(scientificSubdomain);
        scientificSubdomain.getOrganizations().add(this);
        return this;
    }

    public RiResearchOrganization removeSubdomains(ScientificSubdomain scientificSubdomain) {
        this.subdomains.remove(scientificSubdomain);
        scientificSubdomain.getOrganizations().remove(this);
        return this;
    }

    public void setSubdomains(Set<ScientificSubdomain> scientificSubdomains) {
        this.subdomains = scientificSubdomains;
    }

    public PortalUser getManager() {
        return manager;
    }

    public RiResearchOrganization manager(PortalUser portalUser) {
        this.manager = portalUser;
        return this;
    }

    public void setManager(PortalUser portalUser) {
        this.manager = portalUser;
    }

    public PortalUser getContactPerson() {
        return contactPerson;
    }

    public RiResearchOrganization contactPerson(PortalUser portalUser) {
        this.contactPerson = portalUser;
        return this;
    }

    public void setContactPerson(PortalUser portalUser) {
        this.contactPerson = portalUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiResearchOrganization)) {
            return false;
        }
        return id != null && id.equals(((RiResearchOrganization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiResearchOrganization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descriptionSr='" + getDescriptionSr() + "'" +
            ", decsriptionEn='" + getDecsriptionEn() + "'" +
            ", logo='" + getLogo() + "'" +
            ", descriptionSrCyr='" + getDescriptionSrCyr() + "'" +
            "}";
    }
}
