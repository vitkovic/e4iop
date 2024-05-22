package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import e4i.domain.Company;
import e4i.web.rest.vm.PortalUserDTO;
import e4i.web.rest.vm.PortalUserRegisterDTO;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A PortalUser.
 */
@Entity
@Table(name = "portal_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @NotNull
    @Column(name = "eid_id", nullable = false)
    private Long eidId;

    @Column(name = "do_notify")
    private Boolean doNotify;
    
    @Column(name = "phone")
    private String phone;

    @Column(name = "position")
    private String position;

    @OneToOne
    @JoinColumn(unique = true)
    private Researcher researcher;
    
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    
    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PortalUser> portalUserCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PortalUser> portalUserModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Company> companyCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Company> companyModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Advertisement> advertisementCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Advertisement> advertisementModifieds = new HashSet<>();

    @OneToMany(mappedBy = "portalUserSender")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Message> messageSenders = new HashSet<>();

    @OneToMany(mappedBy = "portalUserReceiver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Message> messageReceivers = new HashSet<>();

    @OneToMany(mappedBy = "portalUserOrganizer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Meeting> meetingOrganizers = new HashSet<>();
    
    @OneToMany(mappedBy = "portalUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsPage> cmsPageCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsPage> cmsPageModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsQuestion> cmsQuestionCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsQuestion> cmsQuestionModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsTestimonials> cmsTestimonialCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsTestimonials> cmsTestimonialModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsSlider> cmsSliderCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsSlider> cmsSliderModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsCarousel> cmsCarouselCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsCarousel> cmsCarouselModifieds = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsAdvertisementCategory> cmsAdvertisementCategoryCreateds = new HashSet<>();

    @OneToMany(mappedBy = "modifiedBy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsAdvertisementCategory> cmsAdvertisementCategoryModifieds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUserCreateds", allowSetters = true)
    private PortalUser createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUserModifieds", allowSetters = true)
    private PortalUser modifiedBy;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "portalUsers", allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUsers", allowSetters = true)
    private PortalUserRole role;

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUsers", allowSetters = true)
    private PortalUserStatus status;
    
   // old 
    
    @OneToMany(mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiResearchOrganization> managedOrganizations = new HashSet<>();

    @OneToMany(mappedBy = "contactPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiResearchOrganization> contactOrganizations = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ResearchInfrastructure> managedInfrastructures = new HashSet<>();

    @OneToMany(mappedBy = "contactPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ResearchInfrastructure> contactInfrastructures = new HashSet<>();

    @OneToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RequestForService> requests = new HashSet<>();

    @OneToMany(mappedBy = "portalUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiCalendar> calendars = new HashSet<>();

    @OneToMany(mappedBy = "portalUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RiProcurementProposal> riProposals = new HashSet<>();

    @OneToMany(mappedBy = "portalUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ServiceProposal> serviceProposals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "portal_user_portal_user_roles",
               joinColumns = @JoinColumn(name = "portal_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "portal_user_roles_id", referencedColumnName = "id"))
    private Set<PortalUserRole> portalUserRoles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "portalUsers", allowSetters = true)
    private PortalUserOrganization userOrganization;
    
     
    @ManyToOne
    @JsonIgnoreProperties(value = "portalUserStatuses", allowSetters = true)
    private PortalUserStatus portalUserStatus;
    
   
    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public PortalUser() {
		super();
	}
    
    public PortalUser(PortalUserRegisterDTO pu, User user) {
    	this.phone = pu.getPhone();
    	this.position = pu.getPosition();
    	this.portalUserStatus = pu.getPortalUserStatus();
    	this.user = user;
	}
    
    public PortalUser(PortalUserDTO pu) {
    	this.id = pu.getId();
    	this.phone = pu.getPhone();
    	this.position = pu.getPosition();
    	this.researcher = pu.getResearcher();
    	this.userOrganization = pu.getUserOrganization();
    	this.portalUserRoles = pu.getPortalUserRoles();
    }
    
    public PortalUser(String id, String pId, String firstName, String lastName) {
    	this.id = id;
    	if(pId != null)
    		this.user = new User(pId, firstName, lastName);
    }

    
    public Instant getCreatedAt() {
        return createdAt;
    }

    public PortalUser createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public PortalUser modifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Long getEidId() {
        return eidId;
    }

    public PortalUser eidId(Long eidId) {
        this.eidId = eidId;
        return this;
    }

    public void setEidId(Long eidId) {
        this.eidId = eidId;
    }

    public Boolean isDoNotify() {
        return doNotify;
    }

    public PortalUser doNotify(Boolean doNotify) {
        this.doNotify = doNotify;
        return this;
    }

    public void setDoNotify(Boolean doNotify) {
        this.doNotify = doNotify;
    }
    
    
    public Set<PortalUser> getPortalUserCreateds() {
        return portalUserCreateds;
    }

    public PortalUser portalUserCreateds(Set<PortalUser> portalUsers) {
        this.portalUserCreateds = portalUsers;
        return this;
    }

    public PortalUser addPortalUserCreated(PortalUser portalUser) {
        this.portalUserCreateds.add(portalUser);
        portalUser.setCreatedBy(this);
        return this;
    }

    public PortalUser removePortalUserCreated(PortalUser portalUser) {
        this.portalUserCreateds.remove(portalUser);
        portalUser.setCreatedBy(null);
        return this;
    }

    public void setPortalUserCreateds(Set<PortalUser> portalUsers) {
        this.portalUserCreateds = portalUsers;
    }

    public Set<PortalUser> getPortalUserModifieds() {
        return portalUserModifieds;
    }

    public PortalUser portalUserModifieds(Set<PortalUser> portalUsers) {
        this.portalUserModifieds = portalUsers;
        return this;
    }

    public PortalUser addPortalUserModified(PortalUser portalUser) {
        this.portalUserModifieds.add(portalUser);
        portalUser.setModifiedBy(this);
        return this;
    }

    public PortalUser removePortalUserModified(PortalUser portalUser) {
        this.portalUserModifieds.remove(portalUser);
        portalUser.setModifiedBy(null);
        return this;
    }

    public void setPortalUserModifieds(Set<PortalUser> portalUsers) {
        this.portalUserModifieds = portalUsers;
    }

    public Set<Company> getCompanyCreateds() {
        return companyCreateds;
    }

    public PortalUser companyCreateds(Set<Company> companies) {
        this.companyCreateds = companies;
        return this;
    }

    public PortalUser addCompanyCreated(Company company) {
        this.companyCreateds.add(company);
        company.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCompanyCreated(Company company) {
        this.companyCreateds.remove(company);
        company.setCreatedBy(null);
        return this;
    }

    public void setCompanyCreateds(Set<Company> companies) {
        this.companyCreateds = companies;
    }

    public Set<Company> getCompanyModifieds() {
        return companyModifieds;
    }

    public PortalUser companyModifieds(Set<Company> companies) {
        this.companyModifieds = companies;
        return this;
    }

    public PortalUser addCompanyModified(Company company) {
        this.companyModifieds.add(company);
        company.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCompanyModified(Company company) {
        this.companyModifieds.remove(company);
        company.setModifiedBy(null);
        return this;
    }

    public void setCompanyModifieds(Set<Company> companies) {
        this.companyModifieds = companies;
    }

    public Set<Advertisement> getAdvertisementCreateds() {
        return advertisementCreateds;
    }

    public PortalUser advertisementCreateds(Set<Advertisement> advertisements) {
        this.advertisementCreateds = advertisements;
        return this;
    }

    public PortalUser addAdvertisementCreated(Advertisement advertisement) {
        this.advertisementCreateds.add(advertisement);
        advertisement.setCreatedBy(this);
        return this;
    }

    public PortalUser removeAdvertisementCreated(Advertisement advertisement) {
        this.advertisementCreateds.remove(advertisement);
        advertisement.setCreatedBy(null);
        return this;
    }

    public void setAdvertisementCreateds(Set<Advertisement> advertisements) {
        this.advertisementCreateds = advertisements;
    }

    public Set<Advertisement> getAdvertisementModifieds() {
        return advertisementModifieds;
    }

    public PortalUser advertisementModifieds(Set<Advertisement> advertisements) {
        this.advertisementModifieds = advertisements;
        return this;
    }

    public PortalUser addAdvertisementModified(Advertisement advertisement) {
        this.advertisementModifieds.add(advertisement);
        advertisement.setModifiedBy(this);
        return this;
    }

    public PortalUser removeAdvertisementModified(Advertisement advertisement) {
        this.advertisementModifieds.remove(advertisement);
        advertisement.setModifiedBy(null);
        return this;
    }

    public void setAdvertisementModifieds(Set<Advertisement> advertisements) {
        this.advertisementModifieds = advertisements;
    }

    public Set<Message> getMessageSenders() {
        return messageSenders;
    }

    public PortalUser messageSenders(Set<Message> messages) {
        this.messageSenders = messages;
        return this;
    }

    public PortalUser addMessageSender(Message message) {
        this.messageSenders.add(message);
        message.setPortalUserSender(this);
        return this;
    }

    public PortalUser removeMessageSender(Message message) {
        this.messageSenders.remove(message);
        message.setPortalUserSender(null);
        return this;
    }

    public void setMessageSenders(Set<Message> messages) {
        this.messageSenders = messages;
    }

    public Set<Message> getMessageReceivers() {
        return messageReceivers;
    }

    public PortalUser messageReceivers(Set<Message> messages) {
        this.messageReceivers = messages;
        return this;
    }

    public PortalUser addMessageReceiver(Message message) {
        this.messageReceivers.add(message);
        message.setPortalUserReceiver(this);
        return this;
    }

    public PortalUser removeMessageReceiver(Message message) {
        this.messageReceivers.remove(message);
        message.setPortalUserReceiver(null);
        return this;
    }

    public void setMessageReceivers(Set<Message> messages) {
        this.messageReceivers = messages;
    }

    public Set<Meeting> getMeetingOrganizers() {
        return meetingOrganizers;
    }

    public PortalUser meetingOrganizers(Set<Meeting> meetings) {
        this.meetingOrganizers = meetings;
        return this;
    }

    public PortalUser addMeetingOrganizer(Meeting meeting) {
        this.meetingOrganizers.add(meeting);
        meeting.setPortalUserOrganizer(this);
        return this;
    }

    public PortalUser removeMeetingOrganizer(Meeting meeting) {
        this.meetingOrganizers.remove(meeting);
        meeting.setPortalUserOrganizer(null);
        return this;
    }

    public void setMeetingOrganizers(Set<Meeting> meetings) {
        this.meetingOrganizers = meetings;
    }

    public Set<MeetingParticipant> getMeetingParticipants() {
        return meetingParticipants;
    }

    public PortalUser meetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
        return this;
    }

    public PortalUser addMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.add(meetingParticipant);
        meetingParticipant.setPortalUser(this);
        return this;
    }

    public PortalUser removeMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.remove(meetingParticipant);
        meetingParticipant.setPortalUser(null);
        return this;
    }

    public void setMeetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }

    public Set<CmsPage> getCmsPageCreateds() {
        return cmsPageCreateds;
    }

    public PortalUser cmsPageCreateds(Set<CmsPage> cmsPages) {
        this.cmsPageCreateds = cmsPages;
        return this;
    }

    public PortalUser addCmsPageCreated(CmsPage cmsPage) {
        this.cmsPageCreateds.add(cmsPage);
        cmsPage.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsPageCreated(CmsPage cmsPage) {
        this.cmsPageCreateds.remove(cmsPage);
        cmsPage.setCreatedBy(null);
        return this;
    }

    public void setCmsPageCreateds(Set<CmsPage> cmsPages) {
        this.cmsPageCreateds = cmsPages;
    }

    public Set<CmsPage> getCmsPageModifieds() {
        return cmsPageModifieds;
    }

    public PortalUser cmsPageModifieds(Set<CmsPage> cmsPages) {
        this.cmsPageModifieds = cmsPages;
        return this;
    }

    public PortalUser addCmsPageModified(CmsPage cmsPage) {
        this.cmsPageModifieds.add(cmsPage);
        cmsPage.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsPageModified(CmsPage cmsPage) {
        this.cmsPageModifieds.remove(cmsPage);
        cmsPage.setModifiedBy(null);
        return this;
    }

    public void setCmsPageModifieds(Set<CmsPage> cmsPages) {
        this.cmsPageModifieds = cmsPages;
    }

    public Set<CmsQuestion> getCmsQuestionCreateds() {
        return cmsQuestionCreateds;
    }

    public PortalUser cmsQuestionCreateds(Set<CmsQuestion> cmsQuestions) {
        this.cmsQuestionCreateds = cmsQuestions;
        return this;
    }

    public PortalUser addCmsQuestionCreated(CmsQuestion cmsQuestion) {
        this.cmsQuestionCreateds.add(cmsQuestion);
        cmsQuestion.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsQuestionCreated(CmsQuestion cmsQuestion) {
        this.cmsQuestionCreateds.remove(cmsQuestion);
        cmsQuestion.setCreatedBy(null);
        return this;
    }

    public void setCmsQuestionCreateds(Set<CmsQuestion> cmsQuestions) {
        this.cmsQuestionCreateds = cmsQuestions;
    }

    public Set<CmsQuestion> getCmsQuestionModifieds() {
        return cmsQuestionModifieds;
    }

    public PortalUser cmsQuestionModifieds(Set<CmsQuestion> cmsQuestions) {
        this.cmsQuestionModifieds = cmsQuestions;
        return this;
    }

    public PortalUser addCmsQuestionModified(CmsQuestion cmsQuestion) {
        this.cmsQuestionModifieds.add(cmsQuestion);
        cmsQuestion.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsQuestionModified(CmsQuestion cmsQuestion) {
        this.cmsQuestionModifieds.remove(cmsQuestion);
        cmsQuestion.setModifiedBy(null);
        return this;
    }

    public void setCmsQuestionModifieds(Set<CmsQuestion> cmsQuestions) {
        this.cmsQuestionModifieds = cmsQuestions;
    }

    public Set<CmsTestimonials> getCmsTestimonialCreateds() {
        return cmsTestimonialCreateds;
    }

    public PortalUser cmsTestimonialCreateds(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonialCreateds = cmsTestimonials;
        return this;
    }

    public PortalUser addCmsTestimonialCreated(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonialCreateds.add(cmsTestimonials);
        cmsTestimonials.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsTestimonialCreated(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonialCreateds.remove(cmsTestimonials);
        cmsTestimonials.setCreatedBy(null);
        return this;
    }

    public void setCmsTestimonialCreateds(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonialCreateds = cmsTestimonials;
    }

    public Set<CmsTestimonials> getCmsTestimonialModifieds() {
        return cmsTestimonialModifieds;
    }

    public PortalUser cmsTestimonialModifieds(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonialModifieds = cmsTestimonials;
        return this;
    }

    public PortalUser addCmsTestimonialModified(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonialModifieds.add(cmsTestimonials);
        cmsTestimonials.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsTestimonialModified(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonialModifieds.remove(cmsTestimonials);
        cmsTestimonials.setModifiedBy(null);
        return this;
    }

    public void setCmsTestimonialModifieds(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonialModifieds = cmsTestimonials;
    }

    public Set<CmsSlider> getCmsSliderCreateds() {
        return cmsSliderCreateds;
    }

    public PortalUser cmsSliderCreateds(Set<CmsSlider> cmsSliders) {
        this.cmsSliderCreateds = cmsSliders;
        return this;
    }

    public PortalUser addCmsSliderCreated(CmsSlider cmsSlider) {
        this.cmsSliderCreateds.add(cmsSlider);
        cmsSlider.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsSliderCreated(CmsSlider cmsSlider) {
        this.cmsSliderCreateds.remove(cmsSlider);
        cmsSlider.setCreatedBy(null);
        return this;
    }

    public void setCmsSliderCreateds(Set<CmsSlider> cmsSliders) {
        this.cmsSliderCreateds = cmsSliders;
    }

    public Set<CmsSlider> getCmsSliderModifieds() {
        return cmsSliderModifieds;
    }

    public PortalUser cmsSliderModifieds(Set<CmsSlider> cmsSliders) {
        this.cmsSliderModifieds = cmsSliders;
        return this;
    }

    public PortalUser addCmsSliderModified(CmsSlider cmsSlider) {
        this.cmsSliderModifieds.add(cmsSlider);
        cmsSlider.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsSliderModified(CmsSlider cmsSlider) {
        this.cmsSliderModifieds.remove(cmsSlider);
        cmsSlider.setModifiedBy(null);
        return this;
    }

    public void setCmsSliderModifieds(Set<CmsSlider> cmsSliders) {
        this.cmsSliderModifieds = cmsSliders;
    }

    public Set<CmsCarousel> getCmsCarouselCreateds() {
        return cmsCarouselCreateds;
    }

    public PortalUser cmsCarouselCreateds(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouselCreateds = cmsCarousels;
        return this;
    }

    public PortalUser addCmsCarouselCreated(CmsCarousel cmsCarousel) {
        this.cmsCarouselCreateds.add(cmsCarousel);
        cmsCarousel.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsCarouselCreated(CmsCarousel cmsCarousel) {
        this.cmsCarouselCreateds.remove(cmsCarousel);
        cmsCarousel.setCreatedBy(null);
        return this;
    }

    public void setCmsCarouselCreateds(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouselCreateds = cmsCarousels;
    }

    public Set<CmsCarousel> getCmsCarouselModifieds() {
        return cmsCarouselModifieds;
    }

    public PortalUser cmsCarouselModifieds(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouselModifieds = cmsCarousels;
        return this;
    }

    public PortalUser addCmsCarouselModified(CmsCarousel cmsCarousel) {
        this.cmsCarouselModifieds.add(cmsCarousel);
        cmsCarousel.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsCarouselModified(CmsCarousel cmsCarousel) {
        this.cmsCarouselModifieds.remove(cmsCarousel);
        cmsCarousel.setModifiedBy(null);
        return this;
    }

    public void setCmsCarouselModifieds(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouselModifieds = cmsCarousels;
    }

    public Set<CmsAdvertisementCategory> getCmsAdvertisementCategoryCreateds() {
        return cmsAdvertisementCategoryCreateds;
    }

    public PortalUser cmsAdvertisementCategoryCreateds(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategoryCreateds = cmsAdvertisementCategories;
        return this;
    }

    public PortalUser addCmsAdvertisementCategoryCreated(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategoryCreateds.add(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setCreatedBy(this);
        return this;
    }

    public PortalUser removeCmsAdvertisementCategoryCreated(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategoryCreateds.remove(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setCreatedBy(null);
        return this;
    }

    public void setCmsAdvertisementCategoryCreateds(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategoryCreateds = cmsAdvertisementCategories;
    }

    public Set<CmsAdvertisementCategory> getCmsAdvertisementCategoryModifieds() {
        return cmsAdvertisementCategoryModifieds;
    }

    public PortalUser cmsAdvertisementCategoryModifieds(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategoryModifieds = cmsAdvertisementCategories;
        return this;
    }

    public PortalUser addCmsAdvertisementCategoryModified(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategoryModifieds.add(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setModifiedBy(this);
        return this;
    }

    public PortalUser removeCmsAdvertisementCategoryModified(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategoryModifieds.remove(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setModifiedBy(null);
        return this;
    }

    public void setCmsAdvertisementCategoryModifieds(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategoryModifieds = cmsAdvertisementCategories;
    }

    public PortalUser getCreatedBy() {
        return createdBy;
    }

    public PortalUser createdBy(PortalUser portalUser) {
        this.createdBy = portalUser;
        return this;
    }

    public void setCreatedBy(PortalUser portalUser) {
        this.createdBy = portalUser;
    }

    public PortalUser getModifiedBy() {
        return modifiedBy;
    }

    public PortalUser modifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
        return this;
    }

    public void setModifiedBy(PortalUser portalUser) {
        this.modifiedBy = portalUser;
    }

    public PortalUser company(Company company) {
        this.company = company;
        return this;
    }

   
    public PortalUserRole getRole() {
        return role;
    }

    public PortalUser role(PortalUserRole portalUserRole) {
        this.role = portalUserRole;
        return this;
    }

    public void setRole(PortalUserRole portalUserRole) {
        this.role = portalUserRole;
    }

    public PortalUserStatus getStatus() {
        return status;
    }

    public PortalUser status(PortalUserStatus portalUserStatus) {
        this.status = portalUserStatus;
        return this;
    }

    public void setStatus(PortalUserStatus portalUserStatus) {
        this.status = portalUserStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    
	// jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



    public PortalUserStatus getPortalUserStatus() {
		return portalUserStatus;
	}

	public void setPortalUserStatus(PortalUserStatus portalUserStatus) {
		this.portalUserStatus = portalUserStatus;
	}

	public String getPhone() {
        return phone;
    }

    public PortalUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public PortalUser position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public PortalUser researcher(Researcher researcher) {
        this.researcher = researcher;
        return this;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public Set<RiResearchOrganization> getManagedOrganizations() {
        return managedOrganizations;
    }

    public PortalUser managedOrganizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.managedOrganizations = riResearchOrganizations;
        return this;
    }

    public PortalUser addManagedOrganization(RiResearchOrganization riResearchOrganization) {
        this.managedOrganizations.add(riResearchOrganization);
        riResearchOrganization.setManager(this);
        return this;
    }

    public PortalUser removeManagedOrganization(RiResearchOrganization riResearchOrganization) {
        this.managedOrganizations.remove(riResearchOrganization);
        riResearchOrganization.setManager(null);
        return this;
    }

    public void setManagedOrganizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.managedOrganizations = riResearchOrganizations;
    }

    public Set<RiResearchOrganization> getContactOrganizations() {
        return contactOrganizations;
    }

    public PortalUser contactOrganizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.contactOrganizations = riResearchOrganizations;
        return this;
    }

    public PortalUser addContactOrganization(RiResearchOrganization riResearchOrganization) {
        this.contactOrganizations.add(riResearchOrganization);
        riResearchOrganization.setContactPerson(this);
        return this;
    }

    public PortalUser removeContactOrganization(RiResearchOrganization riResearchOrganization) {
        this.contactOrganizations.remove(riResearchOrganization);
        riResearchOrganization.setContactPerson(null);
        return this;
    }

    public void setContactOrganizations(Set<RiResearchOrganization> riResearchOrganizations) {
        this.contactOrganizations = riResearchOrganizations;
    }

    public Set<ResearchInfrastructure> getManagedInfrastructures() {
        return managedInfrastructures;
    }

    public PortalUser managedInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.managedInfrastructures = researchInfrastructures;
        return this;
    }

    public PortalUser addManagedInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.managedInfrastructures.add(researchInfrastructure);
        researchInfrastructure.setManager(this);
        return this;
    }

    public PortalUser removeManagedInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.managedInfrastructures.remove(researchInfrastructure);
        researchInfrastructure.setManager(null);
        return this;
    }

    public void setManagedInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.managedInfrastructures = researchInfrastructures;
    }

    public Set<ResearchInfrastructure> getContactInfrastructures() {
        return contactInfrastructures;
    }

    public PortalUser contactInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.contactInfrastructures = researchInfrastructures;
        return this;
    }

    public PortalUser addContactInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.contactInfrastructures.add(researchInfrastructure);
        researchInfrastructure.setContactPerson(this);
        return this;
    }

    public PortalUser removeContactInfrastructure(ResearchInfrastructure researchInfrastructure) {
        this.contactInfrastructures.remove(researchInfrastructure);
        researchInfrastructure.setContactPerson(null);
        return this;
    }

    public void setContactInfrastructures(Set<ResearchInfrastructure> researchInfrastructures) {
        this.contactInfrastructures = researchInfrastructures;
    }

    public Set<RequestForService> getRequests() {
        return requests;
    }

    public PortalUser requests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
        return this;
    }

    public PortalUser addRequests(RequestForService requestForService) {
        this.requests.add(requestForService);
        requestForService.setUsers(this);
        return this;
    }

    public PortalUser removeRequests(RequestForService requestForService) {
        this.requests.remove(requestForService);
        requestForService.setUsers(null);
        return this;
    }

    public void setRequests(Set<RequestForService> requestForServices) {
        this.requests = requestForServices;
    }

    public Set<RiCalendar> getCalendars() {
        return calendars;
    }

    public PortalUser calendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
        return this;
    }

    public PortalUser addCalendars(RiCalendar riCalendar) {
        this.calendars.add(riCalendar);
        riCalendar.setPortalUsers(this);
        return this;
    }

    public PortalUser removeCalendars(RiCalendar riCalendar) {
        this.calendars.remove(riCalendar);
        riCalendar.setPortalUsers(null);
        return this;
    }

    public void setCalendars(Set<RiCalendar> riCalendars) {
        this.calendars = riCalendars;
    }

    public Set<RiProcurementProposal> getRiProposals() {
        return riProposals;
    }

    public PortalUser riProposals(Set<RiProcurementProposal> riProcurementProposals) {
        this.riProposals = riProcurementProposals;
        return this;
    }

    public PortalUser addRiProposals(RiProcurementProposal riProcurementProposal) {
        this.riProposals.add(riProcurementProposal);
        riProcurementProposal.setPortalUser(this);
        return this;
    }

    public PortalUser removeRiProposals(RiProcurementProposal riProcurementProposal) {
        this.riProposals.remove(riProcurementProposal);
        riProcurementProposal.setPortalUser(null);
        return this;
    }

    public void setRiProposals(Set<RiProcurementProposal> riProcurementProposals) {
        this.riProposals = riProcurementProposals;
    }

    public Set<ServiceProposal> getServiceProposals() {
        return serviceProposals;
    }

    public PortalUser serviceProposals(Set<ServiceProposal> serviceProposals) {
        this.serviceProposals = serviceProposals;
        return this;
    }

    public PortalUser addServiceProposals(ServiceProposal serviceProposal) {
        this.serviceProposals.add(serviceProposal);
        serviceProposal.setPortalUser(this);
        return this;
    }

    public PortalUser removeServiceProposals(ServiceProposal serviceProposal) {
        this.serviceProposals.remove(serviceProposal);
        serviceProposal.setPortalUser(null);
        return this;
    }

    public void setServiceProposals(Set<ServiceProposal> serviceProposals) {
        this.serviceProposals = serviceProposals;
    }

    public Set<PortalUserRole> getPortalUserRoles() {
        return portalUserRoles;
    }
    
    public List<String> pronadjiPortalUserRolesAbbreviations() {
    	List<String> out = new ArrayList<String>();
    	for(PortalUserRole pur : this.portalUserRoles) {
    		out.add(pur.getAbbreviation());
    	}
        return out;
    }

    public PortalUser portalUserRoles(Set<PortalUserRole> portalUserRoles) {
        this.portalUserRoles = portalUserRoles;
        return this;
    }

    public PortalUser addPortalUserRoles(PortalUserRole portalUserRole) {
        this.portalUserRoles.add(portalUserRole);
        portalUserRole.getUsers().add(this);
        return this;
    }

    public PortalUser removePortalUserRoles(PortalUserRole portalUserRole) {
        this.portalUserRoles.remove(portalUserRole);
        portalUserRole.getUsers().remove(this);
        return this;
    }

    public void setPortalUserRoles(Set<PortalUserRole> portalUserRoles) {
        this.portalUserRoles = portalUserRoles;
    }

    public PortalUserOrganization getUserOrganization() {
        return userOrganization;
    }

    public PortalUser userOrganization(PortalUserOrganization portalUserOrganization) {
        this.userOrganization = portalUserOrganization;
        return this;
    }

    public void setUserOrganization(PortalUserOrganization portalUserOrganization) {
        this.userOrganization = portalUserOrganization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    
   

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortalUser)) {
            return false;
        }
        return id != null && id.equals(((PortalUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortalUser{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", position='" + getPosition() + "'" +
            "}";
    }
}
