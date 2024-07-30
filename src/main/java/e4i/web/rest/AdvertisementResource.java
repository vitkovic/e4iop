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

import e4i.domain.Advertisement;
import e4i.domain.AdvertisementStatus;
import e4i.domain.Document;
import e4i.domain.DocumentType;
import e4i.domain.PortalUser;
import e4i.domain.User;
import e4i.repository.AdvertisementRepository;
import e4i.repository.AdvertisementStatusRepository;
import e4i.repository.DocumentRepository;
import e4i.repository.DocumentTypeRepository;
import e4i.repository.PortalUserRepository;
import e4i.service.AdvertisementService;
import e4i.service.FilesStorageService;
import e4i.service.PortalUserService;
import e4i.service.UserService;
import e4i.web.rest.errors.BadRequestAlertException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link e4i.domain.Advertisement}.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementResource.class);

    private static final String ENTITY_NAME = "advertisement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvertisementService advertisementService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PortalUserService portalUserService;
    
    @Autowired
    AdvertisementRepository advertisementRepository;
    
    @Autowired
    AdvertisementStatusRepository advertisementStatusRepository;
    
    @Autowired
    PortalUserRepository portalUserRepository;
    
    @Autowired
    FilesStorageService storageService;
    
    @Autowired
    DocumentRepository documentRepository;
    
    @Autowired
    DocumentTypeRepository documentTypeRepository;

    public AdvertisementResource(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    /**
     * {@code POST  /advertisements} : Create a new advertisement.
     *
     * @param advertisement the advertisement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advertisement, or with status {@code 400 (Bad Request)} if the advertisement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advertisements")
    public ResponseEntity<Advertisement> createAdvertisement(@Valid @RequestBody Advertisement advertisement) throws URISyntaxException {
        log.debug("REST request to save Advertisement : {}", advertisement);
        if (advertisement.getId() != null) {
            throw new BadRequestAlertException("A new advertisement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        User user = userService.getLoggedInUser();
        PortalUser portalUser = portalUserService.findByUserId(user.getId());
        
        advertisement.setCreatedAt(Instant.now());
  	  	advertisement.setCreatedBy(portalUser);
   	  	portalUserService.save(portalUser);
        
        Advertisement result = advertisementService.save(advertisement);
        return ResponseEntity.created(new URI("/api/advertisements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advertisements} : Updates an existing advertisement.
     *
     * @param advertisement the advertisement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advertisement,
     * or with status {@code 400 (Bad Request)} if the advertisement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advertisement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advertisements")
    public ResponseEntity<Advertisement> updateAdvertisement(@Valid @RequestBody Advertisement advertisement) throws URISyntaxException {
        log.debug("REST request to update Advertisement : {}", advertisement);
        if (advertisement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        User user = userService.getLoggedInUser();
        PortalUser portalUser = portalUserService.findByUserId(user.getId());
        
        advertisement.setModifiedAt(Instant.now());
  	  	advertisement.setModifiedBy(portalUser);
  	  	portalUserService.save(portalUser);
        
        Advertisement result = advertisementService.save(advertisement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /advertisements} : get all the advertisements.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advertisements in body.
     */
    @GetMapping("/advertisements")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Advertisements");
        Page<Advertisement> page;
        if (eagerload) {
            page = advertisementService.findAllWithEagerRelationships(pageable);
        } else {
            page = advertisementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /advertisements/:id} : get the "id" advertisement.
     *
     * @param id the id of the advertisement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisements/{id}")
    public ResponseEntity<Advertisement> getAdvertisement(@PathVariable Long id) {
        log.debug("REST request to get Advertisement : {}", id);
        Optional<Advertisement> advertisement = advertisementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisement);
    }
    
    /**
     * {@code GET  /advertisements/:id} : get the "id" advertisement.
     *
     * @param id the id of the advertisement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advertisements/view/{id}")
    public ResponseEntity<Advertisement> getAdvertisementView(@PathVariable Long id) {
        log.debug("REST request to get Advertisement : {}", id);
        Optional<Advertisement> advertisement = advertisementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advertisement);
    }
   
    /**
     * {@code DELETE  /advertisements/:id} : delete the "id" advertisement.
     *
     * @param id the id of the advertisement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advertisements/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        log.debug("REST request to delete Advertisement : {}", id);
        advertisementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PutMapping("/advertisements/update-created/{advertisementId}/{userId}")
    @Transactional
    public ResponseEntity<Advertisement> updateCreatedBy(@PathVariable Long advertisementId, @PathVariable String userId) throws URISyntaxException {
        log.debug("REST request to update Advertisement : {}", advertisementId);

        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(advertisementId);
        Advertisement advertisement = advertisementOptional.get();
  	  
  	  	PortalUser portalUser = portalUserRepository.findByUserId(userId);
  	  	advertisement.setCreatedBy(portalUser);
        
   	  	portalUserRepository.save(portalUser);
   	  	Advertisement result = advertisementService.save(advertisement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisement.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/advertisements/update-modified/{advertisementId}/{userId}")
    @Transactional
    public ResponseEntity<Advertisement> updateModifiedBy(@PathVariable Long advertisementId, @PathVariable String userId) throws URISyntaxException {
        log.debug("REST request to update Advertisement : {}", advertisementId);

        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(advertisementId);
        Advertisement advertisement = advertisementOptional.get();
  	  
  	  	PortalUser portalUser = portalUserRepository.findByUserId(userId);
  	  	advertisement.setModifiedBy(portalUser);
        
   	  	portalUserRepository.save(portalUser);
   	  	Advertisement result = advertisementService.save(advertisement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisement.getId().toString()))
            .body(result);
    }
    
	  @PostMapping("/advertisements/upload-files")
	  @Transactional
	  public ResponseEntity<Set<Document>> uploadFiles(
			  @RequestParam Long id, 
			  @RequestParam("imageFiles") MultipartFile[] imageFiles,
			  @RequestParam("documentFiles") MultipartFile[] documentFiles
			  ) {	
		  
		  Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
		  Advertisement advertisement = advertisementOptional.get();
		
		  DocumentType imageType = documentTypeRepository.findByType("image");  
		  Set<Document> images = new HashSet<Document>();
		  
       	  DocumentType documentType = documentTypeRepository.findByType("document");  
       	  Set<Document> documents = new HashSet<Document>();
	    	    	
		  Arrays.asList(imageFiles).stream().forEach(file -> {
			  Document image = new Document();		  
			  String namePrefix = "img_ad_" + id + "_";
			  String imageName = storageService.saveImage(namePrefix, file);
			  image.setFilename(imageName);
			  image.setType(imageType);
			  images.add(image);    		
		  });
		  
       	  Arrays.asList(documentFiles).stream().forEach(file -> {
       		  String namePrefix = "doc_ad_" + id + "_";
       		  Document document = new Document();		  
       		  String documentName = storageService.saveDocument(namePrefix, file);
       		  document.setFilename(documentName);
       		  document.setType(documentType);
       		  documents.add(document);    		
       	  });
	
		  documentRepository.saveAll(images);
       	  documentRepository.saveAll(documents);
       	  
	   	  Set<Document> allFiles = images;
	   	  allFiles.addAll(documents);
	   	  allFiles.addAll(advertisement.getDocuments());
	   	  advertisement.setDocuments(allFiles);
	   	         	  
       	  advertisementRepository.save(advertisement);
		  
		  return ResponseEntity.ok().body(images);
	  }
    
    /***********************************************************************************************************
    * Upload slika za oglase
    * @param files
    * @return
    ************************************************************************************************************/
	  @PostMapping("/advertisements/upload-images")
	  @Transactional
	  public ResponseEntity<Set<Document>> uploadImages(@RequestParam Long id, @RequestParam("files") MultipartFile[] files) {	
		  
		  Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
		  Advertisement advertisement = advertisementOptional.get();
		
		  DocumentType documentType = documentTypeRepository.findByType("image");  
		  Set<Document> images = new HashSet<Document>();
	    	    	
		  Arrays.asList(files).stream().forEach(file -> {
			  Document image = new Document();		  
			  String namePrefix = "img_ad_" + id + "_";
			  String imageName = storageService.saveImage(namePrefix, file);
			  image.setFilename(imageName);
			  image.setType(documentType);
			  images.add(image);    		
		  });
	
		  documentRepository.saveAll(images);
		  
	   	  Set<Document> allDocuments = images;
	   	  allDocuments.addAll(advertisement.getDocuments());
	   	  advertisement.setDocuments(allDocuments);
	   	  advertisementRepository.save(advertisement);
		  
		  return ResponseEntity.ok().body(images);
	  }
	  
      @PostMapping("/advertisements/upload-documents")
      @Transactional
      public ResponseEntity<Set<Document>> uploadDocuments(@RequestParam Long id, @RequestParam("files") MultipartFile[] files) {
       	  
		  Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
		  Advertisement advertisement = advertisementOptional.get();
       	
       	  DocumentType documentType = documentTypeRepository.findByType("document");  
       	  Set<Document> documents = new HashSet<Document>();
           	    	
       	  Arrays.asList(files).stream().forEach(file -> {
       		  
       		  String namePrefix = "doc_ad_" + id + "_";
       		  Document document = new Document();		  
       		  
       		  String documentName = storageService.saveDocument(namePrefix, file);
       		  document.setFilename(documentName);
       		  document.setType(documentType);
       		  documents.add(document);    		
       	  });

       	  documentRepository.saveAll(documents);
       	  
       	  Set<Document> allDocuments = documents;
       	  allDocuments.addAll(advertisement.getDocuments());
       	  advertisement.setDocuments(allDocuments);	  
       	  advertisementRepository.save(advertisement);
       	  
       	  return ResponseEntity.ok().body(documents);        
      } 
      
	  @DeleteMapping("/advertisements/delete-image/{id}/{imageId}")
	  @Transactional
	  public ResponseEntity<Set<Document>> deleteImage(@PathVariable Long id, @PathVariable Long imageId) {
	      log.debug("REST request to delete Image for advertisement : {}", id);

	   	  Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
	   	  Advertisement advertisement = advertisementOptional.get();

	      Optional<Document> imgOptional = documentRepository.findById(imageId);
	      Document image = imgOptional.get();
	         	  
	      // Ovako se brisu veze iz many-to-many tabele "company-documents"
	      advertisement.getDocuments().remove(image);
	   	  image.getAdvertisements().remove(advertisement);
	   	  
	      documentRepository.delete(image);
	      storageService.deleteImage(image.getFilename());
	      
	      // Ovo vraca i slike i dokumenta. Izdvojiti samo slike.
	      Set<Document> images = advertisement.getDocuments();
	      return ResponseEntity.ok().body(images);
	  }
	  
	  @DeleteMapping("/advertisements/delete-document/{id}/{documentId}")
	  @Transactional
	  public ResponseEntity<Set<Document>> deleteDocument(@PathVariable Long id, @PathVariable Long documentId) {
	      log.debug("REST request to delete document for advertisement : {}", id);

	   	  Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
	   	  Advertisement advertisement = advertisementOptional.get();

	      Optional<Document> documentIdOptional = documentRepository.findById(documentId);
	      Document document = documentIdOptional.get();

	      // Ovako se brisu veze iz many-to-many tabele "company-documents"
	   	  advertisement.getDocuments().remove(document);
	   	  document.getAdvertisements().remove(advertisement);

	      documentRepository.delete(document);
	      storageService.deleteB2BDocument(document);
	      
	      // Ovo vraca i slike i dokumenta. Izdvojiti samo dokumenta.
	      Set<Document> documents = advertisement.getDocuments();
	      return ResponseEntity.ok().body(documents);
	  }
	  
	    @PutMapping("/advertisements/update-status/{id}/{statusId}")
	    @Transactional
	    public ResponseEntity<Advertisement> updateStatus(@PathVariable Long id, @PathVariable Long statusId) throws URISyntaxException {
	        log.debug("REST request to update Advertisement : {}", id);
	        if (id == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(id);
	        Advertisement advertisement = advertisementOptional.get();
	        
	        Optional<AdvertisementStatus> advertisementStatusOptional = advertisementStatusRepository.findById(statusId);
	        AdvertisementStatus advertisementStatus = advertisementStatusOptional.get();
	        
	        advertisement.setStatus(advertisementStatus);
	        
	        if (advertisementStatus.getStatusEn().equals(AdvertisementStatus.ACTIVE)) {
	        	advertisement.setActivationDatetime(Instant.now());
	        	advertisement.setExpirationDatetime(
	        			ZonedDateTime
	        			.now()
	        			.plusMonths(advertisement.getDuration().getDuration())
	        			.toInstant()
	        			);
	        	advertisement.setDeletionDatetime(null);
	        } else if (advertisementStatus.getStatusEn().equals(AdvertisementStatus.ARCHIVED)) {
	        	advertisement.setDeletionDatetime(Instant.now());
	        }
	        
	        Advertisement result = advertisementService.save(advertisement);
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advertisement.getId().toString()))
	            .body(result);
	    }
	    
	    @GetMapping("/advertisements/browse")
	    public ResponseEntity<List<Advertisement>> getAllAdvertisementsBrowse(
	    		Pageable pageable, 
	    		@RequestParam(required = false, defaultValue = "Активан") String status, 
	    		@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
	    	log.debug("REST request to get a page of Advertisements");
	    	
	    	// Odraditi proveru da li status sa zadatim nazivom postoji u bazi
	    	System.out.println("kukuriku #####################################################################");
	        Page<Advertisement> page;
	        if (eagerload) {
	            page = advertisementRepository.findAllWithEagerRelationships(pageable);
	        } else {
	            page = advertisementRepository.findAllByStatus(status, pageable);
	        }
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/search")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearch(Pageable pageable, @RequestParam String search, @RequestParam Long category) {
	        log.debug("REST request to get search Advertisements : {}", search);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearch(search, category, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/searchstatus")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearchStatus(Pageable pageable, @RequestParam String search,@RequestParam Long status, @RequestParam Long category) {
	        log.debug("REST request to get search Advertisements : {}", search);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearchStatus(search, status, category, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/searchtype")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearchType(Pageable pageable, @RequestParam Long type) {
	        log.debug("REST request to get search Advertisements : {}", type);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearchType(type, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/searchkind")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearchKind(Pageable pageable, @RequestParam Long kind) {
	        log.debug("REST request to get search Advertisements : {}", kind);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearchKind(kind, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/searchdates")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearchDates(@RequestParam String from, @RequestParam String to, Pageable pageable) {
	        log.debug("REST request to get search Advertisements : {}", from + " " + to);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearchDates(from, to, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    
	    /**
	     * {@code GET  /advertisements/:search} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/searchtypestatus")
	    public ResponseEntity<List<Advertisement>> getAdvertisementSearchStatus(@RequestParam Long type, @RequestParam Long status, Pageable pageable) {
	        log.debug("REST request to get search Advertisements : {}", type);
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllBySearchTypeStatus(type, status, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    
	    
	    
	    /**
	     * {@code GET  /advertisements/:searchall} : get the "search" advertisement.
	     *
	     * @param search
	     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
	     */
	    @GetMapping("/advertisements/get")
	    public ResponseEntity<List<Advertisement>> getAdvertisementGetAll(Pageable pageable) {
	        log.debug("REST request to get search Advertisements : {}");
	        Page<Advertisement> page;
	        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        page = advertisementService.findAllFree(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	   
	    @GetMapping("/advertisements/company")
	    public ResponseEntity<List<Advertisement>> getAllAdvertisementsForCompany(
	    		Pageable pageable, 
	    		@RequestParam Long companyId,
	    		@RequestParam(required = false, defaultValue = AdvertisementStatus.ACTIVE) String status) {
	    	log.debug("REST request to get a page of Advertisements for company: {} and status: {}", companyId, status);
	    	
	    	// Odraditi proveru da li status sa zadatim nazivom postoji u bazi
	    	
	        Page<Advertisement> page = advertisementService.findAllByCompanyIdandStatus(companyId, status, pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/company-status")
	    public ResponseEntity<List<Advertisement>> findAllAdvertisementsByCompanyAndStatus(
	    		Pageable pageable, 
	    		@RequestParam Long companyId,
	    		@RequestParam Long statusId) {
	    	log.debug("REST request to get a page of Advertisements for company: {} and status: {}", companyId, statusId);
	    	
	        Page<Advertisement> page = advertisementService.findAllByCompanyIdAndStatusId(companyId, statusId, pageable);
	        	        
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/company-not-status")
	    public ResponseEntity<List<Advertisement>> findAllAdvertisementsByCompanyAndNotStatus(
	    		Pageable pageable, 
	    		@RequestParam Long companyId,
	    		@RequestParam Long statusId) {
	    	log.debug("REST request to get a page of Advertisements for company: {} and not status: {}", companyId, statusId);
	    	
	        Page<Advertisement> page = advertisementService.findAllByCompanyIdAndNotStatusId(companyId, statusId, pageable);
	        	        
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/status")
	    public ResponseEntity<List<Advertisement>> findAllAdvertisementsByStatus(
	    		Pageable pageable, 
	    		@RequestParam Long statusId) {
	    	log.debug("REST request to get a page of Advertisements for status: {}", statusId);
	    	
	        Page<Advertisement> page = advertisementService.findAllByStatusId(statusId, pageable);
	        	        
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/company-type")
	    public ResponseEntity<List<Advertisement>> findAllAdvertisementsByCompanyAndType(
	    		Pageable pageable, 
	    		@RequestParam Long companyId,
	    		@RequestParam Long typeId) {
	    	log.debug("REST request to get a page of Advertisements for company: {} and type: {}", companyId, typeId);
	    	
	        Page<Advertisement> page = advertisementService.findAllByCompanyIdAndTypeId(companyId, typeId, pageable);
	        	        
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/company/status-type")
	    public ResponseEntity<List<Advertisement>> findAllForCompanyByStatusAndType(
	    		Pageable pageable, 
	    		@RequestParam Long companyId,
	    		@RequestParam String status,
	    		@RequestParam String type) {
	    	log.debug("REST request to get a page of Advertisements for company: {} by status {} and type: {}", companyId, status, type);
	    	
	        Page<Advertisement> page = advertisementService.findAllForCompanyByStatusAndType(companyId, status, type, pageable);
	        	        
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }
	    
	    @GetMapping("/advertisements/company-count-all/{companyId}")
	    public ResponseEntity<Long> getCountAllForCompany(@PathVariable Long companyId) {
	    	log.debug("REST request to get count for all Advertisments for Company : {}", companyId);
	        Long count = advertisementService.getCountAllForCompany(companyId);
	        
	        return ResponseEntity.ok(count);
	    }
	    
	    @GetMapping("/advertisements/company-count-active/{companyId}")
	    public ResponseEntity<Long> getCountActiveForCompany(@PathVariable Long companyId) {
	    	log.debug("REST request to get count for active Advertisments for Company : {}", companyId);
	    	Long count = advertisementService.getCountActiveForCompany(companyId);
    	
	    	return ResponseEntity.ok(count);
	    }
	    
	    @GetMapping("/advertisements/company-count-inactive/{companyId}")
	    public ResponseEntity<Long> getCountInactiveForCompany(@PathVariable Long companyId) {
	    	log.debug("REST request to get count for inactive Advertisments for Company : {}", companyId);
	    	Long count = advertisementService.getCountInactiveForCompany(companyId);
	    	
	    	return ResponseEntity.ok(count);
	    }
}
