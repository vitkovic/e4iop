package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementKind;

/**
 * Spring Data  repository for the AdvertisementKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementKindRepository extends JpaRepository<AdvertisementKind, Long> {
}
