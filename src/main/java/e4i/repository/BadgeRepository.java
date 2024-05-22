package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.Badge;

/**
 * Spring Data  repository for the Badge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
