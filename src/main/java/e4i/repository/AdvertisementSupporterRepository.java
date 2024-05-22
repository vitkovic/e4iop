package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSupporter;

/**
 * Spring Data  repository for the AdvertisementSupporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSupporterRepository extends JpaRepository<AdvertisementSupporter, Long> {
}
