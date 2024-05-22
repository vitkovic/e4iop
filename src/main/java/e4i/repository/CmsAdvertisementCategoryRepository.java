package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsAdvertisementCategory;

/**
 * Spring Data  repository for the CmsAdvertisementCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsAdvertisementCategoryRepository extends JpaRepository<CmsAdvertisementCategory, Long> {
}
