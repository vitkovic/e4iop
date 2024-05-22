package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceAvailability;

/**
 * Spring Data  repository for the ServiceAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceAvailabilityRepository extends JpaRepository<ServiceAvailability, Long> {
}
