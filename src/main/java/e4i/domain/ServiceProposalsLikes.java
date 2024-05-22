package e4i.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "service_proposals_likes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceProposalsLikes  implements Serializable{
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

	
	@ManyToOne
    @JsonIgnoreProperties(value = "serviceProposal", allowSetters = true)
    private ServiceProposal proposal;
	
	@ManyToOne
    @JsonIgnoreProperties(value = "portalUsers", allowSetters = true)
    private PortalUser portalUser;
	
	@Column(name = "nlike")
    private Integer nlike;
	
	@Column(name = "ndislike")
    private Integer ndislike;

	public ServiceProposalsLikes() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceProposal getProposal() {
		return proposal;
	}

	public void setProposal(ServiceProposal proposal) {
		this.proposal = proposal;
	}

	public PortalUser getPortalUser() {
		return portalUser;
	}

	public void setPortalUser(PortalUser portalUser) {
		this.portalUser = portalUser;
	}

	public Integer getNlike() {
		return nlike;
	}

	public void setNlike(Integer nlike) {
		this.nlike = nlike;
	}

	public Integer getNdislike() {
		return ndislike;
	}

	public void setNdislike(Integer ndislike) {
		this.ndislike = ndislike;
	}
	
	@Override
    public boolean equals(Object o) {	       	        
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceProposalsLikes)) {
            return false;
        }
        return id != null && id.equals(((ServiceProposalsLikes) o).id);
        
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProposalsLikes{" +	            
            "  ServiceProposal = " + this.getProposal() + 
            "  portalUser = " + this.getPortalUser() +	        		
            ", like='" + getNlike() + "'" +
            ", dislike='" + getNdislike() + "'" +
            "}";
    }

}
