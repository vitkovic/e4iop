package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiAccessMode;

/**
 * Spring Data  repository for the RiAccessMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiAccessModeRepository extends JpaRepository<RiAccessMode, Long> {
}
