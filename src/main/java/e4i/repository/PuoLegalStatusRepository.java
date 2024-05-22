package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.PuoLegalStatus;

/**
 * Spring Data  repository for the PuoLegalStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuoLegalStatusRepository extends JpaRepository<PuoLegalStatus, Long> {
}
