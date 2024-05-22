package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsTestimonials;

/**
 * Spring Data  repository for the CmsTestimonials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsTestimonialsRepository extends JpaRepository<CmsTestimonials, Long> {
}
