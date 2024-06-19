package e4i.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e4i.domain.RiFinanceSource; // Adjust import to use RiFinanceSource

/**
 * Spring Data repository for the RiFinanceSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiFinanceSourceRepository extends JpaRepository<RiFinanceSource, Long> {
}
