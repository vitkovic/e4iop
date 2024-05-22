package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceAccreditation;

/**
 * Spring Data  repository for the ServiceAccreditation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAccreditationRepository extends JpaRepository<ServiceAccreditation, Long> {
}
