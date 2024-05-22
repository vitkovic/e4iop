package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ServiceOfferRespond.
 */
@Entity
@Table(name = "service_offer_respond")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceOfferRespond implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Date and time of submission of respond to offer for RI service
     */
    @ApiModelProperty(value = "Date and time of submission of respond to offer for RI service")
    @Column(name = "date")
    private ZonedDateTime date;

    /**
     * Description of reasons for denial or discussion
     */
    @ApiModelProperty(value = "Description of reasons for denial or discussion")
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "serviceOfferResponds", allowSetters = true)
    private AnswerToOffer offerAnswer;

    @OneToOne
    @JoinColumn(unique = true)
    private RequestRespond requestRespond;

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

    public ServiceOfferRespond date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public ServiceOfferRespond description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnswerToOffer getOfferAnswer() {
        return offerAnswer;
    }

    public ServiceOfferRespond offerAnswer(AnswerToOffer answerToOffer) {
        this.offerAnswer = answerToOffer;
        return this;
    }

    public void setOfferAnswer(AnswerToOffer answerToOffer) {
        this.offerAnswer = answerToOffer;
    }

   

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public RequestRespond getRequestRespond() {
		return requestRespond;
	}

	public void setRequestRespond(RequestRespond requestRespond) {
		this.requestRespond = requestRespond;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOfferRespond)) {
            return false;
        }
        return id != null && id.equals(((ServiceOfferRespond) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOfferRespond{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
