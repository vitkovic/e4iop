package e4i.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e4i.domain.PortalUserRole;
import e4i.domain.PortalUserStatus;
import e4i.repository.PortalUserStatusRepository;
import e4i.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
@Transactional
public class PortalUserStatusResource {
	
	private final Logger log = LoggerFactory.getLogger(PortalUserStatusResource.class);

    private static final String ENTITY_NAME = "portalUserStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortalUserStatusRepository portalUserStatusRepository;

	public PortalUserStatusResource(PortalUserStatusRepository portalUserStatusRepository) {
		this.portalUserStatusRepository = portalUserStatusRepository;
	}
    
	 /**
     * {@code POST  /portal-user-roles} : Create a new portalUserStatus.
     *
     * @param portalUserRole the portalUserStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portalUserRole, or with status {@code 400 (Bad Request)} if the portalUserRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portal-user-statuss")
    public ResponseEntity<PortalUserStatus> createPortalUserStatus(@RequestBody PortalUserStatus portalUserStatus) throws URISyntaxException {
        log.debug("REST request to save portalUserStatus : {}", portalUserStatus);
        if (portalUserStatus.getId() != null) {
            throw new BadRequestAlertException("A new portalUserStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortalUserStatus result = portalUserStatusRepository.save(portalUserStatus);
        return ResponseEntity.created(new URI("/api/portal-user-statuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portal-user-statuss} : Updates an existing portalUserStatus.
     *
     * @param portalUserRole the portalUserStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portalUserRole,
     * or with status {@code 400 (Bad Request)} if the portalUserRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portalUserRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portal-user-statuss")
    public ResponseEntity<PortalUserStatus> updatePortalUserStatus(@RequestBody PortalUserStatus portalUserStatus) throws URISyntaxException {
        log.debug("REST request to update PortalUserStatus : {}", portalUserStatus);
        if (portalUserStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortalUserStatus result = portalUserStatusRepository.save(portalUserStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portalUserStatus.getId().toString()))
            .body(result);
    }
    
    
    /**
     * {@code GET  /portal-user-statuss} : get all the portalUserStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portalUserRoles in body.
     */
    @GetMapping("/portal-user-statuss")
    public List<PortalUserStatus> getAllPortalUserStatuses() {
        log.debug("REST request to get all PortalUserStatuses");
        return portalUserStatusRepository.findAll();
    }

    /**
     * {@code GET  /portal-user-roles/:id} : get the "id" portalUserRole.
     *
     * @param id the id of the portalUserRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUserRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-user-statuss/{id}")
    public ResponseEntity<PortalUserStatus> getPortalUserStatus(@PathVariable Long id) {
        log.debug("REST request to get PortalUserStatus : {}", id);
        Optional<PortalUserStatus> portalUserStatus = portalUserStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(portalUserStatus);
    }

    /**
     * {@code DELETE  /portal-user-roles/:id} : delete the "id" portalUserRole.
     *
     * @param id the id of the portalUserRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portal-user-statuss/{id}")
    public ResponseEntity<Void> deletePortalUserStatus(@PathVariable Long id) {
        log.debug("REST request to delete PortalUserStatus : {}", id);
        portalUserStatusRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    

}
