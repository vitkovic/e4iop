<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="requestForService">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.requestForService.detail.title')">RequestForService</span> {{requestForService.id}}</h2>
                <b-row class="mt-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.date')">Date</span>
                    </b-col>
                    <b-col sm="3">
                        <span v-if="requestForService.date">{{$d(Date.parse(requestForService.date), 'short') }}</span>
                    </b-col>
                    <b-col sm="3">
                        <span  class="span-display" v-text="$t('riportalApp.requestForService.deadline')">Deadline</span>
                    </b-col>
                    <b-col sm="3">                        
                        <span v-if="requestForService.deadline">{{$d(Date.parse(requestForService.deadline), 'short') }}</span>
                    </b-col>
                </b-row>
                <b-row class="mt-4">
                    <b-col sm="3">
                        <span  class="span-display" v-text="$t('riportalApp.requestForService.startTime')">Start Time</span>
                    </b-col>
                    <b-col sm="3">
                        <span v-if="requestForService.startTime">{{$d(Date.parse(requestForService.startTime), 'short') }}</span>
                    </b-col>
                    <b-col sm="3">
                        <span  class="span-display" v-text="$t('riportalApp.requestForService.endTime')">End Time</span>
                    </b-col>
                    <b-col sm="3">
                        <span v-if="requestForService.endTime">{{$d(Date.parse(requestForService.endTime), 'short') }}</span>
                    </b-col>
                </b-row>
                <b-row  class="mt-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.description')">Description</span>
                    </b-col>
                    <b-col sm="9">
                        <span>{{requestForService.description}}</span>
                    </b-col>
                </b-row> 
                <b-row  class="mt-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.services')">Services</span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.services">
                            <router-link class="link-style" :to="{name: 'RiServiceView', params: {riServiceId: requestForService.services.id}}">
                                <span v-if="currentLanguage === 'sr'">{{requestForService.services.nameSr}}</span>
                                <span v-if="currentLanguage === 'en'">{{requestForService.services.nameEn}}</span>
                                <span v-if="currentLanguage === 'src'">{{requestForService.services.nameSrCyr}}</span>
                            </router-link>
                        </div>
                    </b-col>
                </b-row> 
                <b-row  class="mt-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.seeker')"></span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.users">
                            <router-link class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: requestForService.users.id}}">{{requestForService.users.user.lastName}} {{requestForService.users.user.firstName}}</router-link>
                        </div>
                    </b-col>
                </b-row>
                <b-row  class="mt-4 mb-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.seekerOrganization')"></span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.organizations">
                            <router-link class="link-style" :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: requestForService.organizations.id}}">{{requestForService.organizations.legalNameSr}}</router-link>
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

<script lang="ts" src="./request-for-service-details.component.ts">
</script>
