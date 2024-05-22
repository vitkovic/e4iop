package e4i.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "portal_user_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortalUserStatus {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value_sr")
    private String valueSr;
    
    @Column(name = "value_en")
    private String valueEn;
    
    @Column(name = "value_sr_cyr")
    private String valueSrCyr;
    
    

	public PortalUserStatus() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValueSr() {
		return valueSr;
	}

	public void setValueSr(String valueSr) {
		this.valueSr = valueSr;
	}

	public String getValueEn() {
		return valueEn;
	}

	public void setValueEn(String valueEn) {
		this.valueEn = valueEn;
	}

	public String getValueSrCyr() {
		return valueSrCyr;
	}

	public void setValueSrCyr(String valueSrCyr) {
		this.valueSrCyr = valueSrCyr;
	}



	
	

}
