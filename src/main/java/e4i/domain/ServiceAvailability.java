package e4i.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ServiceAvailability.
 */
@Entity
@Table(name = "service_availability")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Available, Temporary unavailable, Permanently unavailable
     */
    @ApiModelProperty(value = "Available, Temporary unavailable, Permanently unavailable")
    @Column(name = "availability")
    private String availability;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "availability_en")
    private String availabilityEn;

    @Column(name = "availability_cyr")
    private String availabilityCyr;

    @Column(name = "meaning_en")
    private String meaningEn;

    @Column(name = "meaning_cyr")
    private String meaningCyr;
    
    

    public ServiceAvailability(Long id, String availability, String availabilityEn, String availabilityCyr) {
		this.id = id;
		this.availability = availability;
		this.availabilityEn = availabilityEn;
		this.availabilityCyr = availabilityCyr;
	}

	public ServiceAvailability() {
		super();
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public ServiceAvailability availability(String availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getMeaning() {
        return meaning;
    }

    public ServiceAvailability meaning(String meaning) {
        this.meaning = meaning;
        return this;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getAvailabilityEn() {
        return availabilityEn;
    }

    public ServiceAvailability availabilityEn(String availabilityEn) {
        this.availabilityEn = availabilityEn;
        return this;
    }

    public void setAvailabilityEn(String availabilityEn) {
        this.availabilityEn = availabilityEn;
    }

    public String getAvailabilityCyr() {
        return availabilityCyr;
    }

    public ServiceAvailability availabilityCyr(String availabilityCyr) {
        this.availabilityCyr = availabilityCyr;
        return this;
    }

    public void setAvailabilityCyr(String availabilityCyr) {
        this.availabilityCyr = availabilityCyr;
    }

    public String getMeaningEn() {
        return meaningEn;
    }

    public ServiceAvailability meaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
        return this;
    }

    public void setMeaningEn(String meaningEn) {
        this.meaningEn = meaningEn;
    }

    public String getMeaningCyr() {
        return meaningCyr;
    }

    public ServiceAvailability meaningCyr(String meaningCyr) {
        this.meaningCyr = meaningCyr;
        return this;
    }

    public void setMeaningCyr(String meaningCyr) {
        this.meaningCyr = meaningCyr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceAvailability)) {
            return false;
        }
        return id != null && id.equals(((ServiceAvailability) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceAvailability{" +
            "id=" + getId() +
            ", availability='" + getAvailability() + "'" +
            ", meaning='" + getMeaning() + "'" +
            ", availabilityEn='" + getAvailabilityEn() + "'" +
            ", availabilityCyr='" + getAvailabilityCyr() + "'" +
            ", meaningEn='" + getMeaningEn() + "'" +
            ", meaningCyr='" + getMeaningCyr() + "'" +
            "}";
    }
}
