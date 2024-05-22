package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUserStatus;
import e4i.repository.PortalUserStatusRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PortalUserStatus}.
 */
@Service
@Transactional
public class PortalUserStatusService {

    private final Logger log = LoggerFactory.getLogger(PortalUserStatusService.class);

    private final PortalUserStatusRepository portalUserStatusRepository;

    public PortalUserStatusService(PortalUserStatusRepository portalUserStatusRepository) {
        this.portalUserStatusRepository = portalUserStatusRepository;
    }

    /**
     * Save a portalUserStatus.
     *
     * @param portalUserStatus the entity to save.
     * @return the persisted entity.
     */
    public PortalUserStatus save(PortalUserStatus portalUserStatus) {
        log.debug("Request to save PortalUserStatus : {}", portalUserStatus);
        return portalUserStatusRepository.save(portalUserStatus);
    }

    /**
     * Get all the portalUserStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PortalUserStatus> findAll() {
        log.debug("Request to get all PortalUserStatuses");
        return portalUserStatusRepository.findAll();
    }


    /**
     * Get one portalUserStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortalUserStatus> findOne(Long id) {
        log.debug("Request to get PortalUserStatus : {}", id);
        return portalUserStatusRepository.findById(id);
    }

    /**
     * Delete the portalUserStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PortalUserStatus : {}", id);
        portalUserStatusRepository.deleteById(id);
    }
}
