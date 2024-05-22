<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <div v-if="portalUser">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.portalUser.detail.title')">PortalUser</span> {{portalUser.id}}</h2>
                <b-row>
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.firstName')">First Name</span>
                    </b-col>
                    <b-col sm="4">
                        <span>{{portalUser.user.firstName}}</span>
                    </b-col>
                    <b-col sm="2">
                        <span class="span-display"  v-text="$t('riportalApp.portalUser.familyName')">Family Name</span>
                    </b-col>
                    <b-col sm="4">
                        <span>{{portalUser.user.lastName}}</span>
                    </b-col>    
                </b-row>                
                <b-row class="mt-4">
                    <b-col sm="2">
                        <span class="span-display"  v-text="$t('global.form[\'email.label\']')">Family Name</span>
                    </b-col>
                    <b-col sm="4">
                        <span>{{portalUser.user.email}}</span>
                    </b-col>
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.phone')">Phone</span>
                    </b-col>
                    <b-col sm="4">
                        <span>{{portalUser.phone}}</span>
                    </b-col>
                </b-row>
                <b-row class="mt-4">
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.position')">Position</span>
                    </b-col>
                    <b-col sm="4">
                        <span>{{portalUser.position}}</span>
                    </b-col>
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.researcher')">Researcher</span>
                    </b-col>
                    <b-col sm="4">
                        <div v-if="portalUser.researcher">
                            <router-link :to="{name: 'ResearcherView', params: {researcherId: portalUser.researcher.id}}">{{portalUser.researcher.id}}</router-link>
                        </div>
                    </b-col>
                </b-row>
                <b-row class="mt-4 mb-4">
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.portalUserRoles')">Portal User Roles</span>
                    </b-col>
                    <b-col sm="4">
                        <span v-for="(portalUserRoles, i) in portalUser.portalUserRoles" :key="portalUserRoles.id">
                            {{portalUserRoles.role}}<br>
                        </span>
                    </b-col>
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.portalUser.userOrganization')">User Organization</span>
                    </b-col>
                    <b-col sm="4">
                        <div v-if="portalUser.userOrganization">
                            <router-link :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: portalUser.userOrganization.id}}">
                                <span v-if="currentLanguage == 'sr'">{{portalUser.userOrganization.legalNameSr}}</span>
                                <span v-if="currentLanguage == 'en'">{{portalUser.userOrganization.legalNameEn}}</span>
                                <span v-if="currentLanguage == 'src'">{{portalUser.userOrganization.legalNameSrCyr}}</span>
                            </router-link>
                        </div>
                    </b-col>
                </b-row>
                
                
                
                
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user-details.component.ts">
</script>
