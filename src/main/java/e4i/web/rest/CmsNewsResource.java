package e4i.web.rest;

import e4i.domain.CmsNews;
import e4i.domain.Document;
import e4i.domain.DocumentType;
import e4i.repository.CmsNewsRepository;
import e4i.repository.DocumentRepository;
import e4i.repository.DocumentTypeRepository;
import e4i.service.CmsNewsService;
import e4i.service.FilesStorageService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    
    @Autowired
    CmsNewsRepository cmsNewsRepository;
    
    @Autowired
    DocumentRepository documentRepository;
    
    @Autowired
    DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    FilesStorageService storageService;

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
        Optional<CmsNews> cmsNews = cmsNewsService.findOne(id);
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
    
    @PostMapping("/cms-news/upload-title-image")                   
    @Transactional
    public ResponseEntity<Document> uploadTitleImage(@RequestParam Long id, @RequestParam("files") MultipartFile[] file) {     
        log.debug("REST request to upload title image for CmsNews : {}", id);
    	try {
    		Optional<CmsNews> cmsNewsOptional = cmsNewsService.findOne(id);
    		
    		if (cmsNewsOptional.isEmpty()) {
    			log.debug("No CmsNews found with id: {}", id);
    			return ResponseEntity.notFound().build();
    		}
    		
    		CmsNews cmsNews = cmsNewsOptional.get();
    		Document existingTitleImage = cmsNews.getTitleImage();
    		
    		// brisanje postojeceg title image ako postoji
    		if(existingTitleImage != null) {
    			storageService.deleteImage(existingTitleImage.getFilename());
    			documentRepository.delete(existingTitleImage);
    		}
    		
    		DocumentType documentType = documentTypeRepository.findByType(DocumentType.IMAGE);    
    		Document image = new Document();
    		String namePrefix = "cms_news_" + id + "_title_image_";
    		String titleImageFilename = storageService.saveImage(namePrefix, file[0]);
    		
    		image.setFilename(titleImageFilename);
    		image.setType(documentType);
    		documentRepository.save(image);
    		
    		cmsNews.setTitleImage(image);
    		cmsNewsService.createOrUpdateCmsNews(cmsNews);
  	
    		return ResponseEntity.ok().body(image);
    	} catch (Exception e) {
    		throw new BadRequestAlertException("Upis nije uspeo", "", "");
    	}
    }
    
    @DeleteMapping("/cms-news/delete-title-image/{cmsNewsId}")
    @Transactional
    public ResponseEntity<CmsNews> deleteTitleImage(@PathVariable Long cmsNewsId) {
        log.debug("REST request to delete title image for CmsNews : {}", cmsNewsId);

		Optional<CmsNews> cmsNewsOptional = cmsNewsService.findOne(cmsNewsId);
		
		if (cmsNewsOptional.isEmpty()) {
			log.debug("No CmsNews found with id: {}", cmsNewsId);
			return ResponseEntity.notFound().build();
		}
		
		CmsNews cmsNews = cmsNewsOptional.get();
		Document existingTitleImage = cmsNews.getTitleImage();
  	    
		// brisanje postojeceg title image ako postoji  
		if(existingTitleImage != null) {
			storageService.deleteImage(existingTitleImage.getFilename());
			documentRepository.delete(existingTitleImage);  
		}
	
		cmsNews.setTitleImage(null);
		cmsNewsService.createOrUpdateCmsNews(cmsNews);
     	  
        return ResponseEntity.ok().body(cmsNews);
    }
    
    @PostMapping("/cms-news/upload-files")
    @Transactional
    public ResponseEntity<?> uploadFiles(
  		  @RequestParam Long id, 
  		  @RequestParam("imageFiles") MultipartFile[] imageFiles,
  		  @RequestParam("documentFiles") MultipartFile[] documentFiles
  		  ) {		  
  	  Optional<CmsNews> cmsNewsOptional = cmsNewsService.getCmsNewsById(id);
  	  if (cmsNewsOptional.isEmpty()) {
  		  return ResponseEntity.notFound().build();
  	  }
  	  
  	  CmsNews cmsNews = cmsNewsOptional.get();
  	
  	  DocumentType imageType = documentTypeRepository.findByType(DocumentType.IMAGE);  
  	  Set<Document> images = new HashSet<Document>();
  	  
   	  DocumentType documentType = documentTypeRepository.findByType(DocumentType.DOCUMENT);  
   	  Set<Document> documents = new HashSet<Document>();
      	    	
  	  Arrays.asList(imageFiles).stream().forEach(file -> {
  		  Document image = new Document();		  
  		  String namePrefix = "img_news_" + id + "_";
  		  String imageName = storageService.saveImage(namePrefix, file);
  		  image.setFilename(imageName);
  		  image.setType(imageType);
  		  images.add(image);    		
  	  });
  	  
   	  Arrays.asList(documentFiles).stream().forEach(file -> {
   		  String namePrefix = "doc_news_" + id + "_";
   		  Document document = new Document();		  
   		  String documentName = storageService.saveDocument(namePrefix, file);
   		  document.setFilename(documentName);
   		  document.setType(documentType);
   		  documents.add(document);    		
   	  });
   	  
  	  documentRepository.saveAll(images);
   	  documentRepository.saveAll(documents);
   	  
     	  Set<Document> allFiles = new HashSet<Document>();
     	  allFiles.addAll(images);
     	  allFiles.addAll(documents);
     	  allFiles.addAll(cmsNews.getDocuments());
     	  cmsNews.setDocuments(allFiles);
     	         	  
   	  cmsNewsService.createOrUpdateCmsNews(cmsNews);
  	  
  	  return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/cms-news/delete-file/{id}/{fileId}")
    @Transactional
    public ResponseEntity<Set<Document>> deleteDocument(@PathVariable Long id, @PathVariable Long fileId) {
        log.debug("REST request to delete document for CmsNews : {}", id);

     	  Optional<CmsNews> cmsNewsOptional = cmsNewsService.getCmsNewsById(id);
     	  if (cmsNewsOptional.isEmpty()) {
     		return ResponseEntity.notFound().build();
     	  }
     	
        Optional<Document> documentOptional = documentRepository.findById(fileId);
     	  if (documentOptional.isEmpty()) {
     		return ResponseEntity.notFound().build();
     	  }
     	  
     	  CmsNews cmsNews = cmsNewsOptional.get();
        Document document = documentOptional.get();

        // Ovako se brisu veze iz many-to-many tabele "company-documents"
        cmsNews.getDocuments().remove(document);
     	  document.getCmsNewses().remove(cmsNews);

        documentRepository.delete(document);
        
        if (document.getType().getType().equals(DocumentType.DOCUMENT)) {
            storageService.deleteB2BDocument(document);
        } else if (document.getType().getType().equals(DocumentType.IMAGE)) {
      	  storageService.deleteImage(document.getFilename());
        }
              
        return ResponseEntity.ok().build();
    }
    
    /**
     * {@code GET  /advertisements/:search} : get the "search" advertisement.
     *
     * @param search
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advertisement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cms-news/search")
    public ResponseEntity<List<CmsNews>> getNewsSearch(Pageable pageable, @RequestParam String search) {
        log.debug("REST request to get search Companies : {}", search);
        Page<CmsNews> page;
        System.out.println("Search %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        page = cmsNewsService.findAllBySearch(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    
    
    
    
}
