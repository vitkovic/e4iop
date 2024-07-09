package e4i.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "advertisement_supporter_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdvertisementSupporterStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String ACCEPTED = "Invitation accepted";
    public static final String REJECTED = "Invitation rejected";
    public static final String NO_RESPONSE = "No response";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "name_sr", nullable = false)
    private String nameSr;

    @Column(name = "name_src", nullable = false)
    private String nameSrc;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "is_visible")
    private Boolean isVisible;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @OneToMany(mappedBy = "status")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AdvertisementSupporter> advertisementSupporters = new HashSet<>();

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSr() {
        return nameSr;
    }
    
    public AdvertisementSupporterStatus nameSr(String nameSr) {
        this.nameSr = nameSr;
        return this;
    }

    public void setNameSr(String nameSr) {
        this.nameSr = nameSr;
    }

    public String getNameSrc() {
        return nameSrc;
    }

    public AdvertisementSupporterStatus nameSrc(String nameSrc) {
        this.nameSrc = nameSrc;
        return this;
    }

    public void setNameSrc(String nameSrc) {
        this.nameSrc = nameSrc;
    }

    public String getNameEn() {
        return nameEn;
    }

    public AdvertisementSupporterStatus nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }
    
    public AdvertisementSupporterStatus isVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }
    
    public AdvertisementSupporterStatus isDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<AdvertisementSupporter> getAdvertisementSupporters() {
        return advertisementSupporters;
    }

    public AdvertisementSupporterStatus advertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
        return this;
    }

    public AdvertisementSupporterStatus addAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.add(advertisementSupporter);
        advertisementSupporter.setStatus(this);
        return this;
    }

    public AdvertisementSupporterStatus removeAdvertisementSupporter(AdvertisementSupporter advertisementSupporter) {
        this.advertisementSupporters.remove(advertisementSupporter);
        advertisementSupporter.setStatus(null);
        return this;
    }    
    
    public void setAdvertisementSupporters(Set<AdvertisementSupporter> advertisementSupporters) {
        this.advertisementSupporters = advertisementSupporters;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvertisementSupporterStatus)) {
            return false;
        }
        return id != null && id.equals(((AdvertisementSupporterStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdvertisementSupporterStatus{" +
            "id=" + getId() +
            ", nameSr='" + getNameSr() + '\'' +
            ", nameSrc='" + getNameSrc() + '\'' +
            ", nameEn='" + getNameEn() + '\'' +
            ", isVisible=" + getIsVisible() +
            ", isDeleted=" + getIsDeleted() +
            '}';
    }
}
