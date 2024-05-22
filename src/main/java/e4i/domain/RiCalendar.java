package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A RiCalendar.
 */
@Entity
@Table(name = "ri_calendar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RiCalendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Service start date and time
     */
    @ApiModelProperty(value = "Service start date and time")
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * Service end date and time
     */
    @ApiModelProperty(value = "Service end date and time")
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * Short note on service realisation
     */
    @ApiModelProperty(value = "Short note on service realisation")
    @Column(name = "cal_note")
    private String calNote;

    /**
     * PU rating of service in range from 1 to 5. Only for delivered services.
     */
    @ApiModelProperty(value = "PU rating of service in range from 1 to 5. Only for delivered services.")
    @Column(name = "cal_rating")
    private String calRating;

    /**
     * PU comment on quality of service. Only for delivered services.
     */
    @ApiModelProperty(value = "PU comment on quality of service. Only for delivered services.")
    @Column(name = "cal_comment")
    private String calComment;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestRespond requestRespond;

    @ManyToOne
    @JsonIgnoreProperties(value = "riCalendars", allowSetters = true)
    private RiServiceStatus serviceStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = "calendars", allowSetters = true)
    private PortalUser portalUsers;

    @ManyToOne
    @JsonIgnoreProperties(value = "calendars", allowSetters = true)
    private ResearchInfrastructure researchInfrastructure;

    @ManyToOne
    @JsonIgnoreProperties(value = "calendars", allowSetters = true)
    private RiService service;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public RiCalendar startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public RiCalendar endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCalNote() {
        return calNote;
    }

    public RiCalendar calNote(String calNote) {
        this.calNote = calNote;
        return this;
    }

    public void setCalNote(String calNote) {
        this.calNote = calNote;
    }

    public String getCalRating() {
        return calRating;
    }

    public RiCalendar calRating(String calRating) {
        this.calRating = calRating;
        return this;
    }

    public void setCalRating(String calRating) {
        this.calRating = calRating;
    }

    public String getCalComment() {
        return calComment;
    }

    public RiCalendar calComment(String calComment) {
        this.calComment = calComment;
        return this;
    }

    public void setCalComment(String calComment) {
        this.calComment = calComment;
    }

    public RequestRespond getRequestRespond() {
        return requestRespond;
    }

    public RiCalendar requestRespond(RequestRespond requestRespond) {
        this.requestRespond = requestRespond;
        return this;
    }

    public void setRequestRespond(RequestRespond requestRespond) {
        this.requestRespond = requestRespond;
    }

    public RiServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public RiCalendar serviceStatus(RiServiceStatus riServiceStatus) {
        this.serviceStatus = riServiceStatus;
        return this;
    }

    public void setServiceStatus(RiServiceStatus riServiceStatus) {
        this.serviceStatus = riServiceStatus;
    }

    public PortalUser getPortalUsers() {
        return portalUsers;
    }

    public RiCalendar portalUsers(PortalUser portalUser) {
        this.portalUsers = portalUser;
        return this;
    }

    public void setPortalUsers(PortalUser portalUser) {
        this.portalUsers = portalUser;
    }

    public ResearchInfrastructure getResearchInfrastructure() {
        return researchInfrastructure;
    }

    public RiCalendar researchInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.researchInfrastructure = researchInfrastructure;
        return this;
    }

    public void setResearchInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.researchInfrastructure = researchInfrastructure;
    }

    public RiService getService() {
        return service;
    }

    public RiCalendar service(RiService riService) {
        this.service = riService;
        return this;
    }

    public void setService(RiService riService) {
        this.service = riService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiCalendar)) {
            return false;
        }
        return id != null && id.equals(((RiCalendar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiCalendar{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", calNote='" + getCalNote() + "'" +
            ", calRating='" + getCalRating() + "'" +
            ", calComment='" + getCalComment() + "'" +
            "}";
    }
}
