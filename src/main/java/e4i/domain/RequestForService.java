package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A RequestForService.
 */
@Entity
@Table(name = "request_for_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RequestForService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    /**
     * Short description of the required service including quantity
     */
    @ApiModelProperty(value = "Short description of the required service including quantity")
    @Column(name = "description")
    private String description;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "rating")
    private Integer rating;

    /**
     * Preferred service start date and time
     */
    @ApiModelProperty(value = "Preferred service start date and time")
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * Preferred service end date and time
     */
    @ApiModelProperty(value = "Preferred service end date and time")
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * The latest date by which service should be completed
     */
    @ApiModelProperty(value = "The latest date by which service should be completed")
    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @OneToOne(mappedBy = "requestForService")
    @JsonIgnore
    private RequestRespond respond;

    @ManyToOne
    @JsonIgnoreProperties(value = "requests", allowSetters = true)
    private RiService services;

    @ManyToOne
    @JsonIgnoreProperties(value = "requests", allowSetters = true)
    private PortalUser users;

    @ManyToOne
    @JsonIgnoreProperties(value = "requests", allowSetters = true)
    private PortalUserOrganization organizations;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "requests", allowSetters = true)
    private RequestState state;
    
    

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ZonedDateTime getDate() {
        return date;
    }

    public RequestForService date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public RequestForService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
    public RequestState getState() {
		return state;
	}

	public void setState(RequestState state) {
		this.state = state;
	}

	public ZonedDateTime getStartTime() {
        return startTime;
    }

    public RequestForService startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public RequestForService endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    

    public ZonedDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(ZonedDateTime deadline) {
		this.deadline = deadline;
	}

	public RequestRespond getRespond() {
        return respond;
    }

    public RequestForService respond(RequestRespond requestRespond) {
        this.respond = requestRespond;
        return this;
    }

    public void setRespond(RequestRespond requestRespond) {
        this.respond = requestRespond;
    }

    public RiService getServices() {
        return services;
    }

    public RequestForService services(RiService riService) {
        this.services = riService;
        return this;
    }

    public void setServices(RiService riService) {
        this.services = riService;
    }

    public PortalUser getUsers() {
        return users;
    }

    public RequestForService users(PortalUser portalUser) {
        this.users = portalUser;
        return this;
    }

    public void setUsers(PortalUser portalUser) {
        this.users = portalUser;
    }

    public PortalUserOrganization getOrganizations() {
        return organizations;
    }

    public RequestForService organizations(PortalUserOrganization portalUserOrganization) {
        this.organizations = portalUserOrganization;
        return this;
    }

    public void setOrganizations(PortalUserOrganization portalUserOrganization) {
        this.organizations = portalUserOrganization;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestForService)) {
            return false;
        }
        return id != null && id.equals(((RequestForService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestForService{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
