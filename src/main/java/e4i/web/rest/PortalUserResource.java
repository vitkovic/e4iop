package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.domain.PortalUser;
import e4i.domain.User;
import e4i.repository.PortalUserRepository;
import e4i.repository.UserRepository;
import e4i.service.MailService;
import e4i.service.PortalUserService;
import e4i.service.UserService;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.vm.PortalUserDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.PortalUser}.
 */
@RestController
@RequestMapping("/api")
public class PortalUserResource {

    private final Logger log = LoggerFactory.getLogger(PortalUserResource.class);

    private static final String ENTITY_NAME = "portalUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PortalUserService portalUserService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PortalUserRepository portalUserRepository;
    private final MailService mailService;
    
   

	public PortalUserResource(PortalUserService portalUserService, UserService userService,
			UserRepository userRepository, PortalUserRepository portalUserRepository, MailService mailService) {
		this.portalUserService = portalUserService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.portalUserRepository = portalUserRepository;
		this.mailService = mailService;
	}

	/**
     * {@code POST  /portal-users} : Create a new portalUser.
     *
     * @param portalUser the portalUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new portalUser, or with status {@code 400 (Bad Request)} if the portalUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/portal-users")
    public ResponseEntity<PortalUser> createPortalUser(@RequestBody PortalUserDTO portalUserDto) throws URISyntaxException {
        log.debug("REST request to save PortalUser : {}", portalUserDto);
        if (portalUserDto.getId() != null) {
            throw new BadRequestAlertException("A new portalUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        User user = userService.registerUserRiPortal(portalUserDto.getUser(), portalUserDto.getUser().getPassword());
        PortalUser portalUser = new PortalUser(portalUserDto);
        portalUser.setUser(user);
        PortalUser result = portalUserService.save(portalUser);
        mailService.sendCreationEmail(user);
        return ResponseEntity.created(new URI("/api/portal-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /portal-users} : Updates an existing portalUser.
     *
     * @param portalUser the portalUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated portalUser,
     * or with status {@code 400 (Bad Request)} if the portalUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portalUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/portal-users")
    public ResponseEntity<PortalUser> updatePortalUser(@RequestBody PortalUserDTO portalUserDto) throws URISyntaxException {
        log.debug("REST request to update PortalUser : {}", portalUserDto);
        if (portalUserDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        PortalUser portalUser = new PortalUser(portalUserDto);
        portalUser.setUser(userService.saveFromManagedUser(portalUserDto.getUser()));        
        
        PortalUser result = portalUserService.save(portalUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, portalUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /portal-users} : get all the portalUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of portalUsers in body.
     */
    @GetMapping("/portal-users")
    public ResponseEntity<List<PortalUser>> getAllPortalUsers(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of PortalUsers");
        Page<PortalUser> page;
        if (eagerload) {
            page = portalUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = portalUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /portal-users/:id} : get the "id" portalUser.
     *
     * @param id the id of the portalUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the portalUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/portal-users/{id}")
    public ResponseEntity<PortalUser> getPortalUser(@PathVariable Long id) {
        log.debug("REST request to get PortalUser : {}", id);
        Optional<PortalUser> portalUser = portalUserService.findOne(id.toString());
        return ResponseUtil.wrapOrNotFound(portalUser);
    }

    /**
     * {@code DELETE  /portal-users/:id} : delete the "id" portalUser.
     *
     * @param id the id of the portalUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/portal-users/{id}")
    public ResponseEntity<Void> deletePortalUser(@PathVariable String id) {
        log.debug("REST request to delete PortalUser : {}", id);
        portalUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    
    @GetMapping("/portal-users-get-roles")
    public List<String> getRoles() {
        log.debug("REST request to get roles for current PortalUser : {}");
        return portalUserService.getRoles();
    }
    /**
     * KOrisnici koji su u istoj organizaciji kao tekuci korisnik
     * @param pageable
     * @param eagerload
     * @return
     */
    @GetMapping("/portal-users/for-organization")
    public List<PortalUser> getAllPortalUsersForOrganization()  {
        log.debug("REST request to get a page of PortalUsers in same organization as current user");
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();
        
        //PortalUser pUser  = portalUserRepository.findByUserIDWithEagerRelationships(user.getId());
        PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
        return portalUserRepository.findByUserOrganizationId(pUser.getUserOrganization().getId());
    }
    
    /**
     * KOrisnici koji su u istoj organizaciji kao tekuci korisnik
     * @param pageable
     * @param eagerload
     * @return
     */
    @GetMapping("/portal-users/for-given-organization/{id}")
    public List<PortalUser> getAllPortalUsersForGivenOrganization(@PathVariable("id") Long id)  {
        log.debug("REST request to get a PortalUsers for given organization");
        
        return portalUserRepository.findByUserOrganizationId(id);
    }
    
    /**
     * Broj korisnika u bazi
     * @param id
     * @return
     */
    @GetMapping("/portal-users-pregled/count")
    public ResponseEntity<Long> countPortalUsersPregled() {
        log.debug("REST request to get count of portal users: {}");
        Long count = portalUserService.countAll();
        return new ResponseEntity<Long>(count, HttpStatus.OK);
    }
    
    
    /**
     * Dolazi se sa strane activate-user-roles. Citaju se podaci o korisniku, njegove uloge i da li je aktiviran. 
     * @param id
     * @return
     */
    @GetMapping("/portal-users/for-activation/{id}")
    public ResponseEntity<PortalUser> getPortalUserForActivation(@PathVariable Long id) {
        log.debug("REST request to get PortalUser : {}", id);
        PortalUser portalUser = portalUserService.findOneForActivation(id);
        return new ResponseEntity<PortalUser>(portalUser, HttpStatus.OK);
    }
    /**
     * Dolazi se sa strane za unos uloga korisnika i eventualnu aktivaciju (ako vec nije aktiviran)
     * @param portalUserDto
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/portal-users/for-activation")
    public ResponseEntity<PortalUser> updatePortalUserRoles(@RequestBody PortalUserDTO portalUserDto) throws URISyntaxException {
        log.debug("REST request to update PortalUser : {}", portalUserDto);
        if (portalUserDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        PortalUser result = portalUserService.saveRolesAndActivate(portalUserDto);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/portal-users/user/{id}")
    public ResponseEntity<PortalUser> getPortalUserByUser(@PathVariable String id) {
        log.debug("REST request to get PortalUser by User : {}", id);
        
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        
        PortalUser portalUser = portalUserRepository.findByUserId(user.getId());
        
//        Optional<PortalUser> portalUser = portalUserService.findOne(id);
        return ResponseEntity.ok().body(portalUser);
    }
}
