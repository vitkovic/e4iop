package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.ResearchInfrastructure;
import e4i.domain.RiService;
import e4i.web.rest.dto.RiServiceDTOSearch;
import e4i.web.rest.dto.RiServiceInfrastructureOrganizationDTO;
import e4i.web.rest.dto.RiServiceStatisticDTO;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RiService entity.
 */
@Repository
public interface RiServiceRepository extends JpaRepository<RiService, Long> {

    @Query(value = "select distinct riService from RiService riService left join fetch riService.subdomains",
        countQuery = "select count(distinct riService) from RiService riService")
    Page<RiService> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct riService from RiService riService left join fetch riService.subdomains")
    List<RiService> findAllWithEagerRelationships();

    @Query("select riService from RiService riService left join fetch riService.subdomains where riService.id =:id")
    Optional<RiService> findOneWithEagerRelationships(@Param("id") Long id);
    
    Page<RiService> findByResearchInfrastructureId(Long researchInfrastructureId, Pageable pageable);
    
    Page<RiService> findByResearchInfrastructureIn(List<ResearchInfrastructure> lista, Pageable pageable);
    
    List<RiService> findByResearchInfrastructureIn(List<ResearchInfrastructure> lista);
    
   
    @Query(nativeQuery = true)
    List<RiServiceDTOSearch> searchBySerbianKeywords(@Param("keywordsNotExist") Integer keywordsExist, 
    		@Param("keywords") String keywords,@Param("subdomainsNotExist") Integer subdomainsExist,
    		@Param("subdomains") List<Long> subdomains);
    
    @Query(nativeQuery = true)
    List<RiServiceInfrastructureOrganizationDTO> searchAllBySerbianKeywords(@Param("keywords") String keywords);
    
    @Query(nativeQuery = true)
    List<RiServiceInfrastructureOrganizationDTO> searchAllByEnglishKeywords(@Param("keywords") String keywords);
    
    @Query(nativeQuery = true)
    List<RiServiceDTOSearch> searchByEnglishKeywords(@Param("keywordsNotExist") Integer keywordsExist, 
    		@Param("keywords") String keywords,@Param("subdomainsNotExist") Integer subdomainsExist,
    		@Param("subdomains") List<Long> subdomains);
    
    /**
     * Pronalazi sve usluge koje je trazio zadati korisnik
     * @param id
     * @return
     */
    @Query("select riService from RiService riService left join fetch riService.requests req "
    		+ " where req.users.id = :id")
    List<RiService> findForRequestsFromGivenUser(@Param("id") String id);
    
    Long countByResearchInfrastructureIdIn(List<Long> researchInfrastructureIds); 
    
    @Query("Select ri.id from RiService ri where ri.researchInfrastructure.id in ( :researchInfrastructureIds )")
    List<Long> findIdsForResearchInfrastructures(@Param("researchInfrastructureIds") List<Long> researchInfrastructureIds);
    
    @Query(nativeQuery = true)
    List<RiServiceStatisticDTO> getStatisticData();
    
    @Query(nativeQuery = true)
    List<RiServiceStatisticDTO> getStatisticDataForResearchOrganization(@Param("id") Long id);
}
