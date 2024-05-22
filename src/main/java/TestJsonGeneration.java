import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ColumnResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;

import org.springframework.transaction.annotation.Transactional;

import dbapi.DBManage;
import e4i.domain.PortalUserOrganization;
import e4i.domain.ResearchInstitution;
import e4i.repository.ResearchInstitutionRepository;
import io.swagger.client.ApiException;
import io.swagger.client.api.*;
import io.swagger.client.model.Company__1;
import io.swagger.client.model.Institution;
import io.swagger.client.model.Researcher;
import io.swagger.client.model.Zaposlenja;
import io.swagger.client.model.Zvanja;

@Transactional
public class TestJsonGeneration {


//	public RequestHandler jsonhandler;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestHandler jsonhandler = new RequestHandler();
		
	/*
		try {
			// RequestHandler jsonhandler = new RequestHandler();
			 URL url = new URL("http://localhost/riportal/companies.json");
			jsonhandler.convertJsonToJavaClass(url, new File("c:\\test"), "", "Company");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	*/	
		// !!! kompanije - prvi api - JSON je komplikovan, pa zahteva dodatnu obradu
		
		try {
			//List<Company__1> companies = jsonhandler.getCompanies(""); // get all companies from api i ubacuje u PUO ime, maticni broj i PIB
			List<Company__1> companies = jsonhandler.getCompanies("100662813"); // maticni broj or regNo and groups
			DBManage mdb = new DBManage();
			//mdb.deleteCompanies(); // if you want to delete all companies from PUO
			//mdb.insertCOM = companies;
			//mdb.insertCompanies(); // inserts into PUO
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// !!!KOPIRANJE IZ RI u PUO
		/*
		try {
			DBManage mdb = new DBManage();
			mdb.delete_PUORItoTRIORG();
			mdb.delete_RItoPUO();
			mdb.copyRItoPUO();
			mdb.copyPUORItoTRIORG();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// innstitucije i istrazivaci - drugi api
		/* iskljucio sam da ne tupi mnogo, ali mogu da rade zajedno sa kompanijama
		try {
			String from ="";
			jsonhandler = new RequestHandler();
				from = "2020-01-01";
				List<Institution> insts = jsonhandler.getInstitutions(from);
				System.out.println("zavrsio inst");
				List<ResearchInstitution> riss = formRIModel(insts);
				DBManage mdb = new DBManage();
			//	mdb.connect();
				List<ResearchInstitution> lri = mdb.selectAllRI();
				mdb.checkListsInstitutions(riss, lri);
				mdb.insertRecord();
				mdb.updateRecord();
				//List<ResearchInstitution> lri = mdb.selectAllRI();
				System.out.println("zavrsio institution");
			
				//DBManage mdb = new DBManage();
				List<e4i.domain.Researcher> db_res = mdb.selectAllRE();
				from = "2023-09-01";
				List<Researcher> research = jsonhandler.getResearchers(from);
				List<e4i.domain.Researcher> ris = formResearchers(research);
				
				mdb.checkListsResearchers(ris,db_res);
				
				mdb.insertRecordResearcher();
				mdb.updateRecordResearcher();
				
				
				List<Researcher> researchdb = mdb.selectAllRERI();
				mdb.checkListsResearchersInstitutions(research,researchdb);
				
				mdb.deleteFORRERI();
				mdb.insertRecordResearcherInstitution();
			//	mdb.updateRecordResearcherInstitution();
				
				System.out.println("zavrsio research");
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
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
			
			
			//ResearchInstitution result = researchInstitutionRepository.save(ris);
			
				
			/*
			puo = new PortalUserOrganization();
			
			//puo.setRegistrationNumber(inst.getNioBroj());
			
			puo.setLegalNameSr(inst.getNaziv());
			
			puo.setLegalNameEn(inst.getNazivEngleski());	
			
			puo.setWebAddress(inst.getWebAdresa());
			
			puo.setPib(inst.getPib());
			
			puo.setRegistrationNumber(inst.getMaticniBroj());
			
			puo.setAddress(inst.getAdresaSedista());
			
			puos.add(puo);
			
			*/
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
