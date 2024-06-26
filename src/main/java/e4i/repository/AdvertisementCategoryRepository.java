package e4i.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.AdvertisementCategory;

/**
 * Spring Data  repository for the AdvertisementCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvertisementCategoryRepository extends JpaRepository<AdvertisementCategory, Long> {
	
	
	 public List<AdvertisementCategory> findAll(Sort srt);
	
	
}
