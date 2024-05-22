package e4i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import e4i.domain.ResearchInfrastructure;

public interface ResearchInfrastructureRepositoryExtension extends JpaRepository<ResearchInfrastructure, Long>{
	 
	 @Query(value="select * from research_infrastructure where upper(name_sr) like ?%",nativeQuery = true)
	 List<ResearchInfrastructure> findResearchInfrastructureByNameSr(String name);
	 
	 @Query(value="select * from research_infrastructure where upper(name_en) like ?%",nativeQuery = true)
	 List<ResearchInfrastructure> findResearchInfrastructureByNameEn(String name);
}
