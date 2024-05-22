<template>
    <div>
        <div class="row justify-content-center" >
            <div class="col-md-10 toastify-container" id="topjumper">
				<b-container style="margin-bottom:10px;">
					<b-row>
						<b-col>
							<h1 v-text="$t('register.title')" id="register-title">Registration</h1>
						</b-col>
						<b-col>
								<button type="button" style="float:right" class="btn btn-info mt-4" v-text="$t('register.form.buttondown')" @click="goUP()">Go Up</button>
						</b-col>					
					</b-row>
				</b-container>
                <div class="alert alert-success" role="alert" v-if="success" v-html="$t('register.messages.success')">
                    <strong>Registration saved!</strong> Please check your email for confirmation.
                </div>

                <div class="alert alert-danger" role="alert" v-if="error" v-html="$t('register.messages.error.fail')">
                    <strong>Registration failed!</strong> Please try again later.
                </div>

                <div class="alert alert-danger" role="alert" v-if="errorUserExists" v-html="$t('register.messages.error.userexists')">
                    <strong>Login name already registered!</strong> Please choose another one.
                </div>

                <div class="alert alert-danger" role="alert" v-if="errorEmailExists" v-html="$t('register.messages.error.emailexists')">
                    <strong>Email is already in use!</strong> Please choose another one.
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-md-10">
                <form id="register-form" name="registerForm" role="form" v-on:submit.prevent="register()" v-if="!success" no-validate>
					<b-container class="border border-primary rounded shadow">
                        <h3 v-text="$t('global.form[\'podaci.o.novom.korisniku\']')">Podaci o korisniku</h3>
                        
                          <b-form-row style="border:1px solid rgb(234, 234, 234);background-color:azure;padding-top:10px;">
                            <b-col sm="6" ><span v-text="$t('global.menu.entities.portalUserStatus')">Status korisnika portala</span>                
		                         <div class="form-group" style="padding:5px;">
		                                                      
		                                    <div v-for="status in portalUserStatuses" :key="status.id">
		                                        <b-form-radio v-model="$v.registerAccount.portalUserStatus.$model"  
		                                            name="some-radios" v-bind:value="status" 
		                                             @change="portalUserStatusCheck($event)">
                                                     <span v-if="currentLanguage=='sr'">{{ status.valueSr }}</span>
                                                     <span v-if="currentLanguage=='en'">{{ status.valueEn }}</span>
                                                     <span v-if="currentLanguage=='src'">{{ status.valueSrCyr }}</span>
		                                        </b-form-radio>
		                                  </div>                                    
		                                </div>
		                        
                        
                         </b-col>
                            <b-col sm="6"> &nbsp;
                       	    </b-col>
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="firstName" v-text="$t('global.form[\'firstName.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.firstName.$model" id="firstName" name="firstName"
                                        :class="{'valid': !$v.registerAccount.firstName.$invalid, 'invalid': $v.registerAccount.firstName.$invalid }"
                                        required >
                                    <div v-if="$v.registerAccount.firstName.$anyDirty && $v.registerAccount.firstName.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.firstName.required"
                                            v-text="$t('register.messages.validate.firstName.required')">
                                            Your fistname is required.
                                        </small>
                                    </div>
                                </div>
                            </b-col>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="lastName" v-text="$t('global.form[\'lastName.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.lastName.$model" id="lastName" name="lastName"
                                        :class="{'valid': !$v.registerAccount.lastName.$invalid, 'invalid': $v.registerAccount.lastName.$invalid }"
                                        required >
                                    <div v-if="$v.registerAccount.lastName.$anyDirty && $v.registerAccount.lastName.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.lastName.required"
                                            v-text="$t('register.messages.validate.lastName.required')">
                                            Your lastname is required.
                                        </small>
                                    </div>
                                </div>
                            </b-col>                           
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="username" v-text="$t('global.form[\'username.label\']')">Username</label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.login.$model" id="username" name="login"
                                        :class="{'valid': !$v.registerAccount.login.$invalid, 'invalid': $v.registerAccount.login.$invalid }"
                                        required minlength="1" maxlength="50" pattern="^[a-zA-Z0-9!#$&'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$" v-bind:placeholder="$t('global.form[\'username.placeholder\']')">
                                    <div v-if="$v.registerAccount.login.$anyDirty && $v.registerAccount.login.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.login.required"
                                            v-text="$t('register.messages.validate.login.required')">
                                            Your username is required.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.login.minLength"
                                            v-text="$t('register.messages.validate.login.minlength')">
                                            Your username is required to be at least 1 character.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.login.maxLength"
                                            v-text="$t('register.messages.validate.login.maxlength')">
                                            Your username cannot be longer than 50 characters.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.login.pattern"
                                            v-text="$t('register.messages.validate.login.pattern')">
                                            Your username can only contain letters and digits.
                                        </small>
                                    </div>
                                </div>
                            </b-col>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="email" v-text="$t('global.form[\'email.label\']')">Email</label>
                                    <input type="email" class="form-control" id="email" name="email"
                                        :class="{'valid': !$v.registerAccount.email.$invalid, 'invalid': $v.registerAccount.email.$invalid }"
                                        v-model="$v.registerAccount.email.$model" minlength=5 maxlength=254 email required  v-bind:placeholder="$t('global.form[\'email.placeholder\']')">
                                    <div v-if="$v.registerAccount.email.$anyDirty && $v.registerAccount.email.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.email.required"
                                            v-text="$t('global.messages.validate.email.required')">
                                            Your email is required.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.email.email"
                                            v-text="$t('global.messages.validate.email.invalid')">
                                            Your email is invalid.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.email.minLength"
                                            v-text="$t('global.messages.validate.email.minlength')">
                                            Your email is required to be at least 5 characters.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.email.maxLength"
                                            v-text="$t('global.messages.validate.email.maxlength')">
                                            Your email cannot be longer than 100 characters.
                                        </small>
                                    </div>
                                </div>
                            </b-col>                            
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="firstPassword" v-text="$t('global.form[\'newpassword.label\']')">New password</label>
                                    <input type="password" class="form-control" id="firstPassword" name="password"
                                        :class="{'valid': !$v.registerAccount.password.$invalid, 'invalid': $v.registerAccount.password.$invalid }"
                                        v-model="$v.registerAccount.password.$model" minlength=4 maxlength=50 required v-bind:placeholder="$t('global.form[\'newpassword.placeholder\']')">
                                    <div v-if="$v.registerAccount.password.$anyDirty && $v.registerAccount.password.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.password.required"
                                            v-text="$t('global.messages.validate.newpassword.required')">
                                            Your password is required.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.password.minLength"
                                            v-text="$t('global.messages.validate.newpassword.minlength')">
                                            Your password is required to be at least 4 characters.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.password.maxLength"
                                            v-text="$t('global.messages.validate.newpassword.maxlength')">
                                            Your password cannot be longer than 50 characters.
                                        </small>
                                    </div>
                                    <!--<jhi-password-strength-bar [passwordToCheck]="registerAccount.password"></jhi-password-strength-bar>-->
                                </div>
                            </b-col>
                            <b-col sm="6">
                                <div class="form-group">
                                    <label class="form-control-label" for="phone" v-text="$t('global.form[\'phone.label\']')">Phone</label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.phone.$model" id="phone" name="phone">
                                </div>
                            </b-col>
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="6">
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
                            </b-col>
                            <b-col sm="2" offset-sm="1">
                               &nbsp;
                            </b-col>
                        </b-form-row>                    
                    </b-container>
                    
                    <div id="jumper">
                    <b-container style="padding-top:10px;" v-if="this.prikazOrganizacije === true || this.prikazInstitucije === true" class="border border-primary rounded shadow mt-4 mb-4" >
                     <label class="form-control-label" v-text="$t('register.naslovOrganizacija')" for="portal-user-userOrganization">User Organization</label><hr/>

                        <b-form-row class="text-center" v-if="this.prikazOrganizacije === true">
                            <b-col sm="4" >
								<div class="form-group"><label class="form-control-label" v-text="$t('register.vasaOrganizacija')" for="portal-user-userOrganization">User Organization</label>


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
                                </div>
                            </b-col>
                            <b-col sm="4"><notifications position="center center" group="loader"/>
								 <div class="form-group"><label class="form-control-label" v-text="$t('register.unosBroj')" for="organizationMaticniBrojF">User Organization</label>
                                     <input type="text" :formn="this" ref="namePUO" class="form-control" id="organizationMaticniBrojF" name="organizationMaticniBrojF" @change="getData()" autocomplete="off" />
                                </div>
                            </b-col>
                             <b-col sm="4" align-self="center">
                                <button type="button" class="btn btn-primary" @click="prikaziOrganizaciju(true)" v-text="$t('register.form.prikaziNovuOrganizaciju')"></button>
                            </b-col><!--
                             <b-col sm="2" align-self="center">
                                <button type="button" v-if="$v.registerAccount.organizationNameSr.$model != null" class="btn btn-primary" @click="resetOrganization(true)" v-text="$t('register.form.OcistiOrganizaciju')"></button>
                            </b-col>-->
                       </b-form-row>
                        <b-form-row class="text-center" align-v="top" v-if="this.prikazInstitucije === true">
                             <b-col sm="4" ><!--
                                <div class="form-group"><label class="form-control-label" v-text="$t('register.chooseInstitution')" for="portal-user-userOrganization">User Organization</label>
                                    <select class="form-control" id="research-Institution" name="researchInstitution" v-model="researchInstitution" @change='findReseacrhersForResearchInstitution()'>
                                        <option v-bind:value="researchInstitutionOption" v-for="researchInstitutionOption in researchInstitutions"  :key="researchInstitutionOption.id" :selected="researchInstitutionOption.id === 1">{{researchInstitutionOption.name}}, {{researchInstitutionOption.address}}</option>
                                    </select>
                                </div>-->
                              <div><label class="form-control-label" v-text="$t('register.chooseInstitution')" for="portal-user-userOrganization">User Organization</label>  
                              <input type="text" :formn="this" ref="researchInstitutionField" class="form-control" name="researchInstitution" id="research-Institution" @keyup="getDataAuto()" @click="showDiv()" autocomplete="off"
                                   />
                        	<div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="ris" v-if="search_data.length && this.patternRI" >
        						<ul class="list-group" @click.stop>
	          							<a class="list-group-item" v-for="data1 in search_data" @click="getRISr(data1,'researchInstitution')" >{{ data1.name }}</a>
        						</ul>
      						</div>   
                            </div>    
                            </b-col>
                             <b-col sm="4">
                                <div class="form-group"><label class="form-control-label" v-text="$t('register.chooseResearcher')" for="researchers">User Organization</label>

                                    <input type="text" :formn="this" ref="researchersField" class="form-control" name="researchers" id="researchers" @keyup="getDataAutoForResearchers()" @click="toggleSearchList($event, 'patternResearchers')" @focusout="toggleSearchList($event, 'patternResearchers')"
                                    autocomplete="off"
                                   />
                                    <div class="form-control" style="text-align:left;height:300px;overflow-y:scroll;" ref="researchersList" v-if="search_data_researchers.length && this.patternResearchers" >
                                        <ul class="list-group" @click.stop>
                                                <a class="list-group-item" v-for="researcherData in search_data_researchers" @mousedown="getResearchers(researcherData)" >{{researcherData.firstName}} {{researcherData.middleName}} {{researcherData.lastName}} - {{researcherData.birthDate}}
                                                </a>
                                        </ul>
                                    </div>
                                </div>
                            </b-col>
                       </b-form-row>
                    </b-container>
                    </div>
                    <b-container class="border border-primary rounded shadow mt-4 mb-4" v-if="(prikaziNovuOrganizaciju == true || prikazOrganizacije==true)">
                        <b-form-row class="mb-4 mt-4">
                            <b-col sm="12">
                               <h3><span v-text="$t('register.organizacija')"></span></h3>
                               <h4><span v-text="$t('register.OrganizationTitle')"></span></h4>
                            </b-col>
                           <!-- <b-col sm="4" offset-sm="4" align-self="center">
                                <button type="button" class="btn btn-primary" @click="prikaziOrganizaciju(false)"  v-text="$t('register.form.prikaziPostojeceOrganizacije')"></button>
                            </b-col>-->
                        </b-form-row>
                        
                        <b-form-row>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationNameSr" v-text="$t('global.form[\'organizationNameSr.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationNameSr.$model" id="organizationNameSr" name="organizationNameSr">
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationLegalStatus" v-text="$t('global.form[\'organizationLegalStatus.label\']')"></label>
                                    <select class="form-control" id="organizationLegalStatus" name="legalStatus" v-model="$v.registerAccount.organizationLegalStatus.$model">
                                        <option v-bind:value="null"></option>
                                        <option v-bind:value="puoLegalStatusOption" 
                                        v-for="puoLegalStatusOption in puoLegalStatuses" :key="puoLegalStatusOption.id">{{puoLegalStatusOption.value}}</option>
                                    </select>
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationAddress" v-text="$t('global.form[\'organizationAddress.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationAddress.$model" id="organizationAddress" name="organizationAddress">
                                </div>
                            </b-col>
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationNameEn" v-text="$t('global.form[\'organizationNameEn.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationNameEn.$model" id="organizationNameEn" name="organizationNameEn">
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationPib" v-text="$t('global.form[\'organizationPib.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationPib.$model" id="organizationPib" name="organizationPib"
                                    :class="{'valid': !$v.registerAccount.organizationPib.$invalid, 'invalid': $v.registerAccount.organizationPib.$invalid }" />
                                    <div v-if="$v.registerAccount.organizationPib.$anyDirty && $v.registerAccount.organizationPib.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.organizationPib.minLength" v-text="$t('entity.validation.minlength', {min: 9})">
                                            This field is required to be at least 9 characters.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.organizationPib.maxLength" v-text="$t('entity.validation.maxlength', {max: 9})">
                                            This field cannot be longer than 9 characters.
                                        </small>
                                    </div>
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationAddressPostalCode" v-text="$t('global.form[\'organizationAddressPostalCode.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationAddressPostalCode.$model" id="organizationAddressPostalCode" name="organizationAddressPostalCode">
                                </div>
                            </b-col>
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="4">
                                 <div class="form-group">
                                    <label class="form-control-label" for="organizationShortName" v-text="$t('global.form[\'organizationShortName.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationShortName.$model" id="organizationShortName" name="organizationShortName">
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationMaticniBroj" v-text="$t('global.form[\'organizationMaticniBroj.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationMaticniBroj.$model" id="organizationMaticniBroj" name="organizationMaticniBroj">
                                    <div v-if="$v.registerAccount.organizationMaticniBroj.$anyDirty && $v.registerAccount.organizationMaticniBroj.$invalid">
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.organizationMaticniBroj.minLength" v-text="$t('entity.validation.minlength', {min: 8})">
                                            This field is required to be at least 8 characters.
                                        </small>
                                        <small class="form-text text-danger" v-if="!$v.registerAccount.organizationMaticniBroj.maxLength" v-text="$t('entity.validation.maxlength', {max: 8})">
                                            This field cannot be longer than 8 characters.
                                        </small>
                                    </div>
                                
                                </div>
                            </b-col>
                            <b-col sm="4">
                               <div class="form-group">
                                    <label class="form-control-label" for="organizationAddressCity" v-text="$t('global.form[\'organizationAddressCity.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationAddressCity.$model" id="organizationAddressCity" name="organizationAddressCity">
                                </div>
                            </b-col>
                        </b-form-row>
                        <b-form-row>
                            <b-col sm="4">
                                 <div class="form-group">
                                    <label class="form-control-label" for="organizationWebAddress" v-text="$t('global.form[\'organizationWebAddress.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationWebAddress.$model" id="organizationWebAddress" name="organizationWebAddress">
                                </div>
                            </b-col>
                            <b-col sm="4">
                                <div class="form-group">
                                    <label class="form-control-label" for="organizationKeywords" v-text="$t('global.form[\'organizationKeywords.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationKeywords.$model" id="organizationKeywords" name="organizationKeywords">
                                </div>
                            </b-col>
                            <b-col sm="4">
                               <div class="form-group">
                                    <label class="form-control-label" for="organizationAddressCountry" v-text="$t('global.form[\'organizationAddressCountry.label\']')"></label>
                                    <input type="text" class="form-control" v-model="$v.registerAccount.organizationAddressCountry.$model" id="organizationAddressCountry" name="organizationAddressCountry">
                                </div>
                            </b-col>
                        </b-form-row>
                    </b-container>
                    
                    
                    
                    
					<button type="button" style="float:right" class="btn btn-info mt-4" v-text="$t('register.form.buttonup')" @click="goUP()">Go Up</button>
                    <button type="submit" :disabled="$v.$invalid " class="btn btn-primary mt-4" v-text="$t('register.form.button')">Register</button>
                </form>
                <p></p>
                
            </div>
        </div>
    </div>
</template>

<script lang='ts' src='./register.component.ts'>
</script>
