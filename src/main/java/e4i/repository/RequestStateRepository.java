package e4i.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import e4i.domain.RequestState;


/**
 * Spring Data  repository for the RequestAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStateRepository extends JpaRepository<RequestState, Long>  {
	
	RequestState findByStateEn(String stateEn);

}
