package e4i.web.rest;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import e4i.service.FilesStorageService;
import e4i.domain.DocumentType;
import e4i.repository.DocumentRepository;
import e4i.repository.DocumentTypeRepository;
import e4i.domain.Company;
import e4i.domain.Document;
import e4i.domain.PortalUser;
import e4i.repository.CompanyRepository;
import e4i.repository.PortalUserRepository;
import e4i.service.CompanyService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link e4i.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyService companyService;
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    DocumentRepository documentRepository;
    
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    FilesStorageService storageService;
    
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * {@code POST  /companies} : Create a new company.
     *
     * @param company the company to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new company, or with status {@code 400 (Bad Request)} if the company has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to save Company : {}", company);
        if (company.getId() != null) {
            throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Company result = companyService.save(company);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /companies} : Updates an existing company.
     *
     * @param company the company to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated company,
     * or with status {@code 400 (Bad Request)} if the company is not valid,
     * or with status {@code 500 (Internal Server Error)} if the company couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) throws URISyntaxException {
        log.debug("REST request to update Company : {}", company);
        if (company.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Company result = companyService.save(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, company.getId().toString()))
            .body(result);
    }

    @PutMapping("/companies/update-created/{companyId}/{userId}")
    @Transactional
    public ResponseEntity<Company> updateCompanyCreatedBy(@PathVariable Long companyId, @PathVariable String userId) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyId);

        Optional<Company> companyOptional = companyRepository.findById(companyId);
        Company company = companyOptional.get();
  	  
  	  	PortalUser portalUser = portalUserRepository.findByUserId(userId);
  	  
//  	  	Set<Company> allCompanyCreateds = portalUser.getCompanyCreateds();
//  	  	allCompanyCreateds.add(company);
//   	  	portalUser.setCompanyCreateds(allCompanyCreateds);
   	  	company.setCreatedBy(portalUser);
        
   	  	portalUserRepository.save(portalUser);
        Company result = companyService.save(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, company.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/companies/update-modified/{companyId}/{userId}")
    @Transactional
    public ResponseEntity<Company> updateCompanyModifiedBy(@PathVariable Long companyId, @PathVariable String userId) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyId);

        
        Optional<Company> companyOptional = companyRepository.findById(companyId);
  	  	Company company = companyOptional.get();
  	  
  	  	PortalUser portalUser = portalUserRepository.findByUserId(userId);
  	  
