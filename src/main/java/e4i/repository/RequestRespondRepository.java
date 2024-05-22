package e4i.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.RequestRespond;

/**
 * Spring Data  repository for the RequestRespond entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRespondRepository extends JpaRepository<RequestRespond, Long> {
	
	Optional<RequestRespond> findByRequestForServiceId(Long requestForServiceId);

	@Query("select rr.id from RequestRespond rr "
			+ " left join rr.requestForService rs "
			+ " where rs.id in ( :requestForServiceIds )")
	List<Long> findByRequestForServiceIdIn(@Param("requestForServiceIds") List<Long> requestForServiceIds);

	
	 @Query("select r.id from RequestRespond r "
	 		+ " left join r.requestAnswer ra "
	 		+ " left join r.serviceOfferRespond sor "
	 		+ " left join sor.offerAnswer oa"
	    	+ " where r.id in ( :lista ) and"
	    	+ " ra.answerEn = 'Request accepted' and "
	    	+ " oa.answerEn = 'Offer accepted' "
	 		+ " and (1 = :dateFromNotExist or (sor.date >= :fromDate)) "
	 		+ " and (1 = :dateToNotExist or (sor.date <= :toDate)) ")
	 List<Long> findRealizedServices(@Param("lista") List<Long> lista, @Param("dateFromNotExist") Integer dateFromNotExist, 
	    		@Param("dateToNotExist") Integer dateToNotExist, @Param("fromDate") ZonedDateTime fromDate, 
	    		@Param("toDate") ZonedDateTime toDate);
	 
	 @Query("select r.id from RequestRespond r "
	 		+ " left join r.requestForService rf"
		 		+ " left join r.requestAnswer ra "
		 		+ " left join r.serviceOfferRespond sor"
		    		+ " where rf.id in ( :lista )"
		 			+ " and (1 = :dateFromNotExist or (r.date >= :fromDate)) "
		 			+ " and (1 = :dateToNotExist or (r.date <= :toDate)) ")
	 List<Long> findOfferedServices(@Param("lista") List<Long> lista, @Param("dateFromNotExist") Integer dateFromNotExist, 
	    		@Param("dateToNotExist") Integer dateToNotExist, @Param("fromDate") ZonedDateTime fromDate, 
	    		@Param("toDate") ZonedDateTime toDate);
}
