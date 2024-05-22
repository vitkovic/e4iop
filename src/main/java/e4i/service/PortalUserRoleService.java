package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUserRole;
import e4i.repository.PortalUserRoleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PortalUserRole}.
 */
@Service
@Transactional
public class PortalUserRoleService {

    private final Logger log = LoggerFactory.getLogger(PortalUserRoleService.class);

    private final PortalUserRoleRepository portalUserRoleRepository;

    public PortalUserRoleService(PortalUserRoleRepository portalUserRoleRepository) {
        this.portalUserRoleRepository = portalUserRoleRepository;
    }

    /**
     * Save a portalUserRole.
     *
     * @param portalUserRole the entity to save.
     * @return the persisted entity.
     */
    public PortalUserRole save(PortalUserRole portalUserRole) {
        log.debug("Request to save PortalUserRole : {}", portalUserRole);
        return portalUserRoleRepository.save(portalUserRole);
    }

    /**
     * Get all the portalUserRoles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PortalUserRole> findAll() {
        log.debug("Request to get all PortalUserRoles");
        return portalUserRoleRepository.findAll();
    }


    /**
     * Get one portalUserRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortalUserRole> findOne(Long id) {
        log.debug("Request to get PortalUserRole : {}", id);
        return portalUserRoleRepository.findById(id);
    }

    /**
     * Delete the portalUserRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PortalUserRole : {}", id);
        portalUserRoleRepository.deleteById(id);
    }
}
