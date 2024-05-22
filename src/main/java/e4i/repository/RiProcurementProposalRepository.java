package e4i.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ResearchInstitution;
import e4i.domain.RiProcurementProposal;
import e4i.web.rest.dto.RiProcurementProposalDTO;
import e4i.web.rest.dto.RiServiceDTOSearch;

/**
 * Spring Data  repository for the RiProcurementProposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiProcurementProposalRepository extends JpaRepository<RiProcurementProposal, Long> {
	
	List<RiProcurementProposal> findAllByPortalUserId(Long portalUserId);
	List<RiProcurementProposal> findByPortalUserIdNot(Long portalUserId);
	
	
	 @Query(nativeQuery = true)
	 Page<RiProcurementProposalDTO> pronadjiAllEditableForUser(@Param("pId") String pid, Pageable pageable);
	
	/*
	@Query(value="select new riportal.web.rest.dto.RiProcurementProposalDTO(rpp, true) from RiProcurementProposal rpp "
			+ "	where rpp.portalUser.id = :pUserId ")			
	Page<RiProcurementProposalDTO> pronadjiAllEditableForUser(@Param("pUserId") Long pUserId, Pageable pageable);
	*/
	@Query(value=" select rpp from RiProcurementProposal rpp "
			+ " where rpp.portalUser.id <> :pUserId or rpp.portalUser.id is null")			
	Page<RiProcurementProposal> pronadjiAllEditableWithoutUser(@Param("pUserId") Long pUserId, Pageable pageable);
	
	
	
	@Query(nativeQuery = true)
	Page<RiProcurementProposalDTO> findAllEditableForAdmin(Pageable pageable);

	
}
