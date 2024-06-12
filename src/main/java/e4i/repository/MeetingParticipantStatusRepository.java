package e4i.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.MeetingParticipantStatus;

/**
 * Spring Data  repository for the MeetingParticipantStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingParticipantStatusRepository extends JpaRepository<MeetingParticipantStatus, Long> {
	
	Optional<MeetingParticipantStatus> findByStatusSr(String statusSr);
	Optional<MeetingParticipantStatus> findByStatusSrc(String statusSrc);
	Optional<MeetingParticipantStatus> findByStatusEn(String statusEn);
	
    @Query("SELECT mps FROM MeetingParticipantStatus mps WHERE mps.statusSr = :status OR mps.statusSrc = :status OR mps.statusEn = :status")
    Optional<MeetingParticipantStatus> findByStatusInAnyLanguage(@Param("status") String status);
}
