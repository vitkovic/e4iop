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

import e4i.domain.CmsPage;
import e4i.service.CmsPageService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsPage}.
 */
@RestController
@RequestMapping("/api")
public class CmsPageResource {

    private final Logger log = LoggerFactory.getLogger(CmsPageResource.class);

    private static final String ENTITY_NAME = "cmsPage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsPageService cmsPageService;

    public CmsPageResource(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    /**
     * {@code POST  /cms-pages} : Create a new cmsPage.
     *
     * @param cmsPage the cmsPage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsPage, or with status {@code 400 (Bad Request)} if the cmsPage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-pages")
    public ResponseEntity<CmsPage> createCmsPage(@Valid @RequestBody CmsPage cmsPage) throws URISyntaxException {
        log.debug("REST request to save CmsPage : {}", cmsPage);
        if (cmsPage.getId() != null) {
            throw new BadRequestAlertException("A new cmsPage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsPage result = cmsPageService.save(cmsPage);
        return ResponseEntity.created(new URI("/api/cms-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-pages} : Updates an existing cmsPage.
     *
     * @param cmsPage the cmsPage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsPage,
     * or with status {@code 400 (Bad Request)} if the cmsPage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsPage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-pages")
    public ResponseEntity<CmsPage> updateCmsPage(@Valid @RequestBody CmsPage cmsPage) throws URISyntaxException {
        log.debug("REST request to update CmsPage : {}", cmsPage);
        if (cmsPage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsPage result = cmsPageService.save(cmsPage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsPage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-pages} : get all the cmsPages.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsPages in body.
     */
    @GetMapping("/cms-pages")
    public ResponseEntity<List<CmsPage>> getAllCmsPages(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of CmsPages");
        Page<CmsPage> page;
        if (eagerload) {
            page = cmsPageService.findAllWithEagerRelationships(pageable);
        } else {
            page = cmsPageService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-pages/:id} : get the "id" cmsPage.
     *
     * @param id the id of the cmsPage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsPage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-pages/{id}")
    public ResponseEntity<CmsPage> getCmsPage(@PathVariable Long id) {
        log.debug("REST request to get CmsPage : {}", id);
        Optional<CmsPage> cmsPage = cmsPageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsPage);
    }

    /**
     * {@code DELETE  /cms-pages/:id} : delete the "id" cmsPage.
     *
     * @param id the id of the cmsPage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-pages/{id}")
    public ResponseEntity<Void> deleteCmsPage(@PathVariable Long id) {
        log.debug("REST request to delete CmsPage : {}", id);
        cmsPageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
