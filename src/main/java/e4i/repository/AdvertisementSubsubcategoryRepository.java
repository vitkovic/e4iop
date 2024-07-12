package e4i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSubsubcategory;

/**
 * Spring Data  repository for the AdvertisementSubsubcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSubsubcategoryRepository extends JpaRepository<AdvertisementSubsubcategory, Long> {
	
    @Query("SELECT ssc FROM AdvertisementSubsubcategory ssc " +
            "JOIN ssc.advertisementSubcategory sc " +
            "JOIN sc.advertisementCategory c " +
            "ORDER BY c.name, sc.name, ssc.name")
     List<AdvertisementSubsubcategory> findAllOrderByCategorySubcategorySubsubcategoryName();
}
