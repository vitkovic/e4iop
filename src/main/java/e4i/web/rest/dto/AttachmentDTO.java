package e4i.web.rest.dto;

import org.springframework.core.io.ByteArrayResource;

public class AttachmentDTO {
    private String fileName;
    private ByteArrayResource file;

    // Constructor, getters, and setters
    public AttachmentDTO() {
    	
    }
    
    public AttachmentDTO(String fileName, ByteArrayResource file) {
        this.fileName = fileName;
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ByteArrayResource getFile() {
        return file;
    }

    public void setFile(ByteArrayResource file) {
        this.file = file;
    }
}
