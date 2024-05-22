package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e4i.domain.InfrastructureProposalsLikes;
import e4i.domain.ResearchInfrastructure;
import e4i.web.rest.dto.InfrastructureProposalsLikesDTO;

public interface InfrastructureProposalsLikesRepository extends JpaRepository<InfrastructureProposalsLikes, Long>{
	
	 @Query("select new e4i.web.rest.dto.InfrastructureProposalsLikesDTO(count(ipl.nlike), count(ipl.ndislike)) "
	 		+ " from InfrastructureProposalsLikes ipl "
	 		+ " where ipl.proposal.id = :proposalId ")
	 InfrastructureProposalsLikesDTO countLikes(@Param("proposalId") Long proposalId);
	 
	 
	 Optional<InfrastructureProposalsLikes> findByPortalUserIdAndProposalId(String portalUserId, Long proposalId);
	 
	 void deleteByProposalId(Long proposalId);

}
