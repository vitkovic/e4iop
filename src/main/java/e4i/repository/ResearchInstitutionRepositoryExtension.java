package e4i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e4i.domain.ResearchInstitution;

public interface ResearchInstitutionRepositoryExtension extends JpaRepository<ResearchInstitution, Long>{
	
	 @Query(value="select * from research_institution where upper(name) like ?%",nativeQuery = true)
	 List<ResearchInstitution> findResearchInstitutionByName(String name);
	 
	 

}
