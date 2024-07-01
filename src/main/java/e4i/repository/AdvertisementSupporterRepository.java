package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementSupporter;

/**
 * Spring Data  repository for the AdvertisementSupporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementSupporterRepository extends JpaRepository<AdvertisementSupporter, Long> {

	Optional<AdvertisementSupporter> findByAdvertisementIdAndCompanyId(Long advertisementId, Long companyId);

	List<AdvertisementSupporter> findAllByAdvertisementId(Long advertisementId);
}
