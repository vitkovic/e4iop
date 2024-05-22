package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CollaborationRating;

/**
 * Spring Data  repository for the CollaborationRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborationRatingRepository extends JpaRepository<CollaborationRating, Long> {
}
