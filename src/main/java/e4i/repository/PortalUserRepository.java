package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Company;
import e4i.domain.PortalUser;
import e4i.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PortalUser entity.
 */
@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, String> {

    @Query(value = "select distinct portalUser from PortalUser portalUser "
    		+ " left join portalUser.user"
    		+ " left join fetch portalUser.portalUserRoles",
        countQuery = "select count(distinct portalUser) from PortalUser portalUser")
    Page<PortalUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct portalUser from PortalUser portalUser left join fetch portalUser.portalUserRoles")
    List<PortalUser> findAllWithEagerRelationships();

    @Query("select portalUser from PortalUser portalUser left join fetch portalUser.portalUserRoles where portalUser.id =:id")
    Optional<PortalUser> findOneWithEagerRelationships(@Param("id") Long id);
    
    
    PortalUser findByUserId(String id);
    /**
    @Query("select portalUser from PortalUser portalUser where user.id =:id")
    PortalUser findByUserIDWithEagerRelationships(@Param("id") Long id);
    */
    
    List<PortalUser> findByUserOrganizationId(Long organizationId);
    
    Page<PortalUser> findByUserOrganizationId(Long organizationId, Pageable pageable);
    
    
    @Query(value="select p.id from PortalUser p where p.userOrganization.id = :portalUserOrganizationId ")
    List<Long> findIdsUserOrganizationId(@Param("portalUserOrganizationId") Long portalUserOrganizationId);
    
    @Query("select portalUser from PortalUser portalUser "
    		+ " left join fetch portalUser.portalUserRoles "    		
    		+ " where portalUser.id =:id")
    Optional<PortalUser> findOneWithRoles(@Param("id") Long id);
    
    @Modifying
    @Query(value="delete from PortalUser p where p.user = :user")
    int deleteByUserId(@Param("user") User user);
    
    List<PortalUser> findAllByCompany(Company company);
	
	List<PortalUser> findAllByCompanyAndDoNotify(Company company, Boolean doNotify);
    
	@Query("SELECT pu.company FROM PortalUser pu WHERE pu.id = :portalUserId")
	Optional<Company> findCompanyByPortalUserId(@Param("portalUserId") String portalUserId);
}
