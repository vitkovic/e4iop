package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.RiService;
import e4i.web.rest.dto.RiServiceDTOSearch;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RiService entity.
 */
@Repository
public interface RiServiceRepositoryExtension extends JpaRepository<RiService, Long> {

	 @Query(value="select * from ri_service where upper(name_sr) like ?%",nativeQuery = true)
	 List<RiService> findRiServiceByNameSr(String name);
	 
	 @Query(value="select * from ri_service where upper(name_en) like ?%",nativeQuery = true)
	 List<RiService> findRiServiceByNameEn(String name);
    
    
    //@Query(nativeQuery = true)
    //List getAllForPatientAsList(@Param("patientId") Long patientId);
}
