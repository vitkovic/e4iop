package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Collaboration;
import e4i.domain.Thread;

/**
 * Spring Data  repository for the Collaboration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
	
	@Query("SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE ((collaboration.companyOffer.id = :companyId " +
	        "OR (collaboration.companyRequest.id = :companyId)) " + 
	        "AND (collaboration.status.id = :statusId))")
	Page<Collaboration> findAllByCompanyAndStatus(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
    
    @Query("SELECT collaboration FROM Collaboration collaboration " +
            "WHERE ((collaboration.companyOffer.id = :companyId) " +
			"AND (collaboration.status.id = :statusId))")
	Page<Collaboration> findAllByCompanyOfferAndStatus(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
    
    @Query("SELECT collaboration FROM Collaboration collaboration " +
            "WHERE ((collaboration.companyRequest.id = :companyId) " +
			"AND (collaboration.status.id = :statusId))")
	Page<Collaboration> findAllByCompanyRequestAndStatus(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
	
	@Deprecated
	@Query("SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE ((collaboration.companyOffer.id = :companyId " +
	        "OR (collaboration.companyRequest.id = :companyId)) " + 
	        "AND (collaboration.isAccepted = :isAccepted))")
	Page<Collaboration> findAllByCompanyAndIsAccepted(@Param("companyId") Long companyId, @Param("isAccepted") Boolean isAccepted, Pageable pageable);
    
	@Deprecated
    @Query("SELECT collaboration FROM Collaboration collaboration " +
            "WHERE ((collaboration.companyOffer.id = :companyId) " +
			"AND (collaboration.isAccepted = :isAccepted))")
	Page<Collaboration> findAllByCompanyOfferAndIsAccepted(@Param("companyId") Long companyId, @Param("isAccepted") Boolean isAccepted, Pageable pageable);
    
	@Deprecated
    @Query("SELECT collaboration FROM Collaboration collaboration " +
            "WHERE ((collaboration.companyRequest.id = :companyId) " +
			"AND (collaboration.isAccepted = :isAccepted))")
	Page<Collaboration> findAllByCompanyRequestAndIsAccepted(@Param("companyId") Long companyId, @Param("isAccepted") Boolean isAccepted, Pageable pageable);

    Optional<Collaboration> findOneByThreads(Thread thread);
    
    Long countByAdvertisementIdAndStatusStatus(Long advertisementId, String status);
    
    List<Collaboration> findAllByAdvertisementIdAndStatusStatus(Long advertisementId, String status);
}
