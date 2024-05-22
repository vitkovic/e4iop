package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiServiceStatus;

/**
 * Spring Data  repository for the RiServiceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiServiceStatusRepository extends JpaRepository<RiServiceStatus, Long> {
}
