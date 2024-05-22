package e4i.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.DocumentKind;
import e4i.repository.DocumentKindRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentKind}.
 */
@Service
@Transactional
public class DocumentKindService {

    private final Logger log = LoggerFactory.getLogger(DocumentKindService.class);

    private final DocumentKindRepository documentKindRepository;

    public DocumentKindService(DocumentKindRepository documentKindRepository) {
        this.documentKindRepository = documentKindRepository;
    }

    /**
     * Save a documentKind.
     *
     * @param documentKind the entity to save.
     * @return the persisted entity.
     */
    public DocumentKind save(DocumentKind documentKind) {
        log.debug("Request to save DocumentKind : {}", documentKind);
        return documentKindRepository.save(documentKind);
    }

    /**
     * Get all the documentKinds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentKind> findAll() {
        log.debug("Request to get all DocumentKinds");
        return documentKindRepository.findAll();
    }


    /**
     * Get one documentKind by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentKind> findOne(Long id) {
        log.debug("Request to get DocumentKind : {}", id);
        return documentKindRepository.findById(id);
    }

    /**
     * Delete the documentKind by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentKind : {}", id);
        documentKindRepository.deleteById(id);
    }
}
