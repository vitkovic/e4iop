package e4i.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.PortalUser;
import e4i.domain.PortalUserOrganization;
import e4i.domain.RiResearchOrganization;
import e4i.domain.User;
import e4i.repository.PortalUserOrganizationRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.RequestForServiceRepository;
import e4i.repository.RequestRespondRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.security.AuthoritiesConstants;
import e4i.security.SecurityUtils;
import e4i.web.rest.dto.OrganizationDetailsReportDTO;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.errors.ResearchOrganizationMustExistException;


@Service
@Transactional
public class PortalUserOrganizationService {
	private final Logger log = LoggerFactory.getLogger(PortalUserOrganizationService.class);

	private final PortalUserOrganizationRepository portalUserOrganizationRepository;
	private final ResearchInfrastructureRepository researchInfrastructureRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;    
    private final RiServiceRepository riServiceRepository;
    private final RequestForServiceRepository requestForServiceRepository;
    private final RequestRespondRepository requestRespondRepository;    

    public PortalUserOrganizationService(PortalUserOrganizationRepository portalUserOrganizationRepository,
			ResearchInfrastructureRepository researchInfrastructureRepository, UserService userService,
			PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository, RiServiceRepository riServiceRepository,
			RequestForServiceRepository requestForServiceRepository,
			RequestRespondRepository requestRespondRepository) {
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.riServiceRepository = riServiceRepository;
		this.requestForServiceRepository = requestForServiceRepository;
		this.requestRespondRepository = requestRespondRepository;
	}

    public Page<PortalUserOrganization> findAll(Pageable pageable) {
    	if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){              
        	return  portalUserOrganizationRepository.findAll(pageable);
        }else {
        	 Optional<User> currentUser = userService.getUserWithAuthorities();
             User user = currentUser.get();             
             PortalUser pUser  = portalUserRepository.findByUserId(user.getId());
             List<String> userRoles = pUser.pronadjiPortalUserRolesAbbreviations();
             
             if(userRoles.contains("RPRIPO")) {
            	 return portalUserOrganizationRepository.findPage(pUser.getUserOrganization().getId(), pageable);
             }else if(userRoles.contains("PA")) {
            	 return  portalUserOrganizationRepository.findAll(pageable);
             }else {            	
            	 return null;
             }
        }
    }
    
    
    public OrganizationDetailsReportDTO getDetailsForRiOrganization(SearchDTO search) {    	
    	
    	RiResearchOrganization ri = riResearchOrganizationRepository.findByPuOrganizationId(search.getOrganizationId());    	
    	Long id = 0L;
    	if(ri != null) {
    		id = ri.getId();    		
    	}else
    		return null;
		
    	//broj infrastruktura
    	List<Long> infrastructureIDs = researchInfrastructureRepository.findIdsByOwnerId(id);
    	int infrastructureNumber = 0;
    	if(infrastructureIDs != null)
    		infrastructureNumber = infrastructureIDs.size();
    	
    	//broj usluga
    	List<Long> serviceIds = riServiceRepository.findIdsForResearchInfrastructures(infrastructureIDs);    	    	
    	int serviceNumber = 0;
    	if(serviceIds != null)
    		serviceNumber = serviceIds.size();
    	
    	//broj trazenih usluga
    	ZonedDateTime dateFrom = null;
    	int dateFromNotExist = 1;
    	if(search.getFromDate() != null) {    		
    		dateFrom = search.getFromDate().atStartOfDay(ZoneId.systemDefault());
    		dateFromNotExist = 0;
    	}
    	ZonedDateTime dateTo = null;
    	int dateToNotExist = 1;
    	if(search.getToDate() != null) {
    		dateTo = search.getToDate().plusDays(1).atStartOfDay(ZoneId.systemDefault());
    		dateToNotExist = 0;
    	}
    	List<Long> requestForServiceIds = requestForServiceRepository.findByServicesIdsIn(serviceIds, dateFromNotExist, 
    			dateToNotExist, dateFrom, dateTo);
    	int requiredServiceNumber = 0;
    	if(requestForServiceIds != null) {
    		requiredServiceNumber = requestForServiceIds.size(); 
    	}
    	
    	//broj realizovanih usluga od NIO
    	List<Long> requestRespondIds = requestRespondRepository.findByRequestForServiceIdIn(requestForServiceIds);
    	List<Long> realizedRequestForServiceIds = requestRespondRepository.findRealizedServices(requestRespondIds, dateFromNotExist, 
    			dateToNotExist, dateFrom, dateTo);
    	int realizedServiceNumber = 0;
    	if(realizedRequestForServiceIds != null) {
    		realizedServiceNumber = realizedRequestForServiceIds.size(); 
    	}
    	
    	// broj usluga koje je organizacija trazila od drugih
    	int requestedServiceNumber = 0;
    	// korisnici koji su iz te organizacije
    	List<Long> portalUserIds = portalUserRepository.findIdsUserOrganizationId(search.getOrganizationId());
    	List<Long> requestedServices = requestForServiceRepository.findByUsersIdsIn(portalUserIds, dateFromNotExist, 
    			dateToNotExist, dateFrom, dateTo);
    	if(requestedServices != null)
    		requestedServiceNumber = requestedServices.size(); 
    	
    	// broj ponuda koje je nio dobila za svoje zahteve
    	int offeredServiceNumber = 0;
    	List<Long> offeredServicesIds = requestRespondRepository.findOfferedServices(requestedServices, dateFromNotExist, 
    			dateToNotExist, dateFrom, dateTo);
    	if(offeredServicesIds != null)
    		offeredServiceNumber = offeredServicesIds.size();
    	
    	// broj usluga koje NIO ugovorila po svojim zahtevima
    	int realizedServiceNumber1 = 0;
    	List<Long> requestedAndRealizedServiceIds = requestRespondRepository.findRealizedServices(offeredServicesIds, dateFromNotExist, 
    			dateToNotExist, dateFrom, dateTo);
    	if(requestedAndRealizedServiceIds != null)
    		realizedServiceNumber1 = requestedAndRealizedServiceIds.size();
    	
    	OrganizationDetailsReportDTO out = new OrganizationDetailsReportDTO(infrastructureNumber, serviceNumber, requiredServiceNumber,
    			realizedServiceNumber, requestedServiceNumber,offeredServiceNumber,realizedServiceNumber1);

    	return out;     
	}
	
	
	public List<RiResearchOrganizationReportDTO> searchForReport(SearchDTO search) {
		if(search.getCurrentLanguage().equals("en")) {
        	List<RiResearchOrganizationReportDTO> out = riResearchOrganizationRepository.findForSearchEn(search.getSearchTerms());
    		return out;
        }else {
        	int nioNotRequested = 1;
        	int firmNotRequested = 1;
        	int keywordsNotRequested = 1;
        	if(search.getSelType() != null && !search.getSelType().equals("")) {
        		if(search.getSelType().equals("1"))
        				nioNotRequested = 0;
        			
        		if(search.getSelType().equals("2"))
        				firmNotRequested = 0;
        		
        	}
        	
        	if(search.getSearchTerms() != null && !search.getSearchTerms().trim().equals(""))
        		keywordsNotRequested = 0;
        	
        	List<RiResearchOrganizationReportDTO> out = portalUserOrganizationRepository.findForSearchSr("%" + search.getSearchTerms().toLowerCase() + "%",keywordsNotRequested, 
        			nioNotRequested, firmNotRequested);
        	return out;
        }
	}

}
