package e4i.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e4i.domain.InfrastructureImage;
import e4i.domain.ResearchInfrastructure;


@SuppressWarnings("unused")
@Repository
public interface InfrastructureImageRepository extends JpaRepository<InfrastructureImage, Long>{
	
	Set<InfrastructureImage> findByResearchInfrastructure(ResearchInfrastructure ri);

}
