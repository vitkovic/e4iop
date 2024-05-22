package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @NotNull
    @Column(name = "rsnis_id", nullable = false)
    private Long rsnisId;

    @NotNull
    @Column(name = "apr_id", nullable = false)
    private Long aprId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "about")
    private String about;

    @Column(name = "fields")
    private String fields;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PortalUser> portalUsers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Advertisement> advertisements = new HashSet<>();

    @OneToMany(mappedBy = "companyOffer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationOffers = new HashSet<>();

    @OneToMany(mappedBy = "companyRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationRequests = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvertisementSupporter> advertisementSupporters = new HashSet<>();

    @OneToMany(mappedBy = "companySender")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Thread> threadsSenders = new HashSet<>();
    
    @OneToMany(mappedBy = "companyReceiver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Thread> threadsReceivers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "company_badge",
               joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "badge_id", referencedColumnName = "id"))
    private Set<Badge> badges = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "company_document",
               joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    // Da bi se izbegao stack overflow error, za korisnika se ne povlaci kompanija
    @ManyToOne
    @JsonIgnoreProperties(value = {"companyCreateds", "company"}, allowSetters = true)
    private PortalUser createdBy;

    // Da bi se izbegao stack overflow error, za korisnika se ne povlaci kompanija
    @ManyToOne
    @JsonIgnoreProperties(value = {"companyModifieds", "company"}, allowSetters = true)
    private PortalUser modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "companyLogos", allowSetters = true)
    private Document logo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Company createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public Company modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getRsnisId() {
        return rsnisId;
    }

    public Company rsnisId(Long rsnisId) {
        this.rsnisId = rsnisId;
        return this;
    }

    public void setRsnisId(Long rsnisId) {
        this.rsnisId = rsnisId;
    }

    public Long getAprId() {
        return aprId;
    }

    public Company aprId(Long aprId) {
        this.aprId = aprId;
        return this;
    }

    public void setAprId(Long aprId) {
        this.aprId = aprId;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public Company about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFields() {
        return fields;
    }

    public Company fields(String fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Set<PortalUser> getPortalUsers() {
        return portalUsers;
    }

    public Company portalUsers(Set<PortalUser> portalUsers) {
        this.portalUsers = portalUsers;
        return this;
    }

    public Company addPortalUser(PortalUser portalUser) {
        this.portalUsers.add(portalUser);
        portalUser.setCompany(this);
        return this;
    }

    public Company removePortalUser(PortalUser portalUser) {
        this.portalUsers.remove(portalUser);
        portalUser.setCompany(null);
        return this;
    }

    public void setPortalUsers(Set<PortalUser> portalUsers) {
        this.portalUsers = portalUsers;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public Company advertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public Company addAdvertisement(Advertisement advertisement) {
        this.advertisements.add(advertisement);
        advertisement.setCompany(this);
        return this;
    }

    public Company removeAdvertisement(Advertisement advertisement) {
        this.advertisements.remove(advertisement);
        advertisement.setCompany(null);
        return this;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public Set<Collaboration> getCollaborationOffers() {
        return collaborationOffers;
    }

    public Company collaborationOffers(Set<Collaboration> collaborations) {
        this.collaborationOffers = collaborations;
        return this;
    }

    public Company addCollaborationOffer(Collaboration collaboration) {
        this.collaborationOffers.add(collaboration);
        collaboration.setCompanyOffer(this);
        return this;
    }

    public Company removeCollaborationOffer(Collaboration collaboration) {
        this.collaborationOffers.remove(collaboration);
        collaboration.setCompanyOffer(null);
        return this;
    }

    public void setCollaborationOffers(Set<Collaboration> collaborations) {
        this.collaborationOffers = collaborations;
    }

    public Set<Collaboration> getCollaborationRequests() {
        return collaborationRequests;
    }

    public Company collaborationRequests(Set<Collaboration> collaborations) {
        this.collaborationRequests = collaborations;
        return this;
    }

    public Company addCollaborationRequest(Collaboration collaboration) {
        this.collaborationRequests.add(collaboration);
        collaboration.setCompanyRequest(this);
        return this;
    }

    public Company removeCollaborationRequest(Collaboration collaboration) {
        this.collaborationRequests.remove(collaboration);
        collaboration.setCompanyRequest(null);
        return this;
    }

    public void setCollaborationRequests(Set<Collaboration> collaborations) {
        this.collaborationRequests = collaborations;
    }

    public Set<AdvertisementSupporter> getAdvertisementSupporters() {
        return advertisementSupporters;
    }

    public Company advertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
        return this;
    }

    public Company addAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.add(advertisementSupporter);
        advertisementSupporter.setCompany(this);
        return this;
    }

    public Company removeAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.remove(advertisementSupporter);
        advertisementSupporter.setCompany(null);
        return this;
    }

    public void setAdvertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
    }

    public Set<Thread> getThreadsSenders() {
        return threadsSenders;
    }

    public Company threadsSenders(Set<Thread> threads) {
        this.threadsSenders = threads;
        return this;
    }

    public Company addThreadSenders(Thread thread) {
        this.threadsSenders.add(thread);
        thread.setCompanySender(this);
        return this;
    }

    public Company removeThreadSenders(Thread thread) {
        this.threadsSenders.remove(thread);
        thread.setCompanySender(null);
        return this;
    }

    public void setThreadsSenders(Set<Thread> threads) {
        this.threadsSenders = threads;
    }
    
    public Set<Thread> getThreadsReceivers() {
        return threadsReceivers;
    }

    public Company threadsReceivers(Set<Thread> threads) {
        this.threadsReceivers = threads;
        return this;
    }

    public Company addThreadReceivers(Thread thread) {
        this.threadsReceivers.add(thread);
        thread.setCompanyReceiver(this);
        return this;
    }

    public Company removeThreadReceivers(Thread thread) {
        this.threadsReceivers.remove(thread);
        thread.setCompanyReceiver(null);
        return this;
    }

    public void setThreadsReceivers(Set<Thread> threads) {
        this.threadsSenders = threads;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public Company badges(Set<Badge> badges) {
        this.badges = badges;
        return this;
    }

    public Company addBadge(Badge badge) {
        this.badges.add(badge);
        badge.getCompanies().add(this);
        return this;
    }

    public Company removeBadge(Badge badge) {
        this.badges.remove(badge);
        badge.getCompanies().remove(this);
        return this;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Company documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Company addDocument(Document document) {
        this.documents.add(document);
        document.getCompanies().add(this);
        return this;
    }

    public Company removeDocument(Document document) {
        this.documents.remove(document);
        document.getCompanies().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public Company createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public Company modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }

    public Document getLogo() {
        return logo;
    }

    public Company logo(Document document) {
        this.logo = document;
        return this;
    }

    public void setLogo(Document document) {
        this.logo = document;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", modifiedAt='" + getModifiedAt() + "'" +
            ", rsnisId=" + getRsnisId() +
            ", aprId=" + getAprId() +
            ", name='" + getName() + "'" +
            ", about='" + getAbout() + "'" +
            ", fields='" + getFields() + "'" +
            "}";
    }
}
