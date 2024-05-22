<template>
    <div class="row justify-content-center">
        <div class="col-12">
	        <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riService.home.createOrEditLabel" v-text="$t('riportalApp.riService.home.createOrEditLabel')">Create or edit a RiService</h2>
                 <div class="border border-primary" style="padding:35px;margin:20px;">
					<h3 v-text="$t('riportalApp.researchInfrastructure.titlebasic')">Osnovni podaci</h3>
					<br>
                <b-form-row>
                    <b-col sm="3"  v-if="currentLanguage === 'sr' || currentLanguage === 'en'">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.nameSr')" for="ri-service-nameSr">Name Sr</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameSr')" @mouseout="hideTooltip('nameSr')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameSr'" v-html="$t('riportalApp.help-service.nameSr_desc')"></div>
                        <input type="text" class="form-control" name="nameSr" id="ri-service-nameSr" @keyup="getData()" 
                            :class="{'valid': !$v.riService.nameSr.$invalid, 'invalid': $v.riService.nameSr.$invalid }" v-model="$v.riService.nameSr.$model" />
                           
                    		<div class="panel-footer" v-if="search_data.length">
        						<ul class="list-group" @click.stop>
	          							<a class="list-group-item" v-for="data1 in search_data" @click="getNameSr(data1,'nameSr')">{{ data1.nameSr }}</a>
        						</ul>
      						</div>
                    </b-col>
                    <b-col sm="3"  v-if="currentLanguage === 'src'">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.nameSr')" for="ri-service-nameSrCyr">Name Sr</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameSr')" @mouseout="hideTooltip('nameSr')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameSr'" v-html="$t('riportalApp.help-service.nameSr_desc')"></div>
                        <input type="text" class="form-control" name="nameSrCyr" id="ri-service-nameSrCyr"
                            :class="{'valid': !$v.riService.nameSrCyr.$invalid, 'invalid': $v.riService.nameSrCyr.$invalid }" v-model="$v.riService.nameSrCyr.$model" />
                    </b-col>
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.nameEn')" for="ri-service-nameEn">Name En</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('nameEn')" @mouseout="hideTooltip('nameEn')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'nameEn'" v-html="$t('riportalApp.help-service.nameEn_desc')"></div>
                        <input type="text" class="form-control" name="nameEn" @keyup="getDataEn()" id="ri-service-nameEn"
                            :class="{'valid': !$v.riService.nameEn.$invalid, 'invalid': $v.riService.nameEn.$invalid }" v-model="$v.riService.nameEn.$model" />
                    		<div class="panel-footer" v-if="search_dataEn.length">
        						<ul class="list-group" @click.stop>
          							<a class="list-group-item" v-for="data1 in search_dataEn" @click="getNameEn(data1,'nameEn')">{{ data1.nameEn }}</a>
        						</ul>
      						</div>
                    </b-col>
                    <b-col sm="3" v-if="currentLanguage === 'sr' || currentLanguage === 'en'">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.keywords')" for="ri-service-keywords">Keywords</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywords')" @mouseout="hideTooltip('keywords')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywords'" v-html="$t('riportalApp.help-service.keywords_desc')"></div>
                        <input type="text" class="form-control" name="keywords" id="ri-service-keywords"
                            :class="{'valid': !$v.riService.keywords.$invalid, 'invalid': $v.riService.keywords.$invalid }" v-model="$v.riService.keywords.$model" />
                    </b-col>
                    <b-col sm="3" v-if="currentLanguage === 'src'">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.keywords')" for="ri-service-keywords">Keywords</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywords')" @mouseout="hideTooltip('keywords')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywords'" v-html="$t('riportalApp.help-service.keywords_desc')"></div>
                        <input type="text" class="form-control" name="keywordsCyr" id="ri-service-keywordsCyr"
                            :class="{'valid': !$v.riService.keywordsCyr.$invalid, 'invalid': $v.riService.keywordsCyr.$invalid }" v-model="$v.riService.keywordsCyr.$model" />
                    </b-col>
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.keywordsEn')" for="ri-service-keywords">Keywords</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('keywordsEn')" @mouseout="hideTooltip('keywordsEn')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'keywordsEn'" v-html="$t('riportalApp.help-service.keywordsEn_desc')"></div>
                        <input type="text" class="form-control" name="keywordsEn" id="ri-service-keywordsEn"
                            :class="{'valid': !$v.riService.keywordsEn.$invalid, 'invalid': $v.riService.keywordsEn.$invalid }" v-model="$v.riService.keywordsEn.$model" />
                    </b-col>
                    
                </b-form-row>
                <b-form-row class="mt-4">
                    <b-col sm="2">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.price')" for="ri-service-price"></label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('price')" @mouseout="hideTooltip('price')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'price'" v-html="$t('riportalApp.help-service.price_desc')"></div>
                        <input type="number" class="form-control" name="price" id="ri-service-price"
                            :class="{'valid': !$v.riService.price.$invalid, 'invalid': $v.riService.price.$invalid }" v-model="$v.riService.price.$model" />
                    </b-col>
                    <b-col sm="1">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.currency')" for="ri-service-currency"></label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('currency')" @mouseout="hideTooltip('currency')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'currency'" v-html="$t('riportalApp.help-service.currency_desc')"></div>                        
                        <select class="form-control" id="ri-service-currency" name="currency" v-model="riService.currency" required>
                            <option v-bind:value="null"></option>
                            <option value="eur">
                                <span v-if="currentLanguage == 'sr'">eur</span>
                                <span v-if="currentLanguage == 'en'">eur</span>
                                <span v-if="currentLanguage == 'src'">еур</span>
                            </option>
                            <option value="din">
                                <span v-if="currentLanguage == 'sr'">din</span>
                                <span v-if="currentLanguage == 'en'">din</span>
                                <span v-if="currentLanguage == 'src'">дин</span>
                            </option>
                        </select>    
                    </b-col>                    
                     <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.accreditationOrganizationName')" for="ri-service-accreditationOrganizationName">Accreditation Organization Name</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('accreditationOrganizationName')" @mouseout="hideTooltip('accreditationOrganizationName')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'accreditationOrganizationName'" v-html="$t('riportalApp.help-service.accreditationOrganizationName_desc')"></div>
                        <input type="text" class="form-control" name="accreditationOrganizationName" id="ri-service-accreditationOrganizationName"
                            :class="{'valid': !$v.riService.accreditationOrganizationName.$invalid, 'invalid': $v.riService.accreditationOrganizationName.$invalid }" v-model="$v.riService.accreditationOrganizationName.$model" />
                    </b-col>
                     <b-col sm="3">
                        
                    </b-col>
                </b-form-row>
                <b-form-row class="mt-4">
                     <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.researchInfrastructure')" for="ri-service-researchInfrastructure">Research Infrastructure</label>
                        <span style="color:red">*</span>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('researchInfrastructure')" @mouseout="hideTooltip('researchInfrastructure')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'researchInfrastructure'" v-html="$t('riportalApp.help-service.researchInfrastructure_desc')"></div>
                        <select class="form-control" id="ri-service-researchInfrastructure" name="researchInfrastructure" v-model="riService.researchInfrastructure" required>
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riService.researchInfrastructure && researchInfrastructureOption.id === riService.researchInfrastructure.id ? riService.researchInfrastructure : researchInfrastructureOption" v-for="researchInfrastructureOption in researchInfrastructures" :key="researchInfrastructureOption.id">
                                <span v-if="currentLanguage == 'sr'">{{researchInfrastructureOption.nameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{researchInfrastructureOption.nameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{researchInfrastructureOption.nameSrCyr}}</span>
                            </option>
                        </select>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.subtypes')" for="ri-service-subtypes">Subtypes</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('subtypes')" @mouseout="hideTooltip('subtypes')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'subtypes'" v-html="$t('riportalApp.help-service.subtypes_desc')"></div>
                            <select class="form-control" id="ri-service-subtypes" name="subtypes" v-model="riService.subtypes">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="riService.subtypes && serviceSubtypeOption.id === riService.subtypes.id ? riService.subtypes : serviceSubtypeOption" v-for="serviceSubtypeOption in serviceSubtypes" :key="serviceSubtypeOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{serviceSubtypeOption.subtype}}</span>
                                    <span v-if="currentLanguage == 'src'">{{serviceSubtypeOption.subtypeCyr}}</span>
                                    <span v-if="currentLanguage == 'en'">{{serviceSubtypeOption.subtypeEn}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.availability')" for="ri-service-availability">Availability</label>
                            <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('availability')" @mouseout="hideTooltip('availability')"  class="question-icon-class"></font-awesome-icon>
                            <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'availability'" v-html="$t('riportalApp.help-service.availability_desc')"></div>
                            <select class="form-control" id="ri-service-availability" name="availability" v-model="riService.availability">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="riService.availability && serviceAvailabilityOption.id === riService.availability.id ? riService.availability : serviceAvailabilityOption" v-for="serviceAvailabilityOption in serviceAvailabilities" :key="serviceAvailabilityOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{serviceAvailabilityOption.availability}}</span>
                                    <span v-if="currentLanguage == 'src'">{{serviceAvailabilityOption.availabilityCyr}}</span>
                                    <span v-if="currentLanguage == 'en'">{{serviceAvailabilityOption.availabilityEn}}</span>
                                </option>
                            </select>
                        </div>
                    </b-col>   
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.accredation')" for="ri-service-accredation">Accredation</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('accredation')" @mouseout="hideTooltip('accredation')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'accredation'" v-html="$t('riportalApp.help-service.accredation_desc')"></div>
                        <select class="form-control" id="ri-service-accredation" name="accredation" v-model="riService.accredation">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riService.accredation && serviceAccreditationOption.id === riService.accredation.id ? riService.accredation : serviceAccreditationOption" v-for="serviceAccreditationOption in serviceAccreditations" :key="serviceAccreditationOption.id">
                                <span v-if="currentLanguage == 'sr'">{{serviceAccreditationOption.status}}</span>
                                <span v-if="currentLanguage == 'en'">{{serviceAccreditationOption.statusEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{serviceAccreditationOption.statusCyr}}</span>
                            </option>
                        </select>
                    </b-col>                 
                </b-form-row>
                <b-form-row class="mb-4 mt-4">
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.descriptionSr')" for="ri-service-descriptionSr">Description Sr</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('descriptionSr')" @mouseout="hideTooltip('descriptionSr')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'descriptionSr'" v-html="$t('riportalApp.help-service.descriptionSr_desc')"></div>
                        <b-form-textarea id="descriptionSr" v-model="$v.riService.descriptionSr.$model" rows="4" 
                            max-rows="6"
                            :class="{'valid': !$v.riService.descriptionSr.$invalid, 'invalid': $v.riService.descriptionSr.$invalid }"
                        ></b-form-textarea>                    
                    </b-col>
               			<b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.descriptionEn')" for="ri-service-descriptionEn">Description En</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('descriptionEn')" @mouseout="hideTooltip('descriptionEn')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'descriptionEn'" v-html="$t('riportalApp.help-service.descriptionEn_desc')"></div>
                        <b-form-textarea id="descriptionEn" v-model="$v.riService.descriptionEn.$model" rows="4" 
                            max-rows="6"
                            :class="{'valid': !$v.riService.descriptionEn.$invalid, 'invalid': $v.riService.descriptionEn.$invalid }"
                        ></b-form-textarea>    
                    </b-col>
                    <b-col sm="3">
                        <label class="position-relative" v-text="$t('riportalApp.riService.subdomains')" for="ri-service-subdomains">Subdomains</label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('subdomains')" @mouseout="hideTooltip('subdomains')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'subdomains'" v-html="$t('riportalApp.help-service.subdomains_desc')"></div>
                        <select class="form-control" id="ri-service-subdomains" multiple name="subdomains" v-model="riService.subdomains">
                            <option v-bind:value="getSelected(riService.subdomains, scientificSubdomainOption)" v-for="scientificSubdomainOption in scientificSubdomains" :key="scientificSubdomainOption.id">
                                <span v-if="currentLanguage == 'sr'">{{scientificSubdomainOption.subdomain}}</span>
                                <span v-if="currentLanguage == 'en'">{{scientificSubdomainOption.subdomainEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{scientificSubdomainOption.subdomainCyr}}</span>
                            </option>
                        </select>
                    </b-col>
                </b-form-row>
                 <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <!-- :disabled="$v.riService.$invalid || isSaving" -->
                    <button type="submit" id="save-entity"  class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
                </div>
               <div class="border border-primary" style="padding:35px;margin:20px;">
	 			<h3 v-text="$t('riportalApp.researchInfrastructure.titleadditional')">Osnovni podaci</h3>
	 			<h5 v-text="$t('riportalApp.researchInfrastructure.titleadditionalcomment')">Osnovni podaci</h5>
                <b-form-row>
					<b-col sm="3">
                        <label class="form-control-label" v-text="$t('riportalApp.riService.termsOfUseSr')" for="ri-service-termsOfUseSr">Terms Of Use Sr</label>
                        <input type="text" readonly ref="termsOfUseSr" class="form-control" name="termsOfUseSr" id="ri-service-termsOfUseSr"
                            :class="{'valid': !$v.riService.termsOfUseSr.$invalid, 'invalid': $v.riService.termsOfUseSr.$invalid }" v-model="$v.riService.termsOfUseSr.$model" /><br>
                         <div v-if="riService.id != null && riService.termsOfUseSr != null && this.linked0 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.termsOfUseSr)">{{riService.termsOfUseSr}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="termsOfUseSr" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    <b-col sm="3">	
                        <label class="form-control-label" v-text="$t('riportalApp.riService.termsOfUseEn')" for="ri-service-termsOfUseEn">Terms Of Use En</label>
                        <input type="text" readonly ref="termsOfUseEn" class="form-control" name="termsOfUseEn" id="ri-service-termsOfUseEn"
                            :class="{'valid': !$v.riService.termsOfUseEn.$invalid, 'invalid': $v.riService.termsOfUseEn.$invalid }" v-model="$v.riService.termsOfUseEn.$model" /><br>
                               <div v-if="riService.id != null && riService.termsOfUseEn != null && this.linked1 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.termsOfUseEn)">{{riService.termsOfUseEn}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="termsOfUseEn" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    <!--
					<b-col sm="3">
                        <label class="form-control-label" v-text="$t('riportalApp.riService.privacyPolicySr')" for="ri-service-privacyPolicySr">Privacy Policy Sr</label>
                        <input type="text" readonly ref="privacyPolicySr" class="form-control" name="privacyPolicySr" id="ri-service-privacyPolicySr"
                            :class="{'valid': !$v.riService.privacyPolicySr.$invalid, 'invalid': $v.riService.privacyPolicySr.$invalid }" v-model="$v.riService.privacyPolicySr.$model" /><br>
                               <div v-if="riService.id != null && riService.privacyPolicySr != null && this.linked2 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.privacyPolicySr)">{{riService.privacyPolicySr}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="privacyPolicySr" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    <b-col sm="3">
                        <label class="form-control-label" v-text="$t('riportalApp.riService.privacyPolicyEn')" for="ri-service-privacyPolicyEn">Privacy Policy En</label>
                        <input type="text" readonly ref="privacyPolicyEn" class="form-control" name="privacyPolicyEn" id="ri-service-privacyPolicyEn"
                            :class="{'valid': !$v.riService.privacyPolicyEn.$invalid, 'invalid': $v.riService.privacyPolicyEn.$invalid }" v-model="$v.riService.privacyPolicyEn.$model" /><br>
                               <div v-if="riService.id != null && riService.privacyPolicyEn != null && this.linked3 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.privacyPolicyEn)">{{riService.privacyPolicyEn}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="privacyPolicyEn" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    -->
                    <b-col sm="3">
                        <label class="form-control-label" v-text="$t('riportalApp.riService.accessPolicySr')" for="ri-service-accessPolicySr">Access Policy Sr</label>
                        <input type="text" readonly ref="accessPolicySr" class="form-control" name="accessPolicySr" id="ri-service-accessPolicySr"
                            :class="{'valid': !$v.riService.accessPolicySr.$invalid, 'invalid': $v.riService.accessPolicySr.$invalid }" v-model="$v.riService.accessPolicySr.$model" /><br>
                               <div v-if="riService.id != null && riService.accessPolicySr != null && this.linked4 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.accessPolicySr)">{{riService.accessPolicySr}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="accessPolicySr" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    <b-col sm="3">
                        <label class="form-control-label" v-text="$t('riportalApp.riService.accessPolicyEn')" for="ri-service-accessPolicyEn">Access Policy En</label>
                        <input type="text" readonly ref="accessPolicyEn" class="form-control" name="accessPolicyEn" id="ri-service-accessPolicyEn"
                            :class="{'valid': !$v.riService.accessPolicyEn.$invalid, 'invalid': $v.riService.accessPolicyEn.$invalid }" v-model="$v.riService.accessPolicyEn.$model" /><br>
                               <div v-if="riService.id != null && riService.accessPolicyEn != null && this.linked5 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.accessPolicyEn)">{{riService.accessPolicyEn}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null"  shortname='ri' :pageObj='riService'  :formn="this" text_field="accessPolicyEn" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                </b-form-row>
                <b-form-row>    
                    
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.pricingPolicySr')" for="ri-service-pricingPolicySr"></label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('pricingPolicySr')" @mouseout="hideTooltip('pricingPolicySr')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'pricingPolicySr'" v-html="$t('riportalApp.help-service.pricingPolicySr_desc')"></div>
                        <input type="text" readonly ref="pricingPolicySr" class="form-control" name="pricingPolicySr" id="ri-service-pricingPolicySr"
                            :class="{'valid': !$v.riService.pricingPolicySr.$invalid, 'invalid': $v.riService.pricingPolicySr.$invalid }" v-model="$v.riService.pricingPolicySr.$model" /><br>
                               <div v-if="riService.id != null && riService.pricingPolicySr != null && this.linked6 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.pricingPolicySr)">{{riService.pricingPolicySr}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="pricingPolicySr" rid="$t('riportalApp.riService.id')"></upload-files>
                    </b-col>
                    <b-col sm="3">
                        <label class="form-control-label position-relative" v-text="$t('riportalApp.riService.pricingPolicyEn')" for="ri-service-pricingPolicyEn"></label>
                        <font-awesome-icon :icon="['fas', 'question-circle']" @mouseover="showTooltip('pricingPolicyEn')" @mouseout="hideTooltip('pricingPolicyEn')"  class="question-icon-class"></font-awesome-icon>
                        <div class="tooltip-hover bg-info text-white p-2 border-0 text-1 w-80 rounded" v-if="currentTooltip === 'pricingPolicyEn'" v-html="$t('riportalApp.help-service.pricingPolicyEn_desc')"></div>
                        <input type="text" readonly ref="pricingPolicyEn" class="form-control" name="pricingPolicyEn" id="ri-service-pricingPolicyEn"
                            :class="{'valid': !$v.riService.pricingPolicyEn.$invalid, 'invalid': $v.riService.pricingPolicyEn.$invalid }" v-model="$v.riService.pricingPolicyEn.$model" /><br>
                               <div v-if="riService.id != null && riService.pricingPolicyEn != null && this.linked7 !=null"><b-button variant="link" class="link-style" @click="formLink(riService.pricingPolicyEn)">{{riService.pricingPolicyEn}}</b-button></div>
                        <upload-files ref="upload_0_M" v-if="riService.id != null" shortname='ri' :pageObj='riService'  :formn="this" text_field="pricingPolicyEn" rid="$t('riportalApp.riService.id')"></upload-files>                    </b-col>
				</b-form-row>                
                <!--
                <div>
                    <div class="form-group" v-if="riService.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riService.id" readonly />
                    </div>
                  
                  
                  
                </div>
                -->
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <!-- :disabled="$v.riService.$invalid || isSaving" -->
                    <button type="submit" ref="form_submit" id="save-entity"  class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-service-update.component.ts">
</script>

<style>
.question-icon-class{
    color: #17a2b8;
}

.tooltip-hover {
  position: absolute;
  top: 40%; /* Adjust the top position based on your preference */
  left: 30%; /* Set left to 100% to position it to the right of the label */
  display: none;
  z-index: 100000; /* Adjust z-index as needed */
}
.question-icon-class:hover + .tooltip-hover  {
  display: block;
}
</style>