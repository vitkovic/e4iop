package e4i.domain;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cms_news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsNews implements Serializable{
    
    private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "sequence_number", nullable = false)
    private Long sequenceNumber;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "newsTitleImages", allowSetters = true)
    private Document titleImage;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "cms_news_document",
               joinColumns = @JoinColumn(name = "cms_news_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Instant getDate() {
		return date;
	}
	
	public void setDate(Instant date) {
		this.date = date;
	}
	
	public Long getSequenceNumber() {
		return sequenceNumber;
	}
	
	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
    public Document getTitleImage() {
        return titleImage;
    }

    public CmsNews titleImage(Document titleImage) {
        this.titleImage = titleImage;
        return this;
    }

    public void setTitleImage(Document titleImage) {
        this.titleImage = titleImage;
    }
	
    public Set<Document> getDocuments() {
        return documents;
    }

    public CmsNews documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public CmsNews addDocument(Document document) {
        this.documents.add(document);
        document.getCmsNewses().add(this);
        return this;
    }

    public CmsNews removeDocument(Document document) {
        this.documents.remove(document);
        document.getCmsNewses().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

	@Override
	public int hashCode() {
		return Objects.hash(content, date, id, sequenceNumber, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmsNews other = (CmsNews) obj;
		return Objects.equals(content, other.content) && Objects.equals(date, other.date)
				&& Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "CmsNews [id=" + id + ", title=" + title + ", content=" + content + ", date=" + date + "]";
	}
}

