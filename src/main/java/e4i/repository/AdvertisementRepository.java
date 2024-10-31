package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Advertisement;
import e4i.domain.Thread;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Advertisement entity.
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
     int status_id_active = 3551;
	
	 @Query("select distinct advertisement from Advertisement advertisement "
	  		+ " where activation_datetime >= TO_TIMESTAMP(:date, 'YYYY-MM-DD HH24:MI:SS') and advertisement.status.id =" +  status_id_active)
	 List<Advertisement> findAllByActivatedLater(@Param("date") String date);
	
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.documents "
    		+ "left join fetch advertisement.kinds",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.documents "
    		+ "left join fetch advertisement.kinds")
    List<Advertisement> findAllWithEagerRelationships();

    @Query("select advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.documents "
    		+ "left join fetch advertisement.kinds "
    		+ "where advertisement.id =:id")
    Optional<Advertisement> findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.subsubcategory",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllAdvertisements(Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.company.id = :companyId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findByCompanyId(Long companyId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement where advertisement.status.status = :status",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByStatus(@Param("status") String status, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.status.id = :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByStatusId(@Param("statusId") Long statusId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement where advertisement.company.id = :companyId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyId(@Param("companyId") Long companyId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (advertisement.status.status = :status or advertisement.status.statusSrc = :status or advertisement.status.statusEn = :status)",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandAnyStatus(@Param("companyId") Long companyId, @Param("status") String status, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join advertisement.kinds "
    		+ "left join fetch advertisement.status "
    		+ "left join fetch advertisement.type "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.status.id = :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandStatusId(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);

    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join advertisement.kinds "
    		+ "left join fetch advertisement.status "
    		+ "left join fetch advertisement.type "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.status.id != :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandNotStatusId(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "left join fetch advertisement.subsubcategory "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.type.id = :typeId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandTypeId(@Param("companyId") Long companyId, @Param("typeId") Long typeId, Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')",
    	 countQuery = "select count(distinct advertisement) from Advertisement advertisement "
   		+ "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
   		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')")
    Page<Advertisement> findAllBySearchAdmin(@Param("search") String search, Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%'))",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findSearchAllByCompanyId(@Param("search") String search,@Param("companyId") Long companyId, Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.category.id=:category",
    		countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    				+     		 "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    				+     		 "or upper(advertisement.title) like CONCAT('%',upper(:search),'%') and advertisement.category.id=:category")
    Page<Advertisement> findAllBySearchAdminbyCategory(@Param("search") String search, @Param("category") Long category,  Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.category.id=:category",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.category.id=:category")
    Page<Advertisement> findSearchAllByCompanyIdbyCategory(@Param("search") String search,@Param("companyId") Long companyId,@Param("category") Long category, Pageable pageable);

    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.category.id=:category and advertisement.status.id = :status",
    		countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    				+     		 "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    				+     		 "or upper(advertisement.title) like CONCAT('%',upper(:search),'%') and advertisement.category.id=:category and advertisement.status.id = :status")
    Page<Advertisement> findAllBySearchAdminbyCategoryStatus(@Param("search") String search,@Param("status") Long status, @Param("category") Long category,  Pageable pageable);
   
   
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.subcategory.id=:category",
    		countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    				+     		 "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    				+     		 "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.subsubcategory.id=:category")
    Page<Advertisement> findAllBySearchAdminbyCategorySub(@Param("search") String search, @Param("category") Long category,  Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.subcategory.id=:category and advertisement.status.id = :status",
    		countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    				+     		 "where (upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    				+     		 "or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.subsubcategory.id=:category and advertisement.status.id = :status")
    Page<Advertisement> findAllBySearchAdminbyCategoryStatusSub(@Param("search") String search,@Param("status") Long status, @Param("category") Long category,  Pageable pageable);
   
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) "
    		+ "and advertisement.category.id=:category and advertisement.status.id = :status",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%')) and advertisement.category.id=:category and advertisement.status.id = :status")
    Page<Advertisement> findSearchAllByCompanyIdbyCategoryStatus(@Param("search") String search,@Param("status") Long status,@Param("companyId") Long companyId,@Param("category") Long category, Pageable pageable);

    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%') and advertisement.status.id = :status",
    		 countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    		+ "where upper(advertisement.description) like CONCAT('%',upper(:search),'%') "
    		+ "or upper(advertisement.title) like CONCAT('%',upper(:search),'%') and advertisement.status.id = :status")
    Page<Advertisement> findAllBySearchAdminStatus(@Param("search") String search,@Param("status") Long status, Pageable pageable);
    
    
   
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId and advertisement.status.id = :status  "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%'))",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId and advertisement.status.id = :status  "
    		+ "and (upper(advertisement.description) like CONCAT('%',upper(:search),'%') or upper(advertisement.title) like CONCAT('%',upper(:search),'%'))")
    Page<Advertisement> findSearchAllByCompanyIdStatus(@Param("search") String search,@Param("status") Long status, @Param("companyId") Long companyId, Pageable pageable);
    
      
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.type.id=:type",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.type.id=:type")
    Page<Advertisement> findAllBySearchAdminType(@Param("type") Long type, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.company.id=:company",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.company.id=:company")
    Page<Advertisement> findAllBySearchAdminCompany(@Param("company") Long company, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.kind.id=:kind",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.kind.id=:kind")
    Page<Advertisement> findAllBySearchAdminKind(@Param("kind") Long kind, Pageable pageable);
    
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
	  		+ " where activation_datetime >= TO_TIMESTAMP(:from, 'YYYY-MM-DD T24:MI:SS') and activation_datetime <= TO_TIMESTAMP(:to, 'YYYY-MM-DD T24:MI:SS')"
	  		+ " and advertisement.status.id =" +  status_id_active,
	  		 countQuery = "select distinct advertisement from Advertisement advertisement "
	  		+ " where activation_datetime >= TO_TIMESTAMP(:from, 'YYYY-MM-DD T24:MI:SS') and activation_datetime <= TO_TIMESTAMP(:to, 'YYYY-MM-DD T24:MI:SS')"
	  		+ " and advertisement.status.id =" +  status_id_active)
	 Page<Advertisement> findAllBySearchAdminDates(@Param("from") String from, @Param("to") String to,Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.type.id=:type and advertisement.company.id = :companyId ",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.type.id=:type and advertisement.company.id = :companyId ")
    Page<Advertisement> findSearchAllByCompanyIdbyType(@Param("type") Long type, @Param("companyId") Long companyId, Pageable pageable);
    
 
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.type.id=:type and advertisement.status.id = :status ",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.type.id=:type and advertisement.status.id = :status ")
    Page<Advertisement> findAllBySearchAdminTypeStatus(@Param("type") Long type,@Param("status") Long status, Pageable pageable);
    
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ " where advertisement.type.id=:type and advertisement.company.id = :companyId and advertisement.status.id = :status ",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement "
        		+ " where advertisement.type.id=:type and advertisement.company.id = :companyId and advertisement.status.id = :status ")
    Page<Advertisement> findSearchAllByCompanyIdbyTypeStatus(@Param("type") Long type, @Param("companyId") Long companyId, @Param("status") Long status,Pageable pageable);
    
    
	Optional<Advertisement> findOneByThreads(Thread thread);
	
	@Query("SELECT COUNT(a) FROM Advertisement a JOIN a.status s "
			+ "WHERE a.company.id = :companyId "
			+ "AND (s.status != :status AND s.statusSrc != :status AND s.statusEn != :status)")
	Long countByCompanyIdAndNotStatus(@Param("companyId") Long companyId, @Param("status") String status);

	@Query("SELECT COUNT(a) FROM Advertisement a JOIN a.status s "
	       + "WHERE a.company.id = :companyId "
	       + "AND (s.status = :status OR s.statusSrc = :status OR s.statusEn = :status)")
	Long countByCompanyIdAndStatus(@Param("companyId") Long companyId, @Param("status") String status);

	
    @Query(value = "SELECT DISTINCT advertisement FROM Advertisement advertisement "
    		+ "WHERE advertisement.company.id = :companyId "
    		+ "AND (advertisement.status.status = :status OR advertisement.status.statusSrc = :status OR advertisement.status.statusEn = :status) "
    		+ "AND (advertisement.type.type = :type OR advertisement.type.typeSrc = :type OR advertisement.type.typeEn = :type)",
        countQuery = "SELECT COUNT(DISTINCT advertisement) FROM Advertisement advertisement "
        		+ "WHERE advertisement.company.id = :companyId "
        		+ "AND (advertisement.status.status = :status OR advertisement.status.statusSrc = :status OR advertisement.status.statusEn = :status) "
        		+ "AND (advertisement.type.type = :type OR advertisement.type.typeSrc = :type OR advertisement.type.typeEn = :type)")
	Page<Advertisement> findAllByCompanyIdandStatusAndType(@Param("companyId") Long companyId,@Param("status") String status, @Param("type") String type, Pageable pageable);
    
    @Query("SELECT a FROM Advertisement a WHERE a.type.id = 3451 AND a.status.id = 3551")
    	Page<Advertisement> findTop6ByTypeOfferOrderByActivationDatetimeDesc(Pageable pageable);
    
    @Query("SELECT a FROM Advertisement a WHERE a.type.id = 3452 AND a.status.id = 3551")
	Page<Advertisement> findTop6ByTypeDemandOrderByActivationDatetimeDesc(Pageable pageable);

    
    
    
    
    
    
    
}
