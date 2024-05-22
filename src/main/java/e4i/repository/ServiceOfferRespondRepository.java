package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceOfferRespond;

/**
 * Spring Data  repository for the ServiceOfferRespond entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOfferRespondRepository extends JpaRepository<ServiceOfferRespond, Long> {
	
	Optional<ServiceOfferRespond> findByRequestRespondId(Long requestRespondId);

}
