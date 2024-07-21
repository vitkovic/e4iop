package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementStatus;

/**
 * Spring Data  repository for the AdvertisementStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementStatusRepository extends JpaRepository<AdvertisementStatus, Long> {
	
	Optional<AdvertisementStatus> findByStatus(String status);
	
    @Query("SELECT ads FROM AdvertisementStatus ads WHERE ads.status = :status OR ads.statusSrc = :status OR ads.statusEn = :status")
    Optional<AdvertisementStatus> findByAnyStatus(@Param("status") String status);
	
}
