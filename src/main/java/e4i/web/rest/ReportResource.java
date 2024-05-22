package e4i.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import e4i.repository.PortalUserOrganizationRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.service.PortalUserOrganizationService;
import e4i.service.ReportService;
import e4i.web.rest.dto.InfrastructureStatisticDTO;
import e4i.web.rest.dto.SearchDTO;
import e4i.web.rest.dto.ServiceStatisticResultDTO;

@RestController
@RequestMapping("/api")
@Transactional
public class ReportResource {
	
	private final Logger log = LoggerFactory.getLogger(ReportResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	    
	private final ResearchInfrastructureRepository researchInfrastructureRepository;
	private final PortalUserOrganizationRepository portalUserOrganizationRepository;
	private final PortalUserOrganizationService portalUserOrganizationService;
	private final ReportService reportService;	 
	 
	public ReportResource(ResearchInfrastructureRepository researchInfrastructureRepository,
			PortalUserOrganizationRepository portalUserOrganizationRepository,
			PortalUserOrganizationService portalUserOrganizationService, ReportService reportService) {
		super();
		this.researchInfrastructureRepository = researchInfrastructureRepository;
		this.portalUserOrganizationRepository = portalUserOrganizationRepository;
		this.portalUserOrganizationService = portalUserOrganizationService;
		this.reportService = reportService;
	}
	 
	
	/**
	 * Statistika za svu infrastrukturu
	 * @return
	 */
	 @GetMapping("/reports/infrastructure-statistic")
	 public InfrastructureStatisticDTO getReportInfrastructureStatistic() {
		 log.debug("REST request to get statistic for infrastructures");
		 InfrastructureStatisticDTO out = reportService.getInfrastructureStatistic();
		 return out;        
	 }
	 
	 /**
	  * Statistika za sve usluge, sa brojem zahteva i brojem realizovanih zahteva
	  * @return
	  */
	 @GetMapping("/reports/service-statistic")
	 public List<ServiceStatisticResultDTO> getReportServiceStatistic() {
		 log.debug("REST request to get statistic for services");
		 List<ServiceStatisticResultDTO> out = reportService.getServiceStatistic();
		 return out;        
	 }
	 
	 
	 /**
	  * Statistika za sve usluge, sa brojem zahteva i brojem realizovanih zahteva
	  * @return
	  */
	 @RequestMapping(value = "/reports/service-statistic/downloadExcel",
     method = RequestMethod.GET)
	 public ResponseEntity<Resource> getReportServiceStatisticExcel() throws Exception{
		 log.debug("REST request to get excel file with statistic for services");
		 
		 Resource out = reportService.getServiceStatisticExcel();
		 String excelReport = "Excel report.xlsx";
		 //return ResponseEntity.ok()
			//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + excelReport + "\"").body(out);  
		 return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excelReport)
			        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(out);
		 
	 }
	 
	 /**
	  * Statistika za sve usluge, sa brojem zahteva i brojem realizovanih zahteva
	  * @return
	  */
	 @RequestMapping(value = "/reports/infrastructure-statistic/downloadExcel",
     method = RequestMethod.GET)
	 public ResponseEntity<Resource> getReportInfrastructureStatisticExcel() throws Exception{
		 log.debug("REST request to get excel file with statistic for services");
		 
		 Resource out = reportService.getInfrastructureStatisticExcel();
		 String excelReport = "Excel report.xlsx";
		 //return ResponseEntity.ok()
			//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + excelReport + "\"").body(out);  
		 return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excelReport)
			        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(out);
		 
	 }
	 
	 /**
	  * Statistika za sve usluge, sa brojem zahteva i brojem realizovanih zahteva
	  * @return
	  */
	 @RequestMapping(value = "/reports/organizations-statistic/downloadExcel",
     method = RequestMethod.POST)
	 public ResponseEntity<Resource> getReportOrganizationsStatisticExcel(@RequestBody SearchDTO search) throws Exception{
		 log.debug("REST request to get excel file with statistic for services");
		 
		 Resource out = reportService.getOrganizationsStatisticExcel(search);
		 String excelReport = "Excel report.xlsx";
		 //return ResponseEntity.ok()
			//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + excelReport + "\"").body(out);  
		 return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excelReport)
			        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(out);
		 
	 }
	 
	 
	 
	 /**
	  * LIsta svih usluga koje nudi jedna organizacija, sa brojevima 
	  * @param id
	  * @return
	  */
	 @PostMapping("/reports/service-statistic-nio")
	 public List<ServiceStatisticResultDTO> getReportServiceByNio(@RequestBody SearchDTO search ) {
		 log.debug("REST request to get statistic for services by nio");
		 List<ServiceStatisticResultDTO> out = reportService.getServiceStatistic(search);
		 if(out == null)
			 return new ArrayList<ServiceStatisticResultDTO>();
		 return out;        
	 }
	 

}
