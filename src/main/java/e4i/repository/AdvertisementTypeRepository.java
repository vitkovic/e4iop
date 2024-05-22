package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementType;

/**
 * Spring Data  repository for the AdvertisementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementTypeRepository extends JpaRepository<AdvertisementType, Long> {
}
