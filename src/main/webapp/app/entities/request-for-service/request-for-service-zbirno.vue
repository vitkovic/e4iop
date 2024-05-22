<template>
    <div class="row justify-content-center">
        <b-container class="border border-primary rounded shadow">
        <div class="col-10">
            <div v-if="requestForService">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.requestForService.detail.title')">RequestForService</span> {{requestForService.id}}</h2>
                <b-row class="mt-2">
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
                <b-row class="mt-2">
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
                <b-row  class="mt-2">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.description')">Description</span>
                    </b-col>
                    <b-col sm="9">
                        <span>{{requestForService.description}}</span>
                    </b-col>
                </b-row> 
                <b-row  class="mt-2">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.services')">Services</span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.services">
                            <router-link  class="link-style" :to="{name: 'RiServiceView', params: {riServiceId: requestForService.services.id}}">
                                <span v-if="currentLanguage === 'sr'">{{requestForService.services.nameSr}}</span>
                                <span v-if="currentLanguage === 'en'">{{requestForService.services.nameEn}}</span>
                                <span v-if="currentLanguage === 'src'">{{requestForService.services.nameSrCyr}}</span>
                            </router-link>
                        </div>
                    </b-col>
                </b-row> 
                <b-row  class="mt-2">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.seeker')"></span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.users">
                            <router-link class="link-style" :to="{name: 'PortalUserView', params: {portalUserId: requestForService.users.id}}">{{requestForService.users.user.lastName}} {{requestForService.users.user.firstName}}</router-link>
                        </div>
                    </b-col>
                </b-row>
                <b-row  class="mt-2 mb-4">
                    <b-col sm="3">
                        <span class="span-display" v-text="$t('riportalApp.requestForService.seekerOrganization')"></span>
                    </b-col>
                    <b-col sm="9">
                        <div v-if="requestForService.organizations">
                            <router-link class="link-style" :to="{name: 'PortalUserOrganizationView', params: {portalUserOrganizationId: requestForService.organizations.id}}">{{requestForService.organizations.legalNameSr}}</router-link>
                        </div>
                    </b-col>
                </b-row>                                                             
            </div>
        </div>
        </b-container>
<!--***********************************************************************************************************
*                               Request Respond
*
 ***********************************************************************************************************-->
        <b-container class="border border-primary rounded shadow mt-4"  v-if="requestRespond">
        <div>
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.requestRespond.detail.title')">RequestRespond</span> {{requestForService.id}}</h2>
                <b-row class="mt-2">
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.requestRespond.date')"></span>
                    </b-col>
                    <b-col sm="2">
                        <span v-if="requestRespond.date">{{$d(Date.parse(requestRespond.date), 'short') }}</span>
                    </b-col>
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.requestRespond.startTime')"></span>
                    </b-col>
                    <b-col sm="2">
                        <span v-if="requestRespond.startTime">{{$d(Date.parse(requestRespond.startTime), 'short') }}</span>
                    </b-col>
                    <b-col  sm="2">
                        <span class="span-display" v-text="$t('riportalApp.requestRespond.endTime')">End Time</span>
                    </b-col>
                    <b-col  sm="2">
                        <span v-if="requestRespond.endTime">{{$d(Date.parse(requestRespond.endTime), 'short') }}</span>
                    </b-col>
                </b-row>                               
                <b-row class="mt-2">
                    <b-col sm="2">
                        <span class="span-display" v-text="$t('riportalApp.requestRespond.price')">Price</span>
                    </b-col>
                    <b-col sm="2">
                        <span>{{requestRespond.price}}</span>                        
                    </b-col>
                    <b-col sm="2">
                        <span  class="span-display" v-text="$t('riportalApp.requestRespond.requestForService')">Request For Service</span>
                    </b-col>
                    <b-col sm="2">
                        <div v-if="requestRespond.requestForService" >
                            <router-link style="color:blue" :to="{name: 'RequestForServiceView', params: {requestForServiceId: requestRespond.requestForService.id}}">{{requestRespond.requestForService.description}}</router-link>
                        </div>
                    </b-col>
                    <b-col sm="2">
                        <span  class="span-display" v-text="$t('riportalApp.requestRespond.requestAnswer')">Answer</span>
                    </b-col>
                    <b-col sm="2">
                        <span v-if="requestRespond.requestAnswer">
                            <span v-if="currentLanguage === 'sr'">{{requestRespond.requestAnswer.answer}}</span>
                            <span v-if="currentLanguage === 'en'">{{requestRespond.requestAnswer.answerEn}}</span>
                            <span v-if="currentLanguage === 'src'">{{requestRespond.requestAnswer.answerCyr}}</span>
                        </span>   
                    </b-col>
                </b-row>
                <b-row class="mt-2 mb-4">
                    <b-col sm="2">
                        <span class="span-display"  v-text="$t('riportalApp.requestRespond.message')">Message</span>
                    </b-col>
                     <b-col sm="10">
                        <span>{{requestRespond.message}}</span>
                    </b-col>
                </b-row>       
            </div>
        </b-container>
<!--***********************************************************************************************************
*                               Offer to respond
*
 ***********************************************************************************************************-->
        <b-container class="border border-primary rounded shadow mt-4" v-if="serviceOfferRespond">
        <div >
            <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.serviceOfferRespond.detail.title')"></span>
                (<span v-text="$t('riportalApp.serviceOfferRespond.detail.forRequest')"></span>
             {{requestForService.id}})</h2>
            <b-row class="mt-2">
                <b-col sm="2">
                    <span class="span-display" v-text="$t('riportalApp.serviceOfferRespond.date')">Date</span>
                </b-col>
                <b-col sm="6">
                    <span v-if="serviceOfferRespond.date">{{$d(Date.parse(serviceOfferRespond.date), 'short') }}</span>
                </b-col>
            </b-row>
            <b-row class="mt-2">
                <b-col sm="2">
                    <span class="span-display" v-text="$t('riportalApp.serviceOfferRespond.description')">Description</span>
                </b-col>
                <b-col sm="6">
                    <span>{{serviceOfferRespond.description}}</span>
                </b-col>
            </b-row>
            <b-row class="mt-2">
                <b-col sm="2">
                    <span class="span-display" v-text="$t('riportalApp.serviceOfferRespond.offerAnswer')">Offer Answer</span>
                </b-col>
                <b-col sm="6">
                    <div v-if="serviceOfferRespond.offerAnswer">
                        {{serviceOfferRespond.offerAnswer.answer}}
                    </div>
                </b-col>
            </b-row>

              
        </div>    
        </b-container>
        <b-container class="mt-4">
        <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
        </button>
        </b-container>


    </div>
</template>

<script lang="ts" src="./request-for-service-zbirno.component.ts">
</script>
