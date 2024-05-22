package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.RiResearchOrganization;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RiResearchOrganization entity.
 */
@Repository
public interface RiResearchOrganizationRepository extends JpaRepository<RiResearchOrganization, Long> {

    @Query(value = "select distinct riResearchOrganization from RiResearchOrganization riResearchOrganization left join fetch riResearchOrganization.subdomains",
        countQuery = "select count(distinct riResearchOrganization) from RiResearchOrganization riResearchOrganization")
    Page<RiResearchOrganization> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct riResearchOrganization from RiResearchOrganization riResearchOrganization left join fetch riResearchOrganization.subdomains")
    List<RiResearchOrganization> findAllWithEagerRelationships();

    @Query("select riResearchOrganization from RiResearchOrganization riResearchOrganization left join fetch riResearchOrganization.subdomains where riResearchOrganization.id =:id")
    Optional<RiResearchOrganization> findOneWithEagerRelationships(@Param("id") Long id);
    
    RiResearchOrganization findByPuOrganizationId(Long puOrganizationId);
    
    @Query(nativeQuery = true)
    List<RiResearchOrganizationReportDTO> findForSearchSr(@Param("name") String name, 
    		@Param("keywordsNotRequested") Integer keywordsNotRequested, 
    		@Param("nioNotRequested") Integer nioNotRequested, @Param("firmNotRequested") Integer firmNotRequested);
    
    @Query("SELECT new e4i.web.rest.dto.RiResearchOrganizationReportDTO(r) FROM RiResearchOrganization r "    		
    		+ "  join r.puOrganization puo "
    		+ "  join puo.nioInstitution ri "
    		+ " WHERE ri.nameEn LIKE %:name%  "
    		+ " or puo.legalNameEn LIKE %:name% ")
    List<RiResearchOrganizationReportDTO> findForSearchEn(@Param("name") String name);
    
    @Query(nativeQuery = true)
    List<RiResearchOrganizationReportDTO> findAllForReport();
    
    Page<RiResearchOrganization> findByManagerId(String managerId, Pageable pageable);
    
    List<RiResearchOrganization> findByManagerId(String managerId);
    
    
    
}
