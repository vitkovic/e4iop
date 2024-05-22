package e4i.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import e4i.domain.RequestState;
import e4i.domain.RiResearchOrganization;
import e4i.repository.PortalUserOrganizationRepository;
import e4i.repository.PortalUserRepository;
import e4i.repository.RequestForServiceRepository;
import e4i.repository.RequestRespondRepository;
import e4i.repository.RequestStateRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.repository.RiResearchOrganizationRepository;
import e4i.repository.RiServiceRepository;
import e4i.web.rest.dto.InfrastructureAccessModeDTO;
import e4i.web.rest.dto.InfrastructureAccessTypeDTO;
import e4i.web.rest.dto.InfrastructureCategoryDTO;
import e4i.web.rest.dto.InfrastructureNumberDTO;
import e4i.web.rest.dto.InfrastructureStatisticDTO;
import e4i.web.rest.dto.InfrastructureStatusDTO;
import e4i.web.rest.dto.OrganizationDetailsReportDTO;
import e4i.web.rest.dto.RiResearchOrganizationReportDTO;
import e4i.web.rest.dto.RiServiceStatisticDTO;
import e4i.web.rest.dto.RiSubcategoryDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.dto.ServiceStatisticResultDTO;

@Service
@Transactional
public class ReportService {
	
	private final Logger log = LoggerFactory.getLogger(ReportService.class);
	
	private final PortalUserOrganizationRepository portalUserOrganizationRepository;
	private final ResearchInfrastructureRepository researchInfrastructureRepository;
    private final UserService userService;
    private final PortalUserRepository portalUserRepository;
    private final RiResearchOrganizationRepository riResearchOrganizationRepository;    
    private final RiServiceRepository riServiceRepository;
    private final RequestForServiceRepository requestForServiceRepository;
    private final RequestRespondRepository requestRespondRepository;
    private final RequestStateRepository requestStateRepository;
    private final PortalUserOrganizationService portalUserOrganizationService;    
    
	
	public ReportService(PortalUserOrganizationRepository portalUserOrganizationRepository,
			ResearchInfrastructureRepository researchInfrastructureRepository, UserService userService,
			PortalUserRepository portalUserRepository,
			RiResearchOrganizationRepository riResearchOrganizationRepository, RiServiceRepository riServiceRepository,
			RequestForServiceRepository requestForServiceRepository, RequestRespondRepository requestRespondRepository,
			RequestStateRepository requestStateRepository,
			PortalUserOrganizationService portalUserOrganizationService) {
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.userService = userService;
		this.portalUserRepository = portalUserRepository;
		this.riResearchOrganizationRepository = riResearchOrganizationRepository;
		this.riServiceRepository = riServiceRepository;
		this.requestForServiceRepository = requestForServiceRepository;
		this.requestRespondRepository = requestRespondRepository;
		this.requestStateRepository = requestStateRepository;
		this.portalUserOrganizationService = portalUserOrganizationService;
		
	}

	public List<ServiceStatisticResultDTO> getServiceStatistic() {
		List<ServiceStatisticResultDTO> out = new ArrayList<ServiceStatisticResultDTO>();
		
		List<RiServiceStatisticDTO> result = riServiceRepository.getStatisticData();
		
		return this.getServices(result, new SearchDTO());
		
	}
	
