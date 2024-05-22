<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 style="display:inline" id="riportalApp.portalUser.home.createOrEditLabel" v-text="$t('riportalApp.portalUser.home.createOrEditLabel')"></h2>
                <h2 v-if="portalUser.id" style="display:inline" v-text="$t('riportalApp.portalUser.home.saId',{'param': portalUser.id})"></h2>
                <b-form-row>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" for="firstName" v-text="$t('global.form[\'firstName.label\']')"></label>
                            <input type="text" class="form-control" v-model="$v.portalUser.user.firstName.$model" id="firstName" name="firstName"
                                :class="{'valid': !$v.portalUser.user.firstName.$invalid, 'invalid': $v.portalUser.user.firstName.$invalid }"
                                required >
                            <div v-if="$v.portalUser.user.firstName.$anyDirty && $v.portalUser.user.firstName.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.firstName.required"
                                    v-text="$t('register.messages.validate.firstName.required')">
                                    Your fistname is required.
                                </small>
                            </div>
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" for="lastName" v-text="$t('global.form[\'lastName.label\']')"></label>
                            <input type="text" class="form-control" v-model="$v.portalUser.user.lastName.$model" id="lastName" name="lastName"
                                :class="{'valid': !$v.portalUser.user.lastName.$invalid, 'invalid': $v.portalUser.user.lastName.$invalid }"
                                required >
                            <div v-if="$v.portalUser.user.lastName.$anyDirty && $v.portalUser.user.lastName.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.lastName.required"
                                    v-text="$t('register.messages.validate.lastName.required')">
                                    Your lastname is required.
                                </small>
                            </div>
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group" v-if="!portalUser.id">
                            <label class="form-control-label" for="email" v-text="$t('global.form[\'email.label\']')">Email</label>
                            <input type="email" class="form-control" id="email" name="email"
                                :class="{'valid': !$v.portalUser.user.email.$invalid, 'invalid': $v.portalUser.user.email.$invalid }"
                                v-model="$v.portalUser.user.email.$model" minlength=5 maxlength=254 email required  v-bind:placeholder="$t('global.form[\'email.placeholder\']')">
                            <div v-if="$v.portalUser.user.email.$anyDirty && $v.portalUser.user.email.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.email.required"
                                    v-text="$t('global.messages.validate.email.required')">
                                    Your email is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.email.email"
                                    v-text="$t('global.messages.validate.email.invalid')">
                                    Your email is invalid.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.email.minLength"
                                    v-text="$t('global.messages.validate.email.minlength')">
                                    Your email is required to be at least 5 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.email.maxLength"
                                    v-text="$t('global.messages.validate.email.maxlength')">
                                    Your email cannot be longer than 100 characters.
                                </small>
                            </div>
                        </div>
                    </b-col>
                </b-form-row>
                <b-form-row v-if="!portalUser.id">
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" for="username" v-text="$t('global.form[\'username.label\']')">Username</label>
                            <input type="text" class="form-control" v-model="$v.portalUser.user.login.$model" id="username" name="login"
                                :class="{'valid': !$v.portalUser.user.login.$invalid, 'invalid': $v.portalUser.user.login.$invalid }"
                                required minlength="1" maxlength="50" pattern="^[a-zA-Z0-9!#$&'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$" v-bind:placeholder="$t('global.form[\'username.placeholder\']')">
                            <div v-if="$v.portalUser.user.login.$anyDirty && $v.portalUser.user.login.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.login.required"
                                    v-text="$t('register.messages.validate.login.required')">
                                    Your username is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.login.minLength"
                                    v-text="$t('register.messages.validate.login.minlength')">
                                    Your username is required to be at least 1 character.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.login.maxLength"
                                    v-text="$t('register.messages.validate.login.maxlength')">
                                    Your username cannot be longer than 50 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.login.pattern"
                                    v-text="$t('register.messages.validate.login.pattern')">
                                    Your username can only contain letters and digits.
                                </small>
                            </div>
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.phone')" for="portal-user-phone">Phone</label>
                            <input type="text" class="form-control" name="phone" id="portal-user-phone"
                                :class="{'valid': !$v.portalUser.phone.$invalid, 'invalid': $v.portalUser.phone.$invalid }" v-model="$v.portalUser.phone.$model" />
                        </div>
                        <!--
                        <div class="form-group">
                            <label class="form-control-label" for="firstPassword" v-text="$t('global.form[\'newpassword.label\']')">New password</label>
                            <input type="password" class="form-control" id="firstPassword" name="password"
                                :class="{'valid': !$v.portalUser.user.password.$invalid, 'invalid': $v.portalUser.user.password.$invalid }"
                                v-model="$v.portalUser.user.password.$model" minlength=4 maxlength=50 required v-bind:placeholder="$t('global.form[\'newpassword.placeholder\']')">
                            <div v-if="$v.portalUser.user.password.$anyDirty && $v.portalUser.user.password.$invalid">
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.password.required"
                                    v-text="$t('global.messages.validate.newpassword.required')">
                                    Your password is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.password.minLength"
                                    v-text="$t('global.messages.validate.newpassword.minlength')">
                                    Your password is required to be at least 4 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.portalUser.user.password.maxLength"
                                    v-text="$t('global.messages.validate.newpassword.maxlength')">
                                    Your password cannot be longer than 50 characters.
                                </small>
                            </div>
                        
                        </div>
                        -->
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group"> 
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.position')" for="portal-user-position">Position</label>
                            <input type="text" class="form-control" name="position" id="portal-user-position"
                                :class="{'valid': !$v.portalUser.position.$invalid, 'invalid': $v.portalUser.position.$invalid }" v-model="$v.portalUser.position.$model" />
                        </div>
                        <!--
                        <div class="form-group">
                            <label class="form-control-label" for="secondPassword" v-text="$t('global.form[\'confirmpassword.label\']')">New password confirmation</label>
                            <input type="password" class="form-control" id="secondPassword" name="confirmPasswordInput"
                                :class="{'valid': !$v.confirmPassword.$invalid, 'invalid': $v.confirmPassword.$invalid }"
                                v-model="$v.confirmPassword.$model" minlength=4 maxlength=50 required v-bind:placeholder="$t('global.form[\'confirmpassword.placeholder\']')">
                            <div v-if="$v.confirmPassword.$dirty && $v.confirmPassword.$invalid">
                                <small class="form-text text-danger" v-if="!$v.confirmPassword.required"
                                    v-text="$t('global.messages.validate.confirmpassword.required')">
                                    Your confirmation password is required.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.confirmPassword.minLength"
                                    v-text="$t('global.messages.validate.confirmpassword.minlength')">
                                    Your confirmation password is required to be at least 4 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.confirmPassword.maxLength"
                                    v-text="$t('global.messages.validate.confirmpassword.maxlength')">
                                    Your confirmation password cannot be longer than 50 characters.
                                </small>
                                <small class="form-text text-danger" v-if="!$v.confirmPassword.sameAsPassword"
                                    v-text="$t('global.messages.error.dontmatch')">
                                    The password and its confirmation do not match!
                                </small>
                            </div>
                        </div>
                        -->
                    </b-col>
                </b-form-row>                
                <b-form-row>
                    <b-col sm="4">
                        <div class="form-group">
                            <label v-text="$t('riportalApp.portalUser.portalUserRoles')" for="portal-user-portalUserRoles">Portal User Roles</label>
                            <select class="form-control" id="portal-user-portalUserRoles" multiple name="portalUserRoles" v-model="portalUser.portalUserRoles">
                                <option v-bind:value="getSelected(portalUser.portalUserRoles, portalUserRoleOption)" v-for="portalUserRoleOption in portalUserRoles" :key="portalUserRoleOption.id">
                                    {{portalUserRoleOption.role}}

                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.userOrganization')" for="portal-user-userOrganization">User Organization</label>

                            <input type="text" :formn="this" ref="portalUserOrganisationField" class="form-control" name="portalUserOrganisation" id="portal-user-organisation" @keyup="getDataAutoForPUO()" @click="toggleSearchList($event, 'patternRI')" @focusout="toggleSearchList($event, 'patternRI')"
                                    autocomplete="off"
                                   />
                                    <div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="ris" v-if="search_data.length && this.patternRI" >
                                        <ul class="list-group" @click.stop>
                                                <a class="list-group-item" v-for="data1 in search_data" @mousedown="getPUOs(data1)" >
                                                    <span v-if="currentLanguage=='sr'">{{ data1.legalNameSr }}</span>
                                                    <span v-if="currentLanguage=='src'">{{ data1.legalNameSrCyr }}</span>
                                                    <span v-if="currentLanguage=='en'">{{ data1.legalNameEn }}</span>
                                                </a>
                                        </ul>
                                    </div>

                            <!-- <select class="form-control" id="portal-user-userOrganization" name="userOrganization" v-model="portalUser.userOrganization">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUser.userOrganization && portalUserOrganizationOption.id === portalUser.userOrganization.id ? portalUser.userOrganization : portalUserOrganizationOption" v-for="portalUserOrganizationOption in portalUserOrganizations" :key="portalUserOrganizationOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{portalUserOrganizationOption.legalNameSr}}</span>
                                    <span v-if="currentLanguage == 'en'">{{portalUserOrganizationOption.legalNameEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{portalUserOrganizationOption.legalNameSrCyr}}</span>
                                </option>
                            </select> -->
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <!-- <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.researcher')" for="portal-user-researcher">Researcher</label>
                            <select class="form-control" id="portal-user-researcher" name="researcher" v-model="portalUser.researcher">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUser.researcher && researcherOption.id === portalUser.researcher.id ? portalUser.researcher : researcherOption" v-for="researcherOption in researchers" :key="researcherOption.id">{{researcherOption.id}}</option>
                            </select>
                        </div> -->

                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.researcher')" for="researchers">User Organization</label>
                            <input type="text" :formn="this" ref="researchersField" class="form-control" name="researchers" id="researchers" @keyup="getDataAutoForResearchers()" @click="toggleSearchList($event, 'patternResearchers')" @focusout="toggleSearchList($event, 'patternResearchers')"
                            autocomplete="off" :disabled="researchersFieldDisabled"
                            />
                            <div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="researchersList" v-if="search_data_researchers.length && this.patternResearchers" >
                                <ul class="list-group" @click.stop>
                                        <a class="list-group-item" v-for="researcherData in search_data_researchers" @mousedown="getResearchers(researcherData)" >{{researcherData.firstName}} {{researcherData.middleName}} {{researcherData.lastName}} - {{researcherData.birthDate}}
                                        </a>
                                </ul>
                            </div>
                        </div>

                        
                        <!--
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.researcher')" for="portal-user-researcher">Researcher</label>
                            <select class="form-control" id="portal-user-researcher" name="researcher" v-model="portalUser.researcher">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUser.researcher && researcherOption.id === portalUser.researcher.id ? portalUser.researcher : researcherOption" v-for="researcherOption in researchers" :key="researcherOption.id">{{researcherOption.id}}</option>
                            </select>
                        </div>
                        -->
                    </b-col> 
                </b-form-row>

                
                
                
                
                
                
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.portalUser.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./portal-user-update.component.ts">
</script>
