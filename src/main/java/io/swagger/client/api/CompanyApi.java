/*
 * Swagger Petstore - OpenAPI 3.0
 * This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach! You can now help us improve the API whether it's by making changes to the definition itself or to the code. That way, with time, we can improve the API in general, and expose some of the new features in OAS3.  _If you're looking for the Swagger 2.0/OAS 2.0 version of Petstore, then click [here](https://editor.swagger.io/?url=https://petstore.swagger.io/v2/swagger.yaml). Alternatively, you can load via the `Edit > Load Petstore OAS 2.0` menu option!_  Some useful links: - [The Pet Store repository](https://github.com/swagger-api/swagger-petstore) - [The source API definition for the Pet Store](https://github.com/swagger-api/swagger-petstore/blob/master/src/main/resources/openapi.yaml)
 *
 * OpenAPI spec version: 1.0.11
 * Contact: apiteam@swagger.io
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.Category;
import io.swagger.client.model.Company;
import io.swagger.client.model.Company__1;
import io.swagger.client.model.ModelApiResponse;
import io.swagger.client.model.Researcher;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyApi {
    private ApiClient apiClient;

    public CompanyApi() {
        this(Configuration.getDefaultApiClient());
    }

    public CompanyApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * Build call for findCompanysByStatus
     * @param status Status values that need to be considered for filter (optional, default to available)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call findCompanysByStatusCall(String status, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        String CompanyCall = "";
        
        // create path and map variables
        //   String localVarPath = "/pet/findByStatus";
        String localVarPath = "";
        //status = null;
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (status != null && status != "") {
        	
        	String[] paramArray = status.split(";");
        	if (paramArray.length>1) {
        	  if (paramArray[0].length() == 8)
        		localVarQueryParams.addAll(apiClient.parameterToPair("companyRegNo", paramArray[0]));
        	  else
        		localVarQueryParams.addAll(apiClient.parameterToPair("companyTaxNo", paramArray[0])); 
        		
        	  localVarQueryParams.addAll(apiClient.parameterToPair("changeGroup", paramArray[1]));
        	
        	} else  {
        		
        		if (paramArray[0].length() == 8)
            		localVarQueryParams.addAll(apiClient.parameterToPair("companyRegNo", paramArray[0]));
            	  else
            		localVarQueryParams.addAll(apiClient.parameterToPair("companyTaxNo", paramArray[0])); 
        		
        		
        	}
        }
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json", "application/xml"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {"companies_auth"};
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call findCompanysByStatusValidateBeforeCall(String status, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        com.squareup.okhttp.Call call = findCompanysByStatusCall(status, progressListener, progressRequestListener);
        return call;

     }

    /**
     * Finds Pets by status
     * Multiple status values can be provided with comma separated strings
     * @param status Status values that need to be considered for filter (optional, default to available)
     * @return List&lt;Pet&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String findCompaniesByStatus(String status) throws ApiException {
        String resp = findCompanysByStatusWithHttpInfo(status);
       // System.out.println("json");
        return resp;
    }

    /**
     * Finds Pets by status
     * Multiple status values can be provided with comma separated strings
     * @param status Status values that need to be considered for filter (optional, default to available)
     * @return ApiResponse&lt;List&lt;Pet&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public String findCompanysByStatusWithHttpInfo(String status) throws ApiException {
        com.squareup.okhttp.Call call = findCompanysByStatusValidateBeforeCall(status, null, null);
        //apiClient.callType = 1;
        //Type localVarReturnType = new TypeToken<List<Company__1>>(){}.getType();
        return apiClient.executeCompany(call, null);
    }
    
    
   
 
    
    
    
    
    
    
    
    
    

}