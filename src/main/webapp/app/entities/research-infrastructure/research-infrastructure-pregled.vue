<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.researchInfrastructure.home.title')" id="research-infrastructure-heading">Research Infrastructures</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && researchInfrastructures && researchInfrastructures.length === 0">
            <span v-text="$t('riportalApp.researchInfrastructure.home.notFound')">No researchInfrastructures found</span>
        </div>
          


        <b-card v-bind:title="$t('global.menu.pregled.search')" class="border border-primary rounded shadow mb-4 class-for-card-title">
            
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
                    <button type="button" id="cancel-save" class="btn btn-primary" style="margin-top:32px" v-on:click="searchRI()">
                        <span v-text="$t('entity.action.search')">Search</span>
                    </button>
                </b-col>
            </b-row>        
        </b-card>

        <div class="table-responsive" v-if="researchInfrastructures && researchInfrastructures.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
<!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
-->    
                    <th class="sticky" v-if="currentLanguage=='sr'" ><span v-text="$t('riportalApp.researchInfrastructure.name')">Name Sr</span> </th>
                    <th class="sticky" v-if="currentLanguage=='src'" ><span v-text="$t('riportalApp.researchInfrastructure.name')">Name Sr Cyr</span> </th>
                    <th class="sticky" v-if="currentLanguage=='en'"  ><span v-text="$t('riportalApp.researchInfrastructure.name')">Name En</span> </th>
                    <th class="sticky" v-if="currentLanguage=='sr'" ><span v-text="$t('riportalApp.researchInfrastructure.description')">Description Sr</span> </th>
                    <th class="sticky" v-if="currentLanguage=='en'" ><span v-text="$t('riportalApp.researchInfrastructure.description')">Description En</span> </th>
                    <th class="sticky" v-if="currentLanguage=='src'" ><span v-text="$t('riportalApp.researchInfrastructure.description')">Description Sr Cyr</span> </th>

                    <!--
                    <th v-on:click="changeOrder('departmentName')"><span v-text="$t('riportalApp.researchInfrastructure.departmentName')">Department Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'departmentName'"></jhi-sort-indicator></th>                        
                    <th v-on:click="changeOrder('riLogo')"><span v-text="$t('riportalApp.researchInfrastructure.riLogo')">Ri Logo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'riLogo'"></jhi-sort-indicator></th>                     
                    -->
                    <th class="sticky" nowrap><span v-text="$t('riportalApp.researchInfrastructure.riWebsite')">Ri Website</span> </th>   
                   <!--
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('technicalSpecificationSr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationSr')">Technical Specification Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('technicalSpecificationEn')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationEn')">Technical Specification En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('technicalSpecificationSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationSrCyr')">Technical Specification Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationSrCyr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('technicalSpecificationLinkSr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkSr')">Technical Specification Link Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('technicalSpecificationLinkEn')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkEn')">Technical Specification Link En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('technicalSpecificationLinkSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkSrCyr')">Technical Specification Link Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'technicalSpecificationLinkSrCyr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='sr'" v-on:click="changeOrder('userManualLinkSr')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkSr')">User Manual Link Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkSr'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='en'" v-on:click="changeOrder('userManualLinkEn')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkEn')">User Manual Link En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkEn'"></jhi-sort-indicator></th>
                    <th v-if="currentLanguage=='src'" v-on:click="changeOrder('userManualLinkSrCyr')"><span v-text="$t('riportalApp.researchInfrastructure.userManualLinkSrCyr')">User Manual Link Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'userManualLinkSrCyr'"></jhi-sort-indicator></th>
                   -->     
                    <th class="sticky" ><span v-text="$t('riportalApp.researchInfrastructure.keywordsNoLanguage')">Keywords</span> </th>

                    <th class="sticky"><span v-text="$t('riportalApp.researchInfrastructure.status')">Status</span> </th>
                    <th class="sticky"><span v-text="$t('riportalApp.researchInfrastructure.accessMode')">Access Mode</span> </th>
                    <th class="sticky"><span v-text="$t('riportalApp.researchInfrastructure.accessType')">Access Type</span> </th>
                    <!--
                    <th><span v-text="$t('riportalApp.researchInfrastructure.category')">Category</span> </th>
                    -->
                    <th class="sticky"><span v-text="$t('riportalApp.researchInfrastructure.owner')">Owner</span> </th>
                    <th class="sticky"><span v-text="$t('riportalApp.researchInfrastructure.manager')">Manager</span> </th>
                    <!--
                    <th v-on:click="changeOrder('contactPerson.id')"><span v-text="$t('riportalApp.researchInfrastructure.contactPerson')">Contact Person</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contactPerson.id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky"></th> 
                </tr>
                </thead>
                <tbody>
                <tr v-for="researchInfrastructure in researchInfrastructures"
                    :key="researchInfrastructure.id">
