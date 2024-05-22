package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.web.rest.dto.RiProcurementProposalDTO;
import e4i.web.rest.dto.ServiceProposalDTO;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ServiceProposal.
 */
@Entity
@Table(name = "service_proposal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@SqlResultSetMappings({
	@SqlResultSetMapping(name="sppDto", 
			classes={
			@ConstructorResult(
			     targetClass=ServiceProposalDTO.class,	
			       columns={
			          @ColumnResult(name="rId", type=Long.class),
			          @ColumnResult(name="rDate", type=ZonedDateTime.class),
			          @ColumnResult(name="rName", type=String.class),
			          @ColumnResult(name="rD", type=String.class),
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
	@NamedNativeQuery(name="ServiceProposal.pronadjiAllEditableForUser", 
			  query=" select service_proposal.id as rId, service_proposal.date as rDate, "
			  		+ " service_proposal.name as rName, "
			  		+ "	service_proposal.description as rD, service_proposal.purpose as rP, "
			  		+ "	jhi_user.first_name as firstName, jhi_user.last_name as lastName, portal_user.id as pId, "
			  		+ " case "
			  		+ "		when service_proposal.portal_user_id = :pId then true "
			  		+ "     when service_proposal.portal_user_id <> :pId then false "
			  		+ "		when service_proposal.portal_user_id is null then false "
			  		+ " end as editable, count(spl.nlike) as likes, count(spl.ndislike) as dislikes "
			  		+ " from service_proposal "
			  		+ " left join portal_user  on portal_user.id = service_proposal.portal_user_id "
			  		+ "	left join jhi_user  on jhi_user.id = portal_user.user_id"
			  		+ " left join service_proposals_likes spl on spl.proposal_id = service_proposal.id "
			  		+ " group by rId, rDate, rName, rD, rP, firstName, lastName, pId", resultSetMapping="sppDto"),
	@NamedNativeQuery(name="ServiceProposal.findAllEditableForAdmin", 
	  query=" select service_proposal.id as rId, service_proposal.date as rDate, "
	  		+ " service_proposal.name as rName, "
	  		+ "	service_proposal.description as rD, service_proposal.purpose as rP, "
	  		+ "	jhi_user.first_name as firstName, jhi_user.last_name as lastName, portal_user.id as pId, "
	  		+ " true as editable, count(ipl.nlike) as likes, count(ipl.ndislike) as dislikes  "
	  		+ " from service_proposal "
	  		+ " left join portal_user  on portal_user.id = service_proposal.portal_user_id "
	  		+ "	left join jhi_user  on jhi_user.id = portal_user.user_id "
	  		+ " left join service_proposals_likes ipl on ipl.proposal_id = service_proposal.id "
	  		+ " group by rId, rDate, rName, rD, rP, firstName, lastName, pId", resultSetMapping="sppDto"),

	})

public class ServiceProposal implements Serializable {

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
     * Name of new service
     */
    @ApiModelProperty(value = "Name of new service")
    @Column(name = "name")
    private String name;

    /**
     * Short description of new service
     */
    @ApiModelProperty(value = "Short description of new service")
    @Column(name = "description")
    private String description;

    /**
     * Purpose of proposed new service
     */
    @ApiModelProperty(value = "Purpose of proposed new service")
    @Column(name = "purpose")
    private String purpose;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceProposals", allowSetters = true)
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

    public ServiceProposal date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public ServiceProposal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ServiceProposal description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public ServiceProposal purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    public ServiceProposal portalUser(PortalUser portalUser) {
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
        if (!(o instanceof ServiceProposal)) {
            return false;
        }
        return id != null && id.equals(((ServiceProposal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProposal{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", purpose='" + getPurpose() + "'" +
            "}";
    }
}
