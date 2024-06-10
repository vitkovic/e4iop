package e4i.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.Meeting;
import e4i.domain.Thread;

/**
 * Spring Data  repository for the Meeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
	
	List<Meeting> findAllByCompanyId(Long companyId);

	Optional<Meeting> findOneByThreads(Thread thread);
}
