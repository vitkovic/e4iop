package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RiCalendar;

/**
 * Spring Data  repository for the RiCalendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiCalendarRepository extends JpaRepository<RiCalendar, Long> {
}