	public List<ServiceStatisticResultDTO> getServiceStatistic(SearchDTO search) {
		List<ServiceStatisticResultDTO> out = new ArrayList<ServiceStatisticResultDTO>();
		RiResearchOrganization riOrganization = riResearchOrganizationRepository.findByPuOrganizationId(search.getOrganizationId());
		if(riOrganization != null) {		
			List<RiServiceStatisticDTO> result = riServiceRepository.getStatisticDataForResearchOrganization(riOrganization.getId());
			return this.getServices(result, search);
		}else
			return null;
		
	}

	
	private List<ServiceStatisticResultDTO> getServices(List<RiServiceStatisticDTO> result, SearchDTO search ) {
		List<ServiceStatisticResultDTO> out = new ArrayList<ServiceStatisticResultDTO>();
		
		SortedMap<Long, List<RiServiceStatisticDTO>> orderedAndGroupedResult = new TreeMap<Long,List<RiServiceStatisticDTO>>();
		for(RiServiceStatisticDTO rd : result) {
			if(orderedAndGroupedResult.containsKey(rd.getId())) {
				orderedAndGroupedResult.get(rd.getId()).add(rd);
			}else {
				List<RiServiceStatisticDTO> val = new ArrayList<RiServiceStatisticDTO>();
				val.add(rd);
				orderedAndGroupedResult.put(rd.getId(), val);
			}
		}
		
		for(Long l : orderedAndGroupedResult.keySet()) {
			List<RiServiceStatisticDTO> val = orderedAndGroupedResult.get(l);			
			int	numberOfRequests = 0;
			int	numberOfRealizedRequests = 0;
			int numberOfVotedRequests = 0;
			double votedValue = 0.;
			
			for(RiServiceStatisticDTO rd : val) {
				boolean serviceCounts = false; 
				if(rd.getRequestId() != null) {
					if(search.getFromDate() != null && search.getToDate() == null ) {
						if(rd.getRequestDate().isAfter(search.getFromDate().minusDays(1)))
							serviceCounts = true;
					}else if(search.getFromDate() != null && search.getToDate() != null ) {
						if(rd.getRequestDate().isAfter(search.getFromDate().minusDays(1)) && rd.getRequestDate().isBefore(search.getToDate().plusDays(1)))
							serviceCounts = true;
					}else if(search.getFromDate() == null && search.getToDate() != null ) {
						if(rd.getRequestDate().isBefore(search.getToDate().plusDays(1)))
							serviceCounts = true;
					}else
						serviceCounts = true;
					if(serviceCounts)
						numberOfRequests++;
					if(rd.getRequestRespondAnswer() != null && rd.getServiceOfferRespondAnswer() != null) {
						if(rd.getRequestRespondAnswer().equalsIgnoreCase("Request accepted") && 
								rd.getServiceOfferRespondAnswer().equalsIgnoreCase("Offer accepted")) {
							if(serviceCounts)
								numberOfRealizedRequests++;
						}
					}
					if(rd.getRequestRating() != null) {
						numberOfVotedRequests++;
						votedValue = votedValue + rd.getRequestRating();
					}
				}				
			}
			
			if(numberOfVotedRequests > 0) {
				votedValue = votedValue/numberOfVotedRequests;
			}
			
			ServiceStatisticResultDTO ssd = new ServiceStatisticResultDTO(val.get(0), numberOfRequests, numberOfRealizedRequests, votedValue);
			out.add(ssd);
		}
		
		return out;
	}


	public InfrastructureStatisticDTO getInfrastructureStatistic() {
		
		InfrastructureStatisticDTO out = new InfrastructureStatisticDTO();
		// broj infrastruktura ukupan i po vrsta, kategorijama, ...
/********************************************************************************************************************/		
		Long infrastructureNumber = researchInfrastructureRepository.count();
		// broj infrastruktura po statusima
		List<InfrastructureStatusDTO> statuses = researchInfrastructureRepository.getNumberPerStatuses();		
		// po access mode
		List<InfrastructureAccessModeDTO> accessModes = researchInfrastructureRepository.getNumberPerAccessMode();
		// po access type
		List<InfrastructureAccessTypeDTO> accessTypes = researchInfrastructureRepository.getNumberPerAccessType();
		// po kategorijama
		List<InfrastructureCategoryDTO> categories = researchInfrastructureRepository.getNumberPerCategory();
		// po podkategorijama
		//List<RiSubcategoryDTO> subcategories = researchInfrastructureRepository.getNumberPerSubcategory();
		
		InfrastructureNumberDTO in = new InfrastructureNumberDTO(infrastructureNumber, statuses, accessModes, 
				accessTypes, categories, null);
		out.setInfrastructureNumberDto(in);
		
		// broj usluga
/********************************************************************************************************************/		
		Long serviceNumber = riServiceRepository.count();
		out.setServiceNumber(serviceNumber);

		// broj zahteva za uslugama
/********************************************************************************************************************/		
		Long requestForServiceNumber = requestForServiceRepository.count();
		out.setRequestForServiceNumber(requestForServiceNumber);
		
		// broj odgovora na zahteve za uslugama
/********************************************************************************************************************/		
		Long requestRespondNUmber = requestRespondRepository.count();
		out.setRequestRespondNUmber(requestRespondNUmber);
		
		// broj realizovanih zahteva za uslugama (
/********************************************************************************************************************/
		RequestState requestState = requestStateRepository.findByStateEn("closed");
		
		Long realizedRequestForServceNUmber = requestForServiceRepository.countRealizedRequests();
				//requestForServiceRepository.countByStateId(requestState.getId());
		out.setRealizedRequestForServceNUmber(realizedRequestForServceNUmber);		
		
		return out;			
	}
	
	
	public Resource getServiceStatisticExcel() throws Exception{
		
		List<RiServiceStatisticDTO> result = riServiceRepository.getStatisticData();
		
		List<ServiceStatisticResultDTO> out =  this.getServices(result, new SearchDTO());
		
		return this.createServiceStatisticExcel(out);
		
	}
	
