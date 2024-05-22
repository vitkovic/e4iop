<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('riportalApp.riService.home.title')" id="ri-service-heading">Ri Services</span>
            <router-link :to="{name: 'RiServiceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-ri-service">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('riportalApp.riService.home.createLabel')">
                    Create a new Ri Service
                </span>
            </router-link>
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
        <div class="table-responsive" v-if="riServices && riServices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <!--
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    -->
                    <th class="sticky" v-if="currentLanguage == 'sr'" v-on:click="changeOrder('nameSr')"><span v-text="$t('riportalApp.riService.name')">Name Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameSr'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage == 'en'"  v-on:click="changeOrder('nameEn')"><span v-text="$t('riportalApp.riService.name')">Name En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameEn'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage == 'src'"  v-on:click="changeOrder('nameSrCyr')"><span v-text="$t('riportalApp.riService.name')">Name Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nameSrCyr'"></jhi-sort-indicator></th>                    
                    
                    
                    <th class="sticky" v-if="currentLanguage == 'sr'"  v-on:click="changeOrder('descriptionSr')"><span v-text="$t('riportalApp.riService.description')">Description Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSr'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage == 'en'"  v-on:click="changeOrder('descriptionEn')"><span v-text="$t('riportalApp.riService.description')">Description En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionEn'"></jhi-sort-indicator></th>
                    <th class="sticky" v-if="currentLanguage == 'src'"  v-on:click="changeOrder('descriptionSrCyr')"><span v-text="$t('riportalApp.riService.description')">Description Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descriptionSrCyr'"></jhi-sort-indicator></th>
                    

                    <th class="sticky" v-on:click="changeOrder('keywords')"><span v-text="$t('riportalApp.riService.keywordsNoLanguage')">Keywords</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator></th>
                    <!--
                    <th class="sticky" v-on:click="changeOrder('accreditationOrganizationName')"><span v-text="$t('riportalApp.riService.accreditationOrganizationName')">Accreditation Organization Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accreditationOrganizationName'"></jhi-sort-indicator></th>
                    
                    <th class="sticky" v-on:click="changeOrder('termsOfUseSr')"><span v-text="$t('riportalApp.riService.termsOfUse')">Terms Of Use Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseSr'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('termsOfUseEn')"><span v-text="$t('riportalApp.riService.termsOfUseEn')">Terms Of Use En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseEn'"></jhi-sort-indicator></th>
                   
                    <th class="sticky" v-on:click="changeOrder('privacyPolicySr')"><span v-text="$t('riportalApp.riService.privacyPolicy')">Privacy Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'privacyPolicySr'"></jhi-sort-indicator></th>
                    <!--
                    <th v-on:click="changeOrder('privacyPolicyEn')"><span v-text="$t('riportalApp.riService.privacyPolicyEn')">Privacy Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'privacyPolicyEn'"></jhi-sort-indicator></th>
                    
                    <th class="sticky" v-on:click="changeOrder('accessPolicySr')"><span v-text="$t('riportalApp.riService.accessPolicy')">Access Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicySr'"></jhi-sort-indicator></th>
                   
                    <th v-on:click="changeOrder('accessPolicyEn')"><span v-text="$t('riportalApp.riService.accessPolicyEn')">Access Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicyEn'"></jhi-sort-indicator></th>
                    
                    <th class="sticky" v-on:click="changeOrder('pricingPolicySr')"><span v-text="$t('riportalApp.riService.pricingPolicy')">Pricing Policy Sr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicySr'"></jhi-sort-indicator></th>
                    
                    <th v-on:click="changeOrder('pricingPolicyEn')"><span v-text="$t('riportalApp.riService.pricingPolicyEn')">Pricing Policy En</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicyEn'"></jhi-sort-indicator></th>
                    -->
                    <!--

                    <th v-on:click="changeOrder('termsOfUseSrCyr')"><span v-text="$t('riportalApp.riService.termsOfUseSrCyr')">Terms Of Use Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'termsOfUseSrCyr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('accessPolicySrCyr')"><span v-text="$t('riportalApp.riService.accessPolicySrCyr')">Access Policy Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accessPolicySrCyr'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('pricingPolicySrCyr')"><span v-text="$t('riportalApp.riService.pricingPolicySrCyr')">Pricing Policy Sr Cyr</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pricingPolicySrCyr'"></jhi-sort-indicator></th>
                    
                    -->
                    <th nowrap class="sticky" v-on:click="changeOrder('availability.id')"><span v-text="$t('riportalApp.riService.availability')">Availability</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'availability.id'"></jhi-sort-indicator></th>
                    <th nowrap class="sticky" v-on:click="changeOrder('accredation.id')"><span v-text="$t('riportalApp.riService.accredation')">Accredation</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'accredation.id'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('subtypes.id')"><span v-text="$t('riportalApp.serviceSubtype.subtype')">Subtypes</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subtypes.id'"></jhi-sort-indicator></th>
                    <th class="sticky" v-on:click="changeOrder('researchInfrastructure.id')"><span v-text="$t('riportalApp.riService.researchInfrastructure')">Research Infrastructure</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'researchInfrastructure.id'"></jhi-sort-indicator></th>
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
                    <td>{{riService.keywords}}</td>
                    <!--
                    <td>{{riService.accreditationOrganizationName}}</td>
                    
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">
                         <span v-if="riService.id != null && riService.termsOfUseSr != null"><a v-bind:href="'/api/files/' + riService.termsOfUseSr">{{riService.termsOfUseSr}}</a></span></span>
                        <span v-if="currentLanguage == 'en'">
                         <span v-if="riService.id != null && riService.termsOfUseEn != null"><a v-bind:href="'/api/files/' + riService.termsOfUseEn">{{riService.termsOfUseEn}}</a></span></span>
                        <span v-if="currentLanguage == 'src'">
						 <span v-if="riService.id != null && riService.termsOfUseSrCyr != null"><a v-bind:href="'/api/files/' + riService.termsOfUseSrCyr">{{riService.termsOfUseSrCyr}}</a></span></span>
                    </td>
                    -->
                    <!--
                    <td>{{riService.termsOfUseEn}}</td>
                    -->
                    <!--
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">
						 <span v-if="riService.id != null && riService.privacyPolicySr != null"><a v-bind:href="'/api/files/' + riService.privacyPolicySr">{{riService.privacyPolicySr}}</a></span></span>
                        <span v-if="currentLanguage == 'en'">
						 <span v-if="riService.id != null && riService.privacyPolicyEn != null"><a v-bind:href="'/api/files/' + riService.privacyPolicyEn">{{riService.privacyPolicyEn}}</a></span></span>
                        <span v-if="currentLanguage == 'src'">{{riService.privacyPolicySr}}</span>
                    </td>
                    -->
                    <!--
                    <td>{{riService.privacyPolicyEn}}</td>
                    -->
                    <!--
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">
						 <span v-if="riService.id != null && riService.accessPolicySr != null"><a v-bind:href="'/api/files/' + riService.accessPolicySr">{{riService.accessPolicySr}}</a></span></span>
                        <span v-if="currentLanguage == 'en'">
						 <span v-if="riService.id != null && riService.accessPolicyEn != null"><a v-bind:href="'/api/files/' + riService.accessPolicyEn">{{riService.accessPolicyEn}}</a></span></span>
                        <span v-if="currentLanguage == 'src'">
						 <span v-if="riService.id != null && riService.accessPolicySrCyr != null"><a v-bind:href="'/api/files/' + riService.accessPolicySrCyr">{{riService.accessPolicySrCyr}}</a></span></span>
                    </td>
                    -->
                    <!--
                    <td>{{riService.accessPolicyEn}}</td>
                    -->
                    <!--
                    <td>                        
                        <span v-if="currentLanguage == 'sr'">{{riService.pricingPolicySr}}
						 <span v-if="riService.id != null && riService.pricingPolicySr != null"><a v-bind:href="'/api/files/' + riService.pricingPolicySr">{{riService.pricingPolicySr}}</a></span></span>
                        <span v-if="currentLanguage == 'en'">{{riService.pricingPolicyEn}}
						 <span v-if="riService.id != null && riService.pricingPolicyEn != null"><a v-bind:href="'/api/files/' + riService.pricingPolicyEn">{{riService.pricingPolicyEn}}</a></span></span>
                        <span v-if="currentLanguage == 'src'">{{riService.pricingPolicySrCyr}}
						 <span v-if="riService.id != null && riService.pricingPolicySrCyr != null"><a v-bind:href="'/api/files/' + riService.pricingPolicySrCyr">{{riService.pricingPolicySrCyr}}</a></span></span>
                    </td>
                    -->
                    <!--
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
                    <td>
                        <div v-if="riService.accredation">                                                            
                            <span v-if="currentLanguage == 'sr'">{{riService.accredation.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.accredation.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.accredation.statusCyr}}</span>                            
                        </div>
                    </td>
                    <td>
                        <div v-if="riService.subtypes">                                                           
                            <span v-if="currentLanguage == 'sr'">{{riService.subtypes.subtype}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.subtypes.subtypeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.subtypes.subtypeCyr}}</span>                                                        
                        </div>
                    </td>
                    <td>
                        <div v-if="riService.researchInfrastructure">
                            <router-link :to="{name: 'ResearchInfrastructureView', params: {researchInfrastructureId: riService.researchInfrastructure.id}}">                                
                                <span v-if="currentLanguage == 'sr'">{{riService.researchInfrastructure.nameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{riService.researchInfrastructure.nameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{riService.researchInfrastructure.nameSrCyr}}</span>
                            </router-link>
                        </div>
                    </td>
                    <td nowrap class="text-right">
                        
                            <router-link :to="{name: 'RiServiceView', params: {riServiceId: riService.id}}" tag="button" class="btn btn-info btn-sm details">
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RiServiceEdit', params: {riServiceId: riService.id}}"  tag="button" class="btn btn-primary btn-sm edit"
                            v-if="authenticated && (hasAnyRole(['PA', 'RPRI']) || hasAnyAuthority('ROLE_ADMIN'))"
                            >                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(riService)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity
                                   v-if="authenticated && (hasAnyRole(['PA', 'RPRI']) || hasAnyAuthority('ROLE_ADMIN'))"
                                   >
                                
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="riportalApp.riService.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-riService-heading" v-text="$t('riportalApp.riService.delete.question', {'id': removeId})">Are you sure you want to delete this Ri Service?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-riService" v-text="$t('entity.action.delete')" v-on:click="removeRiService()">Delete</button>
            </div>
        </b-modal>
        <div v-show="riServices && riServices.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ri-service.component.ts">
</script>
