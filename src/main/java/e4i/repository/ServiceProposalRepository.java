package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ServiceProposal;
import e4i.web.rest.dto.ServiceProposalDTO;

/**
 * Spring Data  repository for the ServiceProposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProposalRepository extends JpaRepository<ServiceProposal, Long> {
	
	 @Query(nativeQuery = true)
	 Page<ServiceProposalDTO> pronadjiAllEditableForUser(@Param("pId") String pid, Pageable pageable);
	 
	 @Query(nativeQuery = true)
		Page<ServiceProposalDTO> findAllEditableForAdmin(Pageable pageable);
}
