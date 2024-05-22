package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import e4i.domain.PortalUserRole;
import e4i.repository.PortalUserRoleRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.PortalUserRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PortalUserRoleResource {

    private final Logger log = LoggerFactory.getLogger(PortalUserRoleResource.class);

    private static final String ENTITY_NAME = "portalUserRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortalUserRoleRepository portalUserRoleRepository;

    public PortalUserRoleResource(PortalUserRoleRepository portalUserRoleRepository) {
        this.portalUserRoleRepository = portalUserRoleRepository;
    }

    /**
     * {@code POST  /portal-user-roles} : Create a new portalUserRole.
     *
     * @param portalUserRole the portalUserRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portalUserRole, or with status {@code 400 (Bad Request)} if the portalUserRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portal-user-roles")
    public ResponseEntity<PortalUserRole> createPortalUserRole(@RequestBody PortalUserRole portalUserRole) throws URISyntaxException {
        log.debug("REST request to save PortalUserRole : {}", portalUserRole);
        if (portalUserRole.getId() != null) {
            throw new BadRequestAlertException("A new portalUserRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortalUserRole result = portalUserRoleRepository.save(portalUserRole);
        return ResponseEntity.created(new URI("/api/portal-user-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portal-user-roles} : Updates an existing portalUserRole.
     *
     * @param portalUserRole the portalUserRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portalUserRole,
     * or with status {@code 400 (Bad Request)} if the portalUserRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portalUserRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portal-user-roles")
    public ResponseEntity<PortalUserRole> updatePortalUserRole(@RequestBody PortalUserRole portalUserRole) throws URISyntaxException {
        log.debug("REST request to update PortalUserRole : {}", portalUserRole);
        if (portalUserRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortalUserRole result = portalUserRoleRepository.save(portalUserRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portalUserRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /portal-user-roles} : get all the portalUserRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portalUserRoles in body.
     */
    @GetMapping("/portal-user-roles")
    public List<PortalUserRole> getAllPortalUserRoles() {
        log.debug("REST request to get all PortalUserRoles");
        return portalUserRoleRepository.findAll();
    }

    /**
     * {@code GET  /portal-user-roles/:id} : get the "id" portalUserRole.
     *
     * @param id the id of the portalUserRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-roles/{id}")
    public ResponseEntity<PortalUserRole> getPortalUserRole(@PathVariable Long id) {
        log.debug("REST request to get PortalUserRole : {}", id);
        Optional<PortalUserRole> portalUserRole = portalUserRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portalUserRole);
    }

    /**
     * {@code DELETE  /portal-user-roles/:id} : delete the "id" portalUserRole.
     *
     * @param id the id of the portalUserRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portal-user-roles/{id}")
    public ResponseEntity<Void> deletePortalUserRole(@PathVariable Long id) {
        log.debug("REST request to delete PortalUserRole : {}", id);
        portalUserRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
