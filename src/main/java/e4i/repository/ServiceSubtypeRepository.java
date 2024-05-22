package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceSubtype;

/**
 * Spring Data  repository for the ServiceSubtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSubtypeRepository extends JpaRepository<ServiceSubtype, Long> {
}
