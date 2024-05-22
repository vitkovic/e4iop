package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsSlider;

/**
 * Spring Data  repository for the CmsSlider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsSliderRepository extends JpaRepository<CmsSlider, Long> {
}
