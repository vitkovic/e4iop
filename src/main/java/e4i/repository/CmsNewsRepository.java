package e4i.repository;

import e4i.domain.CmsNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsNewsRepository extends JpaRepository<CmsNews, Long> {
    // You can add custom query methods here if needed
}
