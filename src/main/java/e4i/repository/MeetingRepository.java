package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.Meeting;

/**
 * Spring Data  repository for the Meeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
