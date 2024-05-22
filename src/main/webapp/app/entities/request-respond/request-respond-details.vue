<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="requestRespond">
                <h2 class="jh-entity-heading"><span v-text="$t('riportalApp.requestRespond.detail.title')">RequestRespond</span> {{requestRespond.id}}</h2>
                <b-row class="mt-4">
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
                <b-row class="mt-4">
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
                <b-row class="mt-4 mb-4">
                    <b-col sm="2">
                        <span class="span-display"  v-text="$t('riportalApp.requestRespond.message')">Message</span>
                    </b-col>
                     <b-col sm="10">
                        <span>{{requestRespond.message}}</span>
                    </b-col>
                </b-row>
                
                
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <button type="button" id="sendToOffer" class="btn btn-primary" v-on:click="sendToOffer()">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('riportalApp.requestRespond.respondsToOffer')"></span>
                </button>
                <router-link  
                    :to="{name: 'RequestForServiceZbirno', params: {requestForServiceId: requestRespond.requestForService.id}}"  tag="button" class="btn btn-info btn-md edit">
                    <span class="d-none d-md-inline" v-text="$t('entity.action.zbirniPrikaz')">Edit</span>
                </router-link>
<!--
                <router-link  :to="{name: 'ServiceOfferRespondRespondCreateChoice', params: {requestRespondId: requestRespond.id}}"  tag="button" class="btn btn-primary btn-md edit">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('riportalApp.requestRespond.respondsToOffer')">Edit</span>
                </router-link>             
-->
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./request-respond-details.component.ts">
</script>
