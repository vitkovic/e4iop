<template>
    <div class="row justify-content-center">
        <div class="col-12">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 style="display:inline" id="riportalApp.portalUser.activateUserRoles.title" v-text="$t('riportalApp.portalUser.activateUserRoles.title')"></h2>
                <h2 v-if="portalUser.id" style="display:inline" v-text="$t('riportalApp.portalUser.home.saId',{'param': portalUser.id})"></h2>
                
                <b-row class="mt-4">
                    <h4>Podaci o korisniku</h4>
                </b-row>
                <b-row>
                    <b-col sm="2" class="text-right">
                        Ime i prezime:
                    </b-col>
                    <b-col sm="2" class="text-left">
                        Dragan Misic
                    </b-col>
                    <b-col sm="2" class="text-left" offset="2" :title="checkboxText" 
                    v-if="portalUser.user">                        
                        <b-form-checkbox v-model="portalUser.user.activated" >
                            <span v-text="$t('userManagement.activated')">Activated</span>
                        </b-form-checkbox>
                    </b-col>
                    

                </b-row>
                <b-row>
                    <b-col sm="2" class="text-right">
                        email:
                    </b-col>
                    <b-col sm="4" class="text-left">
                        dragan.misic@masfak.ni.ac.rs
                    </b-col>
                </b-row>
                

                <b-form-row class="mt-4">                    
                    <b-col sm="4">
                        <div class="form-group">
                            <label v-text="$t('riportalApp.portalUser.portalUserRoles')" for="portal-user-portalUserRoles">Portal User Roles</label>
                            <select class="form-control" id="portal-user-portalUserRoles" multiple name="portalUserRoles" 
                                v-model="portalUser.portalUserRoles" size="5">
                                <option v-bind:value="getSelected(portalUser.portalUserRoles, portalUserRoleOption)" v-for="portalUserRoleOption in portalUserRoles" :key="portalUserRoleOption.id">
                                    {{portalUserRoleOption.role}}
                                </option>
                            </select>
                        </div>
                    </b-col>
                    <b-col sm="4">
                        <div class="form-group">
                            <label class="form-control-label" v-text="$t('riportalApp.portalUser.userOrganization')" for="portal-user-userOrganization">User Organization</label>
                            <select class="form-control" id="portal-user-userOrganization" name="userOrganization" v-model="portalUser.userOrganization">
                                <option v-bind:value="null"></option>
                                <option v-bind:value="portalUser.userOrganization && portalUserOrganizationOption.id === portalUser.userOrganization.id ? portalUser.userOrganization : portalUserOrganizationOption" v-for="portalUserOrganizationOption in portalUserOrganizations" :key="portalUserOrganizationOption.id">
                                    <span v-if="currentLanguage == 'sr'">{{portalUserOrganizationOption.legalNameSr}}</span>
                                    <span v-if="currentLanguage == 'en'">{{portalUserOrganizationOption.legalNameEn}}</span>
                                    <span v-if="currentLanguage == 'src'">{{portalUserOrganizationOption.legalNameSrCyr}}</span>
                                </option>
                            </select>
                        </div>
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
<script lang="ts" src="./activate-user-roles.component.ts">
</script>
