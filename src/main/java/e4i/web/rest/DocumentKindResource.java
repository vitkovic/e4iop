package e4i.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import e4i.domain.DocumentKind;
import e4i.service.DocumentKindService;
import e4i.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link e4i.domain.DocumentKind}.
 */
@RestController
@RequestMapping("/api")
public class DocumentKindResource {

    private final Logger log = LoggerFactory.getLogger(DocumentKindResource.class);

    private static final String ENTITY_NAME = "documentKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentKindService documentKindService;

    public DocumentKindResource(DocumentKindService documentKindService) {
        this.documentKindService = documentKindService;
    }

    /**
     * {@code POST  /document-kinds} : Create a new documentKind.
     *
     * @param documentKind the documentKind to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentKind, or with status {@code 400 (Bad Request)} if the documentKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-kinds")
    public ResponseEntity<DocumentKind> createDocumentKind(@Valid @RequestBody DocumentKind documentKind) throws URISyntaxException {
        log.debug("REST request to save DocumentKind : {}", documentKind);
        if (documentKind.getId() != null) {
            throw new BadRequestAlertException("A new documentKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentKind result = documentKindService.save(documentKind);
        return ResponseEntity.created(new URI("/api/document-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-kinds} : Updates an existing documentKind.
     *
     * @param documentKind the documentKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentKind,
     * or with status {@code 400 (Bad Request)} if the documentKind is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-kinds")
    public ResponseEntity<DocumentKind> updateDocumentKind(@Valid @RequestBody DocumentKind documentKind) throws URISyntaxException {
        log.debug("REST request to update DocumentKind : {}", documentKind);
        if (documentKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentKind result = documentKindService.save(documentKind);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentKind.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-kinds} : get all the documentKinds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentKinds in body.
     */
    @GetMapping("/document-kinds")
    public List<DocumentKind> getAllDocumentKinds() {
        log.debug("REST request to get all DocumentKinds");
        return documentKindService.findAll();
    }

    /**
     * {@code GET  /document-kinds/:id} : get the "id" documentKind.
     *
     * @param id the id of the documentKind to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentKind, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-kinds/{id}")
    public ResponseEntity<DocumentKind> getDocumentKind(@PathVariable Long id) {
        log.debug("REST request to get DocumentKind : {}", id);
        Optional<DocumentKind> documentKind = documentKindService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentKind);
    }

    /**
     * {@code DELETE  /document-kinds/:id} : delete the "id" documentKind.
     *
     * @param id the id of the documentKind to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-kinds/{id}")
    public ResponseEntity<Void> deleteDocumentKind(@PathVariable Long id) {
        log.debug("REST request to delete DocumentKind : {}", id);
        documentKindService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
