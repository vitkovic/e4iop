package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.DocumentKind;

/**
 * Spring Data  repository for the DocumentKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentKindRepository extends JpaRepository<DocumentKind, Long> {
}
