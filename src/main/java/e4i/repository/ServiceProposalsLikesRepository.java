package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e4i.domain.InfrastructureProposalsLikes;
import e4i.domain.ServiceProposalsLikes;
import e4i.web.rest.dto.InfrastructureProposalsLikesDTO;
import e4i.web.rest.dto.ServiceProposalsLikesDTO;

public interface ServiceProposalsLikesRepository extends JpaRepository<ServiceProposalsLikes, Long>{
	
	 @Query("select new e4i.web.rest.dto.ServiceProposalsLikesDTO(count(ipl.nlike), count(ipl.ndislike)) "
	 		+ " from ServiceProposalsLikes ipl "
	 		+ " where ipl.proposal.id = :proposalId ")
	 ServiceProposalsLikesDTO countLikes(@Param("proposalId") Long proposalId);
	 
	 
	 Optional<ServiceProposalsLikes> findByPortalUserIdAndProposalId(String portalUserId, Long proposalId);

	 void deleteByProposalId(Long proposalId);

}
