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
	       "WHERE ((:companyOfferFlag = true AND collaboration.companyOffer.id = :companyId) " +
	       		"OR (:companyRequestFlag = true AND collaboration.companyRequest.id = :companyId)) " +
	       "AND ((:ratingGivenFlag = true AND collaboration.companyOffer.id = :companyId AND collaboration.ratingOffer IS NOT NULL) " + 
	       		"OR (:ratingGivenFlag = true AND collaboration.companyRequest.id = :companyId AND collaboration.ratingRequest IS NOT NULL) " + 
	       		"OR (:ratingReceivedFlag = true AND collaboration.companyOffer.id = :companyId AND collaboration.ratingRequest IS NOT NULL) " + 
	       		"OR (:ratingReceivedFlag = true AND collaboration.companyRequest.id = :companyId AND collaboration.ratingOffer IS NOT NULL) " + 
	       		"OR (:ratingGivenFlag = false AND :ratingReceivedFlag = false))" +
	       "AND collaboration.status.id IN :statusIds")
	Page<Collaboration> findAllFilteredForCompany(@Param("companyId") Long companyId,
														  @Param("statusIds") List<Long> statusIds,
					                                      @Param("companyOfferFlag") boolean companyOfferFlag,
					                                      @Param("companyRequestFlag") boolean companyRequestFlag,
					                                      @Param("ratingGivenFlag") boolean ratingGivenFlag,
					                                      @Param("ratingReceivedFlag") boolean ratingReceivedFlag,
					                                      Pageable pageable);
	
	@Query("SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE ((collaboration.companyOffer.id = :companyId " +
	        "OR (collaboration.companyRequest.id = :companyId)) " + 
	        "AND (collaboration.status.id = :statusId))")
	Page<Collaboration> findAllByCompanyAndStatus(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
    
	
	@Query(value = "SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE collaboration.status.id = :statusId",
	        countQuery ="SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE collaboration.status.id = :statusId" )
	Page<Collaboration> findAllByStatusQ(@Param("statusId") Long status, Pageable pageable);
	
	
	@Query(value = "SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE collaboration.status.id = :statusId and (datetime >= TO_TIMESTAMP(:from, 'YYYY-MM-DD T24:MI:SS') and datetime <= TO_TIMESTAMP(:to, 'YYYY-MM-DD T24:MI:SS'))",
	        countQuery ="SELECT collaboration FROM Collaboration collaboration " +
	        "WHERE collaboration.status.id = :statusId and (datetime >= TO_TIMESTAMP(:from, 'YYYY-MM-DD T24:MI:SS') and datetime <= TO_TIMESTAMP(:to, 'YYYY-MM-DD T24:MI:SS'))" )
	Page<Collaboration> findAllByStatusQandDates(@Param("from") String from, @Param("to") String to, @Param("statusId") Long status, Pageable pageable);
	
	
	
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
    
    List<Collaboration> findByCompanyOfferIdOrCompanyRequestIdAndStatusStatusEn(Long companyOfferId, Long companyRequestId, String status);
    

    @Query("SELECT collaboration FROM Collaboration collaboration " +
    	       "WHERE (collaboration.companyOffer.id = :companyId AND collaboration.ratingRequest IS NOT NULL " +
    	       "OR collaboration.companyRequest.id = :companyId AND collaboration.ratingOffer IS NOT NULL) " +
    	       "AND (collaboration.status.status = :status OR collaboration.status.statusSrc = :status OR collaboration.status.statusEn = :status) " +
    	       "ORDER BY collaboration.datetime DESC")
	List<Collaboration> findAllByCompanyAndRatingAndStatus(@Param("companyId") Long companyId, @Param("status") String status);
    
    @Query("SELECT collaboration FROM Collaboration collaboration " +
    	       "WHERE (collaboration.companyOffer.id = :companyId AND collaboration.ratingRequest IS NOT NULL " +
    	       "OR collaboration.companyRequest.id = :companyId AND collaboration.ratingOffer IS NOT NULL) " +
    	       "AND (collaboration.status.status = :status OR collaboration.status.statusSrc = :status OR collaboration.status.statusEn = :status) " +
    	       "ORDER BY collaboration.datetime DESC")
    Page<Collaboration> findPageByCompanyAndRatingAndStatus(@Param("companyId") Long companyId, @Param("status") String status, Pageable pageable);
}
