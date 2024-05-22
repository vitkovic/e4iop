package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AnswerToOffer;

/**
 * Spring Data  repository for the AnswerToOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerToOfferRepository extends JpaRepository<AnswerToOffer, Long> {
}
