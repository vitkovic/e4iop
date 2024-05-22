package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiAccessType;

/**
 * Spring Data  repository for the RiAccessType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiAccessTypeRepository extends JpaRepository<RiAccessType, Long> {
}
