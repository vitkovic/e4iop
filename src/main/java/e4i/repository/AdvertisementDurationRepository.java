package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementDuration;

/**
 * Spring Data  repository for the AdvertisementDuration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementDurationRepository extends JpaRepository<AdvertisementDuration, Long> {
}
