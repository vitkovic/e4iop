<template>
    <div style="margin-top:-10px;padding:0px;">
	<div  id="searchbox" >		   
	   <b-input-group  class="mb-2" style="padding:50px;padding-top:30px;">
      							<b-input-group-prepend is-text>
						        <b-icon icon="search" scale="1.3" @click="searchRiService()"></b-icon>
      							</b-input-group-prepend>
      							<b-form-input type="text"  placeholder="Traži..."  v-model="search.searchTerms"
                    @keydown.enter.native="searchRiService()"
                    ></b-form-input>
    						</b-input-group>		   
			   
	</div>	   
	
<div style="margin:0px;margin-top:-30px;padding-top:15px;background-color: #abbbc960;">
	
	
  <b-jumbotron class="text-center" style="padding:30px;" >
 
    <template #header><span v-text="$t('home.title1')">Portal otvorene istraživačke infrastrukture Srbije</span></template>
    <template #lead >
      <strong><span v-text="$t('home.pregledajte')">Pregledajte i pretražite istraživačku infrastrukturu Srbije!</span></strong>
    </template>
    <b-button variant="primary" href="#"><span v-text="$t('home.saznajVise')">Saznaj više...</span></b-button>

  </b-jumbotron>
</div>
 <div style="margin:0px;margin-top:-35px;background-color: #f1f5f8;">
		<b-container fluid="sm" style="margin-top:0px;padding-top:50px;">
			
			<b-row>
				<b-col sm="4" class="d-flex align-items-center justify-content-center">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/01_sm_sm.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
						
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{name: 'ResearchInfrastructurePregled'}">
                        <h5 v-text="$t('home.infrastructure')">INFRASTRUKTURA</h5>
                    </router-link>
                    
                  </b-col>
                </b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4>
                      <span class="badge badge-pill badge-warning">{{researchInfrastructureCount}}</span>
                    </h4>
                  </b-col>
                </b-row>
							</b-container>
						
						
					</div>		
				</b-col>
				<b-col sm="4" class="d-flex align-items-center justify-content-center">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/02_n_sm_sm.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
							
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{name: 'RiServicePregled'}">
                      <h5  v-text="$t('home.services')">USLUGE</h5>
                    </router-link>
                  </b-col>
                </b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4><span class="badge badge-pill badge-warning">{{riServicesCount}}</span>
                  </h4>
                </b-col>
              </b-row>
							</b-container>
					</div>
				</b-col>
				<b-col sm="4" class="d-flex align-items-center justify-content-center">
					<div class="d-flex align-items-center justify-content-center" style="position:relative;background-image:url('/content/images/03_sm_sm.jpg');background-repeat: no-repeat;background-size:contain;width:300px;height:188px;">&nbsp;
							<b-container fluid="sm" style="margin-top:0px;">
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <router-link :to="{name: 'PortalUserOrganizationPregled'}">
                      <h5 v-text="$t('home.organizations')">ORGANIZACIJE</h5>
                    </router-link>
                  </b-col>
                </b-row>
								<b-row style="height:20px;"><b-col class="d-flex align-items-center justify-content-center"></b-col></b-row>
								<b-row>
                  <b-col class="d-flex align-items-center justify-content-center">
                    <h4><span class="badge badge-pill badge-warning">{{portalOrganizationsCount}}</span>
                  </h4>
                </b-col>
              </b-row>
							</b-container>
					</div>	
							
				</b-col>
				
			</b-row>
			<b-row style="height:50px;">
				<b-col sm="12" >
						
				</b-col>
			</b-row>
			</b-container>
			
		</div>

    <!-- Rezultat pretrage, ako postoji -->
    <div class="table-responsive mt-4" v-if="services.length > 0">
      <h3><span v-text="$t('riportalApp.riService.searchResults')" style="font-weight:bold"></span></h3>
      <table class="table  table-striped table-bordered">
        
        <thead>
          <tr>
            <th class="col-sm-2"><span  v-text="$t('riportalApp.riService.serviceName')"></span></th>
            <th class="col-sm-6"><span  v-text="$t('riportalApp.riService.serviceDescription')"></span></th>
            <th class="col-sm-2"><span  v-text="$t('riportalApp.researchInfrastructure.home.title')"></span></th>
            <th class="col-sm-2"><span  v-text="$t('register.organizacija')"></span></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="service in services" :key="service.serviceId">
            <td>
              <router-link class="link-style" :to="{name: 'RiServicePregledDetails', 
              params: {riServiceId: service.serviceId, pathInformation:{search:search, path:'Home'}}}">
                <span v-if="currentLanguage === 'sr'">{{service.serviceNameSr}}</span>
                <span v-if="currentLanguage === 'en'">{{service.serviceNameEn}}</span>
                <span v-if="currentLanguage === 'src'">{{service.serviceNameSrCyr}}</span>
              </router-link>
            </td>
            <td>
              <span v-if="currentLanguage === 'sr'">{{service.serviceDescriptionSr}}</span>
              <span v-if="currentLanguage === 'en'">{{service.serviceDescriptionEn}}</span>
              <span v-if="currentLanguage === 'src'">{{service.serviceDescriptionSrCyr}}</span>
            </td>
            <td>
              <router-link class="link-style" :to="{name: 'ResearchInfrastructurePregledDetails', params: {researchInfrastructureId: service.infraStructureId, pathInformation:{search:search, path:'Home'}}}">
                <span v-if="currentLanguage === 'sr'">{{service.infrastructureNameSr}}</span>
                <span v-if="currentLanguage === 'en'">{{service.infrastructureNameEn}}</span>
                <span v-if="currentLanguage === 'src'">{{service.infrastructureNameSrCyr}}</span>
              </router-link>
            </td>
            <td>
              <router-link class="link-style" :to="{name: 'PortalUserOrganizationPregledDetails', 
                params: {portalUserOrganizationId: service.organizationId, pathInformation:{search:search, path:'Home'}}}">
                <span v-if="currentLanguage === 'sr'">{{service.organizationNameSr}}</span>
                <span v-if="currentLanguage === 'en'">{{service.organizationNameEn}}</span>
                <span v-if="currentLanguage === 'src'">{{service.organizationNameSrCyr}}</span>
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>  
    </div>
      
			
</div>	
			
</template>

<script lang="ts" src="./home.component.ts">
</script>

<style scoped>
.UniqueFullWidth .input-group-text {
  width: 48px;
  border-right: none;
  background-color: #ffffff;
}
.UniqueFullWidth [class^="fa-"], [class*=" fa-"] {
  display: inline-block;
  width: 100%;
}
.UniqueFullWidth .LoginInput {
  border-left: none;
}
.jumbotron {
  background-image: url("/content/images/research_find_loupe_search1.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  border-radius: 0 !important;
}
::placeholder {
  color: rgb(226, 226, 226);
  opacity: 1; /* Firefox */
}
::-ms-input-placeholder { /* Edge 12 -18 */
  color: rgb(226, 226, 226);
}
#searchbox {
	background-color:#1f3448;}
</style>