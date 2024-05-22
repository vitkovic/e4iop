package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Thread.
 */
@Entity
@Table(name = "thread")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thread implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @OneToMany(mappedBy = "thread")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "thread_advertisement",
               joinColumns = @JoinColumn(name = "thread_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"))
    private Set<Advertisement> advertisements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "thread_collaboration",
               joinColumns = @JoinColumn(name = "thread_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "collaboration_id", referencedColumnName = "id"))
    private Set<Collaboration> collaborations = new HashSet<>();
    
    @ManyToOne
    @JsonIgnoreProperties(value = "threadsSenders", allowSetters = true)
    private Company companySender;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "threadsReceivers", allowSetters = true)
    private Company companyReceiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Thread subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Thread messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Thread addMessage(Message message) {
        this.messages.add(message);
        message.setThread(this);
        return this;
    }

    public Thread removeMessage(Message message) {
        this.messages.remove(message);
        message.setThread(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public Thread advertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public Thread addAdvertisement(Advertisement advertisement) {
        this.advertisements.add(advertisement);
        advertisement.getThreads().add(this);
        return this;
    }

    public Thread removeAdvertisement(Advertisement advertisement) {
        this.advertisements.remove(advertisement);
        advertisement.getThreads().remove(this);
        return this;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
    
    public Set<Collaboration> getCollaborations() {
        return collaborations;
    }

    public Thread collaborations(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
        return this;
    }

    public Thread addCollaboration(Collaboration collaboration) {
        this.collaborations.add(collaboration);
        collaboration.getThreads().add(this);
        return this;
    }

    public Thread removeCollaboration(Collaboration collaboration) {
        this.collaborations.remove(collaboration);
        collaboration.getThreads().remove(this);
        return this;
    }

    public void setCollaborations(Set<Collaboration> collaborations) {
        this.collaborations = collaborations;
    }

    public Company getCompanySender() {
        return companySender;
    }

    public Thread companySender(Company company) {
        this.companySender = company;
        return this;
    }

    public void setCompanySender(Company company) {
        this.companySender = company;
    }
    
    public Company getCompanyReceiver() {
        return companyReceiver;
    }

    public Thread companyReceiver(Company company) {
        this.companyReceiver = company;
        return this;
    }

    public void setCompanyReceiver(Company company) {
        this.companyReceiver = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thread)) {
            return false;
        }
        return id != null && id.equals(((Thread) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thread{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            "}";
    }
}
