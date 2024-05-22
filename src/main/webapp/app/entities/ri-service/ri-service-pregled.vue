<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riService.home.title')" id="ri-service-heading">Ri Services</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && riServices && riServices.length === 0">
            <span v-text="$t('riportalApp.riService.home.notFound')">No riServices found</span>
        </div>        
        <b-card title="Pretraga" class="border border-primary rounded shadow mb-4 class-for-card-title">
            
            <b-row>
                <b-col sm="3">                        
                    <label class="form-control-label" v-text="$t('riportalApp.riService.enterKeywords')" for="ri-service-keywords">Keywords</label>
                    <input type="text" class="form-control" name="search" id="search"
                        v-model="search.searchTerms" v-on:keyup.enter="searchRI()" />
                </b-col>               
                <b-col sm="4">
                    <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.enterScientificDomain')" for="ri-service-keywords">Keywords</label>
                    <div class="scrollable-div">
                        <ul style="list-style: none;" v-for="domain in scientificDomains" :key="domain.id" >
                            <li>
                                <!--
                                <b-form-checkbox v-model="selectedDomains" :value="domain.id">
                                -->    
                                    <span v-if="currentLanguage === 'sr'">{{domain.domain}}</span>
                                    <span v-if="currentLanguage === 'src'">{{domain.domainCyr}}</span>
                                    <span v-if="currentLanguage === 'en'">{{domain.domainEn}}</span>
                                <!--    
                                </b-form-checkbox>
                                -->
                                <ul style="list-style: none;" v-for="subdomain in domain.subdomains" :key="subdomain.id" >
                                    <li>
                                        <b-form-checkbox v-model="selectedSubdomains" :value="subdomain.id">
                                            <span v-if="currentLanguage === 'sr'">{{subdomain.subdomain}}</span>
                                            <span v-if="currentLanguage === 'src'">{{subdomain.subdomainCyr}}</span>
                                            <span v-if="currentLanguage === 'en'">{{subdomain.subdomainEn}}</span>
                                        </b-form-checkbox>
                                    </li>
                                </ul>
                            </li>
                        </ul>                       
                    </div>
                </b-col>
            </b-row>
            <b-row>
                <b-col sm="3"> 
                    <button type="button" id="cancel-save" class="btn btn-primary" style="margin-top:32px" v-on:click="searchRiService()">
                        <span v-text="$t('entity.action.search')">Search</span>
                    </button>
                </b-col>
            </b-row>        
        </b-card>
        <div class="table-responsive" v-if="riServices && riServices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" v-if="currentLanguage == 'sr'" ><span v-text="$t('riportalApp.riService.name')">Name Sr</span> </th>
                    <th class="sticky" v-if="currentLanguage == 'en'" ><span v-text="$t('riportalApp.riService.name')">Name En</span> </th>
                    <th class="sticky" v-if="currentLanguage == 'src'"><span v-text="$t('riportalApp.riService.name')">Name Sr Cyr</span> </th>                    
                    
                    
                    <th class="sticky" v-if="currentLanguage == 'sr'" ><span v-text="$t('riportalApp.riService.description')">Description Sr</span> </th>
                    <th class="sticky" v-if="currentLanguage == 'en'" ><span v-text="$t('riportalApp.riService.description')">Description En</span> </th>
                    <th class="sticky" v-if="currentLanguage == 'src'"><span v-text="$t('riportalApp.riService.description')">Description Sr Cyr</span> </th>
                    

                    <th class="sticky" ><span v-text="$t('riportalApp.riService.keywords')">Keywords</span> </th>
                    <!--
                    <th v-on:click="changeOrder('accreditationOrganizationName')"><span v-text="$t('riportalApp.riService.accreditationOrganizationName')">Accreditation Organization Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accreditationOrganizationName'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('termsOfUseSr')"><span v-text="$t('riportalApp.riService.termsOfUse')">Terms Of Use Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseSr'"></jhi-sort-indicator></th>
                   
                    <th v-on:click="changeOrder('termsOfUseEn')"><span v-text="$t('riportalApp.riService.termsOfUseEn')">Terms Of Use En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseEn'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('privacyPolicySr')"><span v-text="$t('riportalApp.riService.privacyPolicy')">Privacy Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'privacyPolicySr'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('privacyPolicyEn')"><span v-text="$t('riportalApp.riService.privacyPolicyEn')">Privacy Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'privacyPolicyEn'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('accessPolicySr')"><span v-text="$t('riportalApp.riService.accessPolicy')">Access Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicySr'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('accessPolicyEn')"><span v-text="$t('riportalApp.riService.accessPolicyEn')">Access Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicyEn'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('pricingPolicySr')"><span v-text="$t('riportalApp.riService.pricingPolicy')">Pricing Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicySr'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('pricingPolicyEn')"><span v-text="$t('riportalApp.riService.pricingPolicyEn')">Pricing Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicyEn'"></jhi-sort-indicator></th>
                    -->
                    <!--

                    <th v-on:click="changeOrder('termsOfUseSrCyr')"><span v-text="$t('riportalApp.riService.termsOfUseSrCyr')">Terms Of Use Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseSrCyr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('accessPolicySrCyr')"><span v-text="$t('riportalApp.riService.accessPolicySrCyr')">Access Policy Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicySrCyr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('pricingPolicySrCyr')"><span v-text="$t('riportalApp.riService.pricingPolicySrCyr')">Pricing Policy Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicySrCyr'"></jhi-sort-indicator></th>
                    
                    -->
                    <th class="sticky" nowrap ><span v-text="$t('riportalApp.riService.availability')">Availability</span> </th>
                    <!--
                    <th v-on:click="changeOrder('accredation.id')"><span v-text="$t('riportalApp.riService.accredation')">Accredation</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accredation.id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" ><span v-text="$t('riportalApp.serviceSubtype.subtype')">Subtypes</span> </th>
                    <th class="sticky" ><span v-text="$t('riportalApp.riService.researchInfrastructure')">Research Infrastructure</span> </th>
                    <th class="sticky"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="riService in riServices"
                    :key="riService.id">
                    <!--
                    <td>
                        <router-link :to="{name: 'RiServiceView', params: {riServiceId: riService.id}}">{{riService.id}}</router-link>
                    </td>
                    -->
                    <td>
                        <span v-if="currentLanguage == 'sr'">{{riService.nameSr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.nameEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.nameSrCyr}}</span>
                    </td>
                    <!--
                    <td>{{riService.nameEn}}</td>
                    -->
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.descriptionSr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.descriptionEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.descriptionSrCyr}}</span>
                    </td>
                    <!--
                    <td>{{riService.descriptionEn}}</td>
                    -->
                    <td>
                        <span v-if="currentLanguage == 'sr'">{{riService.keywords}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.keywordsCyr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.keywordsEn}}</span>
                    </td>
                    <!--
                    <td>{{riService.accreditationOrganizationName}}</td>
                    
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.termsOfUseSr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.termsOfUseEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.termsOfUseSrCyr}}</span>
                    </td>
                    
                    <td>{{riService.termsOfUseEn}}</td>
                    
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.privacyPolicySr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.privacyPolicyEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.privacyPolicySr}}</span>
                    </td>
                    
                    <td>{{riService.privacyPolicyEn}}</td>
                    
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.accessPolicySr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.accessPolicyEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.accessPolicySrCyr}}</span>
                    </td>
                    -->
                    <!--
                    <td>{{riService.accessPolicyEn}}</td>
                   
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.pricingPolicySr}}</span>
                        <span v-if="currentLanguage == 'en'">{{riService.pricingPolicyEn}}</span>
                        <span v-if="currentLanguage == 'src'">{{riService.pricingPolicySrCyr}}</span>
                    </td>
                    
                    <td>{{riService.pricingPolicyEn}}</td>
                    -->
                    <!--
                    <td>{{riService.nameSrCyr}}</td>
                    <td>{{riService.termsOfUseSrCyr}}</td>
                    <td>{{riService.accessPolicySrCyr}}</td>
                    <td>{{riService.pricingPolicySrCyr}}</td>
                    <td>{{riService.descriptionSrCyr}}</td>
                    -->
                    <td>
                        <div v-if="riService.availability">                            
                            <span v-if="currentLanguage == 'sr'">{{riService.availability.availability}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.availability.availabilityEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.availability.availabilityCyr}}</span>
                        </div>
                    </td>
                    <!--
                    <td>
                        <div v-if="riService.accredation">                                                            
                            <span v-if="currentLanguage == 'sr'">{{riService.accredation.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.accredation.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.accredation.statusCyr}}</span>                            
                        </div>
                    </td>
                    -->
                    <td>
                        <div v-if="riService.subtypes">                                                           
                            <span v-if="currentLanguage == 'sr'">{{riService.subtypes.subtype}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.subtypes.subtypeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.subtypes.subtypeCyr}}</span>                                                        
                        </div>
                    </td>
                    <td>
                        <div v-if="riService.researchInfrastructure">
                            <router-link :to="{name: 'ResearchInfrastructurePregled', params: {researchInfrastructureId: riService.researchInfrastructure.id}}">                                
                                <span v-if="currentLanguage == 'sr'">{{riService.researchInfrastructure.nameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{riService.researchInfrastructure.nameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{riService.researchInfrastructure.nameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td nowrap class="text-right">
                        
                            <router-link :to="{name: 'RiServicePregledDetails', params: {riServiceId: riService.id}}" tag="button" class="btn btn-info">
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>  
                            <button type="button" id="cancel-save" class="btn btn-primary" 
                            v-on:click="checkOpenedForGivenService(riService.id)"
                            v-if="authenticated">
                                <span v-text="$t('riportalApp.requestForService.request')"></span>
                            </button>                         
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
       
        <div v-show="riServices && riServices.length > 0">
            <div class="row justify-content-center" v-if="showCounter">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center" v-if="showCounter">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-service-pregled.component.ts">
</script>