<!--
                    <td>
                        <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: researchInfrastructure.id}}">{{researchInfrastructure.id}}</router-link>
                    </td>
-->                    
                    <td v-if="currentLanguage=='sr'" >{{researchInfrastructure.nameSr}}</td>
                    <td v-if="currentLanguage=='src'" >{{researchInfrastructure.nameSrCyr}}</td>
                    <td v-if="currentLanguage=='en'" >{{researchInfrastructure.nameEn}}</td>
                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.descriptionSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.descriptionEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.descriptionSrCyr}}</td>
                    
                    <!--
                    <td>{{researchInfrastructure.departmentName}}</td>
                    <td>{{researchInfrastructure.riLogo}}</td>
                    -->
                    <td>
                        <b-link :href="'http://' + researchInfrastructure.riWebsite" >Link</b-link>
                        
                    </td>
                    <!--
                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.technicalSpecificationSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.technicalSpecificationEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.technicalSpecificationSrCyr}}</td>
                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.technicalSpecificationLinkSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.technicalSpecificationLinkEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.technicalSpecificationLinkSrCyr}}</td>

                    <td v-if="currentLanguage=='sr'">{{researchInfrastructure.userManualLinkSr}}</td>
                    <td v-if="currentLanguage=='en'">{{researchInfrastructure.userManualLinkEn}}</td>
                    <td v-if="currentLanguage=='src'">{{researchInfrastructure.userManualLinkSrCyr}}</td>
                    -->

                    <!-- <td>{{researchInfrastructure.keywords}}</td>                     -->
                    <td>
                        <div v-if="currentLanguage == 'sr'">
                            <span v-if="researchInfrastructure.keywords">{{researchInfrastructure.keywords}}</span>
                        </div>
                        <div v-if="currentLanguage == 'en'">
                            <span v-if="researchInfrastructure.keywordsEn">{{researchInfrastructure.keywordsEn}}</span>
                        </div>
                        <div v-if="currentLanguage == 'src'">
                            <span v-if="researchInfrastructure.keywordsCyr">{{researchInfrastructure.keywordsCyr}}</span>
                        </div>                        
                    </td>
                    
                    
                    
                    <td>
                        <div v-if="researchInfrastructure.status">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.status.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.status.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.status.statusCyr}}</span>
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.accessMode">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessMode.mode}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessMode.modeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessMode.modeCyr}}</span>    
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.accessType">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessType.type}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessType.typeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessType.typeCyr}}</span> 
                        </div>
                    </td>
                    <!--
                    <td>
                        <div v-if="researchInfrastructure.category">
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.category.category}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.category.categoryEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.category.categoryCyr}}</span> 
                        </div>
                    </td>
                    -->
                    <td>
                        <div v-if="researchInfrastructure.owner">
                            <router-link class="link-style" :to="{name: 'RiResearchOrganizationView', params: {riResearchOrganizationId: researchInfrastructure.owner.id}}">
                                {{researchInfrastructure.owner.name}}
                            </router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="researchInfrastructure.manager">
                            <router-link class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: researchInfrastructure.manager.id}}">{{researchInfrastructure.manager.user.lastName}} {{researchInfrastructure.manager.user.firstName}}</router-link>
                        </div>
                    </td>
                    <!--
                    <td>
                        <div v-if="researchInfrastructure.contactPerson">
                            <router-link :to="{name: 'PortalUserView', params: {portalUserId: researchInfrastructure.contactPerson.id}}">{{researchInfrastructure.contactPerson.user.lastName}} {{researchInfrastructure.contactPerson.user.firstName}}</router-link>
                        </div>
                    </td>
                    -->
                    <td class="text-right">
                        
                            <router-link :to="{name: 'ResearchInfrastructurePregledDetails', params: {researchInfrastructureId: researchInfrastructure.id}}" tag="button" class="btn btn-info btn-sm details">
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>                            
                            
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        
        <div v-show="researchInfrastructures && researchInfrastructures.length > 0">
            <div class="row justify-content-center" v-if="showCounter">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center" v-if="showCounter">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./research-infrastructure-pregled.component.ts">
</script>
