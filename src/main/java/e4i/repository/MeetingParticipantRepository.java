package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Meeting;
import e4i.domain.MeetingParticipant;

/**
 * Spring Data  repository for the MeetingParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
	
	List<MeetingParticipant> findAllByCompanyIdAndHasRemoved(Long companyId, Boolean hasRemoved);
	
	List<MeetingParticipant> findAllByMeetingId(Long meetingId);
	
	MeetingParticipant findOneByMeetingIdAndIsOrganizer(Long meetingId, Boolean isOrganizer);
	
	Optional<MeetingParticipant> findOneByMeetingIdAndCompanyId(Long meetingId, Long companyId);
	
	@Query("SELECT mp.hasAccepted FROM MeetingParticipant mp WHERE mp.meeting.id = :meetingId AND mp.company.id = :companyId")
	Optional<Boolean> findHasAcceptedByMeetingIdAndCompanyId(@Param("meetingId") Long meetingId, @Param("companyId") Long companyId);

	@Query("SELECT CASE WHEN COUNT(mp) > 0 THEN true ELSE false END " +
		       "FROM MeetingParticipant mp " +
		       "JOIN mp.status mps " +
		       "WHERE mp.meeting.id = :meetingId " +
		       "AND mp.company.id = :companyId " +
		       "AND (mps.statusSr = :status OR mps.statusSrc = :status OR mps.statusEn = :status)")
	Optional<Boolean> checkIfStatusByMeetingIdAndCompanyId(@Param("meetingId") Long meetingId, @Param("companyId") Long companyId, @Param("status") String status);

	
}
