<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <div v-if="researchInfrastructure">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.researchInfrastructure.detail.title')">ResearchInfrastructure</span> {{researchInfrastructure.id}}</h2>
                <b-form-row class="mt-2 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.name')" >Name Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="currentLanguage === 'sr'">{{researchInfrastructure.nameSr}}</span>
                        <span v-if="currentLanguage === 'src'">{{researchInfrastructure.nameSrCyr}}</span>
                        <span v-if="currentLanguage === 'en'">{{researchInfrastructure.nameEn}}</span>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.description')">Description Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div>
                            {{ shortenedDescription() }}
                            <span v-if="getDescriptionByLanguage(researchInfrastructure).length > 90">
                                <a href="#" @click.prevent="toggleDescription">
                                    {{ researchInfrastructure.showFullDescription ? $t('entity.action.readLess') : $t('entity.action.readMore') }}
                                </a>
                            </span>
                        </div>
                        <!-- OLD CODE -->
                        <!-- <span v-if="currentLanguage === 'sr'">{{researchInfrastructure.descriptionSr}}</span>
                        <span v-if="currentLanguage === 'src'">{{researchInfrastructure.descriptionSrCyr}}</span>
                        <span v-if="currentLanguage === 'en'">{{researchInfrastructure.descriptionEn}}</span> -->

                    </b-col>
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.keywords')">Keywords</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span v-if="currentLanguage === 'sr'">{{researchInfrastructure.keywords}}</span>
                        <span v-if="currentLanguage === 'src'">{{researchInfrastructure.keywordsCyr}}</span>
                        <span v-if="currentLanguage === 'en'">{{researchInfrastructure.keywordsEn}}</span>
                    </b-col>  
                    <b-col sm="2" class="border-table">                        
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.subdomains')">Subdomains</span>
                    </b-col>
                    <b-col sm="4" class="border-table">                        
                        <span v-for="(subdomains, i) in researchInfrastructure.subdomains" :key="subdomains.id">                            
                            <span v-if="currentLanguage == 'sr'">{{subdomains.subdomain}}</span>
                            <span v-if="currentLanguage == 'en'">{{subdomains.subdomainEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{subdomains.subdomainCyr}}</span>                            
                            <br>
                        </span>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                   <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.departmentName')">Department Name</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span>{{researchInfrastructure.departmentName}}</span>
                    </b-col> 
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.riWebsite')">Ri Website</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <span>{{researchInfrastructure.riWebsite}}</span>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table"> 
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.accessType')" >Access Type</span>                                             
                    </b-col>
                    <b-col sm="4" class="border-table">                        
                        <div v-if="researchInfrastructure.accessType">                            
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessType.type}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessType.typeEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessType.typeCyr}}</span>                            
                        </div>
                    </b-col>
                    <b-col sm="2" class="border-table">   
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.category')" >Category</span>                               
                    </b-col>                        
                    <b-col sm="4" class="border-table">                           
                        <div v-if="researchInfrastructure.category">                            
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.category.category}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.category.categoryEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.category.categoryCyr}}</span>                            
                        </div>
                    </b-col>                                            
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.status')">Status</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.status">                            
                            <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.status.status}}</span>
                            <span v-if="currentLanguage == 'en'">{{researchInfrastructure.status.statusEn}}</span>
                            <span v-if="currentLanguage == 'src'">{{researchInfrastructure.status.statusCyr}}</span>                            
                        </div>
                    </b-col>                    
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.accessMode')">Access Mode</span>
                    </b-col>                        
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.accessMode">                            
                                <span v-if="currentLanguage == 'sr'">{{researchInfrastructure.accessMode.mode}}</span>
                                <span v-if="currentLanguage == 'en'">{{researchInfrastructure.accessMode.modeEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{researchInfrastructure.accessMode.modeCyr}}</span>                            
                        </div>
                    </b-col>    
                </b-form-row>    
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.technicalSpecification')">Technical Specification Link Sr</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                         <span class="link-style" v-if="researchInfrastructure.id != null && researchInfrastructure.technicalSpecificationLinkSr != null && (currentLanguage === 'sr' || currentLanguage === 'src')">
                            <b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.technicalSpecificationLinkSr)">
                                {{researchInfrastructure.technicalSpecificationLinkSr}}
                            </b-button>
                        </span>
                        <span class="link-style" v-if="researchInfrastructure.id != null && researchInfrastructure.technicalSpecificationLinkEn != null && currentLanguage === 'en'">
                            <b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.technicalSpecificationLinkEn)">
                                {{researchInfrastructure.technicalSpecificationLinkEn}}
                            </b-button>
                        </span>
                    </b-col>  
                     <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.userManual')">User Manual Link Sr</span>
                    </b-col>                        
                    <b-col sm="4" class="border-table">
                        <span class="link-style" v-if="researchInfrastructure.id != null && researchInfrastructure.userManualLinkSr != null && (currentLanguage === 'sr' || currentLanguage === 'src')">
                            <b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.userManualLinkSr)">
                                {{researchInfrastructure.userManualLinkSr}}
                            </b-button>
                        </span>
                        <span class="link-style" v-if="researchInfrastructure.id != null && researchInfrastructure.userManualLinkEn != null && currentLanguage === 'en'">
                            <b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.userManualLinkEn)">
                                {{researchInfrastructure.userManualLinkEn}}
                            </b-button>
                        </span>

                    </b-col>                   
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">                        
                            <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.subcategories')">Subcategories</span>
                    </b-col>                        
                    <b-col sm="4" class="border-table">                        
                        <span v-for="(subcategories, i) in researchInfrastructure.subcategories" :key="subcategories.id">
                                <span v-if="currentLanguage == 'sr'">{{subcategories.subcategory}}</span>
                                <span v-if="currentLanguage == 'en'">{{subcategories.subcategoryEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{subcategories.subCategoryCyr}}</span>
                            <br>
                        </span>
                    </b-col>                   
                    <b-col sm="2" class="border-table">
                            <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.riLogo')">Ri Logo</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                            <img :src="loadImage(researchInfrastructure.riLogo)" width="50"/>
                    </b-col>
                </b-form-row>               
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.owner')" >Owner</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.owner">
                            {{researchInfrastructure.owner.name}}
                        </div>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.manager')" >Manager</span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.manager">
                            {{researchInfrastructure.manager.user.lastName}} {{researchInfrastructure.manager.user.firstName}}
                        </div>
                    </b-col>
                </b-form-row>

                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.acquisitionYear')" ></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.acquisitionYear">
                            {{researchInfrastructure.acquisitionYear}}
                        </div>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.financeSource')" ></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.financeSource">
                            {{researchInfrastructure.financeSource}} 
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-1 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.purchaseValueEuro')" ></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.purchaseValueEuro">
                            {{researchInfrastructure.purchaseValueEuro | currency('')}}
                        </div>
                    </b-col>
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.purchaseValueDin')" ></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.purchaseValueDin">
                            {{researchInfrastructure.purchaseValueDin | currency('')}} 
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-1 mb-4 row-background">
                    <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.projectName')" ></span>
                    </b-col>
                    <b-col sm="4" class="border-table">
                        <div v-if="researchInfrastructure.projectName">
                            {{researchInfrastructure.projectName}}
                        </div>
                    </b-col>                    
                </b-form-row>




                <b-form-row class="mt-2 mb-4 ">
                   <b-col sm="2" class="border-table">
                        <span class="span-display" v-text="$t('riportalApp.researchInfrastructure.images')" ></span>
                    </b-col>
                   <b-col sm="3" v-for="image in researchInfrastructure.images" :key="image.id">
                        <img :src="loadImage(image.filename)" width="100" height="auto"/>
                   </b-col>

                </b-form-row>
                
                
                
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./research-infrastructure-details.component.ts">
</script>
