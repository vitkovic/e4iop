package e4i.web.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import dbapi.DBManage;
import e4i.domain.InfrastructureImage;
import e4i.domain.ResearchInfrastructure;
import e4i.domain.ResearchInstitution;
import e4i.message.ResponseMessage;
import e4i.model.FileInfo;
import e4i.repository.InfrastructureImageRepository;
import e4i.repository.ResearchInfrastructureRepository;
import e4i.service.FilesStorageService;
import e4i.web.rest.errors.BadRequestAlertException;
import e4i.web.rest.errors.InfrastructureImagesLimitException;
import io.swagger.client.api.*;
import io.swagger.client.model.Company__1;
import io.swagger.client.model.Institution;
import io.swagger.client.model.Researcher;
import io.swagger.client.model.Zaposlenja;
import io.swagger.client.model.Zvanja;


@Controller
@RequestMapping("/api")
public class ExternalApiController {

  private final Logger log = LoggerFactory.getLogger(ResearchInfrastructureResourceExtension.class);	
  
  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  public RequestHandler jsonhandler = new RequestHandler();

  @GetMapping("/companies__1/{from}")
  public List<Company__1>  getCompanies(@PathVariable String from) {
	  try {
		    jsonhandler = new RequestHandler();
			//List<Company__1> companies = jsonhandler.getCompanies(""); // get all companies from api i ubacuje u PUO ime, maticni broj i PIB
			List<Company__1> companies = jsonhandler.getCompanies(from); // maticni broj or regNo and groups
			DBManage mdb = new DBManage();
			mdb.deleteCompanies(); // if you want to delete all companies from PUO
			mdb.setInsertCOM(companies);
			mdb.insertCompanies(); // inserts into PUO
		    return companies;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
  }
  
  @GetMapping("/researchers/{from}")
  public List<ResearchInstitution> getResearchers(@PathVariable String from) {
	  try {
		    jsonhandler = new RequestHandler();
			//List<Company__1> companies = jsonhandler.getCompanies(""); // get all companies from api i ubacuje u PUO ime, maticni broj i PIB
		    List<Institution> insts = jsonhandler.getInstitutions(from);
			//System.out.println("zavrsio inst");
			List<ResearchInstitution> riss = formRIModel(insts);
			DBManage mdb = new DBManage();
		//	mdb.connect();
			List<ResearchInstitution> lri = mdb.selectAllRI();
			mdb.checkListsInstitutions(riss, lri);
			mdb.insertRecord();
			mdb.updateRecord();
		    return riss;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
  }
  
  @GetMapping("/copyripuo/")
  public boolean copyFromRItoPUO() {
	  try {
		    DBManage mdb = new DBManage();
			mdb.delete_PUORItoTRIORG();
			mdb.delete_RItoPUO();
			mdb.copyRItoPUO();
			mdb.copyPUORItoTRIORG();
		    return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
  }
  
  @GetMapping("/institutions/{from}")
  public List<e4i.domain.Researcher> getInstitutions(@PathVariable String from) {
	  try {
		    jsonhandler = new RequestHandler();
		    DBManage mdb = new DBManage();
		    List<e4i.domain.Researcher> db_res = mdb.selectAllRE();
			//from = "2023-09-01";
			List<Researcher> research = jsonhandler.getResearchers(from);
			List<e4i.domain.Researcher> ris = formResearchers(research);
			
			mdb.checkListsResearchers(ris,db_res);
			
			mdb.insertRecordResearcher();
			mdb.updateRecordResearcher();
			
			
			List<Researcher> researchdb = mdb.selectAllRERI();
			mdb.checkListsResearchersInstitutions(research,researchdb);
			
			mdb.deleteFORRERI();
		    return ris;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
  }


  public static List<ResearchInstitution> formRIModel(List<Institution> institutions) 
	{
		//List<PortalUserOrganization> puos=  new ArrayList<PortalUserOrganization>();
		//PortalUserOrganization puo = null;
		
		ResearchInstitution ris = null;
		List<ResearchInstitution> riss = new ArrayList<ResearchInstitution>();
		EntityManager em;
		
		
		for (final Institution inst : institutions) {
	          
					
			ris = new ResearchInstitution();
			
			
			ris.setId(Integer.toUnsignedLong(inst.getNioID()));
			
			ris.setNumber(inst.getNioBroj());
			
			ris.setAddress(inst.getAdresaSedista());
			
			ris.setRegisterNumber(inst.getMaticniBroj());
			
			ris.setVatNumber(inst.getPib());
			
			ris.setWebAdress(inst.getWebAdresa());
			
			ris.setTypeName(inst.getVrstaNioNaziv());
			
			ris.setOwnerStructureName(inst.getVlasnickiStatusNaziv());
			
			ris.setCeoName(inst.getRukovodilacIme() !=null ? inst.getRukovodilacIme().toString():"");
			
			ris.setCeoLastName(inst.getRukovodilacPrezime() != null ? inst.getRukovodilacPrezime().toString():"");
			
			ris.setCeoIbi(inst.getRukovodilacIbi() != null ? inst.getRukovodilacIbi().toString():"");
			
			ris.setState(inst.getStanje());
			
			ris.setName(inst.getNaziv());
			
			ris.setNameEn(inst.getNazivEngleski());
			
			riss.add(ris);
			
		
		}
		
		return riss;
	}
  @SuppressWarnings("deprecation")
	public static List<e4i.domain.Researcher> formResearchers(List<Researcher> researchers) 
	{
		List<e4i.domain.Researcher> ris=  new ArrayList<e4i.domain.Researcher>();;
		e4i.domain.Researcher re = null;
		
		
		for (final Researcher researcher : researchers) {
			
			re = new e4i.domain.Researcher();
			
			re.setId(researcher.getId().longValue());
			
			re.setFirstName(researcher.getIme());
			
			re.setLastName(researcher.getPrezime());
			
			re.setMiddleName("");
			
			if (researcher.getDatumRodjenja() != null) {

				LocalDate date = LocalDate.parse(researcher.getDatumRodjenja());
			
				re.setBirthDate(date);
			
			} else 
			{
				
	    		LocalDate date = LocalDate.of(1900,1,1);
				re.setBirthDate(date);
			}
			
			List<Zaposlenja> zp = researcher.getZaposlenja();
			List<Zvanja> zv = researcher.getZvanja();
			Set<ResearchInstitution> riss = new HashSet<ResearchInstitution>();
			
			for (final Zaposlenja zpa : zp) {
				ResearchInstitution riindividual = new ResearchInstitution();
				riindividual.setId(zpa.getId().longValue());
				riindividual.setName(zpa.getNioBroj()); // ovo je broj organizacije koji je veze za sve ostalo
				
				riss.add(riindividual);
			}
			
		
			for (final Zvanja zva : zv) {
				ResearchInstitution riindividual = new ResearchInstitution();
				riindividual.setId(zva.getId().longValue());
				riindividual.setName(zva.getNioBroj()); // ovo je broj organizacije koji je veze za sve ostalo
				
				riss.add(riindividual);
			}
			
			re.setInstitutions(riss);
			
		   ris.add(re);
		}
		
		return ris;
	}
}