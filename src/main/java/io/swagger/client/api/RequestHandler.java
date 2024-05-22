package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
//import io.swagger.client.api.NaucniSoftverEiApi;
import io.swagger.client.auth.HttpBasicAuth;
import io.swagger.client.auth.OAuth;
import io.swagger.client.model.Company__1;
import io.swagger.client.model.Institution;
import io.swagger.client.model.Researcher;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
//import io.swagger.client.model.IstrazivacZaNaucniSoftver;
//import io.swagger.client.model.NioZaNaucniSoftver;
import org.threeten.bp.LocalDate;
//import rs.ac.bg.rcub.util.ConfigurationManager;
//import rs.ac.bg.rcub.util.LoggingHelper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.codemodel.*;
import org.json.*;
public class RequestHandler {
  
	private int auth_type=0;
	
	private static final InstitutionApi apiInstance;
	private static final CompanyApi apiInstanceCompany;
	static String basePathInst;
	static String basePathCompanies;
	static ApiClient defaultClient = Configuration.getDefaultApiClient();
	
    static {
        
        defaultClient.setConnectTimeout(600*1000);
        defaultClient.setReadTimeout(30*600*1000);
        HttpBasicAuth basic = (HttpBasicAuth) defaultClient.getAuthentication("basic");
      //  OAuth token = (OAuth) defaultClient.getAuthentication("companies_auth");
        basePathInst =  "https://prod.admin.enauka.gov.rs/eNaukaProjektniSoftverApi/api/naucniSoftverEI";
        basePathCompanies =  "https://api.cubeteam.com/webapi/v5/data-changes";
        
        defaultClient.setBasePath(basePathInst);
 
        apiInstance = new InstitutionApi();
        apiInstanceCompany = new CompanyApi();
        
    }
    
    public void setAuth() {
    	
    }
   
    /**
     * Get Companies from API by novijeod
     * If we need additional api methods we can add.
     * @param status - for now it is just date for novijeod
     * @return List with Institution objects
     * @throws ApiException
     */
    public List<Company__1> getCompanies(String status) throws ApiException {
    	defaultClient.setBasePath(basePathCompanies);
    	String receivedJSONString = apiInstanceCompany.findCompaniesByStatus(status);
    	List<Company__1> lsc = this.convertToCompany(receivedJSONString);
    	return lsc;
    	//return null;
    }
    
  
    public List<Company__1> convertToCompany(String receivedJSONString){
    	
    	ArrayList<Company__1> lsc = new ArrayList<Company__1>();
    	try {
    		JSONObject jsonObj = new JSONObject(receivedJSONString);
    		
    		if (jsonObj.get("data") != null) {
	    		JSONArray arr = (JSONArray) jsonObj.get("data");
	    		// Treba videti za grupu adresa!!!! - ona nije u sonovnim podesavanjima.
	    		ArrayList<String> reg = new ArrayList<String>( );
	    		for(int i = 0; i< arr.length();i++) {
	    			Company__1 cmp = new Company__1();
	    			JSONObject obj = arr.getJSONObject(i);
	    			JSONObject cmpJSON = (JSONObject) obj.get("company");
	    			String no = cmpJSON.getString("regNo");
	    			boolean exists = false;
	    			if (reg.size() > 0) {
	    				for (String rg:reg) {
	    					if (no.equals(rg)) {
	    						exists = true;
	    					}
	    				}
	    			}
	    			if (!exists) {
	    				cmp.setName(cmpJSON.get("name").toString());
	    				cmp.setRegNo(cmpJSON.get("regNo")!=null?cmpJSON.get("regNo").toString():"");
	    				cmp.setTaxNo(cmpJSON.get("taxNo")!=null?cmpJSON.get("taxNo").toString():"");
	    				lsc.add(cmp);
	    				reg.add(no);
	    			}	
	    		}
	    		
	    	}
    	} catch (JSONException jsex) 
    	{
    		System.out.println(jsex.getMessage());
    	} catch (Exception ex) 
    	{
    		System.out.println(ex.getMessage());
    	}
    	return lsc;
    }
    
    
    
    /**
     * Get Institutions from API by novijeod
     * If we need additional api methods we can add.
     * @param status - for now it is just date for novijeod
     * @return List with Institution objects
     * @throws ApiException
     */
    public List<Institution> getInstitutions(String status) throws ApiException {
    	defaultClient.setBasePath(basePathInst);
    	return apiInstance.findInstitutionsByStatus(status);
    	//return null;
    }
    
    /**
     * Method for creating Class Models(Beans) from API call. It can be used only once for classes formation.
     * @param inputJsonUrl
     * @param outputJavaClassDirectory
     * @param packageName
     * @param javaClassName
     * @throws IOException
     */
    public void convertJsonToJavaClass(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName) 
    		  throws IOException {
    		    JCodeModel jcodeModel = new JCodeModel();

    		    DefaultGenerationConfig config = new DefaultGenerationConfig() {
    		        @Override
    		        public boolean isGenerateBuilders() {
    		            return true;
    		        }

    		        @Override
    		        public SourceType getSourceType() {
    		            return SourceType.JSON;
    		        }
    		    };

    		    SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
    		    mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

    		    jcodeModel.build(outputJavaClassDirectory);
    		}
    
    /**
     * Get Institutions from API by novijeod
     * If we need additional api methods we can add.
     * @param status - for now it is just date for novijeod
     * @return List with Institution objects
     * @throws ApiException
     */
    public List<Researcher> getResearchers(String status) throws ApiException {
    	defaultClient.setBasePath(basePathInst);
    	return apiInstance.findResearchersByStatus(status);
    //	return null;
    }
    
  
    
    
    
}