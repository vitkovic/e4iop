package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSubsubcategory;

/**
 * Spring Data  repository for the AdvertisementSubsubcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSubsubcategoryRepository extends JpaRepository<AdvertisementSubsubcategory, Long> {
}
