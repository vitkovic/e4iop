package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.PortalUserRole;

/**
 * Spring Data  repository for the PortalUserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortalUserRoleRepository extends JpaRepository<PortalUserRole, Long> {
}
