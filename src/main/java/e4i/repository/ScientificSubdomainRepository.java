package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ScientificSubdomain;

/**
 * Spring Data  repository for the ScientificSubdomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificSubdomainRepository extends JpaRepository<ScientificSubdomain, Long> {
}
