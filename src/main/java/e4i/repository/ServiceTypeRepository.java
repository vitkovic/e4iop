package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceType;

/**
 * Spring Data  repository for the ServiceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
