package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiSubcategory;

/**
 * Spring Data  repository for the RiSubcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiSubcategoryRepository extends JpaRepository<RiSubcategory, Long> {
}
