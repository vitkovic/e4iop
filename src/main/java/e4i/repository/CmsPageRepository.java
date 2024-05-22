package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsPage;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CmsPage entity.
 */
@Repository
public interface CmsPageRepository extends JpaRepository<CmsPage, Long> {

    @Query(value = "select distinct cmsPage from CmsPage cmsPage left join fetch cmsPage.documents",
        countQuery = "select count(distinct cmsPage) from CmsPage cmsPage")
    Page<CmsPage> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct cmsPage from CmsPage cmsPage left join fetch cmsPage.documents")
    List<CmsPage> findAllWithEagerRelationships();

    @Query("select cmsPage from CmsPage cmsPage left join fetch cmsPage.documents where cmsPage.id =:id")
    Optional<CmsPage> findOneWithEagerRelationships(@Param("id") Long id);
}
