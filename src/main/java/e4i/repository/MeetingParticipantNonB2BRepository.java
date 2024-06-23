package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e4i.domain.MeetingParticipant;
import e4i.domain.MeetingParticipantNonB2B;

@SuppressWarnings("unused")
@Repository
public interface MeetingParticipantNonB2BRepository extends JpaRepository<MeetingParticipantNonB2B, Long> {
	
	List<MeetingParticipantNonB2B> findAllByMeetingId(Long meetingId);
	
	Optional<MeetingParticipantNonB2B> findOneByMeetingIdAndEmail(Long meetingId, String email);
}
