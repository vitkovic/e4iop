package e4i.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e4i.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
