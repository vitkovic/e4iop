package e4i.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import e4i.domain.Document;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private Path root = null;
  private Path imageRoot = null;
  private String uploadDir = "uploads";
  private String imageDir = "images";
  private Path documentRoot = null;
  private String documentDir = "documents";
  private Path rootabs = Paths.get("c:\\e4i\\uploads");// in case of absolutepath
  
  @Override
  public void init() {
    try {
    	this.root = this.byGetResource(e4i.E4IApp.class);
    	this.documentRoot = this.root.resolve(documentDir);
    	this.imageRoot = this.root.resolve(imageDir);
    	Files.createDirectories(this.root);
    	Files.createDirectories(this.imageRoot);
    	Files.createDirectories(this.documentRoot);
    	
    } catch (IOException e) {
      try {
    	  /*
    	  File file = new File(this.getRiportalPath());
    	  file.mkdirs();
    	  */
    	  return;
      } catch(Exception ex) {
    	  throw new RuntimeException("Could not initialize folder for upload!");
      }
    }
  }
  
  Path byGetResource(Class clazz) {
	    URL classResource = clazz.getResource(clazz.getSimpleName() + ".class");
	    if (classResource == null) {
	        throw new RuntimeException("class resource is null");
	    }
	    String url = classResource.toString();
	    if (url.startsWith("jar:file:")) {
	        // extract 'file:......jarName.jar' part from the url string
	        String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
	        int riportalDirIndex = path.indexOf("e4i.jar");
			String srcUploadDir = path.substring(0, riportalDirIndex) + uploadDir;
	        try {
	            return Paths.get(new URL(srcUploadDir).toURI());//.toString();
	        } catch (Exception e) {
	            throw new RuntimeException("Invalid Jar File URL String");
	        }
	    }else {
	    	return Paths.get(this.getE4iPath());
	    }
	    //throw new RuntimeException("Invalid Jar File URL String");
	}

  public String getE4iPath() {
	  CodeSource codeSource = e4i.E4IApp.class.getProtectionDomain().getCodeSource();	 
	  File jarFile;
	try {
		//jarFile = new File(codeSource.getLocation().getPath());
		System.out.println("protection domain = " + e4i.E4IApp.class.getProtectionDomain());
		System.out.println("code source = " + e4i.E4IApp.class.getProtectionDomain().getCodeSource());
		System.out.println("location = " + e4i.E4IApp.class.getProtectionDomain().getCodeSource().getLocation());
		
		
		System.out.println("toUri = " + e4i.E4IApp.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		System.out.println("path = " + e4i.E4IApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		String jarPath = e4i.E4IApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		
		System.out.println("jarPath = " + jarPath);
		jarFile = new File(jarPath);
		
		String jarDir = jarFile.getParentFile().getPath();
		int riportalDirIndex = jarDir.indexOf("e4i");
		String srcUploadDir = jarDir.substring(0, riportalDirIndex) + uploadDir;
		System.out.println(srcUploadDir + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		return srcUploadDir;
	} catch (Exception ex) {
		ex.printStackTrace();
		return null;
	}
  }
  
  @Override
  public void save(MultipartFile file) {
    try {
      Files.deleteIfExists(this.root.resolve(file.getOriginalFilename()));
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }
  
  
  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      System.out.println("FILENAME = " + filename);
      System.out.println("File = " + file.toString());
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }
  
  @Override
  public String saveInfrastructureImage(Long researchInfrastructureId, MultipartFile file) {
    try {
    	String fileName = "image_ri_" + researchInfrastructureId + "_" + file.getOriginalFilename();
    	Files.deleteIfExists(this.imageRoot.resolve(fileName));
    	
        Path imagePath = this.imageRoot.resolve(fileName); 
        Files.copy(file.getInputStream(), imagePath);
        return fileName;
    } catch (Exception e) {
    	if (e instanceof FileAlreadyExistsException) {
    		throw new RuntimeException("An image of that name already exists.");
    	}

    	throw new RuntimeException(e.getMessage());
    }
  }
  
  @Override
  public String saveLogo(Long researchInfrastructureId, MultipartFile file) {
    try {
    	String fileName = "logo_ri_" + researchInfrastructureId + "_" + file.getOriginalFilename();
    	Files.deleteIfExists(this.imageRoot.resolve(fileName));
    	
        Path imagePath = this.imageRoot.resolve(fileName); 
        Files.copy(file.getInputStream(), imagePath);
        return fileName;
    } catch (Exception e) {
    	if (e instanceof FileAlreadyExistsException) {
    		throw new RuntimeException("An image of that name already exists.");
    	}

    	throw new RuntimeException(e.getMessage());
    }
  }
  
  @Override
  public Resource loadImage(String filename) {
    try {
      Path file = imageRoot.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }
  
  @Override
  public void deleteImage(String filename) {
    try {
    	Path file = imageRoot.resolve(filename);
    	Files.deleteIfExists(this.imageRoot.resolve(filename));     
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
  @Override
  public String saveCompanyLogo(Long id, MultipartFile file) {
    try {
    	String fileName = "logo_" + id + "_" + file.getOriginalFilename();
    	Files.deleteIfExists(this.imageRoot.resolve(fileName));
    	
        Path imagePath = this.imageRoot.resolve(fileName); 
        Files.copy(file.getInputStream(), imagePath);
        return fileName;
    } catch (Exception e) {
    	if (e instanceof FileAlreadyExistsException) {
    		throw new RuntimeException("An image of that name already exists.");
    	}

    	throw new RuntimeException(e.getMessage());
    }
  }
  
  @Override
  public String saveImage(String prefix, MultipartFile file) {
	try {
		String fileName = prefix + file.getOriginalFilename();    	
		Files.deleteIfExists(this.imageRoot.resolve(fileName));
	    Path filePath = this.imageRoot.resolve(fileName);
	    Files.copy(file.getInputStream(), filePath);
	    return fileName;
	} catch (Exception e) {
		if (e instanceof FileAlreadyExistsException) {
			throw new RuntimeException("An image of that name already exists.");
		}
		throw new RuntimeException(e.getMessage());
	}
  }
  
  @Override
  public String saveDocument(String prefix, MultipartFile file) {
	try {
		String fileName = prefix + file.getOriginalFilename();    	
		Files.deleteIfExists(this.documentRoot.resolve(fileName));
	    Path filePath = this.documentRoot.resolve(fileName);
	    Files.copy(file.getInputStream(), filePath);
	    return fileName;
	} catch (Exception e) {
		if (e instanceof FileAlreadyExistsException) {
			throw new RuntimeException("A document of that name already exists.");
		}
		throw new RuntimeException(e.getMessage());
	}
  }
  @Override
  public void deleteB2BDocument(Document document) {
    try {
    	String filename = document.getFilename();
    	Path file = documentRoot.resolve(filename);
    	Files.deleteIfExists(this.documentRoot.resolve(filename));     
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
  
  @Override
  public void deleteResearchInfrastrucureDocument(String filename) {
    try {
    	Path file = root.resolve(filename);
    	Files.deleteIfExists(this.root.resolve(filename));     
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }
  
  @Override
  public Resource loadDocument(String filename) {
    try {
      Path file = documentRoot.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }
  
}