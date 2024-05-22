package e4i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.ScientificDomain;

/**
 * Spring Data  repository for the ScientificDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScientificDomainRepository extends JpaRepository<ScientificDomain, Long> {
	
	 @Query(value = "SELECT distinct domain FROM ScientificDomain domain JOIN FETCH domain.subdomains order by domain.id")
	 List<ScientificDomain> fetchAllDomainsByJoinFetch();
	 
	
}
