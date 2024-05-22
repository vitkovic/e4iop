package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RequestAnswer;

/**
 * Spring Data  repository for the RequestAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestAnswerRepository extends JpaRepository<RequestAnswer, Long> {
}
