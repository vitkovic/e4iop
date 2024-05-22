package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.MeetingType;

/**
 * Spring Data  repository for the MeetingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long> {
}
