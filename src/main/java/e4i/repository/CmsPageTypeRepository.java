package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsPageType;

/**
 * Spring Data  repository for the CmsPageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsPageTypeRepository extends JpaRepository<CmsPageType, Long> {
}
