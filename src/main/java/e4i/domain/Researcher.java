package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Researcher.
 */
@Entity
@Table(name = "researcher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Researcher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "first_name_lat")
    private String firstNameLat;

    @Column(name = "last_name_lat")
    private String lastNameLat;

    @Column(name = "middle_name_lat")
    private String middleNameLat;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToOne(mappedBy = "researcher")
    @JsonIgnore
    private PortalUser portalUser;

    @ManyToMany(mappedBy = "researchers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ResearchInstitution> institutions = new HashSet<>();
    
    
    
    public Researcher(Long id, String firstName, String middleName,String lastName,  LocalDate birthDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.birthDate = birthDate;
	}

	public Researcher() {
		super();
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Researcher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Researcher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Researcher middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Researcher birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PortalUser getPortalUser() {
        return portalUser;
    }

    public Researcher portalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
        return this;
    }

    public void setPortalUser(PortalUser portalUser) {
        this.portalUser = portalUser;
    }

    public Set<ResearchInstitution> getInstitutions() {
        return institutions;
    }

    public Researcher institutions(Set<ResearchInstitution> researchInstitutions) {
        this.institutions = researchInstitutions;
        return this;
    }

    public Researcher addInstitutions(ResearchInstitution researchInstitution) {
        this.institutions.add(researchInstitution);
        researchInstitution.getResearchers().add(this);
        return this;
    }

    public Researcher removeInstitutions(ResearchInstitution researchInstitution) {
        this.institutions.remove(researchInstitution);
        researchInstitution.getResearchers().remove(this);
        return this;
    }

    public void setInstitutions(Set<ResearchInstitution> researchInstitutions) {
        this.institutions = researchInstitutions;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getFirstNameLat() {
		return firstNameLat;
	}

	public void setFirstNameLat(String firstNameLat) {
		this.firstNameLat = firstNameLat;
	}

	public String getLastNameLat() {
		return lastNameLat;
	}

	public void setLastNameLat(String lastNameLat) {
		this.lastNameLat = lastNameLat;
	}

	public String getMiddleNameLat() {
		return middleNameLat;
	}

	public void setMiddleNameLat(String middleNameLat) {
		this.middleNameLat = middleNameLat;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Researcher)) {
            return false;
        }
        return id != null && id.equals(((Researcher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Researcher{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
