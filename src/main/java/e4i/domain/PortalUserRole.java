package e4i.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PortalUserRole.
 */
@Entity
@Table(name = "portal_user_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortalUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "role")
    private String role;

    /**
     * PA, RPU, RPRIPO, RPRI, RPSI
     */
    @ApiModelProperty(value = "PA, RPU, RPRIPO, RPRI, RPSI")
    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany(mappedBy = "portalUserRoles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PortalUser> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public PortalUserRole role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public PortalUserRole abbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public PortalUserRole fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<PortalUser> getUsers() {
        return users;
    }

    public PortalUserRole users(Set<PortalUser> portalUsers) {
        this.users = portalUsers;
        return this;
    }

    public PortalUserRole addUsers(PortalUser portalUser) {
        this.users.add(portalUser);
        portalUser.getPortalUserRoles().add(this);
        return this;
    }

    public PortalUserRole removeUsers(PortalUser portalUser) {
        this.users.remove(portalUser);
        portalUser.getPortalUserRoles().remove(this);
        return this;
    }

    public void setUsers(Set<PortalUser> portalUsers) {
        this.users = portalUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortalUserRole)) {
            return false;
        }
        return id != null && id.equals(((PortalUserRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortalUserRole{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", fullName='" + getFullName() + "'" +
            "}";
    }
}
