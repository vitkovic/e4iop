<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <div v-if="riService">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.riService.detail.title')">RiService</span> {{riService.id}}</h2>
                
                <b-form-row class="mt-2 row-background">
                    <b-col sm="2"  class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.nameSr')">Name Sr</span>
                    </b-col>
                    <b-col sm="4">
                        <span v-if="currentLanguage === 'sr'">{{riService.nameSr}}</span>
                        <span v-if="currentLanguage === 'src'">{{riService.nameSrCyr}}</span>
                        <span v-if="currentLanguage === 'en'">{{riService.nameEn}}</span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.researchInfrastructure')">Research Infrastructure</span>
                    </b-col>
                    <b-col sm="4" class="border-table" v-if="riService.researchInfrastructure">                        
                        <router-link class="link-style" :to="{name: 'ResearchInfrastructurePregledDetails', params: {researchInfrastructureId: riService.researchInfrastructure.id}}">
                            <span v-if="currentLanguage == 'sr'">{{riService.researchInfrastructure.nameSr}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.researchInfrastructure.nameEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.researchInfrastructure.nameSrCyr}}</span>                            
                        </router-link>                                                    
                    </b-col> 
                </b-form-row>                               
                <b-form-row class="mt-2 row-background">
                     <b-col sm="2" class="border-table">
                        <span class="span-display"  v-text="$t('riportalApp.riService.descriptionSr')">Description Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table"> 
                        <span v-if="currentLanguage === 'sr'">{{riService.descriptionSr}}</span>
                        <span v-if="currentLanguage === 'src'">{{riService.descriptionSrCyr}}</span>
                        <span v-if="currentLanguage === 'en'">{{riService.descriptionEn}}</span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.keywordsNoLanguage')">Keywords</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="currentLanguage === 'sr'">{{riService.keywords}}</span>
                        <span v-if="currentLanguage === 'en'">{{riService.keywordsEn}}</span>
                        <span v-if="currentLanguage === 'src'">{{riService.keywordsCyr}}</span>
                    </b-col>                    
                </b-form-row>
                <b-form-row class="mt-2 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.accreditationOrganizationName')">Accreditation Organization Name</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span>{{riService.accreditationOrganizationName}}</span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.accredation')">Accredation</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="riService.accredation" >
                            <span v-if="currentLanguage == 'sr'">{{riService.accredation.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.accredation.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.accredation.statusCyr}}</span>
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-2 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.price')"></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span>{{riService.price}} {{riService.currency}}</span>
                    </b-col>
                     <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.availability')">Availability</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="riService.availability">
                            <span v-if="currentLanguage == 'sr'">{{riService.availability.availability}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.availability.availabilityEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.availability.availabilityCyr}}</span>
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-2 row-background">
                   <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.type')"></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="riService.subtypes" >
                            <span v-if="currentLanguage == 'sr'">{{riService.subtypes.subtype}}</span>
                            <span v-if="currentLanguage == 'en'">{{riService.subtypes.subtypeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{riService.subtypes.subtypeCyr}}</span>
                        </div>                        
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.domain')"></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-for="(subdomains, i) in riService.subdomains" :key="subdomains.id">
                            <span v-if="currentLanguage == 'sr'">{{subdomains.subdomain}}</span>
                            <span v-if="currentLanguage == 'en'">{{subdomains.subdomainEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{subdomains.subdomainCyr}}</span>
                            <br>
                        </span>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-2 row-background">
                    <b-col  sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.termsOfUseSr')">Terms Of Use Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="riService.id != null && riService.termsOfUseSr != null">
                            <b-button v-if="currentLanguage === 'sr' || currentLanguage === 'src'" variant="link" class="link-style" @click="formLink(riService.termsOfUseSr)">
                                {{riService.termsOfUseSr}}
                            </b-button>
                            <b-button v-if="currentLanguage === 'en'" variant="link" class="link-style" @click="formLink(riService.termsOfUseEn)">
                                {{riService.termsOfUseEn}}
                            </b-button>
                        </span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.privacyPolicySr')">Privacy Policy Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="riService.id != null && riService.privacyPolicySr != null">
                            <b-button v-if="currentLanguage === 'sr' || currentLanguage === 'src'" variant="link" class="link-style" @click="formLink(riService.privacyPolicySr)">
                                {{riService.privacyPolicySr}}
                            </b-button>
                            <b-button v-if="currentLanguage === 'en'" variant="link" class="link-style" @click="formLink(riService.privacyPolicyEn)">
                                {{riService.privacyPolicyEn}}
                            </b-button>
                        </span>
                    </b-col>                    
                </b-form-row>                
                <b-form-row class="mt-2 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.accessPolicySr')">Access Policy Sr</span>
                    </b-col>
                    <b-col sm="4">
                        <span v-if="riService.id != null && riService.accessPolicySr != null">
                            <b-button v-if="currentLanguage === 'sr' || currentLanguage === 'src'" variant="link" class="link-style" @click="formLink(riService.accessPolicySr)">
                                {{riService.accessPolicySr}}
                            </b-button>                        
                            <b-button v-if="currentLanguage === 'en'" variant="link" class="link-style" @click="formLink(riService.accessPolicyEn)">
                                {{riService.accessPolicyEn}}
                            </b-button>
                        </span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.riService.pricingPolicySr')">Pricing Policy Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="riService.id != null && riService.pricingPolicySr != null">
                            <b-button v-if="currentLanguage === 'sr' || currentLanguage === 'src'" variant="link" class="link-style" @click="formLink(riService.accessPolicySr)">
                                {{riService.pricingPolicySr}}
                            </b-button>                        
                            <b-button v-if="currentLanguage === 'en'" variant="link" class="link-style" @click="formLink(riService.accessPolicyEn)">
                                {{riService.pricingPolicyEn}}
                            </b-button>
                        </span>
                    </b-col>                    
                </b-form-row>                                                                
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info mt-4">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <!--
                <router-link v-if="riService.id" :to="{name: 'RiServiceEdit', params: {riServiceId: riService.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>-->
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-service-pregled-details.component.ts">
</script>
