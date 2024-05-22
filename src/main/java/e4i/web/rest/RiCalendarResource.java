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

import e4i.domain.RiCalendar;
import e4i.repository.RiCalendarRepository;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.RiCalendar}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RiCalendarResource {

    private final Logger log = LoggerFactory.getLogger(RiCalendarResource.class);

    private static final String ENTITY_NAME = "riCalendar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiCalendarRepository riCalendarRepository;

    public RiCalendarResource(RiCalendarRepository riCalendarRepository) {
        this.riCalendarRepository = riCalendarRepository;
    }

    /**
     * {@code POST  /ri-calendars} : Create a new riCalendar.
     *
     * @param riCalendar the riCalendar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riCalendar, or with status {@code 400 (Bad Request)} if the riCalendar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ri-calendars")
    public ResponseEntity<RiCalendar> createRiCalendar(@RequestBody RiCalendar riCalendar) throws URISyntaxException {
        log.debug("REST request to save RiCalendar : {}", riCalendar);
        if (riCalendar.getId() != null) {
            throw new BadRequestAlertException("A new riCalendar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiCalendar result = riCalendarRepository.save(riCalendar);
        return ResponseEntity.created(new URI("/api/ri-calendars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ri-calendars} : Updates an existing riCalendar.
     *
     * @param riCalendar the riCalendar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riCalendar,
     * or with status {@code 400 (Bad Request)} if the riCalendar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riCalendar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ri-calendars")
    public ResponseEntity<RiCalendar> updateRiCalendar(@RequestBody RiCalendar riCalendar) throws URISyntaxException {
        log.debug("REST request to update RiCalendar : {}", riCalendar);
        if (riCalendar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiCalendar result = riCalendarRepository.save(riCalendar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riCalendar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ri-calendars} : get all the riCalendars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riCalendars in body.
     */
    @GetMapping("/ri-calendars")
    public ResponseEntity<List<RiCalendar>> getAllRiCalendars(Pageable pageable) {
        log.debug("REST request to get a page of RiCalendars");
        Page<RiCalendar> page = riCalendarRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ri-calendars/:id} : get the "id" riCalendar.
     *
     * @param id the id of the riCalendar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riCalendar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ri-calendars/{id}")
    public ResponseEntity<RiCalendar> getRiCalendar(@PathVariable Long id) {
        log.debug("REST request to get RiCalendar : {}", id);
        Optional<RiCalendar> riCalendar = riCalendarRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riCalendar);
    }

    /**
     * {@code DELETE  /ri-calendars/:id} : delete the "id" riCalendar.
     *
     * @param id the id of the riCalendar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ri-calendars/{id}")
    public ResponseEntity<Void> deleteRiCalendar(@PathVariable Long id) {
        log.debug("REST request to delete RiCalendar : {}", id);
        riCalendarRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
