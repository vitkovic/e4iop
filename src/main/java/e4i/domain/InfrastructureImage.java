package e4i.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "infrastructure_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InfrastructureImage  implements Serializable{
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "filename")
    private String filename;
	
	@ManyToOne
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private ResearchInfrastructure researchInfrastructure;

	public InfrastructureImage() {
		super();
	}
	
	

	public InfrastructureImage(String filename, ResearchInfrastructure researchInfrastructure) {
		super();
		this.filename = filename;
		this.researchInfrastructure = researchInfrastructure;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ResearchInfrastructure getResearchInfrastructure() {
		return researchInfrastructure;
	}

	public void setResearchInfrastructure(ResearchInfrastructure researchInfrastructure) {
		this.researchInfrastructure = researchInfrastructure;
	}
	
	
}