	public Resource getInfrastructureStatisticExcel() throws Exception{
		InfrastructureStatisticDTO result = this.getInfrastructureStatistic();				
		
		return this.createInfrastructureStatisticExcel(result);		
	}
	
	/**
	 * Kreira Excel fajl sa svim organizacijama i njihovom detaljnom statistikom
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public Resource getOrganizationsStatisticExcel(SearchDTO search) throws Exception{
		//lista organizacija koje zadovoljavaju uslove pretrage
		List<RiResearchOrganizationReportDTO> out = portalUserOrganizationService.searchForReport(search);
		
		for(RiResearchOrganizationReportDTO r : out) {
			SearchDTO searchNew = new SearchDTO(search,r.getId());
			r.setDetails(portalUserOrganizationService.getDetailsForRiOrganization(searchNew));	
			r.setServices(this.getServiceStatistic(searchNew));
		}
				
		return this.createOrganizationsStatisticExcel(out);		
	}
	
	
	/** 
	 * Kreira excel fajl za statistiku infrastrukture
	 * @param in
	 * @return 
	 */
    private ByteArrayResource createOrganizationsStatisticExcel(List<RiResearchOrganizationReportDTO> input) throws Exception {
    	XSSFWorkbook workbook;
        XSSFSheet sheet;
        
        workbook = new XSSFWorkbook();
        // write header
        sheet = workbook.createSheet("Statistika za organizacije");
        Row row = sheet.createRow(0);
        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        styleHeader.setFont(font);
        styleHeader.setFillBackgroundColor(IndexedColors.WHITE.index);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());        
        
        Cell cell = row.createCell(0);        
        cell.setCellValue((String) "Statistika vezana za organizacije na portalu");        
        cell.setCellStyle(styleHeader);        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        
        
        font = workbook.createFont();
        font.setFontHeight(12);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        CellStyle styleSecondHeader = workbook.createCellStyle();
        styleSecondHeader.setFillBackgroundColor(IndexedColors.WHITE.index);
        styleSecondHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleSecondHeader.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        styleSecondHeader.setFont(font);
        
        font = workbook.createFont();
        font.setFontHeight(12);
        
        CellStyle styleThirdHeader = workbook.createCellStyle();
        styleThirdHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleThirdHeader.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        
        CellStyle style1 = workbook.createCellStyle();
        style1.setFont(font);  
        
        CellStyle styleLeft = workbook.createCellStyle();        
        styleLeft.setFont(font);
        styleLeft.setAlignment(HorizontalAlignment.LEFT);
        
        CellStyle styleBold = workbook.createCellStyle();
        font = workbook.createFont();
        font.setFontHeight(12);
        font.setBold(true);
        styleBold.setFont(font);
        
