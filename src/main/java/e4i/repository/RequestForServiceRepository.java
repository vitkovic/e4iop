package e4i.repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.PortalUser;
import e4i.domain.RequestForService;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiService;

/**
 * Spring Data  repository for the RequestForService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestForServiceRepository extends JpaRepository<RequestForService, Long> {	
	
    //Page<RequestForService> findByServicesIn(List<RiService> lista, Pageable pageable);
    
	@Query("select r from RequestForService r")
    Page<RequestForService> findAllNew(Pageable pageable);
    
    Page<RequestForService> findByServicesIn(List<RiService> lista, Pageable pageable);
    
    Page<RequestForService> findByUsersId(String usersId, Pageable pageable);
    
    List<RequestForService> findByServicesIdAndUsersId(Long servicesId, Long usersId);
    
    List<RequestForService> findByServicesIdAndUsersIdAndStateId(Long servicesId, String usersId, Long stateId);
    List<RequestForService> findByServicesIdAndStateId(Long servicesId,  Long stateId);
    
    @Query("select r.id from RequestForService r "
    		+ " where r.services.id in ( :lista ) "
    		+ " and (1 = :dateFromNotExist or (r.date >= :fromDate)) "
    		+ " and (1 = :dateToNotExist or (r.date <= :toDate)) ")
    List<Long> findByServicesIdsIn(@Param("lista") List<Long> lista, @Param("dateFromNotExist") Integer dateFromNotExist, 
    		@Param("dateToNotExist") Integer dateToNotExist, @Param("fromDate") ZonedDateTime fromDate, 
    		@Param("toDate") ZonedDateTime toDate );    
    
    
    @Query("select r.id from RequestForService r "
    		+ " where r.users.id in ( :lista )"
    		+ " and (1 = :dateFromNotExist or (r.date >= :fromDate)) "
    		+ " and (1 = :dateToNotExist or (r.date <= :toDate)) ")    		
    List<Long> findByUsersIdsIn(@Param("lista") List<Long> lista, @Param("dateFromNotExist") Integer dateFromNotExist, 
    		@Param("dateToNotExist") Integer dateToNotExist, @Param("fromDate") ZonedDateTime fromDate, 
    		@Param("toDate") ZonedDateTime toDate );   
    
    Long countByStateId(Long stateId);
    
    @Query("select count(r.id) from RequestForService r "
    		+ " right join r.respond rd "
    		+ " right join rd.serviceOfferRespond sor"
    		+ " where rd.requestAnswer.answerEn = 'Request accepted' "
    		+ " and sor.offerAnswer.answerEn = 'Offer accepted'")
    Long countRealizedRequests();
    
    Page<RequestForService> findByEndTimeBefore(ZonedDateTime date, Pageable pageable);
    
    Page<RequestForService> findByUsersIdAndEndTimeBefore(String usersId,ZonedDateTime date, Pageable pageable);
    

}
