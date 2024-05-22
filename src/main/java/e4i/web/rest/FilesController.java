package e4i.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import e4i.domain.InfrastructureImage;
import e4i.domain.ResearchInfrastructure;
import e4i.message.ResponseMessage;
import e4i.model.FileInfo;
import e4i.repository.InfrastructureImageRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.service.FilesStorageService;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.InfrastructureImagesLimitException;



@Controller
@RequestMapping("/api")
public class FilesController {

  @Autowired
  FilesStorageService storageService;
  
  @Autowired
  ResearchInfrastructureRepository researchInfrastructureRepository;
  
  @Autowired
  InfrastructureImageRepository infrastructureImageRepository;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      //System.out.println("uspesno $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      //System.out.println("u catchu u kontroleru &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
      message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  
/***********************************************************************************************************
* Upload slika kod infrastruktura
* @param files
* @return
************************************************************************************************************/
  @PostMapping("/upload-images")
  @Transactional
  public ResponseEntity<Set<InfrastructureImage>> uploadImages(@RequestParam Long riId, @RequestParam("files") MultipartFile[] files) {
    String message = "";
    try {
    	Optional<ResearchInfrastructure> riOptional = researchInfrastructureRepository.findById(riId);
    	ResearchInfrastructure ri = riOptional.get();
    	Set<InfrastructureImage> existingImages = ri.getImages();
    	// provera dozvoljenog broja slika (3)
    	if(existingImages != null) {
    		int imageSize = existingImages.size();
    		if(imageSize == 3) {
    			throw new InfrastructureImagesLimitException();
    		}else {
    			if(imageSize + files.length > 3) {
    				throw new InfrastructureImagesLimitException();
    			}
    		}
    	}else {
    		List<String> fileNames = new ArrayList<>();
        	if(files != null && files.length > 3) {
        		throw new InfrastructureImagesLimitException();
        	}	
    	}    	
    	
    	List<InfrastructureImage> images = new ArrayList<InfrastructureImage>();
    	
    	Arrays.asList(files).stream().forEach(file -> {   		    		    		
    		images.add(new InfrastructureImage(storageService.saveInfrastructureImage(riId,file), ri));    		
    	});
    	
    	infrastructureImageRepository.saveAll(images);
    	
    	if(existingImages == null)
    		existingImages = new HashSet<InfrastructureImage>();
    	
    	existingImages.addAll(images);   	    	
	
        return ResponseEntity.ok().body(existingImages);
    } catch (InfrastructureImagesLimitException e) {
        throw e;
    }catch (Exception e) {
        throw new BadRequestAlertException("Upis nije uspeo", "", "");
    }
  }

  /***********************************************************************************************************
  * Upload loga kod infrastruktura
  * @param files
  * @return
  ************************************************************************************************************/
    @PostMapping("/upload-logo")                   
    @Transactional
    public ResponseEntity<String> uploadLogo(@RequestParam Long riId, @RequestParam("files") MultipartFile[] file) {
      String message = "";
      try {
      	Optional<ResearchInfrastructure> riOptional = researchInfrastructureRepository.findById(riId);
      	ResearchInfrastructure ri = riOptional.get();
      	String existingLogoFilename = ri.getRiLogo();
      	// brisanje postojeceg loga ako postoji
      	if(existingLogoFilename != null)
      		storageService.deleteImage(existingLogoFilename);
      	
      	
      	String logoFilename = storageService.saveLogo(riId, file[0]);
      	ri.setRiLogo(logoFilename);
      	researchInfrastructureRepository.save(ri);      	      	
  	
      	return ResponseEntity.ok().body(logoFilename);
      
      }catch (Exception e) {
          throw new BadRequestAlertException("Upis nije uspeo", "", "");
      }
    }
  
  
  @GetMapping("/research-infrastructures/image/{filename}")
  @ResponseBody
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    Resource file = storageService.loadImage(filename);
    
    return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(file);
  }
  
  @GetMapping("/retrieve/image/{filename}")
  @ResponseBody
  public ResponseEntity<Resource> retrieveImage(@PathVariable String filename) {
    Resource file = storageService.loadImage(filename);
    
    return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(file);
  }
  
  @GetMapping("/retrieve/document/{filename}")
  @ResponseBody
  public ResponseEntity<Resource> retrieveDocument(@PathVariable String filename) {
    Resource file = storageService.loadDocument(filename);
    
    return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(file);
  }
}