<template>  
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.researchInfrastructure.home.createOrEditLabel" v-text="$t('riportalApp.researchInfrastructure.home.createOrEditLabel')">Create or edit a ResearchInfrastructure</h2>
                <div class="border border-primary" style="padding:35px;margin:20px;">
					<h3 v-text="$t('riportalApp.researchInfrastructure.titlebasic')">Osnovni podaci</h3>
					<br>
                <b-form-row>
					<b-col sm="3">
                        <div class="form-group" v-if="currentLanguage === 'sr' || currentLanguage === 'en'">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.nameSr')" for="research-infrastructure-nameSr">Name Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameSr')" @mouseout="hideTooltip('nameSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameSr'" v-html="$t('riportalApp.help.nameSr_desc')"></div>
                            <input type="text" ref="nameSr" class="form-control" name="nameSr" id="research-infrastructure-nameSr" @keyup="getData()" autocomplete="off"
                                :class="{'valid': !$v.researchInfrastructure.nameSr.$invalid, 'invalid': $v.researchInfrastructure.nameSr.$invalid }" v-model="$v.researchInfrastructure.nameSr.$model" />
                        	<div class="panel-footer" v-if="search_data.length">
        						<ul class="list-group" @click.stop>
	          							<a class="list-group-item" v-for="data1 in search_data" @click="getNameSr(data1,'nameSr')">{{ data1.nameSr }}</a>
        						</ul>
      						</div>
                        </div>
                        <div class="form-group" v-if="currentLanguage === 'src'">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.nameSr')" for="research-infrastructure-nameSr">Name Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameSr')" @mouseout="hideTooltip('nameSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameSr'" v-html="$t('riportalApp.help.nameSr_desc')"></div>
                            <input type="text" ref="nameSrCyr" class="form-control" name="nameSrCyr" id="research-infrastructure-nameSrCyr" 
                                :class="{'valid': !$v.researchInfrastructure.nameSrCyr.$invalid, 'invalid': $v.researchInfrastructure.nameSrCyr.$invalid }" v-model="$v.researchInfrastructure.nameSrCyr.$model" />
                        </div>
                       
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.nameEn')" for="research-infrastructure-nameEn">Name En</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameEn')" @mouseout="hideTooltip('nameEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameEn'" v-html="$t('riportalApp.help.nameEn_desc')"></div>
                            <input type="text" class="form-control" name="nameEn" id="research-infrastructure-nameEn"  @keyup="getDataEn()" autocomplete="off"
                                :class="{'valid': !$v.researchInfrastructure.nameEn.$invalid, 'invalid': $v.researchInfrastructure.nameEn.$invalid }" v-model="$v.researchInfrastructure.nameEn.$model" />
                        	<div class="panel-footer" v-if="search_dataEn.length">
        						<ul class="list-group" @click.stop>
          							<a class="list-group-item" v-for="data1 in search_dataEn" @click="getNameEn(data1,'nameEn')">{{ data1.nameEn }}</a>
        						</ul>
      						</div>
                        </div>
                    </b-col>
                    <b-col sm="3">                        
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.accessType')" for="research-infrastructure-accessType">Access Type</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('accessType')" @mouseout="hideTooltip('accessType')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'accessType'" v-html="$t('riportalApp.help.accessType_desc')"></div>
                            <select class="form-control" id="research-infrastructure-accessType" name="accessType" v-model="researchInfrastructure.accessType">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.accessType && riAccessTypeOption.id === researchInfrastructure.accessType.id ? researchInfrastructure.accessType : riAccessTypeOption" v-for="riAccessTypeOption in riAccessTypes" :key="riAccessTypeOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{riAccessTypeOption.type}}</span>
                                    <span v-if="currentLanguage == 'en'">{{riAccessTypeOption.typeEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{riAccessTypeOption.typeCyr}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">                        
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.category')" for="research-infrastructure-category">Category</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('category')" @mouseout="hideTooltip('category')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'category'" v-html="$t('riportalApp.help.category_desc')"></div>
                            <select class="form-control" id="research-infrastructure-category" name="category" v-model="researchInfrastructure.category">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.category && riCategoryOption.id === researchInfrastructure.category.id ? researchInfrastructure.category : riCategoryOption" v-for="riCategoryOption in riCategories" :key="riCategoryOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{riCategoryOption.category}}</span>
                                    <span v-if="currentLanguage == 'en'">{{riCategoryOption.categoryEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{riCategoryOption.categoryCyr}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>                        
                </b-form-row>
                <b-form-row>                    
                    <b-col sm="3">
                         <div class="form-group" v-if="currentLanguage === 'sr' || currentLanguage === 'en'">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.keywords')" for="research-infrastructure-keywords">Keywords</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywords')" @mouseout="hideTooltip('keywords')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywords'" v-html="$t('riportalApp.help.keywords_desc')"></div>
                            <div>
                                <input type="text" class="form-control" name="keywords" id="research-infrastructure-keywords"
                                :class="{'valid': !$v.researchInfrastructure.keywords.$invalid, 'invalid': $v.researchInfrastructure.keywords.$invalid }" v-model="$v.researchInfrastructure.keywords.$model" />
                            </div>
                        </div>                  
                        <div class="form-group" v-if="currentLanguage === 'src'">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.keywords')" for="research-infrastructure-keywords">Keywords</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywords')" @mouseout="hideTooltip('keywords')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywords'" v-html="$t('riportalApp.help.keywords_desc')"></div>
                            <input type="text" class="form-control" name="keywordsCyr" id="research-infrastructure-keywordsCyr"
                                :class="{'valid': !$v.researchInfrastructure.keywordsCyr.$invalid, 'invalid': $v.researchInfrastructure.keywordsCyr.$invalid }" v-model="$v.researchInfrastructure.keywordsCyr.$model" />
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.keywordsEn')" for="research-infrastructure-keywords">Keywords</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywordsEn')" @mouseout="hideTooltip('keywordsEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywordsEn'" v-html="$t('riportalApp.help.keywordsEn_desc')"></div>
                            <input type="text" class="form-control" name="keywordsEn" id="research-infrastructure-keywordsEn"
                                :class="{'valid': !$v.researchInfrastructure.keywordsEn.$invalid, 'invalid': $v.researchInfrastructure.keywordsEn.$invalid }" v-model="$v.researchInfrastructure.keywordsEn.$model" />                       
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.departmentName')" for="research-infrastructure-departmentName">Department Name</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('departmentName')" @mouseout="hideTooltip('departmentName')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'departmentName'" v-html="$t('riportalApp.help.departmentName_desc')"></div>
                            <input type="text" class="form-control" name="departmentName" id="research-infrastructure-departmentName"
                                :class="{'valid': !$v.researchInfrastructure.departmentName.$invalid, 'invalid': $v.researchInfrastructure.departmentName.$invalid }" v-model="$v.researchInfrastructure.departmentName.$model" />
                        </div>
                    </b-col>
                    <b-col sm="3">
                        
                    </b-col>
                </b-form-row>
                  <b-form-row>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.riWebsite')" for="research-infrastructure-riWebsite">Ri Website</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('riWebsite')" @mouseout="hideTooltip('riWebsite')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'riWebsite'" v-html="$t('riportalApp.help.riWebsite_desc')"></div>
                            <input type="text" class="form-control" name="riWebsite" id="research-infrastructure-riWebsite"
                                :class="{'valid': !$v.researchInfrastructure.riWebsite.$invalid, 'invalid': $v.researchInfrastructure.riWebsite.$invalid }" v-model="$v.researchInfrastructure.riWebsite.$model" />
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.status')" for="research-infrastructure-status">Status</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('status')" @mouseout="hideTooltip('status')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'status'" v-html="$t('riportalApp.help.status_desc')"></div>
                            <select class="form-control" id="research-infrastructure-status" name="status" v-model="researchInfrastructure.status">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.status && riStatusOption.id === researchInfrastructure.status.id ? researchInfrastructure.status : riStatusOption" v-for="riStatusOption in riStatuses" :key="riStatusOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{riStatusOption.status}}</span>
                                    <span v-if="currentLanguage == 'en'">{{riStatusOption.statusEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{riStatusOption.statusCyr}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.accessMode')" for="research-infrastructure-accessMode">Access Mode</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('accessMode')" @mouseout="hideTooltip('accessMode')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'accessMode'" v-html="$t('riportalApp.help.accessMode_desc')"></div>
                            <select class="form-control" id="research-infrastructure-accessMode" name="accessMode" v-model="researchInfrastructure.accessMode">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.accessMode && riAccessModeOption.id === researchInfrastructure.accessMode.id ? researchInfrastructure.accessMode : riAccessModeOption" v-for="riAccessModeOption in riAccessModes" :key="riAccessModeOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{riAccessModeOption.mode}}</span>
                                    <span v-if="currentLanguage == 'src'">{{riAccessModeOption.modeCyr}}</span>
                                    <span v-if="currentLanguage == 'en'">{{riAccessModeOption.modeEn}}</span>                                    
                                </option>
                            </select>
                        </div>
                    </b-col>                        
                </b-form-row>
                <b-form-row>
                    <b-col sm="3">
                        <div class="form-group position-relative">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.descriptionSr')" for="research-infrastructure-descriptionSr">Description Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('descriptionSr')" @mouseout="hideTooltip('descriptionSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'descriptionSr'" v-html="$t('riportalApp.help.descriptionSr_desc')"></div>
                            <b-form-textarea id="research-infrastructure-descriptionSr" v-model="$v.researchInfrastructure.descriptionSr.$model" placeholder="Enter something..." rows="4" 
                                max-rows="6"
                                :class="{'valid': !$v.researchInfrastructure.descriptionSr.$invalid, 'invalid': $v.researchInfrastructure.descriptionSr.$invalid }"
                            ></b-form-textarea>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group position-relative">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.descriptionEn')" for="research-infrastructure-descriptionEn">Description En</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('descriptionEn')" @mouseout="hideTooltip('descriptionEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'descriptionEn'" v-html="$t('riportalApp.help.descriptionEn_desc')"></div>
                            <b-form-textarea id="research-infrastructure-descriptionEn" v-model="$v.researchInfrastructure.descriptionEn.$model" rows="4" 
                                max-rows="6"
                                :class="{'valid': !$v.researchInfrastructure.descriptionEn.$invalid, 'invalid': $v.researchInfrastructure.descriptionEn.$invalid }"
                            ></b-form-textarea>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group position-relative">
                            <label v-text="$t('riportalApp.researchInfrastructure.subdomains')" for="research-infrastructure-subdomains">Subdomains</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('subdomains')" @mouseout="hideTooltip('subdomains')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'subdomains'" v-html="$t('riportalApp.help.subdomains_desc')"></div>
                            <select class="form-control" id="research-infrastructure-subdomains" multiple name="subdomains" v-model="researchInfrastructure.subdomains">
                                <option v-bind:value="getSelected(researchInfrastructure.subdomains, scientificSubdomainOption)" v-for="scientificSubdomainOption in scientificSubdomains" :key="scientificSubdomainOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{scientificSubdomainOption.subdomain}}</span>
                                    <span v-if="currentLanguage == 'en'">{{scientificSubdomainOption.subdomainEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{scientificSubdomainOption.subdomainCyr}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group position-relative">
                            <label v-text="$t('riportalApp.researchInfrastructure.subcategories')" for="research-infrastructure-subcategories">Subcategories</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('subcategories')" @mouseout="hideTooltip('subcategories')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'subcategories'" v-html="$t('riportalApp.help.subcategories_desc')"></div>
                            <select class="form-control" id="research-infrastructure-subcategories" multiple name="subcategories" v-model="researchInfrastructure.subcategories">
                                <option v-bind:value="getSelected(researchInfrastructure.subcategories, riSubcategoryOption)" v-for="riSubcategoryOption in riSubcategories" :key="riSubcategoryOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{riSubcategoryOption.subcategory}}</span>
                                    <span v-if="currentLanguage == 'en'">{{riSubcategoryOption.subcategoryEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{riSubcategoryOption.subCategoryCyr}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>                        
                </b-form-row>
                <b-form-row>
                    <b-col sm="3">
                        <div class="form-group" v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.owner')" for="research-infrastructure-owner">Owner</label>
                            <span style="color:red">*</span>
                            <select class="form-control" id="research-infrastructure-owner" name="owner" v-model="researchInfrastructure.owner" required>
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.owner && riResearchOrganizationOption.id === researchInfrastructure.owner.id ? researchInfrastructure.owner : riResearchOrganizationOption" v-for="riResearchOrganizationOption in riResearchOrganizations" :key="riResearchOrganizationOption.id">{{riResearchOrganizationOption.name}}</option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group" v-if="authenticated && (hasAnyRole(['PA', 'RPRIPO']) || hasAnyAuthority('ROLE_ADMIN'))">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.manager')" for="research-infrastructure-manager">Manager</label>
                            <select class="form-control" id="research-infrastructure-manager" name="manager" v-model="researchInfrastructure.manager">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.manager && portalUserOption.id === researchInfrastructure.manager.id ? researchInfrastructure.manager : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.user.lastName}} {{portalUserOption.user.firstName}}</option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <!--
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.contactPerson')" for="research-infrastructure-contactPerson">Contact Person</label>
                            <select class="form-control" id="research-infrastructure-contactPerson" name="contactPerson" v-model="researchInfrastructure.contactPerson">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="researchInfrastructure.contactPerson && portalUserOption.id === researchInfrastructure.contactPerson.id ? researchInfrastructure.contactPerson : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.user.lastName}} {{portalUserOption.user.firstName}}</option>
                            </select>
                        </div>
                        -->
                    </b-col>
                    <b-col sm="3">
                        
                    </b-col>                        
                </b-form-row>
                <b-form-row>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.acquisitionYear')" for="research-infrastructure-acquisition-year"></label>
                            <input type="number" class="form-control" name="riWebsite" id="research-infrastructure-acquisition-year"
                                :class="{'valid': !$v.researchInfrastructure.acquisitionYear.$invalid, 'invalid': $v.researchInfrastructure.acquisitionYear.$invalid }" v-model="$v.researchInfrastructure.acquisitionYear.$model" />

                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.purchaseValueEuro')" for="research-infrastructure-purchaseValueEuro"></label>
                            <input type="number" class="form-control" name="purchaseValueEuro" id="research-infrastructure-purchaseValueEuro"
                                :class="{'valid': !$v.researchInfrastructure.purchaseValueEuro.$invalid, 'invalid': $v.researchInfrastructure.purchaseValueEuro.$invalid }" v-model="$v.researchInfrastructure.purchaseValueEuro.$model" />
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.purchaseValueDin')" for="research-infrastructure-purchaseValueDin"></label>
                            <input type="number" class="form-control" name="purchaseValueDin" id="research-infrastructure-purchaseValueDin"
                                :class="{'valid': !$v.researchInfrastructure.purchaseValueDin.$invalid, 'invalid': $v.researchInfrastructure.purchaseValueDin.$invalid }" v-model="$v.researchInfrastructure.purchaseValueDin.$model" />                      
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.financeSource')" for="research-infrastructure-financeSource"></label>
                             <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('financeSource')" @mouseout="hideTooltip('financeSource')"  class="question-icon-class"></font-awesome-icon>
                             <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'financeSource'" v-html="$t('riportalApp.help.financeSource_desc')"></div>  
                            <input type="text" class="form-control" name="financeSource" id="research-infrastructure-financeSource"
                                 v-model="$v.researchInfrastructure.financeSource.$model" />                        
                        </div>                        
                    </b-col>                        
                </b-form-row>
                <b-form-row>                    
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.projectName')" for="research-infrastructure-projectName"></label>
                             <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('projectName')" @mouseout="hideTooltip('projectName')"  class="question-icon-class"></font-awesome-icon>
                             <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'projectName'" v-html="$t('riportalApp.help.projectName_desc')"></div> 
                            <input type="text" class="form-control" name="projectName" id="research-infrastructure-projectName"
                                 v-model="$v.researchInfrastructure.projectName.$model" />                          
                        </div>                        
                    </b-col>                        
                </b-form-row>
                <!--
                <b-form-row class="mb-4">
                    <b-col sm="3">
                        <b-row>
                            <b-col sm="12">
                                    <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.riLogo')" for="research-infrastructure-riLogo">Ri Logo</label>
                                    <b-form-file style="margin-bottom:30px"
                                        v-model="logo"                            
                                        :placeholder="placeholdertextLogo"
                                        multiple
                                        drop-placeholder="Drop file here..."
                                        :browse-text="browseText"
                                        accept="image/*"
                                        :file-name-formatter="formatNames1"
                                    >                                                                        
                                    </b-form-file>                                                               

                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col sm="12">
                                <button type="button" id="save-entity"  class="btn btn-info" v-on:click="saveLogo()">
                                    <span v-text="$t('entity.action.saveLogo')">Save</span>
                                </button>
                            </b-col>
                        </b-row>
                    </b-col>
                    <b-col sm="3">
                        <img :src="loadImage(researchInfrastructure.riLogo)" width="50" class="ml-4 mt-4"/>
                    </b-col>
                </b-form-row>
				-->
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" ref='form_submit' id="save-entity" :disabled="$v.researchInfrastructure.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
  </div>
                
 <div class="border border-primary" style="padding:35px;margin:20px;">
	 <h3 v-text="$t('riportalApp.researchInfrastructure.titleadditional')">Osnovni podaci</h3>
	 <h5 v-text="$t('riportalApp.researchInfrastructure.titleadditionalcomment')">Osnovni podaci</h5>
					<br>
                <b-form-row>
					   <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.userManualLinkEn')" for="research-infrastructure-userManualLinkEn">User Manual Link En</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('userManualLinkEn')" @mouseout="hideTooltip('userManualLinkEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'userManualLinkEn'" v-html="$t('riportalApp.help.userManualLinkEn_desc')"></div> 
                            <input type="text" readonly ref="userManualLinkEn" class="form-control" name="userManualLinkEn" id="research-infrastructure-userManualLinkEn"
                                :class="{'valid': !$v.researchInfrastructure.userManualLinkEn.$invalid, 'invalid': $v.researchInfrastructure.userManualLinkEn.$invalid }" v-model="$v.researchInfrastructure.userManualLinkEn.$model" />        
                        </div>
                         <div v-if="researchInfrastructure.id != null && researchInfrastructure.userManualLinkEn != null && this.linked3!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.userManualLinkEn)">{{researchInfrastructure.userManualLinkEn}}</b-button></div>
                        <upload-files ref="upload_3_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure" :formn="this" text_field="userManualLinkEn" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col>
					<b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.userManualLinkSr')" for="research-infrastructure-userManualLinkSr">User Manual Link Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('userManualLinkSr')" @mouseout="hideTooltip('userManualLinkSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'userManualLinkSr'" v-html="$t('riportalApp.help.userManualLinkSr_desc')"></div>
                            <input type="text" readonly ref="userManualLinkSr" class="form-control" name="userManualLinkSr" id="research-infrastructure-userManualLinkSr"
                                :class="{'valid': !$v.researchInfrastructure.userManualLinkSr.$invalid, 'invalid': $v.researchInfrastructure.userManualLinkSr.$invalid }" v-model="$v.researchInfrastructure.userManualLinkSr.$model" />     
                        </div>
                        <div v-if="researchInfrastructure.id != null && researchInfrastructure.userManualLinkSr != null && this.linked4!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.userManualLinkSr)">{{researchInfrastructure.userManualLinkSr}}</b-button></div>
                        <upload-files ref="upload_4_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure"  :formn="this" text_field="userManualLinkSr" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col>  
                    
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkSr')" for="research-infrastructure-technicalSpecificationLinkSr">Technical Specification Link Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('technicalSpecificationLinkSr')" @mouseout="hideTooltip('technicalSpecificationLinkSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'technicalSpecificationLinkSr'" v-html="$t('riportalApp.help.technicalSpecificationLinkSr_desc')"></div> 
                            <input type="text" readonly ref="technicalSpecificationLinkSr" class="form-control" name="technicalSpecificationLinkSr" id="research-infrastructure-technicalSpecificationLinkSr"
                                :class="{'valid': !$v.researchInfrastructure.technicalSpecificationLinkSr.$invalid, 'invalid': $v.researchInfrastructure.technicalSpecificationLinkSr.$invalid }" v-model="$v.researchInfrastructure.technicalSpecificationLinkSr.$model" />  
                        </div>
                        
                        <div v-if="researchInfrastructure.id != null && researchInfrastructure.technicalSpecificationLinkSr != null && this.linked0!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.technicalSpecificationLinkSr)">{{researchInfrastructure.technicalSpecificationLinkSr}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure" :formn="this" text_field="technicalSpecificationLinkSr" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.technicalSpecificationLinkEn')" for="research-infrastructure-technicalSpecificationLinkEn">Technical Specification Link En</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('technicalSpecificationLinkEn')" @mouseout="hideTooltip('technicalSpecificationLinkEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'technicalSpecificationLinkEn'" v-html="$t('riportalApp.help.technicalSpecificationLinkEn_desc')"></div> 
                            <input type="text" readonly ref="technicalSpecificationLinkEn" class="form-control" name="technicalSpecificationLinkEn" id="research-infrastructure-technicalSpecificationLinkEn"
                                :class="{'valid': !$v.researchInfrastructure.technicalSpecificationLinkEn.$invalid, 'invalid': $v.researchInfrastructure.technicalSpecificationLinkEn.$invalid }" v-model="$v.researchInfrastructure.technicalSpecificationLinkEn.$model" />
                        </div>
                        
                        <div v-if="researchInfrastructure.id != null && researchInfrastructure.technicalSpecificationLinkEn != null && this.linked1!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.technicalSpecificationLinkEn)">{{researchInfrastructure.technicalSpecificationLinkEn}}</b-button></div>
                        <upload-files ref="upload_1_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure"  :formn="this" text_field="technicalSpecificationLinkEn" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col>                        
                </b-form-row>
                <b-form-row>
                 <b-col sm="3">
                        <div class="form-group position-relative">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.termsOfUseSr')" for="research-infrastructure-termsOfUseSr">Tearm of Use Sr</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('termsOfUseSr')" @mouseout="hideTooltip('termsOfUseSr')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'termsOfUseSr'" v-html="$t('riportalApp.help.termsOfUseSr_desc')"></div>  
                            <input type="text" readonly ref="termsOfUseSr" class="form-control" name="termsOfUseSr" id="research-infrastructure-termsOfUseSr"
                                :class="{'valid': !$v.researchInfrastructure.termsOfUseSr.$invalid, 'invalid': $v.researchInfrastructure.termsOfUseSr.$invalid }" v-model="$v.researchInfrastructure.termsOfUseSr.$model" />
                        </div>
                        
                        <div v-if="researchInfrastructure.id != null && researchInfrastructure.termsOfUseSr != null && this.linked5!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.termsOfUseSr)">{{researchInfrastructure.termsOfUseSr}}</b-button></div>
                        <upload-files ref="upload_1_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure"  :formn="this" text_field="termsOfUseSr" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col> 
                    <b-col sm="3">
                        <div class="form-group position-relative">
                            <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.termsOfUseEn')" for="research-infrastructure-termsOfUseEn">Tearm of Use En</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('termsOfUseEn')" @mouseout="hideTooltip('termsOfUseEn')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'termsOfUseEn'" v-html="$t('riportalApp.help.termsOfUseEn_desc')"></div>  
                            <input type="text" readonly ref="termsOfUseEn" class="form-control" name="termsOfUseEn" id="research-infrastructure-termsOfUseEn"
                                :class="{'valid': !$v.researchInfrastructure.termsOfUseEn.$invalid, 'invalid': $v.researchInfrastructure.termsOfUseEn.$invalid }" v-model="$v.researchInfrastructure.termsOfUseEn.$model" /> 
                        </div>
                        
                        <div v-if="researchInfrastructure.id != null && researchInfrastructure.termsOfUseEn != null && this.linked6!=null"><b-button variant="link" class="link-style" @click="formLink(researchInfrastructure.termsOfUseEn)">{{researchInfrastructure.termsOfUseEn}}</b-button></div>
                        <upload-files ref="upload_1_M" v-if="researchInfrastructure.id != null" shortname='ri' :pageObj="researchInfrastructure"  :formn="this" text_field="termsOfUseEn" rid="$t('riportalApp.researchInfrastructure.id')"></upload-files>
                    </b-col>
                    <b-col sm="6">
                        <b-row>
                            <b-col sm="12">
                                <label class="form-control-label position-relative" v-text="$t('riportalApp.researchInfrastructure.images')"></label>   
                                <b-form-file style="margin-bottom:30px"
                                    v-model="imageFiles"                            
                                    :placeholder="placeholdertext"
                                    multiple
                                    drop-placeholder="Drop file here..."
                                    :browse-text="browseText"
                                    accept="image/*"
                                    :file-name-formatter="formatNames"
                                >                                                                        
                                </b-form-file>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col sm="11" offset="1">
                                <button type="button" id="save-entity"  class="btn btn-primary" v-on:click="saveImages()">
                                    <span v-text="$t('entity.action.saveImages')">Save</span>
                                </button>
                            </b-col>
                        </b-row>
                        <b-row class="mt-4" v-if="researchInfrastructure.images">
                            <b-col sm="11" offset="1">
                                <h5 v-text="$t('riportalApp.researchInfrastructure.existingImages')"></h5>
                            </b-col>
                        </b-row>
                        <b-row v-for="image in researchInfrastructure.images" :key="image.id" class="mt-4">
                            <b-col sm="3" class="text-right">
                                <img :src="loadImage(image.filename)" width="50"/>
                            </b-col>
                            <b-col sm="3">
                                <button type="button" id="save-entity"  class="btn btn-danger  mt-1" v-on:click="deleteImage(image.id)">
                                    <span v-text="$t('entity.action.delete')"></span>
                                </button>
                            </b-col>
                        </b-row>
                    </b-col>      
                
                </b-form-row>
               <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" ref='form_submit' id="save-entity"  class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <b-button v-on:click="openCalendar()"
                            variant="info"
                            class="btn"
                            v-b-modal.removeEntity
                            v-text="$t('riportalApp.researchInfrastructure.calendar')"                         
                            >                
                        <span >Calendar</span>
                    </b-button>
                </div>
 </div>
            </form>
        </div>

        <b-modal ref="calendar" id="calendar"  size="lg" modal-class="modal-fullscreen" >
            <span slot="modal-title"><span id="riportalApp.researchInfrastructure.calendar" v-text="$t('riportalApp.researchInfrastructure.calendar')"></span></span>
            <div class="fc fc-media-screen fc-direction-ltr fc-theme-standard">
				<FullCalendar ref="fullCalendar" :options="calendarOptions" />
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
            </div>
        </b-modal>

	     <b-modal ref="calevent" id="calevent"  size="lg" modal-class="modal-mediumscreen">
            <span slot="modal-title"><span v-text="$t('riportalApp.researchInfrastructure.eventMainTitleDialog')"></span></span>
            <div class="fc fc-media-screen fc-direction-ltr fc-theme-standard">
				 <div class="form-group">
	                 <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.eventTitle')" for="evtTitle">Infrastructure Ocupancy Title</label>
	                 <input type="text" ref="evtTitle" class="form-control" name="evtTitle" id="evtTitle" />
	             </div>
	             <div class="form-group">
	                <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.eventStartDate')" for="evtStartDate">Infrastructure Ocupancy Start Date</label>
                 	<input type="date"  ref="evtStartDate" class="form-control" name="evtStartDate" id="evtStartDate" />
                 </div>
                 <div class="form-group">
	                <label class="form-control-label" v-text="$t('riportalApp.researchInfrastructure.eventEndDate')" for="evtEndDate">Infrastructure Ocupancy End Date</label>
                 	<input type="date" ref="evtEndDate" class="form-control" name="evtEndDate" id="evtEndDate"  />
                 </div>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.save')" v-on:click="closeEventDialogOK()">OK</button>
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeEventDialog()">Cancel</button>
            </div>
        </b-modal>
		

    </div>
</template>
<script lang="ts" src="./research-infrastructure-update.component.ts">
</script>
<style>
.fc { /* the calendar root */
  width: 100%;
  height:100%;
  margin: 0 auto;
}
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    max-height: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    position: fixed;
    overflow: auto;
    fill-opacity: 1;
    z-index: 100000;
}
.modal-mediumscreen .modal-dialog {
    max-width: 50%;
    margin: 0 auto;
    height: 50vh;
    display: flex;
    z-index: 110000;
}

.question-icon-class{
    color: #17a2b8;
}

.tooltip-hover {
  position: absolute;
  top: 40%;
  left: 30%;
  display: none;
  z-index: 100000;
}
.form-group:hover .tooltip-hover {
  display: block;
}

</style>