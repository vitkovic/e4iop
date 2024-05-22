package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementStatus;

/**
 * Spring Data  repository for the AdvertisementStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementStatusRepository extends JpaRepository<AdvertisementStatus, Long> {
	
	Optional<AdvertisementStatus> findByStatus(String status);
	
}
