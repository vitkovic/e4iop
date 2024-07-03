package e4i.web.rest;

import e4i.domain.CmsNews;
import e4i.domain.Company;
import e4i.service.CmsNewsService;
import e4i.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CmsNewsResource {

    private final Logger log = LoggerFactory.getLogger(CmsNewsResource.class);

    private static final String ENTITY_NAME = "cmsNews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmsNewsService cmsNewsService;

    @Autowired
    public CmsNewsResource(CmsNewsService cmsNewsService) {
        this.cmsNewsService = cmsNewsService;
    }

    /**
     * {@code POST  /cms-news} : Create a new cmsNews.
     *
     * @param cmsNews the cmsNews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmsNews, or with status {@code 400 (Bad Request)} if the cmsNews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cms-news")
    public ResponseEntity<CmsNews> createCmsNews(@Valid @RequestBody CmsNews cmsNews) throws URISyntaxException {
        log.debug("REST request to save CmsNews : {}", cmsNews);
        if (cmsNews.getId() != null) {
            throw new BadRequestAlertException("A new cmsNews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmsNews result = cmsNewsService.createOrUpdateCmsNews(cmsNews);
        return ResponseEntity.created(new URI("/api/cms-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cms-news} : Updates an existing cmsNews.
     *
     * @param cmsNews the cmsNews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmsNews,
     * or with status {@code 400 (Bad Request)} if the cmsNews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmsNews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cms-news")
    public ResponseEntity<CmsNews> updateCmsNews(@Valid @RequestBody CmsNews cmsNews) throws URISyntaxException {
        log.debug("REST request to update CmsNews : {}", cmsNews);
        if (cmsNews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmsNews result = cmsNewsService.createOrUpdateCmsNews(cmsNews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cmsNews.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cms-news} : get all the cmsNews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmsNews in body.
     */
    @GetMapping("/cms-news")
    public ResponseEntity<List<CmsNews>> getAllCmsNews(Pageable pageable) {
        log.debug("REST request to get a page of CmsNews");
        Page<CmsNews> page = cmsNewsService.getAllCmsNews(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cms-news/:id} : get the "id" cmsNews.
     *
     * @param id the id of the cmsNews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmsNews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-news/{id}")
    public ResponseEntity<CmsNews> getCmsNews(@PathVariable Long id) {
        log.debug("REST request to get CmsNews : {}", id);
        Optional<CmsNews> cmsNews = cmsNewsService.getCmsNewsById(id);
        return ResponseUtil.wrapOrNotFound(cmsNews);
    }

    /**
     * {@code DELETE  /cms-news/:id} : delete the "id" cmsNews.
     *
     * @param id the id of the cmsNews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cms-news/{id}")
    public ResponseEntity<Void> deleteCmsNews(@PathVariable Long id) {
        log.debug("REST request to delete CmsNews : {}", id);
        cmsNewsService.deleteCmsNews(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /advertisements/:search} : get the "search" advertisement.
     *
     * @param search
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-news/search")
    public ResponseEntity<List<CmsNews>> getCompanySearch(Pageable pageable, @RequestParam String search) {
        log.debug("REST request to get search Companies : {}", search);
        Page<CmsNews> page;
        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        page = cmsNewsService.findAllBySearch(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
