<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.riResearchOrganization.home.createOrEditLabel" v-text="$t('riportalApp.riResearchOrganization.home.createOrEditLabel')">Create or edit a RiResearchOrganization</h2>
                <div>
                    <div class="form-group" v-if="riResearchOrganization.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="riResearchOrganization.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.name')" for="ri-research-organization-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ri-research-organization-name"
                            :class="{'valid': !$v.riResearchOrganization.name.$invalid, 'invalid': $v.riResearchOrganization.name.$invalid }" v-model="$v.riResearchOrganization.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.descriptionSr')" for="ri-research-organization-descriptionSr">Description Sr</label>
                        <input type="text" class="form-control" name="descriptionSr" id="ri-research-organization-descriptionSr"
                            :class="{'valid': !$v.riResearchOrganization.descriptionSr.$invalid, 'invalid': $v.riResearchOrganization.descriptionSr.$invalid }" v-model="$v.riResearchOrganization.descriptionSr.$model" />
                        <div v-if="$v.riResearchOrganization.descriptionSr.$anyDirty && $v.riResearchOrganization.descriptionSr.$invalid">
                            <small class="form-text text-danger" v-if="!$v.riResearchOrganization.descriptionSr.maxLength" v-text="$t('entity.validation.maxlength', {max: 1000})">
                                This field cannot be longer than 1000 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.decsriptionEn')" for="ri-research-organization-decsriptionEn">Decsription En</label>
                        <input type="text" class="form-control" name="decsriptionEn" id="ri-research-organization-decsriptionEn"
                            :class="{'valid': !$v.riResearchOrganization.decsriptionEn.$invalid, 'invalid': $v.riResearchOrganization.decsriptionEn.$invalid }" v-model="$v.riResearchOrganization.decsriptionEn.$model" />
                        <div v-if="$v.riResearchOrganization.decsriptionEn.$anyDirty && $v.riResearchOrganization.decsriptionEn.$invalid">
                            <small class="form-text text-danger" v-if="!$v.riResearchOrganization.decsriptionEn.maxLength" v-text="$t('entity.validation.maxlength', {max: 1000})">
                                This field cannot be longer than 1000 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.logo')" for="ri-research-organization-logo">Logo</label>
                        <input type="text" class="form-control" name="logo" id="ri-research-organization-logo"
                            :class="{'valid': !$v.riResearchOrganization.logo.$invalid, 'invalid': $v.riResearchOrganization.logo.$invalid }" v-model="$v.riResearchOrganization.logo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.descriptionSrCyr')" for="ri-research-organization-descriptionSrCyr">Description Sr Cyr</label>
                        <input type="text" class="form-control" name="descriptionSrCyr" id="ri-research-organization-descriptionSrCyr"
                            :class="{'valid': !$v.riResearchOrganization.descriptionSrCyr.$invalid, 'invalid': $v.riResearchOrganization.descriptionSrCyr.$invalid }" v-model="$v.riResearchOrganization.descriptionSrCyr.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.puOrganization')" for="ri-research-organization-puOrganization">Pu Organization</label>

                        <input type="text" :formn="this" ref="portalUserOrganisationField" class="form-control" name="portalUserOrganisation" id="portal-user-organisation" @keyup="getDataAutoForPUO()" @click="toggleSearchList($event, 'patternRI')" @focusout="toggleSearchList($event, 'patternRI')"
                                    autocomplete="off"
                                   />
                                    <div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="ris" v-if="search_data.length && this.patternRI" >
                                        <ul class="list-group" @click.stop>
                                                <a class="list-group-item" v-for="portalUserOrganizationOption in search_data" @mousedown="updatePUOInputField(portalUserOrganizationOption), updateFormFieldsBasedOnPUO(portalUserOrganizationOption)" >
                                                    <span v-if="currentLanguage=='sr'">{{ portalUserOrganizationOption.legalNameSr }}</span>
                                                    <span v-if="currentLanguage=='src'">{{ portalUserOrganizationOption.legalNameSrCyr }}</span>
                                                    <span v-if="currentLanguage=='en'">{{ portalUserOrganizationOption.legalNameEn }}</span>
                                                </a>
                                        </ul>
                                    </div>

                        <!-- <select class="form-control" id="ri-research-organization-puOrganization" 
                            name="puOrganization" v-model="riResearchOrganization.puOrganization"
                            @change="changePuoOrganization($event)"
                            :class="{'valid': !$v.riResearchOrganization.puOrganization.$invalid, 'invalid': $v.riResearchOrganization.puOrganization.$invalid }"
                             required >
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riResearchOrganization.puOrganization && portalUserOrganizationOption.id === riResearchOrganization.puOrganization.id ? riResearchOrganization.puOrganization : portalUserOrganizationOption" v-for="portalUserOrganizationOption in portalUserOrganizations" :key="portalUserOrganizationOption.id">
                                <span v-if="currentLanguage == 'sr'">{{portalUserOrganizationOption.legalNameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{portalUserOrganizationOption.legalNameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{portalUserOrganizationOption.legalNameSrCyr}}</span>
                            </option>
                        </select> -->
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.researchInstitution')" for="ri-research-organization-researchInstitution">Research Institution</label>
                        <select class="form-control" id="ri-research-organization-researchInstitution" 
                            name="researchInstitution" v-model="riResearchOrganization.researchInstitution"
                            :class="{'valid': !$v.riResearchOrganization.researchInstitution.$invalid, 'invalid': $v.riResearchOrganization.researchInstitution.$invalid }"
                            required >
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riResearchOrganization.researchInstitution && researchInstitutionOption.id === riResearchOrganization.researchInstitution.id ? riResearchOrganization.researchInstitution : researchInstitutionOption" v-for="researchInstitutionOption in researchInstitutions" :key="researchInstitutionOption.id">
                                <span v-if="currentLanguage==='sr'">{{researchInstitutionOption.name}}</span>
                                <span v-if="currentLanguage==='src'">{{researchInstitutionOption.nameCyr}}</span>
                                <span v-if="currentLanguage==='en'">{{researchInstitutionOption.nameEn}}</span>
                            </option>
                        </select>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.activityStatus')" for="ri-research-organization-activityStatus">Activity Status</label>
                        <select class="form-control" id="ri-research-organization-activityStatus" name="activityStatus" v-model="riResearchOrganization.activityStatus">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riResearchOrganization.activityStatus && ripoActivityStatusOption.id === riResearchOrganization.activityStatus.id ? riResearchOrganization.activityStatus : ripoActivityStatusOption" v-for="ripoActivityStatusOption in ripoActivityStatuses" :key="ripoActivityStatusOption.id">
                                <span v-if="currentLanguage == 'sr'">{{ripoActivityStatusOption.status}}</span>
                                <span v-if="currentLanguage == 'en'">{{ripoActivityStatusOption.statusEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{ripoActivityStatusOption.statusCyr}}</span>
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('riportalApp.riResearchOrganization.subdomains')" for="ri-research-organization-subdomains">Subdomains</label>
                        <select class="form-control" id="ri-research-organization-subdomains" multiple name="subdomains" v-model="riResearchOrganization.subdomains">
                            <option v-bind:value="getSelected(riResearchOrganization.subdomains, scientificSubdomainOption)" v-for="scientificSubdomainOption in scientificSubdomains" :key="scientificSubdomainOption.id">
                                <span v-if="currentLanguage == 'sr'">{{scientificSubdomainOption.subdomain}}</span>
                                <span v-if="currentLanguage == 'en'">{{scientificSubdomainOption.subdomainEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{scientificSubdomainOption.subdomainCyr}}</span>
                                </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.manager')" for="ri-research-organization-manager">Manager</label>
                        <select class="form-control" id="ri-research-organization-manager" name="manager" v-model="riResearchOrganization.manager">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riResearchOrganization.manager && portalUserOption.id === riResearchOrganization.manager.id ? riResearchOrganization.manager : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">
                                {{portalUserOption.user.firstName}} {{portalUserOption.user.lastName}}
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.riResearchOrganization.contactPerson')" for="ri-research-organization-contactPerson">Contact Person</label>
                        <select class="form-control" id="ri-research-organization-contactPerson" name="contactPerson" v-model="riResearchOrganization.contactPerson">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="riResearchOrganization.contactPerson && portalUserOption.id === riResearchOrganization.contactPerson.id ? riResearchOrganization.contactPerson : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">
                                {{portalUserOption.user.firstName}} {{portalUserOption.user.lastName}}
                            </option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.riResearchOrganization.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ri-research-organization-update.component.ts">
</script>
