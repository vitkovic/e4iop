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

import e4i.domain.CmsTestimonials;
import e4i.service.CmsTestimonialsService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsTestimonials}.
 */
@RestController
@RequestMapping("/api")
public class CmsTestimonialsResource {

    private final Logger log = LoggerFactory.getLogger(CmsTestimonialsResource.class);

    private static final String ENTITY_NAME = "cmsTestimonials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsTestimonialsService cmsTestimonialsService;

    public CmsTestimonialsResource(CmsTestimonialsService cmsTestimonialsService) {
        this.cmsTestimonialsService = cmsTestimonialsService;
    }

    /**
     * {@code POST  /cms-testimonials} : Create a new cmsTestimonials.
     *
     * @param cmsTestimonials the cmsTestimonials to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsTestimonials, or with status {@code 400 (Bad Request)} if the cmsTestimonials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-testimonials")
    public ResponseEntity<CmsTestimonials> createCmsTestimonials(@Valid @RequestBody CmsTestimonials cmsTestimonials) throws URISyntaxException {
        log.debug("REST request to save CmsTestimonials : {}", cmsTestimonials);
        if (cmsTestimonials.getId() != null) {
            throw new BadRequestAlertException("A new cmsTestimonials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsTestimonials result = cmsTestimonialsService.save(cmsTestimonials);
        return ResponseEntity.created(new URI("/api/cms-testimonials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-testimonials} : Updates an existing cmsTestimonials.
     *
     * @param cmsTestimonials the cmsTestimonials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsTestimonials,
     * or with status {@code 400 (Bad Request)} if the cmsTestimonials is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsTestimonials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-testimonials")
    public ResponseEntity<CmsTestimonials> updateCmsTestimonials(@Valid @RequestBody CmsTestimonials cmsTestimonials) throws URISyntaxException {
        log.debug("REST request to update CmsTestimonials : {}", cmsTestimonials);
        if (cmsTestimonials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsTestimonials result = cmsTestimonialsService.save(cmsTestimonials);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsTestimonials.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-testimonials} : get all the cmsTestimonials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsTestimonials in body.
     */
    @GetMapping("/cms-testimonials")
    public ResponseEntity<List<CmsTestimonials>> getAllCmsTestimonials(Pageable pageable) {
        log.debug("REST request to get a page of CmsTestimonials");
        Page<CmsTestimonials> page = cmsTestimonialsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-testimonials/:id} : get the "id" cmsTestimonials.
     *
     * @param id the id of the cmsTestimonials to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsTestimonials, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-testimonials/{id}")
    public ResponseEntity<CmsTestimonials> getCmsTestimonials(@PathVariable Long id) {
        log.debug("REST request to get CmsTestimonials : {}", id);
        Optional<CmsTestimonials> cmsTestimonials = cmsTestimonialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsTestimonials);
    }

    /**
     * {@code DELETE  /cms-testimonials/:id} : delete the "id" cmsTestimonials.
     *
     * @param id the id of the cmsTestimonials to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-testimonials/{id}")
    public ResponseEntity<Void> deleteCmsTestimonials(@PathVariable Long id) {
        log.debug("REST request to delete CmsTestimonials : {}", id);
        cmsTestimonialsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
