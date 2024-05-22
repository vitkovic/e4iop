package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RequestState.
 */
@Entity
@Table(name = "request_state")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RequestState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Request opened, closed
     */
    @ApiModelProperty(value = "Request opened or closed")
    @Column(name = "state_sr")
    private String stateSr;
    
    @Column(name = "state_en")
    private String stateEn;
    
    @Column(name = "state_sr_cyr")
    private String StateSrCyr;
    
    
    

    public RequestState() {
		super();
	}

	public RequestState(Long id) {
		this.id = id;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

	public String getStateSr() {
		return stateSr;
	}

	public void setStateSr(String stateSr) {
		this.stateSr = stateSr;
	}

	public String getStateEn() {
		return stateEn;
	}

	public void setStateEn(String stateEn) {
		this.stateEn = stateEn;
	}

	public String getStateSrCyr() {
		return StateSrCyr;
	}

	public void setStateSrCyr(String stateSrCyr) {
		StateSrCyr = stateSrCyr;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestState)) {
            return false;
        }
        return id != null && id.equals(((RequestState) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestState{" +
            "id=" + getId() +
            ", state='" + getStateSr() + "'" +
            "}";
    }
}
