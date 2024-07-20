package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementType;

/**
 * Spring Data  repository for the AdvertisementType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementTypeRepository extends JpaRepository<AdvertisementType, Long> {

	Optional<AdvertisementType> findByType(String type);
	
	@Query("SELECT DISTINCT at FROM AdvertisementType at "
	       + "WHERE at.type = :type OR at.typeSrc = :type OR at.typeEn = :type")
	Optional<AdvertisementType> findByAnyType(@Param("type") String type);

	
}
