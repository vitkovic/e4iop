<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.portalUserOrganization.home.createOrEditLabel" v-text="$t('riportalApp.portalUserOrganization.home.createOrEditLabel')">Create or edit a PortalUserOrganization</h2>
                <b-form-row>
                    <b-col class="sm-3">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.legalNameSr')" for="portal-user-organization-legalNameSr">Legal Name Sr</label>
                        <input type="text" class="form-control" name="legalNameSr" id="portal-user-organization-legalNameSr"
                           :class="{'valid': !$v.portalUserOrganization.legalNameSr.$invalid, 'invalid': $v.portalUserOrganization.legalNameSr.$invalid }" v-model="$v.portalUserOrganization.legalNameSr.$model" />                        
                    </b-col>
                    <b-col class="sm-3">
                        <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.legalNameEn')" for="portal-user-organization-legalNameEn">Legal Name En</label>                        
                        <input type="text" class="form-control" name="legalNameEn" id="portal-user-organization-legalNameEn"
                            :class="{'valid': !$v.portalUserOrganization.legalNameEn.$invalid, 'invalid': $v.portalUserOrganization.legalNameEn.$invalid }" v-model="$v.portalUserOrganization.legalNameEn.$model" />
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.shortName')" for="portal-user-organization-shortName">Short Name</label>
                            <input type="text" class="form-control" name="shortName" id="portal-user-organization-shortName"
                                :class="{'valid': !$v.portalUserOrganization.shortName.$invalid, 'invalid': $v.portalUserOrganization.shortName.$invalid }" v-model="$v.portalUserOrganization.shortName.$model" />
                        </div>
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.webAddress')" for="portal-user-organization-webAddress">Web Address</label>
                            <input type="text" class="form-control" name="webAddress" id="portal-user-organization-webAddress"
                                :class="{'valid': !$v.portalUserOrganization.webAddress.$invalid, 'invalid': $v.portalUserOrganization.webAddress.$invalid }" v-model="$v.portalUserOrganization.webAddress.$model" />
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.pib')" for="portal-user-organization-pib">Pib</label>
                            <input type="text" class="form-control" name="pib" id="portal-user-organization-pib" required @change='getDataPibAPI()'
                                :class="{'valid': !$v.portalUserOrganization.pib.$invalid, 'invalid': $v.portalUserOrganization.pib.$invalid }" v-model="$v.portalUserOrganization.pib.$model" />
                            <div v-if="$v.portalUserOrganization.pib.$anyDirty && $v.portalUserOrganization.pib.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.pib.required"
                                    v-text="$t('riportalApp.portalUserOrganization.pibRequired')">
                                    Your fistname is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.pib.minLength" v-text="$t('entity.validation.minlength', {min: 9})">
                                    This field is required to be at least 9 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.pib.maxLength" v-text="$t('entity.validation.maxlength', {max: 9})">
                                    This field cannot be longer than 8 characters.
                                </small>
                            </div>
                        </div>
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.registrationNumber')" for="portal-user-organization-registrationNumber">Registration Number</label>
                            <input type="text" class="form-control" name="registrationNumber" id="portal-user-organization-registrationNumber" required
                                :class="{'valid': !$v.portalUserOrganization.registrationNumber.$invalid, 'invalid': $v.portalUserOrganization.registrationNumber.$invalid }" @change='getDataMatAPI()' v-model="$v.portalUserOrganization.registrationNumber.$model" />
                            <div v-if="$v.portalUserOrganization.registrationNumber.$anyDirty && $v.portalUserOrganization.registrationNumber.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.registrationNumber.required"
                                    v-text="$t('riportalApp.portalUserOrganization.registrationNumberRequired')">
                                    Your fistname is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.registrationNumber.minLength" v-text="$t('entity.validation.minlength', {min: 8})">
                                    This field is required to be at least 8 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUserOrganization.registrationNumber.maxLength" v-text="$t('entity.validation.maxlength', {max: 8})">
                                    This field cannot be longer than 8 characters.
                                </small>
                            </div>
                        </div>
                    </b-col>

                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.keywords')" for="portal-user-organization-keywords">Keywords</label>
                            <input type="text" class="form-control" name="keywords" id="portal-user-organization-keywords"
                                :class="{'valid': !$v.portalUserOrganization.keywords.$invalid, 'invalid': $v.portalUserOrganization.keywords.$invalid }" v-model="$v.portalUserOrganization.keywords.$model" />
                        </div>
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.address')" for="portal-user-organization-address">Address</label>
                            <input type="text" class="form-control" name="address" id="portal-user-organization-address"
                                :class="{'valid': !$v.portalUserOrganization.address.$invalid, 'invalid': $v.portalUserOrganization.address.$invalid }" v-model="$v.portalUserOrganization.address.$model" />
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.postalCode')" for="portal-user-organization-postalCode">Postal Code</label>
                            <input type="text" class="form-control" name="postalCode" id="portal-user-organization-postalCode"
                                :class="{'valid': !$v.portalUserOrganization.postalCode.$invalid, 'invalid': $v.portalUserOrganization.postalCode.$invalid }" v-model="$v.portalUserOrganization.postalCode.$model" />
                        </div>
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.city')" for="portal-user-organization-city">City</label>
                            <input type="text" class="form-control" name="city" id="portal-user-organization-city"
                                :class="{'valid': !$v.portalUserOrganization.city.$invalid, 'invalid': $v.portalUserOrganization.city.$invalid }" v-model="$v.portalUserOrganization.city.$model" />
                        </div>
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.country')" for="portal-user-organization-country">Country</label>
                            <input type="text" class="form-control" name="country" id="portal-user-organization-country"
                                :class="{'valid': !$v.portalUserOrganization.country.$invalid, 'invalid': $v.portalUserOrganization.country.$invalid }" v-model="$v.portalUserOrganization.country.$model" />
                        </div> 
                    </b-col>
                    <b-col class="sm-3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUserOrganization.legalStatus')" for="portal-user-organization-legalStatus">Legal Status</label>
                            <select class="form-control" id="portal-user-organization-legalStatus" name="legalStatus" v-model="portalUserOrganization.legalStatus">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUserOrganization.legalStatus && puoLegalStatusOption.id === portalUserOrganization.legalStatus.id ? portalUserOrganization.legalStatus : puoLegalStatusOption" v-for="puoLegalStatusOption in puoLegalStatuses" :key="puoLegalStatusOption.id">{{puoLegalStatusOption.value}}</option>
                            </select>
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row>
                    <b-col sm="3">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.researchInstitution')" for="ri-research-organization-researchInstitution">Research Institution</label>

                            <input type="text" ref="researchInstitutionField" class="form-control" name="researchInstitution" id="ri-research-organization-researchInstitution" @keyup="getDataAutoForNIO()" @click="toggleSearchList($event, 'patternRI')" @focusout="toggleSearchList($event, 'patternRI')" autocomplete="off"
                                   />
                                   <div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="ris" v-if="search_data.length && this.patternRI" >
        						<ul class="list-group" @click.stop>
                                    <a class="list-group-item" v-for="researchInstitutionOption in search_data" :key="researchInstitutionOption.id" @mousedown="updateNIOInputField(researchInstitutionOption), updateFormFieldsBasedOnNIO(researchInstitutionOption)">                                    
                                        <span v-if="currentLanguage==='sr'">{{researchInstitutionOption.name}}</span>
                                        <span v-if="currentLanguage==='src'">{{researchInstitutionOption.nameCyr}}</span>
                                        <span v-if="currentLanguage==='en'">{{researchInstitutionOption.nameEn}}</span>
                                    </a>
        						</ul>
      						</div> 


                            <!-- <select @change="changeInstitution($event)" class="form-control" id="ri-research-organization-researchInstitution" 
                                name="researchInstitution" v-model="portalUserOrganization.nioInstitution">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUserOrganization.researchInstitution && researchInstitutionOption.id === portalUserOrganization.nioInstitution.id ? portalUserOrganization.nioInstitution : researchInstitutionOption" v-for="researchInstitutionOption in researchInstitutions" :key="researchInstitutionOption.id">
                                    <span v-if="currentLanguage==='sr'">{{researchInstitutionOption.name}}</span>
                                    <span v-if="currentLanguage==='src'">{{researchInstitutionOption.nameCyr}}</span>
                                    <span v-if="currentLanguage==='en'">{{researchInstitutionOption.nameEn}}</span>
                                </option>
                            </select> -->
                        </div>
                    </b-col>
                </b-form-row>
<!--
                <div>
                    <div class="form-group" v-if="portalUserOrganization.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="portalUserOrganization.id" readonly />
                    </div>
                </div>
-->                
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="button" @click="save()" id="save-entity" :disabled="$v.portalUserOrganization.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./portal-user-organization-update.component.ts">
</script>
