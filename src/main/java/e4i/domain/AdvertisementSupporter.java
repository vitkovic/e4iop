package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.Thread;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdvertisementSupporter.
 */
@Entity
@Table(name = "advertisement_supporter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementSupporter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "has_accepted")
    private Boolean hasAccepted;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementSupporters", allowSetters = true)
    private Advertisement advertisement;

    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementSupporters", allowSetters = true)
    private Company company;
    
    @ManyToMany(mappedBy = "advertisementSupporters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Thread> threads = new HashSet<>();
    
    @ManyToOne
    @JsonIgnoreProperties(value = "advertisementSupporters", allowSetters = true)
    private AdvertisementSupporterStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasAccepted() {
        return hasAccepted;
    }

    public AdvertisementSupporter hasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
        return this;
    }

    public void setHasAccepted(Boolean hasAccepted) {
        this.hasAccepted = hasAccepted;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public AdvertisementSupporter advertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
        return this;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Company getCompany() {
        return company;
    }

    public AdvertisementSupporter company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    public Set<Thread> getThreads() {
        return threads;
    }

    public AdvertisementSupporter threads(Set<Thread> threads) {
        this.threads = threads;
        return this;
    }

    public AdvertisementSupporter addThread(Thread thread) {
        this.threads.add(thread);
        thread.getAdvertisementSupporters().add(this);
        return this;
    }

    public AdvertisementSupporter removeThread(Thread thread) {
        this.threads.remove(thread);
        thread.getAdvertisementSupporters().remove(this);
        return this;
    }

    public void setThreads(Set<Thread> threads) {
        this.threads = threads;
    }
    
    public AdvertisementSupporterStatus getStatus() {
        return status;
    }

    public AdvertisementSupporter status(AdvertisementSupporterStatus advertisementSupporterStatus) {
        this.status = advertisementSupporterStatus;
        return this;
    }

    public void setStatus(AdvertisementSupporterStatus advertisementSupporterStatus) {
        this.status = advertisementSupporterStatus;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementSupporter)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementSupporter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementSupporter{" +
            "id=" + getId() +
            ", hasAccepted='" + isHasAccepted() + "'" +
            "}";
    }
}
