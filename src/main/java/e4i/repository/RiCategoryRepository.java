package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiCategory;

/**
 * Spring Data  repository for the RiCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiCategoryRepository extends JpaRepository<RiCategory, Long> {
}
