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

import e4i.domain.CmsSlider;
import e4i.service.CmsSliderService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsSlider}.
 */
@RestController
@RequestMapping("/api")
public class CmsSliderResource {

    private final Logger log = LoggerFactory.getLogger(CmsSliderResource.class);

    private static final String ENTITY_NAME = "cmsSlider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsSliderService cmsSliderService;

    public CmsSliderResource(CmsSliderService cmsSliderService) {
        this.cmsSliderService = cmsSliderService;
    }

    /**
     * {@code POST  /cms-sliders} : Create a new cmsSlider.
     *
     * @param cmsSlider the cmsSlider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsSlider, or with status {@code 400 (Bad Request)} if the cmsSlider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-sliders")
    public ResponseEntity<CmsSlider> createCmsSlider(@Valid @RequestBody CmsSlider cmsSlider) throws URISyntaxException {
        log.debug("REST request to save CmsSlider : {}", cmsSlider);
        if (cmsSlider.getId() != null) {
            throw new BadRequestAlertException("A new cmsSlider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsSlider result = cmsSliderService.save(cmsSlider);
        return ResponseEntity.created(new URI("/api/cms-sliders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-sliders} : Updates an existing cmsSlider.
     *
     * @param cmsSlider the cmsSlider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsSlider,
     * or with status {@code 400 (Bad Request)} if the cmsSlider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsSlider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-sliders")
    public ResponseEntity<CmsSlider> updateCmsSlider(@Valid @RequestBody CmsSlider cmsSlider) throws URISyntaxException {
        log.debug("REST request to update CmsSlider : {}", cmsSlider);
        if (cmsSlider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsSlider result = cmsSliderService.save(cmsSlider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsSlider.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-sliders} : get all the cmsSliders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsSliders in body.
     */
    @GetMapping("/cms-sliders")
    public ResponseEntity<List<CmsSlider>> getAllCmsSliders(Pageable pageable) {
        log.debug("REST request to get a page of CmsSliders");
        Page<CmsSlider> page = cmsSliderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-sliders/:id} : get the "id" cmsSlider.
     *
     * @param id the id of the cmsSlider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsSlider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-sliders/{id}")
    public ResponseEntity<CmsSlider> getCmsSlider(@PathVariable Long id) {
        log.debug("REST request to get CmsSlider : {}", id);
        Optional<CmsSlider> cmsSlider = cmsSliderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsSlider);
    }

    /**
     * {@code DELETE  /cms-sliders/:id} : delete the "id" cmsSlider.
     *
     * @param id the id of the cmsSlider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-sliders/{id}")
    public ResponseEntity<Void> deleteCmsSlider(@PathVariable Long id) {
        log.debug("REST request to delete CmsSlider : {}", id);
        cmsSliderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
