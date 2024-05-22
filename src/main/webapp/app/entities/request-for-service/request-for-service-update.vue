<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.requestForService.home.createOrEditLabel" v-text="$t('riportalApp.requestForService.home.createOrEditLabel')">Create or edit a RequestForService</h2>
                <div>
                    <!--
                    <div class="form-group" v-if="requestForService.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="requestForService.id" readonly />
                    </div>
                    -->
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.requestForService.date')" for="request-for-service-date">Date</label>
                        <div class="d-flex">
                            <input id="request-for-service-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.requestForService.date.$invalid, 'invalid': $v.requestForService.date.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.requestForService.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                    </div>
                    -->

                    <b-form-row>
                        <b-col sm="6">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.date')" for="request-for-service-date">Date</label>
                                 
                                <div class="d-flex">
                                    <input id="request-for-service-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.requestForService.date.$invalid, 'invalid': $v.requestForService.date.$invalid }"
                                    :value="convertDateTimeFromServer($v.requestForService.date.$model)"
                                    @change="updateZonedDateTimeField('date', $event)"/>
                                </div>
                                <!--
                                <b-form-datepicker
                                    id="datepicker-dateformat2"
                                    :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
                                    v-model="$v.requestForService.date.$model"
                                    locale="sr"                            
                                    @input="updateZonedDateTimeField('date', $event)"
                                ></b-form-datepicker> 
                                -->
                            </div>                            
                        </b-col>
                        <b-col sm="6">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.services')" for="request-for-service-services">Services</label>
                                <select class="form-control" id="request-for-service-services" name="services" v-model="requestForService.services">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="requestForService.services && riServiceOption.id === requestForService.services.id ? requestForService.services : riServiceOption" v-for="riServiceOption in riServices" :key="riServiceOption.id">
                                        <span v-if="currentLanguage == 'sr'">{{riServiceOption.nameSr}}</span>
                                        <span v-if="currentLanguage == 'en'">{{riServiceOption.nameEn}}</span>
                                        <span v-if="currentLanguage == 'src'">{{riServiceOption.nameSrCyr}}</span>
                                    </option>
                                </select>
                            </div>    
                            <!--
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.title')" for="title">Role</label>
                                <input type="text" class="form-control" name="title" id="title"
                                    :class="{'valid': !$v.requestForService.title.$invalid, 'invalid': $v.requestForService.title.$invalid }" v-model="$v.requestForService.title.$model" />
                            </div>                            
                            -->
                        </b-col>
                    </b-form-row>    
                    <b-form-row>                       
                        <b-col sm="12">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.description')" for="request-for-service-description">Description</label>
                                <b-form-textarea id="request-for-service-description" v-model="$v.requestForService.description.$model" placeholder="Enter something..." rows="4" 
                                        max-rows="6"
                                        :class="{'valid': !$v.requestForService.description.$invalid, 'invalid': $v.requestForService.description.$invalid }"
                                ></b-form-textarea>
                            </div>
                        </b-col>
                    </b-form-row>

                    <b-form-row>                        
                        <b-col sm="6">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.startTime')" for="request-for-service-startTime">Start Time</label>
                                <div class="d-flex"> 
                                    <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestForService.startTime.$model" type="datetime"
                                        required                                     
                                        >
                                    </date-picker>                                                               
                                </div> 
                            </div>                            
                        </b-col>
                        <b-col sm="6">                            
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.endTime')" for="request-for-service-endTime">End Time</label>
                                <div class="d-flex"> 
                                    <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestForService.endTime.$model" type="datetime"
                                        required                                     
                                        >
                                    </date-picker>                                                               
                                </div> 
                            </div>
                        </b-col>                        
                    </b-form-row>
                    <b-form-row>
                        <b-col sm="6">
                            <div class="form-group">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.deadline')" for="datepicker-dateformat2">Deadline</label>
                                <div class="d-flex"> 
                                    <date-picker style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.requestForService.deadline.$model" type="date"
                                        required                               
                                        >
                                    </date-picker>                                                               
                                </div>                                
                            </div>
                        </b-col>
                    </b-form-row>
                    <b-form-row>
                        <b-col sm="6">
                            <div class="form-group" v-if="authenticated && (hasAnyRole(['PA', 'RPRIPO']) || hasAnyAuthority('ROLE_ADMIN'))">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.user')" for="request-for-service-user">User</label>
                                
                                <select class="form-control" id="research-infrastructure-manager" name="manager" v-model="requestForService.users" required>
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="requestForService.users && portalUserOption.id === requestForService.users.id ? requestForService.users : portalUserOption" v-for="portalUserOption in portalUsers" :key="portalUserOption.id">{{portalUserOption.user.lastName}} {{portalUserOption.user.firstName}}</option>
                                </select>
                            </div>
                        </b-col>
                        <b-col sm="6">
                            <div class="form-group" v-if="authenticated && (hasAnyRole(['PA']) || hasAnyAuthority('ROLE_ADMIN'))">
                                <label class="form-control-label" v-text="$t('riportalApp.requestForService.organizations')" for="request-for-service-organization">Owner</label>                                
                                <select class="form-control" id="request-for-service-organization" name="request-for-service-organization" v-model="requestForService.organizations">
                                    <option v-bind:value="null"></option>
                                    <option v-bind:value="requestForService.organizations && organizationOption.id === requestForService.organizations.id ? requestForService.organizations : organizationOption" v-for="organizationOption in portalUserOrganizations" :key="organizationOption.id">
                                        <span v-if="currentLanguage === 'sr'">{{organizationOption.legalNameSr}}</span>
                                        <span v-if="currentLanguage === 'en'">{{organizationOption.legalNameEn}}</span>
                                        <span v-if="currentLanguage === 'src'">{{organizationOption.legalNameSrCyr}}</span>
                                    </option>
                                </select>
                            </div>
                        </b-col>
                    </b-form-row>
                                      
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.requestForService.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <router-link  
                        :to="{name: 'RequestForServiceZbirno', params: {requestForServiceId: requestForService.id}}"  tag="button" class="btn btn-info btn-md edit">
                        <span class="d-none d-md-inline" v-text="$t('entity.action.zbirniPrikaz')">Edit</span>
                    </router-link>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./request-for-service-update.component.ts">
</script>
