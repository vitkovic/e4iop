package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A RequestRespond.
 */
@Entity
@Table(name = "request_respond")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RequestRespond implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Date and time of submission of respond to request for RI service
     */
    @ApiModelProperty(value = "Date and time of submission of respond to request for RI service")
    @Column(name = "date")
    private ZonedDateTime date;

    /**
     * service start date and time
     */
    @ApiModelProperty(value = "service start date and time")
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * service end date and time
     */
    @ApiModelProperty(value = "service end date and time")
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * The price of requested service in RSD
     */
    @ApiModelProperty(value = "The price of requested service in RSD")
    @Column(name = "price")
    private Double price;

    /**
     * Short description of reasons for denial or discussion
     */
    @ApiModelProperty(value = "Short description of reasons for denial or discussion")
    @Column(name = "message")
    private String message;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestForService requestForService;
    
    @OneToOne(mappedBy = "requestRespond")
    @JsonIgnore
    private ServiceOfferRespond serviceOfferRespond;

  

    @OneToOne(mappedBy = "requestRespond")
    @JsonIgnore
    private RiCalendar calendar;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "requestResponds", allowSetters = true)
    private RequestAnswer requestAnswer;       

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

    public RequestRespond date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public RequestRespond startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public RequestRespond endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public RequestRespond price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public RequestRespond message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RequestForService getRequestForService() {
        return requestForService;
    }

    public RequestRespond requestForService(RequestForService requestForService) {
        this.requestForService = requestForService;
        return this;
    }

    public void setRequestForService(RequestForService requestForService) {
        this.requestForService = requestForService;
    }

   
   

    public ServiceOfferRespond getServiceOfferRespond() {
		return serviceOfferRespond;
	}

	public void setServiceOfferRespond(ServiceOfferRespond serviceOfferRespond) {
		this.serviceOfferRespond = serviceOfferRespond;
	}

	public RiCalendar getCalendar() {
        return calendar;
    }

    public RequestRespond calendar(RiCalendar riCalendar) {
        this.calendar = riCalendar;
        return this;
    }

    public void setCalendar(RiCalendar riCalendar) {
        this.calendar = riCalendar;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public RequestAnswer getRequestAnswer() {
		return requestAnswer;
	}

	public void setRequestAnswer(RequestAnswer requestAnswer) {
		this.requestAnswer = requestAnswer;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestRespond)) {
            return false;
        }
        return id != null && id.equals(((RequestRespond) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestRespond{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", price=" + getPrice() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
