package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSubcategory;

/**
 * Spring Data  repository for the AdvertisementSubcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSubcategoryRepository extends JpaRepository<AdvertisementSubcategory, Long> {
}
