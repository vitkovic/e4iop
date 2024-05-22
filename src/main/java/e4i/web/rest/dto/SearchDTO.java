package e4i.web.rest.dto;

import java.time.LocalDate;
import java.util.List;

public class SearchDTO {
	String searchTerms;
	String currentLanguage;
	List<Long> selSubdomains;
	String selType;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Long organizationId;
	
	public SearchDTO() {
		super();
	}
	
	public SearchDTO(SearchDTO s, Long id) {
		this.searchTerms = s.getSearchTerms();
		this.currentLanguage = s.getCurrentLanguage();
		this.selSubdomains = s.getSelSubdomains();
		this.selType = s.getSelType();
		this.fromDate = s.getFromDate();
		this.toDate = s.getToDate();
		this.organizationId = id;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public List<Long> getSelSubdomains() {
		return selSubdomains;
	}

	public void setSelSubdomains(List<Long> selSubdomains) {
		this.selSubdomains = selSubdomains;
	}

	public String getSelType() {
		return selType;
	}

	public void setSelType(String selType) {
		this.selType = selType;
	}

	

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	
	

}
