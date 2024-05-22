package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.CmsPageType;
import e4i.service.CmsPageTypeService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsPageType}.
 */
@RestController
@RequestMapping("/api")
public class CmsPageTypeResource {

    private final Logger log = LoggerFactory.getLogger(CmsPageTypeResource.class);

    private static final String ENTITY_NAME = "cmsPageType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsPageTypeService cmsPageTypeService;

    public CmsPageTypeResource(CmsPageTypeService cmsPageTypeService) {
        this.cmsPageTypeService = cmsPageTypeService;
    }

    /**
     * {@code POST  /cms-page-types} : Create a new cmsPageType.
     *
     * @param cmsPageType the cmsPageType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsPageType, or with status {@code 400 (Bad Request)} if the cmsPageType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-page-types")
    public ResponseEntity<CmsPageType> createCmsPageType(@Valid @RequestBody CmsPageType cmsPageType) throws URISyntaxException {
        log.debug("REST request to save CmsPageType : {}", cmsPageType);
        if (cmsPageType.getId() != null) {
            throw new BadRequestAlertException("A new cmsPageType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsPageType result = cmsPageTypeService.save(cmsPageType);
        return ResponseEntity.created(new URI("/api/cms-page-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-page-types} : Updates an existing cmsPageType.
     *
     * @param cmsPageType the cmsPageType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsPageType,
     * or with status {@code 400 (Bad Request)} if the cmsPageType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsPageType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-page-types")
    public ResponseEntity<CmsPageType> updateCmsPageType(@Valid @RequestBody CmsPageType cmsPageType) throws URISyntaxException {
        log.debug("REST request to update CmsPageType : {}", cmsPageType);
        if (cmsPageType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsPageType result = cmsPageTypeService.save(cmsPageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsPageType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-page-types} : get all the cmsPageTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsPageTypes in body.
     */
    @GetMapping("/cms-page-types")
    public List<CmsPageType> getAllCmsPageTypes() {
        log.debug("REST request to get all CmsPageTypes");
        return cmsPageTypeService.findAll();
    }

    /**
     * {@code GET  /cms-page-types/:id} : get the "id" cmsPageType.
     *
     * @param id the id of the cmsPageType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsPageType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-page-types/{id}")
    public ResponseEntity<CmsPageType> getCmsPageType(@PathVariable Long id) {
        log.debug("REST request to get CmsPageType : {}", id);
        Optional<CmsPageType> cmsPageType = cmsPageTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsPageType);
    }

    /**
     * {@code DELETE  /cms-page-types/:id} : delete the "id" cmsPageType.
     *
     * @param id the id of the cmsPageType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-page-types/{id}")
    public ResponseEntity<Void> deleteCmsPageType(@PathVariable Long id) {
        log.debug("REST request to delete CmsPageType : {}", id);
        cmsPageTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
