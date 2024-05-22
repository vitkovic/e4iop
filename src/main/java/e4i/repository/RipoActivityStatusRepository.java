package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RipoActivityStatus;

/**
 * Spring Data  repository for the RipoActivityStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RipoActivityStatusRepository extends JpaRepository<RipoActivityStatus, Long> {
}
