package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.PortalUserOrganization;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;

/**
 * Spring Data  repository for the PortalUserOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortalUserOrganizationRepository extends JpaRepository<PortalUserOrganization, Long> {
	
	Optional<PortalUserOrganization> findByRegistrationNumber(String registrationNumber);
	
	Optional<PortalUserOrganization> findByNioInstitutionId(Long instid);
	
	Optional<PortalUserOrganization> findByPib(String pib);
	
	
	@Query(nativeQuery = true)
    List<RiResearchOrganizationReportDTO> findForSearchSr(@Param("name") String name, 
    		@Param("keywordsNotRequested") Integer keywordsNotRequested, 
    		@Param("nioNotRequested") Integer nioNotRequested, @Param("firmNotRequested") Integer firmNotRequested);
    
    @Query(nativeQuery = true)
    List<RiResearchOrganizationReportDTO> findAllForReport();
    
    @Query("select p from PortalUserOrganization p where p.id = :id ")
    Page<PortalUserOrganization> findPage(@Param("id") Long id, Pageable pageable);
    
    @Query(value="select * from portal_user_organisation where registration_number=?",nativeQuery = true)
	 List<PortalUserOrganization> findByRegistrationNumberList(String name);
    
    @Query(value="select * from portal_user_organisation where pib=?",nativeQuery = true)
	 List<PortalUserOrganization> findByPibList(String name);
    
    
    List<PortalUserOrganization> findByLegalNameSrContainingIgnoreCase(String name);
    List<PortalUserOrganization> findByLegalNameSrCyrContainingIgnoreCase(String name);
    List<PortalUserOrganization> findByLegalNameEnContainingIgnoreCase(String name);
}
