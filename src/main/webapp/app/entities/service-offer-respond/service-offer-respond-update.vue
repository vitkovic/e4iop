<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="riportalApp.serviceOfferRespond.home.createOrEditLabel" v-text="$t('riportalApp.serviceOfferRespond.home.createOrEditLabel')">Create or edit a ServiceOfferRespond</h2>
                <div>
                    <div class="form-group" v-if="serviceOfferRespond.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="serviceOfferRespond.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceOfferRespond.date')" for="service-offer-respond-date">Date</label>
                        <div class="d-flex"> 
                            <date-picker  style="width: -webkit-fill-available;" format="DD.MM.YYYY HH:mm" v-model="$v.serviceOfferRespond.date.$model" type="datetime"
                                id="service-offer-respond-date" required                                     
                                >
                            </date-picker>                                                               
                        </div> 
                        <!--
                        <div class="d-flex">
                            <input id="service-offer-respond-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.serviceOfferRespond.date.$invalid, 'invalid': $v.serviceOfferRespond.date.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.serviceOfferRespond.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                        -->
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceOfferRespond.description')" for="service-offer-respond-description">Description</label>
                        <input type="text" class="form-control" name="description" id="service-offer-respond-description"
                            :class="{'valid': !$v.serviceOfferRespond.description.$invalid, 'invalid': $v.serviceOfferRespond.description.$invalid }" v-model="$v.serviceOfferRespond.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceOfferRespond.offerAnswer')" for="service-offer-respond-offerAnswer">Offer Answer</label>
                        <select class="form-control" id="service-offer-respond-offerAnswer" name="offerAnswer" v-model="serviceOfferRespond.offerAnswer">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="serviceOfferRespond.offerAnswer && answerToOfferOption.id === serviceOfferRespond.offerAnswer.id ? serviceOfferRespond.offerAnswer : answerToOfferOption" v-for="answerToOfferOption in answerToOffers" :key="answerToOfferOption.id">
                                {{answerToOfferOption.answer}}
                            </option>
                        </select>
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('riportalApp.serviceOfferRespond.respondsToRequest')" for="service-offer-respond-respondsToRequest">Responds To Request</label>
                        <select class="form-control" id="service-offer-respond-respondsToRequest" name="respondsToRequest" v-model="serviceOfferRespond.respondsToRequest">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="serviceOfferRespond.respondsToRequest && requestRespondOption.id === serviceOfferRespond.respondsToRequest.id ? serviceOfferRespond.respondsToRequest : requestRespondOption" v-for="requestRespondOption in requestResponds" :key="requestRespondOption.id">{{requestRespondOption.id}}</option>
                        </select>
                    </div>
                    -->
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.serviceOfferRespond.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                    <router-link  v-if="serviceOfferRespond.respondToRequest" 
                        :to="{name: 'RequestForServiceZbirno', params: {requestForServiceId: serviceOfferRespond.respondToRequest.requestForService.id}}"  tag="button" class="btn btn-info btn-md edit">
                        <span class="d-none d-md-inline" v-text="$t('entity.action.zbirniPrikaz')">Edit</span>
                    </router-link>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./service-offer-respond-update.component.ts">
</script>
