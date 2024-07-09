package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSupporterStatus;

/**
 * Spring Data repository for the AdvertisementSupporterStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSupporterStatusRepository extends JpaRepository<AdvertisementSupporterStatus, Long> {
	
	Optional<AdvertisementSupporterStatus> findByNameSr(String nameSr);
	Optional<AdvertisementSupporterStatus> findByNameSrc(String nameSrc);
	Optional<AdvertisementSupporterStatus> findByNameEn(String nameEn);
	
    @Query("SELECT ass FROM AdvertisementSupporterStatus ass WHERE ass.nameSr = :name OR ass.nameSrc = :name OR ass.nameEn = :name")
    Optional<AdvertisementSupporterStatus> findByNameInAnyLanguage(@Param("name") String name);
}
