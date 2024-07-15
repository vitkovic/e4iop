package e4i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import e4i.domain.CmsQuestion;
import e4i.domain.Company;

/**
 * Spring Data  repository for the CmsQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmsQuestionRepository extends JpaRepository<CmsQuestion, Long> {
	
	
	  @Query(value = "select distinct cmsquestion from CmsQuestion cmsquestion where "
	    		+ " upper(cmsquestion.question) like CONCAT('%',upper(:search),'%') or upper(cmsquestion.answer) like CONCAT('%',upper(:search),'%')",
	            countQuery = "select count(distinct cmsquestion) from CmsQuestion cmsquestion where "
	    		+ " upper(cmsquestion.question) like CONCAT('%',upper(:search),'%') or upper(cmsquestion.answer) like CONCAT('%',upper(:search),'%')")
	        Page<CmsQuestion> findAllSearch(@Param("search") String search,Pageable pageable);

	
	
}
