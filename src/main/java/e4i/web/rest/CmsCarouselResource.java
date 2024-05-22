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

import e4i.domain.CmsCarousel;
import e4i.service.CmsCarouselService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsCarousel}.
 */
@RestController
@RequestMapping("/api")
public class CmsCarouselResource {

    private final Logger log = LoggerFactory.getLogger(CmsCarouselResource.class);

    private static final String ENTITY_NAME = "cmsCarousel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsCarouselService cmsCarouselService;

    public CmsCarouselResource(CmsCarouselService cmsCarouselService) {
        this.cmsCarouselService = cmsCarouselService;
    }

    /**
     * {@code POST  /cms-carousels} : Create a new cmsCarousel.
     *
     * @param cmsCarousel the cmsCarousel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsCarousel, or with status {@code 400 (Bad Request)} if the cmsCarousel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-carousels")
    public ResponseEntity<CmsCarousel> createCmsCarousel(@Valid @RequestBody CmsCarousel cmsCarousel) throws URISyntaxException {
        log.debug("REST request to save CmsCarousel : {}", cmsCarousel);
        if (cmsCarousel.getId() != null) {
            throw new BadRequestAlertException("A new cmsCarousel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsCarousel result = cmsCarouselService.save(cmsCarousel);
        return ResponseEntity.created(new URI("/api/cms-carousels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-carousels} : Updates an existing cmsCarousel.
     *
     * @param cmsCarousel the cmsCarousel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsCarousel,
     * or with status {@code 400 (Bad Request)} if the cmsCarousel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsCarousel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-carousels")
    public ResponseEntity<CmsCarousel> updateCmsCarousel(@Valid @RequestBody CmsCarousel cmsCarousel) throws URISyntaxException {
        log.debug("REST request to update CmsCarousel : {}", cmsCarousel);
        if (cmsCarousel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsCarousel result = cmsCarouselService.save(cmsCarousel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsCarousel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-carousels} : get all the cmsCarousels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsCarousels in body.
     */
    @GetMapping("/cms-carousels")
    public ResponseEntity<List<CmsCarousel>> getAllCmsCarousels(Pageable pageable) {
        log.debug("REST request to get a page of CmsCarousels");
        Page<CmsCarousel> page = cmsCarouselService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-carousels/:id} : get the "id" cmsCarousel.
     *
     * @param id the id of the cmsCarousel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsCarousel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-carousels/{id}")
    public ResponseEntity<CmsCarousel> getCmsCarousel(@PathVariable Long id) {
        log.debug("REST request to get CmsCarousel : {}", id);
        Optional<CmsCarousel> cmsCarousel = cmsCarouselService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsCarousel);
    }

    /**
     * {@code DELETE  /cms-carousels/:id} : delete the "id" cmsCarousel.
     *
     * @param id the id of the cmsCarousel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-carousels/{id}")
    public ResponseEntity<Void> deleteCmsCarousel(@PathVariable Long id) {
        log.debug("REST request to delete CmsCarousel : {}", id);
        cmsCarouselService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
