package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ResearchInstitution;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ResearchInstitution entity.
 */
@Repository
public interface ResearchInstitutionRepository extends JpaRepository<ResearchInstitution, Long> {

    @Query(value = "select distinct researchInstitution from ResearchInstitution researchInstitution left join fetch researchInstitution.researchers",
        countQuery = "select count(distinct researchInstitution) from ResearchInstitution researchInstitution")
    Page<ResearchInstitution> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct researchInstitution from ResearchInstitution researchInstitution left join fetch researchInstitution.researchers")
    List<ResearchInstitution> findAllWithEagerRelationships();

    @Query("select researchInstitution from ResearchInstitution researchInstitution left join fetch researchInstitution.researchers where researchInstitution.id =:id")
    Optional<ResearchInstitution> findOneWithEagerRelationships(@Param("id") Long id);
    
    
    List<ResearchInstitution> findByOrderByNameAsc();
    List<ResearchInstitution> findByOrderByNameCyrAsc();
    List<ResearchInstitution> findByOrderByNameEnAsc();
    
   
}
