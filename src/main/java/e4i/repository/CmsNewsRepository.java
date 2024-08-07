package e4i.repository;

import e4i.domain.CmsNews;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsNewsRepository extends JpaRepository<CmsNews, Long> {
    // You can add custom query methods here if needed
	
	
	  @Query(value = "select distinct cmsnews from CmsNews cmsnews "
	    		+ "where (upper(cmsnews.title) like CONCAT('%',upper(:search),'%') or upper(cmsnews.content) like CONCAT('%',upper(:search),'%')) ",
	  countQuery = "select count(distinct cmsnews) from CmsNews cmsnews "
	   		+ "where (upper(cmsnews.title) like CONCAT('%',upper(:search),'%') or upper(cmsnews.content) like CONCAT('%',upper(:search),'%'))")
	  
	   Page<CmsNews> findAllSearch(@Param("search") String search, Pageable pageable);

		
	  @Query("select cms_news from CmsNews cms_news left join fetch cms_news.documents where cms_news.id =:id")	
	  Optional<CmsNews> findOneWithEagerRelationships(@Param("id") Long id);
	
	
	
	
	
}