        int rowCount = 0;
        for(RiResearchOrganizationReportDTO r : input) {
        	row = sheet.createRow(++rowCount);
        	// naziv i adresa organizacije
        	this.createExcelCell(row, 0, r.getName(), styleSecondHeader, sheet);
            this.createExcelCell(row, 1, r.getAddress(), styleSecondHeader, sheet);
            // kreiranje sekcije details
            row = sheet.createRow(++rowCount);                
            cell = row.createCell(1);        
            cell.setCellValue((String) "Zbirno");        
            cell.setCellStyle(styleThirdHeader);        
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 1, 3));
            if(r.getDetails() != null) {
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj infrastruktura", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getInfrastructureNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj objavljenih usluga", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getServiceNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj traženih usluga od organizacije", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getRequiredServiceNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj realizovanih usluga", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getRealizedServiceNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj usluga koje je organizacija tražila", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getRequestedServiceNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj ponuda koje je organizacija dobila za svoje zahteve", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getOfferedServiceNumber(), styleLeft, sheet);
	            row = sheet.createRow(++rowCount);
	            this.createExcelCell(row, 2, "Broj ponuda koje je organizacija tražila i realizovala", style1, sheet);
	            this.createExcelCell(row, 3, r.getDetails().getRealizedServiceNumber1(), styleLeft, sheet);
            }
            
            // kreiranje sekcije servisi
            row = sheet.createRow(++rowCount);                
            cell = row.createCell(1);        
            cell.setCellValue((String) "Servisi");        
            cell.setCellStyle(styleThirdHeader);        
            sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 1, 8));
            
            if(r.getServices() != null) {     
            	row = sheet.createRow(++rowCount);
	            createExcelCell(row, 2, "Naziv", styleBold, sheet);
	            createExcelCell(row, 3, "Opis", styleBold, sheet);
	            createExcelCell(row, 4, "Infrastruktura", styleBold, sheet);
	            createExcelCell(row, 5, "Raspoloživost", styleBold, sheet);
	            createExcelCell(row, 6, "Tip", styleBold, sheet);
	            createExcelCell(row, 7, "Broj zahteva", styleBold, sheet);
	            createExcelCell(row, 8, "Broj realizovanih zahteva", styleBold, sheet);
	            createExcelCell(row, 9, "Prosečna ocena", styleBold, sheet);	            
	            
	            for (ServiceStatisticResultDTO record: r.getServices()) {
	                row = sheet.createRow(++rowCount);
	                int columnCount = 2;
	                createExcelCell(row, columnCount++, record.getNameSr(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getDescriptionSr(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getResearchInfrastructure().getNameSr(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getAvailability().getAvailability(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getSubtypes().getSubtype(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getRequestForServiceNumber(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getRealizedRequestsForServiceNumber(), style1, sheet);
	                createExcelCell(row, columnCount++, record.getRequestRating(), style1, sheet);
	            }
            }                       
        }        
                
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(8);
        sheet.autoSizeColumn(9);               
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        
        return new ByteArrayResource(outputStream.toByteArray());        
    }
	
	/** 
	 * Kreira excel fajl za statistiku infrastrukture
	 * @param in
	 * @return 
	 */
    private ByteArrayResource createInfrastructureStatisticExcel(InfrastructureStatisticDTO input) throws Exception {
    	XSSFWorkbook workbook;
        XSSFSheet sheet;
        
        workbook = new XSSFWorkbook();
        // write header
        sheet = workbook.createSheet("infrastruktura");
        Row row = sheet.createRow(0);
        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        styleHeader.setFont(font);
        styleHeader.setFillBackgroundColor(IndexedColors.WHITE.index);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());        
        
        Cell cell = row.createCell(0);        
        cell.setCellValue((String) "Statistika vezana za infrastrukturu na portalu");        
        cell.setCellStyle(styleHeader);        
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        
        font = workbook.createFont();
        font.setFontHeight(12);
        CellStyle styleSecondHeader = workbook.createCellStyle();
        styleSecondHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleSecondHeader.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        
        CellStyle style1 = workbook.createCellStyle();
        style1.setFont(font);  
        
        int rowCount = 0;
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        this.createExcelCell(row, 0, "Ukupan broj registrovane infrastrukture", style1, sheet);
        this.createExcelCell(row, 1, input.getInfrastructureNumberDto().getInfrastructureNumber(), style1, sheet);
        
        row = sheet.createRow(++rowCount);                
        cell = row.createCell(2);        
        cell.setCellValue((String) "Po statusu");        
        cell.setCellStyle(styleSecondHeader);        
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 2, 3));               
        
        for(InfrastructureStatusDTO status : input.getInfrastructureNumberDto().getNumberPerStatuses()) {
        	row = sheet.createRow(++rowCount);                
            this.createExcelCell(row, 2, status.getStatus().getStatus(), style1, sheet);
            this.createExcelCell(row, 3, status.getStatusNumber(), style1, sheet);	
        }
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        cell = row.createCell(2);        
        cell.setCellValue((String) "Po načinu pristupa");        
        cell.setCellStyle(styleSecondHeader);        
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 2, 3));               
        
        for(InfrastructureAccessTypeDTO atype : input.getInfrastructureNumberDto().getNumberPerAccessTypes()) {
        	row = sheet.createRow(++rowCount);                
            this.createExcelCell(row, 2, atype.getRiAccessType().getType(), style1, sheet);
            this.createExcelCell(row, 3, atype.getRiAccessTypeNumber(), style1, sheet);	
        }
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        cell = row.createCell(2);        
        cell.setCellValue((String) "Po režimu pristupa");        
        cell.setCellStyle(styleSecondHeader);        
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 2, 3));               
        
        for(InfrastructureAccessModeDTO amode : input.getInfrastructureNumberDto().getNumberPeraccessModes()) {
        	row = sheet.createRow(++rowCount);                
            this.createExcelCell(row, 2, amode.getRiAccessMode().getMode(), style1, sheet);
            this.createExcelCell(row, 3, amode.getRiAccessModeNUmber(), style1, sheet);	
        }
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        cell = row.createCell(2);        
        cell.setCellValue((String) "Po kategorijama");        
        cell.setCellStyle(styleSecondHeader);        
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 2, 3));               
        
        for(InfrastructureCategoryDTO cat : input.getInfrastructureNumberDto().getNumberPerCategories()) {
        	row = sheet.createRow(++rowCount);                
            this.createExcelCell(row, 2, cat.getRiCategoryDto().getCategory(), style1, sheet);
            this.createExcelCell(row, 3, cat.getRiCategoryNumber(), style1, sheet);	
        }
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        this.createExcelCell(row, 0, "Ukupan broj usluga", style1, sheet);
        this.createExcelCell(row, 1, input.getServiceNumber(), style1, sheet);
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        this.createExcelCell(row, 0, "Ukupan broj zahteva za uslugama", style1, sheet);
        this.createExcelCell(row, 1, input.getRequestForServiceNumber(), style1, sheet);
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        this.createExcelCell(row, 0, "Ukupan broj odgovora na zahteve (ponuda)", style1, sheet);
        this.createExcelCell(row, 1, input.getRequestRespondNUmber(), style1, sheet);
        
        row = sheet.createRow(++rowCount);
        
        row = sheet.createRow(++rowCount);                
        this.createExcelCell(row, 0, "Ukupan broj realizovaniih zahteva", style1, sheet);
        this.createExcelCell(row, 1, input.getRealizedRequestForServceNUmber(), style1, sheet);
        
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        
        
       // InfrastructureAccessTypeDTO
        
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        
        return new ByteArrayResource(outputStream.toByteArray());
        
    }
	

	/** 
	 * Kreira excel fajl za statistiku usluga
	 * @param in
	 * @return 
	 */
    private ByteArrayResource createServiceStatisticExcel(List<ServiceStatisticResultDTO> services) throws Exception {
    	XSSFWorkbook workbook;
        XSSFSheet sheet;
        
        workbook = new XSSFWorkbook();
        // write header
        sheet = workbook.createSheet("Usluge");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        //style.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());
        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillBackgroundColor(IndexedColors.WHITE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.LIME.getIndex());
        
        createExcelCell(row, 0, "Naziv", style, sheet);
        createExcelCell(row, 1, "Opis", style, sheet);
        createExcelCell(row, 2, "Infrastruktura", style, sheet);
        createExcelCell(row, 3, "Raspoloživost", style, sheet);
        createExcelCell(row, 4, "Tip", style, sheet);
        createExcelCell(row, 5, "Broj zahteva", style, sheet);
        createExcelCell(row, 6, "Broj realizovanih zahteva", style, sheet);
        createExcelCell(row, 7, "Prosečna ocena", style, sheet);
        
        // kreiranje sadrzaja
        int rowCount = 1;
        
        font = workbook.createFont();
        font.setFontHeight(12);
        CellStyle style1 = workbook.createCellStyle();
        style1.setFont(font);        
        for (ServiceStatisticResultDTO record: services) {
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createExcelCell(row, columnCount++, record.getNameSr(), style1, sheet);
            createExcelCell(row, columnCount++, record.getDescriptionSr(), style1, sheet);
            createExcelCell(row, columnCount++, record.getResearchInfrastructure().getNameSr(), style1, sheet);
            createExcelCell(row, columnCount++, record.getAvailability().getAvailability(), style1, sheet);
            createExcelCell(row, columnCount++, record.getSubtypes().getSubtype(), style1, sheet);
            createExcelCell(row, columnCount++, record.getRequestForServiceNumber(), style1, sheet);
            createExcelCell(row, columnCount++, record.getRealizedRequestsForServiceNumber(), style1, sheet);
            createExcelCell(row, columnCount++, record.getRequestRating(), style1, sheet);
        }
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        
        return new ByteArrayResource(outputStream.toByteArray());
        
    }
    
    private void createExcelCell(Row row, int columnCount, Object valueOfCell, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        
        if(valueOfCell == null) return;
        
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        }  else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }


}
