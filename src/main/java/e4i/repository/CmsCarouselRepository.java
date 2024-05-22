package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsCarousel;

/**
 * Spring Data  repository for the CmsCarousel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsCarouselRepository extends JpaRepository<CmsCarousel, Long> {
}
