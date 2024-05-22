package e4i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ResearchInfrastructure;
import e4i.domain.ResearchInstitution;
import e4i.domain.Researcher;

/**
 * Spring Data  repository for the Researcher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
	
	@Query(value="select r from Researcher r JOIN r.institutions ris  "
			+ "where ris.id= :instid order by r.firstName")
	List<Researcher> findResearchersForInstitutionsAsc(@Param("instid") Long instid);
	 
	
	
	
}
