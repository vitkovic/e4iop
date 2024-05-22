package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsQuestion;

/**
 * Spring Data  repository for the CmsQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsQuestionRepository extends JpaRepository<CmsQuestion, Long> {
}
