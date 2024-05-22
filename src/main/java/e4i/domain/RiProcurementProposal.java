package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.web.rest.dto.RiProcurementProposalDTO;
import e4i.web.rest.dto.RiServiceDTOSearch;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A RiProcurementProposal.
 */
@Entity
@Table(name = "ri_procurement_proposal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SqlResultSetMappings({
	@SqlResultSetMapping(name="rppDto", 
			classes={
			@ConstructorResult(
			     targetClass=RiProcurementProposalDTO.class,	
			       columns={
			          @ColumnResult(name="rId", type=Long.class),
			          @ColumnResult(name="rDate", type=ZonedDateTime.class),
			          @ColumnResult(name="rName", type=String.class),
			          @ColumnResult(name="rTp", type=String.class),
			          @ColumnResult(name="rP", type=String.class),
			          @ColumnResult(name="firstName", type=String.class),
			          @ColumnResult(name="lastName", type=String.class),
			          @ColumnResult(name="pId", type=Long.class),
			          @ColumnResult(name="editable", type=Boolean.class),
			          @ColumnResult(name="likes", type=Integer.class),
			          @ColumnResult(name="dislikes", type=Integer.class),
			          }
			   )
			}
	)
}) 

@NamedNativeQueries({	
	@NamedNativeQuery(name="RiProcurementProposal.pronadjiAllEditableForUser", 
			  query=" select ri_procurement_proposal.id as rId, ri_procurement_proposal.date as rDate, "
			  		+ " ri_procurement_proposal.name as rName, "
			  		+ "	ri_procurement_proposal.technical_specification as rTp, ri_procurement_proposal.purpose as rP, "
			  		+ "	jhi_user.first_name as firstName, jhi_user.last_name as lastName, portal_user.id as pId, "
			  		+ " case "
			  		+ "		when ri_procurement_proposal.portal_user_id = :pId then true "
			  		+ "     when ri_procurement_proposal.portal_user_id <> :pId then false "
			  		+ "		when ri_procurement_proposal.portal_user_id is null then false "
			  		+ " end as editable, count(ipl.nlike) as likes, count(ipl.ndislike) as dislikes "
			  		+ " from ri_procurement_proposal "
			  		+ " left join portal_user  on portal_user.id = ri_procurement_proposal.portal_user_id "
			  		+ "	left join jhi_user  on jhi_user.id = portal_user.user_id "
			  		+ " left join infrastructure_proposals_likes ipl on ipl.proposal_id = ri_procurement_proposal.id "
			  		+ " group by rId, rDate, rName, rTp, rP, firstName, lastName, pId", resultSetMapping="rppDto"),
	@NamedNativeQuery(name="RiProcurementProposal.findAllEditableForAdmin", 
	  query=" select ri_procurement_proposal.id as rId, ri_procurement_proposal.date as rDate, "
	  		+ " ri_procurement_proposal.name as rName, "
	  		+ "	ri_procurement_proposal.technical_specification as rTp, ri_procurement_proposal.purpose as rP, "
	  		+ "	jhi_user.first_name as firstName, jhi_user.last_name as lastName, portal_user.id as pId, "
	  		+ " true as editable, count(ipl.nlike) as likes, count(ipl.ndislike) as dislikes  "
	  		+ " from ri_procurement_proposal "
	  		+ " left join portal_user  on portal_user.id = ri_procurement_proposal.portal_user_id "
	  		+ "	left join jhi_user  on jhi_user.id = portal_user.user_id "
	  		+ " left join infrastructure_proposals_likes ipl on ipl.proposal_id = ri_procurement_proposal.id "
	  		+ " group by rId, rDate, rName, rTp, rP, firstName, lastName, pId", resultSetMapping="rppDto"),
	})

public class RiProcurementProposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Proposal submission date and time
     */
    @ApiModelProperty(value = "Proposal submission date and time")
    @Column(name = "date")
    private ZonedDateTime date;

    /**
     * Name of the RI to be procured
     */
    @ApiModelProperty(value = "Name of the RI to be procured")
    @Column(name = "name")
    private String name;

    /**
     * Technical specification of the RI to be procured
     */
    @ApiModelProperty(value = "Technical specification of the RI to be procured")
    @Column(name = "technical_specification")
    private String technicalSpecification;

    /**
     * Purpose of proposed RI
     */
    @ApiModelProperty(value = "Purpose of proposed RI")
    @Column(name = "purpose")
    private String purpose;

    @ManyToOne
    @JsonIgnoreProperties(value = "riProposals", allowSetters = true)
    private PortalUser portalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public RiProcurementProposal date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public RiProcurementProposal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechnicalSpecification() {
        return technicalSpecification;
    }

    public RiProcurementProposal technicalSpecification(String technicalSpecification) {
        this.technicalSpecification = technicalSpecification;
        return this;
    }

    public void setTechnicalSpecification(String technicalSpecification) {
        this.technicalSpecification = technicalSpecification;
    }

    public String getPurpose() {
        return purpose;
    }

    public RiProcurementProposal purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    public RiProcurementProposal portalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
        return this;
    }

    public void setPortalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiProcurementProposal)) {
            return false;
        }
        return id != null && id.equals(((RiProcurementProposal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiProcurementProposal{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", name='" + getName() + "'" +
            ", technicalSpecification='" + getTechnicalSpecification() + "'" +
            ", purpose='" + getPurpose() + "'" +
            "}";
    }
}
