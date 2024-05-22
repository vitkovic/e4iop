package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datetime", nullable = false)
    private Instant datetime;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_deleted_sender")
    private Boolean isDeletedSender;

    @Column(name = "is_deleted_receiver")
    private Boolean isDeletedReceiver;

    @ManyToOne
    @JsonIgnoreProperties(value = "messages", allowSetters = true)
    private Thread thread;

    @ManyToOne
    @JsonIgnoreProperties(value = "messageSenders", allowSetters = true)
    private PortalUser portalUserSender;

    @ManyToOne
    @JsonIgnoreProperties(value = "messageReceivers", allowSetters = true)
    private PortalUser portalUserReceiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public Message datetime(Instant datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public Message content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public Message isRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean isIsDeletedSender() {
        return isDeletedSender;
    }

    public Message isDeletedSender(Boolean isDeletedSender) {
        this.isDeletedSender = isDeletedSender;
        return this;
    }

    public void setIsDeletedSender(Boolean isDeletedSender) {
        this.isDeletedSender = isDeletedSender;
    }

    public Boolean isIsDeletedReceiver() {
        return isDeletedReceiver;
    }

    public Message isDeletedReceiver(Boolean isDeletedReceiver) {
        this.isDeletedReceiver = isDeletedReceiver;
        return this;
    }

    public void setIsDeletedReceiver(Boolean isDeletedReceiver) {
        this.isDeletedReceiver = isDeletedReceiver;
    }

    public Thread getThread() {
        return thread;
    }

    public Message thread(Thread thread) {
        this.thread = thread;
        return this;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public PortalUser getPortalUserSender() {
        return portalUserSender;
    }

    public Message portalUserSender(PortalUser portalUser) {
        this.portalUserSender = portalUser;
        return this;
    }

    public void setPortalUserSender(PortalUser portalUser) {
        this.portalUserSender = portalUser;
    }

    public PortalUser getPortalUserReceiver() {
        return portalUserReceiver;
    }

    public Message portalUserReceiver(PortalUser portalUser) {
        this.portalUserReceiver = portalUser;
        return this;
    }

    public void setPortalUserReceiver(PortalUser portalUser) {
        this.portalUserReceiver = portalUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", content='" + getContent() + "'" +
            ", isRead='" + isIsRead() + "'" +
            ", isDeletedSender='" + isIsDeletedSender() + "'" +
            ", isDeletedReceiver='" + isIsDeletedReceiver() + "'" +
            "}";
    }
}
