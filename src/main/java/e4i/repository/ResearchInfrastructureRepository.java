package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ResearchInfrastructure;
import e4i.web.rest.dto.InfrastructureAccessModeDTO;
import e4i.web.rest.dto.InfrastructureAccessTypeDTO;
import e4i.web.rest.dto.InfrastructureCategoryDTO;
import e4i.web.rest.dto.InfrastructureStatusDTO;
import e4i.web.rest.dto.ResearchInfrastructureDTOSearch;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.RiSubcategoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ResearchInfrastructure entity.
 */
@Repository
public interface ResearchInfrastructureRepository extends JpaRepository<ResearchInfrastructure, Long> {

    @Query(value = "select distinct researchInfrastructure from ResearchInfrastructure researchInfrastructure left join fetch researchInfrastructure.subdomains left join fetch researchInfrastructure.subcategories",
        countQuery = "select count(distinct researchInfrastructure) from ResearchInfrastructure researchInfrastructure")
    Page<ResearchInfrastructure> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct researchInfrastructure from ResearchInfrastructure researchInfrastructure left join fetch researchInfrastructure.subdomains left join fetch researchInfrastructure.subcategories")
    List<ResearchInfrastructure> findAllWithEagerRelationships();

    @Query("select researchInfrastructure from ResearchInfrastructure researchInfrastructure "
    		+ "left join fetch researchInfrastructure.subdomains "
    		+ "left join fetch researchInfrastructure.subcategories "
    		+ " where researchInfrastructure.id =:id")
    Optional<ResearchInfrastructure> findOneWithEagerRelationships(@Param("id") Long id);
    
    Page<ResearchInfrastructure> findByOwnerId(Long ownerId, Pageable pageable);
    List<ResearchInfrastructure> findByOwnerId(Long ownerId);
    
    // samo infrastruktura za koju je on odgovorna osoba
    Page<ResearchInfrastructure> findByManagerId(String managerId, Pageable pageable);
    
    List<ResearchInfrastructure> findByManagerId(String managerId);
    
    @Query(nativeQuery = true)
    List<ResearchInfrastructureDTOSearch> searchBySerbianKeywords(@Param("keywordsNotExist") Integer keywordsExist, 
    		@Param("keywords") String keywords,@Param("subdomainsNotExist") Integer subdomainsExist,
    		@Param("subdomains") List<Long> subdomains);
    
    @Query(nativeQuery = true)
    List<ResearchInfrastructureDTOSearch> searchByEnglishKeywords(@Param("keywordsNotExist") Integer keywordsExist, 
    		@Param("keywords") String keywords,@Param("subdomainsNotExist") Integer subdomainsExist,
    		@Param("subdomains") List<Long> subdomains);
    
    @Query("select ri.id from ResearchInfrastructure ri where ri.owner.id = :ownerId ")
    List<Long> findIdsByOwnerId(@Param("ownerId") Long ownerId);
    
    
    @Query("select new e4i.web.rest.dto.InfrastructureStatusDTO(count(ri), ris) from ResearchInfrastructure ri "
    		+ " right join ri.status ris group by ris ")
    List<InfrastructureStatusDTO> getNumberPerStatuses();
    
    @Query("select new e4i.web.rest.dto.InfrastructureAccessModeDTO(count(ri), ra) from ResearchInfrastructure ri "
    		+ " right join ri.accessMode ra group by ra ")
    List<InfrastructureAccessModeDTO> getNumberPerAccessMode();
    
    @Query("select new e4i.web.rest.dto.InfrastructureAccessTypeDTO(count(ri), ra) from ResearchInfrastructure ri "
    		+ " right join ri.accessType ra group by ra ")
    List<InfrastructureAccessTypeDTO> getNumberPerAccessType();
    
    @Query("select new e4i.web.rest.dto.InfrastructureCategoryDTO(count(ri), ra) from ResearchInfrastructure ri "
    		+ " right join ri.category ra group by ra ")
    List<InfrastructureCategoryDTO> getNumberPerCategory();
    
   /*
    @Query("select new e4i.web.rest.dto.RiSubcategoryDTO(count(ri), ra) from ResearchInfrastructure ri "
    		+ " right join ri.subcategory ra group by ra ")
    List<RiSubcategoryDTO> getNumberPerSubcategory();
    */
   
    
}
