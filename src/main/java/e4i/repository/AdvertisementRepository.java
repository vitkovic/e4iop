package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Advertisement;
import e4i.domain.Thread;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Advertisement entity.
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query(value = "select distinct advertisement from Advertisement advertisement left join fetch advertisement.documents",
        countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct advertisement from Advertisement advertisement left join fetch advertisement.documents")
    List<Advertisement> findAllWithEagerRelationships();

    @Query("select advertisement from Advertisement advertisement left join fetch advertisement.documents where advertisement.id =:id")
    Optional<Advertisement> findOneWithEagerRelationships(@Param("id") Long id);
    
    Page<Advertisement> findByCompanyId(Long companyId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement where advertisement.status.status = :status",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByStatus(@Param("status") String status, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement where advertisement.status.id = :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByStatusId(@Param("statusId") Long statusId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement where advertisement.company.id = :companyId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyId(@Param("companyId") Long companyId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.status.status = :status",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandStatus(@Param("companyId") Long companyId, @Param("status") String status, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.status.id = :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandStatusId(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);

    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.status.id != :statusId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandNotStatusId(@Param("companyId") Long companyId, @Param("statusId") Long statusId, Pageable pageable);
    
    @Query(value = "select distinct advertisement from Advertisement advertisement "
    		+ "where advertisement.company.id = :companyId "
    		+ "and advertisement.type.id = :typeId",
            countQuery = "select count(distinct advertisement) from Advertisement advertisement")
    Page<Advertisement> findAllByCompanyIdandTypeId(@Param("companyId") Long companyId, @Param("typeId") Long typeId, Pageable pageable);

	Optional<Advertisement> findOneByThreads(Thread thread);
}
