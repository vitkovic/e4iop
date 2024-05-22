package e4i.web.rest.dto;

import e4i.domain.PortalUser;
import e4i.domain.RiAccessMode;
import e4i.domain.RiAccessType;
import e4i.domain.RiResearchOrganization;
import e4i.domain.RiStatus;
import e4i.domain.User;

public class ResearchInfrastructureDTOSearch {

	    private Long id;
	    private String descriptionSr;
	    private String descriptionEn;
	    private String descriptionSrCyr;
	    private String nameSr;
	    private String nameEn;
	    private String nameSrCyr;
	    private String riWebsite;
	    private String keywords;
	    private String keywordsEn;
	    private String keywordsCyr;
	    private RiStatus status;

		private RiAccessMode accessMode;
	    private RiAccessType accessType;
	    private RiResearchOrganization owner;
	    private PortalUser manager;
	    
	    
		public ResearchInfrastructureDTOSearch(Long id, 
				String descriptionSr, String descriptionEn,String descriptionSrCyr, 
				String nameSr, String nameEn, String nameSrCyr, String riWebsite,
				String keywords, String keywordsEn, String keywordsCyr, 
				Long statusId, String status, String statusCyr, String statusEn,
				Long accessModeId, String accessMode, String accessModeCyr, String accessModeEn,
				Long accessTypeId, String accessType, String accessTypeCyr, String accessTypeEn,
				Long ownerId, String ownerName,
				String managerId, String userId, String managerFirstName, String managerLastName) {
			super();
			this.id = id;
			this.descriptionSr = descriptionSr;
			this.descriptionEn = descriptionEn;
			this.descriptionSrCyr = descriptionSrCyr;
			this.nameSr = nameSr;
			this.nameEn = nameEn;
			this.nameSrCyr = nameSrCyr;
			this.riWebsite = riWebsite;
			this.keywords = keywords;
			this.keywordsEn = keywordsEn;
			this.keywordsCyr = keywordsCyr;
			
			this.status = new RiStatus();
			this.status.setId(statusId);
			this.status.setStatus(status);
			this.status.setStatusCyr(statusCyr);
			this.status.setStatusEn(statusEn);
			
			this.accessMode = new RiAccessMode();
			this.accessMode.setId(accessModeId);
			this.accessMode.setMode(accessMode);
			this.accessMode.setModeCyr(accessModeCyr);
			this.accessMode.setModeEn(accessModeEn);
			
			this.accessType = new RiAccessType();
			this.accessType.setId(accessTypeId);
			this.accessType.setType(accessType);
			this.accessType.setTypeCyr(accessTypeCyr);
			this.accessType.setTypeEn(accessTypeEn);
			
			this.owner = new RiResearchOrganization();
			this.owner.setId(ownerId);
			this.owner.setName(ownerName);
			
			this.manager = new PortalUser();
			this.manager.setId(managerId);
			this.manager.setUser(new User());
			this.manager.getUser().setId(userId);
			this.manager.getUser().setFirstName(managerFirstName);
			this.manager.getUser().setLastName(managerLastName);
		}

		public ResearchInfrastructureDTOSearch() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescriptionSr() {
			return descriptionSr;
		}

		public void setDescriptionSr(String descriptionSr) {
			this.descriptionSr = descriptionSr;
		}

		public String getDescriptionEn() {
			return descriptionEn;
		}

		public void setDescriptionEn(String descriptionEn) {
			this.descriptionEn = descriptionEn;
		}

		public String getDescriptionSrCyr() {
			return descriptionSrCyr;
		}

		public void setDescriptionSrCyr(String descriptionSrCyr) {
			this.descriptionSrCyr = descriptionSrCyr;
		}

		public String getNameSr() {
			return nameSr;
		}

		public void setNameSr(String nameSr) {
			this.nameSr = nameSr;
		}

		public String getNameEn() {
			return nameEn;
		}

		public void setNameEn(String nameEn) {
			this.nameEn = nameEn;
		}

		public String getNameSrCyr() {
			return nameSrCyr;
		}

		public void setNameSrCyr(String nameSrCyr) {
			this.nameSrCyr = nameSrCyr;
		}

		public String getRiWebsite() {
			return riWebsite;
		}

		public void setRiWebsite(String riWebsite) {
			this.riWebsite = riWebsite;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public String getKeywordsEn() {
			return keywordsEn;
		}

		public void setKeywordsEn(String keywordsEn) {
			this.keywordsEn = keywordsEn;
		}

		public String getKeywordsCyr() {
			return keywordsCyr;
		}

		public void setKeywordsCyr(String keywordsCyr) {
			this.keywordsCyr = keywordsCyr;
		}

		public RiStatus getStatus() {
			return status;
		}

		public void setStatus(RiStatus status) {
			this.status = status;
		}
		
	    public RiAccessMode getAccessMode() {
			return accessMode;
		}

		public void setAccessMode(RiAccessMode accessMode) {
			this.accessMode = accessMode;
		}

		public RiAccessType getAccessType() {
			return accessType;
		}

		public void setAccessType(RiAccessType accessType) {
			this.accessType = accessType;
		}

		public RiResearchOrganization getOwner() {
			return owner;
		}

		public void setOwner(RiResearchOrganization owner) {
			this.owner = owner;
		}

		public PortalUser getManager() {
			return manager;
		}

		public void setManager(PortalUser manager) {
			this.manager = manager;
		}
	
}