//  	  	Set<Company> allCompanyModified = portalUser.getCompanyModifieds();
//  	  	allCompanyModified.add(company);
//  	  	portalUser.setCompanyModifieds(allCompanyModified);
  	  	company.setModifiedBy(portalUser);
  	  
   	  	portalUserRepository.save(portalUser);
        Company result = companyService.save(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, company.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Companies");
        Page<Company> page;
        if (eagerload) {
            page = companyService.findAllWithEagerRelationships(pageable);
        } else {
            page = companyService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/:id} : get the "id" company.
     *
     * @param id the id of the company to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the company, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        log.debug("REST request to get Company : {}", id);
        Optional<Company> company = companyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(company);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" company.
     *
     * @param id the id of the company to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/companies/upload-logo")                   
    @Transactional
    public ResponseEntity<Document> uploadCompanyLogo(@RequestParam Long id, @RequestParam("files") MultipartFile[] file) {         
    	try {
    		Optional<Company> companyOptional = companyRepository.findById(id);
    		Company company = companyOptional.get();
    		Document existingLogo = company.getLogo();
    		
    		// brisanje postojeceg loga ako postoji
    		if(existingLogo != null) {
    			storageService.deleteImage(existingLogo.getFilename());
    			documentRepository.delete(existingLogo);
    		}
    		
    		DocumentType documentType = documentTypeRepository.findByType("image");    
    		Document image = new Document();		  		
    		String logoFilename = storageService.saveCompanyLogo(id, file[0]);
    		
    		image.setFilename(logoFilename);
    		image.setType(documentType);
    		documentRepository.save(image);
    		
    		company.setLogo(image);
    		companyRepository.save(company);
  	
    		return ResponseEntity.ok().body(image);
    	} catch (Exception e) {
    		throw new BadRequestAlertException("Upis nije uspeo", "", "");
    	}
    }
    
    @PostMapping("/companies/upload-images")
    @Transactional
    public ResponseEntity<Set<Document>> uploadImages(@RequestParam Long id, @RequestParam("files") MultipartFile[] files) {
  		  
  	  Optional<Company> companyOptional = companyRepository.findById(id);
  	  Company company = companyOptional.get();
  	
  	  DocumentType documentType = documentTypeRepository.findByType("image");  
  	  Set<Document> images = new HashSet<Document>();
      	    	
  	  Arrays.asList(files).stream().forEach(file -> {
  		  Document image = new Document();		  
  		  String imageName = storageService.saveInfrastructureImage(id, file);
  		  image.setFilename(imageName);
  		  image.setType(documentType);
  		  images.add(image);    		
  	  });

  	  documentRepository.saveAll(images);
  	  
     	  Set<Document> allDocuments = images;
     	  allDocuments.addAll(company.getDocuments());
     	  company.setDocuments(allDocuments);
  	  companyRepository.save(company);
  	  
  	  return ResponseEntity.ok().body(images);
    }
    
    @PostMapping("/companies/upload-documents")
    @Transactional
    public ResponseEntity<Set<Document>> uploadCompanyDocuments(@RequestParam Long id, @RequestParam("files") MultipartFile[] files) {
     	  
     	  Optional<Company> companyOptional = companyRepository.findById(id);
     	  Company company = companyOptional.get();
     	
     	  DocumentType documentType = documentTypeRepository.findByType("document");  
     	  Set<Document> documents = new HashSet<Document>();
         	    	
     	  Arrays.asList(files).stream().forEach(file -> {
     		  
     		  String nameStart = "doc_company_" + id + "_";
     		  Document document = new Document();		  
     		  
     		  String documentName = storageService.saveDocument(nameStart, file);
     		  document.setFilename(documentName);
     		  document.setType(documentType);
     		  documents.add(document);    		
     	  });

     	  documentRepository.saveAll(documents);
     	  
     	  Set<Document> allDocuments = documents;
     	  allDocuments.addAll(company.getDocuments());
     	  company.setDocuments(allDocuments);	  
     	  companyRepository.save(company);
     	  
     	  return ResponseEntity.ok().body(documents);        
    }      

    @DeleteMapping("/companies/delete-logo/{companyId}")
    @Transactional
    public ResponseEntity<Company> deleteCompanyLogo(@PathVariable Long companyId) {
        log.debug("REST request to delete logo for company : {}", companyId);

     	  Optional<Company> companyOptional = companyRepository.findById(companyId);
     	  Company company = companyOptional.get();

     	  Document existingLogo = company.getLogo();
  	    
     	  // brisanje postojeceg loga ako postoji
     	  if(existingLogo != null)
     		  storageService.deleteImage(existingLogo.getFilename());
     	  	  documentRepository.delete(existingLogo);
           	  
     	  company.setLogo(null);
     	  companyRepository.save(company);
     	  
        return ResponseEntity.ok().body(company);
    }
    
    @DeleteMapping("/companies/delete-image/{companyId}/{imageId}")
    @Transactional
    public ResponseEntity<Set<Document>> deleteCompanyImage(@PathVariable Long companyId, @PathVariable Long imageId) {
        log.debug("REST request to delete Image for company : {}", companyId);

     	  Optional<Company> companyOptional = companyRepository.findById(companyId);
     	  Company company = companyOptional.get();

        Optional<Document> imgOptional = documentRepository.findById(imageId);
        Document image = imgOptional.get();
           	  
        // Ovako se brisu veze iz many-to-many tabele "company-documents"
     	  company.getDocuments().remove(image);
     	  image.getCompanies().remove(company);
     	  
        documentRepository.delete(image);
        storageService.deleteImage(image.getFilename());
        
        // Ovo vraca i slike i dokumenta. Izdvojiti samo slike.
        Set<Document> images = company.getDocuments();
        return ResponseEntity.ok().body(images);
    }
    
    @DeleteMapping("/companies/delete-document/{companyId}/{documentId}")
    @Transactional
    public ResponseEntity<Set<Document>> deleteCompanyDocument(@PathVariable Long companyId, @PathVariable Long documentId) {
        log.debug("REST request to delete document for company : {}", companyId);

     	  Optional<Company> companyOptional = companyRepository.findById(companyId);
     	  Company company = companyOptional.get();

        Optional<Document> documentIdOptional = documentRepository.findById(documentId);
        Document document = documentIdOptional.get();

        // Ovako se brisu veze iz many-to-many tabele "company-documents"
     	  company.getDocuments().remove(document);
     	  document.getCompanies().remove(company);

        documentRepository.delete(document);
        storageService.deleteDocument(document);
        
        // Ovo vraca i slike i dokumenta. Izdvojiti samo slike.
        Set<Document> documents = company.getDocuments();
        return ResponseEntity.ok().body(documents);
    }
       
    @GetMapping("/companies/autocomplete/{name}/{excludedIds}")
    public List<Company> getAllAutocompleteByNameWithoutExlcuded(@PathVariable String name, @PathVariable List<Long> excludedIds) {
        log.debug("REST request to get list of Companies by autocomplete withouth excluded ones");
      
        if (name.isBlank()) {
            List<Company> companies = companyRepository.findAllNotInIds(excludedIds);
            return companies;
        } else {
            List<Company> companies = companyRepository.findAllByNameContainingIgnoreCaseAndNotInIds(name, excludedIds);
            return companies;
        }
    }
}
