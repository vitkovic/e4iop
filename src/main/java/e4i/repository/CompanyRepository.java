package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.Company;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "select distinct company from Company company left join fetch company.badges left join fetch company.documents",
        countQuery = "select count(distinct company) from Company company")
    Page<Company> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct company from Company company left join fetch company.badges left join fetch company.documents")
    List<Company> findAllWithEagerRelationships();

    @Query("select company from Company company left join fetch company.badges left join fetch company.documents where company.id =:id")
    Optional<Company> findOneWithEagerRelationships(@Param("id") Long id);
}
