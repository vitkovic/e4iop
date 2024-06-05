package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
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
}
