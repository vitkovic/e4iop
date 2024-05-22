package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @OneToMany(mappedBy = "logo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Company> companyLogos = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Badge> badges = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsSlider> cmsSliders = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsCarousel> cmsCarouses = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsAdvertisementCategory> cmsAdvertisementCategories = new HashSet<>();

    @OneToMany(mappedBy = "image")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CmsTestimonials> cmsTestimonials = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
    private DocumentType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "documents", allowSetters = true)
    private DocumentKind kind;

    @ManyToMany(mappedBy = "documents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Company> companies = new HashSet<>();

    @ManyToMany(mappedBy = "documents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<CmsPage> cmsPages = new HashSet<>();

    @ManyToMany(mappedBy = "documents")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Advertisement> advertisements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public Document filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<Company> getCompanyLogos() {
        return companyLogos;
    }

    public Document companyLogos(Set<Company> companies) {
        this.companyLogos = companies;
        return this;
    }

    public Document addCompanyLogo(Company company) {
        this.companyLogos.add(company);
        company.setLogo(this);
        return this;
    }

    public Document removeCompanyLogo(Company company) {
        this.companyLogos.remove(company);
        company.setLogo(null);
        return this;
    }

    public void setCompanyLogos(Set<Company> companies) {
        this.companyLogos = companies;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public Document badges(Set<Badge> badges) {
        this.badges = badges;
        return this;
    }

    public Document addBadge(Badge badge) {
        this.badges.add(badge);
        badge.setImage(this);
        return this;
    }

    public Document removeBadge(Badge badge) {
        this.badges.remove(badge);
        badge.setImage(null);
        return this;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public Set<CmsSlider> getCmsSliders() {
        return cmsSliders;
    }

    public Document cmsSliders(Set<CmsSlider> cmsSliders) {
        this.cmsSliders = cmsSliders;
        return this;
    }

    public Document addCmsSlider(CmsSlider cmsSlider) {
        this.cmsSliders.add(cmsSlider);
        cmsSlider.setImage(this);
        return this;
    }

    public Document removeCmsSlider(CmsSlider cmsSlider) {
        this.cmsSliders.remove(cmsSlider);
        cmsSlider.setImage(null);
        return this;
    }

    public void setCmsSliders(Set<CmsSlider> cmsSliders) {
        this.cmsSliders = cmsSliders;
    }

    public Set<CmsCarousel> getCmsCarouses() {
        return cmsCarouses;
    }

    public Document cmsCarouses(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouses = cmsCarousels;
        return this;
    }

    public Document addCmsCarouse(CmsCarousel cmsCarousel) {
        this.cmsCarouses.add(cmsCarousel);
        cmsCarousel.setImage(this);
        return this;
    }

    public Document removeCmsCarouse(CmsCarousel cmsCarousel) {
        this.cmsCarouses.remove(cmsCarousel);
        cmsCarousel.setImage(null);
        return this;
    }

    public void setCmsCarouses(Set<CmsCarousel> cmsCarousels) {
        this.cmsCarouses = cmsCarousels;
    }

    public Set<CmsAdvertisementCategory> getCmsAdvertisementCategories() {
        return cmsAdvertisementCategories;
    }

    public Document cmsAdvertisementCategories(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategories = cmsAdvertisementCategories;
        return this;
    }

    public Document addCmsAdvertisementCategory(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategories.add(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setImage(this);
        return this;
    }

    public Document removeCmsAdvertisementCategory(CmsAdvertisementCategory cmsAdvertisementCategory) {
        this.cmsAdvertisementCategories.remove(cmsAdvertisementCategory);
        cmsAdvertisementCategory.setImage(null);
        return this;
    }

    public void setCmsAdvertisementCategories(Set<CmsAdvertisementCategory> cmsAdvertisementCategories) {
        this.cmsAdvertisementCategories = cmsAdvertisementCategories;
    }

    public Set<CmsTestimonials> getCmsTestimonials() {
        return cmsTestimonials;
    }

    public Document cmsTestimonials(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonials = cmsTestimonials;
        return this;
    }

    public Document addCmsTestimonials(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonials.add(cmsTestimonials);
        cmsTestimonials.setImage(this);
        return this;
    }

    public Document removeCmsTestimonials(CmsTestimonials cmsTestimonials) {
        this.cmsTestimonials.remove(cmsTestimonials);
        cmsTestimonials.setImage(null);
        return this;
    }

    public void setCmsTestimonials(Set<CmsTestimonials> cmsTestimonials) {
        this.cmsTestimonials = cmsTestimonials;
    }

    public DocumentType getType() {
        return type;
    }

    public Document type(DocumentType documentType) {
        this.type = documentType;
        return this;
    }

    public void setType(DocumentType documentType) {
        this.type = documentType;
    }

    public DocumentKind getKind() {
        return kind;
    }

    public Document kind(DocumentKind documentKind) {
        this.kind = documentKind;
        return this;
    }

    public void setKind(DocumentKind documentKind) {
        this.kind = documentKind;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Document companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Document addCompany(Company company) {
        this.companies.add(company);
        company.getDocuments().add(this);
        return this;
    }

    public Document removeCompany(Company company) {
        this.companies.remove(company);
        company.getDocuments().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<CmsPage> getCmsPages() {
        return cmsPages;
    }

    public Document cmsPages(Set<CmsPage> cmsPages) {
        this.cmsPages = cmsPages;
        return this;
    }

    public Document addCmsPage(CmsPage cmsPage) {
        this.cmsPages.add(cmsPage);
        cmsPage.getDocuments().add(this);
        return this;
    }

    public Document removeCmsPage(CmsPage cmsPage) {
        this.cmsPages.remove(cmsPage);
        cmsPage.getDocuments().remove(this);
        return this;
    }

    public void setCmsPages(Set<CmsPage> cmsPages) {
        this.cmsPages = cmsPages;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public Document advertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
        return this;
    }

    public Document addAdvertisement(Advertisement advertisement) {
        this.advertisements.add(advertisement);
        advertisement.getDocuments().add(this);
        return this;
    }

    public Document removeAdvertisement(Advertisement advertisement) {
        this.advertisements.remove(advertisement);
        advertisement.getDocuments().remove(this);
        return this;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            "}";
    }
}
