package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.CollaborationStatus;

/**
 * Spring Data  repository for the CollaborationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollaborationStatusRepository extends JpaRepository<CollaborationStatus, Long> {
	
	Optional<CollaborationStatus> findByStatus(String status);
	
    @Query("SELECT cs FROM CollaborationStatus cs WHERE cs.status = :status OR cs.statusSrc = :status OR cs.statusEn = :status")
    Optional<CollaborationStatus> findByAnyStatus(@Param("status") String status);

}
