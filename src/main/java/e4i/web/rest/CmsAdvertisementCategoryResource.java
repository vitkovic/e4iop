package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.CmsAdvertisementCategory;
import e4i.service.CmsAdvertisementCategoryService;
import e4i.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.CmsAdvertisementCategory}.
 */
@RestController
@RequestMapping("/api")
public class CmsAdvertisementCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CmsAdvertisementCategoryResource.class);

    private static final String ENTITY_NAME = "cmsAdvertisementCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsAdvertisementCategoryService cmsAdvertisementCategoryService;

    public CmsAdvertisementCategoryResource(CmsAdvertisementCategoryService cmsAdvertisementCategoryService) {
        this.cmsAdvertisementCategoryService = cmsAdvertisementCategoryService;
    }

    /**
     * {@code POST  /cms-advertisement-categories} : Create a new cmsAdvertisementCategory.
     *
     * @param cmsAdvertisementCategory the cmsAdvertisementCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsAdvertisementCategory, or with status {@code 400 (Bad Request)} if the cmsAdvertisementCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-advertisement-categories")
    public ResponseEntity<CmsAdvertisementCategory> createCmsAdvertisementCategory(@RequestBody CmsAdvertisementCategory cmsAdvertisementCategory) throws URISyntaxException {
        log.debug("REST request to save CmsAdvertisementCategory : {}", cmsAdvertisementCategory);
        if (cmsAdvertisementCategory.getId() != null) {
            throw new BadRequestAlertException("A new cmsAdvertisementCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsAdvertisementCategory result = cmsAdvertisementCategoryService.save(cmsAdvertisementCategory);
        return ResponseEntity.created(new URI("/api/cms-advertisement-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-advertisement-categories} : Updates an existing cmsAdvertisementCategory.
     *
     * @param cmsAdvertisementCategory the cmsAdvertisementCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsAdvertisementCategory,
     * or with status {@code 400 (Bad Request)} if the cmsAdvertisementCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsAdvertisementCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-advertisement-categories")
    public ResponseEntity<CmsAdvertisementCategory> updateCmsAdvertisementCategory(@RequestBody CmsAdvertisementCategory cmsAdvertisementCategory) throws URISyntaxException {
        log.debug("REST request to update CmsAdvertisementCategory : {}", cmsAdvertisementCategory);
        if (cmsAdvertisementCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsAdvertisementCategory result = cmsAdvertisementCategoryService.save(cmsAdvertisementCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsAdvertisementCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-advertisement-categories} : get all the cmsAdvertisementCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsAdvertisementCategories in body.
     */
    @GetMapping("/cms-advertisement-categories")
    public List<CmsAdvertisementCategory> getAllCmsAdvertisementCategories() {
        log.debug("REST request to get all CmsAdvertisementCategories");
        return cmsAdvertisementCategoryService.findAll();
    }

    /**
     * {@code GET  /cms-advertisement-categories/:id} : get the "id" cmsAdvertisementCategory.
     *
     * @param id the id of the cmsAdvertisementCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsAdvertisementCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-advertisement-categories/{id}")
    public ResponseEntity<CmsAdvertisementCategory> getCmsAdvertisementCategory(@PathVariable Long id) {
        log.debug("REST request to get CmsAdvertisementCategory : {}", id);
        Optional<CmsAdvertisementCategory> cmsAdvertisementCategory = cmsAdvertisementCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmsAdvertisementCategory);
    }

    /**
     * {@code DELETE  /cms-advertisement-categories/:id} : delete the "id" cmsAdvertisementCategory.
     *
     * @param id the id of the cmsAdvertisementCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-advertisement-categories/{id}")
    public ResponseEntity<Void> deleteCmsAdvertisementCategory(@PathVariable Long id) {
        log.debug("REST request to delete CmsAdvertisementCategory : {}", id);
        cmsAdvertisementCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
