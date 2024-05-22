package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiStatus;

/**
 * Spring Data  repository for the RiStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiStatusRepository extends JpaRepository<RiStatus, Long> {
}
