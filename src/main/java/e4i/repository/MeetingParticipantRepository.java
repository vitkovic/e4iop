package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.MeetingParticipant;

/**
 * Spring Data  repository for the MeetingParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
}
