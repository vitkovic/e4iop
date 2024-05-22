<template>
    <div class="row justify-content-center">
        <div class="col-10">
            <div v-if="portalUserOrganization">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.portalUserOrganization.detail.title')">PortalUserOrganization</span> {{portalUserOrganization.id}}</h2>
                <b-container>
                    <b-row class="mt-4">
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.legalNameSr')">Legal Name Sr</span>
                        </b-col>
                        <b-col sm="3">
                            <span v-if="currentLanguage == 'sr'">{{portalUserOrganization.legalNameSr}}</span>
                            <span v-if="currentLanguage == 'src'">{{portalUserOrganization.legalNameSrCyr}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.legalNameEn')">Legal Name En</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.legalNameEn}}</span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-4">
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.shortName')">Short Name</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.shortName}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.webAddress')">Web Address</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.webAddress}}</span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-4">
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.pib')">Pib</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.pib}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.registrationNumber')">Registration Number</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.registrationNumber}}</span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-4">
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.keywords')">Keywords</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.keywords}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display" v-text="$t('riportalApp.portalUserOrganization.address')">Address</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.address}}</span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-4">
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.postalCode')">Postal Code</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.postalCode}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.city')">City</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.city}}</span>
                        </b-col>
                    </b-row>
                    <b-row class="mt-4 mb-4">
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.country')">Country</span>
                        </b-col>
                        <b-col sm="3">
                            <span>{{portalUserOrganization.country}}</span>
                        </b-col>
                        <b-col sm="3">
                            <span class="span-display"  v-text="$t('riportalApp.portalUserOrganization.legalStatus')">Legal Status</span>
                        </b-col>
                        <b-col sm="3">
                            <div v-if="portalUserOrganization.legalStatus">
                                <router-link :to="{name: 'PuoLegalStatusView', params: {puoLegalStatusId: portalUserOrganization.legalStatus.id}}">{{portalUserOrganization.legalStatus.value}}</router-link>
                            </div>
                        </b-col>
                    </b-row>
                </b-container>
                
                
                
                
                
                
                <!--
                <dl class="row jh-entity-details">
                   
                   
                    <dt>
                        <span v-text="$t('riportalApp.portalUserOrganization.legalNameSrCyr')">Legal Name Sr Cyr</span>
                    </dt>
                    <dd>
                        <span>{{portalUserOrganization.legalNameSrCyr}}</span>
                    </dd>
                   
                </dl>
                -->
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>               
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./portal-user-organization-details.component.ts">
</script>
