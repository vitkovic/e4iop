package e4i.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e4i.domain.PortalUserStatus;

@SuppressWarnings("unused")
@Repository
public interface PortalUserStatusRepository extends JpaRepository<PortalUserStatus, Long> {

}
