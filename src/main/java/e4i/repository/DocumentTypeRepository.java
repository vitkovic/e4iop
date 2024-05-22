package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.DocumentType;

/**
 * Spring Data  repository for the DocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

	DocumentType findByType(String type);
}
