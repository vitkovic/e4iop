package e4i.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import e4i.domain.Document;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file);
  
  public String saveInfrastructureImage(Long researchInfrastructureId, MultipartFile file);
  public String saveLogo(Long researchInfrastructureId, MultipartFile file);
  
  public Resource loadImage(String filename);
  public Resource loadDocument(String filename);

  public void deleteImage(String filename);


  public Resource load(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();
  
  public String saveImage(String prefix, MultipartFile file);
  public String saveDocument(String prefix, MultipartFile file);
  public String saveCompanyLogo(Long id, MultipartFile file);
  public void deleteDocument(Document document);
}
